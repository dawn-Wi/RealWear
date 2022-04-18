package com.gausslab.realwear;

import static com.gausslab.realwear.ViewComponent.VIEW_TYPE_IMAGE;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gausslab.realwear.factory.TaskViewComponentFactory;

import java.util.List;

public class TaskTextDetailsRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    private final List<ViewComponent> componentList;
    private int imageComponentPosition = -1;

    public TaskTextDetailsRecyclerViewAdapter(List<ViewComponent> data)
    {
        componentList = data;
        for(int i = 0; i < data.size(); i++)
        {
            if(componentList.get(i).getViewType() == VIEW_TYPE_IMAGE)
                imageComponentPosition = i;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        return TaskViewComponentFactory.getViewHolder(viewType, LayoutInflater.from(parent.getContext()), parent, false);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position)
    {
        if(holder instanceof BaseViewHolder)
        {
            ((BaseViewHolder) holder).bindData(componentList.get(position));
        }
    }

    @Override
    public int getItemCount()
    {
        return componentList.size();
    }

    @Override
    public int getItemViewType(int position)
    {
        return componentList.get(position).getViewType();
    }
}