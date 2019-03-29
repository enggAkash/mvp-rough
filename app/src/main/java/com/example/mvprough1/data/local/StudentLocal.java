package com.example.mvprough1.data.local;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.mvprough1.data.Student;
import com.example.mvprough1.data.StudentInterface;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class StudentLocal implements StudentInterface {

    private SharedPreferences studentSp;

    private JSONArray studentJA;

    public StudentLocal(Context context) {
        studentSp = context.getSharedPreferences("student_sp", Context.MODE_PRIVATE);

        String studentJo = studentSp.getString("student_jo", "{\"student\"=[]}");
        studentJA = getStudentsFromStr(studentJo);
    }

    private JSONArray getStudentsFromStr(String jsonString) {
        JSONArray studentJa = new JSONArray();

        try {
            JSONObject rootJo = new JSONObject(jsonString);

            studentJa = rootJo.getJSONArray("students");

            /*for (int i = 0, length = studentJa.length(); i < length; i++) {

                JSONObject jo = new JSONObject();
                int id = jo.getInt("id");
                String name = jo.getString("name");
                String email = jo.getString("email");

                Student s = new Student(id, name, email);
                tempStudent.put(id, s);
            }*/

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return studentJa;
    }

    @Override
    public boolean registerStudent(int id, String name, String email) {
        return false;
    }

    @Override
    public Student searchStudentById(int studentId) {
        Student student = null;

        try {

            for (int i = 0, length = studentJA.length(); i < length; i++) {

                JSONObject jo = new JSONObject();

                int id = jo.getInt("id");
                String name = jo.getString("name");
                String email = jo.getString("email");

                Student s = new Student(id, name, email);

                if (s.getId() == studentId) {
                    student = s;
                    break;
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return student;
    }

    @Override
    public ArrayList<Student> searchStudentByName(String studentName) {
        ArrayList<Student> studentArrayList = new ArrayList<>();

        try {

            for (int i = 0, length = studentJA.length(); i < length; i++) {

                JSONObject jo = new JSONObject();

                int id = jo.getInt("id");
                String name = jo.getString("name");
                String email = jo.getString("email");

                Student s = new Student(id, name, email);

                if (s.getName().equalsIgnoreCase(studentName))
                    studentArrayList.add(s);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return studentArrayList;
    }

    @Override
    public Student searchStudentByEmail(String studentEmail) {
        Student student = null;

        try {

            for (int i = 0, length = studentJA.length(); i < length; i++) {

                JSONObject jo = new JSONObject();

                int id = jo.getInt("id");
                String name = jo.getString("name");
                String email = jo.getString("email");

                Student s = new Student(id, name, email);

                if (s.getEmai().equalsIgnoreCase(studentEmail)) {
                    student = s;
                    break;
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return student;
    }

    @Override
    public boolean deleteStudent(int id) {
        return true;
    }

    @Override
    public ArrayList<Student> getAllStudent() {
        return null;
    }

}
