package com.mindorks.framework.mvp.ui.filter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.mindorks.framework.mvp.R;
import com.mindorks.framework.mvp.data.db.model.KitchenOption;
import com.mindorks.framework.mvp.data.db.model.UserFilter;
import com.mindorks.framework.mvp.data.network.model.RestaurantFilterResponse;
import com.mindorks.framework.mvp.ui.base.BaseActivity;
import com.mindorks.framework.mvp.ui.base.BasePresenter;
import com.mindorks.framework.mvp.ui.user.restaurants.UserRestaurantsActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.functions.Consumer;

public class RestaurantFilterActivity extends BaseActivity implements RestaurantFilterMvpView {

    @Inject
    RestaurantFilterMvpPresenter<RestaurantFilterMvpView> mPresenter;

    @Inject
    RestaurantFilterOptionsAdapter mRestaurantFilterOptionsAdapter;

    @Inject
    RestaurantFilterKitchenOptionsAdapter mRestaurantFilterKitchenOptionsAdapter;

    @BindView(R.id.filter_kitchen_options_view)
    RecyclerView mKitchenOptionsView;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.distanceBar)
    SeekBar distanceBar;

    @BindView(R.id.work_time_active_restaurant_switch)
    Switch switchWorkTime;
    private boolean checkedSwitchWorkTime = false;

    @BindView(R.id.delivery_user_preferences_switch)
    Switch switchDelivery;
    private boolean checkedSwitchDelivery = false;


    @BindView(R.id.daily_menu_user_preferences_switch)
    Switch switchDailyMenu;
    private boolean checkedSwitchDailyMenu = false;


    @BindView(R.id.filterSubmitBtn)
    Button buttonSubmit;

    @BindView(R.id.filterCancelBtn)
    Button buttonCancel;

    @Inject
    LinearLayoutManager mLayoutManager1;

    @Inject
    LinearLayoutManager mLayoutManager2;

    @BindView(R.id.filterDistanceText)
    TextView distanceProgres;

    private int currentDistance = 0;


    private RestaurantFilterResponse.RestaurantFilter restaurantFilter;

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, RestaurantFilterActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_filter);

        getActivityComponent().inject(this);

        setUnBinder(ButterKnife.bind(this));

        mPresenter.onAttach(RestaurantFilterActivity.this);

        setUp();
    }


    @Override
    protected void setUp() {
        setSupportActionBar(mToolbar);

        System.out.println("OVDE");
        if (getSupportActionBar() != null){
            System.out.println("OVDI ?");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        System.out.println("Ovde sam");
        mLayoutManager1.setOrientation(LinearLayoutManager.VERTICAL);
//        mFilterOptionsView.setLayoutManager(mLayoutManager1);
//        mFilterOptionsView.setItemAnimator(new DefaultItemAnimator());
//        mFilterOptionsView.setAdapter(mRestaurantFilterOptionsAdapter);

        mLayoutManager2.setOrientation(LinearLayoutManager.VERTICAL);
        mKitchenOptionsView.setLayoutManager(mLayoutManager2);
        mKitchenOptionsView.setItemAnimator(new DefaultItemAnimator());
        mKitchenOptionsView.setAdapter(mRestaurantFilterKitchenOptionsAdapter);

        mPresenter.onViewPrepared();
        updateDistance(20);


        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = UserRestaurantsActivity.getStartIntent(RestaurantFilterActivity.this);
                startActivity(intent);
                //finish();
            }
        });

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveFilterState();
                Intent intent = UserRestaurantsActivity.getStartIntent(RestaurantFilterActivity.this);
                startActivity(intent);
                //finish();
            }
        });


        switchWorkTime.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                checkedSwitchWorkTime = isChecked;
            }
        });

        switchDelivery.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                checkedSwitchDelivery = isChecked;
            }
        });

        switchDailyMenu.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                checkedSwitchDailyMenu = isChecked;
            }
        });

    }

    @Override
    public void updateRestaurantFilterOptions(List<RestaurantFilterResponse.RestaurantFilter.RestaurantFilterOptions> restaurantFilterOptions) {
        System.out.println("**************---------------+++++++++++" + restaurantFilterOptions.size());
        mRestaurantFilterOptionsAdapter.addItems(restaurantFilterOptions);
    }

    @Override
    public void updateKitchenOptions(List<RestaurantFilterResponse.RestaurantFilter.KitchenOptions> kitchenOptions) {

        System.out.println("************** " + kitchenOptions.size());
        mRestaurantFilterKitchenOptionsAdapter.addItems(kitchenOptions);
    }

    @Override
    public void updateDistance(int distance) {
        this.currentDistance = distance;
        distanceProgres.setText("Distance "+ distance +"/"+ distanceBar.getMax());
        distanceBar.setProgress(distance);
        distanceBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                distanceProgres.setText("Distance "+ progress +"/"+ distanceBar.getMax());
                currentDistance = progress;
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
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void saveFilterState() {
        System.out.println("************ Delivery: " + checkedSwitchDelivery + " work: "
                + checkedSwitchWorkTime + " daily menu: "
                + checkedSwitchDailyMenu + " distance: " + currentDistance);

        final List<RestaurantFilterResponse.RestaurantFilter.KitchenOptions> kos =
                mRestaurantFilterKitchenOptionsAdapter.getmKitchenOptionList();


        UserFilter userFilter = new UserFilter();
        userFilter.setDailyMenu(checkedSwitchDailyMenu);
        userFilter.setDelivery(checkedSwitchDelivery);
        userFilter.setOpen(checkedSwitchWorkTime);
        userFilter.setDistance(currentDistance);



        ((BasePresenter)mPresenter).getDataManager().saveUserFilter(userFilter).subscribe(new Consumer<Long>() {
            @Override
            public void accept(Long userFilterId) throws Exception {
                KitchenOption kitchenOption;
                for (RestaurantFilterResponse.RestaurantFilter.KitchenOptions ko: kos) {
                    kitchenOption = new KitchenOption();
                    kitchenOption.setChecked(ko.getValue() == null ? false: ko.getValue());
                    kitchenOption.setKitchenName(ko.getName());
                    kitchenOption.setUserFilterId(userFilterId);
                    ((BasePresenter)mPresenter).getDataManager().saveKitchenOption(kitchenOption).subscribe(new Consumer<Boolean>() {
                        @Override
                        public void accept(Boolean aBoolean) throws Exception {
                            System.out.println("Nasisi se karine, mamu ti jebem vegansku.");
                        }
                    });
                }
                ((BasePresenter)mPresenter).getDataManager().setActiveUserFilterId(userFilterId);
            }
        });

        // TODO vi3: odraditi proveru da li postoji trenutni user filter, pa onda na njega
        // vezivati dalje i brisati sve kitchen options-e

    }

}
