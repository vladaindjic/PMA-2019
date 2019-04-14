package com.mindorks.framework.mvp.ui.notification;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mindorks.framework.mvp.R;
import com.mindorks.framework.mvp.data.network.model.NotificationResponse;
import com.mindorks.framework.mvp.di.component.ActivityComponent;
import com.mindorks.framework.mvp.ui.base.BaseFragment;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NotificationFragment extends BaseFragment implements NotificationMvpView {

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
            System.out.println("POZIVAM ON ATTACH");
            mPresenter.onAttach(this);
        }

        setUp(view);
        return view;
    }

    @Override
    protected void setUp(View view) {
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mNotificationView.setLayoutManager(mLayoutManager);
        mNotificationView.setItemAnimator(new DefaultItemAnimator());
        mNotificationView.setAdapter(mNotificationListAdapter);

        System.out.println("POZIVAM ON onVPrepared");

        mPresenter.onViewPrepared();
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
    public void updateNotifications(List<NotificationResponse.Notifications.Notification> notificationsList) {
        mNotificationListAdapter.addItems(notificationsList);
    }
}
