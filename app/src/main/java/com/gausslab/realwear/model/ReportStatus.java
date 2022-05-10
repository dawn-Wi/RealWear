package com.gausslab.realwear.model;

import android.support.annotation.NonNull;

import com.gausslab.realwear.App;
import com.gausslab.realwear.R;

public enum ReportStatus
{
    SUBMITTED,
    CONFIRMED;

    @NonNull
    @Override
    public String toString()
    {
        switch(this)
        {
            case SUBMITTED:
                return App.getStringResource(R.string.enum_assignmentStatus_unassigned);
            case CONFIRMED:
                return App.getStringResource(R.string.enum_assignmentStatus_assigned);
        }
        return super.toString();
    }
}


