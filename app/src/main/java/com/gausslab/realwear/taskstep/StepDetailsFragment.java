package com.gausslab.realwear.taskstep;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.gausslab.realwear.util.BasicListFragment;
import com.gausslab.realwear.R;
import com.gausslab.realwear.taskdetail.MyTaskDetailsViewModel;
import com.gausslab.realwear.util.adapter.ImagePinRecyclerViewAdapter;
import com.gausslab.realwear.databinding.FragmentStepdetailsBinding;
import com.gausslab.realwear.model.TaskStep;

public class StepDetailsFragment extends Fragment
{
    private final TaskStep currTaskStep;
    FragmentStepdetailsBinding binding;
    private MyTaskDetailsViewModel myTaskDetailsViewModel;
    private OnStepStatusUpdateListener listener;
    private TextView tv_header;
    private TextView tv_content;
    private ImageView iv_imageView;
    private Button bt_nextStep;
    private FrameLayout fl_pinList;
    private ImagePinRecyclerViewAdapter imagePinListAdapter;
    private int maxStepNumber;

    public StepDetailsFragment(TaskStep currStep, int maxStepNumber, OnStepStatusUpdateListener listener)
    {
        currTaskStep = currStep;
        this.maxStepNumber = maxStepNumber;
        this.listener = listener;
    }

    public StepDetailsFragment(TaskStep currStep)
    {
        currTaskStep = currStep;
    }

    public static StepDetailsFragment newInstance(TaskStep currStep, int maxStepNumber, OnStepStatusUpdateListener listener)
    {
        StepDetailsFragment fragment = new StepDetailsFragment(currStep, maxStepNumber, listener);
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public static StepDetailsFragment newInstance(TaskStep currStep)
    {
        StepDetailsFragment fragment = new StepDetailsFragment(currStep);
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        myTaskDetailsViewModel = new ViewModelProvider(requireActivity()).get(MyTaskDetailsViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        binding = FragmentStepdetailsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        iv_imageView = view.findViewById(R.id.stepDetails_iv_imageView);
        tv_header = view.findViewById(R.id.stepDetails_tv_header);
        tv_content = view.findViewById(R.id.stepDetails_tv_content);
        bt_nextStep = view.findViewById(R.id.stepDetails_bt_nextStep);
        fl_pinList = view.findViewById(R.id.stepDetails_fl_pinList);
        init();

        if(listener == null)
        {
//            bt_complete.setVisibility(View.GONE);
            bt_nextStep.setVisibility(View.GONE);
        }

//        if(currTaskStep.getProgressStatus() == ProgressStatus.PENDING_REVIEW)
//            bt_complete.setEnabled(false);

        String s = " " + currTaskStep.getStepNumber() + "/" + maxStepNumber;
        tv_header.append(s);
        if(Integer.parseInt(currTaskStep.getStepNumber()) == maxStepNumber)
        {
            bt_nextStep.setVisibility(View.GONE);
        }

        tv_content.setText(currTaskStep.getTextContent());
        iv_imageView.setImageDrawable(currTaskStep.getPinnedImage().getImageDrawable());

        bt_nextStep.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(listener != null)
                    listener.onNextStep(currTaskStep);
            }
        });
    }

    private void init()
    {
        //region Adapters
        imagePinListAdapter = new ImagePinRecyclerViewAdapter(currTaskStep.getPinnedImage().getImagePinList(), null);
        //endregion

        //region Fragments
        FragmentManager fm = getChildFragmentManager();
        Fragment pinListFragment = BasicListFragment.newInstance(imagePinListAdapter);
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(fl_pinList.getId(), pinListFragment);
        transaction.commit();
        //endregion
    }

    public TaskStep getTaskStep()
    {
        return currTaskStep;
    }

    public interface OnStepStatusUpdateListener
    {
        void onStepCompleted(TaskStep step);

        void onNextStep(TaskStep step);

        void onReturnToTask();
    }
}