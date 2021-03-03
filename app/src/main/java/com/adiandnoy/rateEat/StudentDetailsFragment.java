package com.adiandnoy.rateEat;

import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.adiandnoy.rateEat.model.Model;
import com.adiandnoy.rateEat.model.Student;
import com.squareup.picasso.Picasso;


public class StudentDetailsFragment extends AddStudentFragment {
    Student student;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = super.onCreateView( inflater,  container,
                 savedInstanceState);

        // setup all student info in the display
        editImage.setVisibility(View.INVISIBLE);
        idEt.setEnabled(false);
        nameEt.setEnabled(false);
        saveBtn.setVisibility(View.INVISIBLE);
        cancelBtn.setVisibility(view.INVISIBLE);

        final String studentId = StudentDetailsFragmentArgs.fromBundle(getArguments()).getStudentId();
        Log.d("studentId:",studentId);

        Model.instance.getStudent(studentId, new Model.GetStudentListener() {
            @Override
            public void onComplete(Student st) {
                student = st;
                idEt.setText(student.getId());
                nameEt.setText(student.getName());
                if (st.getImageUrl() != null){
                    Picasso.get().load(st.getImageUrl()).placeholder(R.drawable.avatar).into(avatarImageView);
                }
            }
        });
        return view;

    }
}