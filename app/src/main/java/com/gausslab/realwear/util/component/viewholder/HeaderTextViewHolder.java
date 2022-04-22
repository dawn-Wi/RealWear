package com.gausslab.realwear.util.component.viewholder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.gausslab.realwear.util.component.ComponentData;
import com.gausslab.realwear.util.component.ViewComponent;
import com.gausslab.realwear.databinding.ComponentTextHeaderBinding;

public class HeaderTextViewHolder extends BaseViewHolder
{
    private final TextView tv_description;

    public HeaderTextViewHolder(@NonNull ComponentTextHeaderBinding binding)
    {
        super(binding.getRoot());
        tv_description = binding.compTaskTvDescriptionText;
    }

    @Override
    public void bindData(ViewComponent viewComponent)
    {
        ComponentData data = viewComponent.getData();
        if(data.getTextContent() == null)
            tv_description.setVisibility(View.GONE);
        else
        {
            tv_description.setText(data.getTextContent());
            tv_description.setVisibility(View.VISIBLE);
        }
    }
}