package com.gausslab.realwear.mytask;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gausslab.realwear.viewmodel.MyTasksViewModel;
import com.gausslab.realwear.R;
import com.gausslab.realwear.model.Task;

import java.util.List;

public class MyTasksListFragment extends Fragment {
    MyTasksViewModel myTasksViewModel;

    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;

    public List<Task> taskList;

    public MyTasksListFragment(List<Task> taskList) {this.taskList = taskList;}

    public static MyTasksListFragment newInstance(int columnCount, List<Task> taskList){
        MyTasksListFragment fragment = new MyTasksListFragment(taskList);
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT,columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
        myTasksViewModel = new ViewModelProvider(requireActivity()).get(MyTasksViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mytasks_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            MyTasksRecyclerViewAdapter adapter = new MyTasksRecyclerViewAdapter(taskList, new ViewModelProvider(requireActivity()).get(MyTasksViewModel.class));

            recyclerView.addItemDecoration(new DividerItemDecoration(view.getContext(), LinearLayoutManager.VERTICAL));
            recyclerView.setAdapter(adapter);

            myTasksViewModel.isListLoaded().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
                @Override
                public void onChanged(Boolean isChanged) {
                    if(isChanged) {
                        adapter.setMyTaskList(myTasksViewModel.getMyTaskList());
                        adapter.notifyDataSetChanged();
                    }
                }
            });
        }
        return view;
    }

    @Override
    public void onViewCreated(@Nullable View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

}