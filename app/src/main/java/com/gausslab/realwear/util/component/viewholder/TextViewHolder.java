package com.gausslab.realwear.util.component.viewholder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.gausslab.realwear.util.component.ComponentData;
import com.gausslab.realwear.util.component.ViewComponent;
import com.gausslab.realwear.databinding.ComponentTextBinding;

public class TextViewHolder extends BaseViewHolder
{
    protected final TextView tv_label;
    protected final TextView tv_content;
    protected final ConstraintLayout cl_container;

    public TextViewHolder(@NonNull ComponentTextBinding binding)
    {
        super(binding.getRoot());
        tv_label = binding.compTextTvLabel;
        tv_content = binding.compTextTvContent;
        cl_container = binding.compTextClContainer;
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