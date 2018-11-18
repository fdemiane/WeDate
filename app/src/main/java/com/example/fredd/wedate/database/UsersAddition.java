package com.example.fredd.wedate.database;

import com.example.fredd.wedate.encryption.MD5;
import com.example.fredd.wedate.monitor.User;

public class UsersAddition {

    MD5 encryption = new MD5();
    public UsersAddition()
    {

        User president = new User("khadijeh58" , "Ic0ok_bemieh", "Khadijeh" , "Jamal" , "female" ,"president" , 1958);
        User vicePresident = new User("Latifeh59" , "Ic0ok_fasolia", "Latifeh" , "Khoury" , "female" ,"vice president" , 1959);
        User board1 = new User("Nada98" , "N0d0?", "Nada" , "Farhat" , "female" ,"board" , 1998);
        User board2 = new User("Freddy98" , "Fre0dy?", "Freddy" , "Demiane" , "male" ,"board" , 1998);
        User board3 = new User("Jabri98" , "J0br1?", "Omar" , "Jabri" , "male" ,"board" , 1998);
        User csChair = new User("Azzam90" , "A_Z_z_0_M", "Azzam" , "Mourad" , "male" , "cschair" , 1990);
        User csFaculty = new User("Faisal89" , "Faisalabukhazam89?" , "Faisal" , "Abu-khzam" , "male" , "csfaculty" , 1989);



    }
}
