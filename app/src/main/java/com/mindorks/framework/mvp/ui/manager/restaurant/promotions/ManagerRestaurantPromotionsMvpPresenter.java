package com.mindorks.framework.mvp.ui.manager.restaurant.promotions;

import com.mindorks.framework.mvp.ui.base.MvpPresenter;

public interface ManagerRestaurantPromotionsMvpPresenter<V extends ManagerRestaurantPromotionsMvpView>
        extends MvpPresenter<V> {
    public void onViewPrepared();

    void deletePromotion(Long promotionId);
}
