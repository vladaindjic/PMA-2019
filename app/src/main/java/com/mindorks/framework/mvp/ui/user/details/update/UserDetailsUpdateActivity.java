package com.mindorks.framework.mvp.ui.user.details.update;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.mindorks.framework.mvp.R;
import com.mindorks.framework.mvp.data.network.model.UpdateUserDetailsRequest;
import com.mindorks.framework.mvp.data.network.model.UserDetailsResponse;
import com.mindorks.framework.mvp.ui.base.BaseActivity;
import com.mindorks.framework.mvp.ui.base.BasePresenter;
import com.mindorks.framework.mvp.ui.login.LoginActivity;
import com.mindorks.framework.mvp.ui.splash.SplashActivity;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserDetailsUpdateActivity extends BaseActivity implements UserDetailsUpdateMvpView {

    @Inject
    UserDetailsUpdateMvpPresenter<UserDetailsUpdateMvpView> mPresenter;

    @BindView(R.id.user_details_img_update)
    ImageView imageView;

    @BindView(R.id.user_details_username_update)
    TextView txtViewUsername;
    
    @BindView(R.id.user_details_name_update)
    EditText editViewName;

    @BindView(R.id.user_details_lastname_update)
    EditText editViewLastname;

    
    @BindView(R.id.user_details_email_update)
    EditText editViewEmail;

    @BindView(R.id.edit_user_save_button_update)
    Button btnSave;

    @BindView(R.id.edit_user_cancel_button_update)
    Button btnCancel;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @Inject
    LinearLayoutManager mLayoutManager;

    private UserDetailsResponse.UserDetails details;

    private static final int CAMERA_REQUEST_CODE = 1071;
    private static final int READ_EXTERNAL_STORAGE_REQUEST_CODE = 1072;
    private static final int WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 1073;
    private static final int READ_EXTERNAL_STORAGE_REQUEST_CODE_CAMERA_SHOT = 1077;


    private static final int REQUEST_CAMERA = 1, SELECT_FILE = 0;
    byte[] imgBytes = null;
    private Uri userImageUri = null;
    private String mCameraFileName;


    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, UserDetailsUpdateActivity.class);
        return intent;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details_update);

        getActivityComponent().inject(this);

        setUnBinder(ButterKnife.bind(this));

        mPresenter.onAttach(this);

        setUp();
    }

    @Override
    protected void setUp() {
        setSupportActionBar(mToolbar);


        Intent intent = getIntent();
        Long userId = intent.getLongExtra("userId", 0L);
        if (userId == 0L) {
            Toast.makeText(this, "Ne valja ti ovo, druze (:", Toast.LENGTH_SHORT).show();
        }

        mPresenter.onViewPrepared(userId);

        // =========================================================== vi3: slika
        // trebalo bi i ovde postaviti, jer moze korisnik od nule da se doda
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseOrTakeImage();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUserDetails();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back();
            }
        });

    }

    @Override
    public void updateDetails(UserDetailsResponse.UserDetails details) {
        this.details = details;

        // azuriranje polja koja se prikazuje
        editViewName.setText(details.getName());
        editViewLastname.setText(details.getLastname());
        editViewEmail.setText(details.getEmail());
        txtViewUsername.setText(details.getUsername());

        String imgUrl = ((BasePresenter) mPresenter).getDataManager().getCurrentUserProfilePicUrl();
        System.out.println("updateDetails " + imgUrl);
        Glide.with(this)
                .load(imgUrl)
                // kako bismo izbegli kesiranje
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(imageView);

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
                        if (ActivityCompat.checkSelfPermission(UserDetailsUpdateActivity.this,
                                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(UserDetailsUpdateActivity.this,
                                    new String[]{Manifest.permission.CAMERA}, CAMERA_REQUEST_CODE);
                            return;
                        }
                    }

                    StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                    StrictMode.setVmPolicy(builder.build());
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    File outFile = new File(Environment.getExternalStorageDirectory(),
                            "korisnik_" +
                                    String.valueOf(System.currentTimeMillis()) + ".jpg");
                    mCameraFileName = outFile.toString();
                    Uri imageUri = Uri.fromFile(outFile);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                    startActivityForResult(intent, REQUEST_CAMERA);

                } else if (items[i].equals("Gallery")) {
                    // ovo sam ostavio, jer mi stvarno nije jasno po kom principu ovo radi jebeno
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (ActivityCompat.checkSelfPermission(UserDetailsUpdateActivity.this,
                                Manifest.permission.READ_EXTERNAL_STORAGE)
                                != PackageManager.PERMISSION_GRANTED) {

                            // Should we show an explanation?
                            if (shouldShowRequestPermissionRationale(
                                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
                                // Explain to the user why we need to read the contacts
                            }

                            ActivityCompat.requestPermissions(UserDetailsUpdateActivity.this,
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
                            "korisnik_" +
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
                    if (ActivityCompat.checkSelfPermission(UserDetailsUpdateActivity.this,
                            Manifest.permission.READ_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED) {

                        // Should we show an explanation?
                        if (shouldShowRequestPermissionRationale(
                                Manifest.permission.READ_EXTERNAL_STORAGE)) {
                            // Explain to the user why we need to read the contacts
                        }

                        ActivityCompat.requestPermissions(UserDetailsUpdateActivity.this,
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
        return getStreamByteFromImage(file);
//        int size = (int) file.length();
//        byte[] bytes = new byte[size];
//        try {
//            BufferedInputStream buf = new BufferedInputStream(new FileInputStream(file));
//            buf.read(bytes, 0, bytes.length);
//            buf.close();
//            return bytes;
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return null;
    }

    public byte[] getImgBytes() {
        return imgBytes;
    }

    //this is the byte stream that I upload.
    public static byte[] getStreamByteFromImage(final File imageFile) {

        Bitmap photoBitmap = BitmapFactory.decodeFile(imageFile.getPath());
        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        int imageRotation = getImageRotation(imageFile);

        if (imageRotation != 0)
            photoBitmap = getBitmapRotatedByDegree(photoBitmap, imageRotation);

        photoBitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);

        return stream.toByteArray();
    }



    private static int getImageRotation(final File imageFile) {

        ExifInterface exif = null;
        int exifRotation = 0;

        try {
            exif = new ExifInterface(imageFile.getPath());
            exifRotation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (exif == null)
            return 0;
        else
            return exifToDegrees(exifRotation);
    }

    private static int exifToDegrees(int rotation) {
        if (rotation == ExifInterface.ORIENTATION_ROTATE_90)
            return 90;
        else if (rotation == ExifInterface.ORIENTATION_ROTATE_180)
            return 180;
        else if (rotation == ExifInterface.ORIENTATION_ROTATE_270)
            return 270;

        return 0;
    }

    private static Bitmap getBitmapRotatedByDegree(Bitmap bitmap, int rotationDegree) {
        Matrix matrix = new Matrix();
        matrix.preRotate(rotationDegree);

        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    // =========================================================== vi3: slika


    @Override
    public void back() {
        finish();
    }
    
    private void updateUserDetails() {
        UpdateUserDetailsRequest request = new UpdateUserDetailsRequest();
        request.setEmail(editViewEmail.getText().toString());
        request.setFirstname(editViewName.getText().toString());
        request.setLastname(editViewLastname.getText().toString());
        if (imgBytes == null || imgBytes.length <= 0) {
            // samo update profila
            mPresenter.updateUserDetails(request);
        } else {
            // update slike i profila
            mPresenter.submitUserImage(imgBytes, request);
        }
    }

    @Override
    public void openLoginActivity() {
        Intent intent = LoginActivity.getStartIntent(UserDetailsUpdateActivity.this);
        startActivity(intent);
        finish();
    }
}
