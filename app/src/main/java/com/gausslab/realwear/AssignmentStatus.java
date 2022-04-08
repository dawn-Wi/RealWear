package com.gausslab.realwear;

public enum AssignmentStatus
{
    UNASSIGNED,
    ASSIGNED,
    OPEN,
    CLOSED;

    public static AssignmentStatus getAssignmentStatusFromString(String string)
    {
        if(string == null)
            return UNASSIGNED;
        switch(string)
        {
            case "UNASSIGNED":
                return UNASSIGNED;
            case "ASSIGNED":
                return ASSIGNED;
            case "OPEN":
                return OPEN;
            case "CLOSED":
                return CLOSED;
            default:
                return null;
        }
    }
}
