package com.gausslab.realwear;
import static com.gausslab.realwear.App.equalHelper;

import android.graphics.drawable.Drawable;
import com.google.firebase.firestore.Exclude;
import java.util.ArrayList;
import java.util.List;

public class ImagePinHolder
{
    private Drawable baseImageDrawable;
    private Drawable pinnedImageDrawable;
    private List<ImagePin> imagePinList;

    public ImagePinHolder()
    {
        imagePinList = new ArrayList<>();
    }

    public ImagePinHolder(Drawable baseImageDrawable)
    {
        this.baseImageDrawable = baseImageDrawable;
        imagePinList = new ArrayList<ImagePin>();
    }

    public ImagePinHolder(Drawable baseImageDrawable, List<ImagePin> list)
    {
        this.baseImageDrawable = baseImageDrawable;
        this.imagePinList = list;
    }

    @Override
    public boolean equals(Object obj)
    {
        if(!(obj instanceof ImagePinHolder))
            return false;
        ImagePinHolder comparePinHolder = (ImagePinHolder) obj;
        if(!equalHelper(baseImageDrawable, comparePinHolder.getBaseImageDrawable()))
            return false;
        if(!equalHelper(pinnedImageDrawable, comparePinHolder.getPinnedImageDrawable()))
            return false;
        return equalHelper(imagePinList, comparePinHolder.getImagePinList());
    }

    @Exclude
    public Drawable getImageDrawable()
    {
        if(pinnedImageDrawable != null && imagePinList.size() > 0)
            return pinnedImageDrawable;
        return baseImageDrawable;
    }

    @Exclude
    public void addImagePin(ImagePin toAdd)
    {
        imagePinList.add(toAdd);
    }

    @Exclude
    public Drawable getBaseImageDrawable()
    {
        return baseImageDrawable;
    }

    @Exclude
    public void setBaseImageDrawable(Drawable baseImageDrawable) { this.baseImageDrawable = baseImageDrawable; }

    @Exclude
    public Drawable getPinnedImageDrawable() { return pinnedImageDrawable; }

    @Exclude
    public void setPinnedImageDrawable(Drawable pinnedImageDrawable) { this.pinnedImageDrawable = pinnedImageDrawable; }

    public List<ImagePin> getImagePinList()
    {
        return imagePinList;
    }

    public void setImagePinList(List<ImagePin> imagePinList)
    {
        this.imagePinList = imagePinList;
    }
}
