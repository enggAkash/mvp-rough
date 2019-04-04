package com.example.mvprough1.registersearch;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.mvprough1.R;
import com.example.mvprough1.data.Student;
import com.example.mvprough1.data.StudentDataSource;

import java.util.ArrayList;

public class RegisterSearchView extends AppCompatActivity implements RegisterSearchContract.View {

    private RegisterSearchPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_search_view);


        // Create Data Source
        StudentDataSource dataSource = new StudentDataSource(this);


    }

    @Override
    public void setmPresenter(RegisterSearchPresenter registerSearchPresenter) {
        mPresenter = registerSearchPresenter;
    }

    @Override
    public void showLoadingUI() {

    }

    @Override
    public void hideLoadingUI() {

    }

    @Override
    public void refreshStudenList(ArrayList<Student> students) {

    }

    @Override
    public void showNoStudentUI() {

    }

    @Override
    public void showErr(String msg) {

    }

    @Override
    public void saveSharedPref(String str, String spName, String spKey) {

    }

    @Override
    public void displayStudent(ArrayList<Student> students) {

    }

    @Override
    public void showMsg(String msg) {

    }

    @Override
    public void studentToEdit(Student student) {

    }

    private String getStudentJsonSp() {

        return "";
    }

}
