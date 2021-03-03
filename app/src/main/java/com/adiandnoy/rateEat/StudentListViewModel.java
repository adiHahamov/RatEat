package com.adiandnoy.rateEat;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.adiandnoy.rateEat.model.Model;
import com.adiandnoy.rateEat.model.Student;

import java.util.List;

public class StudentListViewModel extends ViewModel {
    private LiveData<List<Student>> stList;

    public StudentListViewModel(){
        Log.d("TAG","StudentListViewModel");
        stList = Model.instance.getAllStudents();
    }
    public LiveData<List<Student>> getList(){
        return stList;
    }
}
