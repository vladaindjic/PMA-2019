package com.mindorks.framework.mvp.ui.settings;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;

import com.mindorks.framework.mvp.R;
import com.mindorks.framework.mvp.data.network.model.SettingsResponse;
import com.mindorks.framework.mvp.ui.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LanguageSettingsOptionsAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    public static final int VIEW_TYPE_EMPTY = 0;
    public static final int VIEW_TYPE_NORMAL = 1;
    private final Context context;

    private List<SettingsResponse.SettingsData.LanguageOption> mOptionList;

    public LanguageSettingsOptionsAdapter(Context context , List<SettingsResponse.SettingsData.LanguageOption> mOptionList) {
        this.mOptionList = mOptionList;
        this.context = context;
    }

    public void addItems(List<SettingsResponse.SettingsData.LanguageOption> optionsList) {
        this.mOptionList.addAll(optionsList);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new LanguageSettingsOptionsAdapter.ViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.item_language_settings_option_view, parent,
                        false));
    }


    @Override
    public int getItemViewType(int position) {
        if (mOptionList != null && mOptionList.size() > 0) {
            return VIEW_TYPE_NORMAL;
        } else {
            return VIEW_TYPE_EMPTY;
        }
    }

    @Override
    public int getItemCount() {
        if (mOptionList != null && mOptionList.size() > 0) {
            return mOptionList.size();
        } else {
            return 1;
        }
    }

    public class ViewHolder extends BaseViewHolder {

        @BindView(R.id.language_radio_button_group)
        RadioGroup languageRadioGroup;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        protected void clear() {
//            languageRadioGroup.removeAllViews();
        }

        public void onBind(int position) {
            super.onBind(position);

            if (mOptionList == null || mOptionList.size() <= 0) {
                return;
            }

            final SettingsResponse.SettingsData.LanguageOption option = mOptionList.get(position);


            if (option.getName() != null) {
                RadioButton radioButton = new RadioButton(context);
                radioButton.setId(View.generateViewId());
                radioButton.setText(option.getName());
                languageRadioGroup.addView(radioButton,position);
//                notifyDataSetChanged();
            }

        }
    }
}


