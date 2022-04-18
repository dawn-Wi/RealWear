package com.gausslab.realwear.viewholder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.gausslab.realwear.ComponentData;
import com.gausslab.realwear.ViewComponent;
import com.gausslab.realwear.databinding.ComponentTextSubtextBinding;

public class SubTextViewHolder extends BaseViewHolder
{
    protected final TextView tv_label;
    protected final TextView tv_content;
    protected final ConstraintLayout cl_container;

    public SubTextViewHolder(@NonNull ComponentTextSubtextBinding binding)
    {
        super(binding.getRoot());
        tv_label = binding.compTextsubtextTvLabel;
        tv_content = binding.compTextsubtextTvContent;
        cl_container = binding.compTextsubtextClContainer;
    }

    @Override
    public void bindData(ViewComponent viewComponent)
    {
        ComponentData data = viewComponent.getData();
        if(data.getLabelText() == null)
            tv_label.setVisibility(View.GONE);
        else
        {
            tv_label.setText(data.getLabelText());
            tv_label.setVisibility(View.VISIBLE);
        }
        if(data.getTextContent() == null)
            tv_content.setVisibility(View.GONE);
        else
        {
            tv_content.setText(data.getTextContent());
            tv_content.setVisibility(View.VISIBLE);
        }
    }
}