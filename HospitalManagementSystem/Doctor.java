package HospitalManagementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Doctor {
    private Connection con;
    public Doctor(Connection con){
        this.con=con;

    }


    public void viewDoctors(){
        String query="SELECT * FROM DOCTORS";
        try {
            PreparedStatement pstmtt=con.prepareStatement(query);
            ResultSet rs = pstmtt.executeQuery();
            System.out.printf("%-5s %-20s %-10s%n", "ID", "NAME","SPECIALIZATION");
            System.out.println("------------------------------------");
            while (rs.next()) {
                int id = rs.getInt("ID");
                String name = rs.getString("NAME");
                String spec = rs.getString("SPECIALIZATION");

                System.out.printf("%-5s %-20s %-10s%n", id, name, spec);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public boolean getDoctorById(int id){
        String query="SELECT * FROM DOCTORS WHERE ID=?";
        try {
            PreparedStatement pstmt2 = con.prepareStatement(query);
            pstmt2.setInt(1, id);
            ResultSet rs = pstmt2.executeQuery();
            if (rs.next()) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
