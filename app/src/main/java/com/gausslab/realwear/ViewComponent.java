package com.gausslab.realwear;


public class ViewComponent
{
    public final static int VIEW_TYPE_TEXT = 1;
    public final static int VIEW_TYPE_TEXT_HEADER = 2;
    public final static int VIEW_TYPE_TEXT_WITH_DRAWABLE = 3;
    public final static int VIEW_TYPE_TEXT_EXPANDABLE = 4;
    public final static int VIEW_TYPE_TEXT_SUB = 5;
    public final static int VIEW_TYPE_IMAGE = 6;

    private int viewType;
    private ComponentData data;

    public ViewComponent()
    {
    }

    public ViewComponent(int viewType, ComponentData data)
    {
        this.viewType = viewType;
        this.data = data;
    }

    public ComponentData getData()
    {
        return data;
    }

    public void setData(ComponentData data)
    {
        this.data = data;
    }

    public int getViewType()
    {
        return viewType;
    }

    public void setViewType(int viewType)
    {
        this.viewType = viewType;
    }
}
