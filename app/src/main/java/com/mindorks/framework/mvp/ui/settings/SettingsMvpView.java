package com.mindorks.framework.mvp.ui.settings;

import com.mindorks.framework.mvp.data.network.model.SettingsResponse;
import com.mindorks.framework.mvp.ui.base.MvpView;

import java.util.List;

public interface SettingsMvpView  extends MvpView {

    void updateGeneralSettingsOptions(List<SettingsResponse.SettingsData.SettingsGeneralOption> settingsResponse);
    void updateLanguageSettingsOptions(List<SettingsResponse.SettingsData.LanguageOption> settingsResponse);

}
