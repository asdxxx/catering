package com.ruoyi;

import com.ruoyi.catering.utils.RedisUtil;
import com.ruoyi.framework.web.domain.server.Sys;
import redis.clients.jedis.GeoCoordinate;
import redis.clients.jedis.GeoRadiusResponse;
import redis.clients.jedis.Jedis;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: catering
 * @description:
 * @author: liu sheng yin
 * @create: 2020-09-26 13:46
 */
public class Test {
    public static void main(String[] args) {
//        Jedis redis = RedisUtil.getJedis();
//        String memberName1 = "2";
//        GeoCoordinate geoCoordinate1 = new GeoCoordinate(120.52792, 27.995340);
////        String memberName2 = "瞿溪风味集";
//        String memberName2 = "3";
//        GeoCoordinate geoCoordinate2 = new GeoCoordinate(120.532416, 27.992773);
////        String memberName3 = "小胖籽餐厅";
//        String memberName3 = "4";
//        GeoCoordinate geoCoordinate3 = new GeoCoordinate(120.53879, 27.981310);
//        Map add = new HashMap();
//        add.put(memberName1, geoCoordinate1);
//        add.put(memberName2, geoCoordinate2);
//        add.put(memberName3, geoCoordinate3);
//        redis.geoadd("restaurant", add);
////        redis.del("restaurant");

        GeoCoordinate geoCoordinate = new GeoCoordinate(116.46, 39.92);
//        GeoCoordinate geoCoordinate = new GeoCoordinate(121.175410, 28.386766);
        List<GeoRadiusResponse> list = RedisUtil.geoRadius("restaurant", geoCoordinate, 10);
        System.out.println("size=" + list.size());
        for (GeoRadiusResponse geoRadiusResponse : list) {
            System.out.println(geoRadiusResponse.getMemberByString() + "=====" + geoRadiusResponse.getDistance());
        }

//        String a = "120.870497";
//        double b = Double.parseDouble(a);
//        System.out.println(a);
//        System.out.println(b);
    }
}