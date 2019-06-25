package com.mindorks.framework.mvp.ui.manager.restaurant.dish;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.mindorks.framework.mvp.R;
import com.mindorks.framework.mvp.data.network.model.DishDetailsResponse;
import com.mindorks.framework.mvp.data.network.model.DishRequestDto;
import com.mindorks.framework.mvp.ui.base.BaseActivity;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ManagerDishDetailsActivity extends BaseActivity implements
        ManagerDishDetailsMvpView,ManagerDishNutitiveValueCallback {

    private static final String TAG = "ManagerDishDetailsActivity";

    @Inject
    ManagerDishDetailsMvpPresenter<ManagerDishDetailsMvpView> mPresenter;


    @BindView(R.id.manager_dish_details_img)
    ImageView imageView;

    @BindView(R.id.manager_dish_details_name)
    TextView txtViewName;

//    @BindView(R.id.manager_dish_details_kitchen)
//    TextView txtViewKitchen;

    @BindView(R.id.manager_dish_details_txt_price_values)
    TextView txtViewPrice;

    @BindView(R.id.manager_dish_details_txt_description_values)
    TextView txtViewDescription;

    @BindView(R.id.manager_nutritive_values_txt)
    EditText txtNutritionValue;

    // Kitchen
    @Inject
    ManagerDishDetailsNutritiveValuesAdapter mNutritiveValuesAdapter;

    @Inject
    LinearLayoutManager mLayoutManager;

    @BindView(R.id.manager_dish_details_nutritive_values_recyclerview)
    RecyclerView mRecyclerView;

    @BindView(R.id.nutritive_values_spinner)
    Spinner spinner;

    @BindView(R.id.dish_kitchen_spinner)
    Spinner kitchenSpinner;

    ArrayAdapter<String> adapter;
    ArrayAdapter<String> kitchenAdapter;

    List<String> nutritionValue;

    DishDetailsResponse.DishDetails dishDetailsOrginal;
    DishDetailsResponse.DishDetails dishDetailsEdited;
    private long dishId;


    private static final int CAMERA_REQUEST_CODE = 1021;
    private static final int READ_EXTERNAL_STORAGE_REQUEST_CODE = 1022;
    private static final int WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 1023;
    private static final int READ_EXTERNAL_STORAGE_REQUEST_CODE_CAMERA_SHOT = 1027;


    private static final int REQUEST_CAMERA = 1, SELECT_FILE = 0;
    byte[] imgBytes = null;
    private Uri userImageUri = null;
    private String mCameraFileName;

    public ManagerDishDetailsActivity() {
        // Required empty public constructor
    }

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, ManagerDishDetailsActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_dish_details);

        getActivityComponent().inject(this);

        setUnBinder(ButterKnife.bind(this));

        mPresenter.onAttach(ManagerDishDetailsActivity.this);

        setUp();

    }

    @Override
    protected void onResume() {
        super.onResume();
        // vi3 prebaceno onResume
        // myLocalSetUp();
    }

    @Override
    protected void setUp() {
        mNutritiveValuesAdapter.setmCallback(this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mNutritiveValuesAdapter);
        // vi3 prebaceno onResume
        myLocalSetUp();
    }

    private void myLocalSetUp() {
        Bundle bundle = getIntent().getExtras();
        dishId = bundle.getLong("dishId",-1L);

        this.nutritionValue = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.nutrition_values)));

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, nutritionValue);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

        mPresenter.getRestaurantKithen();

//        kitchenSpinner.setAdapter(adapter);

        //Ako je id razlicit od -1 ucitaj podatke pa prikazi.
        if (dishId != -1L) {
            mPresenter.onViewPrepared(dishId);
        } else {
            // FIXME vlada mici: ako hoces da ide na onResume, hendlaj spinner :P
            this.dishDetailsEdited = new DishDetailsResponse.DishDetails();
            this.dishDetailsEdited.setKitchen(new DishDetailsResponse.Kitchen());
            this.dishDetailsEdited.setNutritiveValues(new ArrayList<DishDetailsResponse.NutritiveValue>());
            this.dishDetailsOrginal = new DishDetailsResponse.DishDetails();
            this.dishDetailsOrginal.setKitchen(new DishDetailsResponse.Kitchen());
            this.dishDetailsOrginal.setNutritiveValues(new ArrayList<DishDetailsResponse.NutritiveValue>());
        }

        // =========================================================== vi3: slika
        // trebalo bi i ovde postaviti, jer moze jelo od nule da se doda
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseOrTakeImage();
            }
        });

    }

    @Override
    public void updateDishDetails(final DishDetailsResponse.DishDetails dishDetails) {
        this.dishDetailsEdited = dishDetails;
        this.saveOrginalDishState();

        if (dishDetails.getImageUrl() != null) {
            Glide.with(this)
                    .load(dishDetails.getImageUrl())
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .into(imageView);
            // FIXME vi3: ako liniju ispod uklonim, nece da update-uje sliku
            imageView.setImageResource(R.drawable.login_bg);
        }

        if (dishDetails.getName() != null) {
            txtViewName.setText(dishDetails.getName());
        }
        this.setKitchenSpiner(dishDetails.getKitchen());
//       if (dishDetails.getKitchen() != null && dishDetails.getKitchen().getName() != null) {
//            txtViewKitchen.setText(dishDetails.getKitchen().getName());
//        }
        if (dishDetails.getPrice() != null) {
            txtViewPrice.setText(String.format(Locale.US, "%.2f", dishDetails.getPrice()));
        }
        if (dishDetails.getDescription() != null) {
            txtViewDescription.setText(dishDetails.getDescription());
        }

        if (dishDetails.getNutritiveValues() != null) {
            mNutritiveValuesAdapter.addItems(dishDetails.getNutritiveValues());
            this.nutritionValue = this.removeUsedNutirionValues(dishDetails.getNutritiveValues());
            adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, nutritionValue);
            spinner.setAdapter(adapter);
        } else {
            // TODO vi3: ne bi bilo lose prikazati prazan prikaz kada nema nutritivnih vrednosti
            Toast.makeText(this,
                    "Nema nutritivnih vrednosti za ovo jelo",
                    Toast.LENGTH_SHORT).show();
        }

        // =========================================================== vi3: slika
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseOrTakeImage();
            }
        });

    }

    // vi3: slika
    private void chooseOrTakeImage() {
        final CharSequence[] items = {"Camera", "Gallery", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Image");

        builder.setItems(items, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (items[i].equals("Camera")) {

                    // prvo moramo traziti permission za pisanje
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (ActivityCompat.checkSelfPermission(ManagerDishDetailsActivity.this,
                                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(ManagerDishDetailsActivity.this,
                                    new String[]{Manifest.permission.CAMERA}, CAMERA_REQUEST_CODE);
                            return;
                        }
                    }

                    StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                    StrictMode.setVmPolicy(builder.build());
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    File outFile = new File(Environment.getExternalStorageDirectory(),
                            "jelo_" +
                                    String.valueOf(System.currentTimeMillis()) + ".jpg");
                    mCameraFileName = outFile.toString();
                    Uri imageUri = Uri.fromFile(outFile);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                    startActivityForResult(intent, REQUEST_CAMERA);

                } else if (items[i].equals("Gallery")) {
                    // ovo sam ostavio, jer mi stvarno nije jasno po kom principu ovo radi jebeno
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (ActivityCompat.checkSelfPermission(ManagerDishDetailsActivity.this,
                                Manifest.permission.READ_EXTERNAL_STORAGE)
                                != PackageManager.PERMISSION_GRANTED) {

                            // Should we show an explanation?
                            if (shouldShowRequestPermissionRationale(
                                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
                                // Explain to the user why we need to read the contacts
                            }

                            ActivityCompat.requestPermissions(ManagerDishDetailsActivity.this,
                                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                    READ_EXTERNAL_STORAGE_REQUEST_CODE);

                            // MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE is an
                            // app-defined int constant that should be quite unique

                            return;
                        }
                    }

                    System.out.println("------------------------------ Imas permisiju, konjino " +
                            "jedna");
                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                    intent.setType("image/*");
                    startActivityForResult(intent, SELECT_FILE);

                } else if (items[i].equals("Cancel")) {
                    dialogInterface.dismiss();
                }
            }
        });
        builder.show();
    }
    // vi3: slika
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case CAMERA_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                    StrictMode.setVmPolicy(builder.build());
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    File outFile = new File(Environment.getExternalStorageDirectory(),
                            "jelo_" +
                                    String.valueOf(System.currentTimeMillis()) + ".jpg");
                    mCameraFileName = outFile.toString();
                    Uri imageUri = Uri.fromFile(outFile);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                    startActivityForResult(intent, REQUEST_CAMERA);
                } else {
                    Toast.makeText(this, "Camera permission missing",
                            Toast.LENGTH_SHORT).show();
                }
                break;

            case READ_EXTERNAL_STORAGE_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                    intent.setType("image/*");
                    startActivityForResult(intent, SELECT_FILE);
                } else {
                    Toast.makeText(this, "No access to external storage permission " +
                                    "missing",
                            Toast.LENGTH_SHORT).show();
                }
                break;
            case READ_EXTERNAL_STORAGE_REQUEST_CODE_CAMERA_SHOT:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Uri imgUri = Uri.fromFile(new File(mCameraFileName));
                    imageView.setImageURI(imgUri);
                    String path = getPath(imgUri);
                    if (path == null) {
                        path = mCameraFileName;
                    }
                    imgBytes = getBytesFromFile(path);
                } else {
                    Toast.makeText(this, "No access to external storage permission " +
                                    "missing when taking shot",
                            Toast.LENGTH_SHORT).show();
                }

        }
    }
    // vi3: slika
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_CAMERA) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ActivityCompat.checkSelfPermission(ManagerDishDetailsActivity.this,
                            Manifest.permission.READ_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED) {

                        // Should we show an explanation?
                        if (shouldShowRequestPermissionRationale(
                                Manifest.permission.READ_EXTERNAL_STORAGE)) {
                            // Explain to the user why we need to read the contacts
                        }

                        ActivityCompat.requestPermissions(ManagerDishDetailsActivity.this,
                                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                READ_EXTERNAL_STORAGE_REQUEST_CODE_CAMERA_SHOT);

                        // MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE is an
                        // app-defined int constant that should be quite unique

                        return;
                    }
                }

                Uri imgUri = null;
                if (data == null || data.getData() == null) {
                    imgUri = Uri.fromFile(new File(mCameraFileName));
                }
                imageView.setImageURI(imgUri);
                String path = getPath(imgUri);
                if (path == null) {
                    path = mCameraFileName;
                }
                imgBytes = getBytesFromFile(path);
            } else if (requestCode == SELECT_FILE) {
                userImageUri = data.getData();
                String path = getPath(userImageUri);
                imgBytes = getBytesFromFile(path);
                if (imgBytes == null) {
                    Toast.makeText(this, "Cannot read image", Toast.LENGTH_SHORT).show();
                    return;
                }
                imageView.setImageURI(userImageUri);
            }
        }
    }
    // vi3: slika
    //method to get the file path from uri
    public String getPath(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = getContentResolver().query(uri, projection, null, null,
                null);
        if (cursor == null) return null;
        int column_index =             cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String s=cursor.getString(column_index);
        cursor.close();
        return s;
    }
    // vi3: slika
    public byte[] getBytesFromFile(String imgPath) {
        File file = new File(imgPath);
        int size = (int) file.length();
        byte[] bytes = new byte[size];
        try {
            BufferedInputStream buf = new BufferedInputStream(new FileInputStream(file));
            buf.read(bytes, 0, bytes.length);
            buf.close();
            return bytes;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public byte[] getImgBytes() {
        return imgBytes;
    }

    // =========================================================== vi3: slika

    private void setKitchenSpiner(DishDetailsResponse.Kitchen kitchen) {
        if(kitchen!=null){
            for(int i=0;i<kitchenAdapter.getCount();i++){
                if(kitchen.getName().toLowerCase().equals(kitchenAdapter.getItem(i).toLowerCase())){
                    kitchenSpinner.setSelection(i);
                }
            }
        }
    }

    @Override
    public void setKitchenAdapter(List<String> kitchens) {
        kitchenAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, kitchens);
        kitchenSpinner.setAdapter(kitchenAdapter);
    }

    @Override
    public void back() {
        finish();
    }

    private void saveOrginalDishState() {
        this.dishDetailsOrginal = new DishDetailsResponse.DishDetails();
        this.dishDetailsOrginal.setId(this.dishDetailsEdited.getId());
        this.dishDetailsOrginal.setDescription(this.dishDetailsEdited.getDescription());
        DishDetailsResponse.Kitchen kitchen = new DishDetailsResponse.Kitchen();
        kitchen.setId(this.dishDetailsEdited.getKitchen().getId());
        kitchen.setName(this.dishDetailsEdited.getName());
        this.dishDetailsOrginal.setKitchen(kitchen);
        this.dishDetailsOrginal.setImageUrl(this.dishDetailsEdited.getImageUrl());
        this.dishDetailsOrginal.setName(this.dishDetailsEdited.getName());
        this.dishDetailsOrginal.setPrice(this.dishDetailsEdited.getPrice());
        List<DishDetailsResponse.NutritiveValue> nutritiveValues = new ArrayList<>();
        nutritiveValues.addAll(this.dishDetailsEdited.getNutritiveValues());
        this.dishDetailsOrginal.setNutritiveValues(nutritiveValues);
    }

    @OnClick(R.id.manager_dish_details_cancel_btn)
    public void cancelUpdate() {
        this.updateDishDetails(this.dishDetailsOrginal);
        // vi3: slika - ponistimo imgBytes
        imgBytes = null;
    }

    @OnClick(R.id.manager_dish_details_submit_btn)
    public void saveChanges(){
        System.out.println("Ovdije sam");
        this.dishDetailsEdited.setName(txtViewName.getText().toString());
        this.dishDetailsEdited.setPrice(Double.parseDouble(txtViewPrice.getText().toString()));
        this.dishDetailsEdited.setDescription(txtViewDescription.getText().toString());
        DishDetailsResponse.Kitchen kitchen = new DishDetailsResponse.Kitchen();
        kitchen.setName(kitchenSpinner.getSelectedItem().toString());
        this.dishDetailsEdited.setKitchen(kitchen);

        System.out.println(this.dishDetailsEdited.getNutritiveValues().size());


        DishRequestDto requestData = new DishRequestDto(this.dishDetailsEdited);
        if(dishId!=-1L) {
            mPresenter.updateDish(dishId,requestData);
        }else{
            System.out.println("PUT JELA");
            mPresenter.createDish(requestData);
        }
    }


    private List<String> removeUsedNutirionValues(List<DishDetailsResponse.NutritiveValue> nutritiveValues) {
        List<String> myValues = new ArrayList<>();
        List<String> dishValues = new ArrayList<>();

        for (String value : nutritionValue) {
            myValues.add(value.toLowerCase());
        }

        for (DishDetailsResponse.NutritiveValue value : nutritiveValues) {
            dishValues.add(value.getName().toLowerCase());
        }

        List<String> union = new ArrayList<>(myValues);
        union.addAll(dishValues);

        List<String> intersection = new ArrayList<>(myValues);
        intersection.retainAll(dishValues);


        union.removeAll(intersection);
        return union;
    }


    @OnClick(R.id.manager_add_nutritive_value_btn)
    public void addNutritionValue() {
        String nutritionValue = spinner.getSelectedItem().toString();
        System.out.println(spinner.getSelectedItemPosition());
        Toast.makeText(this, spinner.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
        System.out.println(this.txtNutritionValue.getText().toString());
        switch (nutritionValue.toLowerCase()) {
            case "kalorijska vrednost": {
                DishDetailsResponse.NutritiveValue value = new DishDetailsResponse.NutritiveValue();
                value.setName(this.spinner.getSelectedItem().toString());
                value.setValue(Double.parseDouble(this.txtNutritionValue.getText().toString()));
                value.setUnit("kcal");
                this.dishDetailsEdited.getNutritiveValues().add(value);
                this.mNutritiveValuesAdapter.addItems(this.dishDetailsEdited.getNutritiveValues());
                this.nutritionValue.remove(this.spinner.getSelectedItemPosition());
                adapter.notifyDataSetChanged();
                break;
            }
            case "masti": {
                DishDetailsResponse.NutritiveValue value = new DishDetailsResponse.NutritiveValue();
                value.setName(this.spinner.getSelectedItem().toString());
                value.setValue(Double.parseDouble(this.txtNutritionValue.getText().toString()));
                value.setUnit("g");
                this.dishDetailsEdited.getNutritiveValues().add(value);
                this.mNutritiveValuesAdapter.addItems(this.dishDetailsEdited.getNutritiveValues());
                this.nutritionValue.remove(this.spinner.getSelectedItemPosition());
                adapter.notifyDataSetChanged();
                break;
            }
            case "proteini": {
                DishDetailsResponse.NutritiveValue value = new DishDetailsResponse.NutritiveValue();
                value.setName(this.spinner.getSelectedItem().toString());
                value.setValue(Double.parseDouble(this.txtNutritionValue.getText().toString()));
                value.setUnit("g");
                this.dishDetailsEdited.getNutritiveValues().add(value);
                this.mNutritiveValuesAdapter.addItems(this.dishDetailsEdited.getNutritiveValues());
                this.nutritionValue.remove(this.spinner.getSelectedItemPosition());
                adapter.notifyDataSetChanged();
                break;
            }
            case "ugljeni hidrati": {
                DishDetailsResponse.NutritiveValue value = new DishDetailsResponse.NutritiveValue();
                value.setName(this.spinner.getSelectedItem().toString());

                value.setValue(Double.parseDouble(this.txtNutritionValue.getText().toString()));
                value.setUnit("g");
                this.dishDetailsEdited.getNutritiveValues().add(value);
                this.mNutritiveValuesAdapter.addItems(this.dishDetailsEdited.getNutritiveValues());
                this.nutritionValue.remove(this.spinner.getSelectedItemPosition());
                adapter.notifyDataSetChanged();
                break;
            }
        }
        this.txtNutritionValue.setText("");


    }

    @Override
    public void deleteNutritiveValue(String name) {
        List<DishDetailsResponse.NutritiveValue> nutritiveValues = this.dishDetailsEdited.getNutritiveValues();
        for(DishDetailsResponse.NutritiveValue value : nutritiveValues){
            if(value.getName().toLowerCase().equals(name.toLowerCase())){
                this.dishDetailsEdited.getNutritiveValues().remove(value);
                this.nutritionValue.add(value.getName());
                break;
            }
        }
        mNutritiveValuesAdapter.addItems(this.dishDetailsEdited.getNutritiveValues());
        adapter.notifyDataSetChanged();
    }
}
