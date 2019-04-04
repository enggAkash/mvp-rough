package com.example.mvprough1.registersearch;

import com.example.mvprough1.data.Student;
import com.example.mvprough1.data.StudentDataSource;
import com.example.mvprough1.util.Validator;

import java.util.ArrayList;

public class RegisterSearchPresenter implements RegisterSearchContract.Presenter {


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
            mStudentView.refreshStudenList(allStudent);
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
        mStudentDataSource.deleteStudent(id);
        mStudentView.showMsg("Student Deleted Successfully");
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

        Student studentByEmail = mStudentDataSource.searchStudentByEmail(email);

        if (studentByEmail != null) {
            mStudentView.showErr("Student with this Email already exist");
            return;
        }

        if (!mStudentDataSource.registerStudent(id, name, email)) {
            mStudentView.showErr("Failed to Register Student");
            return;
        }

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
        Student studentById = mStudentDataSource.searchStudentById(id);

        if (!Validator.isValidEmail(email)) {
            mStudentView.showErr("Invalid Email");
            return;
        }

        Student studentByEmail = mStudentDataSource.searchStudentByEmail(email);

        if (studentByEmail != null && studentByEmail.getId() != id) {
            mStudentView.showErr("Student with this Email already exist");
            return;
        }

        if (!mStudentDataSource.registerStudent(id, name, email)) {
            mStudentView.showErr("Failed to Register Student");
            return;
        }

        showStudentList();
    }
}
