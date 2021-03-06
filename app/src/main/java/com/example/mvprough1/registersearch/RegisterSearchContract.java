package com.example.mvprough1.registersearch;

import com.example.mvprough1.BasePresenter;
import com.example.mvprough1.BaseView;
import com.example.mvprough1.data.Student;

import java.util.ArrayList;

public interface RegisterSearchContract {

    interface View extends BaseView<RegisterSearchPresenter> {

        void showLoadingUI();

        void hideLoadingUI();

        void refreshStudentList(ArrayList<Student> students);

        void showNoStudentUI();

        void showErr(String msg);

        void displayStudent(ArrayList<Student> students);

        void showMsg(String msg);

        void studentToEdit(Student student);

        void resetRegisterFields();

    }

    interface Presenter extends BasePresenter {

        void showStudentList();

        void getStudent(int id);

        void deleteStudent(int id);

        void registerStudent(int id, String name, String email);

        void searchById(int id);

        void searchByName(String name);

        void editStudent(int id);

        void updateStudent(int id, String name, String email);

        void searchStudent(String searchCriteria, String searchBy);
    }

}
