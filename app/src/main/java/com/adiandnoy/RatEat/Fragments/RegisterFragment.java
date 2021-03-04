package com.adiandnoy.RatEat.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;

import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.adiandnoy.RatEat.AppMainActivity;
import com.adiandnoy.RatEat.R;
import com.adiandnoy.RatEat.model.Dish;
import com.adiandnoy.RatEat.model.Model;
import com.adiandnoy.RatEat.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.Executor;

import static android.app.Activity.RESULT_OK;
public class RegisterFragment extends Fragment {

    private FirebaseAuth mAuth;
    private static final String TAG = "EmailPassword";

    TextInputEditText name;
    TextInputEditText lastName;
    TextInputEditText mail;
    TextInputEditText password;
    ImageButton editImage;
    ImageView userImage;
    Button saveUserButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        name =  view.findViewById(R.id.input_first_name);
        lastName =  view.findViewById(R.id.input_last_name);
        mail =  view.findViewById(R.id.input_email);
        password =  view.findViewById(R.id.input_password);
        saveUserButton = view.findViewById(R.id.save_user_btn);
        userImage = view.findViewById(R.id.input_profile_img);
        editImage = view.findViewById(R.id.editImageRegisterFragment);

        mAuth = FirebaseAuth.getInstance();

        editImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editImage();
            }
        });


        saveUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!validateForm()) {
                    return;
                }

                User user = new User();
                user.setId(UUID.randomUUID().toString());

                //Save the image
                BitmapDrawable drawable = (BitmapDrawable)userImage.getDrawable();
                Bitmap bitmap = drawable.getBitmap();

                Model.instance.uploadImage(bitmap, user.getId(), new Model.uploadImageListener() {
                    @Override
                    public void onComplete(String url) {
                        if (url == null){
                            displayFailedError();
                        }else {
                            user.setImageUrl(url);
                            String email = mail.getText().toString();
                            String convPassword = password.getText().toString();

                            mAuth.createUserWithEmailAndPassword(email, convPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()) {
                                        Log.d("onComplete","done");
//                                        progressBar.setVisibility(View.INVISIBLE);
                                        Toast.makeText(getActivity(), "User has been registered successfully!", Toast.LENGTH_LONG).show();
                                    }
                                    else{
                                        Toast.makeText(getContext(), "Failed to signUp! please check your credentials", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                            user.setName(name.getText().toString());
                            user.setLastName(lastName.getText().toString());
                            user.setMail(mail.getText().toString());
                            user.setPassword(password.getText().toString());

                            Model.instance.addUser(user, new Model.AddUserListener() {
                                @Override
                                public void onComplete() {
                                }
                            });
                            FirebaseUser currentUser = mAuth.getCurrentUser();

                        }
                    }
                });
            }
        });

        return view;
    }
    private void displayFailedError() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Operation Failed");
        builder.setMessage("Saving image failed, please try again later...");
        builder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }
    private boolean validateForm() {
        boolean valid = true;
        String nameS = Objects.requireNonNull(name.getText()).toString();
        String lastNameS = Objects.requireNonNull(lastName.getText()).toString();
        String mailS = Objects.requireNonNull(mail.getText()).toString();
        String passwordS = Objects.requireNonNull(password.getText()).toString();


        if(name == null || TextUtils.isEmpty(nameS) ||
                lastName == null ||  TextUtils.isEmpty(lastNameS) ||
                mail == null || TextUtils.isEmpty(mailS) ||
                password == null || TextUtils.isEmpty(passwordS)) {
            Toast.makeText(getContext(), "Please fill all fields to Sign Up", Toast.LENGTH_SHORT).show();
           valid = false;
        }
        if(nameS.length() > 60) {
            Toast.makeText(getContext(), "The entered User Name is too long", Toast.LENGTH_SHORT).show();
            valid = false;
        }
        if(mailS.length() > 200 || !Patterns.EMAIL_ADDRESS.matcher(mailS).matches()) {
            Toast.makeText(getContext(), "The entered Email is wrong", Toast.LENGTH_SHORT).show();
            valid = false;
        }
        if(passwordS.length() < 6) {
            Toast.makeText(getContext(), "The entered password must be longer than 6 characters", Toast.LENGTH_SHORT).show();
            valid = false;
        }
//        String email = mail.getText().toString();
//        if (TextUtils.isEmpty(email)) {
//            mail.setError("Required.");
//            valid = false;
//        } else {
//            mail.setError(null);
//        }
//
//        String convPassword = password.getText().toString();
//        if (TextUtils.isEmpty(convPassword)) {
//            password.setError("Required.");
//            valid = false;
//        } else {
//            password.setError(null);
//        }

        return valid;
    }

    static final int REQUEST_IMAGE_CAPTURE = 1;
    private void editImage() {
        Intent takePictureIntent = new Intent(
                MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE &&
                resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            userImage.setImageBitmap(imageBitmap);
        }
    }
}