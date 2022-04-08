package com.gausslab.realwear;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.gausslab.realwear.adapter_listener_interface.OnAllInteractionListener;
import com.gausslab.realwear.databinding.FragmentMytasksBinding;

public class MyTasksFragment extends Fragment {
//    MyTasksViewModel myTasksViewModel;
    FragmentMytasksBinding binding;
    TaskRecyclerViewAdapter currUserTasksAdapter;
    FragmentManager fragmentManager;

    FrameLayout fl_taskList;

    public MyTasksFragment() {
        // Required empty public constructor
    }


    public static MyTasksFragment newInstance(String param1, String param2) {
        MyTasksFragment fragment = new MyTasksFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fl_taskList = binding.myTasksFlTaskList;

        init();

//        myTasksViewModel.isCurrUserTasksUpdated().observe(getViewLifecycleOwner(), new Observer<Boolean>()
//        {
//            @Override
//            public void onChanged(Boolean isUpdated)
//            {
//                if(isUpdated)
//                    currUserTasksAdapter.setTaskList(myTasksViewModel.getCurrUserTasks());
//            }
//        });
    }
    private void init()
    {
        //region Fragments
        Fragment frag = MyTasksListFragment.newInstance(currUserTasksAdapter);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(fl_taskList.getId(), frag);
        transaction.commit();
        //endregion
    }
}