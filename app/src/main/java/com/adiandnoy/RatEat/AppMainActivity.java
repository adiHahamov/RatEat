package com.adiandnoy.RatEat;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.adiandnoy.RatEat.Fragments.TabBarFragment;

public class AppMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_main);
//
//        final TabBarFragment tabBar = new TabBarFragment();
//        FragmentManager manager = getSupportFragmentManager();
//        FragmentTransaction tran = manager.beginTransaction();
//        tran.add(R.id.appmain_frg_container,tabBar);
//        tran.commit();

    }
}