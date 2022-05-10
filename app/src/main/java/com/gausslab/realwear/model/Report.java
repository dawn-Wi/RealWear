package com.gausslab.realwear.model;

import static com.gausslab.realwear.App.equalHelper;
import com.gausslab.realwear.util.component.ViewComponent;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.Exclude;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Report
{
    private String reportId;
    private ReportStatus reportStatus;
    private String userId;
    private String userDisplayName;
    private Map<String, Timestamp> times;
    private String details;
    private List<ViewComponent> attachedViewComponents;

    public Report()
    {
        times = new HashMap<>();
        attachedViewComponents = new ArrayList<>();
    }

    public Report(String userId, String userDisplayName)
    {
        this();
        this.userId = userId;
        this.userDisplayName = userDisplayName;
    }

    @Exclude
    public void addTime(ReportStatus status, Timestamp time)
    {
        times.put(status.name(), time);
    }

    @Exclude
    public Timestamp getTime(ReportStatus status)
    {
        return times.get(status);
    }

    @Exclude
    public void clearTimes()
    {
        times.clear();
    }

    @Exclude
    public void addViewComponent(ViewComponent component)
    {
        attachedViewComponents.add(component);
    }

    @Exclude
    public void removeViewComponent(ViewComponent component)
    {
        attachedViewComponents.remove(component);
    }

    @Exclude
    public void clearViewComponents()
    {
        attachedViewComponents.clear();
    }

    public String getReportId()
    {
        return reportId;
    }

    public void setReportId(String reportId)
    {
        this.reportId = reportId;
    }

    public ReportStatus getReportStatus()
    {
        return reportStatus;
    }

    public void setReportStatus(ReportStatus reportStatus)
    {
        this.reportStatus = reportStatus;
    }

    public String getUserId()
    {
        return userId;
    }

    public void setUserId(String userId)
    {
        this.userId = userId;
    }

    public String getUserDisplayName()
    {
        return userDisplayName;
    }

    public void setUserDisplayName(String userDisplayName)
    {
        this.userDisplayName = userDisplayName;
    }

    public Map<String, Timestamp> getTimes()
    {
        return times;
    }

    public void setTimes(Map<String, Timestamp> times)
    {
        this.times = times;
    }

    public String getDetails()
    {
        return details;
    }

    public void setDetails(String details)
    {
        this.details = details;
    }

    public List<ViewComponent> getAttachedViewComponents()
    {
        return attachedViewComponents;
    }

    public void setAttachedViewComponents(List<ViewComponent> attachedViewComponents)
    {
        this.attachedViewComponents = attachedViewComponents;
    }

    @Override
    public boolean equals(Object obj)
    {
        if(!(obj instanceof Report))
            return false;
        Report compareReport = (Report) obj;
        if(!equalHelper(reportId, compareReport.getReportId()))
            return false;
        if(!equalHelper(reportStatus, compareReport.getReportStatus()))
            return false;
        if(!equalHelper(userId, compareReport.getUserId()))
            return false;
        if(!equalHelper(userDisplayName, compareReport.getUserDisplayName()))
            return false;
        if(!equalHelper(times, compareReport.getTimes()))
            return false;
        if(!equalHelper(details, compareReport.getDetails()))
            return false;
        return equalHelper(attachedViewComponents, compareReport.getAttachedViewComponents());
    }
}
