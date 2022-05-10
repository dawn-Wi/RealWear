package com.gausslab.realwear.taskstep;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.gausslab.realwear.databinding.FragmentMytaskStepBinding;
import com.gausslab.realwear.model.TaskStep;
import com.gausslab.realwear.viewmodel.MyTaskDetailsViewModel;
import com.gausslab.realwear.viewmodel.MyTasksViewModel;

public class MyTaskStepFragment extends Fragment {
    MyTasksViewModel myTasksViewModel;
    MyTaskDetailsViewModel myTaskDetailsViewModel;
    FragmentMytaskStepBinding binding;
    FragmentManager fragmentManager;

    FrameLayout fl_stepDetails;
    TextView tv_myTasksHeader;
    TextView tv_taskDetailsHeader;
    Button bt_start;
    Button bt_complete;
    Button bt_home;


    public MyTaskStepFragment() {
        // Required empty public constructor
    }

    public static MyTaskStepFragment newInstance() {
        MyTaskStepFragment fragment = new MyTaskStepFragment();
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
        binding = FragmentMytaskStepBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        int stepNumber = MyTaskStepFragmentArgs.fromBundle(getArguments()).getStepNumber();
        myTaskDetailsViewModel.setCurrStep(stepNumber);

        fl_stepDetails = binding.myStepDetailsFlDetails;
        tv_myTasksHeader = binding.myStepDetailsTvNavHeader1;
        tv_taskDetailsHeader = binding.myStepDetailsTvNavHeader2;
        bt_complete = binding.myStepDetailsBtCompleteStep;
        bt_start = binding.myStepDetailsBtStartStep;
        bt_home = binding.myStepDetailsBtHome;

        init();

        //region OnClickListeners
        tv_myTasksHeader.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                NavHostFragment.findNavController(MyTaskStepFragment.this).navigate(R.id.action_myTaskStepFragment_to_myTasksFragment);
            }
        });

        tv_taskDetailsHeader.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                int taskId = Integer.parseInt(myTaskDetailsViewModel.getCurrTask().getTaskId());
                MyTaskStepFragmentDirections.ActionMyTaskStepFragmentToMyTasksDetailsFragment action = MyTaskStepFragmentDirections.actionMyTaskStepFragmentToMyTasksDetailsFragment();
                action.setTaskId(taskId);
                NavHostFragment.findNavController(MyTaskStepFragment.this).navigate(action);
            }
        });

        bt_start.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //Start
                myTasksViewModel.startTask(myTaskDetailsViewModel.getCurrTask());
                bt_start.setEnabled(false);
            }
        });

        bt_complete.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //Complete
                myTasksViewModel.completeTask(myTaskDetailsViewModel.getCurrTask());
                bt_start.setEnabled(false);
                bt_complete.setEnabled(false);
            }
        });

        bt_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(MyTaskStepFragment.this).navigate(R.id.action_myTaskStepFragment_to_homeFragment);
            }
        });
        //endregion
    }

    private void init()
    {
        //region Adapters
        //endregion

        //region Fragments
        Fragment frag = StepDetailsFragment.newInstance(myTaskDetailsViewModel.getCurrStep(), myTaskDetailsViewModel.getCurrTask().getSteps().size(), new StepDetailsFragment.OnStepStatusUpdateListener()
        {
            @Override
            public void onStepCompleted(TaskStep step)
            {
            }

            @Override
            public void onNextStep(TaskStep step)
            {
                MyTaskStepFragmentDirections.ActionMyTaskStepFragmentSelf action =  MyTaskStepFragmentDirections.actionMyTaskStepFragmentSelf();
                action.setStepNumber(Integer.parseInt(step.getStepNumber()) + 1);
                NavHostFragment.findNavController(MyTaskStepFragment.this).navigate(action);
            }

            @Override
            public void onReturnToTask()
            {
                NavHostFragment.findNavController(MyTaskStepFragment.this).navigateUp();
            }
        });
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(fl_stepDetails.getId(), frag);
        transaction.commit();
        //endregion
    }
}
