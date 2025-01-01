package DAO;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import Model.Employee;

public class DataImportExportDAOImpl implements DataImportExportDAO<Employee> {
    private Connection connection;
    public DataImportExportDAOImpl(){
        connection = DBConnection.getConnection();
    }
    @Override
    public boolean importData(String fileName) throws IOException {
        String query = "INSERT INTO employee (nom, prenom, salaire, email, phone, role, poste,holidayBalance) VALUES (?, ?, ?, ?, ?, ?, ?,25)";
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName));
            PreparedStatement pstmt = connection.prepareStatement(query);
        ){
            String line = reader.readLine();
            while((line = reader.readLine()) != null){
                String[] data = line.split(",");
                pstmt.setString(1, data[0]);
                pstmt.setString(2, data[1]);
                pstmt.setDouble(3, Double.parseDouble(data[2]));
                pstmt.setString(4, data[3]);
                pstmt.setString(5, data[4]);
                pstmt.setString(6, data[5]);
                pstmt.setString(7, data[6]);
                pstmt.addBatch();
            }
            pstmt.executeBatch();
        }catch(IOException | SQLException e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public void exportData(String fileName, List<Employee> data) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))){
            writer.write("nom,prenom,salaire,email,phone,role,poste,holidayBalance\n");
            for(Employee employee : data){
                writer.write(employee.getNom() + "," + employee.getPrenom() + "," + employee.getSalaire() + "," + employee.getEmail() + "," + employee.getPhone() + "," + employee.getRole() + "," + employee.getPoste() + "," + employee.getHolidayBalance() + "\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}