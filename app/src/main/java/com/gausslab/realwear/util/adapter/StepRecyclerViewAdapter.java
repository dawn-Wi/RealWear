package com.gausslab.realwear.util.adapter;

import android.graphics.drawable.Drawable;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.gausslab.realwear.R;
import com.gausslab.realwear.util.adapter.adapter_listener_interface.OnClickInteractionListener;
import com.gausslab.realwear.util.adapter.adapter_listener_interface.OnContextMenuInteractionListener;
import com.gausslab.realwear.util.adapter.adapter_listener_interface.OnItemInteractionListener;
import com.gausslab.realwear.databinding.ObjectMytaskstepBinding;
import com.gausslab.realwear.model.TaskStep;

import java.util.List;

public class StepRecyclerViewAdapter extends RecyclerView.Adapter<StepRecyclerViewAdapter.ViewHolder>
{
    private final List<TaskStep> stepList;
    private OnItemInteractionListener<TaskStep> listener;

    public StepRecyclerViewAdapter(List<TaskStep> items)
    {
        stepList = items;
    }

    public StepRecyclerViewAdapter(List<TaskStep> items, OnItemInteractionListener<TaskStep> listener)
    {
        stepList = items;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        return new ViewHolder(ObjectMytaskstepBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position)
    {
        holder.stepNumber.setText(stepList.get(position).getStepNumber());
        Drawable img = stepList.get(position).getPinnedImage().getImageDrawable();
        if(img != null)
            holder.image.setImageDrawable(stepList.get(position).getPinnedImage().getImageDrawable());
        else
            holder.image.setVisibility(View.GONE);
        holder.textContent.setText(stepList.get(position).getTextContent());
        if(listener != null && listener instanceof OnClickInteractionListener)
        {
            holder.cardView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    ((OnClickInteractionListener<TaskStep>) listener).onItemClick(stepList.get(holder.getAbsoluteAdapterPosition()));
                }
            });
        }
        else if(listener != null && listener instanceof OnContextMenuInteractionListener)
        {

            holder.cardView.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener()
            {
                @Override
                public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
                {
                    menu.setHeaderTitle(v.getResources().getText(R.string.context_menuTitle));
                    MenuItem edit = menu.add(Menu.NONE, 1, 1, v.getResources().getText(R.string.context_edit));
                    MenuItem remove = menu.add(Menu.NONE, 2, 2, v.getResources().getText(R.string.context_remove));
                    edit.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener()
                    {
                        @Override
                        public boolean onMenuItemClick(MenuItem menuItem)
                        {
                            ((OnContextMenuInteractionListener<TaskStep>) listener).onContextEdit(stepList.get(holder.getAbsoluteAdapterPosition()));
                            return true;
                        }
                    });
                    remove.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener()
                    {
                        @Override
                        public boolean onMenuItemClick(MenuItem menuItem)
                        {
                            ((OnContextMenuInteractionListener<TaskStep>) listener).onContextRemove(stepList.get(holder.getAbsoluteAdapterPosition()));
                            return true;
                        }
                    });
                }
            });
        }

    }

    @Override
    public int getItemCount()
    {
        return stepList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        public final TextView stepNumber;
        public final TextView textContent;
        public final ImageView image;
        public final CardView cardView;

        public ViewHolder(@NonNull ObjectMytaskstepBinding binding)
        {
            super(binding.getRoot());
            stepNumber = binding.objTaskstepTvStepNumber;
            textContent = binding.objTaskstepTvTextContent;
            image = binding.objTaskstepIvImageView;
            cardView = binding.objTaskstepCardview;
        }

        @Override
        public String toString()
        {
            return super.toString() + " '" + stepNumber.getText() + "'";
        }
    }
}