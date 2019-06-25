package com.mindorks.framework.mvp.ui.manager.restaurant.promotions;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mindorks.framework.mvp.R;
import com.mindorks.framework.mvp.data.network.model.RestaurantPromotionsResponse;
import com.mindorks.framework.mvp.di.component.ActivityComponent;
import com.mindorks.framework.mvp.ui.base.BaseFragment;
import com.mindorks.framework.mvp.ui.manager.restaurant.ManagerRestaurantActivity;
import com.mindorks.framework.mvp.ui.user.restaurant.UserRestaurantActivity;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class ManagerRestaurantPromotionsFragment extends BaseFragment implements
        ManagerRestaurantPromotionsMvpView, ManagerRestaurantPromotionsCallback {


    private static final String TAG = "ManagerRestaurantPromotionsFragment";

    @Inject
    ManagerRestaurantPromotionsMvpPresenter<ManagerRestaurantPromotionsMvpView> mPresenter;

    //Inject Adapter
    @Inject
    ManagerRestaurantPromotionsAdapter mManagerRestaurantPromotionsAdapter;

    //Inject LayoutManager
    @Inject
    LinearLayoutManager mLayoutManager;

    //Bind View
    @BindView(R.id.manager_restaurant_promotions_view)
    RecyclerView mRecyclerView;


    public static ManagerRestaurantPromotionsFragment newInstance() {
        Bundle args = new Bundle();
        ManagerRestaurantPromotionsFragment fragment = new ManagerRestaurantPromotionsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public ManagerRestaurantPromotionsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getContext().setTheme(R.style.AppTheme);
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_manager_restaurant_promotions, container, false);

        ActivityComponent component = getActivityComponent();
        if (component != null) {
            component.inject(this);
            setUnBinder(ButterKnife.bind(this, view));
            mPresenter.onAttach(this);
            mManagerRestaurantPromotionsAdapter.setmCallback(this);
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
        if (isVisibleToUser) {
            if (getBaseActivity() != null) {
                // vi3 prebaceno onResume
                mPresenter.onViewPrepared();
            }
        }
    }

    @Override
    protected void setUp(View view) {
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mManagerRestaurantPromotionsAdapter);

        // TODO vi3: dobaljanje restorana za koji je menadzer zaduzen
        // vi3 prebaceno onResume
        // mPresenter.onViewPrepared();
    }

    @OnClick(R.id.add_promotion_button)
    public void onAddPromotionButton(){
        Toast.makeText(getActivity(),"Dodaj novu promociju",Toast.LENGTH_SHORT).show();

        ManagerRestaurantActivity managerRestaurantActivity = (ManagerRestaurantActivity)getActivity();
        if (managerRestaurantActivity != null) {
            managerRestaurantActivity.openPromotionDetailsActivity(-1L);
        } else {
            Toast.makeText(getContext(), "Ne valja ti ovo, druze (:", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRestaurantsEmptyViewRetryClick() {

    }

    @Override
    public void openPromotionDetailsActivity(RestaurantPromotionsResponse.Promotion promotion) {
        // TODO vi3: ovde ima smisla implementirati logiku za editovanje ili brisanje
        Toast.makeText(getActivity(),"Detalji promocije",Toast.LENGTH_SHORT).show();
        ManagerRestaurantActivity managerRestaurantActivity = (ManagerRestaurantActivity)getActivity();
        if (managerRestaurantActivity != null) {
            managerRestaurantActivity.openPromotionDetailsActivity(promotion.getId());
        } else {
            Toast.makeText(getContext(), "Ne valja ti ovo, druze (:", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void deletePromotion(Long promotionId) {
        Toast.makeText(getActivity(),"Deleted Promotion...",Toast.LENGTH_SHORT).show();
        mPresenter.deletePromotion(promotionId);
    }


    @Override
    public void updateRestaurantPromotionsList(List<RestaurantPromotionsResponse.Promotion> promotions) {
        mManagerRestaurantPromotionsAdapter.addItems(promotions);
    }


}
