package com.mindorks.framework.mvp.data.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SettingsResponse {


    @Expose
    @SerializedName("http_status_code")
    private int httpStatusCode;

    @Expose
    @SerializedName("message")
    private String message;

    @Expose
    @SerializedName("data")
    private SettingsResponse.SettingsData data;

    public SettingsResponse() {
    }

    public SettingsResponse(int httpStatusCode, String message, SettingsData data) {
        this.httpStatusCode = httpStatusCode;
        this.message = message;
        this.data = data;
    }

    public int getHttpStatusCode() {
        return httpStatusCode;
    }

    public void setHttpStatusCode(int httpStatusCode) {
        this.httpStatusCode = httpStatusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public SettingsData getData() {
        return data;
    }

    public void setData(SettingsData data) {
        this.data = data;
    }

    public static class SettingsData {

        @Expose
        @SerializedName("options")
        private List<SettingsResponse.SettingsData.SettingsGeneralOption> settingsGeneralOptionsList;

        @Expose
        @SerializedName("language_options")
        private List<SettingsResponse.SettingsData.LanguageOption> languageOptionList;

        public SettingsData(List<SettingsGeneralOption> settingsGeneralOptionsList, List<LanguageOption> languageOptionList) {
            this.settingsGeneralOptionsList = settingsGeneralOptionsList;
            this.languageOptionList = languageOptionList;
        }

        public SettingsData() {
        }

        public List<SettingsGeneralOption> getSettingsGeneralOptionsList() {
            return settingsGeneralOptionsList;
        }

        public void setSettingsGeneralOptionsList(List<SettingsGeneralOption> settingsGeneralOptionsList) {
            this.settingsGeneralOptionsList = settingsGeneralOptionsList;
        }

        public List<LanguageOption> getLanguageOptionList() {
            return languageOptionList;
        }

        public void setLanguageOptionList(List<LanguageOption> languageOptionList) {
            this.languageOptionList = languageOptionList;
        }

        public static class SettingsGeneralOption {
            @Expose
            @SerializedName("id")
            private Long id;
            @Expose
            @SerializedName("name")
            private String name;
            @Expose
            @SerializedName("value")
            private Boolean value;

            public SettingsGeneralOption(Long id, String name, Boolean value) {
                this.id = id;
                this.name = name;
                this.value = value;
            }

            public SettingsGeneralOption(String name) {
                this.name = name;
            }

            public SettingsGeneralOption(String name, Boolean value) {
                this.name = name;
                this.value = value;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public Boolean getValue() {
                return value;
            }

            public void setValue(Boolean value) {
                this.value = value;
            }

            public Long getId() {
                return id;
            }

            public void setId(Long id) {
                this.id = id;
            }
        }

        public static class LanguageOption {
            @Expose
            @SerializedName("id")
            private Long id;

            @Expose
            @SerializedName("name")
            private String name;

            @Expose
            @SerializedName("value")
            private Boolean value;

            public LanguageOption(Long id, String name, Boolean value) {
                this.id = id;
                this.name = name;
                this.value = value;
            }

            public LanguageOption() {
            }

            public LanguageOption(String name, Boolean value) {
                this.name = name;
                this.value = value;
            }

            public Long getId() {
                return id;
            }

            public void setId(Long id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public Boolean getValue() {
                return value;
            }

            public void setValue(Boolean value) {
                this.value = value;
            }
        }
    }
}
