package com.example.mvprough1.registersearch;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mvprough1.R;
import com.example.mvprough1.data.Student;

import java.util.List;

public class StudentAdapter extends ArrayAdapter<Student> {
    private List<Student> mStudentList;
    private AdapterViewContract.View mViewContract;

    public StudentAdapter(Context context, int resource, List<Student> studentList, AdapterViewContract.View viewContract) {
        super(context, resource, studentList);
        mStudentList = studentList;
        mViewContract = viewContract;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null)
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_item, parent, false);

        final Student student = mStudentList.get(position);

        LinearLayout studentLayout = convertView.findViewById(R.id.student_layout);
        TextView id = convertView.findViewById(R.id.student_id);
        TextView name = convertView.findViewById(R.id.student_name);
        ImageView editStudent = convertView.findViewById(R.id.edit_student);
        ImageView deleteStudent = convertView.findViewById(R.id.delete_student);

        id.setText(String.valueOf(student.getId()));
        name.setText(student.getName());

        studentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mViewContract != null) {
                    mViewContract.onStudentClickItem(student.getId());
                }
            }
        });

        editStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewContract.editStudent(student.getId());
            }
        });

        deleteStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewContract.deleteStudent(student.getId());
            }
        });


        return convertView;
    }

    @Override
    public int getCount() {
        return mStudentList.size();
    }


}
