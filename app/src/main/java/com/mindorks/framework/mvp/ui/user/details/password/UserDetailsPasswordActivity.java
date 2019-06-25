package com.mindorks.framework.mvp.ui.user.details.password;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.mindorks.framework.mvp.R;
import com.mindorks.framework.mvp.data.network.model.ChangePasswordRequest;
import com.mindorks.framework.mvp.ui.base.BaseActivity;
import com.mindorks.framework.mvp.ui.login.LoginActivity;
import com.mindorks.framework.mvp.ui.user.details.update.UserDetailsUpdateActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserDetailsPasswordActivity extends BaseActivity implements UserDetailsPasswordMvpView {

    @Inject
    UserDetailsPasswordMvpPresenter<UserDetailsPasswordMvpView> mPresenter;

    @BindView(R.id.old_password_txt)
    TextView txtOldPassword;
    @BindView(R.id.old_password_edit)
    TextView editOldPassword;

    @BindView(R.id.new_password_txt)
    TextView txtNewPassword;
    @BindView(R.id.new_password_edit)
    TextView editNewPassword;

    @BindView(R.id.repeated_password)
    TextView txtRepeatedPassword;
    @BindView(R.id.repeated_password_edit)
    TextView editRepeatedPassword;

    @BindView(R.id.btn_change_password)
    Button btnChange;
    @BindView(R.id.btn_cancel_change_password)
    Button btnCancel;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, UserDetailsPasswordActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details_password);


        getActivityComponent().inject(this);

        setUnBinder(ButterKnife.bind(this));

        mPresenter.onAttach(this);

        setUp();
    }

    @Override
    protected void setUp() {
        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePassword();
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
    public void back() {
        finish();
    }

    @Override
    public void changePassword() {
        ChangePasswordRequest request = new ChangePasswordRequest();
        request.setOldPassword(editOldPassword.getText().toString());
        request.setNewPassword(editNewPassword.getText().toString());
        request.setRepetedPassword(editRepeatedPassword.getText().toString());
        mPresenter.changePassword(request);
    }

    @Override
    public void openLoginActivity() {
        Intent intent = LoginActivity.getStartIntent(UserDetailsPasswordActivity.this);
        startActivity(intent);
        finish();
    }
}
