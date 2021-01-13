package com.adiandnoy.RatEat.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.adiandnoy.RatEat.R;
import com.adiandnoy.RatEat.adapters.StudentsAdapter;
import com.adiandnoy.RatEat.model.Model;
import com.adiandnoy.RatEat.model.Student;

import java.util.List;

public class StudentListFragment extends Fragment {

    public StudentListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_student_list, container, false);
        RecyclerView rv = view.findViewById(R.id.studentlistfrag_list);

        rv.hasFixedSize();

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rv.setLayoutManager(layoutManager);

        List<Student> data = Model.instance.getAllStudents();

        StudentsAdapter adapter = new StudentsAdapter(getLayoutInflater());
        adapter.data = data;
        rv.setAdapter(adapter);

        adapter.setOnClickListener(new StudentsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Log.d("TAG","row was clicked " + position);

            }
        });

        return view;
    }

}