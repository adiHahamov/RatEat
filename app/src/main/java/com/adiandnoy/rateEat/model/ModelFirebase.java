package com.adiandnoy.rateEat.model;

import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;
import androidx.annotation.NonNull;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.LinkedList;
import java.util.List;

public class ModelFirebase {

    interface GetAllStudentsListener{
        void onComplete(List<Student> list);
    }
    public void getAllStudents(Long lastUpdated, final GetAllStudentsListener listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Timestamp ts = new Timestamp(lastUpdated,0);
        db.collection("students").whereGreaterThanOrEqualTo("lastUpdated",ts).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                List<Student> data = new LinkedList<Student>();
                if (task.isSuccessful()){
                    for (DocumentSnapshot doc:task.getResult()) {
                        Student st = new Student();
                        st.fromMap(doc.getData());
                        //Student st = doc.toObject(Student.class);
                        data.add(st);
                        Log.d("TAG","st: " + st.getId());
                    }
                }
                listener.onComplete(data);
            }
        });
    }

    public void addStudent(Student student, final Model.AddStudentListener listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("students").document(student.getId())
                .set(student.toMap()).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d("TAG","student added successfully");
                listener.onComplete();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("TAG","fail adding student");
                listener.onComplete();
            }
        });
    }

    public void updateStudent(Student student, Model.AddStudentListener listener) {
        addStudent(student,listener);
    }

    public void getStudent(String id, final Model.GetStudentListener listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("students").document(id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                Student student = null;
                if (task.isSuccessful()){
                    DocumentSnapshot doc = task.getResult();
                    if (doc != null) {
                        student = new Student();
                        student.fromMap(task.getResult().getData());
                    }
                }
                listener.onComplete(student);
            }
        });
    }

    public void delete(Student student, final Model.DeleteListener listener) {
//        FirebaseFirestore db = FirebaseFirestore.getInstance();
//        db.collection("students").document(student.getId())
//                .delete().addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//                listener.onComplete();
//            }
//        });
    }



    public void uploadImage(Bitmap imageBmp, String name, final Model.UploadImageListener listener){
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
