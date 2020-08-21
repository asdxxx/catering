package com.ruoyi;

import com.ruoyi.catering.domain.Restaurant;
import com.ruoyi.catering.utils.BusinessUtil;
import com.ruoyi.system.domain.SysUser;
import org.apache.velocity.Template;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @program: catering
 * @description:
 * @author: liu sheng yin
 * @create: 2020-08-19 09:40
 */
public class Test {
    //使用：
    public static void main(String[] args) {
        Map map = new HashMap();
        map.put("user", "张三");
        map.put("store", "123");
        String s = BusinessUtil.processTemplate("${use11r}你好，${store}需要回收！", map);
        System.out.println(s);
    }

}