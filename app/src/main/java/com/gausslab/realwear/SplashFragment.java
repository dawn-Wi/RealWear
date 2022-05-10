package com.gausslab.realwear;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.os.IBinder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.gausslab.realwear.repository.ReportRepository;
import com.gausslab.realwear.repository.TaskRepository;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SplashFragment extends Fragment {
    App app;
    TaskRepository taskRepository;
    ReportRepository reportRepository;
    FileService fileService;
    ImageView splash_iv_nameImageView;
    ImageView splash_iv_logoImageView;

    public SplashFragment() {
        // Required empty public constructor
    }


    public static SplashFragment newInstance(String param1, String param2) {
        SplashFragment fragment = new SplashFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Initialize all repositories

        taskRepository = TaskRepository.getInstance();
        reportRepository = ReportRepository.getInstance();
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        FirebaseDataSource ds = new FirebaseDataSource();
        taskRepository.setExecutor(executorService);
        taskRepository.setDataSource(ds);
        reportRepository.setExecutor(executorService);
        reportRepository.setDataSource(ds);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        //Initialize services
        ServiceConnection connection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder service) {
                FileService.LocalBinder binder = (FileService.LocalBinder) service;
                fileService = binder.getService();
                fileService.setFirebaseDataSource(ds);
                taskRepository.setFileService(fileService);
                reportRepository.setFileService(fileService);
                App.setFileService(fileService);
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {
            }
        };
        Intent intent = new Intent(requireActivity(), FileService.class);
        requireActivity().startService(intent);
        requireActivity().bindService(intent, connection, Context.BIND_AUTO_CREATE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash, container, false);
    }

    @Override
    public void onViewCreated(@Nullable View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        splash_iv_nameImageView = view.findViewById(R.id.splash_iv_nameImageView);
        splash_iv_logoImageView = view.findViewById(R.id.splash_iv_logoImageView);
        NavHostFragment.findNavController(SplashFragment.this).navigate(R.id.action_splashFragment_to_homeFragment);

    }
}