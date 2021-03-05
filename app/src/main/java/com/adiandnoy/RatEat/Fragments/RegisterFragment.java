package com.adiandnoy.RatEat.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
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

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
public class RegisterFragment extends Fragment {

    private FirebaseAuth mAuth;
    private static final String TAG = "EmailPassword";

    ImageButton backBtn;
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

        backBtn = view.findViewById(R.id.backBtnFromRegister);
        name =  view.findViewById(R.id.input_first_name);
        lastName =  view.findViewById(R.id.input_last_name);
        mail =  view.findViewById(R.id.input_email);
        password =  view.findViewById(R.id.input_password);
        saveUserButton = view.findViewById(R.id.save_user_btn);
        userImage = view.findViewById(R.id.input_profile_img);
        editImage = view.findViewById(R.id.editImageRegisterFragment);

        userImage.setImageResource(R.drawable.profile);

        mAuth = FirebaseAuth.getInstance();
        name.requestFocus();
        editImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editImage();
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final NavController navController = Navigation.findNavController(getView());
                navController.navigate(R.id.action_registerFragment_to_SignInFragment);
            }
        });

        saveUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!validateForm()) {
                    return;
                }

                User user = new User();

                //Save the image
                BitmapDrawable drawable = (BitmapDrawable)userImage.getDrawable();
                Bitmap bitmap = drawable.getBitmap();

                Model.instance.uploadImage(bitmap, UUID.randomUUID().toString(), new Model.uploadImageListener() {
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
                                        FirebaseUser userAuth = mAuth.getCurrentUser();
                                        user.setId(userAuth.getUid());
                                        user.setName(name.getText().toString());
                                        user.setLastName(lastName.getText().toString());
                                        user.setMail(mail.getText().toString());
                                        user.setPassword(password.getText().toString());

                                        Model.instance.addUser(user, new Model.AddUserListener() {
                                            @Override
                                            public void onComplete() {
                                            }
                                        });
                                        Toast.makeText(getActivity(), "User has been registered successfully!", Toast.LENGTH_LONG).show();
                                    }
                                    else{
                                        Toast.makeText(getContext(), "Failed to signUp! please check your credentials", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

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

        return valid;
    }

    static final int REQUEST_IMAGE_CAPTURE = 1;
//    private void editImage() {
        private void editImage() {
            final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("Choose your profile picture");

            builder.setItems(options, new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int item) {

                    if (options[item].equals("Take Photo")) {
                        Intent takePicture = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(takePicture, 0);

                    } else if (options[item].equals("Choose from Gallery")) {
                        Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(pickPhoto, 1);

                    } else if (options[item].equals("Cancel")) {
                        dialog.dismiss();
                    }
                }
            });
            builder.show();
        }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_CANCELED) {
            switch (requestCode) {
                case 0:
                    if (resultCode == RESULT_OK && data != null) {
                        Bitmap selectedImage = (Bitmap) data.getExtras().get("data");
                        userImage.setImageBitmap(selectedImage);
                    }

                    break;
                case 1:
                    if (resultCode == RESULT_OK && data != null) {
                        Uri selectedImage = data.getData();
                        String[] filePathColumn = {MediaStore.Images.Media.DATA};
                        if (selectedImage != null) {
                            Cursor cursor = getActivity().getContentResolver().query(selectedImage,
                                    filePathColumn, null, null, null);
                            if (cursor != null) {
                                cursor.moveToFirst();
                                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                                String picturePath = cursor.getString(columnIndex);
                                userImage.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                                cursor.close();
                            }
                        }

                    }
                    break;
            }
        }
    }
}