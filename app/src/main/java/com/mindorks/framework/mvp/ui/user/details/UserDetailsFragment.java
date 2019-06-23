package com.mindorks.framework.mvp.ui.user.details;


import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
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
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.mindorks.framework.mvp.R;
import com.mindorks.framework.mvp.data.network.model.UserDetailsResponse;
import com.mindorks.framework.mvp.di.component.ActivityComponent;
import com.mindorks.framework.mvp.ui.base.BaseFragment;
import com.mindorks.framework.mvp.ui.base.BasePresenter;
import com.mindorks.framework.mvp.ui.user.restaurants.UserRestaurantsActivity;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserDetailsFragment extends BaseFragment implements
        UserDetailsMvpView {

    public static final String TAG = "UserDetailsFragment";

    private static final int CAMERA_REQUEST_CODE = 1001;
    private static final int READ_EXTERNAL_STORAGE_REQUEST_CODE = 1002;
    private static final int READ_EXTERNAL_STORAGE_REQUEST_CODE_CAMERA_SHOT = 1007;
    private static final int WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 1003;

    private static final int REQUEST_CAMERA = 1, SELECT_FILE = 0;

    private Bitmap userImageBitmap = null;
    private Uri userImageUri = null;

    UserDetailsResponse.UserDetails oldUser;


    @Inject
    UserDetailsMvpPresenter<UserDetailsMvpView> mPresenter;

    @BindView(R.id.user_details_img)
    ImageView imageView;

    @BindView(R.id.user_details_name)
    EditText txtViewName;

    @BindView(R.id.user_details_lastname)
    EditText txtViewLastname;

    @BindView(R.id.user_details_username)
    TextView txtViewUsername;

    @BindView(R.id.user_details_email)
    EditText txtViewEmail;

    @Inject
    LinearLayoutManager mLayoutManager;

    private UserDetailsResponse.UserDetails details;
    private String mCameraFileName;


    public UserDetailsFragment() {
        // Required empty public constructor
        this.oldUser = new UserDetailsResponse.UserDetails();
    }

    public static UserDetailsFragment newInstance() {
        Bundle args = new Bundle();
        UserDetailsFragment fragment = new UserDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_details, container, false);

        ActivityComponent component = getActivityComponent();
        if (component != null) {
            component.inject(this);
            setUnBinder(ButterKnife.bind(this, view));
            mPresenter.onAttach(this);
            // mKitchensAdapter.setmCallback(this);
        }
        return view;
    }

    @Override
    protected void setUp(View view) {
        Bundle bundle = getArguments();
        Long Id = bundle.getLong("Id");

        //mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        mPresenter.onViewPrepared(Id);
    }

    @Override
    public void updateDetails(final UserDetailsResponse.UserDetails details) {
        this.details = details;
        //copy new data
        oldUser.setEmail(details.getEmail());
        oldUser.setName(details.getName());
        oldUser.setLastname(details.getLastname());
        oldUser.setUsername(details.getUsername());
        oldUser.setImageUrl(details.getImageUrl());

        // azuriranje polja koja se prikazuje
        txtViewName.setText(details.getName());
        txtViewLastname.setText(details.getLastname());
        txtViewEmail.setText(details.getEmail());
        txtViewUsername.setText(details.getUsername());

        String imgUrl = ((BasePresenter) mPresenter).getDataManager().getCurrentUserProfilePicUrl();
        System.out.println("updateDetails " + imgUrl);
        Glide.with(this)
                .load(imgUrl)
                // kako bismo izbegli kesiranje
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(imageView);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseOrTakeImage();
            }
        });
    }

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
                            "korisnik_" +
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
            case READ_EXTERNAL_STORAGE_REQUEST_CODE_CAMERA_SHOT:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Uri imgUri = Uri.fromFile(new File(mCameraFileName));
                    imageView.setImageURI(imgUri);
                    String path = getPath(imgUri);
                    if (path == null) {
                        path = mCameraFileName;
                    }
                    byte[] imgBytes = getBytesFromFile(path);
                    mPresenter.uploadImageRaw(imgBytes);
                } else {
                    Toast.makeText(getBaseActivity(), "No access to external storage permission " +
                                    "missing when taking shot",
                            Toast.LENGTH_SHORT).show();
                }

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {

            if (requestCode == REQUEST_CAMERA) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (getBaseActivity().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED) {

                        // Should we show an explanation?
                        if (shouldShowRequestPermissionRationale(
                                Manifest.permission.READ_EXTERNAL_STORAGE)) {
                            // Explain to the user why we need to read the contacts
                        }

                        requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
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
                byte[] imgBytes = getBytesFromFile(path);
                mPresenter.uploadImageRaw(imgBytes);

            } else if (requestCode == SELECT_FILE) {
                userImageUri = data.getData();
                String path = getPath(userImageUri);
                byte[] imgBytes = getBytesFromFile(path);
                if (imgBytes == null) {
                    Toast.makeText(getBaseActivity(), "Cannot read image", Toast.LENGTH_SHORT).show();
                    return;
                }
                mPresenter.uploadImageRaw(imgBytes);

            }

        }
    }

    public Uri getImageUri(Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(getBaseActivity().getContentResolver(), inImage, "Title", null);
        return android.net.Uri.parse(path);
    }

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

    public void uploadPhoto(Uri imgUri) {
        String path = getPath(imgUri);
        File file = new File(path);

        mPresenter.uploadImageBytes(file);
    }

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

    @OnClick(R.id.nav_back_btn)
    void onNavBackClick() {
        getBaseActivity().onFragmentDetached(TAG);
    }

    @Override
    public void onDestroyView() {
        mPresenter.onDetach();
        super.onDestroyView();
    }

    @Override
    public void successImageUpload() {
        Toast.makeText(getBaseActivity(), "Image has been successfully upload", Toast.LENGTH_SHORT).show();

        if (details.getImageUrl() != null) {
            ((BasePresenter) mPresenter).getDataManager().setCurrentUserProfilePicUrl(details.getImageUrl());
        } else {
            ((BasePresenter) mPresenter).getDataManager().setCurrentUserProfilePicUrl(BasePresenter.ENTITY_USER_IMAGE_URL);
        }
        String imgUrl = ((BasePresenter) mPresenter).getDataManager().getCurrentUserProfilePicUrl();
        System.out.println("successImageUpload " + imgUrl);
        // postavljamo sliku
        Glide.with(this)
                .load(imgUrl)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(imageView);

        ((UserRestaurantsActivity)getBaseActivity()).updateUserProfilePic(imgUrl);

    }

    @Override
    public void failImageUpload() {
        Toast.makeText(getBaseActivity(), "Image upload fail", Toast.LENGTH_SHORT).show();
        // vracamo sliku koja je bila prethodna
//        Glide.with(this)
//                .load(((BasePresenter) mPresenter).getDataManager().getCurrentUserProfilePicUrl())
//                .into(imageView);
    }


    @OnClick(R.id.edit_user_cancel_button)
    public void cancelUpdate(){
        this.updateDetails(oldUser);
    }
}
