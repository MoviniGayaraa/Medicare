package lk.ijse.pharmacy.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lk.ijse.pharmacy.dto.Customer;
import lk.ijse.pharmacy.tm.CustomerTm;
import lk.ijse.pharmacy.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerModel {
    public static boolean add(Customer customer) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("INSERT INTO Customer VALUES (?,?,?,?,?,?,?)",
                customer.getCustID(),
                customer.getFirstName(),
                customer.getLastName(),
                customer.getStreet(),
                customer.getCity(),
                customer.getLane(),
                customer.getContact()
        );
    }

    public static boolean update(Customer customer) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE customer SET firstName=?,lastName=?,street=?,city=?,lane=?,contact=? WHERE custID=?";
//        try (PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql)) {
//
//            pstm.setString(1, customer.getName());
//            pstm.setString(2, customer.getEmail());
//            pstm.setString(3, customer.getContact());
//            pstm.setString(4, customer.getId());
//
//            return pstm.executeUpdate() > 0;
//        }
        return CrudUtil.crudUtil(
                sql,
                customer.getFirstName(),
                customer.getLastName(),
                customer.getStreet(),
                customer.getCity(),
                customer.getLane(),
                customer.getContact(),
                customer.getCustID()
        );
    }

    public static boolean delete(String id) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM customer WHERE custID=?";
        return CrudUtil.crudUtil(
                sql,
                id);
    }

    public static Customer findById(String id) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM customer WHERE custID=?";

        ResultSet resultSet = CrudUtil.crudUtil(sql,id);
        if(resultSet.next()){
            return (new Customer(
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

    public static ObservableList<CustomerTm> getAll() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM customer";

        ObservableList<CustomerTm> obList = FXCollections.observableArrayList();

        ResultSet resultSet = CrudUtil.crudUtil(sql);
        while (resultSet.next()) {
            obList.add(new CustomerTm(
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

    public static List<String> loadIds() throws SQLException, ClassNotFoundException {
        String sql = "SELECT custID FROM customer";
        ResultSet resultSet = CrudUtil.crudUtil(sql);

        List<String> data = new ArrayList<>();

        while (resultSet.next()) {
            data.add(resultSet.getString(1));
        }
        return data;
    }

    public static String getCustName(String cust_id) throws SQLException, ClassNotFoundException {
        String sql = "SELECT firstName,lastName FROM customer WHERE custID=?";
        ResultSet resultSet = CrudUtil.crudUtil(sql, cust_id);

        if (resultSet.next()) {
            return (new String(
                    resultSet.getString(1) + " " + resultSet.getString(2)
            ));
        }
        return null;
    }

    public static int getTotCustomers() throws SQLException, ClassNotFoundException {
        String sql="SELECT COUNT(custID) FROM Customer";
        ResultSet resultSet= CrudUtil.crudUtil(sql);
        int count=0;
        while (resultSet.next()){
            count=resultSet.getInt(1);
        }
        return count;

    }
}
