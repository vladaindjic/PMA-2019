package com.mindorks.framework.mvp.ui.user.preferences;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.content.Intent;

import com.mindorks.framework.mvp.R;
import com.mindorks.framework.mvp.data.prefs.AppPreferencesHelper;
import com.mindorks.framework.mvp.utils.LocaleHelper;

import java.util.Locale;

public class UserPreferencesFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {

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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        //registerChangeListener();
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getContext());
        boolean useThemeDark = sp.getBoolean("pref_dark_theme", false);
        if (useThemeDark) {
            getContext().setTheme(R.style.AppThemeDark);
        } else {
            getContext().setTheme(R.style.AppTheme);
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

        languagePref_ID = getPreferenceScreen().getSharedPreferences().getString(KEY_PREF_LANGUAGE, "");

        Locale locale = new Locale(languagePref_ID);
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
        languagePref_ID = getPreferenceScreen().getSharedPreferences().getString(KEY_PREF_LANGUAGE, "");

        Locale locale = new Locale(languagePref_ID);
        Locale.setDefault(locale);
        Configuration config = getContext().getResources().getConfiguration();
        config.locale = locale;
        getContext().getResources().updateConfiguration(config,
                getContext().getResources().getDisplayMetrics());
    }

    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
//        //Your Code
//        if (key.equals("pref_dark_theme")) {
//            getActivity().recreate();
//            //restartActivity();
//        }
//        languagePref_ID = sharedPreferences.getString(KEY_PREF_LANGUAGE, "");
//
//        switch (languagePref_ID) {
//            case "en":
//                //Locale localeEN = new Locale("en_US");
//
//                LocaleHelper.setLocale(getContext(), "en");
//                getActivity().recreate();
////                restartActivity();
//                break;
//            case "sr":
//
//                LocaleHelper.setLocale(getContext(), "sr");
//                getActivity().recreate();
////                restartActivity();
//                break;
//            case "cir":
//
//                LocaleHelper.setLocale(getContext(), "cir");
//                getActivity().recreate();
////                restartActivity();
//                break;
//            case "it":
//
//                LocaleHelper.setLocale(getContext(), "it");
//                getActivity().recreate();
////                restartActivity();
//                break;
//            case "es":
//                LocaleHelper.setLocale(getContext(), "es");
//                getActivity().recreate();
////                restartActivity();
//                break;
//
//
//        }

//        getActivity().recreate();

        // obavestavamo UserRestaurantsActivity da je doslo do promene ove opcije
        if (key.equals(AppPreferencesHelper.PREF_KEY_NOTIFICATIONS)) {
            AppPreferencesHelper.RECENTLY_CHANGED_NOTIFICATION_PREFERENCE = true;
        }

        restartActivity();
    }

}
