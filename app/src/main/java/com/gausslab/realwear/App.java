package com.gausslab.realwear;

import android.app.Application;
import android.content.Context;

import androidx.core.content.ContextCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class App extends Application {
    private static final MutableLiveData<Boolean> isWorking = new MutableLiveData<>(false);
    private static FileService fileService;
    private static Context mContext;

    public static String getDeviceQrImagePath(String deviceId)
    {
        return "deviceQrImages/device_" + deviceId + ".jpg";
    }

    public static String getFileProvider() {
        return "com.gausslab.realwear.fileprovider";
    }

    public static FileService getFileService() {
        return fileService;
    }

    public static void setFileService(FileService fs) {
        fileService = fs;
    }

    public static String getTaskStepImagePath(String taskId, String stepNumber)
    {
        return "taskImages/task_" + taskId + "/stepImages/step_" + stepNumber + ".jpg";
    }

    public static String getReportImagePath(String reportId)
    {
        return "reportImages/report_" + reportId + ".jpg";
    }

    public static int getCardColor(ColorName colorName)
    {
        return getCardColor(colorName, ColorType.NORMAL);
    }

    public static int getCardColor(ColorName colorName, ColorType type)
    {
        switch(colorName)
        {
            case GREEN:
                if(type == ColorType.FADED)
                    return ContextCompat.getColor(mContext, R.color.cardBackground_green_faded);
                return ContextCompat.getColor(mContext, R.color.cardBackground_green);
            case YELLOW:
                if(type == ColorType.FADED)
                    return ContextCompat.getColor(mContext, R.color.cardBackground_yellow_faded);
                return ContextCompat.getColor(mContext, R.color.cardBackground_yellow);
            case RED:
                if(type == ColorType.FADED)
                    return ContextCompat.getColor(mContext, R.color.cardBackground_red_faded);
                return ContextCompat.getColor(mContext, R.color.cardBackground_red);
            case WHITE:
                if(type == ColorType.FADED)
                    return ContextCompat.getColor(mContext, R.color.cardBackground_white_faded);
                return ContextCompat.getColor(mContext, R.color.cardBackground_white);
            default:
                return 0;
        }
    }

    public static boolean equalHelper(Object a, Object b)
    {
        if(a == null && b == null)
            return true;
        if(a == null || b == null)
            return false;
        else
            return a.equals(b);
    }

    public static LiveData<Boolean> isWorking()
    {
        return isWorking;
    }

    public static void setIsWorking(boolean val)
    {
        isWorking.setValue(val);
    }
    public static void setContext(Context c)
    {
        mContext = c;
    }


    public enum ColorName
    {
        RED,
        YELLOW,
        GREEN,
        WHITE
    }
    public enum ColorType
    {
        NORMAL,
        FADED
    }

}
