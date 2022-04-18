package com.gausslab.realwear;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.gausslab.realwear.databinding.ObjectImagepinitemBinding;
import com.gausslab.realwear.model.ImagePin;

import java.util.List;

public class ImagePinRecyclerViewAdapter extends RecyclerView.Adapter<ImagePinRecyclerViewAdapter.ViewHolder>
{
    private final List<ImagePin> pinList;
    private OnItemInteractionListener listener;

    public ImagePinRecyclerViewAdapter(List<ImagePin> items)
    {
        pinList = items;
    }

    public ImagePinRecyclerViewAdapter(List<ImagePin> items, OnItemInteractionListener listener)
    {
        pinList = items;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        return new ViewHolder(ObjectImagepinitemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position)
    {
        holder.pinNumber.setText("" + (position + 1));
        holder.pinText.setText(pinList.get(position).getDetails());
        holder.cardView.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener()
        {
            @Override
            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
            {
                menu.setHeaderTitle(v.getResources().getText(R.string.context_menuTitle));
                MenuItem remove = menu.add(Menu.NONE, 1, 1, v.getResources().getText(R.string.context_remove));
                remove.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener()
                {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem)
                    {
                        listener.onContextRemove(pinList.get(holder.getAbsoluteAdapterPosition()));
                        return true;
                    }
                });
            }
        });
    }

    public void update()
    {
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount()
    {
        return pinList.size();
    }

    public interface OnItemInteractionListener
    {
        void onContextRemove(ImagePin pin);
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        public final TextView pinNumber;
        public final TextView pinText;
        public final CardView cardView;

        public ViewHolder(@NonNull ObjectImagepinitemBinding binding)
        {
            super(binding.getRoot());
            pinNumber = binding.objImagepinlistobjectTvPinNumber;
            pinText = binding.objImagepinlistobjectTvPinText;
            cardView = binding.objImagepinlistobjectCardview;
        }

        @Override
        public String toString()
        {
            return super.toString() + " '" + pinText.getText() + "'";
        }
    }
}