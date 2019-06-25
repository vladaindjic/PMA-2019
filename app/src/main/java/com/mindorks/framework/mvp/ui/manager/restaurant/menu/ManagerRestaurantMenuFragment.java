package com.mindorks.framework.mvp.ui.manager.restaurant.menu;


import android.os.Bundle;
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

    private static Long counter = 0L;

    synchronized private static Long getNewCounter() {
        return --counter;
    }

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

    @BindView(R.id.manager_menu_dish_type_edit_text)
    EditText editDishTypeName;

    @BindView(R.id.manager_menu_add_dish_type_btn)
    Button btnAddDishType;

    @BindView(R.id.manager_menu_submit_btn)
    Button btnSubmit;
    @BindView(R.id.manager_menu_cancel_btn)
    Button btnCancel;


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
    public void onResume() {
        super.onResume();
        // vi3 prebaceno onResume
        myLocalSetUp();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if (getBaseActivity() != null) {
                // vi3 prebaceno onResume
                myLocalSetUp();
            }
        }
    }

    @Override
    protected void setUp(View view) {

        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mDishTypeListAdapter);
//        // vi3 prebaceno onResume
//        myLocalSetUp();
    }

    private void myLocalSetUp() {
        // TODO vi3: ovde svakako ide restoran ciji je korisnik manager
        mPresenter.onViewPrepared();
        mPresenter.getAllRestaurantDishes();

        // na klik dugmeta radimo dodavanje
        btnAddDishType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewDishTypeToMenu();
            }
        });

        editDishTypeName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                checkIfDishTypeNameIsOkAndAllowBtnAddClick();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkIfDishTypeNameIsOkAndAllowBtnAddClick();
            }

            @Override
            public void afterTextChanged(Editable s) {
                checkIfDishTypeNameIsOkAndAllowBtnAddClick();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitUpdatingMenu();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelUpdatingMenu();
            }
        });


        // proverimo da li treba da se dozvoli klik ili ne
        // podrazumevano bi trebalo da bude blokirano
        checkIfDishTypeNameIsOkAndAllowBtnAddClick();
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
        for (MenuResponse.DishType dt : this.menu.getDishTypeList()) {
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
        for (MenuResponse.DishType dt : this.menu.getDishTypeList()) {
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
        for (MenuResponse.DishType dt : this.menu.getDishTypeList()) {
            if (dt.getId().equals(dishType.getId())) {
                dt.getDishList().add(dish);
                break;
            }
        }
        // trebalo bi da update dishtype liste odradi i update dish lista
        // ali i autocomplete-ova
        mDishTypeListAdapter.addItems(this.menu.getDishTypeList());
    }

    public void checkIfDishTypeNameIsOkAndAllowBtnAddClick() {
        // nema edit text, pa blokiramo dodavanje
        if (editDishTypeName == null) {
            btnAddDishType.setClickable(false);
            return;
        }

        // prazan unos, pa blokiramo dodavanje
        String typedTxt = editDishTypeName.getText().toString().trim();
        if (typedTxt.equals("")) {
            btnAddDishType.setClickable(false);
            return;
        }

        // da li se mozda neko ime poklapa?
        for (MenuResponse.DishType dt : this.menu.getDishTypeList()) {
            if (dt.getName().toUpperCase().equals(typedTxt.toUpperCase())) {
                btnAddDishType.setClickable(false);
                return;
            }
        }

        // unos je u redu, moze dopustiti dodavanje
        btnAddDishType.setClickable(true);
    }

    public void addNewDishTypeToMenu() {
        MenuResponse.DishType dishType = new MenuResponse.DishType();
        // Ako ne radim direktan submit, nemam id
        // Pa cu ubaciti svoj reverse counter da dobijam unique negativne identifikatore.
        // Pre nego sto odradim submit, svima setujem id na null, pa ce biti kreirani na serveru.
        dishType.setId(getNewCounter());
        // ime preuzimamo iz edit texta
        dishType.setName(editDishTypeName.getText().toString());
        // nemamo jela
        dishType.setDishList(new ArrayList<MenuResponse.Dish>());
        // dodajemo dish type na postojecu listu u meniju
        this.menu.getDishTypeList().add(dishType);
        // update-ujemo samo listu u meniju
        this.mDishTypeListAdapter.addItems(this.menu.getDishTypeList());
        // ponistavamo unos
        this.editDishTypeName.setText("");
    }

    private void submitUpdatingMenu() {
        MenuResponse.Menu menu = new MenuResponse.Menu();
        menu.setId(this.menu.getId());
        menu.setName(this.menu.getName());
        // proveriti da li treba da se pravi nova lista
        menu.setDishTypeList(this.menu.getDishTypeList());
        // svi negativni ID-evi se ponistavaju
        for (MenuResponse.DishType dt : menu.getDishTypeList()) {
            if (dt.getId() <= 0) {
                dt.setId(null);
            }
        }
        mPresenter.submitMenu(menu);

        // TODO vi3: takodje treba videti koji je najbolji nacin da se uradi
        // update fotografije
    }

    private void cancelUpdatingMenu() {
        // posto smo memoizovali restoran koji smo svukli sa neta
        // samo ih ponovo postavimo i to je to
        // sve izmene ce biti ponostene
        this.updateMenu(this.originalMenu);
    }
}
