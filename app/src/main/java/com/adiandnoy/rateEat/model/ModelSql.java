package com.adiandnoy.rateEat.model;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class ModelSql {

    public LiveData<List<Student>> getAllStudents(){
        return AppLocalDb.db.studentDao().getAllStudents();
    }

    public interface AddStudentListener{
        void onComplete();
    }
    public void addStudent(final Student student, final Model.AddStudentListener listener){
        class MyAsyncTask extends AsyncTask {
            @Override
            protected Object doInBackground(Object[] objects) {
                AppLocalDb.db.studentDao().insertAll(student);
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                if (listener != null){
                    listener.onComplete();
                }
            }
        };
        MyAsyncTask task = new MyAsyncTask();
        task.execute();
    }
}
