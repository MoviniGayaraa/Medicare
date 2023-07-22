package lk.ijse.pharmacy.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lk.ijse.pharmacy.dto.Customer;
import lk.ijse.pharmacy.dto.Supplier;
import lk.ijse.pharmacy.tm.SupplierTm;
import lk.ijse.pharmacy.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SupplierModel {
    public static boolean add(Supplier supplier) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("INSERT INTO supplier VALUES (?,?,?,?,?,?,?)",
                supplier.getSupID(),
                supplier.getFirstName(),
                supplier.getLastName(),
                supplier.getStreet(),
                supplier.getCity(),
                supplier.getLane(),
                supplier.getContact()
        );
    }

    public static boolean update(Supplier supplier) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE supplier SET firstName=?,lastName=?,street=?,city=?,lane=?,contact=? WHERE supID=?";
        return CrudUtil.crudUtil(
                sql,
                supplier.getFirstName(),
                supplier.getLastName(),
                supplier.getStreet(),
                supplier.getCity(),
                supplier.getLane(),
                supplier.getCity(),
                supplier.getSupID()
        );
    }

    public static boolean delete(String id) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM supplier WHERE supID=?";
        return CrudUtil.crudUtil(
                sql,
                id);
    }

    public static Supplier findById(String id) throws SQLException, ClassNotFoundException {

        String sql = "SELECT * FROM supplier WHERE supID=?";

        ResultSet resultSet = CrudUtil.crudUtil(sql,id);
        if(resultSet.next()){
            return (new Supplier(
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

    public static ObservableList<SupplierTm> getAll() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM supplier";
        ObservableList<SupplierTm> obList = FXCollections.observableArrayList();
        ResultSet resultSet = CrudUtil.crudUtil(sql);
        while(resultSet.next()){
            obList.add(new SupplierTm(
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
}
