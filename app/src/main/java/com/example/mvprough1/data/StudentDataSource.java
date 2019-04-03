package com.example.mvprough1.data;

import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class StudentDataSource implements StudentInterface {

    private String mStudentJsonStr;
    private SaveSp mSaveSpInterface;
    private ArrayList<Student> mStudents;

    public StudentDataSource(String studentJson, StudentInterface.SaveSp saveSpInterface) {
        this.mStudentJsonStr = studentJson;
        this.mSaveSpInterface = saveSpInterface;
        this.mStudents = extractStudentJson(this.mStudentJsonStr);
    }

    @Override
    public boolean registerStudent(int id, String name, String email) {

        if (searchStudentById(id) != null)
            return false;
        if (searchStudentByEmail(email) != null)
            return false;
        if (!isValidEmail(email))
            return false;

        mStudents.add(new Student(id, name, email));

        saveStudentJsonStr();

        return true;
    }

    @Override
    public Student searchStudentById(int id) {
        Student student = null;

        for (Student s : mStudents) {
            if (s.getId() == id) {
                student = s;
                break;
            }
        }

        return student;
    }

    @Override
    public ArrayList<Student> searchStudentByName(String name) {
        ArrayList<Student> students = new ArrayList<>();

        for (Student s : mStudents) {
            if (s.getName().equalsIgnoreCase(name)) {
                students.add(s);
                break;
            }
        }

        return students;
    }

    @Override
    public Student searchStudentByEmail(String email) {
        Student student = null;

        for (Student s : mStudents) {
            if (s.getEmai().equalsIgnoreCase(email)) {
                student = s;
                break;
            }
        }

        return student;
    }

    @Override
    public void deleteStudent(int id) {

        for (Student s : mStudents) {
            if (s.getId() == id) {
                mStudents.remove(s);
                break;
            }
        }

        saveStudentJsonStr();
    }

    @Override
    public ArrayList<Student> getAllStudent() {
        return mStudents;
    }

    @Override
    public boolean editStudent(int id, String name, String email) {
        for (int i = 0; i < mStudents.size(); i++) {
            Student student = mStudents.get(i);

            if (student.getId() == id) {

                if (searchStudentByEmail(email) != null)
                    return false;
                if (!isValidEmail(email))
                    return false;

                student.setName(name);
                student.setEmai(email);

                mStudents.set(i, student);

                saveStudentJsonStr();
                return true;
            }
        }

        return false;
    }

    private ArrayList<Student> extractStudentJson(String jsonStr) {
        ArrayList<Student> students = new ArrayList<>();

        if (TextUtils.isEmpty(jsonStr))
            mStudentJsonStr = jsonStr = "{\"student\":[]}";

        try {
            JSONObject rootJo = new JSONObject(jsonStr);

            JSONArray studentJa = rootJo.getJSONArray("students");

            for (int i = 0; i < studentJa.length(); i++) {
                JSONObject jo = studentJa.getJSONObject(i);

                Student s = new Student(jo.getInt("id"), jo.getString("name"), jo.getString("email"));
                students.add(s);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return students;
    }

    private void saveStudentJsonStr() {
        if (mSaveSpInterface != null) {

            JSONArray studentJa = new JSONArray(mStudents);

            JSONObject rootJo = new JSONObject();

            try {
                rootJo.put("students", studentJa);

                mStudentJsonStr = rootJo.toString();

                mSaveSpInterface.saveStudentSp(mStudentJsonStr);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean isValidEmail(String email) {

        String emailRegex = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";

        return email.matches(emailRegex);
    }
}
