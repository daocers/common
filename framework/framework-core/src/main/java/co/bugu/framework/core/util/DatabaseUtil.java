package co.bugu.framework.core.util;

import co.bugu.framework.core.exception.DbBatchException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by daocers on 2016/9/20.
 * 数据库辅助类，用于批量操作
 * 需要
 */
public class DatabaseUtil {
    private static Logger logger = LoggerFactory.getLogger(DatabaseUtil.class);

    /**
     * 批量insert
     *
     * insert into user(id, name, age) values(1, 2, 3), (4,5,6);
     * insert into user(id, name, age) values (1,2,3); insert into user(id, name, age) values (4, 5,6);
     * 注意以上两种方式，第一种效率要高很多
     *
     * @param dataSource
     * @param firstSql  insert into user (id, namg, age) values 格式即可
     * @param  dataList firstSql中的参数
     * @throws DbBatchException
     */
    public static void batchInsert(DataSource dataSource, String firstSql, List<String[]> dataList) throws DbBatchException {
        Connection conn = null;
        try{
            conn = dataSource.getConnection();
            conn.setAutoCommit(false);
            PreparedStatement ps = conn.prepareStatement("");
            if(firstSql.toLowerCase().contains("values")){
                firstSql = firstSql.split("values")[0];
            }
            if(firstSql.endsWith(",")){
                firstSql = firstSql.substring(0, firstSql.length() - 1);
            }

            StringBuilder builder = new StringBuilder();
            builder.append(firstSql).append(" values ");
            for(String[] rowData: dataList){
                builder.append(" (");
                for(int i = 0; i < rowData.length; i++){
                    String cell = rowData[i];
                    builder.append("'").append(cell).append("'");
                    if(i != rowData.length - 1){
                        builder.append(", ");
                    }
                }
                builder.append("), ");
            }
            String sql = builder.substring(0, builder.length() - 2) + ";";
            logger.debug("批量sql为: {}", sql);
            ps.addBatch(sql);
            ps.executeBatch();
            conn.commit();
            conn.setAutoCommit(true);
            ps.close();

        }catch (Exception e){
            throw new DbBatchException("批量insert异常", e);
        }finally {
            if(conn != null){
                try {
                    conn.close();
                } catch (SQLException e) {
                    logger.error("关闭数据库连接异常", e);
                }
            }
        }
    }
}
