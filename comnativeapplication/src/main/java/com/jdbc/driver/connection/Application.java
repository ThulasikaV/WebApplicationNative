package com.jdbc.driver.connection;

import java.sql.*;

/**
 * Created by printha on 8/5/18.
 */
public class Application {
    public Connection getConnection(){
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/rcvms");
            return  con;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static void main(String[] args) {
        int id = 5;
        String name= "BBQ 2";
        String query = "select * from facility WHERE id = ? AND name = ?";

        String query2 = "select facility.name, facility_type.type_name from facility " +
                "INNER JOIN facility_type on facility.facility_type_id = facility_type.id " +
                "where facility_type.type_name = 'BBQ' ";

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet s = null;
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/rcvms","root", "root");
            ps = con.prepareStatement(query);
            ps.setInt(1,id);
            ps.setString(2,name);
            s = ps.executeQuery();
            while (s.next()){
                System.out.println(s.getInt("id"));
                System.out.println(s.getString("created_by"));
                System.out.println(s.getString("name"));
            }

            PreparedStatement ps2 = con.prepareStatement(query2);
            ResultSet s2 = ps2.executeQuery();
            while (s2.next()){
                System.out.print(s2.getString("name") + "     ");
                System.out.println(s2.getString("type_name"));

            }

            String insertQuery= "INSERT INTO facility(created_by, created_date, modified_by, modified_date, capacity, deposit, fee_per_slot, is_default_time_slots_used, name, operational_status, facility_type_id)" +
                    " values(null,null,null,null,2,70,10,TRUE,'BBQ3','Available',8)";
            PreparedStatement ps3 = con.prepareStatement(insertQuery);
            boolean es=ps3.execute();
            System.out.println("Success: "+ es);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {

                if (s != null){
                    s.close();
                }

                if (ps != null) {
                    ps.close();
                }

                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


    }
}
