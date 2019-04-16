package com.mindorks.framework.mvp.ui.manager.restaurant.utils;

import android.view.View;
import android.widget.TextView;

import com.mindorks.framework.mvp.R;
import com.mindorks.framework.mvp.ui.base.BaseViewHolder;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ManagerEmptyViewHolderTextViewOnly extends BaseViewHolder {

    @BindView(R.id.our_empty_view_item_text_view_only_message)
    TextView messageTextView;

    public ManagerEmptyViewHolderTextViewOnly(View itemView, String txtMessage) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        messageTextView.setText(txtMessage);
    }

    @Override
    protected void clear() {

    }

}
