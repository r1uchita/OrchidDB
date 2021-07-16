
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oracle_util;
import java.sql.ResultSet;
import java.util.Scanner;

/**
 *
 * @author Deepak
 */
public class testing_db {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        // TODO code application logic here
        connection_oracle  obj=new connection_oracle();
        Scanner sc=new Scanner(System.in);
        /*int user_id=sc.nextInt();
        boolean available=obj.verify_records(user_id);
        if(available==true)
            System.out.println("Record is avilable");
        else
            System.out.println("Record is not avialble");
       */
      //  obj.createTable();
        System.out.println("Enter user name and password:");
        String uname=sc.next();
        String pass=sc.next();
        System.out.println(obj.create_user(uname, pass));
      }
    
}
