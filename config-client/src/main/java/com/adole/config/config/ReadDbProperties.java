package com.adole.config.config;

import com.adole.config.common.Constant;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.Ordered;
import org.springframework.core.env.*;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ReadDbProperties implements EnvironmentPostProcessor, Ordered {

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
        String brokerCode = "";
        String app = "";
        if (!StringUtils.isEmpty(argParams)) {
            String[] split = argParams.split("=");
            if (split.length != 2 || split[1].split("\\|").length != 2) {
                throw new RuntimeException("paramter type error!");
            }

            String[] paramValue = split[1].split("\\|");
            app = paramValue[0];
            brokerCode = paramValue[1];
        }

        Map<String, String> map = new HashMap();
        map = System.getenv();
        String config = map.get(Constant.CONFIG_PARAM);


        String[] split = config.split("\\|");
        Properties newProperties = new Properties();

    }

    public int getOrder() {
        return 0;
    }
}
