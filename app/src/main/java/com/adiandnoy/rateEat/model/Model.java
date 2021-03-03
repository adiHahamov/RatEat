package com.adiandnoy.rateEat.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import androidx.lifecycle.LiveData;
import com.adiandnoy.rateEat.MyApplication;
import java.util.List;

public class Model {
    public final static Model instance = new Model();

    ModelFirebase modelFirebase = new ModelFirebase();
    ModelSql modelSql = new ModelSql();

    private Model() {

    }

    public interface Listener<T> {
        void onComplete(T result);
    }

    LiveData<List<Student>> studentList;
    public LiveData<List<Student>> getAllStudents() {
        if (studentList == null){
            studentList = modelSql.getAllStudents();
            refreshAllStudents(null);
        }
        return studentList;
    }

    public interface GetAllStudentsListener{
        void onComplete();
    }
    public void refreshAllStudents(final GetAllStudentsListener listener) {
        //1. get local last update date
        final SharedPreferences sp = MyApplication.context.getSharedPreferences("TAG", Context.MODE_PRIVATE);
        long lastUpdated = sp.getLong("lastUpdated",0);
        //2. get all updated record from firebase from the last update date
        modelFirebase.getAllStudents(lastUpdated, new ModelFirebase.GetAllStudentsListener() {
            @Override
            public void onComplete(List<Student> result) {
                //3. insert the new updates to the local db
                long lastU = 0;
                for (Student s: result) {
                    modelSql.addStudent(s,null);
                    if (s.getLastUpdated()>lastU){
                        lastU = s.getLastUpdated();
                    }
                }
                //4. update the local last update date
                sp.edit().putLong("lastUpdated", lastU).commit();
                //5. return the updates data to the listeners
                if(listener != null){
                    listener.onComplete();
                }
            }
        });
    }

    public interface GetStudentListener {
        void onComplete(Student student);
    }

    public void getStudent(String id, GetStudentListener listener) {
        modelFirebase.getStudent(id, listener);
    }

    public interface AddStudentListener {
        void onComplete();
    }

    public void addStudent(final Student student, final AddStudentListener listener) {
        modelFirebase.addStudent(student, new AddStudentListener() {
            @Override
            public void onComplete() {
                refreshAllStudents(new GetAllStudentsListener() {
                    @Override
                    public void onComplete() {
                        listener.onComplete();
                    }
                });
            }
        });
    }

    public interface UpdateStudentListener extends AddStudentListener { }

    public void updateStudent(final Student student, final AddStudentListener listener) {
        modelFirebase.updateStudent(student, listener);
    }

    interface DeleteListener extends AddStudentListener { }

    public void deleteStudent(Student student, DeleteListener listener) {
        modelFirebase.delete(student, listener);
    }

    public interface UploadImageListener extends Listener<String>{ }

    public void uploadImage(Bitmap imageBmp, String name, final UploadImageListener listener) {
        modelFirebase.uploadImage(imageBmp, name, listener);
    }
}