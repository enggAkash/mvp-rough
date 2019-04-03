package com.example.mvprough1.data;

import java.util.ArrayList;

public interface StudentInterface {

    boolean registerStudent(int id, String name, String email);

    Student searchStudentById(int id);

    ArrayList<Student> searchStudentByName(String name);

    Student searchStudentByEmail(String email);

    void deleteStudent(int id);

    ArrayList<Student> getAllStudent();

    boolean editStudent(int id, String name, String email);

    interface SaveSp {
        void saveStudentSp(String studentJsonStr);
    }

}
