package com.gausslab.realwear.mytask;

import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.gausslab.realwear.util.adapter.adapter_listener_interface.OnItemInteractionListener;
import com.gausslab.realwear.util.adapter.adapter_listener_interface.OnMyTaskContextMenuInteractionListener;
import com.gausslab.realwear.viewmodel.MyTasksViewModel;
import com.gausslab.realwear.model.MyTask;

import java.util.List;

public class MyTasksRecyclerViewAdapter extends TaskRecyclerViewAdapter{
    private List<MyTask> myTaskList;
    public MyTasksRecyclerViewAdapter(List<MyTask> items, MyTasksViewModel mvm) {
        super(items,mvm);
    }
    public MyTasksRecyclerViewAdapter(List<MyTask> items, MyTasksViewModel mvm,OnItemInteractionListener<MyTask> clickListener)
    {
        super(items, mvm, clickListener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        super.onBindViewHolder(holder, position);
        MyTask task = taskList.get(position);
        if(listener != null && listener instanceof OnMyTaskContextMenuInteractionListener)
        {
            holder.card.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    ((OnMyTaskContextMenuInteractionListener<MyTask>) listener).onItemClick(taskList.get(holder.getAbsoluteAdapterPosition()));
                }
            });
            holder.card.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener()
            {
                @Override
                public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
                {
                    menu.setHeaderTitle("Select Action");
                    MenuItem returnTask = menu.add(Menu.NONE, 1, 1, "Return Task");
                    returnTask.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener()
                    {
                        @Override
                        public boolean onMenuItemClick(MenuItem item)
                        {
                            ((OnMyTaskContextMenuInteractionListener<MyTask>) listener).onContextReturnTask(taskList.get(holder.getAbsoluteAdapterPosition()));
                            return true;
                        }
                    });
                }
            });
        }
    }
    public void setMyTaskList(List<MyTask> newList){
        myTaskList = newList;
        notifyDataSetChanged();
    }
}
