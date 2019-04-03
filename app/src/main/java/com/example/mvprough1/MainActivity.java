package com.example.mvprough1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.mvprough1.registersearch.RegisterSearchContract;
import com.example.mvprough1.registersearch.RegisterSearchPresenter;

public class MainActivity extends AppCompatActivity implements RegisterSearchContract.View {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public String getStudentSp() {
        return null;
    }

    @Override
    public void setPresenter(RegisterSearchPresenter registerSearchPresenter) {

    }
}
