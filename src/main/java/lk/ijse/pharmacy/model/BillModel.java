package lk.ijse.pharmacy.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lk.ijse.pharmacy.dto.Bill;
import lk.ijse.pharmacy.dto.Customer;
import lk.ijse.pharmacy.tm.BillTm;
import lk.ijse.pharmacy.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BillModel {

    public static boolean addBill(Bill bill) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO bill(billID,orderID,totalAmt,custPay,discount,orderDate)" +
                "VALUES (?,?,?,?,?,?)";

        return CrudUtil.crudUtil(
                sql,
                bill.getBillID(),
                bill.getOrderID(),
                bill.getTotalAmt(),
                bill.getCustPay(),
                bill.getDiscount(),
                bill.getOrderDate()
        );
    }

    public static boolean updateBill(Bill bill) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE bill SET orderID=?,totalAmt=?,custPay=?,discount=?,orderDate=? WHERE billID=?";

        return CrudUtil.crudUtil(
                sql,
                bill.getOrderID(),
                bill.getTotalAmt(),
                bill.getCustPay(),
                bill.getDiscount(),
                bill.getOrderDate(),
                bill.getBillID()
        );

    }

    public static boolean deleteBill(String id) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM bill WHERE billID=?";
        return CrudUtil.crudUtil(
                sql,
                id);
    }

    public static ObservableList<BillTm> getAll() throws SQLException, ClassNotFoundException {

        String sql = "SELECT * FROM bill";

        ObservableList<BillTm> obList = FXCollections.observableArrayList();

        ResultSet resultSet = CrudUtil.crudUtil(sql);
        while (resultSet.next()) {

            obList.add(new BillTm(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getDouble(3),
                    resultSet.getDouble(4),
                    resultSet.getDouble(5),
                    resultSet.getDate(6)
            ));

        }
        return obList;
    }
    public static Bill Search(String billID) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM bill WHERE billID=?";

        ResultSet resultSet = CrudUtil.crudUtil(sql,billID);

        if (resultSet.next()) {
            return (new Bill(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getDouble(3),
                    resultSet.getDouble(4),
                    resultSet.getDouble(5),
                    resultSet.getString(6)
            ));

        }
        return null;


    }

}
