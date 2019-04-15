package com.mindorks.framework.mvp.ui.settings;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;

import com.mindorks.framework.mvp.R;
import com.mindorks.framework.mvp.data.network.model.SettingsResponse;
import com.mindorks.framework.mvp.ui.base.BaseViewHolder;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GeneralSettingsOptionsAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    public static final int VIEW_TYPE_EMPTY = 0;
    public static final int VIEW_TYPE_NORMAL = 1;

    private List<SettingsResponse.SettingsData.SettingsGeneralOption> mOptionList;

    public GeneralSettingsOptionsAdapter(List<SettingsResponse.SettingsData.SettingsGeneralOption> mOptionList) {
        this.mOptionList = mOptionList;
    }


    public void addItems(List<SettingsResponse.SettingsData.SettingsGeneralOption> optionsList) {
        this.mOptionList.addAll(optionsList);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new GeneralSettingsOptionsAdapter.ViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.item_general_settings_option_view, parent,
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

        @BindView(R.id.general_options_switch)
        Switch optionSwitch;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        protected void clear() {
            optionSwitch.setText("");
            optionSwitch.setChecked(false);
        }

        public void onBind(int position) {
            super.onBind(position);

            if (mOptionList == null || mOptionList.size() <= 0) {
                return;
            }

            final SettingsResponse.SettingsData.SettingsGeneralOption option = mOptionList.get(position);


            if (option.getName() != null) {
                optionSwitch.setText(option.getName());
                optionSwitch.setChecked(option.getValue());
            }

        }
    }
}

