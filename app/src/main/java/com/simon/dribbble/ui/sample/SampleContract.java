package com.simon.dribbble.ui.sample;

import com.simon.dribbble.data.model.Cook;
import com.simon.dribbble.ui.BasePresenter;
import com.simon.dribbble.ui.BaseView;

import java.util.List;

/**
 * Created by: Simon
 * Email: simon.han0220@gmail.com
 * Created on: 2016/8/19 9:54
 */

public interface SampleContract {

    interface View extends BaseView<Presenter> {
        /**
         * 设置是否显示正在加载页面
         *
         * @param active
         */
        void setLoading(boolean active);

        /**
         * 显示数据
         *
         * @param cooks
         */
        void showCooks(List<Cook> cooks);


    }

    interface Presenter extends BasePresenter {
        /**
         * 加载数据
         *
         * @param forceUpdate
         */
        void loadData(boolean forceUpdate);

        /**
         * 需要接收页面跳转带回来的数据的方法
         *
         * @param requestCode
         * @param resultCode
         */
        void result(int requestCode, int resultCode);
    }
}
