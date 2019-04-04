package com.example.mvprough1.registersearch;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;

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

        init();

        // Create Data Source
        StudentDataSource dataSource = new StudentDataSource(this);
        RegisterSearchPresenter presenter = new RegisterSearchPresenter(dataSource, this);

    }

    private void init() {

        Button mRegisterTab = findViewById(R.id.register_tab);
        Button mSearchTab = findViewById(R.id.search_tab);

        LinearLayout mRegisterLayout = findViewById(R.id.register_layout);
        EditText mStudentId = findViewById(R.id.id_et);
        EditText mStudentName = findViewById(R.id.name_et);
        EditText mEmailId = findViewById(R.id.email_et);
        Button mRegisterBtn = findViewById(R.id.register_btn);

        LinearLayout mSearchLayout = findViewById(R.id.search_layout);
        Spinner searchCriteriaSpinner = findViewById(R.id.search_criteria_spinner);
        EditText searchText = findViewById(R.id.search_text_et);
        Button searchBtn = findViewById(R.id.search_btn);

        ListView studentListView = findViewById(R.id.student_list_view);


    }

    @Override
    public void setPresenter(RegisterSearchPresenter registerSearchPresenter) {
        mPresenter = registerSearchPresenter;
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.start();
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
