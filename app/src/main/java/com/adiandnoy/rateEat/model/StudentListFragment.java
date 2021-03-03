package com.adiandnoy.rateEat.model;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.adiandnoy.rateEat.Model;
import com.adiandnoy.rateEat.R;
import com.adiandnoy.rateEat.StudentListFragmentDirections;
import com.adiandnoy.rateEat.StudentListViewModel;
import com.adiandnoy.rateEat.model.Student;
import com.squareup.picasso.Picasso;
import java.util.List;

public class StudentListFragment extends Fragment {
    StudentListViewModel viewModel;

    ProgressBar pb;
    Button addBtn;
    MyAdapter adapter;
    SwipeRefreshLayout sref;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d("TAG","onCreateView");
        View view = inflater.inflate(R.layout.fragment_student_list, container, false);

        viewModel = new ViewModelProvider(this).get(StudentListViewModel.class);

        ListView list = view.findViewById(R.id.studentslist_list);
        pb = view.findViewById(R.id.studentslist_progress);
        addBtn = view.findViewById(R.id.studentslist_add_btn);
        pb.setVisibility(View.INVISIBLE);
        sref = view.findViewById(R.id.studentslist_swipe);

        sref.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                sref.setRefreshing(true);
                reloadData();

            }
        });

        adapter = new MyAdapter();
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String id = viewModel.getList().getValue().get(i).getId();
                StudentListFragmentDirections.ActionStudentListFragmentToStudentDetailsFragment direc = StudentListFragmentDirections.actionStudentListFragmentToStudentDetailsFragment(id);
                Navigation.findNavController(view).navigate(direc);
            }
        });
        addBtn.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_studentListFragment_to_addStudentFragment));

        viewModel.getList().observe(getViewLifecycleOwner(), new Observer<List<Student>>() {
            @Override
            public void onChanged(List<Student> students) {
                adapter.notifyDataSetChanged();
            }
        });
        return view;
    }

    void reloadData(){
        pb.setVisibility(View.VISIBLE);
        addBtn.setEnabled(false);
        Model.instance.refreshAllStudents(new Model.GetAllStudentsListener() {
            @Override
            public void onComplete() {
                pb.setVisibility(View.INVISIBLE);
                addBtn.setEnabled(true);
                sref.setRefreshing(false);
            }
        });
    }

    class MyAdapter extends BaseAdapter{
        @Override
        public int getCount() {
            if (viewModel.getList().getValue() == null){
                return 0;
            }
            return viewModel.getList().getValue().size();
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
                view = getLayoutInflater().inflate(R.layout.list_row, null);
            }
            TextView tv = view.findViewById(R.id.listrow_test_tv);
            ImageView imv = view.findViewById(R.id.listrow_imagev);
            Student st = viewModel.getList().getValue().get(i);
            tv.setText(st.getId());

            imv.setImageResource(R.drawable.avatar);
            if (st.getImageUrl() != null){
                Picasso.get().load(st.getImageUrl()).placeholder(R.drawable.avatar).into(imv);
            }
            return view;
        }
    }
}