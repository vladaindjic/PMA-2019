package com.mindorks.framework.mvp.ui.notification;

import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mindorks.framework.mvp.R;
import com.mindorks.framework.mvp.data.db.model.Notification;
import com.mindorks.framework.mvp.data.network.model.NotificationResponse;
import com.mindorks.framework.mvp.data.network.model.RestaurantsResponse;
import com.mindorks.framework.mvp.ui.base.BaseViewHolder;
import com.mindorks.framework.mvp.ui.user.restaurants.utils.PromotionNotificationCallBack;
import com.mindorks.framework.mvp.ui.user.restaurants.utils.UserRestaurantsCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NotificationListAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    public static final int VIEW_TYPE_EMPTY = 0;
    public static final int VIEW_TYPE_NORMAL = 1;

    private List<Notification> mNotifications;

    private UserRestaurantsCallback mCallback;
    private PromotionNotificationCallBack mCallbackPromotion;

    public UserRestaurantsCallback getmCallback() {
        return mCallback;
    }

    public void setmCallback(UserRestaurantsCallback mCallback) {
        this.mCallback = mCallback;
    }

    public PromotionNotificationCallBack getmCallbackPromotion() {
        return mCallbackPromotion;
    }

    public void setmCallbackPromotion(PromotionNotificationCallBack mCallbackPromotion) {
        this.mCallbackPromotion = mCallbackPromotion;
    }

    public NotificationListAdapter(ArrayList<Notification> mNotifications) {
        this.mNotifications = mNotifications;
    }

    public void addItems(List<Notification> notificationList) {
        this.mNotifications.addAll(notificationList);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NotificationListAdapter.ViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_item_view, parent,
                        false));
    }

    @Override
    public int getItemCount() {
        if (mNotifications != null && mNotifications.size() > 0) {
            return mNotifications.size();
        } else {
            return 1;
        }
    }

    public class ViewHolder extends BaseViewHolder {
        @BindView(R.id.notification_text_view)
        TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        protected void clear() {
            textView.setText("");
        }

        public void onBind(final int position) {
            super.onBind(position);

            if (mNotifications == null || mNotifications.size() <= 0) {
                return;
            }

            final Notification notification = mNotifications.get(position);


            if (notification.getNotificationTitle() != null) {
                textView.setText(notification.getNotificationTitle());
            }
            System.out.println("Koliko puta sam ovde");

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mCallbackPromotion != null) {
                        mCallbackPromotion.openPromotionDetails(notification.getPrmotionId());
                    }
                }
            });
//            textView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    mNotifications.remove(notification);
//                    if (notification.getRestaurantId() != null) {
//                        if (mCallback != null) {
//                            RestaurantsResponse.Restaurant restaurant =
//                                    new RestaurantsResponse.Restaurant();
//                            restaurant.setId(notification.getRestaurantId());
//                            mCallback.openRestaurantDetailsActivity(restaurant);
//                        }
//                    }
//                    notifyDataSetChanged();
//                }
//            });
        }
    }

}