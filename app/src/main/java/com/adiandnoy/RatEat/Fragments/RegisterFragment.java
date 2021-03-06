package com.adiandnoy.RatEat.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
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

import java.io.FileNotFoundException;
import java.io.InputStream;
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

//        userImage.setImageResource(R.drawable.ic_profile);

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
                Navigation.findNavController(getView()).popBackStack();
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
//                userImage.setImageResource(R.drawable.ic_profile);
                BitmapDrawable drawable = (BitmapDrawable) userImage.getDrawable();
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
                                        FirebaseUser userAuth = mAuth.getCurrentUser();
                                        user.setId(userAuth.getUid());
                                        user.setName(name.getText().toString());
                                        user.setLastName(lastName.getText().toString());
                                        user.setMail(mail.getText().toString());
                                        user.setPassword(password.getText().toString());

                                        Model.instance.addUser(user, new Model.AddUserListener() {
                                            @Override
                                            public void onComplete() {
//                                                Model.instance.getAllUsers();
                                                final NavController navController = Navigation.findNavController(getView());
                                                navController.navigate(R.id.studentListFragment);
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

        if (name.getText().toString().isEmpty()) {
            name.setError("Enter a first name");
            valid = false;
        }

        if (lastName.getText().toString().isEmpty()) {
            name.setError("Enter a last name");
            valid = false;
        }
        if (mail.getText().toString().isEmpty()) {
            mail.setError("Enter a mail");
            valid = false;
        }

        if (password.getText().toString().isEmpty()) {
            password.setError("Enter a password");
            valid = false;
        }

        if(name.getText().toString().length() > 30) {
            name.setError("The entered name is too long");
//            Toast.makeText(getContext(), "The entered first name is too long", Toast.LENGTH_SHORT).show();
            valid = false;
        }

        if(lastName.getText().toString().length() > 30) {
            lastName.setError("The entered last name is too long");
            valid = false;
        }

//        if(name.getText().toString().length() > 30) {
//            Toast.makeText(getContext(), "The entered first name is too long", Toast.LENGTH_SHORT).show();
//            valid = false;
//        }

        if(mail.getText().toString().length() > 100 || !Patterns.EMAIL_ADDRESS.matcher(mail.getText().toString()).matches()) {
            mail.setError("The mail is too long or does not fit to mail pattern");
//            Toast.makeText(getContext(), "The entered Email is wrong", Toast.LENGTH_SHORT).show();
            valid = false;
        }

        if(password.getText().toString().length() < 6) {
            password.setError("The password must contain at least 6 characters");
//            Toast.makeText(getContext(), "The entered password must be longer than 6 characters", Toast.LENGTH_SHORT).show();
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
                        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                        intent.setType("image/*");
                        startActivityForResult(Intent.createChooser(intent,"Choose from Gallery"), 1);

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
                        Bitmap fixedBitmapRotation = RotateBitmap(selectedImage, 90);
                        userImage.setImageBitmap(fixedBitmapRotation);
//                        userImage.setRotation(90);
                    }

                    break;
                case 1:
                    if (resultCode == RESULT_OK && requestCode == 1 && data!= null) {
                        try {
                            InputStream inputStream = getActivity().getContentResolver().openInputStream(data.getData());
                            Bitmap bitmap  = BitmapFactory.decodeStream(inputStream);
                            Bitmap fixedBitmapRotation = RotateBitmap(bitmap, 90);
                            userImage.setImageBitmap(fixedBitmapRotation);
//                            userImage.setRotation(90);
                        }catch (FileNotFoundException e){
                            e.printStackTrace();
                        }

                    }
                    break;
            }
        }
    }


    private Bitmap RotateBitmap(Bitmap bitmap, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }
}