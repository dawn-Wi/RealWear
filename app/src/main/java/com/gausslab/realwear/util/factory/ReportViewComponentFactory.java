package com.gausslab.realwear.util.factory;

import static com.gausslab.realwear.util.component.ViewComponent.VIEW_TYPE_IMAGE;
import static com.gausslab.realwear.util.component.ViewComponent.VIEW_TYPE_TEXT;
import static com.gausslab.realwear.util.component.ViewComponent.VIEW_TYPE_TEXT_EXPANDABLE;
import static com.gausslab.realwear.util.component.ViewComponent.VIEW_TYPE_TEXT_HEADER;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;


import com.gausslab.realwear.util.component.ComponentData;
import com.gausslab.realwear.repository.ReportRepository;
import com.gausslab.realwear.util.component.ViewComponent;
import com.gausslab.realwear.databinding.ComponentImageBinding;
import com.gausslab.realwear.databinding.ComponentTextBinding;
import com.gausslab.realwear.databinding.ComponentTextExpandableBinding;
import com.gausslab.realwear.databinding.ComponentTextHeaderBinding;
import com.gausslab.realwear.model.Report;
import com.gausslab.realwear.util.component.viewholder.ExpandableTextViewHolder;
import com.gausslab.realwear.util.component.viewholder.HeaderTextViewHolder;
import com.gausslab.realwear.util.component.viewholder.ImageViewHolder;
import com.gausslab.realwear.util.component.viewholder.TextViewHolder;

import java.util.ArrayList;
import java.util.List;

public class ReportViewComponentFactory
{
    private final ReportRepository reportRepository = ReportRepository.getInstance();

    public static RecyclerView.ViewHolder getViewHolder(int viewType, LayoutInflater layoutInflater, ViewGroup parent, boolean attachToParent)
    {
        switch(viewType)
        {
            case VIEW_TYPE_TEXT_HEADER:
                return new HeaderTextViewHolder(ComponentTextHeaderBinding.inflate(layoutInflater, parent, attachToParent));
            case VIEW_TYPE_TEXT:
                return new TextViewHolder(ComponentTextBinding.inflate(layoutInflater, parent, attachToParent));
//            case VIEW_TYPE_TEXT_SUB:
//                return new SubTextViewHolder(com.baec23.arlogbook.databinding.ComponentTextSubtextBinding.inflate(layoutInflater, parent, attachToParent));
            case VIEW_TYPE_TEXT_EXPANDABLE:
                return new ExpandableTextViewHolder(ComponentTextExpandableBinding.inflate(layoutInflater, parent, attachToParent));
            case VIEW_TYPE_IMAGE:
                return new ImageViewHolder(ComponentImageBinding.inflate(layoutInflater, parent, attachToParent));
            default:
                return new TextViewHolder(ComponentTextBinding.inflate(layoutInflater, parent, attachToParent));
        }
    }

    public static List<ViewComponent> generateReportViewComponents(Report report)
    {
        List<ViewComponent> toReturn = new ArrayList<>();
        toReturn.add(new ViewComponent(VIEW_TYPE_TEXT_HEADER, new ComponentData("Description", report.getDetails())));
        toReturn.addAll(report.getAttachedViewComponents());
        return toReturn;
    }
}
