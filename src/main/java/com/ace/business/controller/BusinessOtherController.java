package com.ace.business.controller;

import com.ace.common.service.CommonService;
import com.alibaba.fastjson.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
@RequestMapping(value ="/business/deal")
public class BusinessOtherController {
    @Autowired
    private CommonService commonService;
    @ResponseBody
    @RequestMapping(value ="/saveStudentInfo",method = RequestMethod.POST)
    public Map<String,Object> saveStudentInfo(HttpServletRequest request){
        Map<String,Object> map=new HashMap<>();
        try {
            //获取数据信息
            String data=request.getParameter("data");
            StringBuilder sql=new StringBuilder("INSERT INTO T_STUDENT(ID,NAME,GID,CID,SEX,AGE,STARTDATE,ENDDATE,ADDRESS,INTRODUCE) ");
            sql.append("values(ID_SEQ.nextval,:name,:gid,:cid,:sex,:age,:startdate,:enddate,:address,:introduce)");
            JSONArray arr=JSONArray.parseArray(data);
            Map<String,Object> fieldInfo=(Map<String,Object>)arr.get(0);
            Map<String,Object> fieldType=(Map<String,Object>)arr.get(1);
            commonService.update(sql.toString(),fieldInfo,fieldType);
            map.put("status",true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status",false);
        }
        return map;
    }
}
