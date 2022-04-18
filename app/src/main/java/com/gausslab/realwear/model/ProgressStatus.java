package com.gausslab.realwear.model;

public enum ProgressStatus
{
    NOT_STARTED,
    STARTED,
    PENDING_REVIEW,
    REJECTED,
    APPROVED;

    public static ProgressStatus getProgressStatusFromString(String string)
    {
        if(string == null)
            return NOT_STARTED;
        switch(string)
        {
            case "NOT_STARTED":
                return NOT_STARTED;
            case "STARTED":
                return STARTED;
            case "PENDING_REVIEW":
                return PENDING_REVIEW;
            case "REJECTED":
                return REJECTED;
            case "APPROVED":
                return APPROVED;
            default:
                return null;
        }
    }
}
