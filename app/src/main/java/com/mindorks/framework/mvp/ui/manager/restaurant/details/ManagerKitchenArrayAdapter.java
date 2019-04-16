package com.mindorks.framework.mvp.ui.manager.restaurant.details;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.mindorks.framework.mvp.R;
import com.mindorks.framework.mvp.data.network.model.RestaurantDetailsResponse;

import java.util.ArrayList;
import java.util.List;

public class ManagerKitchenArrayAdapter extends ArrayAdapter<RestaurantDetailsResponse.Kitchen> {
    private LayoutInflater layoutInflater;
    private List<RestaurantDetailsResponse.Kitchen> mKitchens;
    private List<RestaurantDetailsResponse.Kitchen> originalKitchenList;

    public List<RestaurantDetailsResponse.Kitchen> getmKitchens() {
        return mKitchens;
    }

    public void setmKitchens(List<RestaurantDetailsResponse.Kitchen> mKitchens) {
        this.mKitchens = mKitchens;
    }

    private Filter mFilter = new Filter() {
        @Override
        public String convertResultToString(Object resultValue) {
            return ((RestaurantDetailsResponse.Kitchen) resultValue).getName();
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            if (constraint != null) {
                List<RestaurantDetailsResponse.Kitchen> suggestions = new ArrayList<>();
                for (RestaurantDetailsResponse.Kitchen kitchen : mKitchens) {
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
                addAll((ArrayList<RestaurantDetailsResponse.Kitchen>) results.values);
            } else {
                // no filter, add entire original list back in
                addAll(new ArrayList<RestaurantDetailsResponse.Kitchen>());
            }
            notifyDataSetChanged();
        }
    };

    public ManagerKitchenArrayAdapter(Context context, int textViewResourceId, List<RestaurantDetailsResponse.Kitchen> kitchens) {
        super(context, textViewResourceId, kitchens);
        // copy all the customers into a master list
        mKitchens = new ArrayList<RestaurantDetailsResponse.Kitchen>(kitchens.size());
        mKitchens.addAll(kitchens);
        // dodamo i u originalnu listu koju ne diramo
        originalKitchenList = new ArrayList<>();
        originalKitchenList.addAll(kitchens);

        layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            view = layoutInflater.inflate(R.layout.manager_autocomplete_kitchen_list_item, null);
        }

        RestaurantDetailsResponse.Kitchen kitchen = getItem(position);

        TextView name = (TextView) view.findViewById(R.id.manager_autocomplete_kitchen_list_item_name);
        name.setText(kitchen.getName());

        return view;
    }

    @Override
    public Filter getFilter() {
        return mFilter;
    }

    // FIXME vi3: najjednostavniji i ujedno neoptimalan hack da nemam u listi kuhinje koje vec
    // restoran ima
    public void checkKitchensThatAreInRestaurantAndUpdateList(RestaurantDetailsResponse.RestaurantDetails restaurantDetails) {
        if (this.mKitchens == null) {
            return;
        }

        this.mKitchens.clear();
        this.mKitchens.addAll(originalKitchenList);
        // sve one kuhinje kojih nema u restoranu, dodajemo ovde
        for (RestaurantDetailsResponse.Kitchen kRes : restaurantDetails.getKitchens()) {
            for (RestaurantDetailsResponse.Kitchen kOrig: originalKitchenList) {
                if (kRes.getId().equals(kOrig.getId())) {
                    this.mKitchens.remove(kOrig);
                }
            }
        }


    }
}
