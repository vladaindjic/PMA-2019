package com.mindorks.framework.mvp.ui.manager.restaurant.promotions.details;

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
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.mindorks.framework.mvp.R;
import com.mindorks.framework.mvp.data.network.model.PromotionDetailsResponse;
import com.mindorks.framework.mvp.ui.base.BaseActivity;
import com.mindorks.framework.mvp.ui.manager.restaurant.ManagerRestaurantActivity;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ManagerRestaurantPromotionDetailsActivity extends BaseActivity implements ManagerRestaurantPromotionDetailsMvpView {


    @Inject
    ManagerRestaurantPromotionDetailsMvpPresenter<ManagerRestaurantPromotionDetailsMvpView> mPresenter;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.promotion_details_title_text_edit)
    EditText titleEditText;
    @BindView(R.id.promotion_details_subtitle_text_edit)
    EditText subtitleEditText;


    //View polja
    @BindView(R.id.promotion_details_text_edit)
    EditText detaildEditText;
    @BindView(R.id.promotion_price_text_edit)
    EditText priceEditText;
    @BindView(R.id.promotion_duration_text_edit)
    EditText durationEditText;
    @BindView(R.id.promotion_details_image_view)
    ImageView imageView;
    private PromotionDetailsResponse.Promotion orginalPromotion;
    private PromotionDetailsResponse.Promotion editedPromotion;
    private long promotionId;


    private static final int CAMERA_REQUEST_CODE = 1011;
    private static final int READ_EXTERNAL_STORAGE_REQUEST_CODE = 1012;
    private static final int WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 1013;

    private static final int REQUEST_CAMERA = 1, SELECT_FILE = 0;
    byte[] imgBytes = null;
    private Uri userImageUri = null;
    private String mCameraFileName;


    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, ManagerRestaurantPromotionDetailsActivity.class);
        return intent;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_restaurant_promotion_details);

        getActivityComponent().inject(this);

        setUnBinder(ButterKnife.bind(this));

        mPresenter.onAttach(ManagerRestaurantPromotionDetailsActivity.this);
        setUp();
    }

    @Override
    protected void setUp() {
        setSupportActionBar(mToolbar);
        promotionId = getIntent().getLongExtra("promotionId", 0L);
        //Ako je id razlicit od -1 ucitaj podatke pa prikazi.
        if (promotionId != -1) {
            mPresenter.loadData(promotionId);
        } else {
            this.editedPromotion = new PromotionDetailsResponse.Promotion();
        }
        // =========================================================== vi3: slika
        // trebalo bi i ovde postaviti, jer moze promocija od nule da se doda
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseOrTakeImage();
            }
        });
    }

    @Override
    public void updatePromotion(PromotionDetailsResponse.Promotion promotion) {

        this.editedPromotion = promotion;

        this.saveOriginalPromotion();


        if (promotion.getImageUrl() != null) {
            Glide.with(imageView.getContext())
                    .load(promotion.getImageUrl())
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .into(imageView);
        }

        if (promotion.getTitle() != null) {
            titleEditText.setText(promotion.getTitle());
        }

        if (promotion.getSubTitle() != null) {
            subtitleEditText.setText(promotion.getSubTitle());
        }

        if (promotion.getDetails() != null) {
            detaildEditText.setText(promotion.getDetails());
        }

        if (promotion.getPrice() != null) {
            priceEditText.setText(promotion.getPrice());
        }

        if (promotion.getDuration() != null) {
            durationEditText.setText(promotion.getDuration());
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
                        if (ActivityCompat.checkSelfPermission(ManagerRestaurantPromotionDetailsActivity.this,
                                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(ManagerRestaurantPromotionDetailsActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(ManagerRestaurantPromotionDetailsActivity.this,
                                    new String[]{Manifest.permission.CAMERA}, CAMERA_REQUEST_CODE);
                            return;
                        }
                    }

                    StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                    StrictMode.setVmPolicy(builder.build());
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    File outFile = new File(Environment.getExternalStorageDirectory(),
                            "promocija_" +
                                    String.valueOf(System.currentTimeMillis()) + ".jpg");
                    mCameraFileName = outFile.toString();
                    Uri imageUri = Uri.fromFile(outFile);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                    startActivityForResult(intent, REQUEST_CAMERA);

                } else if (items[i].equals("Gallery")) {
                    // ovo sam ostavio, jer mi stvarno nije jasno po kom principu ovo radi jebeno
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
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
                            "promocija_" +
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
                    Toast.makeText(this, "Cannot read image", Toast.LENGTH_SHORT).show();
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

    @OnClick(R.id.submit_promotion_edit)
    public void submitPromotionEdit() {

        this.editedPromotion.setDetails(this.detaildEditText.getText().toString());
        this.editedPromotion.setTitle(this.titleEditText.getText().toString());
        this.editedPromotion.setSubTitle(this.subtitleEditText.getText().toString());
        this.editedPromotion.setPrice(this.priceEditText.getText().toString());
        this.editedPromotion.setDuration(this.durationEditText.getText().toString());

        if (promotionId != -1) {
            mPresenter.updatePromotion(this.promotionId,this.editedPromotion);
        } else {
            //Create
            mPresenter.savePromotion(this.editedPromotion);
        }
    }


    @OnClick(R.id.cancel_promotion_edit)
    public void cancelPromotionEdit() {
        //Vrati vrednosti na stare
        if (this.orginalPromotion != null) {
            this.updatePromotion(this.orginalPromotion);
        } else {
            back();
        }
        // vi3: slika - ponistimo imgBytes
        imgBytes = null;
    }


    private void saveOriginalPromotion() {
        this.orginalPromotion = new PromotionDetailsResponse.Promotion();

        this.orginalPromotion.setId(this.editedPromotion.getId());
        this.orginalPromotion.setTitle(this.editedPromotion.getTitle());
        this.orginalPromotion.setSubTitle(this.editedPromotion.getSubTitle());
        this.orginalPromotion.setImageUrl(this.editedPromotion.getImageUrl());
        this.orginalPromotion.setDetails(this.editedPromotion.getDetails());
        this.orginalPromotion.setPrice(this.editedPromotion.getPrice());
        this.orginalPromotion.setDuration(this.editedPromotion.getDuration());

    }

    @Override
    public  void back(){
        this.finish();
    }
}
