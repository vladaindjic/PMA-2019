package com.mindorks.framework.mvp.ui.manager.restaurant.cook;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mindorks.framework.mvp.R;
import com.mindorks.framework.mvp.data.network.model.MenuResponse;
import com.mindorks.framework.mvp.data.network.model.RestaurantCookResponse;
import com.mindorks.framework.mvp.data.network.model.RestaurantsResponse;
import com.mindorks.framework.mvp.di.component.ActivityComponent;
import com.mindorks.framework.mvp.ui.base.BaseFragment;
import com.mindorks.framework.mvp.ui.manager.restaurant.ManagerRestaurantActivity;
import com.mindorks.framework.mvp.ui.user.restaurants.utils.UserRestaurantsCallback;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class ManagerRestaurantCookFragment extends BaseFragment implements
        ManagerRestaurantCookMvpView, ManagerCookItemDeleteCallback {

    @Inject
    ManagerRestaurantCookMvpPresenter<ManagerRestaurantCookMvpView> mPresenter;

    public static final String TAG = "ManagerRestaurantCookFragment";

    @Inject
    ManagerRestaurantCookItemListAdapter mManagerRestaurantCookItemListAdapter;

    @BindView(R.id.item_cook_item_list_view)
    RecyclerView mManagerRestaurantCookView;

    @Inject
    LinearLayoutManager mLayoutManager;

    @BindView(R.id.cook_item_add_btn)
    FloatingActionButton addDishBtn;

    @BindView(R.id.cook_dish_search_edit)
    EditText editSearch;

    List<RestaurantCookResponse.RestaurantCook.RestaurantCookItem> dishList;

    public ManagerRestaurantCookFragment() {
        // Required empty public constructor
    }

    public static ManagerRestaurantCookFragment newInstance() {
        Bundle args = new Bundle();
        ManagerRestaurantCookFragment fragment = new ManagerRestaurantCookFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // svetla tema je podrzaumevana za managera
        getContext().setTheme(R.style.AppTheme);
        View view = inflater.inflate(R.layout.fragment_manager_restaurant_cook, container, false);

        ActivityComponent component = getActivityComponent();
        if (component != null) {
            component.inject(this);
            setUnBinder(ButterKnife.bind(this, view));
            mPresenter.onAttach(this);
            mManagerRestaurantCookItemListAdapter.setmCallback((ManagerRestaurantActivity)getBaseActivity());
            mManagerRestaurantCookItemListAdapter.setmDeleteCallback(this);
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
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser) {
            if (getBaseActivity() != null) {
                // vi3 prebaceno onResume
                mPresenter.onViewPrepared();
            }
        }
    }

    @Override
    protected void setUp(View view) {
        // vi3 prebaceno onResume
        mPresenter.onViewPrepared();
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mManagerRestaurantCookView.setLayoutManager(mLayoutManager);
        mManagerRestaurantCookView.setItemAnimator(new DefaultItemAnimator());
        mManagerRestaurantCookView.setAdapter(mManagerRestaurantCookItemListAdapter);

        System.out.println("POZIVAM ON onVPrepared");

        addDishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openEmptyDishDetailsActivity();
            }
        });

        editSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ubaciti novu listu
                applyLocalSearch();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

    }

    @Override
    public void onDestroyView() {
        mPresenter.onDetach();
        super.onDestroyView();
    }


    @Override
    public void updateRestaurantCook(List<RestaurantCookResponse.RestaurantCook.RestaurantCookItem> restaurantCookItemList) {
        editSearch.setText("");
        // memoizacija dishList-e
        this.dishList = restaurantCookItemList;
        mManagerRestaurantCookItemListAdapter.addItems(restaurantCookItemList);
    }

    public void openEmptyDishDetailsActivity() {
        ManagerRestaurantActivity managerRestaurantActivity = (ManagerRestaurantActivity)getActivity();
        if (managerRestaurantActivity != null) {
            MenuResponse.Dish dish = new MenuResponse.Dish();
            dish.setId(-1L);
            managerRestaurantActivity.openDishDetailsActivity(dish);
        } else {
            Toast.makeText(getContext(), "Ne valja ti ovo, druze (:", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void deleteDish(Long id) {
        mPresenter.deleteDish(id);
    }

    private void applyLocalSearch() {
        if (this.dishList == null || this.dishList.size() <= 0) {
            return;
        }

        String query = editSearch.getText().toString().trim().toUpperCase();
        if (query.trim().equals("") && this.dishList != null) {
            // ponistavamo pretragu
            mManagerRestaurantCookItemListAdapter.addItems(this.dishList);
            return;
        }

        List<RestaurantCookResponse.RestaurantCook.RestaurantCookItem> tmpList = new ArrayList<>();
        for(RestaurantCookResponse.RestaurantCook.RestaurantCookItem rci: this.dishList) {
            if (rci.getName().trim().toUpperCase().contains(query)) {
                tmpList.add(rci);
            }
        }

        mManagerRestaurantCookItemListAdapter.addItems(tmpList);
    }
}
