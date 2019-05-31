package com.mindorks.framework.mvp.ui.user.preferences;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.content.Intent;
import android.widget.Toast;
import com.mindorks.framework.mvp.R;
import com.mindorks.framework.mvp.utils.LocaleHelper;

import java.util.Locale;

public class UserPreferencesFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener{

    public static final String TAG = "UserPreferencesFragment";


    public static final String PREF_USER_TYPE = "pref_user_type";

    private SharedPreferences.OnSharedPreferenceChangeListener preferenceChangeListener;
    public static final String KEY_PREF_LANGUAGE = "pref_language";
    public String languagePref_ID;
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
        //registerChangeListener();
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getContext());
        boolean useThemeDark = sp.getBoolean("pref_dark_theme", false);
        if (useThemeDark) {
            getContext().setTheme(R.style.AppThemeDark);
        } else {
            getContext().setTheme(R.style.AppTheme);
        }

        languagePref_ID = sp.getString(KEY_PREF_LANGUAGE, "");

        switch (languagePref_ID) {
            case "en":
                //Locale localeEN = new Locale("en_US");
                System.out.println("evo me pref en");

                LocaleHelper.setLocale(getActivity(), "en");
                //setLocaleOnCreate(localeEN);
                break;
            case "sr":
                System.out.println("evo me activity sr");

                LocaleHelper.setLocale(getActivity(), "sr");
                //setLocaleOnCreate(localeHU);
                break;

        }
        super.onCreate(savedInstanceState);

    }

    private void restartActivity() {
        Intent intent = getActivity().getIntent();
        getActivity().finish();
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();

        getPreferenceScreen().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this);

        Locale locale = new Locale(LocaleHelper.getLanguage(getContext()));
        Locale.setDefault(locale);
        Configuration config = getContext().getResources().getConfiguration();
        config.locale = locale;
        getContext().getResources().updateConfiguration(config,
                getContext().getResources().getDisplayMetrics());
    }

    @Override
    public void onPause() {
        super.onPause();

        getPreferenceScreen().getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(this);
    }

    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,String key)
    {
        //Your Code
        //if (key.equals("pref_dark_theme")) {

        restartActivity();
        //}
    }

}
