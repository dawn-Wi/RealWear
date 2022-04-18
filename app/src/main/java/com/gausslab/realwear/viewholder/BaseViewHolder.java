package com.gausslab.realwear.viewholder;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gausslab.realwear.ViewComponent;

public abstract class BaseViewHolder extends RecyclerView.ViewHolder
{
    public BaseViewHolder(@NonNull View itemView)
    {
        super(itemView);
    }

    public abstract void bindData(ViewComponent viewComponent);
}
