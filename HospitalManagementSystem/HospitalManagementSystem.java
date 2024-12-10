package HospitalManagementSystem;

import com.sun.security.jgss.GSSUtil;
import com.sun.source.tree.TryTree;

import java.sql.*;
import java.util.Scanner;


public class HospitalManagementSystem {
    private static final String url="jdbc:oracle:thin:@localhost:1521:orcl";
    private static final String usn="HOSPITAL";
    private static final String pwd="SUBRAT";

    public static void main(String[] args) {
        try{
            Class.forName("oracle.jdbc.driver.OracleDriver");
        }catch (Exception e){
            e.printStackTrace();
        }

    Scanner sc=new Scanner(System.in);
      try{
        Connection con=DriverManager.getConnection(url,usn,pwd);
        Patient patient=new Patient(con,sc);
        Doctor doctor=new Doctor(con);
        while (true){
            System.out.println("HOSPITAL MANAGEMENT SYSTEM");
            System.out.println("1. Add Patient");
            System.out.println("2. View Patients");
            System.out.println("3. View Doctors");
            System.out.println("4. Book Appointment");
            System.out.println("5. Exit");
            System.out.println("Enter Your Choice: ");

            int choice=sc.nextInt();

            switch(choice){
                case 1:
                    //Add Patient
                    patient.addPatient();
                    System.out.println();
                    break;
                case 2:
                    //view patients
                    patient.viewPatient();
                    System.out.println();
                    break;
                case 3:
                    //view doctors
                    doctor.viewDoctors();
                    System.out.println();
                    break;

                case 4:
                    //Book appointment
                    bookAppointment(patient,doctor,con,sc);
                    break;

                case 5:
                    return;
                default:
                    System.out.println("Enter Valid Choice!!!");
                    break;

            }
        }
    }catch(Exception e){
        e.printStackTrace();
    }
}
public static void bookAppointment(Patient patient,Doctor doctor,Connection con,Scanner sc){
    System.out.println("Enter patient Id: ");
        int patientId=sc.nextInt();
    System.out.println("Enter Doctor Id: ");
    int doctorId=sc.nextInt();
    System.out.println("Enter Appointment Date(YYYY-MM-DD): ");
    String appointmentDate=sc.next();
    if (patient.getPatientById(patientId) && doctor.getDoctorById(doctorId)){
        if(checkDoctorAvailable(doctorId,appointmentDate, con)){
            String appointmentQuery="INSERT INTO APPOINTMENTS(PATIENT_ID,DOCTOR_ID,APPOINTMENT_DATE)VALUES(?,?,?)";
            try{
                PreparedStatement pstmt=con.prepareStatement(appointmentQuery);
                pstmt.setInt(1,patientId);
                pstmt.setInt(2,doctorId);
                pstmt.setString(3,appointmentDate);
                int rowsAff=pstmt.executeUpdate();
                if(rowsAff>0){
                    System.out.println("Appointment Booked");
                }else {
                    System.out.println("Failed to Book Appointment");
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        } else {
            System.out.println("Doctor is unavailable on the selected date. Please choose another date.");
        }
    } else {
        System.out.println("Either patient or doctor doesn't exist!");
    }
}
    public static boolean checkDoctorAvailable(int doctorId,String appointmentDate,Connection con){
        String query="SELECT COUNT(*) FROM APPOINTMENTS WHERE DOCTOR_ID=? AND APPOINTMENT_DATE = ?";
        try{
            PreparedStatement pstmt=con.prepareStatement(query);
            pstmt.setInt(1,doctorId);
            pstmt.setString(2,appointmentDate);
            ResultSet rs=pstmt.executeQuery();
            if (rs.next()){
                int count=rs.getInt(1);
                if (count==0){
                    return true;
                }else {
                    return false;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}

