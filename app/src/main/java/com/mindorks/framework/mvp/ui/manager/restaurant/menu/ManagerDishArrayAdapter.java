package com.mindorks.framework.mvp.ui.manager.restaurant.menu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.mindorks.framework.mvp.R;
import com.mindorks.framework.mvp.data.network.model.MenuResponse;
import com.mindorks.framework.mvp.data.network.model.RestaurantDetailsResponse;

import java.util.ArrayList;
import java.util.List;

public class ManagerDishArrayAdapter extends ArrayAdapter<MenuResponse.Dish> {
    private LayoutInflater layoutInflater;
    private List<MenuResponse.Dish> mDishes;
    private List<MenuResponse.Dish> originalDishList;

    public List<MenuResponse.Dish> getmDishes() {
        return mDishes;
    }

    public void setmDishes(List<MenuResponse.Dish> mDishes) {
        this.mDishes = mDishes;
    }

    private Filter mFilter = new Filter() {
        @Override
        public String convertResultToString(Object resultValue) {
            return ((MenuResponse.Dish) resultValue).getName();
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            if (constraint != null) {
                List<MenuResponse.Dish> suggestions = new ArrayList<>();
                for (MenuResponse.Dish kitchen : mDishes) {
                    // Note: change the "contains" to "startsWith" if you only want starting matches
                    if (kitchen.getName().toUpperCase().contains(constraint.toString().toUpperCase())) {
                        suggestions.add(kitchen);
                    }
                }

                results.values = suggestions;
                results.count = suggestions.size();
            }

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            clear();
            if (results != null && results.count > 0) {
                // we have filtered results
                addAll((ArrayList<MenuResponse.Dish>) results.values);
            } else {
                // no filter, add entire original list back in
                addAll(new ArrayList<MenuResponse.Dish>());
            }
            notifyDataSetChanged();
        }
    };

    public ManagerDishArrayAdapter(Context context, int textViewResourceId, List<MenuResponse.Dish> kitchens) {
        super(context, textViewResourceId, kitchens);
        // copy all the customers into a master list
        mDishes = new ArrayList<MenuResponse.Dish>(kitchens.size());
        mDishes.addAll(kitchens);
        // dodamo i u originalnu listu koju ne diramo
        originalDishList = new ArrayList<>();
        originalDishList.addAll(kitchens);

        layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            view = layoutInflater.inflate(R.layout.manager_autocomplete_dish_list_item, null);
        }

        MenuResponse.Dish kitchen = getItem(position);

        TextView name = (TextView) view.findViewById(R.id.manager_autocomplete_dish_list_item_name);
        name.setText(kitchen.getName());

        return view;
    }

    @Override
    public Filter getFilter() {
        return mFilter;
    }

    // FIXME vi3: najjednostavniji i ujedno neoptimalan hack da nemam u listi kuhinje koje vec
    // restoran ima
    public void checkDishesThatAreInRestaurantAndUpdateList(MenuResponse.DishType dishType) {
        if (this.mDishes == null) {
            return;
        }

        this.mDishes.clear();
        this.mDishes.addAll(originalDishList);
        // sve one kuhinje kojih nema u restoranu, dodajemo ovde
        for (MenuResponse.Dish kRes : dishType.getDishList()) {
            for (MenuResponse.Dish kOrig: originalDishList) {
                if (kRes.getId().equals(kOrig.getId())) {
                    this.mDishes.remove(kOrig);
                }
            }
        }


    }
}
