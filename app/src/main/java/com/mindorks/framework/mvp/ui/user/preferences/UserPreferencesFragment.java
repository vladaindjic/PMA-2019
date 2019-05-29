package com.mindorks.framework.mvp.ui.user.preferences;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.content.Intent;
import android.widget.Toast;
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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getContext());
        boolean useThemeDark = sp.getBoolean("pref_dark_theme", false);
        if (useThemeDark) {
            getContext().setTheme(R.style.AppThemeDark);
        } else {
            getContext().setTheme(R.style.AppTheme);
        }
        super.onCreate(savedInstanceState);
        registerChangeListener();
    }

    private void restartActivity() {
        Intent intent = getActivity().getIntent();
        getActivity().finish();
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void registerChangeListener () {
        final SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getContext());

        sp.registerOnSharedPreferenceChangeListener(new SharedPreferences.OnSharedPreferenceChangeListener() {
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                Toast toast=Toast.makeText(getContext(),"Hello Javatpoint",Toast.LENGTH_SHORT);
                toast.setMargin(50,50);
                toast.show();
                restartActivity();
            }
        });
    }
}
