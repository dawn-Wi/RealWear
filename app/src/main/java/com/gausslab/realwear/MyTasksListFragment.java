package com.gausslab.realwear;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gausslab.realwear.placeholder.PlaceholderContent;

public class MyTasksListFragment extends Fragment {
    protected RecyclerView.Adapter adapter;
    RecyclerView recyclerView;

    public MyTasksListFragment(RecyclerView.Adapter adapter) {
        this.adapter = adapter;
    }

    public static MyTasksListFragment newInstance(RecyclerView.Adapter adapter) {
        MyTasksListFragment fragment = new MyTasksListFragment(adapter);
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mytasks_list, container, false);

        // Set the adapter
        if(view instanceof RecyclerView)
        {
            Context context = view.getContext();
            recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setAdapter(adapter);
        }

        return view;
    }

    @Override
    public void onDestroyView()
    {
        recyclerView.setAdapter(null);
        recyclerView = null;
        super.onDestroyView();
    }
}