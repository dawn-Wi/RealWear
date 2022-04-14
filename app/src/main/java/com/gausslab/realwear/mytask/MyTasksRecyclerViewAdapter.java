package com.gausslab.realwear.mytask;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.gausslab.realwear.adapter_listener_interface.OnClickInteractionListener;
import com.gausslab.realwear.viewmodel.MyTasksViewModel;
import com.gausslab.realwear.databinding.ObjectMytasksBinding;
import com.gausslab.realwear.model.MyTask;

import java.util.List;

public class MyTasksRecyclerViewAdapter extends RecyclerView.Adapter<MyTasksRecyclerViewAdapter.ViewHolder> {

    private List<MyTask> myTaskList;
    private OnClickInteractionListener<MyTask> listener;

    public MyTasksRecyclerViewAdapter(List<MyTask> items, OnClickInteractionListener<MyTask> listener){
        myTaskList = items;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        return new ViewHolder(ObjectMytasksBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position){
        MyTask currMyTask = myTaskList.get(position);
        holder.tv_title.setText(currMyTask.getTitle());
        holder.tv_description.setText(currMyTask.getDescription());
        holder.tv_location.setText("Location"); //TODO: Implement
        holder.tv_manager.setText(currMyTask.getCreatorId()); //TODO: Change to Display Name
        holder.tv_date.setText("2020/01/01");
//        holder.tv_date.setText(myTaskList.get(position).getTimes().get(AssignmentStatus.ASSIGNED.name()).toDate().toString());
        holder.tv_status.setText(currMyTask.getProgressStatus().name());
    }

    @Override
    public int getItemCount(){return myTaskList.size();}

    public class ViewHolder extends RecyclerView.ViewHolder{
        public final TextView tv_title;
        public final TextView tv_location;
        public final TextView tv_description;
        public final TextView tv_manager;
        public final TextView tv_date;
        public final TextView tv_status;

        public ViewHolder(ObjectMytasksBinding binding){
            super(binding.getRoot());
            tv_title = binding.objTaskListTvTitle;
            tv_location = binding.objTaskListTvLocation;
            tv_description = binding.objTaskListTvDescription;
            tv_manager = binding.objTaskListTvManager;
            tv_date = binding.objTaskListTvDate;
            tv_status = binding.objTaskListTvStatus;
        }

    }
    public void setMyTaskList(List<MyTask> newList){
        myTaskList = newList;
        notifyDataSetChanged();
    }

//    private List<MyTask> taskList;
//    protected OnItemInteractionListener<MyTask> listener;
//    private MyTasksViewModel myTasksViewModel;
//
//    public MyTasksRecyclerViewAdapter(List<MyTask> items, MyTasksViewModel mvm,OnItemInteractionListener<MyTask> clickListener){
//        taskList = items;
//        myTasksViewModel = mvm;
//        listener = clickListener;
//    }
//
//    @Override
//    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        return new ViewHolder(ObjectMytasksBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
//    }
//
//    @Override
//    public void onBindViewHolder(final ViewHolder holder, int position) {
//        MyTask currTask = taskList.get(position);
//        holder.tv_title.setText(currTask.getTitle());
//        holder.tv_location.setText("Location"); //TODO: Implement
//        holder.tv_manager.setText(currTask.getCreatorId()); //TODO: Change to Display Name
//        //holder.tv_date.setText(currTask.getTimes().get(AssignmentStatus.ASSIGNED.name()).toDate().toString());
//        holder.tv_status.setText(currTask.getProgressStatus().name());
//
//        holder.bt_options.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View v)
//            {
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                    holder.bt_options.showContextMenu(v.getX(), v.getY());
//                }
//            }
//        });
//
//        if(listener != null)
//        {
//            if(listener instanceof OnClickInteractionListener)
//            {
//                holder.card.setOnClickListener(new View.OnClickListener()
//                {
//                    @Override
//                    public void onClick(View view)
//                    {
//                        ((OnClickInteractionListener<MyTask>) listener).onItemClick(taskList.get(holder.getAbsoluteAdapterPosition()));
//                    }
//                });
//            }
//            if(listener instanceof OnContextMenuInteractionListener)
//            {
//                holder.card.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener()
//                {
//                    @Override
//                    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
//                    {
//                        menu.setHeaderTitle("Select Action");
//                        MenuItem edit = menu.add(Menu.NONE, 1, 1, "Edit");
//                        MenuItem remove = menu.add(Menu.NONE, 3, 3, "Remove");
//                        edit.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener()
//                        {
//                            @Override
//                            public boolean onMenuItemClick(MenuItem menuItem)
//                            {
//                                ((OnContextMenuInteractionListener<MyTask>) listener).onContextEdit(taskList.get(holder.getAbsoluteAdapterPosition()));
//                                return true;
//                            }
//                        });
//                        remove.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener()
//                        {
//                            @Override
//                            public boolean onMenuItemClick(MenuItem item)
//                            {
//                                ((OnContextMenuInteractionListener<MyTask>) listener).onContextRemove(taskList.get(holder.getAbsoluteAdapterPosition()));
//                                return true;
//                            }
//                        });
//                    }
//                });
//            }
//        }
//    }
//
//    public void setTaskList(List<MyTask> newList)
//    {
//        TaskListDiffUtil diffUtil = new TaskListDiffUtil(taskList, newList);
//        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffUtil);
//        diffResult.dispatchUpdatesTo(this);
//        taskList = newList;
//    }
//
//    @Override
//    public int getItemCount()
//    {
//        return taskList.size();
//    }
//
//    public class ViewHolder extends RecyclerView.ViewHolder
//    {
//        public final TextView tv_title;
//        public final TextView tv_location;
//        public final TextView tv_manager;
//        public final TextView tv_date;
//        public final TextView tv_status;
//        public final ImageButton bt_options;
//        public final CardView card;
//
//        public ViewHolder(@NonNull ObjectMytasksBinding binding)
//        {
//            super(binding.getRoot());
//            tv_title = binding.objTaskListTvTitle;
//            tv_location = binding.objTaskListTvLocation;
//            tv_manager = binding.objTaskListTvManager;
//            tv_date = binding.objTaskListTvDate;
//            tv_status = binding.objTaskListTvStatus;
//            bt_options = binding.objTaskListBtOptions;
//            card = binding.objTasklistCard;
//        }
//
//        @Override
//        public String toString()
//        {
//            return super.toString() + " '" + tv_title.getText() + "'";
//        }
//    }
}

//public class MyTasksRecyclerViewAdapter extends TaskRecyclerViewAdapter {
//
//    public MyTasksRecyclerViewAdapter(List<MyTask> items) { super(items);}
//
//    public MyTasksRecyclerViewAdapter(List<MyTask> items, OnItemInteractionListener<MyTask> clickListener) {
//        super(items, clickListener);
//    }
//
//    @Override
//    public void onBindViewHolder(ViewHolder holder, int position){
//        super.onBindViewHolder(holder,position);
//        MyTask myTask = taskList.get(position);
//        if(myTask.getProgressStatus() == ProgressStatus.PENDING_REVIEW){
//            holder.tv_status.getBackground().setTint(App.getCardColor(App.ColorName.GREEN));
//        }
//        else if(myTask.getProgressStatus() == ProgressStatus.STARTED)
//        {
//            holder.tv_status.getBackground().setTint(App.getCardColor(App.ColorName.YELLOW));
//        }
//        else if(myTask.getProgressStatus() == ProgressStatus.REJECTED)
//        {
//            holder.tv_status.getBackground().setTint(App.getCardColor(App.ColorName.RED));
//        }
//        else if(myTask.getProgressStatus() == ProgressStatus.NOT_STARTED)
//        {
//            //holder.tv_status.getBackground().setTint(App.getCardColor(App.ColorName.GREEN));
//        }
//    }
//}