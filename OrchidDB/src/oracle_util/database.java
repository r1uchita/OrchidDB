/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oracle_util;

import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Deepak
 */
public class database {

    /**
     * @param args the command line arguments
     */
    /*String dbName="oracledb";
      public void createTable() throws SQLException {
    String createString =
        "create table " + dbName +
        ".COFFEES " +
        "(COF_NAME varchar(32) NOT NULL, " +
        "SUP_ID int NOT NULL, " +
        "PRICE float NOT NULL, " +
        "SALES integer NOT NULL, " +
        "TOTAL integer NOT NULL, " +
        "PRIMARY KEY (COF_NAME), " +
        "FOREIGN KEY (SUP_ID) REFERENCES " +
        dbName + ".SUPPLIERS (SUP_ID))";

         Statement stmt = null;
        try {
            connection_oracle  obj=new connection_oracle();
            obj.get_DB_connection();
            stmt = con.createStatement();
            stmt.executeUpdate(createString);
        } 
        catch (SQLException e) {
            System.out.println(e);
        }
        finally {
            if (stmt != null) { stmt.close(); }
        }
}
    */
    public static void main(String[] args) {
        // TODO code application logic here
        
    }
    
}
