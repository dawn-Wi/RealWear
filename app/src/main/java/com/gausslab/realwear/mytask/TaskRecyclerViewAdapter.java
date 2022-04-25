package com.gausslab.realwear.mytask;

import static com.google.android.gms.common.internal.safeparcel.SafeParcelable.NULL;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.gausslab.realwear.util.TaskListDiffUtil;
import com.gausslab.realwear.util.adapter.adapter_listener_interface.OnItemInteractionListener;
import com.gausslab.realwear.databinding.ObjectMytasksBinding;
import com.gausslab.realwear.model.MyTask;
import com.gausslab.realwear.viewmodel.MyTasksViewModel;

import java.util.List;

public class TaskRecyclerViewAdapter extends RecyclerView.Adapter<TaskRecyclerViewAdapter.ViewHolder> {

    protected List<MyTask> taskList;
    protected OnItemInteractionListener<MyTask> listener;
    protected MyTasksViewModel myTasksViewModel;

    public TaskRecyclerViewAdapter(List<MyTask> items, MyTasksViewModel mvm) {
        taskList = items;
        myTasksViewModel = mvm;
    }

    public TaskRecyclerViewAdapter(List<MyTask> items, MyTasksViewModel mvm,OnItemInteractionListener<MyTask> clickListener) {
        taskList = items;
        myTasksViewModel = mvm;
        listener = clickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(ObjectMytasksBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        MyTask currTask = taskList.get(position);
        holder.tv_title.setText(currTask.getTitle());
        holder.tv_location.setText("Location"); //TODO: Implement
//        myTasksViewModel.loadTaskCreatorName(currTask.getCreatorId());
        holder.tv_description.setText(currTask.getDescription());
        if(currTask.getCreatorUserDisplayName()==null){
            holder.tv_manager.setText(currTask.getCreatorId());
        }else{
            holder.tv_manager.setText(currTask.getCreatorUserDisplayName());
        }
        //holder.tv_date.setText(currTask.getTimes().get(AssignmentStatus.ASSIGNED.name()).toDate().toString());
        holder.tv_status.setText(currTask.getProgressStatus().name());

    }

    public void setTaskList(List<MyTask> newList) {
        TaskListDiffUtil diffUtil = new TaskListDiffUtil(taskList, newList);
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffUtil);
        diffResult.dispatchUpdatesTo(this);
        taskList = newList;
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView tv_title;
        public final TextView tv_location;
        public final TextView tv_description;
        public final TextView tv_manager;
        public final TextView tv_date;
        public final TextView tv_status;
        public final CardView card;


        public ViewHolder(@NonNull ObjectMytasksBinding binding) {
            super(binding.getRoot());
            tv_title = binding.objTaskListTvTitle;
            tv_location = binding.objTaskListTvLocation;
            tv_description = binding.objTaskListTvDescription;
            tv_manager = binding.objTaskListTvManager;
            tv_date = binding.objTaskListTvDate;
            tv_status = binding.objTaskListTvStatus;
            card = binding.objTasklistCard;
        }

        @Override
        public String toString() {
            return super.toString() + " '" + tv_title.getText() + "'";
        }
    }
}

