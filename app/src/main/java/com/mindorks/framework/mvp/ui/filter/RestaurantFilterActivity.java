package com.mindorks.framework.mvp.ui.filter;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
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
import com.mindorks.framework.mvp.data.network.model.FilterRestaurantRequest;
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

    public static final int DEFAULT_DISTANCE = 30;
    private int KITCHEN_NUMBER = 0;

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

        mLayoutManager1.setOrientation(LinearLayoutManager.VERTICAL);
        mLayoutManager2.setOrientation(LinearLayoutManager.VERTICAL);
        mKitchenOptionsView.setLayoutManager(mLayoutManager2);
        mKitchenOptionsView.setItemAnimator(new DefaultItemAnimator());
        mKitchenOptionsView.setAdapter(mRestaurantFilterKitchenOptionsAdapter);

        mPresenter.onViewPrepared();


        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = UserRestaurantsActivity.getStartIntent(RestaurantFilterActivity.this);
                startActivity(intent);
                finish();
            }
        });

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveFilterState();
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

        // TODO vi3: odraditi poziv da dobavimo sadrzaj filtera iz baze
        // i onda da setujemo odgovarajuce vrednosti
        mPresenter.readUserFilter();
    }

    @Override
    public void updateUserFilter(UserFilter userFilter) {
        if (userFilter == null) {
            // default distance
            updateDistance(DEFAULT_DISTANCE);
            return;
        }
        // set distance
        updateDistance((int) userFilter.getDistance());
        // set open
        this.switchWorkTime.setChecked(userFilter.isOpen());
        this.checkedSwitchWorkTime = userFilter.isOpen();
        // set delivery
        this.switchDelivery.setChecked(userFilter.isDelivery());
        this.checkedSwitchDelivery = userFilter.isDelivery();
        // set daily menu
        this.switchDailyMenu.setChecked(userFilter.isDailyMenu());
        this.checkedSwitchDailyMenu = userFilter.isDailyMenu();
        // check if there is any kitchen option stored in db
        if (userFilter.getKitchenOptionList() == null || userFilter.getKitchenOptionList().size() <= 0) {
            return;
        }
        List<RestaurantFilterResponse.RestaurantFilter.KitchenOptions> kitchenOptionsFromServer =
                mRestaurantFilterKitchenOptionsAdapter.getmKitchenOptionList();
        // marke kitchen option pulled from server checked if it is checked in db
        for (RestaurantFilterResponse.RestaurantFilter.KitchenOptions koServer : kitchenOptionsFromServer) {
            for (KitchenOption koDb : userFilter.getKitchenOptionList()) {
                // FIXME vi3: check if the equality by name is ok
                if (koServer.getName().toUpperCase().equals(koDb.getKitchenName().toUpperCase())) {
                    koServer.setValue(koDb.getChecked());
                }
            }
        }
        List<RestaurantFilterResponse.RestaurantFilter.KitchenOptions> tmpList = new ArrayList<>();
        for (RestaurantFilterResponse.RestaurantFilter.KitchenOptions koServer :
                kitchenOptionsFromServer) {
            tmpList.add(koServer);
        }
        mRestaurantFilterKitchenOptionsAdapter.addItems(tmpList);
    }

    @Override
    public void updateDistance(final int distance) {
        this.currentDistance = distance;
        distanceProgres.setText(getString(R.string.filterDistanceString) + " " + distance + "/" + distanceBar.getMax());
        distanceBar.setProgress(distance);
        distanceBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                distanceProgres.setText(getString(R.string.filterDistanceString) + " " + progress + "/" + distanceBar.getMax());
                currentDistance = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
//                Toast.makeText(getApplicationContext(),
//                        "vrednost se pocinje menjati " + distanceBar.getScrollX() + "do" + distanceBar.getScrollY(),
//                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                distanceProgres.setText(getString(R.string.filterDistanceString) + " " + currentDistance + "/" + distanceBar.getMax());
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


        ((BasePresenter) mPresenter).getDataManager()
                .saveUserFilter(userFilter)
                .subscribeOn(((BasePresenter)mPresenter).getSchedulerProvider().io())
                .observeOn(((BasePresenter)mPresenter).getSchedulerProvider().ui())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long userFilterId) throws Exception {
                        setKitchenNumber(kos.size());
                        KitchenOption kitchenOption;
                        for (RestaurantFilterResponse.RestaurantFilter.KitchenOptions ko : kos) {
                            kitchenOption = new KitchenOption();
                            kitchenOption.setChecked(ko.getValue() == null ? false : ko.getValue());
                            kitchenOption.setKitchenName(ko.getName());
                            kitchenOption.setUserFilterId(userFilterId);
                            ((BasePresenter) mPresenter).getDataManager()
                                    .saveKitchenOption(kitchenOption)
                                    .subscribeOn(((BasePresenter) mPresenter).getSchedulerProvider().io())
                                    .observeOn(((BasePresenter) mPresenter).getSchedulerProvider().ui())
                                    .subscribe(new Consumer<Boolean>() {
                                        @Override
                                        public void accept(Boolean aBoolean) throws Exception {

                                            // tek kada se sacuvaju sve kuhinje, ima smisla otvoriti novu aktivnost
                                            if (decrementKitchenNumber() <= 0) {
                                                // sve zavrseno i otvarano aktivnost
                                                openUserRestaurantsActivity();
                                            }

                                        }
                                    }, new Consumer<Throwable>() {
                                        @Override
                                        public void accept(Throwable throwable) throws Exception {
                                            // ako se desi bilo kakva greska, sve se ponistava
                                            openUserRestaurantsActivity();
                                        }
                                    });
                        }
                        ((BasePresenter) mPresenter).getDataManager().setActiveUserFilterId(userFilterId);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        // ako se desi bilo kakva greska, sve se ponistava
                        openUserRestaurantsActivity();
                    }
                });

        // TODO vi3: odraditi proveru da li postoji trenutni user filter, pa onda na njega
        // vezivati dalje i brisati sve kitchen options-e

    }

    private void openUserRestaurantsActivity() {
        Intent intent = UserRestaurantsActivity.getStartIntent(RestaurantFilterActivity.this);
        startActivity(intent);
        finish();
    }

    private synchronized void setKitchenNumber(int number) {
        KITCHEN_NUMBER = number;
    }

    private synchronized int getKitchenNumber() {
        return KITCHEN_NUMBER;
    }

    private synchronized int decrementKitchenNumber() {
        KITCHEN_NUMBER--;
        return KITCHEN_NUMBER;
    }

    // ako se pritisne back, vracamo se na restorane
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (Build.VERSION.SDK_INT > 5
                && keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            onBackPressed();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public void onBackPressed() {
        Intent intent = UserRestaurantsActivity.getStartIntent(this);
        startActivity(intent);
        finish();
    }



}
