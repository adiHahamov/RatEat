package com.adiandnoy.RatEat.model;

import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModelFirebase {
    public void getAllDishes(long lastUpdateDate, Model.GetAllDishesListener listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        List<Dish> dishList = new ArrayList<Dish>();
        Timestamp ts = new Timestamp(lastUpdateDate,0);
        db.collection("dishes").whereGreaterThanOrEqualTo("lastUpdated",ts)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Dish dish = new Dish();
                                dish.fromMap(document.getData());
//                                Dish dish = document.toObject(Dish.class);
                                dishList.add(dish);
                            }
//                            return dishList;
                        }
                        listener.onComplete(dishList);
                    }
                });
//        return dishList;
    }

    public void addDish(Dish dish, Model.AddDisheListener listener) {
        // Access a Cloud Firestore instance from your Activity
        FirebaseFirestore db = FirebaseFirestore.getInstance();

// Add a new document with a generated ID
        db.collection("dishes").document(dish.getId())
                .set(dish.toMap())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("TAG", "");
                        listener.onComplete();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("TAG", "Error adding document", e);
                        listener.onComplete();
                    }
                });
    }

    public void getAllUsers(Model.GetAllUsersListener listener) {

    }
//    public void getUser(String uId, Model.GetUserListener listener) {
//        FirebaseFirestore db = FirebaseFirestore.getInstance();
//        db.collection("users").document(uId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//            User user = null;
//            @Override
//            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                User user = null;
//                if (task.isSuccessful()) {
//                    DocumentSnapshot doc = task.getResult();
//                    if (doc != null) {
//                        user = new User();
//                        user = task.getResult().toObject(User.class);
////                        dish = task.getResult().toObject(Dish.class);
//                    }
//                }
//                listener.onComplete(user);
//            }
//        });
//    };

    public void addUser(User user, Model.AddUserListener listener) {
        // Access a Cloud Firestore instance from your Activity
        FirebaseFirestore db = FirebaseFirestore.getInstance();

// Add a new document with a generated ID
        db.collection("users").document(user.getId())
                .set(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("TAG", "");
                        listener.onComplete();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("TAG", "Error adding document", e);
                        listener.onComplete();
                    }
                });
    }

    public void getDish(String id, Model.GetDisheListener listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("dishes").document(id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            Dish dish = null;
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                Dish dish = null;
                if (task.isSuccessful()) {
                    DocumentSnapshot doc = task.getResult();
                    if (doc != null) {
                        dish = new Dish();
                        dish.fromMap(task.getResult().getData());
//                        dish = task.getResult().toObject(Dish.class);
                    }
                }
                listener.onComplete(dish);
            }
        });
    }

    public void deleteDish(Dish dish, Model.DeleteDisheListener listener) {

        FirebaseFirestore db = FirebaseFirestore.getInstance();

// Delete document with a generated ID
        db.collection("dishes").document(dish.getId())
                .delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        listener.onComplete();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("TAG", "Error delete document", e);
                        listener.onComplete();
                    }
                });
    }

    public void updateDish(Dish dish, Model.UpdateDishListener listener) {

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("dishes").document(dish.getId()).set(dish).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                listener.onComplete();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("TAG", "Error update document", e);
                listener.onComplete();
            }
        });
    }


    public void uploadImage(Bitmap imageBmp, String name,  Model.uploadImageListener listener){
        FirebaseStorage storage = FirebaseStorage.getInstance();
        final StorageReference imagesRef = storage.getReference().child("images").child(name);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        imageBmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();
        UploadTask uploadTask = imagesRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception exception) {
                listener.onComplete(null);
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                imagesRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Uri downloadUrl = uri;
                        listener.onComplete(downloadUrl.toString());
                    }
                });
            }
        });
    }

}
