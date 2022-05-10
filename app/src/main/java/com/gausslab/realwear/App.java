package com.gausslab.realwear;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;

import androidx.core.content.ContextCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.gausslab.realwear.repository.DeviceRepository;
import com.gausslab.realwear.repository.ReportRepository;
import com.gausslab.realwear.repository.TaskRepository;
import com.gausslab.realwear.repository.UserRepository;

import java.util.Locale;

public class App extends Application
{
    private static final MutableLiveData<Boolean> isWorking = new MutableLiveData<>(false);
    private static FileService fileService;
    private static Resources mResources;
    private static Context mContext;
    private static Locale mLocale;

    private static final DeviceRepository deviceRepository = DeviceRepository.getInstance();
    private static final ReportRepository reportRepository = ReportRepository.getInstance();
    private static final TaskRepository taskRepository = TaskRepository.getInstance();
    private static final UserRepository userRepository = UserRepository.getInstance();

    public static Locale getLocale()
    {
        return mLocale;
    }

    public static void logout()
    {
        deviceRepository.logout();
        reportRepository.logout();
        //taskRepository.logout();
        userRepository.logout();
    }

    public static String getStringResource(int id)
    {
        return mResources.getString(id);
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
        mResources = getResources();
        mContext = getApplicationContext();
        mLocale = mResources.getConfiguration().getLocales().get(0);
    }

    public static String getTaskAttachedImagePath(String subPath)
    {
        return "taskAttachedImages/task_" + subPath + ".jpg";
    }

    public static String getTaskStepImagePath(String taskId, String stepNumber)
    {
        return "taskImages/task_" + taskId + "/stepImages/step_" + stepNumber + ".jpg";
    }

    public static String getDeviceQrImagePath(String deviceId)
    {
        return "deviceQrImages/device_" + deviceId + ".jpg";
    }

    public static String getDeviceImagePath(String deviceId)
    {
        return "deviceImages/device_" + deviceId + ".jpg";
    }

    public static String getReportImagePath(String reportId)
    {
        return "reportImages/report_" + reportId + ".jpg";
    }


    public static String getFileProvider() {
        return "com.gausslab.realwear.fileprovider";
    }
    public static String getPdfFileProvider()
    {
        return "com.gausslab.arlogbook.pdffileprovider";
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

    public static String getQRImagePath()
    {
        return "qrImages/qr_temp.jpg";
    }


    public static FileService getFileService()
    {
        return fileService;
    }

    public static void setFileService(FileService fs)
    {
        fileService = fs;
    }

    public static LiveData<Boolean> isWorking()
    {
        return isWorking;
    }

    public static void setIsWorking(boolean val)
    {
        isWorking.setValue(val);
    }

    public static void postIsWorking(boolean val) { isWorking.postValue(val); }


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