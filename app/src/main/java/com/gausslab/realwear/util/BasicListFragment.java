package com.gausslab.realwear.util;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gausslab.realwear.R;

public class BasicListFragment extends Fragment
{
    protected RecyclerView.Adapter adapter;
    RecyclerView recyclerView;

    public BasicListFragment(RecyclerView.Adapter adapter)
    {
        this.adapter = adapter;
    }

    public static BasicListFragment newInstance(RecyclerView.Adapter adapter)
    {
        BasicListFragment fragment = new BasicListFragment(adapter);
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.list_basic, container, false);

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