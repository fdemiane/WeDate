package com.example.fredd.wedate.database;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.fredd.wedate.activities.ChangePasswordActivity;
import com.example.fredd.wedate.activities.MainActivity;
import com.example.fredd.wedate.activities.MainScreenActivity;
import com.example.fredd.wedate.encryption.MD5;
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
import java.util.Set;
import java.util.TreeSet;

public class DatabaseQuerier {


    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String FIRST_NAME = "first";
    private static final String LAST_NAME = "last";
    private static final String BIRTH_YEAR = "birth";
    private static final String SEX = "sex";
    private static final String TAG = "tag";
    private static User result;
    private static String flowString = "";

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

    public static void getUserByUsernameMainActivity(String username, final MainActivity activityRef) throws Exception {
        result = new User();
        final boolean flag = false;

        CollectionReference citiesRef = db.collection("users");
        Query query = citiesRef.whereEqualTo("username", result.encryptString(username));
        Log.d("TAG", username + " " + result.encryptString(username));

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
                                    result.setBirthYear((int) ((long) entry.getValue()));
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

    public static void checkUserExistsChangePasswordActivity(String userName, final ChangePasswordActivity activityRef) throws Exception {


        userName = User.encryptString(userName);
        CollectionReference citiesRef = db.collection("users");
        Query query = citiesRef.whereEqualTo("username", userName);


        final String finalUserName = userName;
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {

                    try {
                        if (task.getResult().getDocuments().size() == 0) {
                            activityRef.setWrongTextField("User does not exist");

                            return;
                        }
                        flowString = task.getResult().getDocuments().get(0).getId();
                        Log.d("ID", flowString);
                        activityRef.validateStep2();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                } else {
                    activityRef.setWrongTextField("User does not exist");


                }

            }
        });


    }

    public static void checkPasswordCorrectChangePasswordActivity(String password, final ChangePasswordActivity activityRef) throws Exception {


        MD5 md5 = new MD5();
        password = md5.encrypt(password);
        CollectionReference citiesRef = db.collection("users");
        Query query = citiesRef.whereEqualTo("password", password);


        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    try {
                        if (task.getResult().getDocuments().size() == 0) {
                            activityRef.setWrongTextField("Old password is incorrect");
                            return;
                        }
                        activityRef.validateStep3();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                } else {
                    activityRef.setWrongTextField("Old password is incorrect");


                }

            }
        });


    }

    public static void getAllUsersWithTag(final Set<String> tags, final MainScreenActivity activityRef , final int index) throws Exception {

        final Set<User> users = new TreeSet<>();
        db.collection("users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String tag = (String) document.getData().get("tag");
                                try {
                                    tag = User.decryptString(tag);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                                if (tags.contains(tag)) {
                                    User user = new User();
                                    Map<String, Object> mp = document.getData();
                                    for (Map.Entry<String, Object> entry : mp.entrySet()) {
                                        switch (entry.getKey()) {
                                            case USERNAME:
                                                user.setUsername((String) entry.getValue());
                                                break;
                                            case PASSWORD:
                                                user.setPassword((String) entry.getValue());
                                                break;
                                            case FIRST_NAME:
                                                user.setFirstName((String) entry.getValue());
                                                break;
                                            case LAST_NAME:
                                                user.setLastName((String) entry.getValue());
                                                break;
                                            case BIRTH_YEAR:
                                                user.setBirthYear((int) ((long) entry.getValue()));
                                                break;
                                            case SEX:
                                                user.setSex((String) entry.getValue());
                                                break;
                                            case TAG:
                                                user.setTag((String) entry.getValue());
                                                break;


                                        }
                                    }

                                    user.setEncrypted(true);
                                    try {
                                        user.decrypt();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    users.add(user);

                                }


                            }
                            activityRef.updateUsersIndexed(users , index);

                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

    }

    public static void getAllUsers(final MainScreenActivity activityRef) throws Exception {

        final Set<User> users = new TreeSet<>();
        db.collection("users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                User user = new User();
                                Map<String, Object> mp = document.getData();
                                for (Map.Entry<String, Object> entry : mp.entrySet()) {
                                    switch (entry.getKey()) {
                                        case USERNAME:
                                            user.setUsername((String) entry.getValue());
                                            break;
                                        case PASSWORD:
                                            user.setPassword((String) entry.getValue());
                                            break;
                                        case FIRST_NAME:
                                            user.setFirstName((String) entry.getValue());
                                            break;
                                        case LAST_NAME:
                                            user.setLastName((String) entry.getValue());
                                            break;
                                        case BIRTH_YEAR:
                                            user.setBirthYear((int) ((long) entry.getValue()));
                                            break;
                                        case SEX:
                                            user.setSex((String) entry.getValue());
                                            break;
                                        case TAG:
                                            user.setTag((String) entry.getValue());
                                            break;


                                    }

                                    user.setEncrypted(true);
                                    try {
                                        user.decrypt();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    Log.d("TAG" , "test");
                                    Log.d("TAG",user.getUsername());
                                    users.add(user);

                                }


                            }
                            activityRef.updateUsers(users);

                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

    }

    public static void updatePasswordChangePasswordActivity(String newPassword, final ChangePasswordActivity activityRef) throws Exception {


        MD5 md5 = new MD5();
        newPassword = md5.encrypt(newPassword);
        db.collection("users").document(flowString).update("password", newPassword).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                activityRef.onSuccess();

            }
        });


    }


}
