package com.gausslab.realwear.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.gausslab.realwear.FirebaseDataSource;
import com.gausslab.realwear.model.Result;
import com.gausslab.realwear.model.User;
//import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserRepository extends Repository
{
    //region Singleton Pattern
    private static final UserRepository INSTANCE = new UserRepository();
    private final MutableLiveData<Boolean> userListUpdated = new MutableLiveData<>(false);
    //endregion

    private User currUser;
    private List<User> userList;

    public static UserRepository getInstance()
    {
        return INSTANCE;
    }

//    public void tryLogin(final String username, final String password, final RepositoryListenerCallback<Result> callback)
//    {
//        firebaseDataSource.auth_login(username, password, result ->
//        {
//            if(result instanceof Result.Success)
//            {
//                FirebaseUser firebaseUser = ((Result.Success<FirebaseUser>) result).getData();
//                getUser(firebaseUser.getUid(), callback);
//            }
//            else
//                callback.onUpdate(result);
//        });
//    }

//    public void tryRegister(final String username, final String password, final String userDisplayName, final RepositoryListenerCallback<Result> callback)
//    {
//        firebaseDataSource.getNewKey(FirebaseDataSource.KeyType.USER, result ->
//        {
//            if(result instanceof Result.Success)
//            {
//                String newKey = ((Result.Success<String>) result).getData();
//                firebaseDataSource.auth_register(username, password, result2 ->
//                {
//                    if(result2 instanceof Result.Success)
//                    {
//                        String firebaseUid = ((Result.Success<String>) result2).getData();
//                        User toCreate = new User(newKey, firebaseUid, username, userDisplayName, User.Position.WORKER);
//                        createUser(toCreate);
//                        callback.onUpdate(new Result.Success<String>("Success"));
//                    }
//                    else
//                        callback.onUpdate(result2);
//                });
//            }
//            else
//            {
//                callback.onUpdate(result);
//            }
//        });
//    }

    public void savedLogin(String firebaseUid, RepositoryListenerCallback<Result> callback)
    {
        firebaseDataSource.getDocumentsFromCollection_whereEqualTo_once("users", "firebaseUid", firebaseUid, new FirebaseDataSource.DataSourceCallback<Result>()
        {
            @Override
            public void onComplete(Result result)
            {
                if(result instanceof Result.Success)
                {
                    List<DocumentSnapshot> docList = ((Result.Success<List<DocumentSnapshot>>) result).getData();
                    if(docList.size() != 1)
                    {
                        callback.onUpdate(new Result.Error(new Exception("User not found")));
                    }
                    else
                    {
                        getUser(firebaseUid, callback);
                    }
                }
                else
                {
                    callback.onUpdate(result);
                }
            }
        });
    }

    public void logout()
    {
        currUser = null;
    }

    private void createUser(User toCreate)
    {
        Map<String, Object> toAdd = new HashMap<>();
        toAdd.put("userId", toCreate.getUserId());
        toAdd.put("displayName", toCreate.getDisplayName());
        toAdd.put("loginId", toCreate.getLoginId());
        toAdd.put("firebaseUid", toCreate.getFirebaseUid());
        toAdd.put("position", toCreate.getPosition().toString());
        //toAdd.put("phoneNumber", toCreate.getContactNumber().getMobileNumber());

//        firebaseDataSource.submitDataToCollection_autoDocumentName("users", toAdd, new FirebaseDataSource.DataSourceCallback<Result>()
//        {
//            @Override
//            public void onComplete(Result result)
//            {
//                if(result instanceof Result.Success)
//                {
//                    Log.d("DEBUG", "UserRepository : createUser() : Success");
//                }
//                else
//                {
//                    Log.d("DEBUG", "UserRepository : createUser() : Failed");
//                }
//            }
//        });
    }

    public void loadUserList()
    {
        firebaseDataSource.getDocumentsFromCollection("users", new FirebaseDataSource.DataSourceListenerCallback<Result>()
        {
            @Override
            public void onUpdate(Result result)
            {
                if(result instanceof Result.Success)
                {
                    List<DocumentSnapshot> resultList = ((Result.Success<List<DocumentSnapshot>>) result).getData();
                    List<User> toReturn = new ArrayList<>();
                    for(DocumentSnapshot doc : resultList)
                    {
                        User toAdd = doc.toObject(User.class);
                        toReturn.add(toAdd);
                    }
                    userList = toReturn;
                    userListUpdated.postValue(true);
                }
            }
        });
    }

    public List<User> getUserList()
    {
        if(userList == null)
        {
            userList = new ArrayList<>();
            loadUserList();
        }
        return userList;
    }


    private void getUser(String firebaseUid, RepositoryListenerCallback<Result> callback)
    {

        firebaseDataSource.getDocumentsFromCollection_whereEqualTo_once("users", "firebaseUid", firebaseUid, result2 ->
        {
            if(result2 instanceof Result.Success)
            {
                List<DocumentSnapshot> documentsList = ((Result.Success<List<DocumentSnapshot>>) result2).getData();
                if(documentsList.size() == 0)
                {
                    Log.d("DEBUG", "UserRepository : tryLogin() : user not found using firebaseUid " + firebaseUid);
                    callback.onUpdate(new Result.Error(new Exception("User Not Found")));
                }
                else if(documentsList.size() > 1)
                {
                    Log.d("DEBUG", "UserRepository : tryLogin() : found more than one document for user with firebaseUid " + firebaseUid);
                    callback.onUpdate(new Result.Error(new Exception("Multiple Users with Same firebaseUid Found")));
                }
                else
                {
                    DocumentSnapshot documentSnapshot = documentsList.get(0);
                    User fetchedUser = new User();
                    fetchedUser.setUserId(documentSnapshot.getString("userId"));
                    fetchedUser.setFirebaseUid(documentSnapshot.getString("firebaseUid"));
                    fetchedUser.setDisplayName(documentSnapshot.getString("displayName"));
                    fetchedUser.setLoginId(documentSnapshot.getString("loginId"));
                    fetchedUser.setPosition(User.Position.valueOf(documentSnapshot.getString("position")));
                    //fetchedUser.setContactNumber(new ContactNumber(documentSnapshot.getString("phoneNumber")));
                    currUser = fetchedUser;
                    callback.onUpdate(new Result.Success<User>(currUser));
                }
            }
            else
            {
                callback.onUpdate(result2);
            }
        });
    }

    public User getCurrUser()
    {
        return currUser;
    }

    public User getUserFromId(String userId)
    {
        if(userList != null)
        {
            for(User u : userList)
            {
                if(u.getUserId().equals(userId))
                {
                    return u;
                }
            }
        }
        return null;
    }

    public LiveData<Boolean> isUserListUpdated()
    {
        return userListUpdated;
    }

}


