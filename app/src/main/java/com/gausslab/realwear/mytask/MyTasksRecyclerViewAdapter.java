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
    public MyTasksRecyclerViewAdapter(List<MyTask> items, OnItemInteractionListener<MyTask> clickListener)
    {
        super(items, clickListener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        super.onBindViewHolder(holder, position);
        MyTask task = taskList.get(position);
//        if(task.getProgressStatus() == ProgressStatus.PENDING_REVIEW)
//        {
//            holder.tv_status.getBackground().setTint(App.getCardColor(App.ColorName.GREEN));
//        }
//        else if(task.getProgressStatus() == ProgressStatus.STARTED)
//        {
//            holder.tv_status.getBackground().setTint(App.getCardColor(App.ColorName.YELLOW));
//        }
//        else if(task.getProgressStatus() == ProgressStatus.REJECTED)
//        {
//            holder.tv_status.getBackground().setTint(App.getCardColor(App.ColorName.RED));
//        }
//        else if(task.getProgressStatus() == ProgressStatus.NOT_STARTED)
//        {
//            holder.tv_status.getBackground().setTint(App.getCardColor(App.ColorName.WHITE));
//        }

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

//public class MyTasksRecyclerViewAdapter extends RecyclerView.Adapter<MyTasksRecyclerViewAdapter.ViewHolder> {
//
//    private List<MyTask> myTaskList;
//    private OnItemInteractionListener<MyTask> listener;
//    private MyTasksViewModel myTasksViewModel;
//
//    public MyTasksRecyclerViewAdapter(List<MyTask> items, OnItemInteractionListener<MyTask> listener){
//        myTaskList = items;
//        this.listener = listener;
//    }
//    public MyTasksRecyclerViewAdapter(List<MyTask> items, MyTasksViewModel mvm){
//        myTaskList = items;
//        myTasksViewModel = mvm;
//    }
////    public MyTasksRecyclerViewAdapter(List<MyTask> items, MyTasksViewModel mvm,OnClickInteractionListener<MyTask> listener){
////        myTaskList = items;
////        myTasksViewModel = mvm;
////        this.listener = listener;
////    }
//
//    @Override
//    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
//        return new ViewHolder(ObjectMytasksBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false));
//    }
//
//    @Override
//    public void onBindViewHolder(final ViewHolder holder, int position){
//        MyTask currMyTask = myTaskList.get(position);
//        holder.tv_title.setText(currMyTask.getTitle());
//        holder.tv_description.setText(currMyTask.getDescription());
//        holder.tv_location.setText("Location"); //TODO: Implement
//        holder.tv_manager.setText(currMyTask.getCreatorId()); //TODO: Change to Display Name
//        holder.tv_date.setText("2020/01/01");
////        holder.tv_date.setText(myTaskList.get(position).getTimes().get(AssignmentStatus.ASSIGNED.name()).toDate().toString());
//        holder.tv_status.setText(currMyTask.getProgressStatus().name());
//    }
//
//    @Override
//    public int getItemCount(){return myTaskList.size();}
//
//    public class ViewHolder extends RecyclerView.ViewHolder{
//        public final TextView tv_title;
//        public final TextView tv_location;
//        public final TextView tv_description;
//        public final TextView tv_manager;
//        public final TextView tv_date;
//        public final TextView tv_status;
//
//        public ViewHolder(ObjectMytasksBinding binding){
//            super(binding.getRoot());
//            tv_title = binding.objTaskListTvTitle;
//            tv_location = binding.objTaskListTvLocation;
//            tv_description = binding.objTaskListTvDescription;
//            tv_manager = binding.objTaskListTvManager;
//            tv_date = binding.objTaskListTvDate;
//            tv_status = binding.objTaskListTvStatus;
//
//            if(listener != null && listener instanceof OnClickInteractionListener){
//
//            }
//        }
//
//    }
//    public void setMyTaskList(List<MyTask> newList){
//        myTaskList = newList;
//        notifyDataSetChanged();
//    }
//
//}
