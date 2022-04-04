package com.gausslab.realwear;

import android.app.Application;

public class App extends Application {
    private static FileService fileService;

    public static String getFileProvider() {
        return "com.gausslab.realwear.fileprovider";
    }

    public static FileService getFileService() {
        return fileService;
    }

    public static void setFileService(FileService fs) {
        fileService = fs;
    }

    public static String getReportImagePath(String reportId)
    {
        return "reportImages/report_" + reportId + ".jpg";
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


}
