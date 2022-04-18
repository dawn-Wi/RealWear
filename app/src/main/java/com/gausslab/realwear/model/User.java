package com.gausslab.realwear.model;

//import static com.baec23.arlogbook.App.equalHelper;

import android.graphics.Bitmap;

import androidx.annotation.NonNull;

public class User
{
    private String userId;
    private String firebaseUid;
    private String loginId;
    private String displayName;
    private Bitmap profilePicture;
    private Position position;
//    private ContactNumber contactNumber = new ContactNumber();
//    private Address address = new Address();

    public User()
    {

    }

    public User(String userId, String firebaseUid, String loginId, String displayName, Position pos)
    {
        this.userId = userId;
        this.loginId = loginId;
        this.displayName = displayName;
        this.firebaseUid = firebaseUid;
        this.position = pos;
    }

    public String getUserId()
    {
        return userId;
    }

    public void setUserId(String userId)
    {
        this.userId = userId;
    }

    public String getFirebaseUid()
    {
        return firebaseUid;
    }

    public void setFirebaseUid(String firebaseUid)
    {
        this.firebaseUid = firebaseUid;
    }

    public String getLoginId()
    {
        return loginId;
    }

    public void setLoginId(String loginId)
    {
        this.loginId = loginId;
    }

    public String getDisplayName()
    {
        return displayName;
    }

    public void setDisplayName(String displayName)
    {
        this.displayName = displayName;
    }

//    public Bitmap getProfilePicture()
//    {
//        return profilePicture;
//    }
//
//    public void setProfilePicture(Bitmap profilePicture)
//    {
//        this.profilePicture = profilePicture;
//    }
//
//    public ContactNumber getContactNumber()
//    {
//        return contactNumber;
//    }
//
//    public void setContactNumber(ContactNumber contactNumber)
//    {
//        this.contactNumber = contactNumber;
//    }
//
//    public Address getAddress()
//    {
//        return address;
//    }
//
//    public void setAddress(Address address)
//    {
//        this.address = address;
//    }

    public Position getPosition()
    {
        return position;
    }

    public void setPosition(Position position)
    {
        this.position = position;
    }

//    public boolean equals(User u)
//    {
//        if(!equalHelper(userId, u.getUserId()))
//            return false;
//        if(!equalHelper(firebaseUid, u.getFirebaseUid()))
//            return false;
//        if(!equalHelper(loginId, u.getLoginId()))
//            return false;
//        if(!equalHelper(displayName, u.getDisplayName()))
//            return false;
//        if(!equalHelper(profilePicture, u.getProfilePicture()))
//            return false;
//        if(!equalHelper(position, u.getPosition()))
//            return false;
//        if(!equalHelper(contactNumber, u.getContactNumber()))
//            return false;
//        return equalHelper(address, u.getAddress());
//    }

    @NonNull
    @Override
    public String toString()
    {
        return displayName;
    }

    public enum Position
    {
        WORKER,
        MANAGER
    }
}
