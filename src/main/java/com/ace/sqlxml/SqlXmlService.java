package com.ace.sqlxml;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
@Service
public class SqlXmlService {
    /**
     * 通过id获取sql语句对象信息
     */
    public SqlEntity getSqlEntity(String id){
        SqlEntity sqlEntity=null;
        try {
            //读取Resource目录下的XML文件
            Resource resource = (Resource) new ClassPathResource("/sqlxml/sql.xml");
            //利用输入流获取XML文件内容
            BufferedReader br = new BufferedReader(new InputStreamReader(resource.getInputStream(), "UTF-8"));
            StringBuffer buffer = new StringBuffer();
            String line = "";
            while ((line = br.readLine()) != null) {
                buffer.append(line);
            }
            br.close();
            SqlList data=(SqlList)XmlBuilder.xmlStrToObject(SqlList.class, buffer.toString());
            if(data!=null){
                List<SqlEntity> list = data.getList();
                for(SqlEntity sql:list){
                    if(id.equals(sql.getId())){
                        sqlEntity=sql;
                        break;
                    }
                }
            }else{
                sqlEntity=new SqlEntity();
            }
        }catch (Exception e) {
            e.printStackTrace();
            sqlEntity=new SqlEntity();
        }
        return sqlEntity;
    }

    /**
     * 获取所有sql配置文件里的信息
     */
    public List<SqlEntity> getSqlAll(){
        List<SqlEntity> list=null;
        try {
            //读取Resource目录下的XML文件
            Resource resource = (Resource) new ClassPathResource("/sqlxml/sql.xml");
            //利用输入流获取XML文件内容
            BufferedReader br = new BufferedReader(new InputStreamReader(resource.getInputStream(), "UTF-8"));
            StringBuffer buffer = new StringBuffer();
            String line = "";
            while ((line = br.readLine()) != null) {
                buffer.append(line);
            }
            br.close();
            SqlList data=(SqlList)XmlBuilder.xmlStrToObject(SqlList.class, buffer.toString());
            if(data!=null){
                list = data.getList();
            }else{
                list=new ArrayList<>();
            }
        }catch (Exception e) {
            e.printStackTrace();
            list=new ArrayList<>();
        }
        return list;
    }
}
