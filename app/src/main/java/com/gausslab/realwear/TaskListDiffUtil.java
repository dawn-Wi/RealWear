package com.gausslab.realwear;

import androidx.recyclerview.widget.DiffUtil;

import com.gausslab.realwear.model.MyTask;

import java.util.List;

public class TaskListDiffUtil extends DiffUtil.Callback
{
    private final List<MyTask> oldList;
    private final List<MyTask> newList;

    public TaskListDiffUtil(List<MyTask> oldList, List<MyTask> newList)
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
