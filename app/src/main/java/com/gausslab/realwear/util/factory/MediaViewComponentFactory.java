package com.gausslab.realwear.util.factory;

import static com.gausslab.realwear.util.component.ViewComponent.VIEW_TYPE_IMAGE;
import static com.gausslab.realwear.util.component.ViewComponent.VIEW_TYPE_TEXT;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.gausslab.realwear.util.component.ViewComponent;
import com.gausslab.realwear.util.adapter.adapter_listener_interface.OnContextMenuInteractionListener;
import com.gausslab.realwear.databinding.ComponentImageBinding;
import com.gausslab.realwear.databinding.ComponentTextBinding;
import com.gausslab.realwear.util.component.viewholder.RemovableImageViewHolder;
import com.gausslab.realwear.util.component.viewholder.TextViewHolder;

public class MediaViewComponentFactory
{
    public static RecyclerView.ViewHolder getViewHolder(int viewType, LayoutInflater layoutInflater, ViewGroup parent, boolean attachToParent, OnContextMenuInteractionListener<ViewComponent> listener)
    {
        switch(viewType)
        {
            case VIEW_TYPE_TEXT:
                return new TextViewHolder(ComponentTextBinding.inflate(layoutInflater, parent, attachToParent));
            case VIEW_TYPE_IMAGE:
                return new RemovableImageViewHolder(ComponentImageBinding.inflate(layoutInflater, parent, attachToParent), listener);
            default:
                return new TextViewHolder(ComponentTextBinding.inflate(layoutInflater, parent, attachToParent));
        }
    }

}
