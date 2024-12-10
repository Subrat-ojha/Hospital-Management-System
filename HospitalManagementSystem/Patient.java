package HospitalManagementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class Patient {
    private Connection con;
    private Scanner sc;

    public Patient(Connection con, Scanner sc){
        this.con=con;
        this.sc=sc;
    }

    public void addPatient(){
        System.out.println("Enter Patient Name ");
        String name=sc.next();
        System.out.println("Enter Patient Age ");
        int age=sc.nextInt();
        System.out.println("Enter Patient Gender ");
        String gender=sc.next();

        try{
            String query="INSERT INTO PATIENTS(NAME,AGE,GENDER) VALUES(?,?,?)";
            PreparedStatement pstmt=con.prepareStatement(query);
           // pstmt.executeQuery();
            pstmt.setString(1,name);
            pstmt.setInt(2,age);
            pstmt.setString(3,gender);
            int affRow=pstmt.executeUpdate();

            if(affRow>0){
                System.out.println("Patient added Succesfully");
            }else{
                System.out.println("Failed to add Patient");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void viewPatient(){
        String query="SELECT * FROM PATIENTS";
        try {
           PreparedStatement pstmtt=con.prepareStatement(query);
            ResultSet rs = pstmtt.executeQuery();
            System.out.printf("%-15s %-20s %-10s %-10s%n", "ID","NAME", "AGE","GENDER");
            System.out.println("-------------");
            while (rs.next()) {
                int id=rs.getInt("ID");
                String name = rs.getString("NAME");
                int age = rs.getInt("AGE");
                String gender = rs.getString("GENDER");

                System.out.printf("%-10s %-20s %-10s %-10S%n", id,name, age, gender);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public boolean getPatientById(int id){
        String query="SELECT * FROM PATIENTS WHERE ID=?";
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
