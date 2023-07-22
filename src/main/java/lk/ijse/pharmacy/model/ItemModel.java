package lk.ijse.pharmacy.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.pharmacy.db.DBConnection;
import lk.ijse.pharmacy.dto.Item;
import lk.ijse.pharmacy.dto.PlaceOrder;
import lk.ijse.pharmacy.tm.ItemTM;
import lk.ijse.pharmacy.util.CrudUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemModel {
    public static boolean save(Item itmAdd) throws SQLException, ClassNotFoundException {  //data baes ekata dana set eka
        String sql = "INSERT INTO medicine(medID, medName,medUnitPrice, type, mfgDate , expDate, qtyOnHand) " +
                "VALUES(?, ?, ?, ? ,? ,?, ?)";
//        try (PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql)) {
//
//            pstm.setString(1, itmAdd.getItemCode());
//            pstm.setString(2, itmAdd.getItemMedName());
//            pstm.setString(3, itmAdd.getItemType());
//            pstm.setString(4, itmAdd.getItemDate());
//            pstm.setString(5, itmAdd.getItemmfgDate());
//            pstm.setString(6, itmAdd.getItemQOH());
//
//
//            return pstm.executeUpdate() > 0;
//        }

        return CrudUtil.crudUtil(
                sql,
                itmAdd.getItemCode(),
                itmAdd.getItemMedName(),
                itmAdd.getItemUnitPrice(),
                itmAdd.getItemType(),
                itmAdd.getItemmfgDate(),
                itmAdd.getItemDate(),
                itmAdd.getItemQOH()
        );
    }
    public static boolean delete(String id) throws SQLException, ClassNotFoundException {

        String sql = "DELETE FROM medicine WHERE medID = ?";

//        try (PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql)) {
//
//            pstm.setString(1, id);
//            return pstm.executeUpdate() > 0;
//        }

        return CrudUtil.crudUtil(
                sql,
                id
        );
    }
    public static ObservableList<ItemTM> getAll() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM medicine";

//        try (PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql)) {
//
//            ResultSet resultSet = pstm.executeQuery();
//
//            ObservableList<ItemTM> dataList = FXCollections.observableArrayList();
//
//            while (resultSet.next()) {
//                dataList.add(new ItemTM(
//                        resultSet.getString(1),
//                        resultSet.getString(2),
//                        resultSet.getString(3),
//                        resultSet.getString(4),
//                        resultSet.getString(5),
//                        resultSet.getString(6),
//                        resultSet.getString(7)
//                ));
//            }
//            return dataList;
//        }

        ObservableList<ItemTM> obList = FXCollections.observableArrayList();

        ResultSet resultSet = CrudUtil.crudUtil(sql);
        while (resultSet.next()) {
            obList.add(new ItemTM(
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
    public static boolean update(Item supAdd) throws SQLException {

        String sql = "UPDATE medicine SET medName = ?, medUnitPrice=?, type = ?, mfgDate = ?, expDate = ?, qtyOnHand = ? WHERE medID = ?";


        try (PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql)) {

            pstm.setString(1, supAdd.getItemMedName());
            pstm.setString(2, supAdd.getItemUnitPrice());
            pstm.setString(3, supAdd.getItemType());
            pstm.setString(4, supAdd.getItemDate());
            pstm.setString(5, supAdd.getItemmfgDate());
            pstm.setString(6, supAdd.getItemQOH());
            pstm.setString(7, supAdd.getItemCode());

            return pstm.executeUpdate() > 0;
        }
    }


    public static List<String> loadCodes() throws SQLException, ClassNotFoundException {
        String sql = "SELECT medID FROM medicine";
        ResultSet resultSet = CrudUtil.crudUtil(sql);

        List<String> data =new ArrayList<>();

        while (resultSet.next()) {
            data.add(resultSet.getString(1));
        }
        return data;
    }

    public static Item findById(String itemcode) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM medicine WHERE medID=?";

        ResultSet resultSet = CrudUtil.crudUtil(sql,itemcode);
        if(resultSet.next()){
            return (new Item(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(7),
                    resultSet.getString(6)
            ));
        }
        return null;
    }

    public static boolean updateQty(List<PlaceOrder> placeOrderList) throws SQLException, ClassNotFoundException {
        for(PlaceOrder placeorder : placeOrderList) {
            if(!updateQty(placeorder)) {
                return false;
            }
        }
        return true;
    }

    private static boolean updateQty(PlaceOrder placeorder) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE medicine SET qtyOnHand = (qtyOnHand - ?) WHERE medID = ?";

        return CrudUtil.crudUtil(
                sql,
                placeorder.getOrdereditemqty(),
                placeorder.getOrdereditemcode()
        );
    }
}
