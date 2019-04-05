package com.example.mvprough1.registersearch;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mvprough1.R;
import com.example.mvprough1.data.Student;
import com.example.mvprough1.data.StudentDataSource;

import java.util.ArrayList;

public class RegisterSearchView extends AppCompatActivity implements RegisterSearchContract.View {
    private static final String TAG = "RegisterSearchView";

    private RegisterSearchPresenter mPresenter;
    private Button mRegisterTab;
    private Button mSearchTab;
    private LinearLayout mRegisterLayout;
    private EditText mStudentId;
    private EditText mStudentName;
    private EditText mStudentEmail;
    private Button mRegisterBtn;
    private LinearLayout mSearchLayout;
    private Spinner searchCriteriaSpinner;
    private EditText searchText;
    private Button mSearchBtn;
    private ProgressBar progressBar;
    private ListView studentListView;
    private TextView emptyListView;

    private StudentAdapter studentAdapter;
    private ArrayList<Student> mStudentList;

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
        mRegisterTab = findViewById(R.id.register_tab);
        mSearchTab = findViewById(R.id.search_tab);

        mRegisterLayout = findViewById(R.id.register_layout);
        mStudentId = findViewById(R.id.id_et);
        mStudentName = findViewById(R.id.name_et);
        mStudentEmail = findViewById(R.id.email_et);
        mRegisterBtn = findViewById(R.id.register_btn);

        mSearchLayout = findViewById(R.id.search_layout);
        searchCriteriaSpinner = findViewById(R.id.search_criteria_spinner);
        searchText = findViewById(R.id.search_text_et);
        mSearchBtn = findViewById(R.id.search_btn);

        progressBar = findViewById(R.id.progress_bar);

        studentListView = findViewById(R.id.student_list_view);

        emptyListView = findViewById(R.id.empty_list_view);

        mRegisterTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSearchLayout.setVisibility(View.GONE);
                mRegisterLayout.setVisibility(View.VISIBLE);
            }
        });

        mSearchTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRegisterLayout.setVisibility(View.GONE);
                mSearchLayout.setVisibility(View.VISIBLE);
            }
        });

        mStudentList = new ArrayList<>();
//        mStudentList.add(new Student(1, "Akash kumar", "akash@example.com"));
//        mStudentList.add(new Student(2, "Akash kushwaha", "kushwaha@email.com"));
        studentAdapter = new StudentAdapter(this, R.layout.student_item, mStudentList);

        studentListView.setAdapter(studentAdapter);

        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = Integer.parseInt(mStudentId.getText().toString().trim());
                String name = mStudentName.getText().toString().trim();
                String email = mStudentEmail.getText().toString().trim();

                mPresenter.registerStudent(id, name, email);
            }
        });

        mSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d(TAG, "selected Spinner : " + searchCriteriaSpinner.getSelectedItem().toString());

            }
        });


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
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoadingUI() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void refreshStudentList(ArrayList<Student> students) {
        emptyListView.setVisibility(View.GONE);
        for (Student s : students) {

            int id = s.getId();
            String name = s.getName();
            String email = s.getEmail();

            Log.d(TAG, "refreshStudentList: " + id + "\t" + name + "\t" + email);
        }

        mStudentList.clear();
        mStudentList.addAll(students);
        studentAdapter.notifyDataSetChanged();
        Log.d(TAG, "refreshStudentList: " + studentAdapter.getCount());
    }

    @Override
    public void showNoStudentUI() {
        studentListView.setEmptyView(emptyListView);
        emptyListView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showErr(String msg) {
        Toast toast = Toast.makeText(this, msg, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    @Override
    public void displayStudent(ArrayList<Student> students) {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();

        StringBuilder studentText = new StringBuilder();
        for (Student s : students) {
            studentText
                    .append("ID: ").append(s.getId())
                    .append("\nName: ").append(s.getName())
                    .append("\nEmail: ").append(s.getEmail())
                    .append("\n\n");
        }

        alertDialog.setMessage(studentText);

        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        alertDialog.show();
    }

    @Override
    public void showMsg(String msg) {
        Toast toast = Toast.makeText(this, msg, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    @Override
    public void studentToEdit(Student student) {
        if (mRegisterLayout.getVisibility() != View.VISIBLE) {
            mSearchLayout.setVisibility(View.GONE);
            mRegisterLayout.setVisibility(View.VISIBLE);
        }

        mStudentId.setText(student.getId());
        mStudentName.setText(student.getName());
        mStudentEmail.setText(student.getEmail());
    }

    @Override
    public void resetRegisterFields() {
        mStudentId.setText("");
        mStudentName.setText("");
        mStudentEmail.setText("");
    }
}
