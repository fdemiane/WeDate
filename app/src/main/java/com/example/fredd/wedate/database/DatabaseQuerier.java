package com.example.fredd.wedate.database;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.fredd.wedate.MainActivity;
import com.example.fredd.wedate.monitor.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Semaphore;

public class DatabaseQuerier {


    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String FIRST_NAME = "first";
    private static final String LAST_NAME = "last";
    private static final String BIRTH_YEAR = "birth";
    private static final String SEX = "sex";
    private static final String TAG = "tag";
    private static User result;

    static FirebaseFirestore db;

    static {
        db = FirebaseFirestore.getInstance();
    }


    public static void addUsers(Collection<User> users) throws Exception {
        for (User user : users)
            addUser(user);
    }

    public static void addUser(User user) throws Exception {


        user.encrypt();
        Map<String, Object> userMp = new HashMap<>();
        userMp.put(USERNAME, user.getUsername());
        userMp.put(PASSWORD, user.getPassword());
        userMp.put(FIRST_NAME, user.getFirstName());
        userMp.put(LAST_NAME, user.getLastName());
        userMp.put(BIRTH_YEAR, user.getBirthYear());
        userMp.put(SEX, user.getSex());
        userMp.put(TAG, user.getTag());


        db.collection("users")
                .add(userMp)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("Db", "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("Db", "Error adding document", e);
                    }
                });
    }

    public static void getUserByUsername(String username, final MainActivity activityRef) throws Exception {
        result = new User();
        final boolean flag = false;

        CollectionReference citiesRef = db.collection("users");
        Query query = citiesRef.whereEqualTo("username", result.encryptString(username));
        Log.d("TAG" , username +" "+result.encryptString(username));

        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {

                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Map<String, Object> mp = document.getData();
                        for (Map.Entry<String, Object> entry : mp.entrySet()) {
                            switch (entry.getKey()) {
                                case USERNAME:
                                    result.setUsername((String) entry.getValue());
                                    break;
                                case PASSWORD:
                                    result.setPassword((String) entry.getValue());
                                    break;
                                case FIRST_NAME:
                                    result.setFirstName((String) entry.getValue());
                                    break;
                                case LAST_NAME:
                                    result.setLastName((String) entry.getValue());
                                    break;
                                case BIRTH_YEAR:
                                    result.setBirthYear((int) ((long)entry.getValue()));
                                    break;
                                case SEX:
                                    result.setSex((String) entry.getValue());
                                    break;
                                case TAG:
                                    result.setTag((String) entry.getValue());
                                    break;


                            }
                        }
                    }


                } else {


                }
                if (result.getUsername() != null) {

                    result.setEncrypted(true);
                    try {
                        result.decrypt();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                    activityRef.authenticate(result);
                } else
                    activityRef.authenticate(result);
            }
        });


    }

    public static User getUserByFirstName(String first) throws Exception {

        result = new User();

        CollectionReference citiesRef = db.collection("users");

        Query query = citiesRef.whereEqualTo("first", result.encryptString(first));


        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {

                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Map<String, Object> mp = document.getData();
                        for (Map.Entry<String, Object> entry : mp.entrySet()) {
                            switch (entry.getKey()) {

                                case USERNAME:
                                    result.setUsername((String) entry.getValue());
                                    Log.d("TAG", result.getUsername());

                                    break;
                                case PASSWORD:
                                    result.setPassword((String) entry.getValue());
                                    break;
                                case FIRST_NAME:
                                    result.setFirstName((String) entry.getValue());
                                    break;
                                case LAST_NAME:
                                    result.setLastName((String) entry.getValue());
                                    break;
                                case BIRTH_YEAR:
                                    //result.setBirthYear((int) entry.getValue());
                                    break;
                                case SEX:
                                    result.setSex((String) entry.getValue());
                                    break;
                                case TAG:
                                    result.setTag((String) entry.getValue());
                                    break;

                            }
                        }
                    }

                    result.setEncrypted(true);
                    try {
                        result.decrypt();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                } else {


                }


            }


        });


        return result;
    }


}
