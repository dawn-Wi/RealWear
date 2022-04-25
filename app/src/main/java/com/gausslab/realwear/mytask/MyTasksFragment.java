package com.gausslab.realwear.mytask;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.gausslab.realwear.R;
import com.gausslab.realwear.util.BasicListFragment;
import com.gausslab.realwear.util.adapter.adapter_listener_interface.OnMyTaskContextMenuInteractionListener;
import com.gausslab.realwear.databinding.FragmentMytasksBinding;
import com.gausslab.realwear.model.MyTask;
import com.gausslab.realwear.viewmodel.MyTasksViewModel;

import java.util.List;

public class MyTasksFragment extends Fragment {
    MyTasksViewModel myTasksViewModel;
    MyTasksRecyclerViewAdapter currUserTasksAdapter;
    List<MyTask> myTasks;
    FrameLayout fl_taskList;
    Button home;
    FragmentMytasksBinding binding;
    FragmentManager fragmentManager;

    public MyTasksFragment() {

    }

    @Override
    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        myTasksViewModel = new ViewModelProvider(requireActivity()).get(MyTasksViewModel.class);
        fragmentManager = getChildFragmentManager();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentMytasksBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@Nullable View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fl_taskList = binding.myTasksFlTaskList;
        home = binding.taskListBtHome;
        myTasks = myTasksViewModel.getMyTaskList();

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myTasksViewModel.falseListLoaded();
                NavHostFragment.findNavController(MyTasksFragment.this).navigate(R.id.action_myTasksFragment_to_homeFragment2);
            }
        });

        init();

        myTasksViewModel.isCurrUserTasksUpdated().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isUpdated) {
                if (isUpdated) {
                    if (myTasksViewModel.getCurrUserTasks().size() == 0){

                    }
                    else{

                    }
                    currUserTasksAdapter.setTaskList(myTasksViewModel.getCurrUserTasks());
                }
            }
        });

    }

    private void init(){
        currUserTasksAdapter = new MyTasksRecyclerViewAdapter(myTasksViewModel.getMyTaskList(), myTasksViewModel, new OnMyTaskContextMenuInteractionListener<MyTask>() {
            @Override
            public void onItemClick(MyTask obj) {
                int taskId = Integer.parseInt(obj.getTaskId());
                MyTasksFragmentDirections.ActionMyTasksFragmentToMyTasksDetailsFragment action = MyTasksFragmentDirections.actionMyTasksFragmentToMyTasksDetailsFragment();
                action.setTaskId(taskId);
                NavHostFragment.findNavController(MyTasksFragment.this).navigate(action);
            }

            @Override
            public void onContextReturnTask(MyTask obj) {

            }
        });

        Fragment fragment = BasicListFragment.newInstance(currUserTasksAdapter);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(fl_taskList.getId(), fragment);
        transaction.commit();
    }




}