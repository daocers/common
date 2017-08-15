package co.bugu.framework.core.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by daocers on 2016/7/29.
 */
public class MysqlUtil {
    public static List<String> getTables(String database) throws SQLException {
        List<String> tableList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement ps = connection.prepareStatement("show tables");
        ResultSet resultSet = ps.executeQuery();
        while(resultSet.next()){
        }
        return tableList;
    }
}
