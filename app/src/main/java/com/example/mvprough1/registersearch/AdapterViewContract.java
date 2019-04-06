package com.example.mvprough1.registersearch;

public interface AdapterViewContract {


    interface View {

        void onStudentClickItem(int id);

        void deleteStudent(int id);

        void editStudent(int id);

    }


}
