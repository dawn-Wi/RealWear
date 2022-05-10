package com.gausslab.realwear.taskdetail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.gausslab.realwear.util.BasicListFragment;
import com.gausslab.realwear.util.adapter.StepRecyclerViewAdapter;
import com.gausslab.realwear.util.adapter.TaskTextDetailsRecyclerViewAdapter;
import com.gausslab.realwear.util.adapter.adapter_listener_interface.OnItemInteractionListener;
import com.gausslab.realwear.databinding.FragmentMytasksdetailsNewBinding;
import com.gausslab.realwear.util.factory.TaskViewComponentFactory;
import com.gausslab.realwear.model.Task;
import com.gausslab.realwear.model.TaskStep;
import com.gausslab.realwear.viewmodel.MyTaskDetailsViewModel;

public class MyTasksDetailsFragment_new extends Fragment
{
    OnItemInteractionListener<TaskStep> itemInteractionListener;
    private MyTaskDetailsViewModel myTaskDetailsViewModel;
    private FragmentMytasksdetailsNewBinding binding;
    private FragmentManager fragmentManager;
    private TaskTextDetailsRecyclerViewAdapter textDetailsAdapter;
    private StepRecyclerViewAdapter stepsAdapter;

    private TextView tv_title;
    private FrameLayout fl_textDetails;
    private FrameLayout fl_stepList;

    public MyTasksDetailsFragment_new()
    {
        itemInteractionListener = null;
    }

    public MyTasksDetailsFragment_new(OnItemInteractionListener<TaskStep> itemInteractionListener)
    {
        this.itemInteractionListener = itemInteractionListener;
    }

    public static MyTasksDetailsFragment_new newInstance()
    {
        MyTasksDetailsFragment_new fragment = new MyTasksDetailsFragment_new();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public static MyTasksDetailsFragment_new newInstance(OnItemInteractionListener<TaskStep> itemInteractionListener)
    {
        MyTasksDetailsFragment_new fragment = new MyTasksDetailsFragment_new(itemInteractionListener);
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        myTaskDetailsViewModel = new ViewModelProvider(requireActivity()).get(MyTaskDetailsViewModel.class);
        fragmentManager = getChildFragmentManager();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        binding = FragmentMytasksdetailsNewBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        tv_title = binding.taskDetailsTvTitle;
        fl_textDetails = binding.taskDetailsFlTextDetails;
        fl_stepList = binding.taskDetailsFlStepList;

        init();

        tv_title.setText(myTaskDetailsViewModel.getCurrTask().getTitle());

        return root;
    }

    private void init()
    {

        //region Adapters
        textDetailsAdapter = new TaskTextDetailsRecyclerViewAdapter(TaskViewComponentFactory.generateTaskViewTextComponents(myTaskDetailsViewModel.getCurrTask()));
        stepsAdapter = new StepRecyclerViewAdapter(myTaskDetailsViewModel.getCurrTask().getSteps(), itemInteractionListener);
        //endregion

        //region Fragments
        Fragment textDetailsFrag = BasicListFragment.newInstance(textDetailsAdapter);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(fl_textDetails.getId(), textDetailsFrag);
        if(stepsAdapter.getItemCount() != 0)
        {
            Fragment stepsFrag = BasicListFragment.newInstance(stepsAdapter);
            transaction.replace(fl_stepList.getId(), stepsFrag);
        }
        else
        {
            fl_stepList.setVisibility(View.GONE);
        }
        transaction.commit();
        //endregion
    }

    public Task getTask()
    {
        return myTaskDetailsViewModel.getCurrTask();
    }
}