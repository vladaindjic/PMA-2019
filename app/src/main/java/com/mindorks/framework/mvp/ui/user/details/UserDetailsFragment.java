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
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.mindorks.framework.mvp.R;
import com.mindorks.framework.mvp.data.network.model.UserDetailsResponse;
import com.mindorks.framework.mvp.di.component.ActivityComponent;
import com.mindorks.framework.mvp.ui.base.BaseFragment;

import java.io.ByteArrayOutputStream;
import java.io.File;
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

    private static final int REQUEST_CAMERA = 1, SELECT_FILE = 0;

    private Bitmap userImageBitmap = null;
    private Uri userImageUri = null;


    @Inject
    UserDetailsMvpPresenter<UserDetailsMvpView> mPresenter;

    @BindView(R.id.user_details_img)
    ImageView imageView;

    @BindView(R.id.user_details_name)
    TextView txtViewName;

    @BindView(R.id.user_details_lastname)
    TextView txtViewLastname;

    @BindView(R.id.user_details_username)
    TextView txtViewUsername;

    @BindView(R.id.user_details_email)
    TextView txtViewEmail;

    @Inject
    LinearLayoutManager mLayoutManager;

    private UserDetailsResponse.UserDetails details;

    public UserDetailsFragment() {
        // Required empty public constructor
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

        // azuriranje polja koja se prikazuje
        txtViewName.setText(details.getName());
        txtViewLastname.setText(details.getLastname());
        txtViewEmail.setText(details.getEmail());
        txtViewUsername.setText(details.getUsername());


        if (details.getImageUrl() != null) {
            Glide.with(this)
                    .load(details.getImageUrl())
                    .into(imageView);
        }

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
                    if (ActivityCompat.checkSelfPermission(getBaseActivity(),
                            Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getBaseActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(getBaseActivity(),
                                new String[]{Manifest.permission.CAMERA}, CAMERA_REQUEST_CODE);
                        return;
                    }

                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, REQUEST_CAMERA);

                } else if (items[i].equals("Gallery")) {
                    if (ActivityCompat.checkSelfPermission(getBaseActivity(),
                            Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getBaseActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(getBaseActivity(),
                                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, READ_EXTERNAL_STORAGE_REQUEST_CODE);
                        return;
                    }
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
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {

            if (requestCode == REQUEST_CAMERA) {
                Bundle bundle = data.getExtras();
                Bitmap bmp = (Bitmap)bundle.get("data");
                userImageUri = getImageUri(bmp);
                imageView.setImageURI(userImageUri);
//                imageView.setImageBitmap(bmp);

                uploadPhoto(userImageUri);
            } else if (requestCode == SELECT_FILE) {
                userImageUri = data.getData();
                imageView.setImageURI(userImageUri);
                userImageBitmap = null;
                uploadPhoto(userImageUri);
            }

        }
    }

    public Uri getImageUri(Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(getBaseActivity().getContentResolver(), inImage, "Title", null);
        return android.net.Uri.parse(path);
    }

    public void uploadPhoto(Uri imgUri) {
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
        String path = getPath(imgUri);
        File file = new File(path);

        System.out.println("ALOOOOOOOOOOOOOOOOOOOOOOOOOOO " + file.exists() + " " + file.getAbsolutePath());
        mPresenter.uploadImageBytes(file);
    }

    //method to get the file path from uri
    public String getPath(Uri uri) {
        Cursor cursor = getBaseActivity().getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
        cursor.close();

        cursor = getBaseActivity().getContentResolver().query(
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        cursor.close();

        return path;
    }

    public static byte[] convertBitmapToByteArray(Bitmap bmp) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
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
        // posto zarko ne vraca novi link
        imageView.setImageURI(userImageUri);
    }

    @Override
    public void failImageUpload() {
        // vracamo sliku koja je bila prethodna
        if (details.getImageUrl() != null) {
            Glide.with(this)
                    .load(details.getImageUrl())
                    .into(imageView);
        }
    }
}
