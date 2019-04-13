package com.mindorks.framework.mvp.ui.filter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;

import com.mindorks.framework.mvp.R;
import com.mindorks.framework.mvp.data.network.model.RestaurantFilterResponse;
import com.mindorks.framework.mvp.ui.base.BaseActivity;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

public class RestaurantFilterActivity extends BaseActivity implements RestaurantFilterMvpView {

    @Inject
    RestaurantFilterMvpPresenter<RestaurantFilterMvpView> mPresenter;

    @Inject
    RestaurantFilterOptionsAdapter mRestaurantFilterOptionsAdapter;

    @Inject
    RestaurantFilterKitchenOptionsAdapter mRestaurantFilterKitchenOptionsAdapter;

    @BindView(R.id.filter_restaurant_options_view)
    RecyclerView mFilterOptionsView;

    @BindView(R.id.filter_kitchen_options_view)
    RecyclerView mKitchenOptionsView;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.distanceBar)
    SeekBar distanceBar;

    @BindView(R.id.filterSubmitBtn)
    Button buttonSubmit;

    @BindView(R.id.filterCancelBtn)
    Button buttonCancel;

    @Inject
    LinearLayoutManager mLayoutManager;

    private RestaurantFilterResponse.RestaurantFilter restaurantFilter;

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, RestaurantFilterActivity.class);
        return intent;
    }

    @Override
    protected void setUp() {
        setSupportActionBar(mToolbar);

        System.out.println("Ovde sam");
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mFilterOptionsView.setLayoutManager(mLayoutManager);
        mFilterOptionsView.setItemAnimator(new DefaultItemAnimator());
        mFilterOptionsView.setAdapter(mRestaurantFilterOptionsAdapter);

        mKitchenOptionsView.setLayoutManager(mLayoutManager);
        mKitchenOptionsView.setItemAnimator(new DefaultItemAnimator());
        mKitchenOptionsView.setAdapter(mRestaurantFilterKitchenOptionsAdapter);

        mPresenter.onViewPrepared();

    }

    @Override
    public void updateRestaurantFilterOptions(List<RestaurantFilterResponse.RestaurantFilter.RestaurantFilterOptions> restaurantFilterOptions) {

    }

    @Override
    public void updateKitchenOptions(List<RestaurantFilterResponse.RestaurantFilter.KitchenOptions> kitchenOptions) {

    }

    @Override
    public void updateDistance(int distance) {
        distanceBar.scrollTo(0, distance);
        distanceBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Toast.makeText(getApplicationContext(),
                        "STAGOD " + distanceBar.getScrollX() + "do" + distanceBar.getScrollY(),
                        Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                Toast.makeText(getApplicationContext(),
                        "vrednost se pocinje menjati " + distanceBar.getScrollX() + "do" + distanceBar.getScrollY(),
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Toast.makeText(getApplicationContext(),
                        "vrednost se promenila na " + distanceBar.getScrollX() + "do" + distanceBar.getScrollY(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
