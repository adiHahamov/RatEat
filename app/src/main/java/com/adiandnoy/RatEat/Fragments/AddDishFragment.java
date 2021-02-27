package com.adiandnoy.RatEat.Fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.renderscript.ScriptGroup;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.adiandnoy.RatEat.R;
import com.adiandnoy.RatEat.model.Dish;
import com.adiandnoy.RatEat.model.Model;
import com.google.android.material.textfield.TextInputEditText;

import java.util.UUID;

import static android.app.Activity.RESULT_OK;

public class AddDishFragment extends Fragment {

    TextInputEditText name;
    TextInputEditText description;
    TextInputEditText resturant;
    TextInputEditText ingredient;
    ImageButton editImage;
    ImageView dishImage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_dish, container, false);
        Button addDish = view.findViewById(R.id.save_dish_buttom);

        name =  view.findViewById(R.id.input_dish_name);
        description =  view.findViewById(R.id.input_dish_description);
        resturant =  view.findViewById(R.id.input_resturant_name);
        ingredient =  view.findViewById(R.id.input_dish_ingredient);
        editImage =  view.findViewById(R.id.editImageAddDishFragment);
        dishImage = view.findViewById(R.id.import_image_add_dish_fragment);

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

                //Save the image
                BitmapDrawable drawable = (BitmapDrawable)dishImage.getDrawable();
                Bitmap bitmap = drawable.getBitmap();
                Model.instance.uploadImage(bitmap, dish.getId(), new Model.uploadImageListener() {
                    @Override
                    public void onComplete(String url) {
                        if (url == null){

                        }else {
                            dish.setImageUrl(url);
                            Model.instance.addDish(dish,new Model.AddDisheListener() {
                                @Override
                                public void onComplete() {
//                                    Navigation.findNavController(addDish)
                                }
                            });
                        }
                    }
                });


            }
        });

        // Inflate the layout for this fragment
        return view;
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
            dishImage.setImageBitmap(imageBitmap);
        }
    }
}