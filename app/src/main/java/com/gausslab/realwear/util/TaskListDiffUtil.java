package com.gausslab.realwear.util;

import androidx.recyclerview.widget.DiffUtil;

import com.gausslab.realwear.model.Task;

import java.util.List;

public class TaskListDiffUtil extends DiffUtil.Callback
{
    private final List<Task> oldList;
    private final List<Task> newList;

    public TaskListDiffUtil(List<Task> oldList, List<Task> newList)
    {
        this.oldList = oldList;
        this.newList = newList;
    }

    @Override
    public int getOldListSize()
    {
        return oldList.size();
    }

    @Override
    public int getNewListSize()
    {
        return newList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition)
    {
//        return oldList.get(oldItemPosition).getTaskId().equals(newList.get(newItemPosition).getTaskId());
        return true;
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition)
    {
        return oldList.get(oldItemPosition).equals(newList.get(newItemPosition));
    }
}
