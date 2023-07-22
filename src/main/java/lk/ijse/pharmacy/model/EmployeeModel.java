package lk.ijse.pharmacy.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lk.ijse.pharmacy.dto.Customer;
import lk.ijse.pharmacy.dto.Employee;
import lk.ijse.pharmacy.tm.CustomerTm;
import lk.ijse.pharmacy.tm.EmployeeTm;
import lk.ijse.pharmacy.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeModel {

    public static boolean save(Employee employee) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO Employee(empID, firstName, lastName, street, city, lane, contact)" +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        return CrudUtil.crudUtil(
                sql,
                employee.getEmpId(),
                employee.getFirstName(),
                employee.getLastName(),
                employee.getStreet(),
                employee.getCity(),
                employee.getLane(),
                employee.getContact()
        );
    }

    public static ObservableList<EmployeeTm> getAll() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM Employee";

        ObservableList<EmployeeTm> obList = FXCollections.observableArrayList();

        ResultSet resultSet = CrudUtil.crudUtil(sql);
        while (resultSet.next()) {
            obList.add(new EmployeeTm(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6),
                    resultSet.getString(7)
            ));
        }
        return obList;
    }

    public static boolean update(Employee employee) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE Employee SET firstName =?, lastName =?, street =?, city =?, " +
                "lane =?, contact =? WHERE empID =?";
        return CrudUtil.crudUtil(
                sql,
                employee.getFirstName(),
                employee.getLastName(),
                employee.getStreet(),
                employee.getCity(),
                employee.getLane(),
                employee.getContact(),
                employee.getEmpId()
        );
    }

    public static boolean delete(String empId) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM Employee WHERE empID = ?";
        return CrudUtil.crudUtil(sql, empId);
    }

    public static Employee findById(String id) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM Employee WHERE empID=?";

        ResultSet resultSet = CrudUtil.crudUtil(sql,id);
        if(resultSet.next()){
            return (new Employee(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6),
                    resultSet.getString(7)
            ));
        }
        return null;
    }


    public static List<String> loadIds() throws SQLException, ClassNotFoundException {
        String sql = "SELECT empID FROM employee";
        ResultSet resultSet = CrudUtil.crudUtil(sql);

        List<String> data = new ArrayList<>();

        while (resultSet.next()) {
            data.add(resultSet.getString(1));
        }
        return data;
    }

    public static int getTotEmployee() throws SQLException, ClassNotFoundException {
        String sql="SELECT COUNT(empID) FROM Employee";
        ResultSet resultSet= CrudUtil.crudUtil(sql);
        int count=0;
        while (resultSet.next()){
            count=resultSet.getInt(1);
        }
        return count;

    }
}
