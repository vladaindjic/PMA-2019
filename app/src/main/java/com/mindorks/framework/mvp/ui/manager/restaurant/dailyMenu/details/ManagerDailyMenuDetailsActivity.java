package com.mindorks.framework.mvp.ui.manager.restaurant.dailyMenu.details;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.mindorks.framework.mvp.R;
import com.mindorks.framework.mvp.data.network.model.MealResponse;
import com.mindorks.framework.mvp.data.network.model.MenuResponse;
import com.mindorks.framework.mvp.ui.base.BaseActivity;
import com.mindorks.framework.mvp.ui.manager.restaurant.menu.ManagerDishArrayAdapter;
import com.mindorks.framework.mvp.ui.manager.restaurant.menu.ManagerDishListAdapter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ManagerDailyMenuDetailsActivity extends BaseActivity implements ManagerDailyMenuDetailsMvpView, ManagerDishListAdapter.ManagerDishListMealItemCallBack {


    @Inject
    ManagerDailyMenuDetailsMvpPresenter<ManagerDailyMenuDetailsMvpView> mPresenter;

    @Inject
    LinearLayoutManager mLayoutManager;

    ManagerDishArrayAdapter mDishArrayAdapter;

    @BindView(R.id.manager_dish_autocomplete_text)
    AutoCompleteTextView autocompleteDish;

    @BindView(R.id.manager_add_dish_menu_button)
    Button addDishButton;

    @BindView(R.id.manager_meal_dish_recycler_view)
    RecyclerView mRecyclerView;


    @Inject
    ManagerDishListAdapter mDishAdapter;

    private MenuResponse.Dish typedDish;
    private MealResponse.MealDetails mealDetails;

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, ManagerDailyMenuDetailsActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_daily_menu_details);

        getActivityComponent().inject(this);

        setUnBinder(ButterKnife.bind(this));

        mPresenter.onAttach(ManagerDailyMenuDetailsActivity.this);

        setUp();
    }

    @Override
    protected void setUp() {

        this.mealDetails = new MealResponse.MealDetails();
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mDishAdapter);
        mDishAdapter.setmDishMealCallbakc(this);

        mPresenter.prepareDishForAutocomplete();
        addDishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addDishToMeal();
            }
        });

        autocompleteDish.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object o = parent.getItemAtPosition(position);
                if (o instanceof MenuResponse.Dish) {
                    memoizeTypedDish((MenuResponse.Dish) o);
                }
            }
        });


        autocompleteDish.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                removeTypedDish();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                removeTypedDish();
            }

            @Override
            public void afterTextChanged(Editable s) {
                removeTypedDish();
            }
        });
    }

    @Override
    public void prepareDishForAutocomplete(List<MenuResponse.Dish> dishList) {
        mDishArrayAdapter = new ManagerDishArrayAdapter(this, R.id.manager_autocomplete_dish_list_item_name, dishList);
        autocompleteDish.setThreshold(1);
        autocompleteDish.setAdapter(mDishArrayAdapter);

        //Dodati kad se menja postojeci obrok

    }

    private void addDishToMeal() {
        // dodajemo izabranu kuhinju u restoran
        this.mealDetails.getDishList().add(this.typedDish);
        // update-ujemo samo prikaz kuhinja
        mDishAdapter.addItems(this.mealDetails.getDishList());
        // moramo odraditi i update svih kuhinja u adapteru
        mDishArrayAdapter.checkDishesThatAreMealAndUpdateList(this.mealDetails);
        // ponistavamo unos
        this.autocompleteDish.setText("");
    }


    // memoizujemo kuhinju
    private void memoizeTypedDish(MenuResponse.Dish dish) {
        this.typedDish = dish;
        // kada zapamtimo kuhinju, dozvolimo da dugme za dodavanje moze da se klikne
        this.addDishButton.setClickable(true);
    }

    // uklanjamo memoizovanu kuhinju
    private void removeTypedDish() {
        this.typedDish = null;
        // ponistimo kuhinju, a samim tim i mogucnost pritiska dugmeta za dodavanje
        this.addDishButton.setClickable(false);
    }


    @Override
    public void removeDishFromMeal(MenuResponse.Dish dish) {
        this.mealDetails.getDishList().remove(dish);

        mDishAdapter.addItems(this.mealDetails.getDishList());
        mDishArrayAdapter.checkDishesThatAreMealAndUpdateList(this.mealDetails);

    }
}
