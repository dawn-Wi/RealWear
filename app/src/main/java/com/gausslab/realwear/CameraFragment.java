package com.gausslab.realwear;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.gausslab.realwear.viewmodel.MainViewModel;

import java.io.File;

public class CameraFragment extends Fragment {
    private MainViewModel mainViewModel;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private FrameLayout imageFrameLayout;
    private ImageView imageView;
    private Button camera_bt_retake;
    private Button camera_bt_submit;
    FileService fileService;
    private File imageFile;

    public CameraFragment() {
        // Required empty public constructor
    }


    public static CameraFragment newInstance(String param1, String param2) {
        CameraFragment fragment = new CameraFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fileService = App.getFileService();
        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_camera, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imageFrameLayout = view.findViewById(R.id.camera_fl_imageFrame);
        imageView = view.findViewById(R.id.camera_iv_image);
        camera_bt_retake = view.findViewById(R.id.camera_bt_retake);
        camera_bt_submit = view.findViewById(R.id.camera_bt_submit);

        mainViewModel.isSubmitSuccessful().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean submitSuccessful)
            {
                if(submitSuccessful)
                    Toast.makeText(getContext(), "보고서 제출 완료", Toast.LENGTH_SHORT).show();
            }
        });



        ActivityResultLauncher<Intent> launchCamera = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Bitmap bitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
                            imageView.setImageBitmap(bitmap);
                            imageView.invalidate();
                        }
                    }
                });
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        FileService fileService = App.getFileService();
        imageFile = fileService.createFile("",  "temp.jpg");

        if(imageFile != null)
        {
            Uri photoURI = FileProvider.getUriForFile(requireActivity(), App.getFileProvider(), imageFile);
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
        }
        launchCamera.launch(takePictureIntent);

        camera_bt_retake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchCamera.launch(takePictureIntent);
            }
        });

        camera_bt_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainViewModel.submitImage(imageFile);
            }
        });

    }

}