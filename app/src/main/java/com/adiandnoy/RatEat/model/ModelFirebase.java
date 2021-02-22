package com.adiandnoy.RatEat.model;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModelFirebase {
    public void getAllDishes(Model.GetAllDishesListener listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        List<Dish> dishList = new ArrayList<Dish>();

        db.collection("dishes")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Dish dish = document.toObject(Dish.class);
                                dishList.add(dish);
                            }
//                            return dishList;
                        }
                        listener.onComplete(dishList);
                    }
                });
//        return dishList;
    }

    ;

//                {
////                    @Override
////                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
////                        if (task.isSuccessful()) {
////                            for (QueryDocumentSnapshot document : task.getResult()) {
////                                Log.d(TAG, document.getId() + " => " + document.getData());
////                            }
////                        } else {
////                            Log.d(TAG, "Error getting documents: ", task.getException());
////                        }
////                    }
//                });
//    }

    public void addDish(Dish dish, Model.AddDisheListener listener) {
        // Access a Cloud Firestore instance from your Activity
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Create a new user with a first and last name
//        Map<String, Object> dishToDB = new HashMap<>();
//        dishToDB.put("first", "Ada");
//        dishToDB.put("last", "Lovelace");
//        dishToDB.put("born", 1815);

// Add a new document with a generated ID
        db.collection("dishes").document(dish.getId())
                .set(dish)
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

    public void addUser(User user, Model.AddUserListener listener) {
    }

    public void getDish(String id, Model.GetDisheListener listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("dishes").document(id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                Dish dish = null;
                if (task.isSuccessful()) {
                    DocumentSnapshot doc = task.getResult();
                    if (doc != null) {
                        dish = task.getResult().toObject(Dish.class);
                    }
                }
                listener.onComplete(dish);
            }
        });

//        db.collection("dishes").document(id)
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()) {
//                            Dish dish = task.getResult().toObjects(Dish.class);
//                        }
//                        listener.onComplete(dish);
//                    }
//                });
    }
}
