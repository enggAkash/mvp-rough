package com.example.mvprough1.data;

import java.util.ArrayList;

public class StudentRegister implements StudentInterface {



    @Override
    public boolean registerStudent(int id, String name, String email) {
        return false;
    }

    @Override
    public ArrayList<Student> searchStudentById(int id) {
        return null;
    }

    @Override
    public ArrayList<Student> searchStudentByName(String name) {
        return null;
    }

    @Override
    public ArrayList<Student> searchStudentByEmail(String email) {
        return null;
    }

    @Override
    public void deleteStudent(int id) {

    }

    @Override
    public ArrayList<Student> getAllStudent() {
        return null;
    }

    @Override
    public Student getStudent(int id) {
        return null;
    }
}
