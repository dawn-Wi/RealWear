package com.gausslab.realwear.util.component;

import android.graphics.drawable.Drawable;

import com.gausslab.realwear.model.ImagePinHolder;

import java.util.List;

public class ComponentData
{
    String labelText;
    String textContent;
    ImagePinHolder pinHolder;
    List<ViewComponent> subComponents;

    public ComponentData()
    {
    }

    public ComponentData(String labelText, String textContent)
    {
        this.labelText = labelText;
        this.textContent = textContent;
    }

    public ComponentData(String labelText, String textContent, List<ViewComponent> subComponents)
    {
        this.labelText = labelText;
        this.textContent = textContent;
        this.subComponents = subComponents;
    }

    public ComponentData(String labelText, Drawable d)
    {
        this.labelText = labelText;
        this.pinHolder = new ImagePinHolder(d);
    }

    public String getLabelText() { return labelText; }

    public void setLabelText(String labelText) { this.labelText = labelText; }

    public String getTextContent()
    {
        return textContent;
    }

    public void setTextContent(String textContent)
    {
        this.textContent = textContent;
    }

    public ImagePinHolder getPinHolder()
    {
        return pinHolder;
    }

    public void setPinHolder(ImagePinHolder pinHolder)
    {
        this.pinHolder = pinHolder;
    }

    public List<ViewComponent> getSubComponents()
    {
        return subComponents;
    }

    public void setSubComponents(List<ViewComponent> subComponents)
    {
        this.subComponents = subComponents;
    }
}
