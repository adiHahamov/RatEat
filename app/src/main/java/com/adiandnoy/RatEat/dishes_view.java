package com.adiandnoy.RatEat;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RatingBar;

import androidx.appcompat.app.AppCompatActivity;

import java.util.LinkedList;
import java.util.List;

public class dishes_view extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dishes_view);

        ListView list = findViewById(R.id.dishes_view_list);
        MyAdapter adapter = new MyAdapter();
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            // i was selected
            }
        });
    }

    class MyAdapter extends BaseAdapter{

        List<String> data = new LinkedList<String>();

        MyAdapter(){
            for (int i=0;i<10;i++){
                data.add("element" + i);
            }
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null){

            view = getLayoutInflater().inflate(R.layout.dishes_view_row,null);
            }
            RatingBar r = view.findViewById(R.id.dishes_view_rating_bar);
            r.setNumStars(i);
            return view;
        }
    }
}
