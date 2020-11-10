package com.ace.business.controller;

import com.ace.common.service.CommonService;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            List<Object> params=new ArrayList<>();
            StringBuilder sql=new StringBuilder("INSERT INTO T_STUDENT(ID,NAME,GID,CID,STARTDATE,ENDDATE,INTRODUCE,SEX,ADDRESS,AGE) ");
            sql.append("values(ID_SEQ.nextval,?,?,?,?,?,?,?,?,?)");
            //转换为json
            JSONObject jsonObject = JSONObject.parseObject(data);
            params.add(jsonObject.get("NAME"));
            params.add(jsonObject.get("GRADENAME"));
            params.add(jsonObject.get("CLASSNAME"));
            params.add(jsonObject.get("STARTDATE"));
            params.add(jsonObject.get("ENDDATE"));
            params.add(jsonObject.get("INTRODUCE"));
            params.add(jsonObject.get("SEX"));
            params.add(jsonObject.get("ADDRESS"));
            params.add(jsonObject.get("AGE"));
            commonService.update(sql.toString(),params.toArray());
            map.put("status",true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status",false);
        }
        return map;
    }
}
