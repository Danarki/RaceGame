package RaceGame;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SQL {

    public static Connection connect() {
        try {
            Class.forName("com.mysql.jdbc.Driver");

            return DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/racegame", "root", "");
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    public static ResultSet select(){
        Connection con = connect();
        try {
            assert con != null;
            Statement stmt = con.createStatement();

            return stmt.executeQuery("select * from score ORDER BY score DESC");
        } catch (Exception e){
            System.out.println(e);
        }
        return null;
    }

    public static void insert(String name, Integer score){
        Connection con = connect();
        try {
            assert con != null;
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String dateFormatted = sdf.format(date);


            String query = "insert into score (username, score, date) values ('" + name + "', " + score + ", '" + dateFormatted + "')";
            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.execute();

            con.close();
        } catch (Exception e){
            System.out.println(e);
        }
    }

    public static void main(String[] args) {
        insert("Danarki", 1500);
        select();
    }
}