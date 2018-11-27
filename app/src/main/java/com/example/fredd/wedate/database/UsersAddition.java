package com.example.fredd.wedate.database;

import com.example.fredd.wedate.encryption.MD5;
import com.example.fredd.wedate.monitor.User;

import java.util.ArrayList;

public class UsersAddition {

    MD5 encryption = new MD5();

    public UsersAddition() throws Exception {

        User president = new User("Termos98", "I_am_President!", "Abdallah", "Termos", "male", "president", 1998, "https://firebasestorage.googleapis.com/v0/b/dateme-fa221.appspot.com/o/termos.jpg?alt=media&token=b3e9ef77-016d-4b3c-81c6-26482a5c015c");
        User vicePresident = new User("Sara1998", "I_am_Vice_President!", "Sara", "Shoujaa", "female", "vice-president", 1998,"https://firebasestorage.googleapis.com/v0/b/dateme-fa221.appspot.com/o/sara.jpg?alt=media&token=85c397a7-9bc5-4f0f-961d-c99dbcccb85d");
        User board1 = new User("Nada98", "N0d0?", "Nada", "Farhat", "female", "board", 1998,"https://firebasestorage.googleapis.com/v0/b/dateme-fa221.appspot.com/o/nada.jpg?alt=media&token=2dc8b26a-2371-4f6b-90b6-ad125e075d85");
        User board2 = new User("Freddy98", "Fre0dy?", "Freddy", "Demiane", "male", "board", 1998, "https://firebasestorage.googleapis.com/v0/b/dateme-fa221.appspot.com/o/freddy.jpg?alt=media&token=a45d4461-5929-4f15-b1b6-1b11b22b846b");
        User board3 = new User("Jabri98", "J0br1?", "Omar", "Jabri", "male", "board", 1998,"https://firebasestorage.googleapis.com/v0/b/dateme-fa221.appspot.com/o/jabri.jpg?alt=media&token=2cf647f2-4f93-4519-a073-95172824fda6");
        User csChair = new User("Azzam90", "A_Z_z_0_M", "Azzam", "Mourad", "male", "cschair", 1990,"https://firebasestorage.googleapis.com/v0/b/dateme-fa221.appspot.com/o/azzam.jpg?alt=media&token=2a6f25a7-be74-4e8a-afea-5e6e8a5c4f73");
        User csFaculty = new User("Faisal89", "Faisalabukhazam89?", "Faisal", "Abu-khzam", "male", "csfaculty", 1989 , "https://firebasestorage.googleapis.com/v0/b/dateme-fa221.appspot.com/o/faisal.jpg?alt=media&token=ad679133-a8fb-4d91-8ca3-cb0d984aac7a");

        ArrayList<User> users = new ArrayList<>();
        users.add(president);
        users.add(vicePresident);
        users.add(board1);
        users.add(board2);
        users.add(board3);
        users.add(csChair);
        users.add(csFaculty);
        DatabaseQuerier.addUsers(users);


    }
}
