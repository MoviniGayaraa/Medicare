package lk.ijse.pharmacy.model;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import lk.ijse.pharmacy.dto.PlaceOrder;
import lk.ijse.pharmacy.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class OrderDetailModel {

    public static boolean save(String orderid, List<PlaceOrder> placeOrderList) throws SQLException, ClassNotFoundException {
        for(PlaceOrder placeOrder : placeOrderList) {
            if(!save(orderid, placeOrder)) {
                return false;
            }
        }
        return true;
    }

    private static boolean save(String orderid, PlaceOrder placeOrder) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO orderdetail(medID,orderID,qty)" +
                "VALUES(?, ?, ?)";

        return CrudUtil.crudUtil(
                sql,
                placeOrder.getOrdereditemcode(),
                orderid,
                placeOrder.getOrdereditemqty()
        );
    }

    public static ObservableList<PieChart.Data> getDataToPieChart() throws SQLException, ClassNotFoundException {
        String sql="SELECT medicine.medName,COUNT(orderdetail.medID) FROM orderDetail INNER JOIN medicine ON medicine.medID = orderdetail.medID INNER JOIN orders\n" +
                " ON orderdetail.orderID=orders.orderID WHERE MONTH(orders.date) = MONTH(CURRENT_DATE()) GROUP BY medicine.medName LIMIT 5;\n";
        ObservableList<PieChart.Data> datalist = FXCollections.observableArrayList();
        ResultSet resultSet = CrudUtil.crudUtil(sql);

        while(resultSet.next()){
            datalist.add(
                    new PieChart.Data(
                            resultSet.getString(1),
                            resultSet.getInt(2)
                    )
            );
        }
        return datalist;

    }
}
