package com.mindorks.framework.mvp.ui.user.preferences;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;

import com.mindorks.framework.mvp.R;

public class UserPreferencesFragment extends PreferenceFragmentCompat {

    public static final String TAG = "UserPreferencesFragment";


    public static final String PREF_USER_TYPE = "pref_user_type";

    private SharedPreferences.OnSharedPreferenceChangeListener preferenceChangeListener;

    public static UserPreferencesFragment newInstance() {
        Bundle args = new Bundle();
        UserPreferencesFragment fragment = new UserPreferencesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.user_preferences);

    }
}
