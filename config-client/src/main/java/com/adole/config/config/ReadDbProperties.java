package com.adole.config.config;

import com.adole.config.common.Constant;
import com.adole.config.common.EncryptUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.Ordered;
import org.springframework.core.env.*;
import org.springframework.util.StringUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ReadDbProperties implements EnvironmentPostProcessor, Ordered {

    //命令行参数
    private static final String APP_MODULE_BROKER = "com.adole.broker";

    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        MutablePropertySources propertySources = environment.getPropertySources();
        String argParams = "";
        for (PropertySource<?> propertySource : propertySources) {
            if (propertySource instanceof SimpleCommandLinePropertySource) {
                String property = ((SimpleCommandLinePropertySource) propertySource).getProperty(CommandLinePropertySource.DEFAULT_NON_OPTION_ARGS_PROPERTY_NAME);
                String[] nonOptionArgs = StringUtils.commaDelimitedListToStringArray(property);
                for (String nonOptionArg : nonOptionArgs) {
                    if (nonOptionArg.contains(APP_MODULE_BROKER + "=")) {
                        argParams = nonOptionArg;
                        break;
                    }
                }
            }
        }
        String module = "";
        String app = "";
        if (!StringUtils.isEmpty(argParams)) {
            String[] split = argParams.split("=");
            if (split.length != 2) {
                throw new RuntimeException("paramter type error!");
            }

            module = split[1];
        }
        //获取环境变量
        Map<String, String> map = new HashMap();
        map = System.getenv();
        String config = map.get(Constant.CONFIG_PARAM);
        System.out.println("环境变量配置：" + config);
        String[] split = config.split("\\|");
        Properties newProperties = new Properties();
        try {
            EncryptUtil des = new EncryptUtil(Constant.CONFIG_PARAM, "utf-8");
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(split[0], split[1], des.decode(split[2]));
            String sql = "SELECT param,`value` FROM `t_param_value` WHERE module = ? AND `status` = 0;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, module);
            ResultSet resultSet = ps.executeQuery();
            int col = resultSet.getMetaData().getColumnCount();
            while (resultSet.next()) {
                System.out.println(resultSet.getString(1) + ":" + resultSet.getString(2));
                newProperties.put(resultSet.getString(1), resultSet.getString(2));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        propertySources.addFirst(new PropertiesPropertySource("application.properties", newProperties));
    }

    public int getOrder() {
        return 0;
    }
}
