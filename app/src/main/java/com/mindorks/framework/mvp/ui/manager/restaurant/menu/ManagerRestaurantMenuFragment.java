package com.mindorks.framework.mvp.ui.manager.restaurant.menu;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mindorks.framework.mvp.R;
import com.mindorks.framework.mvp.data.network.model.MenuResponse;
import com.mindorks.framework.mvp.di.component.ActivityComponent;
import com.mindorks.framework.mvp.ui.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class ManagerRestaurantMenuFragment extends BaseFragment implements ManagerRestaurantMenuMvpView,
        ManagerDishTypeListAdapter.ManagerDishTypeItemListCallback,
        ManagerDishListAdapter.ManagerDishListItemCallback {

    private static final String TAG = "ManagerRestaurantMenuFragment";

    @Inject
    ManagerRestaurantMenuMvpPresenter<ManagerRestaurantMenuMvpView> mPresenter;

    @Inject
    ManagerDishTypeListAdapter mDishTypeListAdapter;

    @Inject
    LinearLayoutManager mLayoutManager;

    @BindView(R.id.manager_dish_type_list_recyclerview)
    RecyclerView mRecyclerView;

    @BindView(R.id.manager_edit_text_menu_name)
    EditText editMenuName;

    private MenuResponse.Menu menu;
    private MenuResponse.Menu originalMenu;
    private List<MenuResponse.Dish> allDishes;

    public static ManagerRestaurantMenuFragment newInstance() {
        Bundle args = new Bundle();
        ManagerRestaurantMenuFragment fragment = new ManagerRestaurantMenuFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public ManagerRestaurantMenuFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_manager_restaurant_menu, container, false);
        ActivityComponent component = getActivityComponent();
        if (component != null) {
            component.inject(this);
            setUnBinder(ButterKnife.bind(this, view));
            mPresenter.onAttach(this);
            // TODO: eventualno callback za RETRY dugme
            mDishTypeListAdapter.setmCallback(this);
            mDishTypeListAdapter.setManagerDishListItemCallback(this);
        }
        return view;
    }

    @Override
    protected void setUp(View view) {

        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mDishTypeListAdapter);

        // TODO vi3: ovde svakako ide restoran ciji je korisnik manager
        mPresenter.onViewPrepared(1L);
        mPresenter.getAllRestaurantDishes(1L);
    }

    // updateMenu ili updateAllRestaurantDishes mora da se zavrsi ranije
    @Override
    synchronized public void updateMenu(MenuResponse.Menu menu) {
        this.menu = menu;
        this.saveOriginalMenuState();

        // moramo postaviti sva jela za autocomplete, pa onda dishtypes
        // jer ce svaki dishtype iskoristiti listu sa jelima
        if (this.allDishes != null) {
            mDishTypeListAdapter.setDishList(this.allDishes);
        }

        editMenuName.setText(menu.getName());
        mDishTypeListAdapter.addItems(menu.getDishTypeList());
    }

    // jedna od ove dve metoda mora da se zavrsi ranije
    @Override
    synchronized public void updateAllRestaurantDishes(List<MenuResponse.Dish> dishList) {
        if (dishList == null) {
            this.allDishes = new ArrayList<>();
        } else {
            this.allDishes = dishList;
        }
        mDishTypeListAdapter.setDishList(this.allDishes);
        // da probamo da update-ujemo sve ViewHoldere Za DishType i tako proguramo ovu lisu
        if (this.menu != null) {
            mDishTypeListAdapter.addItems(this.menu.getDishTypeList());
        }
    }

    void saveOriginalMenuState() {
        this.originalMenu = new MenuResponse.Menu();

        this.originalMenu.setId(this.menu.getId());
        this.originalMenu.setName(this.menu.getName());

        this.originalMenu.setDishTypeList(new ArrayList<MenuResponse.DishType>());
        // trebalo bi da iskopiramo svaki dishType, a sacuvacemo dish u njima
        for (MenuResponse.DishType dt: this.menu.getDishTypeList()) {
            this.originalMenu.getDishTypeList().add(dt.copyAllButDishes());
        }
    }

    // uklanjamo dishType
    @Override
    public void removeDishType(MenuResponse.DishType dishType) {
        // uklanjamo tip jela
        this.menu.getDishTypeList().remove(dishType);
        // radimo update dishType liste
        mDishTypeListAdapter.addItems(this.menu.getDishTypeList());
    }

    @Override
    public void onsEmptyViewRetryButtonClick() {

    }

    @Override
    public void removeDishFromMenu(MenuResponse.Dish dish, MenuResponse.DishType dishType) {
        // nadjemo dishtype
        for (MenuResponse.DishType dt: this.menu.getDishTypeList()) {
            if (dt.getId().equals(dishType.getId())) {
                dt.getDishList().remove(dish);
                break;
            }
        }

        // trebalo bi da update dishtype liste odradi i update dish lista
        // ali i autocomplete-ova
        mDishTypeListAdapter.addItems(this.menu.getDishTypeList());
    }

    @Override
    public void addDishToDishType(MenuResponse.Dish dish, MenuResponse.DishType dishType) {
        // nadjemo dishtype
        for (MenuResponse.DishType dt: this.menu.getDishTypeList()) {
            if (dt.getId().equals(dishType.getId())) {
                dt.getDishList().add(dish);
                break;
            }
        }
        // trebalo bi da update dishtype liste odradi i update dish lista
        // ali i autocomplete-ova
        mDishTypeListAdapter.addItems(this.menu.getDishTypeList());
    }
}
