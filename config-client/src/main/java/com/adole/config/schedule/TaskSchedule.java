package com.adole.config.schedule;

import com.adole.config.common.Constant;
import com.sun.org.apache.xml.internal.security.utils.Base64;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Component
public class TaskSchedule {

    @Scheduled(fixedRate = 10000)
    public void loadSystem(){
        BASE64Encoder encoder = new BASE64Encoder();
        BASE64Decoder decoder = new BASE64Decoder();
        Map<String,String> map = new HashMap();
        map = System.getenv();
        String url = map.get(Constant.CONFIG_PARAM);

        String pass = "123456";
        try {
            System.out.println(new String (decoder.decodeBufferToByteBuffer(pass).array(),"GBK"));
            System.out.println(encoder.encode(decoder.decodeBufferToByteBuffer(pass)));
        }catch (Exception e){

        }


    }
}
