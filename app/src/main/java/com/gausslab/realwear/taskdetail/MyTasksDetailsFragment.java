package com.gausslab.realwear.taskdetail;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.gausslab.realwear.R;
import com.gausslab.realwear.util.adapter.adapter_listener_interface.OnClickInteractionListener;
import com.gausslab.realwear.databinding.FragmentMytasksDetailsBinding;
import com.gausslab.realwear.model.TaskStep;
import com.gausslab.realwear.viewmodel.MyTaskDetailsViewModel;
import com.gausslab.realwear.viewmodel.MyTasksViewModel;

public class MyTasksDetailsFragment extends Fragment {
    MyTasksViewModel myTasksViewModel;
    MyTaskDetailsViewModel myTaskDetailsViewModel;
    FragmentMytasksDetailsBinding binding;
    FragmentManager fragmentManager;

    FrameLayout fl_taskDetails;
    TextView tv_myTasksHeader;
    Button bt_start;
    Button bt_complete;


    public MyTasksDetailsFragment() {
        // Required empty public constructor
    }


    public static MyTasksDetailsFragment newInstance() {
        MyTasksDetailsFragment fragment = new MyTasksDetailsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myTaskDetailsViewModel = new ViewModelProvider(requireActivity()).get(MyTaskDetailsViewModel.class);
        myTasksViewModel = new ViewModelProvider(requireActivity()).get(MyTasksViewModel.class);
        fragmentManager = getChildFragmentManager();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentMytasksDetailsBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }
    @Override
    public void onViewCreated(@NonNull View view, @NonNull Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        int taskId = MyTasksDetailsFragmentArgs.fromBundle(getArguments()).getTaskId();
        myTaskDetailsViewModel.setCurrTask(taskId);

        fl_taskDetails = binding.myTaskDetailsFlDetails;
        tv_myTasksHeader = binding.myTaskDetailsTvNavHeader1;
        bt_complete = binding.myTaskDetailsBtCompleteTask;
        bt_start = binding.myTaskDetailsBtStartTask;

        init();

        //region OnClickListeners
        tv_myTasksHeader.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                NavHostFragment.findNavController(MyTasksDetailsFragment.this).navigate(R.id.action_myTasksDetailsFragment_to_myTasksFragment);
            }
        });
        bt_start.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                myTasksViewModel.startTask(myTaskDetailsViewModel.getCurrTask());
            }
        });

        bt_complete.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                myTasksViewModel.completeTask(myTaskDetailsViewModel.getCurrTask());
            }
        });
        //endregion

    }
    private void init()
    {
        //region Adapters
        //endregion

        //region Fragments

        Fragment frag = MyTasksDetailsFragment_new.newInstance(new OnClickInteractionListener<TaskStep>()
        {
            @Override
            public void onItemClick(TaskStep obj)
            {
                MyTasksDetailsFragmentDirections.ActionMyTasksDetailsFragmentToWNavigationMyStepDetailsFragment action = MyTasksDetailsFragmentDirections.actionMyTasksDetailsFragmentToWNavigationMyStepDetailsFragment();
                action.setStepNumber(Integer.parseInt(obj.getStepNumber()));
                NavHostFragment.findNavController(MyTasksDetailsFragment.this).navigate(action);
            }
        });
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(fl_taskDetails.getId(), frag);
        transaction.commit();
        //endregion
    }
}