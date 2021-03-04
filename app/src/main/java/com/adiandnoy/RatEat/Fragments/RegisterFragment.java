package com.adiandnoy.RatEat.Fragments;

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
//        mail.getText().toString();
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
//                user.setName(name.getText().toString());
//                user.setLastName(lastName.getText().toString());
//                user.setMail(mail.getText().toString());
//                user.setPassword(password.getText().toString());

                //Save the image
                BitmapDrawable drawable = (BitmapDrawable)userImage.getDrawable();
                Bitmap bitmap = drawable.getBitmap();

                Model.instance.uploadImage(bitmap, user.getId(), new Model.uploadImageListener() {
                    @Override
                    public void onComplete(String url) {
                        if (url == null){

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
//                                        User user = new User();
                                         user.setName(name.getText().toString());
                                         user.setLastName(lastName.getText().toString());
                                         user.setMail(mail.getText().toString());
                                         user.setPassword(password.getText().toString());

                                        Model.instance.addUser(user, new Model.AddUserListener() {
                                            @Override
                                            public void onComplete() {
//                                                final NavController navController = Navigation.findNavController(view);
//                                                navController.navigate(R.id.signInFragment);
                                            }
                                        });

//                                        FirebaseUser currentUser = mAuth.getCurrentUser();
                                    }
                                    else{
                                        Toast.makeText(getContext(), "Failed to signUp! please check your credentials", Toast.LENGTH_SHORT).show();
//                                        progressBar.setVisibility(View.INVISIBLE);
//                                        signUpBtn.setVisibility(View.VISIBLE);
                                    }
                                }
                            });
//                            Model.instance.addUser(user,new Model.AddUserListener() {
//                                @Override
//                                public void onComplete() {
//
//                                }
//                            });
                        }
                    }
                });
            }
        });

        return view;
    }

//    @Override
//    public void onStart() {
//        super.onStart();
//        // Check if user is signed in (non-null) and update UI accordingly.
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//        if(currentUser != null){
//            currentUser.reload();
//        }
//    }

//    private void createAccount(String email, String password) {
//        Log.d(TAG, "createAccount:" + email);
//        if (!validateForm()) {
//            return;
//        }

//        showProgressBar();

        // [START create_user_with_email]
/*        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener((Executor) this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
//                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(getActivity(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
//                            updateUI(null);
                        }

                        // [START_EXCLUDE]
//                        hideProgressBar();
                        // [END_EXCLUDE]
                    }
                });*/

//        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//            @Override
//            public void onComplete(@NonNull Task<AuthResult> task) {
//                if (task.isSuccessful()) {
//                    // Sign in success, update UI with the signed-in user's information
//                    Log.d(TAG, "createUserWithEmail:success");
//                    FirebaseUser user = mAuth.getCurrentUser();
////                            updateUI(user);
//                } else {
//                    // If sign in fails, display a message to the user.
//                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
//                    Toast.makeText(getActivity(), "Authentication failed.",
//                            Toast.LENGTH_SHORT).show();
////                            updateUI(null);
//                }
//
//                // [START_EXCLUDE]
////                        hideProgressBar();
//                // [END_EXCLUDE]
//            }
//        });
        // [END create_user_with_email]
//    }

    private boolean validateForm() {
        boolean valid = true;

        String email = mail.getText().toString();
        if (TextUtils.isEmpty(email)) {
            mail.setError("Required.");
            valid = false;
        } else {
            mail.setError(null);
        }

        String convPassword = password.getText().toString();
        if (TextUtils.isEmpty(convPassword)) {
            password.setError("Required.");
            valid = false;
        } else {
            password.setError(null);
        }

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