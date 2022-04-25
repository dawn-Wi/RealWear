package com.gausslab.realwear.repository;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.gausslab.realwear.App;
import com.gausslab.realwear.FileService;
import com.gausslab.realwear.FirebaseDataSource;
import com.gausslab.realwear.model.Device;
import com.gausslab.realwear.model.Result;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DeviceRepository extends Repository
{
    //region Singleton Pattern
    private static final DeviceRepository INSTANCE = new DeviceRepository();
    private final MutableLiveData<Boolean> dataLoaded = new MutableLiveData<>(false);
    //endregion
    private final MutableLiveData<Boolean> deviceListUpdated = new MutableLiveData<>(false);
    private final Map<String, MutableLiveData<Boolean>> qrLoadedMap = new HashMap<>();
    private final Map<String, MutableLiveData<Boolean>> imageLoadedMap = new HashMap<>();
    private Map<String, Device> deviceMap = new HashMap<>();
    private Map<String, Drawable> deviceQrDrawableMap = new HashMap<>();
    private Map<String, Drawable> deviceImageDrawableMap = new HashMap<>();

    public static DeviceRepository getInstance()
    {
        return INSTANCE;
    }

    public void createNewDevice(Device toAdd, RepositoryCallback<Result> callback)
    {
        firebaseDataSource.getNewKey(FirebaseDataSource.KeyType.DEVICE, new FirebaseDataSource.DataSourceCallback<Result<String>>()
        {
            @Override
            public void onComplete(Result<String> result)
            {
                if(result instanceof Result.Success)
                {
                    Log.d("DEBUG", "DeviceRepository : createNewDevice() - Got new key");
                    String key = ((Result.Success<String>) result).getData();
                    toAdd.setDeviceId(key);
                    generateDeviceQr(toAdd, new RepositoryCallback<Result>()
                    {
                        @Override
                        public void onComplete(Result result)
                        {
                            Uri downloadUrl = ((Result.Success<Uri>) result).getData();
                            toAdd.setQrDownloadUrl(downloadUrl.toString());
                            firebaseDataSource.submitDataToCollection_specifiedDocumentName("devices", "device_" + key, toAdd, new FirebaseDataSource.DataSourceCallback<Result>()
                            {
                                @Override
                                public void onComplete(Result result)
                                {
                                    if(result instanceof Result.Success)
                                    {
                                        Log.d("DEBUG", "DeviceRepository : createNewDevice() - Submit successful");
                                        callback.onComplete(new Result.Success<String>(toAdd.getDeviceId()));
                                    }
                                    else
                                    {
                                        callback.onComplete(result);
                                    }
                                }
                            });
                        }
                    });
                }
                else
                {
                    callback.onComplete(result);
                }
            }
        });
    }

    public void updateDevice(Device device, RepositoryCallback<Result> callback)
    {
        firebaseDataSource.submitDataToCollection_specifiedDocumentName("devices", "device_" + device.getDeviceId(), device, new FirebaseDataSource.DataSourceCallback<Result>()
        {
            @Override
            public void onComplete(Result result)
            {
                callback.onComplete(result);
            }
        });
    }

    public List<Device> getDeviceList()
    {
        return new ArrayList<Device>(deviceMap.values());
    }

    public Device getDevice(String deviceId)
    {
        if(deviceMap.containsKey(deviceId))
            return deviceMap.get(deviceId);
        return null;
    }

    public Drawable getDeviceImageDrawable(String deviceId)
    {
        if(deviceMap.get(deviceId).getImageDownloadUrl() != null && deviceImageDrawableMap.containsKey(deviceId))
            return deviceImageDrawableMap.get(deviceId);
        else
            return null;
    }


    private void generateDeviceQr(Device device, RepositoryCallback<Result> callback)
    {
        String toEncode = "co.kr.gausslab.arlogbook.device_" + device.getDeviceId();
        generateDeviceQr_helper(toEncode, App.getDeviceQrImagePath(device.getDeviceId()), new RepositoryCallback<Result>()
        {
            @Override
            public void onComplete(Result result)
            {
                callback.onComplete(result);
            }
        });
    }

    private void generateDeviceQr_helper(String toEncode, String localDestinationPath, RepositoryCallback<Result> callback)
    {
        QRCodeWriter writer = new QRCodeWriter();
        try
        {
            BitMatrix bitMatrix = writer.encode(toEncode, BarcodeFormat.QR_CODE, 512, 512);
            int width = bitMatrix.getWidth();
            int height = bitMatrix.getHeight();
            Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
            for(int x = 0; x < width; x++)
            {
                for(int y = 0; y < height; y++)
                {
                    bmp.setPixel(x, y, bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE);
                }
            }
            fileService.saveBitmapToDisk(localDestinationPath, bmp, new FileService.FileServiceCallback<Result>()
            {
                @Override
                public void onComplete(Result result)
                {
                    if(result instanceof Result.Success)
                    {
                        File localFile = ((Result.Success<File>) result).getData();
                        fileService.uploadFileToDatabase(localFile, localDestinationPath, new FileService.FileServiceCallback<Result>()
                        {
                            @Override
                            public void onComplete(Result result)
                            {
                                callback.onComplete(result);
                            }
                        });
                    }
                    else
                    {
                        callback.onComplete(new Result.Error(new Exception("DeviceRepository : createQr() : Problem saving QR bitmap to disk")));
                    }
                }
            });
        }
        catch(WriterException e)
        {
            e.printStackTrace();
        }
    }

    private void saveDeviceImageDrawable(String deviceId, Drawable d, RepositoryCallback<Result> callback)
    {
    }

    private void loadDeviceDrawable(String deviceId, RepositoryCallback<Result> callback)
    {

    }

    private void loadDeviceList(RepositoryCallback<Result> callback)
    {
        firebaseDataSource.getDocumentsFromCollection("devices", new FirebaseDataSource.DataSourceListenerCallback<Result>()
        {
            @Override
            public void onUpdate(Result result)
            {
                if(result instanceof Result.Success)
                {
                    List<DocumentSnapshot> snapshots = ((Result.Success<List<DocumentSnapshot>>) result).getData();
                    Map<String, Device> newMap = new HashMap<>();
                    for(DocumentSnapshot doc : snapshots)
                    {
                        Device toAdd = new Device(doc.getString("name"),
                                doc.getString("deviceId"),
                                doc.getString("make"),
                                doc.getString("model"),
                                doc.getString("serialNumber"),
                                doc.getString("location"),
                                doc.getString("lastMaintenanceDate"),
                                doc.getString("purchaseDate"),
                                doc.getString("qrDownloadUrl"),
                                doc.getString("imageDownloadUrl"));
                        newMap.put(toAdd.getDeviceId(), toAdd);
                    }
                    deviceMap = newMap;
                    deviceListUpdated.postValue(true);
                }
                else
                {
                }
                if(dataLoaded.getValue() == false)
                    callback.onComplete(new Result.Success<String>("DeviceRepository finished loading for the first time"));
            }
        });
    }

    public LiveData<Boolean> isDeviceListUpdated() { return deviceListUpdated; }

    public LiveData<Boolean> isDataLoaded() { return dataLoaded; }

    public LiveData<Boolean> isQrLoaded(String deviceId)
    {
        if(!qrLoadedMap.containsKey(deviceId))
            qrLoadedMap.put(deviceId, new MutableLiveData<>(false));
        return qrLoadedMap.get(deviceId);
    }

    public LiveData<Boolean> isImageLoaded(String deviceId)
    {
        if(!imageLoadedMap.containsKey(deviceId))
            imageLoadedMap.put(deviceId, new MutableLiveData<>(false));
        return imageLoadedMap.get(deviceId);
    }

    public void init()
    {
        loadDeviceList(new RepositoryCallback<Result>()
        {
            @Override
            public void onComplete(Result result)
            {
                dataLoaded.postValue(true);
            }
        });
    }

    public void logout()
    {

    }

}


