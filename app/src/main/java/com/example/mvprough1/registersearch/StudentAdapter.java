package com.example.mvprough1.registersearch;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mvprough1.R;
import com.example.mvprough1.data.Student;

import java.util.List;

public class StudentAdapter extends ArrayAdapter<Student> {
    private List<Student> mStudentList;

    public StudentAdapter(Context context, int resource, List<Student> studentList) {
        super(context, resource, studentList);
        mStudentList = studentList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null)
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_item, parent, false);

        Student student = mStudentList.get(position);

        TextView id = convertView.findViewById(R.id.student_id);
        TextView name = convertView.findViewById(R.id.student_name);
        ImageView editStudent = convertView.findViewById(R.id.edit_student);
        ImageView deleteStudent = convertView.findViewById(R.id.delete_student);

        id.setText(String.valueOf(student.getId()));
        name.setText(student.getName());

        return convertView;
    }

    @Override
    public int getCount() {
        return mStudentList.size();
    }


}
