package lk.ijse.pharmacy.model;

import lk.ijse.pharmacy.dto.User;
import lk.ijse.pharmacy.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserModel {
    public static boolean save(User user) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO user(Username,Password,Email)VALUES(?,?,?)";

        return CrudUtil.crudUtil(sql,
                user.getUsername(),
                user.getPassword(),
                user.getEmial());


    }

    public static User SearchById(String username) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM User WHERE username=?";
        ResultSet resultSet = null;

        resultSet = CrudUtil.crudUtil(sql,
                username);
        if (resultSet.next()) {
            return new User(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3)
            );
        }


        return null;
    }
}
