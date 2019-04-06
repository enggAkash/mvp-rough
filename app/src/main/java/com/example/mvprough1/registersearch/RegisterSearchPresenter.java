package com.example.mvprough1.registersearch;

import android.text.TextUtils;
import android.util.Log;

import com.example.mvprough1.data.Student;
import com.example.mvprough1.data.StudentDataSource;
import com.example.mvprough1.util.Validator;

import java.util.ArrayList;

public class RegisterSearchPresenter implements RegisterSearchContract.Presenter {
    private static final String TAG = "RegisterSearchPresenter";

    private StudentDataSource mStudentDataSource;
    private RegisterSearchContract.View mStudentView;

    public RegisterSearchPresenter(StudentDataSource studentDataSource, RegisterSearchContract.View studentView) {
        mStudentDataSource = studentDataSource;
        mStudentView = studentView;

        mStudentView.setPresenter(this);
    }

    @Override
    public void start() {
        mStudentView.showLoadingUI();
        showStudentList();
        mStudentView.hideLoadingUI();
    }

    @Override
    public void showStudentList() {
        ArrayList<Student> allStudent = mStudentDataSource.getAllStudent();

        if (allStudent.isEmpty())
            mStudentView.showNoStudentUI();
        else
            mStudentView.refreshStudentList(allStudent);
    }

    @Override
    public void getStudent(int id) {
        Student student = mStudentDataSource.searchStudentById(id);

        if (student == null)
            mStudentView.showErr("No Student Found with this ID");
        else {

            ArrayList<Student> students = new ArrayList<Student>();
            students.add(student);

            mStudentView.displayStudent(students);
        }
    }

    @Override
    public void deleteStudent(int id) {
        if (mStudentDataSource.searchStudentById(id) == null) {
            mStudentView.showErr("Student does not exist with ID: " + id);
            return;
        }

        mStudentDataSource.deleteStudent(id);
        mStudentView.showMsg("Student Deleted Successfully");

        showStudentList();
    }

    @Override
    public void registerStudent(int id, String name, String email) {

        Student studentById = mStudentDataSource.searchStudentById(id);

        if (studentById != null) {
            mStudentView.showErr("Student with this ID is already exist");
            return;
        }

        if (!Validator.isValidEmail(email)) {
            mStudentView.showErr("Invalid Email");
            return;
        }

        ArrayList<Student> studentByEmailList = mStudentDataSource.searchStudentByEmail(email);

        for (Student s : studentByEmailList) {
            if (s.getEmail().equalsIgnoreCase(email)) {
                mStudentView.showErr("Student with this Email already exist");
                return;
            }
        }

        if (!mStudentDataSource.registerStudent(id, name, email)) {
            mStudentView.showErr("Failed to Register Student");
            return;
        }

        mStudentView.resetRegisterFields();

        showStudentList();
    }

    @Override
    public void searchById(int id) {

        Student student = mStudentDataSource.searchStudentById(id);

        if (student == null) {
            mStudentView.showErr("Student with this ID is does not exist");
            return;
        }

        ArrayList<Student> students = new ArrayList<Student>();
        students.add(student);

        mStudentView.displayStudent(students);
    }

    @Override
    public void searchByName(String name) {

        ArrayList<Student> students = mStudentDataSource.searchStudentByName(name);

        if (students.isEmpty()) {
            mStudentView.showErr("No Student Found with this Name");
            return;
        }

        mStudentView.displayStudent(students);
    }

    @Override
    public void editStudent(int id) {

        Student student = mStudentDataSource.searchStudentById(id);

        if (student == null) {
            mStudentView.showErr("No Student Found with this ID");
            return;
        }

        mStudentView.studentToEdit(student);
    }

    @Override
    public void updateStudent(int id, String name, String email) {

        if (!Validator.isValidEmail(email)) {
            mStudentView.showErr("Invalid Email");
            return;
        }

        ArrayList<Student> studentByEmailList = mStudentDataSource.searchStudentByEmail(email);

        for (Student s : studentByEmailList) {
            if (s.getEmail().equalsIgnoreCase(email) && s.getId() != id) {
                mStudentView.showErr("Student with this Email already exist");
                return;
            }
        }

        if (!mStudentDataSource.editStudent(id, name, email)) {
            mStudentView.showErr("Failed to Update Student");
            return;
        }

        mStudentView.showMsg("Student Updated Successfully");

        mStudentView.resetRegisterFields();

        showStudentList();
    }

    @Override
    public void searchStudent(String searchCriteria, String searchBy) {

        if (TextUtils.isEmpty(searchCriteria)) {
            mStudentView.showErr("Enter Search Criteria");
            return;
        }

        switch (searchBy) {
            case "Search By ID":

                if (!Validator.isInt(searchCriteria)) {
                    mStudentView.showErr("ID must be number");
                    return;
                }

                int id = Integer.parseInt(searchCriteria);

                Student student = mStudentDataSource.searchStudentById(id);

                if (student == null) {
                    mStudentView.showMsg("No Data Found with ID: " + searchCriteria);
                    return;
                }

                ArrayList<Student> studentList = new ArrayList<>();
                studentList.add(student);

                mStudentView.displayStudent(studentList);

                break;
            case "Search By Name":

                ArrayList<Student> studentNamedList = mStudentDataSource.searchStudentByName(searchCriteria);
                Log.d("displayStudent", "searchStudent: " + studentNamedList.size());


                if (studentNamedList.isEmpty()) {
                    mStudentView.showMsg("No Data Found with Name: " + searchCriteria);
                    return;
                }

                mStudentView.displayStudent(studentNamedList);

                break;
            case "Search By Email":

                ArrayList<Student> studentEmailList = mStudentDataSource.searchStudentByEmail(searchCriteria);

                if (studentEmailList.isEmpty()) {
                    mStudentView.showMsg("No Data Found with Email: " + searchCriteria);
                    return;
                }

                mStudentView.displayStudent(studentEmailList);

                break;
        }

    }
}
