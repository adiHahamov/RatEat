package com.adiandnoy.RatEat.Fragments;

import android.graphics.Paint;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.adiandnoy.RatEat.R;
import com.adiandnoy.RatEat.model.Dish;
import com.adiandnoy.RatEat.model.Model;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class SignInFragment extends Fragment{
    FirebaseAuth mAuth;
    View view;
    TextInputEditText mail;
    TextInputEditText password;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_login, container, false);
        Button sign_in = view.findViewById(R.id.login);

        mail =  view.findViewById(R.id.email);
        password =  view.findViewById(R.id.password);

//        TextInputEditText emailTextView = view.findViewById(R.id.email);
//        TextInputEditText passwordTextView = view.findViewById(R.id.password);
//        String email = Objects.requireNonNull(emailTextView.getText()).toString();
//        String password = Objects.requireNonNull(passwordTextView.getText()).toString();

        TextView register = view.findViewById(R.id.register);
        register.setPaintFlags(register.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signInUser(mail.getText().toString(),password.getText().toString());
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view)
                        .navigate(R.id.action_navBar_to_register);
            }
        });


//        SpannableString content = new SpannableString("Content");
//        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
//        textView.setText(content);

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

    private void signInUser(String mail,String password) {

        boolean isValid = IsUserInputValidate(mail, password);
        if(isValid){
//            progressBar.setVisibility(View.VISIBLE);
//            signInBtn.setVisibility(View.INVISIBLE);
            mAuth.signInWithEmailAndPassword(mail, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
//                        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
//                        if(currentUser.isEmailVerified()){
//                            progressBar.setVisibility(View.INVISIBLE);
                            final NavController navController = Navigation.findNavController(getView());
                        navController.navigate(R.id.studentListFragment);
//                            navController.navigate(R.id.action_tabBarFragment_to_dishesListFragment);
//                        }
//                        else{
//                            progressBar.setVisibility(View.INVISIBLE);
//                            signInBtn.setVisibility(View.VISIBLE);
//                            currentUser.sendEmailVerification();
//                            Toast.makeText(getContext(), "Check your email to verify your account!", Toast.LENGTH_SHORT).show();
//                        }
                    }
                    else{
//                        progressBar.setVisibility(View.INVISIBLE);
//                        signInBtn.setVisibility(View.VISIBLE);
                        Toast.makeText(getContext(), "Failed to login! please check your credentials", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private boolean IsUserInputValidate(String email, String password) {
        if(email == null || email.isEmpty() ||
                password == null || password.isEmpty()){
            Toast.makeText(getContext(), "Please fill all fields to Sign In", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Toast.makeText(getContext(), "The entered Email is wrong", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(password.length() < 6){
            Toast.makeText(getContext(), "The entered password must be longer than 6 characters", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}