package com.example.mvprough1.registersearch;

import com.example.mvprough1.BasePresenter;
import com.example.mvprough1.BaseView;

public interface RegisterSearchContract {

    interface View extends BaseView<RegisterSearchPresenter> {

        String getStudentSp();

    }

    interface Presenter extends BasePresenter {

        void saveStudentSp();
    }

}
