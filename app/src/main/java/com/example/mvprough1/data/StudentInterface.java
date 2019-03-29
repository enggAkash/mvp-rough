package com.example.mvprough1.data;

import java.util.ArrayList;

public interface StudentInterface {

    boolean registerStudent(int id, String name, String email);

    Student searchStudentById(int id);

    ArrayList<Student> searchStudentByName(String name);

    Student searchStudentByEmail(String email);

    boolean deleteStudent(int id);

    ArrayList<Student> getAllStudent();

}
