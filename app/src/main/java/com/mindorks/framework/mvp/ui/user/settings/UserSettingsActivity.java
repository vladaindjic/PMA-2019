package com.mindorks.framework.mvp.ui.user.settings;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;

import com.mindorks.framework.mvp.R;
import com.mindorks.framework.mvp.ui.base.BaseActivity;
import com.mindorks.framework.mvp.ui.user.preferences.UserPreferencesFragment;
import com.mindorks.framework.mvp.ui.user.restaurants.UserRestaurantsActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserSettingsActivity extends BaseActivity implements UserSettingsMvpView {

    @Inject
    UserSettingsMvpPresenter<UserSettingsMvpView> mPresenter;

//    @BindView(R.id.toolbar)
//    Toolbar mToolbar;

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, UserSettingsActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_settings);
        getActivityComponent().inject(this);

        setUnBinder(ButterKnife.bind(this));

        mPresenter.onAttach(UserSettingsActivity.this);

        getSupportFragmentManager()
                .beginTransaction()
                .disallowAddToBackStack()
                .setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                .add(R.id.user_preferences_container_settings_activity, UserPreferencesFragment.newInstance(),
                        UserPreferencesFragment.TAG)
                .commit();
        setUp();
    }

    @Override
    protected void setUp() {
        //setSupportActionBar(mToolbar);
    }

    @Override
    protected void onDestroy() {
        mPresenter.onDetach();
        super.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (Build.VERSION.SDK_INT > 5
                && keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            onBackPressed();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public void onBackPressed() {
        Intent intent = UserRestaurantsActivity.getStartIntent(this);
        startActivity(intent);
    }
}
