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
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.adiandnoy.RatEat.R;
import com.adiandnoy.RatEat.model.Dish;
import com.adiandnoy.RatEat.model.Model;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.UUID;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

public class AddDishFragment extends Fragment {

    TextInputEditText name;
    TextInputEditText description;
    TextInputEditText resturant;
    TextInputEditText ingredient;
    ImageButton editImage;
    ImageView dishImage;
    RatingBar dishStars;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_dish, container, false);
        Button addDish = view.findViewById(R.id.save_dish_buttom);

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        String uid = currentUser.getUid();

        name = view.findViewById(R.id.input_dish_name);
        description = view.findViewById(R.id.input_dish_description);
        resturant = view.findViewById(R.id.input_resturant_name);
        ingredient = view.findViewById(R.id.input_dish_ingredient);
        editImage = view.findViewById(R.id.editImageAddDishFragment);
        dishImage = view.findViewById(R.id.import_image_add_dish_fragment);
        dishStars = view.findViewById(R.id.dishRatingBar);

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
//    static final int REQUEST_IMAGE_CAPTURE = 1;
//    private void editImage() {
//        Intent takePictureIntent = new Intent(
//                MediaStore.ACTION_IMAGE_CAPTURE);
//        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
//            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
//        }
//    }

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
    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == REQUEST_IMAGE_CAPTURE &&
//                resultCode == RESULT_OK) {
//            Bundle extras = data.getExtras();
//            Bitmap imageBitmap = (Bitmap) extras.get("data");
//            dishImage.setImageBitmap(imageBitmap);
//        }
//    }
}