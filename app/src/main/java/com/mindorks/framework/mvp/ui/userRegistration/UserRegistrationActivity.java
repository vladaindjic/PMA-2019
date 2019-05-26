package com.mindorks.framework.mvp.ui.userRegistration;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.mindorks.framework.mvp.R;
import com.mindorks.framework.mvp.ui.base.BaseActivity;
import com.mindorks.framework.mvp.ui.login.LoginActivity;
import com.mindorks.framework.mvp.ui.login.LoginMvpView;
import com.mindorks.framework.mvp.ui.main.MainActivity;
import com.mindorks.framework.mvp.ui.main.MainMvpPresenter;
import com.mindorks.framework.mvp.ui.main.MainMvpView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UserRegistrationActivity extends BaseActivity implements UserRegistrationMvpView {

    @Inject
    UserRegistrationMvpPresenter<UserRegistrationMvpView> mPresenter;


    @BindView(R.id.et_email_user_registration)
    EditText mEmailEditText;

    @BindView(R.id.et_password_user_registration)
    EditText mPasswordEditText;

    @BindView(R.id.et_repeat_password_user_registration)
    EditText mRepeatPasswordEditText;


    @BindView(R.id.et_name_user_registration)
    EditText mNameEditText;

    @BindView(R.id.et_username_user_registration)
    EditText mUsernameEditText;

    @BindView(R.id.et_lastname_user_registration)
    EditText mLastNameEditText;


    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, UserRegistrationActivity.class);
        return intent;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_registration);

        getActivityComponent().inject(this);

        setUnBinder(ButterKnife.bind(this));

        mPresenter.onAttach(UserRegistrationActivity.this);

        setUp();

    }

    @Override
    protected void setUp() {

    }

    @Override
    public void openLoginActivity() {
        Intent intent = LoginActivity.getStartIntent(UserRegistrationActivity.this);
        startActivity(intent);
        //finish();
    }

    @Override
    public void finishRegistration() {
        finish();
    }

    @OnClick(R.id.btn_user_registration)
    void onRegistrationUserClick(View v) {
        mPresenter.onRegisterUserClick(mEmailEditText.getText().toString(),
                mUsernameEditText.getText().toString(), mNameEditText.getText().toString(),
                mLastNameEditText.getText().toString(), mPasswordEditText.getText().toString(),
                mRepeatPasswordEditText.getText().toString());
    }

    @OnClick(R.id.txt_login)
    void onUserLoginTextViewClick(View v) {
        mPresenter.onUserLoginTextViewClick();
    }


}
