package com.gausslab.realwear;

import static com.gausslab.realwear.App.equalHelper;

public class ImagePin
{
    private float xPosPercent;
    private float yPosPercent;
    private String details;

    public ImagePin()
    {

    }

    public ImagePin(float xPercent, float yPercent, String details)
    {
        xPosPercent = xPercent;
        yPosPercent = yPercent;
        this.details = details;
    }

    public boolean equals(Object obj)
    {
        if(!(obj instanceof ImagePin))
            return false;
        ImagePin comparePin = (ImagePin) obj;
        if(!equalHelper(details, comparePin.getDetails()))
            return false;
        if(xPosPercent != comparePin.getxPosPercent())
            return false;
        return yPosPercent == comparePin.getyPosPercent();
    }

    public float getxPosPercent()
    {
        return xPosPercent;
    }

    public void setxPosPercent(float xPosPercent)
    {
        this.xPosPercent = xPosPercent;
    }

    public float getyPosPercent()
    {
        return yPosPercent;
    }

    public void setyPosPercent(float yPosPercent)
    {
        this.yPosPercent = yPosPercent;
    }

    public String getDetails()
    {
        return details;
    }

    public void setDetails(String details)
    {
        this.details = details;
    }
}
