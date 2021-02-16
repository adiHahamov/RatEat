package com.adiandnoy.RatEat.adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.adiandnoy.RatEat.R;
import com.adiandnoy.RatEat.model.Dish;
import com.adiandnoy.RatEat.model.Student;

public class StudentViewHolder extends RecyclerView.ViewHolder{
    public StudentsAdapter.OnItemClickListener listener;
    TextView studentId;
    ImageView studentImage;
    int position;

    public StudentViewHolder(@NonNull View itemView) {
        super(itemView);
//        studentId = itemView.findViewById(R.id.listrow_text_v);
        studentImage = itemView.findViewById(R.id.listrow_image_v);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(position);
            }
        });
    }

    public void bindData(Dish dish, int position) {
//        studentId.setText(student.id);
        this.position = position;
    }
}
