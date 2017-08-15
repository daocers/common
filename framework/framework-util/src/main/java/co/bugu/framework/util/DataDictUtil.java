package co.bugu.framework.util;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by daocers on 2016/7/31.
 */
public class DataDictUtil {
    private static String t_url;
    private static String t_user;
    private static String t_pwd;

    static {
        t_url = "jdbc:mysql://101.200.205.46:3306/retailer?useUnicode=true&characterEncoding=UTF8";
        t_user = "root";
        t_pwd = "HERO4black1";
    }

    public static void init(String ip, String user, String pwd, String db) {
        t_url = "jdbc:mysql://101.200.205.46:3306/retailer?useUnicode=true&characterEncoding=UTF8";
        t_user = "root";
        t_pwd = "HERO4black1";
    }

    public static void getAllTables(){
        Connection conn = null;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(t_url, t_user, t_pwd);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("show tables");
            while(rs.next()){
                String table = rs.getString(1);
                Statement stmt1 = conn.createStatement();
                ResultSet rs1 = stmt1.executeQuery("show full COLUMNs from " + table);
                System.out.println("*********");
                System.out.println(table);
                System.out.println("|字段名|类型|说明|");
                System.out.println("|:----|:---|:----|");

                while(rs1.next()){
                    String field = rs1.getString(1);
                    String type = rs1.getString(2);
                    String comment = rs1.getString(9);
                    System.out.println("|" + field + "|" + type + "|" + comment + "|");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static List<String> execute(String table) {
        Connection conn = null;
        List<String> fields = new ArrayList<String>();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(t_url, t_user, t_pwd);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("desc " + table);
            while (rs.next()) {
                if (!rs.getString(1).contains("gmt") && !"id".equals(rs.getString(1))) {
//                    fields.add(StringUtil.toLowerCase(rs.getString(1)));
                }
            }
        } catch (SQLException e) {
            System.out.println("MySQL操作错误");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return fields;
    }

    public static void main(String[] args){
//        execute("mall_goods");
        getAllTables();

    }

}
