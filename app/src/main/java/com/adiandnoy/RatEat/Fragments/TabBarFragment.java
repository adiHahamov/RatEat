package com.adiandnoy.RatEat.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.adiandnoy.RatEat.R;

public class TabBarFragment extends Fragment implements View.OnClickListener {
    Fragment[] tabs = new Fragment[3];
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_tab_bar, container, false);
        Button students = view.findViewById(R.id.tabfrag_students_btn);
        Button info = view.findViewById(R.id.tabfrag_info_btn);
        Button add = view.findViewById(R.id.tabfrag_add_btn);

        students.setTag(0);
        info.setTag(1);
        add.setTag(2);

        students.setOnClickListener(this);
        info.setOnClickListener(this);
        add.setOnClickListener(this);

        tabs[0] = new com.adiandnoy.RatEat.Fragments.StudentListFragment();
        tabs[1] = new com.adiandnoy.RatEat.Fragments.StudentInfoFragment();
        tabs[2] = new com.adiandnoy.RatEat.Fragments.AddStudentFragment();
        return view;
    }

    @Override
    public void onClick(View view) {
        int selected = (int)view.getTag();
        FragmentManager manager = getFragmentManager();
        FragmentTransaction tran = manager.beginTransaction();
        tran.replace(R.id.tabfrag_container,tabs[selected]);
        tran.commit();
    }
}