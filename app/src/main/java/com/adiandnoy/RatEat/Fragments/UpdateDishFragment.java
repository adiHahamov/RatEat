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

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;

import com.adiandnoy.RatEat.R;
import com.adiandnoy.RatEat.model.Dish;
import com.adiandnoy.RatEat.model.Model;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import java.util.UUID;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

public class UpdateDishFragment extends Fragment {

    private FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
    TextInputEditText dishName;
    TextInputEditText dishDescription;
    TextInputEditText resturantName;
    TextInputEditText dishIngredients;
    ImageButton editImage;
    ImageButton backBtn;
    ImageView dishImage;
    RatingBar dishStars;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_update_dish, container, false);
        Button saveUpdatedDish = view.findViewById(R.id.saveUpdatedDish);

        dishName = view.findViewById(R.id.editDishName);
        dishDescription =  view.findViewById(R.id.editDishDescription);
        resturantName =  view.findViewById(R.id.editResturantName);
        dishIngredients =  view.findViewById(R.id.editDishIngredient);
        editImage = view.findViewById(R.id.editImageUpdateDishFragment);
        dishImage = view.findViewById(R.id.editImage);
        dishStars = view.findViewById(R.id.editDishRatingBar);
        backBtn = view.findViewById(R.id.backBtnFromUpd);
        dishName.requestFocus();

        // Gets the parameters
        String imgUrlParam = UpdateDishFragmentArgs.fromBundle(getArguments()).getDishImgUrl();
        String dishNameParam = UpdateDishFragmentArgs.fromBundle(getArguments()).getDishName();
        String dishDescriptionParam = UpdateDishFragmentArgs.fromBundle(getArguments()).getDishDescription();
        String dishIngredientsParam = UpdateDishFragmentArgs.fromBundle(getArguments()).getDishIngredients();
        String resturantNameParam = UpdateDishFragmentArgs.fromBundle(getArguments()).getResturantName();
        Float ratingBarParam = UpdateDishFragmentArgs.fromBundle(getArguments()).getStars();
        String dishId = UpdateDishFragmentArgs.fromBundle(getArguments()).getUId();

        Picasso.get().load(imgUrlParam).into(dishImage);
        dishName.setText(dishNameParam);
        dishDescription.setText(dishDescriptionParam);
        dishIngredients.setText(dishIngredientsParam);
        resturantName.setText(resturantNameParam);
        dishStars.setRating(ratingBarParam);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(getView()).popBackStack();
            }
        });

        editImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editImage();
            }
        });

        saveUpdatedDish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dish dish = new Dish();
                dish.setId(dishId);
                dish.setDishName(dishName.getText().toString());
                dish.setDishDescription(dishDescription.getText().toString());
                dish.setResturantName(resturantName.getText().toString());
                dish.setIngredients(dishIngredients.getText().toString());
                dish.setUserID(currentUser.getUid());
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
                                    navController.navigate(R.id.studentInfoFragment);
//                                    Navigation.findNavController(addDish)
//                                    Navigation.findNavController(view).navigate(R.id.action_registerFragment_to_studentListFragment);
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
                        dishImage.setImageBitmap(selectedImage);
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
                                dishImage.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                                cursor.close();
                            }
                        }

                    }
                    break;
            }
        }
//        dishImage.setRotation(90);
    }
}