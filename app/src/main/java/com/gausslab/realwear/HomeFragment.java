package com.gausslab.realwear;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.gausslab.realwear.repository.TaskRepository;
import com.gausslab.realwear.viewmodel.MyTasksViewModel;

public class HomeFragment extends Fragment
{
    MyTasksViewModel myTasksViewModel;
    private Button home_bt_camera;
    private Button home_bt_mytask;

    public HomeFragment()
    {
        // Required empty public constructor
    }

    public static HomeFragment newInstance(String param1, String param2)
    {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        myTasksViewModel = new ViewModelProvider(requireActivity()).get(MyTasksViewModel.class);
        FirebaseDataSource ds = new FirebaseDataSource();
        TaskRepository.getInstance().setDateSource(ds);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        home_bt_camera = view.findViewById(R.id.home_bt_camera);
        home_bt_mytask = view.findViewById(R.id.home_bt_mytask);

        home_bt_camera.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                myTasksViewModel.loadMyTaskList("21");
                NavHostFragment.findNavController(HomeFragment.this).navigate(R.id.action_homeFragment_to_cameraFragment);
            }
        });

        home_bt_mytask.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                NavHostFragment.findNavController(HomeFragment.this).navigate(R.id.action_homeFragment_to_myTasksFragment);
            }
        });
    }
}