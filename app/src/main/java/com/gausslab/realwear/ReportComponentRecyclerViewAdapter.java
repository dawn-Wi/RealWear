package com.gausslab.realwear;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gausslab.realwear.viewholder.BaseViewHolder;
import com.gausslab.realwear.ViewComponent;
import com.gausslab.realwear.factory.ReportViewComponentFactory;

import java.util.List;

public class ReportComponentRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    private final List<ViewComponent> componentList;

    public ReportComponentRecyclerViewAdapter(List<ViewComponent> data)
    {
        componentList = data;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        return ReportViewComponentFactory.getViewHolder(viewType, LayoutInflater.from(parent.getContext()), parent, false);
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
