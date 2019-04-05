package com.example.mvprough1.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;

import com.example.mvprough1.util.Constant;
import com.example.mvprough1.util.Validator;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class StudentDataSource implements StudentInterface {
    private static final String TAG = "StudentDataSource";

    private String mStudentJsonStr;
    private ArrayList<Student> mStudents;
    private Context mContext;

    public StudentDataSource(Context context) {
        this.mContext = context;
        this.mStudentJsonStr = getStudentSp();
        this.mStudents = extractStudentJson(this.mStudentJsonStr);
    }

    @Override
    public boolean registerStudent(int id, String name, String email) {

        if (searchStudentById(id) != null)
            return false;
        if (searchStudentByEmail(email) != null)
            return false;
        if (!Validator.isValidEmail(email))
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
            if (s.getEmail().equalsIgnoreCase(email)) {
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
                if (!Validator.isValidEmail(email))
                    return false;

                student.setName(name);
                student.setEmail(email);

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

            Log.d(TAG, "extractStudentJson: " + jsonStr);
            JSONObject rootJo = new JSONObject(jsonStr);

            JSONArray studentJa = rootJo.getJSONArray("students");

            Log.d(TAG, "extractStudentJson: " + studentJa.toString());

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
        if (mContext != null) {

            Gson gson = new GsonBuilder().create();
            JsonArray studentJa = gson.toJsonTree(mStudents).getAsJsonArray();

            JsonObject rootJo = new JsonObject();

            rootJo.add("students", studentJa.getAsJsonArray());

            mStudentJsonStr = rootJo.toString();

            SharedPreferences sharedPreferences = mContext.getSharedPreferences(Constant.SP_NAME, Context.MODE_PRIVATE);
            SharedPreferences.Editor edit = sharedPreferences.edit();
            edit.putString(Constant.SP_KEY, mStudentJsonStr);
            edit.apply();

        }
    }

    private String getStudentSp() {

        SharedPreferences sharedPreferences = mContext.getSharedPreferences(Constant.SP_NAME, Context.MODE_PRIVATE);

        return sharedPreferences.getString(Constant.SP_KEY, "{\"students\":[]}");
    }
}
