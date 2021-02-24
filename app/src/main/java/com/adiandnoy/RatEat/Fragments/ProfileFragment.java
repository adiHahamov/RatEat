package com.adiandnoy.RatEat.Fragments;

import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.adiandnoy.RatEat.R;

public class ProfileFragment extends Fragment {

    Button dtls_btn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        dtls_btn = view.findViewById(R.id.my_dtls_btn);

        dtls_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDetailsFragment dtls_fragment = new MyDetailsFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.nav_fragment, dtls_fragment);
                transaction.commit();
            }
        });

        return view;
    }
}