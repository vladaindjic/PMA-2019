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
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.mindorks.framework.mvp.R;
import com.mindorks.framework.mvp.data.network.model.RestaurantRatingResponse;
import com.mindorks.framework.mvp.di.component.ActivityComponent;
import com.mindorks.framework.mvp.ui.base.BaseFragment;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserRestaurantRatingFragment extends BaseFragment implements UserRestaurantRatingMvpView, CommentCallback {

    private static final String TAG = "UserDishRatingFragment";

    @Inject
    UserRestaurantRatingMvpPresenter<UserRestaurantRatingMvpView> mPresenter;


    @BindView(R.id.restaurant_rating_text_value)
    TextView ratingTextView;

    @BindView(R.id.restaurant_comment_text_edit)
    EditText restaurantCommentText;


    //TODO Milan: Obraditi rating dogadjaj
    @BindView(R.id.rating_bar)
    RatingBar ratingBar;


    //Comments Section
    @BindView(R.id.comment_recycler_view)
    RecyclerView mRecyclerView;


    @Inject
    LinearLayoutManager mLayoutManager;

    @Inject
    UserRestaurantCommentAdapter mUserRestaurantCommentAdapter;

    boolean callRatingChnaged;
    Long restaurantId;

    public UserRestaurantRatingFragment() {
        // Required empty public constructor
        this.callRatingChnaged = true;
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
        }
        return view;
    }

    @Override
    protected void setUp(View view) {
        final Long restaurantId = getBaseActivity().getIntent().getLongExtra("restaurantId", 0L);
        this.restaurantId =restaurantId;
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mUserRestaurantCommentAdapter.setMyContext(getActivity());
        mUserRestaurantCommentAdapter.setmCallback(this);
        mRecyclerView.setAdapter(mUserRestaurantCommentAdapter);


        //Set handler for rating
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                if (callRatingChnaged) {
                    mPresenter.rateRestaurant(restaurantId, ratingBar);
                    Toast.makeText(getActivity(), ratingBar.getRating() + " " + restaurantId, Toast.LENGTH_SHORT).show();
                }
            }
        });

        mPresenter.onViewPrepared(restaurantId);

    }

    @OnClick(R.id.restaurant_comment_button)
    public void leaveComment() {
//        Toast.makeText(getActivity(), this.restaurantCommentText.getText(), Toast.LENGTH_SHORT).show();
        mPresenter.leaveComment(restaurantId,restaurantCommentText.getText().toString().trim());
        hideKeyboard();
        restaurantCommentText.setText("");
    }


    @Override
    public void updateRestaurantRating(RestaurantRatingResponse.RestaurantRating restaurantRating) {

        ratingTextView.setText(restaurantRating.getRating().toString());
        if (restaurantRating.getRated() != -1) {
            callRatingChnaged = false;
            ratingBar.setRating(restaurantRating.getRated());
            ratingBar.setIsIndicator(true);
        }
        mUserRestaurantCommentAdapter.addItems(restaurantRating.getComments());
    }

    @Override
    public void updateRestaurantRatingValue(Double value) {
        ratingTextView.setText(value.toString());
    }

    @Override
    public void voteUp(Long id) {

        mPresenter.likeComment(id);

    }

    @Override
    public void voteDown(Long id) {

        mPresenter.dislikeComment(id);
    }
}
