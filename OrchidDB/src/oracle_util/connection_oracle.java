package oracle_util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import java.sql.*;
import oracle.jdbc.*;
import oracle.jdbc.pool.OracleDataSource;
//import oracle.jdbc.pool.OracleDataSource;

public class connection_oracle {

    public static String jdbcUrl = " jdbc:oracle:thin:@localhost:1521:oracledb";
    public static String userid = "admin";
    public static String password = "admin";
    public static Connection conn;
    public static Statement stmt;
    public static ResultSet rset;
    public static ResultSetMetaData rsmd;
    public static String query;
    public static String sqlString;

    public String create_user(String uname, String Password) throws SQLException {
        connection_oracle con = new connection_oracle();
        con.get_DB_connection();
        stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        query = "CREATE USER \"" + uname + "\" IDENTIFIED BY \"" + Password + "\"";
        System.out.println("\nExecuting query: " + query);
        try {
            stmt.execute(query);
            return "Sucess";
        } catch (Exception e) {
            return e.toString();
        }
    }

    public ResultSet get_column_name(String table_name) throws SQLException {
        connection_oracle con = new connection_oracle();
        con.get_DB_connection();
        stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        query = "SELECT * From ";
        table_name = table_name.toLowerCase();
        query = query + table_name;
     //   System.out.println("\nExecuting query: " + query);
        rset = stmt.executeQuery(query);
//          rsmd = rset.getMetaData();
       /*   int columnCount = rsmd.getColumnCount();
         String column_name[]=new String[columnCount];
         for (int i = 1; i <= columnCount; i++ ) {
         column_name[i] = rsmd.getColumnName(i);
         }*/
        return rset;
    }

    public ResultSet get_table_name() throws SQLException {
        connection_oracle con = new connection_oracle();
        con.get_DB_connection();
        stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        query = "select TABLE_NAME from user_tables";
        // System.out.println("\nExecuting query: " + query);
        rset = stmt.executeQuery(query);
        return rset;
    }

    public String  rename_table_name(String old_table_name, String new_table_name) throws SQLException {
        connection_oracle con = new connection_oracle();
        con.get_DB_connection();
        stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        query = "rename  ";
        query = query + old_table_name + " to " + new_table_name;

        System.out.println("\nExecuting query: " + query);
        try{
        rset = stmt.executeQuery(query);
        return "sucess";
        }
        catch(Exception e){
        return e.toString();
        }
    }

    public String drop_table_name(String table_name) throws SQLException {
        connection_oracle con = new connection_oracle();
        con.get_DB_connection();
        stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        query = "drop table  ";
        query = query + table_name;

        System.out.println("\nExecuting query: " + query);
        try {
            rset = stmt.executeQuery(query);
            return "Sucess";
        } catch (Exception e) {
            return e.toString();
        }

    }

    public ResultSet  desc_table_name(String table_name) throws SQLException {
        connection_oracle con = new connection_oracle();
        con.get_DB_connection();
        stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        query = "describe  ";
        query = query + table_name;

        System.out.println("\nExecuting query: " + query);
          rset = stmt.executeQuery(query);
          return rset;
          
    }
    public String get_DB_connection() throws SQLException {
        OracleDataSource ds= new OracleDataSource();
        ds.setDriverType("thin");
        ds.setServerName("localhost");
        ds.setDatabaseName("oracledb");
        ds.setPortNumber(1521);
        ds.setUser(userid);
        ds.setPassword(password);
        try {
            conn = ds.getConnection();
            return "sucess";
        } catch (Exception e) {
            return e.toString();
        }
    }

    public ResultSet getAllrecords() throws SQLException {
        connection_oracle obj = new connection_oracle();
        obj.get_DB_connection();
        stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        query = "SELECT * FROM employee ORDER BY emp_id";
        System.out.println("\nExecuting query: " + query);
        rset = stmt.executeQuery(query);
        return rset;
    }

    public boolean verify_records(int id) throws SQLException {
        boolean available = false;
        connection_oracle obj = new connection_oracle();
        obj.get_DB_connection();
        stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        query = "SELECT * FROM employee WHERE emp_id =" + id;
        rset = stmt.executeQuery(query);
        if (rset.absolute(1) == false) {
            return available;
        } else {
            while (rset.next()) {
                System.out.println(rset.getInt(1) + rset.getString(2));
            }
            available = true;
            return available;
        }

    }

    public String add_record(String query) throws SQLException {
        connection_oracle obj = new connection_oracle();
        obj.get_DB_connection();
        stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
   
         //   System.out.println("\nInserting: " + query);
            try{
                 int set =stmt.executeUpdate(query);
                  return Integer.toString(set);
            }
            catch(Exception e){
                return e.toString();
                 }
        }
    
    public String  create_table(String query) throws SQLException {
        
        try {
            connection_oracle obj = new connection_oracle();
            obj.get_DB_connection();
            stmt = conn.createStatement();
            stmt.executeUpdate(query);
            System.out.println("Table is Created");
            return "sucess";
        } catch (SQLException e) {
            System.out.println(e);
            return e.toString();
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }

  public   String alter_table(String query) throws SQLException {
        connection_oracle con = new connection_oracle();
        con.get_DB_connection();
        stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);

        System.out.println("\nExecuting query: " + query);
        try {
            rset = stmt.executeQuery(query);
            return "Sucess";
        } catch (Exception e) {
            return e.toString();
        }

    }
}
