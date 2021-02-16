package com.adiandnoy.RatEat.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;

import com.adiandnoy.RatEat.R;
import com.adiandnoy.RatEat.model.Dish;
import com.adiandnoy.RatEat.model.Model;

public class SignInFragment extends Fragment{
//    Fragment[] tabs = new Fragment[3];
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_tab_bar, container, false);
        Button sign_in = view.findViewById(R.id.sign_in);

//        Button info = view.findViewById(R.id.tabfrag_info_btn);
//        Button add = view.findViewById(R.id.tabfrag_add_btn);

//        students.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Dish dish = new Dish();
//                dish.setId("2");
//                Model.instance.addDish(dish,new Model.AddDisheListener() {
//                    @Override
//                    public void onComplete() {
//                        int i;
//                    }
//                });
//            }
//        });
//
//        info.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Navigation.findNavController(view).navigate(R.id.action_tabBarFragment_to_studentInfoFragment);
//            }
//        });
//
//        add.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Navigation.findNavController(view).navigate(R.id.action_tabBarFragment_to_addStudentFragment);
//            }
//        });

//        students.setTag(0);
//        info.setTag(1);
//        add.setTag(2);
//
//        students.setOnClickListener(this);
//        info.setOnClickListener(this);
//        add.setOnClickListener(this);
//
//        tabs[0] = new com.adiandnoy.RatEat.Fragments.StudentListFragment();
//        tabs[1] = new com.adiandnoy.RatEat.Fragments.StudentInfoFragment();
//        tabs[2] = new com.adiandnoy.RatEat.Fragments.AddStudentFragment();
        return view;
    }

//    @Override
//    public void onClick(View view) {
//        int selected = (int)view.getTag();
//        FragmentManager manager = getFragmentManager();
//        FragmentTransaction tran = manager.beginTransaction();
//        tran.replace(R.id.tabfrag_container,tabs[selected]);
//        tran.commit();
//    }
}