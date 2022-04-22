package com.gausslab.realwear.util.component.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.gausslab.realwear.util.component.ComponentData;
import com.gausslab.realwear.util.component.ViewComponent;
import com.gausslab.realwear.databinding.ComponentImageBinding;

public class ImageViewHolder extends BaseViewHolder
{
    protected final TextView tv_label;
    protected final ImageView iv_image;

    public ImageViewHolder(@NonNull ComponentImageBinding binding)
    {
        super(binding.getRoot());
        iv_image = binding.compReportIvImage;
        tv_label = binding.compReportTvImageLabel;
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
        if(data.getPinHolder().getImageDrawable() == null)
            iv_image.setVisibility(View.GONE);
        else
        {
            iv_image.setImageDrawable(data.getPinHolder().getImageDrawable());
            iv_image.setVisibility(View.VISIBLE);
        }
    }
}