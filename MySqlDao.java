package wcfb.dao;

import java.sql.*;

/**
 * Created by wcfb on 2019/3/20
 * 数据库处理
 */
public class MySqlDao {

    private static String DRIVERCLASS = "com.mysql.jdbc.Driver";
    private static String URL = "jdbc:mysql://139.196.98.202:3306/spring_evaluation?characterEncoding=UTF-8";
    private static String USER = "root";
    private static String PASSWORD = "123456";
    private static Connection con = null;
    // 注册驱动,创建数据库连接
    static {
//        readDataConf();
        try {
            Class.forName(DRIVERCLASS);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            con = DriverManager.getConnection(URL,USER,PASSWORD);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
    //读取数据库配置文件
//    public static void readDataConf() {
//        File file = new File("setting/dao/dao.ini");
//        FileInputStream fis = null;
//        try {
//            fis = new FileInputStream(file);
//        } catch (FileNotFoundException e) {
//        }
//        String allText = "";
//        for (int i = 0; i < file.length(); i++) {
//            try {
//                allText+= (char) fis.read();
//            } catch (IOException e) {
//            }
//        }
//        String[]  items= allText.split("#");
//        DRIVERCLASS=items[1].trim();
//        URL=items[2].trim();
//        USER=items[3].trim();
//        PASSWORD=items[4].trim();
//    }
    // 获取数据库连接
    public static Connection getConnection() {
        if (con == null) {
            try {
                con = DriverManager.getConnection(URL, USER, PASSWORD);
            } catch (SQLException e) {
            }
        }
        return con;
    }
    //数据库查询
    public static ResultSet select(String sql, String[] items) {
        PreparedStatement pst=null;
        getConnection();
        try {
            pst=con.prepareStatement(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for(int i=0;i<items.length;i++) {
            try {
                pst.setString(i+1, items[i]);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        try {
            return pst.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    //数据库添加/删除/更改
    public static int update(String sql,String[] items) {
        PreparedStatement pst=null;
        getConnection();
        try {
            pst=con.prepareStatement(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for(int i=0;i<items.length;i++) {
            try {
                pst.setString(i+1, items[i]);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        try {
            return pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
}
