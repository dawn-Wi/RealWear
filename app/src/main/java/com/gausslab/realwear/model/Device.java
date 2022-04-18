package com.gausslab.realwear.model;


import androidx.annotation.NonNull;

public class Device
{
    String name;
    String deviceId;
    String make;
    String model;
    String serialNumber;
    String location;
    String lastMaintenanceDate;
    String purchaseDate;
    String qrDownloadUrl;
    String imageDownloadUrl;

    public Device()
    {

    }

    public Device(String name, String deviceId, String make, String model, String serialNumber, String location, String lastMaintenanceDate, String purchaseDate, String qrDownloadUrl, String imageDownloadUrl)
    {
        this.name = name;
        this.deviceId = deviceId;
        this.make = make;
        this.model = model;
        this.serialNumber = serialNumber;
        this.location = location;
        this.lastMaintenanceDate = lastMaintenanceDate;
        this.purchaseDate = purchaseDate;
        this.qrDownloadUrl = qrDownloadUrl;
        this.imageDownloadUrl = imageDownloadUrl;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getDeviceId()
    {
        return deviceId;
    }

    public void setDeviceId(String deviceId)
    {
        this.deviceId = deviceId;
    }

    public String getMake()
    {
        return make;
    }

    public void setMake(String make)
    {
        this.make = make;
    }

    public String getModel()
    {
        return model;
    }

    public void setModel(String model)
    {
        this.model = model;
    }

    public String getSerialNumber()
    {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber)
    {
        this.serialNumber = serialNumber;
    }

    public String getLocation()
    {
        return location;
    }

    public void setLocation(String location)
    {
        this.location = location;
    }

    public String getLastMaintenanceDate()
    {
        return lastMaintenanceDate;
    }

    public void setLastMaintenanceDate(String lastMaintenanceDate)
    {
        this.lastMaintenanceDate = lastMaintenanceDate;
    }

    public String getPurchaseDate()
    {
        return purchaseDate;
    }

    public void setPurchaseDate(String purchaseDate)
    {
        this.purchaseDate = purchaseDate;
    }

    public String getQrDownloadUrl()
    {
        return qrDownloadUrl;
    }

    public void setQrDownloadUrl(String qrDownloadUrl)
    {
        this.qrDownloadUrl = qrDownloadUrl;
    }

    public String getImageDownloadUrl()
    {
        return imageDownloadUrl;
    }

    public void setImageDownloadUrl(String imageDownloadUrl)
    {
        this.imageDownloadUrl = imageDownloadUrl;
    }



    @NonNull
    @Override
    public String toString()
    {
        return name;
    }
}
