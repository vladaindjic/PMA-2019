package com.mindorks.framework.mvp.ui.user.dish.ratings;


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
import com.mindorks.framework.mvp.data.network.model.ComentVoteRequest;
import com.mindorks.framework.mvp.data.network.model.RestaurantRatingResponse;
import com.mindorks.framework.mvp.di.component.ActivityComponent;
import com.mindorks.framework.mvp.ui.base.BaseFragment;

import org.w3c.dom.Text;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserDishRatingFragment extends BaseFragment implements UserDishRatingMvpView, CommentCallback{

    private static final String TAG = "UserDishRatingFragment";

    @Inject
    UserDishRatingMvpPresenter<UserDishRatingMvpView> mPresenter;

    @BindView(R.id.dish_rating_text_value)
    TextView ratingTextView;

    @BindView(R.id.dish_rating_bar)
    RatingBar ratingBar;

    @BindView(R.id.dish_comment_text_edit)
    EditText commentText;

    @BindView(R.id.dish_comment_recycler_view)
    RecyclerView mRecyclerView;

    @Inject
    LinearLayoutManager mLayoutManager;

    @Inject
    UserDishCommentAdapter mUserDishCommentAdapter;

    boolean callRatingChnaged;
    Long dishId;

    public UserDishRatingFragment() {
        // Required empty public constructor
        callRatingChnaged = true;
    }

    public static UserDishRatingFragment newInstance() {
        Bundle args = new Bundle();
        UserDishRatingFragment fragment = new UserDishRatingFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_dish_rating, container, false);
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
        final Long dishId = getBaseActivity().getIntent().getLongExtra("dishId", 0L);
        this.dishId = dishId;
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mUserDishCommentAdapter.setMyContext(getActivity());
        mUserDishCommentAdapter.setmCallback(this);
        mRecyclerView.setAdapter(mUserDishCommentAdapter);


        //Set handler for rating
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                if (callRatingChnaged) {
                    mPresenter.rateDish(dishId, ratingBar);
                    Toast.makeText(getActivity(), ratingBar.getRating() + " " + dishId, Toast.LENGTH_SHORT).show();
                }
            }
        });

        mPresenter.onViewPrepared(dishId);
    }

    @Override
    public void updateDishRating(RestaurantRatingResponse.RestaurantRating restaurantRating) {

        ratingTextView.setText(restaurantRating.getRating().toString());
        if (restaurantRating.getRated() != -1) {
            callRatingChnaged = false;
            ratingBar.setRating(restaurantRating.getRated());
            ratingBar.setIsIndicator(true);
        }
        mUserDishCommentAdapter.addItems(restaurantRating.getComments());
    }

    @OnClick(R.id.dish_comment_button)
    public void leaveComment(){
        mPresenter.leaveComment(dishId,commentText.getText().toString().trim());
        hideKeyboard();
        commentText.setText("");
    }

    @Override
    public void updateDishRatingValue(Double score) {
        ratingTextView.setText(score.toString());
    }

    @Override
    public void voteUp(Long id) {
        ComentVoteRequest request = new ComentVoteRequest("POSITIVE");
        mPresenter.voteComent(id,request);
    }

    @Override
    public void voteDown(Long id) {
        ComentVoteRequest request = new ComentVoteRequest("NEGATIVE");
        mPresenter.voteComent(id,request);
    }
}
