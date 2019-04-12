package com.mindorks.framework.mvp.ui.user.restaurant.ratings;


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
import com.mindorks.framework.mvp.di.component.ActivityComponent;
import com.mindorks.framework.mvp.ui.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserRestaurantRatingFragment extends BaseFragment implements UserRestaurantRatingMvpView {

    private static final String TAG = "UserRestaurantRatingFragment";

    @Inject
    UserRestaurantRatingMvpPresenter<UserRestaurantRatingMvpView> mPresenter;


    @BindView(R.id.restaurant_rating_text_value)
    TextView ratingTextView;

    //TODO Milan: Obraditi rating dogadjaj
    @BindView(R.id.restaurant_comment_text_edit)
    EditText restaurantCommentText;


    //Comments Section
    @BindView(R.id.comment_recycler_view)
    RecyclerView mRecyclerView;


    @Inject
    LinearLayoutManager mLayoutManager;

    @Inject
    UserRestaurantCommentAdapter mUserRestaurantCommentAdapter;



    public UserRestaurantRatingFragment() {
        // Required empty public constructor
    }

    public static UserRestaurantRatingFragment newInstance() {
        Bundle args = new Bundle();
        UserRestaurantRatingFragment fragment = new UserRestaurantRatingFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_restaurant_rating, container, false);
        ActivityComponent component = getActivityComponent();
        if (component != null) {
            component.inject(this);
            setUnBinder(ButterKnife.bind(this, view));
            mPresenter.onAttach(this);
            // mKitchensAdapter.setmCallback(this);
        }
        return view;
    }

    @Override
    protected void setUp(View view) {

        List<String> comments = new ArrayList<String>();
        comments.add("Koemntar 1");
        comments.add("KOmentar 2");
        comments.add("Komentar 3, ovo je malo duzi komentar da vidimo sta ce biti sa linijama i kada ce si  kako prelomiti, da li radi sve  kako treba/????");
        this.updateRestaurantCommentList(comments);

        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mUserRestaurantCommentAdapter);


    }

    @OnClick(R.id.restaurant_comment_button)
    public void leaveComment() {
        Toast.makeText(getActivity(), this.restaurantCommentText.getText(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void updateRestaurantCommentList(List<String> comments) {
        mUserRestaurantCommentAdapter.addItems(comments);
    }
}
