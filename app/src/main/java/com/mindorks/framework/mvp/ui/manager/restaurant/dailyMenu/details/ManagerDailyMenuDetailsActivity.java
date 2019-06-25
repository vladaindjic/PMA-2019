package com.mindorks.framework.mvp.ui.manager.restaurant.dailyMenu.details;

import android.app.TimePickerDialog;
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
import android.widget.EditText;
import android.widget.TimePicker;

import com.mindorks.framework.mvp.R;
import com.mindorks.framework.mvp.data.network.model.DishDetailsResponse;
import com.mindorks.framework.mvp.data.network.model.MealResponse;
import com.mindorks.framework.mvp.data.network.model.MenuResponse;
import com.mindorks.framework.mvp.ui.base.BaseActivity;
import com.mindorks.framework.mvp.ui.manager.restaurant.dish.ManagerDishDetailsActivity;
import com.mindorks.framework.mvp.ui.manager.restaurant.menu.ManagerDishArrayAdapter;
import com.mindorks.framework.mvp.ui.manager.restaurant.menu.ManagerDishListAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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

    @BindView(R.id.meal_name_edit_text)
    EditText mealNameTxt;

//    @BindView(R.id.user_meal_start_time_txt)
//    EditText startTimeTxt;

//    @BindView(R.id.user_meal_end_time_txt)
//    EditText endTimeTxt;

    @BindView(R.id.user_meal_details_txt_price_values)
    EditText priceTxt;

    @BindView(R.id.user_meal_details_txt_description_values)
    EditText descriptonTxt;

    @BindView(R.id.user_meal_start_time_btn)
    Button startTimeButton;

    @BindView(R.id.user_meal_end_time_btn)
    Button endTimeButton;

    private Long dailyMenuId;

    @Inject
    ManagerDishListAdapter mDishAdapter;

    private MenuResponse.Dish typedDish;
    private MealResponse.MealDetails mealDetails;
    private MealResponse.MealDetails mealDetailsOrginal;
    private long mealId;


    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, ManagerDailyMenuDetailsActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setTheme(R.style.AppTheme);

        setContentView(R.layout.activity_manager_daily_menu_details);

        getActivityComponent().inject(this);

        setUnBinder(ButterKnife.bind(this));

        mPresenter.onAttach(ManagerDailyMenuDetailsActivity.this);

        setUp();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //myLocalSetUp();
    }

    @Override
    protected void setUp() {
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mDishAdapter);
        mDishAdapter.setmDishMealCallbakc(this);
        // vi3 prebaceno onResume
        myLocalSetUp();
    }

    private void myLocalSetUp() {
        this.mealDetails = new MealResponse.MealDetails();
        this.mealDetailsOrginal = new MealResponse.MealDetails();
        this.dailyMenuId = getIntent().getLongExtra("menuId",-1L);
        this.mealId = getIntent().getLongExtra("mealId",-1L);


        mPresenter.prepareDishForAutocomplete();
        if(mealId!=-1L){
            mPresenter.loadMeal(mealId);
        } else {
            mDishAdapter.addItems(new ArrayList<MenuResponse.Dish>());
        }
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

    @Override
    public void updateMeal(MealResponse.MealDetails meal) {
        this.mealDetails = meal;
        this.saveOrginalMeal();
        SimpleDateFormat sdf =new SimpleDateFormat("H:m");
        this.mealNameTxt.setText(meal.getName());
//        this.startTimeTxt.setText(meal.getStartTime());
//        this.endTimeTxt.setText(meal.getEndTime());
        this.startTimeButton.setText(sdf.format(meal.getStartTime()));
        this.endTimeButton.setText(sdf.format(meal.getEndTime()));
        this.descriptonTxt.setText(meal.getDescription());
        this.priceTxt.setText(meal.getPrice().toString());
        //TODO: Dodati jela
        mDishAdapter.addItems(meal.getDishList());
        mDishArrayAdapter.checkDishesThatAreMealAndUpdateList(mealDetails);

    }

    @OnClick(R.id.user_meal_start_time_btn)
    public void openStarTimeDialog(){
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog tp1 = new TimePickerDialog(ManagerDailyMenuDetailsActivity.this,  android.R.style.Theme_Holo_Light_Dialog,new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                System.out.println(hourOfDay +" : "+ minute);
                startTimeButton.setText(hourOfDay+":"+minute);
                Calendar cal = Calendar.getInstance();
                cal.set(Calendar.HOUR_OF_DAY,hourOfDay);
                cal.set(Calendar.MINUTE,minute);
                Date date = cal.getTime();
                mealDetails.setStartTime(date);
            }
        },hour,minute,false);
        tp1.setTitle("Pick start time");
        tp1.show();
    }



    @OnClick(R.id.user_meal_end_time_btn)
    public void openEndTimeDialot(){
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog tp1 = new TimePickerDialog(ManagerDailyMenuDetailsActivity.this,  android.R.style.Theme_Holo_Light_Dialog,new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                System.out.println(hourOfDay +" : "+ minute);
                endTimeButton.setText(hourOfDay+":"+minute);
                Calendar cal = Calendar.getInstance();
                cal.set(Calendar.HOUR_OF_DAY,hourOfDay);
                cal.set(Calendar.MINUTE,minute);
                Date date = cal.getTime();
                mealDetails.setEndTime(date);
            }
        },hour,minute,false);
        tp1.setTitle("Pick end time");
        tp1.show();
    }

    @Override
    public void back() {
        finish();
    }

    private void saveOrginalMeal() {
        this.mealDetailsOrginal = new MealResponse.MealDetails();
        this.mealDetailsOrginal.setName(this.mealDetails.getName());
        this.mealDetailsOrginal.setId(this.mealDetails.getId());
        this.mealDetailsOrginal.setStartTime(this.mealDetails.getStartTime());
        this.mealDetailsOrginal.setEndTime(this.mealDetails.getEndTime());
        this.mealDetailsOrginal.setDescription(this.mealDetails.getDescription());
        this.mealDetailsOrginal.setPrice(this.mealDetails.getPrice());
        List<DishDetailsResponse.NutritiveValue> nutritiveValues = new ArrayList<>();
        if (mealDetails.getNutritiveValues() != null) {
            for (DishDetailsResponse.NutritiveValue nv : this.mealDetails.getNutritiveValues()) {
                DishDetailsResponse.NutritiveValue newNV = new DishDetailsResponse.NutritiveValue();
                newNV.setId(nv.getId());
                newNV.setUnit(nv.getUnit());
                newNV.setName(nv.getName());
                newNV.setValue(nv.getValue());
                nutritiveValues.add(newNV);
            }
        }
        this.mealDetailsOrginal.setNutritiveValues(nutritiveValues);
        List<MenuResponse.Dish> dishList = new ArrayList<>();
        if (mealDetails.getDishList() != null) {
            for (MenuResponse.Dish dish : this.mealDetails.getDishList()) {
                MenuResponse.Dish newDish = new MenuResponse.Dish();
                newDish.setId(dish.getId());
                newDish.setName(dish.getName());
                newDish.setPrice(dish.getPrice());
                newDish.setImgUrl(dish.getImgUrl());
                dishList.add(newDish);
            }
        }
        this.mealDetailsOrginal.setDishList(dishList);
    }

    @OnClick(R.id.submit_meal_details_btn)
    public void submitMealDetais() {
        mealDetails.setDescription(descriptonTxt.getText().toString());
//        mealDetails.setEndTime(endTimeTxt.getText().toString());
        mealDetails.setName(mealNameTxt.getText().toString());
        mealDetails.setPrice(Double.parseDouble(priceTxt.getText().toString()));
//        mealDetails.setStartTime(startTimeTxt.getText().toString());

        if (mealId != -1L) {
            mPresenter.updateMeal(mealId,mealDetails);
        }else {
            mPresenter.createMeal(dailyMenuId, mealDetails);
        }
    }

    @OnClick(R.id.cancel_meal_details_btn)
    public void cancelSubmit() {
        if(this.mealDetailsOrginal.getId()!=null) {
            this.updateMeal(mealDetailsOrginal);
        }else {
            back();
        }
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
