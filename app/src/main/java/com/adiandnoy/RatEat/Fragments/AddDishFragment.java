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
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.adiandnoy.RatEat.R;
import com.adiandnoy.RatEat.model.Dish;
import com.adiandnoy.RatEat.model.Model;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.UUID;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

public class AddDishFragment extends Fragment {

    private FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
    TextInputEditText name;
    TextInputEditText description;
    TextInputEditText resturant;
    TextInputEditText ingredient;
    ImageButton editImage;
    ImageView dishImage;
    RatingBar dishStars;
    Boolean photoSelected = false;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
        final NavController navController = Navigation.findNavController(view);
        if(currentUser != null) {
            showData();
        }
        else{
            Toast.makeText(getContext(), "Not allowed! sign in first", Toast.LENGTH_LONG).show();
            navController.navigate(R.id.tabBarFragment);
        }
    }

    private void showData() {
        Button addDish = getView().findViewById(R.id.save_dish_buttom);
//        ImageButton backBtn = getView().findViewById(R.id.backBtnFromAdd);
//        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        String uid = currentUser.getUid();

        name = getView().findViewById(R.id.input_dish_name);
        name.requestFocus();
        description = getView().findViewById(R.id.input_dish_description);
        resturant = getView().findViewById(R.id.input_resturant_name);
        ingredient = getView().findViewById(R.id.input_dish_ingredient);
        editImage = getView().findViewById(R.id.editImageAddDishFragment);
        dishImage = getView().findViewById(R.id.import_image_add_dish_fragment);

        dishStars = getView().findViewById(R.id.dishRatingBar);

        editImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editImage();
            }
        });

        addDish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                editImage();
                if (!validateInput()) return;

                Dish dish = new Dish();
                dish.setId(UUID.randomUUID().toString());
                dish.setDishName(name.getText().toString());
                dish.setDishDescription(description.getText().toString());
                dish.setResturantName(resturant.getText().toString());
                dish.setIngredients(ingredient.getText().toString());
                dish.setUserID(uid);
                dish.setDeleted(false);
                float f  = dishStars.getRating();
                double d = (double) f;
                dish.setStars(d);

                //Save the image
                BitmapDrawable drawable = (BitmapDrawable) dishImage.getDrawable();
                Bitmap bitmap = drawable.getBitmap();

                Model.instance.uploadImage(bitmap, dish.getId(), new Model.uploadImageListener() {
                    @Override
                    public void onComplete(String url) {
                        if (url == null) {
                            displayFailedError();

                        } else {
                            dish.setImageUrl(url);
                            Model.instance.addDish(dish, new Model.AddDisheListener() {
                                @Override
                                public void onComplete() {
                                    final NavController navController = Navigation.findNavController(getView());
                                    navController.navigate(R.id.studentListFragment);
//                                    Navigation.findNavController(addDish)
//                                    Navigation.findNavController(view).navigate(R.id.action_registerFragment_to_studentListFragment);
                                }
                            });
                        }
                    }
                });
            }
        });
    }

    private boolean validateInput(){
        boolean valid = true;

        if(name.getText().toString().isEmpty()){
            valid = false;
            name.setError("Enter a name");
        }

        if(description.getText().toString().isEmpty()){
            valid = false;
            description.setError("Enter a description");
        }

        if(resturant.getText().toString().isEmpty()){
            valid = false;
            resturant.setError("Enter a restaurant");
        }

        if(ingredient.getText().toString().isEmpty()){
            valid = false;
            ingredient.setError("Enter the ingredients");
        }

        return valid;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_dish, container, false);
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

    private void editImage() {
        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Choose your dish picture");

        builder.setItems(options, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (options[item].equals("Take Photo")) {
                    Intent takePicture = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(takePicture, 0);

                } else if (options[item].equals("Choose from Gallery")) {
//                    Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
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
                        dishImage.setImageBitmap(fixedBitmapRotation);
//                        dishImage.setRotation(90);
                        photoSelected = true;
                    }

                    break;
                case 1:
                    if (resultCode == RESULT_OK && requestCode == 1 && data!= null) {
                        try {
                            InputStream inputStream = getActivity().getContentResolver().openInputStream(data.getData());
                            Bitmap bitmap  = BitmapFactory.decodeStream(inputStream);
                            Bitmap fixedBitmapRotation = RotateBitmap(bitmap, 90);
                            dishImage.setImageBitmap(fixedBitmapRotation);
//                            dishImage.setRotation(90);
                            photoSelected = true;
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