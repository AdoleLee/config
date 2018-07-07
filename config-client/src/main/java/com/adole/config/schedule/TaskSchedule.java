package com.adole.config.schedule;

import com.adole.config.common.Constant;
import com.adole.config.common.EncryptUtil;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Component
public class TaskSchedule {

    @Scheduled(fixedRate = 1000000000)
    public void loadSystem() {
        try {
            Map<String, String> map = new HashMap();
            map = System.getenv();
            String config = map.get(Constant.CONFIG_PARAM);
            config = "jdbc:mysql://localhost:3306/dbconfig?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=Hongkong|root|cD1axKGCmlY=";
            System.out.println(config);
            String[] arr = config.split("\\|");
            String driver = "com.mysql.cj.jdbc.Driver";
            String url = arr[0];
            String user = arr[1];
            String passwd = arr[2];
            EncryptUtil des = new EncryptUtil(Constant.CONFIG_PARAM, "utf-8");
            passwd = des.decode(passwd);

            Class.forName(driver);
            Connection connection = DriverManager.getConnection(url, user, passwd);

            String sql = "SELECT * FROM `t_param_value`";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet resultSet = ps.executeQuery();
            int col = resultSet.getMetaData().getColumnCount();
            while (resultSet.next()) {
                for (int i = 1; i <= col; i++) {

                    System.out.println(resultSet.getString(i));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
