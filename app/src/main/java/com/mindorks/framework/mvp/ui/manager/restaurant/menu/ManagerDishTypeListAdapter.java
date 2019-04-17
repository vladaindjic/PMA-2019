package com.mindorks.framework.mvp.ui.manager.restaurant.menu;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import com.mindorks.framework.mvp.R;
import com.mindorks.framework.mvp.data.network.model.MenuResponse;
import com.mindorks.framework.mvp.data.network.model.RestaurantDetailsResponse;
import com.mindorks.framework.mvp.ui.base.BaseViewHolder;
import com.mindorks.framework.mvp.ui.manager.restaurant.utils.ManagerEmptyViewHolderTextViewOnly;
import com.mindorks.framework.mvp.ui.utils.OnRetryButtonClickCallback;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ManagerDishTypeListAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    public static final int VIEW_TYPE_EMPTY = 0;
    public static final int VIEW_TYPE_NORMAL = 1;


    private ManagerDishTypeItemListCallback mCallback;

    private List<MenuResponse.DishType> mDishTypeList;
    private Context context;

    private List<MenuResponse.Dish> dishList;

    public List<MenuResponse.Dish> getDishList() {
        return dishList;
    }

    public void setDishList(List<MenuResponse.Dish> dishList) {
        this.dishList = dishList;
    }

    ManagerDishListAdapter.ManagerDishListItemCallback managerDishListItemCallback;

    public ManagerDishTypeListAdapter(List<MenuResponse.DishType> mDishTypeList, Context context) {
        this.mDishTypeList = mDishTypeList;
        this.context = context;
    }

    public ManagerDishListAdapter.ManagerDishListItemCallback getManagerDishListItemCallback() {
        return managerDishListItemCallback;
    }

    public void setManagerDishListItemCallback(ManagerDishListAdapter.ManagerDishListItemCallback managerDishListItemCallback) {
        this.managerDishListItemCallback = managerDishListItemCallback;
    }

    public interface ManagerDishTypeItemListCallback extends OnRetryButtonClickCallback {
        void removeDishType(MenuResponse.DishType dishType);

        void addDishToDishType(MenuResponse.Dish dish, MenuResponse.DishType dishType);
    }

    public ManagerDishTypeItemListCallback getmCallback() {
        return mCallback;
    }

    public void setmCallback(ManagerDishTypeItemListCallback mCallback) {
        this.mCallback = mCallback;
    }

    public void addItems(List<MenuResponse.DishType> dishTypeList) {
        mDishTypeList.clear();
        mDishTypeList.addAll(dishTypeList);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        switch (viewType) {
            case VIEW_TYPE_NORMAL:
                return new ManagerDishTypeListAdapter.ViewHolder(
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.manager_dish_type_list_item_layout, parent,
                                false));
            case VIEW_TYPE_EMPTY:
            default:
                return new ManagerEmptyViewHolderTextViewOnly(
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.manager_empty_view_item_text_view_only
                                , parent, false), "No dish type");
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mDishTypeList != null && mDishTypeList.size() > 0) {
            return VIEW_TYPE_NORMAL;
        } else {
            return VIEW_TYPE_EMPTY;
        }
    }

    @Override
    public int getItemCount() {
        if (mDishTypeList != null && mDishTypeList.size() > 0) {
            return mDishTypeList.size();
        } else {
            return 1;
        }
    }

    public class ViewHolder extends BaseViewHolder {

        @BindView(R.id.manager_remove_dish_type_btn)
        Button btnRemoveDishType;

        @BindView(R.id.manager_edit_text_dish_type_name)
        EditText editDishTypeName;

        @BindView(R.id.manager_dish_list_recyclerview)
        RecyclerView mRecyclerView;

        @BindView(R.id.manager_dish_type_dish_list_autocomplete_txt)
        AutoCompleteTextView autoDishes;

        @BindView(R.id.manager_dish_type_add_kitchen_btn)
        Button btnAddDish;

        // Injektovanje ne prolazi
        @Inject
        LinearLayoutManager mLayoutManager;

        @Inject
        ManagerDishListAdapter mManagerDishListAdapter;

        ManagerDishArrayAdapter innerDishArrayAdapter;

        MenuResponse.DishType innerDishType;

        private MenuResponse.Dish innerTypedDish;

        private boolean currentlyAddingDish = false;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        protected void clear() {
            editDishTypeName.setText("");
            if (mManagerDishListAdapter != null) {
                mManagerDishListAdapter.addItems(new ArrayList<MenuResponse.Dish>());
            }
        }

        public void onBind(int position) {
            super.onBind(position);

            final MenuResponse.DishType dishType = mDishTypeList.get(position);
            this.innerDishType = dishType;

            if (dishType.getName() != null) {
                editDishTypeName.setText(dishType.getName());
            }

            // pripremimo sva jela koja treba da se nadju u autocomplete-u
            if (dishList != null) {
                prepareDishesForAutocomplete();
            }
            // FIXME vi3: videti da li postoji sansa da se ovo samo injektuje
            // Za sada je moralo ovako da se implementira, jer injekcija ne prolazi
            mManagerDishListAdapter =
                    new ManagerDishListAdapter(new ArrayList<MenuResponse.Dish>());
            // dodajemo sva jela
            mManagerDishListAdapter.addItems(dishType.getDishList());
            // dodajemo i dishType
            mManagerDishListAdapter.setmDishType(dishType);

            mLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);

            mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            mRecyclerView.setLayoutManager(mLayoutManager);
            mRecyclerView.setItemAnimator(new DefaultItemAnimator());
            mRecyclerView.setAdapter(mManagerDishListAdapter);


            // postavljamo callback u unutrasnjem adapteru jela
            if (managerDishListItemCallback != null) {
                mManagerDishListAdapter.setmCallback(managerDishListItemCallback);
            }

            // kada pritisnemo dugme za brisanje dishType-a, isti se obrise u fragmentu
            btnRemoveDishType.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mCallback != null) {
                        mCallback.removeDishType(dishType);
                    }
                }
            });

            // kada pritisnemo dugme za dodavanje jela, to jelo se dodaje na kategoriju
            // (dishType) u fragmentu
            btnAddDish.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mCallback != null) {
                        // NOTE vi3: ovo je zaista opasan hack, jer sam ja debil
                        // TODO vi3: proveri da li to ovo radi kako treba ili treba lepse odraditi
                        // Oznacavamo da dodajemo jelo, kako nam se ne bi obrisalo memoizovano
                        // jelo (u metodi removeTypedDish()).
                        currentlyAddingDish = true;
                        // praznimo prikaz
                        autoDishes.setText("");
                        // pozivamo dodavanje jela
                        mCallback.addDishToDishType(innerTypedDish, innerDishType);
                        // moramo sami ukloniti jelo koje smo dodali
                        currentlyAddingDish = false;
                        // brisemo ono sto je bilo memoizovano
                        removeTypedDish();

                    }
                }
            });

            // ako se nesto ukuca, ponistimo prethodni izbor korisnika
            autoDishes.addTextChangedListener(new TextWatcher() {
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

            // kada korisnik izabere jelo, memoizujemo ga
            autoDishes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Object o = parent.getItemAtPosition(position);
                    if (o instanceof MenuResponse.Dish) {
                        memoizeTypedDish((MenuResponse.Dish) o);
                    }
                }
            });

            // podrazumevano nema izabranog jela
            this.removeTypedDish();
            this.currentlyAddingDish = false;

        }

        private void prepareDishesForAutocomplete() {
            if (dishList == null) {
                return;
            }

            List<MenuResponse.Dish> tmpDishes = new ArrayList<>();
            tmpDishes.addAll(dishList);
            innerDishArrayAdapter = new ManagerDishArrayAdapter(context,
                    R.id.manager_autocomplete_dish_list_item_name, tmpDishes);
            // cim prvo slovo unese, nesto ce se prikazati
            autoDishes.setThreshold(1);
            autoDishes.setAdapter(innerDishArrayAdapter);
            if (this.innerDishType != null) {
                innerDishArrayAdapter.checkDishesThatAreInRestaurantAndUpdateList(innerDishType);
            }
        }

        // uklanjamo memoizovanu kuhinju
        private void removeTypedDish() {
            if (this.currentlyAddingDish) {
                // Text je promenjen sa setText iz listenera koji je dodat na dugme btnAddDish.
                // To znaci da nema potrebe da uklanjamo jelo.
                return;
            }
            this.innerTypedDish = null;
            // ponistimo kuhinju, a samim tim i mogucnost pritiska dugmeta za dodavanje
            this.btnAddDish.setClickable(false);
            // s obzirom na to da ne dodajemo, mozemo ponistiti
            this.currentlyAddingDish = false;
        }

        // memoizujemo kuhinju
        private void memoizeTypedDish(MenuResponse.Dish dish) {
            this.innerTypedDish = dish;
            // kada zapamtimo kuhinju, dozvolimo da dugme za dodavanje moze da se klikne
            this.btnAddDish.setClickable(true);
        }
    }

//    public class EmptyViewHolder extends BaseViewHolder {
//
//        @BindView(R.id.btn_retry)
//        Button retryButton;
//
//        @BindView(R.id.tv_message)
//        TextView messageTextView;
//
//        public EmptyViewHolder(View itemView) {
//            super(itemView);
//            ButterKnife.bind(this, itemView);
//        }
//
//        @Override
//        protected void clear() {
//
//        }
//
//        @OnClick(R.id.btn_retry)
//        void onRetryClick() {
//            if (mCallback != null)
//                mCallback.onsEmptyViewRetryButtonClick();
//        }
//    }

}
