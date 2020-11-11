package com.ace.business.controller;

import com.ace.common.controller.CommonController;
import com.ace.common.service.CommonService;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
@RequestMapping(value ="/business/deal")
public class BusinessOtherController extends CommonController {
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
            super.saveInfo(data,sql.toString());
            map.put("status",true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status",false);
        }
        return map;
    }

    @ResponseBody
    @RequestMapping(value ="/saveOrg",method = RequestMethod.POST)
    public Map<String,Object> saveOrg(HttpServletRequest request){
        Map<String,Object> map=new HashMap<>();
        try {
            //获取数据信息
            String data=request.getParameter("data");
            StringBuilder sql=new StringBuilder("insert INTO T_ORG(ORG_ID,ORG_NAME,ORG_ADDRESS,STARTTIME,ENDTIME,STATUS,ORG_INFO,MONEY,PROVICE_ID) ");
            sql.append("values(ID_SEQ.nextval,:org_name,:org_address,:starttime,:endtime,:status,:org_info,:money,:provice_id)");
            super.saveInfo(data,sql.toString());
            map.put("status",true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status",false);
        }
        return map;
    }
    @ResponseBody
    @RequestMapping(value ="/deleteOrg",method = RequestMethod.POST)
    public Map<String,Object> deleteOrg(HttpServletRequest request){
        Map<String,Object> map=new HashMap<>();
        String data=request.getParameter("data");
        //转换为json数据
        List<JSONObject> params=new ArrayList<>();
        try {
            JSONObject jsonObject = JSONObject.parseObject(data);
            params.add(jsonObject);
            String sql="delete from t_org where ORG_ID=:org_id";
            commonService.batchUpdate(params,sql);
            map.put("status",true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status",false);
        }
        return map;
    }
    @ResponseBody
    @RequestMapping(value ="/deleteMutilInfo",method = RequestMethod.POST)
    public Map<String,Object> deleteMutilInfo(HttpServletRequest request){
        Map<String,Object> map=new HashMap<>();
        String data=request.getParameter("data");
        try {
            JSONArray array=JSONArray.parseArray(data);
            List<Map<String,Object>> params=new ArrayList<>();
            JSONObject obj;
            for(int i=0;i<array.size();i++){
                obj=JSONObject.parseObject(array.get(i)+"");
                Map<String,Object> m=new HashMap<>();
                m.put("aid",obj.get("aid"));
                params.add(m);
            }
            //删除数据信息
            String sql="delete from T_INFO where AID=:aid";
            commonService.batchUpdate(params,sql);
            map.put("status",true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status",false);
        }
        return map;
    }
}
