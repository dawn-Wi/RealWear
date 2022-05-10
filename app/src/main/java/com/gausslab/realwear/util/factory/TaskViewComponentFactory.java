package com.gausslab.realwear.util.factory;

import static com.gausslab.realwear.util.component.ViewComponent.VIEW_TYPE_IMAGE;
import static com.gausslab.realwear.util.component.ViewComponent.VIEW_TYPE_TEXT;
import static com.gausslab.realwear.util.component.ViewComponent.VIEW_TYPE_TEXT_EXPANDABLE;
import static com.gausslab.realwear.util.component.ViewComponent.VIEW_TYPE_TEXT_HEADER;
import static com.gausslab.realwear.util.component.ViewComponent.VIEW_TYPE_TEXT_SUB;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.gausslab.realwear.databinding.ComponentTextBinding;
import com.gausslab.realwear.databinding.ComponentTextExpandableBinding;
import com.gausslab.realwear.databinding.ComponentTextHeaderBinding;
import com.gausslab.realwear.databinding.ComponentTextSubtextBinding;
import com.gausslab.realwear.model.Device;
import com.gausslab.realwear.model.Task;
import com.gausslab.realwear.repository.DeviceRepository;
import com.gausslab.realwear.util.component.ComponentData;
import com.gausslab.realwear.util.component.ViewComponent;
import com.gausslab.realwear.util.component.viewholder.ExpandableTextViewHolder;
import com.gausslab.realwear.util.component.viewholder.HeaderTextViewHolder;
import com.gausslab.realwear.util.component.viewholder.ImageViewHolder;
import com.gausslab.realwear.util.component.viewholder.SubTextViewHolder;
import com.gausslab.realwear.util.component.viewholder.TextViewHolder;

import java.util.ArrayList;
import java.util.List;

public class TaskViewComponentFactory
{
    private static final DeviceRepository deviceRepository = DeviceRepository.getInstance();

    public static RecyclerView.ViewHolder getViewHolder(int viewType, LayoutInflater layoutInflater, ViewGroup parent, boolean attachToParent)
    {
        switch(viewType)
        {
            case VIEW_TYPE_TEXT_HEADER:
                return new HeaderTextViewHolder(ComponentTextHeaderBinding.inflate(layoutInflater, parent, attachToParent));
            case VIEW_TYPE_TEXT:
                return new TextViewHolder(ComponentTextBinding.inflate(layoutInflater, parent, attachToParent));
            case VIEW_TYPE_TEXT_SUB:
                return new SubTextViewHolder(ComponentTextSubtextBinding.inflate(layoutInflater, parent, attachToParent));
            case VIEW_TYPE_TEXT_EXPANDABLE:
                return new ExpandableTextViewHolder(ComponentTextExpandableBinding.inflate(layoutInflater, parent, attachToParent));
            case VIEW_TYPE_IMAGE:
                return new ImageViewHolder(com.gausslab.realwear.databinding.ComponentImageBinding.inflate(layoutInflater, parent, attachToParent));
            default:
                return new TextViewHolder(ComponentTextBinding.inflate(layoutInflater, parent, attachToParent));
        }
    }

    public static List<ViewComponent> generateTaskViewTextComponents(Task task)
    {
        List<ViewComponent> toReturn = new ArrayList<>();
        toReturn.add(new ViewComponent(VIEW_TYPE_TEXT_HEADER, new ComponentData("", task.getDescription())));

        List<ViewComponent> statusSubList = new ArrayList<>();
        if(task.getRejectedMessage() != null && task.getRejectedMessage().length() > 0)
            statusSubList.add(new ViewComponent(VIEW_TYPE_TEXT_SUB, new ComponentData("Rejection Reason", task.getRejectedMessage())));
        if(task.getTimes().get(task.getProgressStatus().name()) != null)
            statusSubList.add(new ViewComponent(VIEW_TYPE_TEXT_SUB, new ComponentData("Time", task.getTimes().get(task.getProgressStatus().name()).toDate().toString())));
        toReturn.add(new ViewComponent(VIEW_TYPE_TEXT_EXPANDABLE, new ComponentData("Status", task.getProgressStatus().name(), statusSubList)));

        if(task.getAssociatedDeviceName() != null && task.getAssociatedDeviceName().length() > 0)
        {
            List<ViewComponent> deviceSubList = new ArrayList<>();
            Device d = deviceRepository.getDevice(task.getAssociatedDeviceId());
            deviceSubList.add(new ViewComponent(VIEW_TYPE_TEXT_SUB, new ComponentData("Make", d.getMake())));
            deviceSubList.add(new ViewComponent(VIEW_TYPE_TEXT_SUB, new ComponentData("Model", d.getModel())));
            deviceSubList.add(new ViewComponent(VIEW_TYPE_TEXT_SUB, new ComponentData("Serial Number", d.getSerialNumber())));
            deviceSubList.add(new ViewComponent(VIEW_TYPE_TEXT_SUB, new ComponentData("Location", d.getLocation())));
            deviceSubList.add(new ViewComponent(VIEW_TYPE_TEXT_SUB, new ComponentData("Purchase Date", d.getPurchaseDate())));
            deviceSubList.add(new ViewComponent(VIEW_TYPE_TEXT_SUB, new ComponentData("Last Maintenance Date", d.getLastMaintenanceDate())));
            toReturn.add(new ViewComponent(VIEW_TYPE_TEXT_EXPANDABLE, new ComponentData("Associated Device", task.getAssociatedDeviceName(), deviceSubList)));
        }
        return toReturn;
    }
}
