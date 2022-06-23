package com.learn.myzhxy;

import com.learn.myzhxy.pojo.Admin;
import com.learn.myzhxy.pojo.Clazz;
import com.learn.myzhxy.pojo.Student;
import com.learn.myzhxy.service.impl.AdminServiceImpl;
import com.learn.myzhxy.service.impl.ClazzServiceImpl;
import com.learn.myzhxy.util.CreateVerifiCodeImage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.w3c.dom.ls.LSException;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@SpringBootTest
class MyzhxyApplicationTests {

    @Test
    void contextLoads() {
    }
    @Autowired
    AdminServiceImpl adminService;
    @Autowired
    ClazzServiceImpl clazzService;
    @Test
    public void test1(){
        List<Clazz> list = clazzService.list();
        list.stream().forEach(System.out::println);
//        Map<Integer, String> collect = list.stream().collect(Collectors.toMap(Clazz::getId, t->Math.max(Clazz::getId,Clazz::getNumber)));
//        System.out.println(collect);
    }
    @Test
    public void test2(){
        BufferedImage verifiCodeImage = CreateVerifiCodeImage.getVerifiCodeImage();
        char[] verifiCode = CreateVerifiCodeImage.getVerifiCode();
        System.out.println(verifiCodeImage);
        System.out.println(verifiCode);
    }
    @Test
    public void test3(){
         List<HashMap<String,String>> list1 = new ArrayList<>();
         List<HashMap<String,String>> list2 = new ArrayList<>();

        for(int i = 0;i< 9;i++){
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("username", "qq"+i);
            map.put("passWord", "123"+i);
            map.put("sss", "sss"+i);
            list1.add(map);
        }
        for(int j = 0;j< 5;j++){
            HashMap<String, String> map1 = new HashMap<String, String>();
            map1.put("username", "qq"+j);
            map1.put("passWord", "123"+j);
            list2.add(map1);
        }
        System.out.println("list1:"+list1);
        System.out.println("list2:"+list2);
        HashMap<String,String> m1= new HashMap<>();
        m1.put("username","qq0");
        m1.put("passWord","1230");
        List<HashMap<String,String>> list3 = new ArrayList<>();
        for(int a = 0; a <list1.size();a++){
            for (int b = 0;b < list2.size();b++){
                System.out.println(a+"_"+b+"_"+list1.get(a));
                if((!list1.get(a).get("username").equals(list2.get(b).get("username")))){
                    list1.remove(a);
                    System.out.println(a+"_"+list1);
                }


            }

        }
        System.out.println("remove-list3:"+list3);
    }
}
