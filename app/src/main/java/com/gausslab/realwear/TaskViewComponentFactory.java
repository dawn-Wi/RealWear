package com.gausslab.realwear;

import static com.baec23.arlogbook.util.component.ViewComponent.VIEW_TYPE_IMAGE;
import static com.baec23.arlogbook.util.component.ViewComponent.VIEW_TYPE_TEXT;
import static com.baec23.arlogbook.util.component.ViewComponent.VIEW_TYPE_TEXT_EXPANDABLE;
import static com.baec23.arlogbook.util.component.ViewComponent.VIEW_TYPE_TEXT_HEADER;
import static com.baec23.arlogbook.util.component.ViewComponent.VIEW_TYPE_TEXT_SUB;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.baec23.arlogbook.databinding.ComponentImageBinding;
import com.baec23.arlogbook.databinding.ComponentTextBinding;
import com.baec23.arlogbook.databinding.ComponentTextExpandableBinding;
import com.baec23.arlogbook.databinding.ComponentTextHeaderBinding;
import com.baec23.arlogbook.databinding.ComponentTextSubtextBinding;
import com.baec23.arlogbook.model.Device;
import com.baec23.arlogbook.model.Task;
import com.baec23.arlogbook.repository.DeviceRepository;
import com.baec23.arlogbook.util.component.ViewComponent;
import com.baec23.arlogbook.util.component.data.ComponentData;
import com.baec23.arlogbook.util.component.viewholder.ExpandableTextViewHolder;
import com.baec23.arlogbook.util.component.viewholder.HeaderTextViewHolder;
import com.baec23.arlogbook.util.component.viewholder.ImageViewHolder;
import com.baec23.arlogbook.util.component.viewholder.SubTextViewHolder;
import com.baec23.arlogbook.util.component.viewholder.TextViewHolder;
import com.gausslab.realwear.model.MyTask;

import java.util.ArrayList;
import java.util.List;

public class TaskViewComponentFactory
{
    private static final DeviceRepository deviceRepository = DeviceRepository.getInstance();
t
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
                return new ImageViewHolder(ComponentImageBinding.inflate(layoutInflater, parent, attachToParent));
            default:
                return new TextViewHolder(ComponentTextBinding.inflate(layoutInflater, parent, attachToParent));
        }
    }

    public static List<ViewComponent> generateTaskViewTextComponents(MyTask mytask)
    {
        List<ViewComponent> toReturn = new ArrayList<>();
        toReturn.add(new ViewComponent(VIEW_TYPE_TEXT_HEADER, new ComponentData("", mytask.getDescription())));

        List<ViewComponent> statusSubList = new ArrayList<>();
        if(mytask.getRejectedMessage() != null && mytask.getRejectedMessage().length() > 0)
            statusSubList.add(new ViewComponent(VIEW_TYPE_TEXT_SUB, new ComponentData("Rejection Reason", mytask.getRejectedMessage())));
        statusSubList.add(new ViewComponent(VIEW_TYPE_TEXT_SUB, new ComponentData("Time", mytask.getTimes().get(mytask.getProgressStatus().name()).toDate().toString())));
        toReturn.add(new ViewComponent(VIEW_TYPE_TEXT_EXPANDABLE, new ComponentData("Status", mytask.getProgressStatus().name(), statusSubList)));

        if(mytask.getAssociatedDeviceName() != null && mytask.getAssociatedDeviceName().length() > 0)
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
