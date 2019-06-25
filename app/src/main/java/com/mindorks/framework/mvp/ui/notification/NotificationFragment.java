package com.mindorks.framework.mvp.ui.notification;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mindorks.framework.mvp.R;
import com.mindorks.framework.mvp.data.db.model.Notification;
import com.mindorks.framework.mvp.data.network.model.NotificationResponse;
import com.mindorks.framework.mvp.data.network.model.RestaurantsResponse;
import com.mindorks.framework.mvp.di.component.ActivityComponent;
import com.mindorks.framework.mvp.ui.base.BaseFragment;
import com.mindorks.framework.mvp.ui.user.restaurant.promotions.details.PromotionDetailsActivity;
import com.mindorks.framework.mvp.ui.user.restaurants.UserRestaurantsActivity;
import com.mindorks.framework.mvp.ui.user.restaurants.utils.PromotionNotificationCallBack;
import com.mindorks.framework.mvp.ui.user.restaurants.utils.UserRestaurantsCallback;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NotificationFragment extends BaseFragment implements NotificationMvpView, UserRestaurantsCallback, PromotionNotificationCallBack {

    public static final String TAG = "NotificationFragment";

    @Inject
    NotificationMvpPresenter<NotificationMvpView> mPresenter;

    @Inject
    NotificationListAdapter mNotificationListAdapter;

    @BindView(R.id.item_notifications_view)
    RecyclerView mNotificationView;

    @Inject
    LinearLayoutManager mLayoutManager;

    public static NotificationFragment newInstance() {
        Bundle args = new Bundle();
        NotificationFragment fragment = new NotificationFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notification, container, false);

        ActivityComponent component = getActivityComponent();
        if (component != null) {
            component.inject(this);
            setUnBinder(ButterKnife.bind(this, view));
            mPresenter.onAttach(this);
            mNotificationListAdapter.setmCallback(this);
            mNotificationListAdapter.setmCallbackPromotion(this);
        }

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        // vi3 prebaceno onResume
        mPresenter.onViewPrepared();
    }

    @Override
    protected void setUp(View view) {
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mNotificationView.setLayoutManager(mLayoutManager);
        mNotificationView.setItemAnimator(new DefaultItemAnimator());
        mNotificationView.setAdapter(mNotificationListAdapter);

        // vi3 prebaceno onResume
        // mPresenter.onViewPrepared();
    }

    @OnClick(R.id.nav_back_btn)
    void onNavBackClick() {
        getBaseActivity().onFragmentDetached(TAG);
    }

    @Override
    public void onDestroyView() {
        mPresenter.onDetach();
        super.onDestroyView();
    }

    @Override
    public void updateNotifications(List<Notification>notificationsList) {
        mNotificationListAdapter.addItems(notificationsList);
    }

    @Override
    public void openRestaurantDetailsActivity(RestaurantsResponse.Restaurant restaurant) {
        UserRestaurantsActivity userRestaurantsActivity = (UserRestaurantsActivity) getActivity();
        if (userRestaurantsActivity != null) {
            userRestaurantsActivity.openRestaurantDetailsActivity(restaurant);
        } else {
            Toast.makeText(getContext(), "Ne valja ti ovo, druze (:", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onsEmptyViewRetryButtonClick() {

    }

    @Override
    public void openPromotionDetails(String prmotionId) {
        Intent intent = PromotionDetailsActivity.getStartIntent(getActivity());
        intent.putExtra("promotionId",Long.parseLong(prmotionId));
        startActivity(intent);
    }
}
