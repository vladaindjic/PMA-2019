package com.mindorks.framework.mvp.ui.manager.restaurant.details;


import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.mindorks.framework.mvp.R;
import com.mindorks.framework.mvp.data.network.model.RestaurantDetailsResponse;
import com.mindorks.framework.mvp.di.component.ActivityComponent;
import com.mindorks.framework.mvp.ui.base.BaseFragment;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class ManagerRestaurantDetailsFragment extends BaseFragment implements
        ManagerRestaurantDetailsMvpView, ManagerRestaurantDetailsKitchensAdapter.ManagerRestaurantDetailsKitchensAdapterCallback {

    private static final String TAG = "ManagerRestaurantDetailsFragment";


    @Inject
    ManagerRestaurantDetailsMvpPresenter<ManagerRestaurantDetailsMvpView> mPresenter;


    @BindView(R.id.manager_restaurant_details_img)
    ImageView imageView;

    @BindView(R.id.manager_restaurant_details_name)
    EditText editName;

    @BindView(R.id.manager_restaurant_details_address)
    EditText editAddress;

    @BindView(R.id.manager_dish_details_txt_description_values)
    CheckBox checkBoxDelivery;

    @BindView(R.id.manager_dish_details_edit_txt_price_values)
    EditText editPhone;

    @BindView(R.id.manager_restaurant_details_edit_txt_work_time_values)
    EditText editWorkTime;

    @BindView(R.id.manager_restaurant_details_edit_txt_email_values)
    EditText editEmail;

    @BindView(R.id.manager_kitchens_autocomplete_txt)
    AutoCompleteTextView autoKitchen;

    @BindView(R.id.manager_add_kitchen_btn)
    Button btnAddKitchen;

    @BindView(R.id.manager_restaurant_details_submit_btn)
    Button btnSubmit;
    @BindView(R.id.manager_restaurant_details_cancel_btn)
    Button btnCancel;

    // Kitchen
    @Inject
    ManagerRestaurantDetailsKitchensAdapter mKitchensAdapter;

    @Inject
    LinearLayoutManager mLayoutManager;

    @BindView(R.id.manager_dish_details_nutritive_values_recyclerview)
    RecyclerView mRecyclerView;

    ManagerKitchenArrayAdapter mKitchenArrayAdapter;

    private RestaurantDetailsResponse.Kitchen typedKitchen;

    private RestaurantDetailsResponse.RestaurantDetails restaurantDetails;

    private RestaurantDetailsResponse.RestaurantDetails originalRestaurantDetails;

    private static final int CAMERA_REQUEST_CODE = 10001;
    private static final int READ_EXTERNAL_STORAGE_REQUEST_CODE = 10002;
    private static final int WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 10003;

    private static final int REQUEST_CAMERA = 1, SELECT_FILE = 0;
    byte[] imgBytes = null;
    private Uri userImageUri = null;
    private String mCameraFileName;

    public static ManagerRestaurantDetailsFragment newInstance() {
        Bundle args = new Bundle();
        ManagerRestaurantDetailsFragment fragment = new ManagerRestaurantDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public ManagerRestaurantDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_manager_restaurant_details, container, false);

        ActivityComponent component = getActivityComponent();
        if (component != null) {
            component.inject(this);
            setUnBinder(ButterKnife.bind(this, view));
            mPresenter.onAttach(this);
            mKitchensAdapter.setmCallback(this);
        }
        return view;
    }

    @Override
    protected void setUp(View view) {
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mKitchensAdapter);

        // TODO vi3: izvuci iz LoginResponsa. a pre toga sacuvati u preferences
        mPresenter.onViewPrepared();
        //mPresenter.getAndPrepareKitchensForAutocomplete();

        // mislim da ima vise logike ovo ostavit ovde, nego svaki put setovati
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelUpdatingRestaurantDetails();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitUpdatingRestaurantDetails();
            }
        });

        btnAddKitchen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addKitchenToRestaurant();
            }
        });

        // kada korisnik izabere kuhinju, mi nju sacuvamo
        autoKitchen.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object o = parent.getItemAtPosition(position);
                if (o instanceof RestaurantDetailsResponse.Kitchen) {
                    memoizeTypedKitchen((RestaurantDetailsResponse.Kitchen) o);
                }
            }
        });

        // kada se nesto otkuca, mi ponistimo kuhinju iz izbora
        autoKitchen.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                removeTypedKitchen();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                removeTypedKitchen();
            }

            @Override
            public void afterTextChanged(Editable s) {
                removeTypedKitchen();
            }
        });

        // podrazumevano uklanjamo kuhinju
        removeTypedKitchen();
    }
    // TODO vi3: vidi da li je i ovo ovde ok???
    @Override
    synchronized public void prepareKitchensForAutocomplete(List<RestaurantDetailsResponse.Kitchen> kitchenList) {
        mKitchenArrayAdapter = new ManagerKitchenArrayAdapter(getContext(),
                R.id.manager_autocomplete_kitchen_list_item_name, kitchenList);
        // cim prvo slovo unese, nesto ce se prikazati
        autoKitchen.setThreshold(1);
        autoKitchen.setAdapter(mKitchenArrayAdapter);
        if (this.restaurantDetails != null) {
            mKitchenArrayAdapter.checkKitchensThatAreInRestaurantAndUpdateList(this.restaurantDetails);
        }
    }
    @Override
    synchronized public void updateRestaurantDetails(final RestaurantDetailsResponse.RestaurantDetails restaurantDetails) {
        this.restaurantDetails = restaurantDetails;
        this.saveOriginalRestaurantDetailsState();

        // azuriranje polja koja se prikazuje
        editName.setText(restaurantDetails.getName());
        editAddress.setText(restaurantDetails.getAddress());

        checkBoxDelivery.setChecked(restaurantDetails.isDelivery());
        editPhone.setText(restaurantDetails.getPhone());

        editWorkTime.setText(restaurantDetails.getWorkTime());
        editEmail.setText(restaurantDetails.getEmail());

        // TODO vi3: prikazati kuhinju
        if (restaurantDetails.getKitchens() != null) {
            mKitchensAdapter.addItems(restaurantDetails.getKitchens());
        } else {
            // TODO vi3: ne bi bilo lose prikazati prazan prikaz kada kuhinja nema
            Toast.makeText(getContext(),
                    "Nema kuhinja za ovaj restoran",
                    Toast.LENGTH_SHORT).show();
        }

        if (restaurantDetails.getImageUrl() != null) {
            Glide.with(this)
                    .load(restaurantDetails.getImageUrl())
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .into(imageView);
        }
        // poizbacuj sve kuhinje
        if (mKitchenArrayAdapter != null)
            mKitchenArrayAdapter.checkKitchensThatAreInRestaurantAndUpdateList(this.restaurantDetails);

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

        AlertDialog.Builder builder = new AlertDialog.Builder(getBaseActivity());
        builder.setTitle("Add Image");

        builder.setItems(items, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (items[i].equals("Camera")) {

                    // prvo moramo traziti permission za pisanje
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (ActivityCompat.checkSelfPermission(getBaseActivity(),
                                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getBaseActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(getBaseActivity(),
                                    new String[]{Manifest.permission.CAMERA}, CAMERA_REQUEST_CODE);
                            return;
                        }
                    }

                    StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                    StrictMode.setVmPolicy(builder.build());
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    File outFile = new File(Environment.getExternalStorageDirectory(),
                            "restoran_" +
                                    String.valueOf(System.currentTimeMillis()) + ".jpg");
                    mCameraFileName = outFile.toString();
                    Uri imageUri = Uri.fromFile(outFile);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                    startActivityForResult(intent, REQUEST_CAMERA);

                } else if (items[i].equals("Gallery")) {
                    // ovo sam ostavio, jer mi stvarno nije jasno po kom principu ovo radi jebeno
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (getBaseActivity().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                                != PackageManager.PERMISSION_GRANTED) {

                            // Should we show an explanation?
                            if (shouldShowRequestPermissionRationale(
                                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
                                // Explain to the user why we need to read the contacts
                            }

                            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
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
                            "restoran_" +
                                    String.valueOf(System.currentTimeMillis()) + ".jpg");
                    mCameraFileName = outFile.toString();
                    Uri imageUri = Uri.fromFile(outFile);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                    startActivityForResult(intent, REQUEST_CAMERA);
                } else {
                    Toast.makeText(getBaseActivity(), "Camera permission missing",
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
                    Toast.makeText(getBaseActivity(), "No access to external storage permission " +
                                    "missing",
                            Toast.LENGTH_SHORT).show();
                }
                break;

        }
    }
    // vi3: slika
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_CAMERA) {
                Uri imgUri = data.getData();
                if (data.getData() == null) {
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
                    Toast.makeText(getBaseActivity(), "Cannot read image", Toast.LENGTH_SHORT).show();
                    return;
                }
                imageView.setImageURI(userImageUri);
                System.out.println("VI3: SIZE: " + imgBytes.length);
            }
        }
    }
    // vi3: slika
    //method to get the file path from uri
    public String getPath(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = getBaseActivity().getContentResolver().query(uri, projection, null, null,
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

    // cuvamo originalno stanje
    private void saveOriginalRestaurantDetailsState() {
        this.originalRestaurantDetails = new RestaurantDetailsResponse.RestaurantDetails();

        this.originalRestaurantDetails.setId(this.restaurantDetails.getId());
        this.originalRestaurantDetails.setName(this.restaurantDetails.getName());
        this.originalRestaurantDetails.setEmail(this.restaurantDetails.getEmail());
        this.originalRestaurantDetails.setPhone(this.restaurantDetails.getPhone());
        this.originalRestaurantDetails.setAddress(this.restaurantDetails.getAddress());
        this.originalRestaurantDetails.setDelivery(this.restaurantDetails.isDelivery());
        this.originalRestaurantDetails.setImageUrl(this.restaurantDetails.getImageUrl());
        this.originalRestaurantDetails.setWorkTime(this.restaurantDetails.getWorkTime());

        this.originalRestaurantDetails.setKitchens(new ArrayList<RestaurantDetailsResponse.Kitchen>());
        this.originalRestaurantDetails.getKitchens().addAll(this.restaurantDetails.getKitchens());

    }

    private void cancelUpdatingRestaurantDetails() {
        // posto smo memoizovali restoran koji smo svukli sa neta
        // samo ih ponovo postavimo i to je to
        // sve izmene ce biti ponostene
        this.updateRestaurantDetails(this.originalRestaurantDetails);
        // ponistimo imgBytes
        imgBytes = null;
    }

    private void submitUpdatingRestaurantDetails() {
        RestaurantDetailsResponse.RestaurantDetails restaurantDetails = new RestaurantDetailsResponse.RestaurantDetails();
        //restaurantDetails.setId(this.restaurantDetails.getId());
        restaurantDetails.setName(this.editName.getText().toString());
        restaurantDetails.setAddress(this.editAddress.getText().toString());
        restaurantDetails.setEmail(this.editEmail.getText().toString());
        restaurantDetails.setDelivery(this.checkBoxDelivery.isChecked());
        restaurantDetails.setPhone(this.editPhone.getText().toString());
        restaurantDetails.setWorkTime(this.editWorkTime.getText().toString());
        restaurantDetails.setKitchens(this.restaurantDetails.getKitchens());

        mPresenter.submitRestaurantDetails(restaurantDetails);

        // TODO vi3: takodje treba videti koji je najbolji nacin da se uradi
        // update fotografije
    }

    // dodajemo kuhinju na restoran
    private void addKitchenToRestaurant() {
        // dodajemo izabranu kuhinju u restoran
        this.restaurantDetails.getKitchens().add(this.typedKitchen);
        // update-ujemo samo prikaz kuhinja
        mKitchensAdapter.addItems(this.restaurantDetails.getKitchens());
        // moramo odraditi i update svih kuhinja u adapteru
        if (mKitchenArrayAdapter != null)
            mKitchenArrayAdapter.checkKitchensThatAreInRestaurantAndUpdateList(this.restaurantDetails);
        // ponistavamo unos
        this.autoKitchen.setText("");
    }

    // uklanjamo kuhinju sa restorana
    @Override
    public void removeKitchenFromRestaurantDetails(RestaurantDetailsResponse.Kitchen kitchen) {
        // uklanjamo kuhinju
        this.restaurantDetails.getKitchens().remove(kitchen);
        // update-ujemo samo prikaz kuhinja
        mKitchensAdapter.addItems(this.restaurantDetails.getKitchens());
        // moramo odraditi i update svih kuhinja u adapteru
        if(mKitchenArrayAdapter != null)
            mKitchenArrayAdapter.checkKitchensThatAreInRestaurantAndUpdateList(this.restaurantDetails);
    }

    // memoizujemo kuhinju
    private void memoizeTypedKitchen(RestaurantDetailsResponse.Kitchen kitchen) {
        this.typedKitchen = kitchen;
        // kada zapamtimo kuhinju, dozvolimo da dugme za dodavanje moze da se klikne
        this.btnAddKitchen.setClickable(true);
    }

    // uklanjamo memoizovanu kuhinju
    private void removeTypedKitchen() {
        this.typedKitchen = null;
        // ponistimo kuhinju, a samim tim i mogucnost pritiska dugmeta za dodavanje
        this.btnAddKitchen.setClickable(false);
    }


    // FIXME vi3: eventualno blokiraj unos kuhinje, ako si dodao sve kuhinje na restoran
}
