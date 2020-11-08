package com.ace.sysytem.controller;

import com.ace.common.service.CommonService;
import com.ace.sqlxml.SqlXmlService;
import com.ace.sysytem.entity.BaseInfoSys;
import com.alibaba.fastjson.JSONArray;
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
@RequestMapping(value ="/sys/deal")
public class OtherController {
    @Autowired
    private CommonService commonService;
    private Map<String,Object> map=null;
    /**
     * 批量插入数据信息
     */
    @ResponseBody
    @RequestMapping(value ="/batchSaveBaseInfo",method = RequestMethod.POST)
    public Map<String,Object> batchSaveBaseInfo(HttpServletRequest request){
        boolean flag=false;
        //获取数据
        map=new HashMap<>();
        String data=request.getParameter("data");
        String tableName=request.getParameter("tableName");
        StringBuilder sql=new StringBuilder();
        try {
            if(data!=null){
                //1、首先删除数据信息
                sql.append("DELETE FROM T_BASE_INFO_SYS WHERE TABLENAME=?");
                List<Object> params=new ArrayList<>();
                params.add(tableName);
                commonService.update(sql.toString(),params.toArray());
                //字符串转换为list
                JSONArray array=JSONArray.parseArray(data);
                //转换为list
                List<BaseInfoSys> list= JSONObject.parseArray(array.toJSONString(), BaseInfoSys.class);
                List<BaseInfoSys> result=new ArrayList<>();
                for(BaseInfoSys b:list){
                    b.setTablename(tableName);
                    result.add(b);
                }
                //清空
                sql.setLength(0);
                sql.append("INSERT INTO T_BASE_INFO_SYS(ID,FIELDNAME,FIELDTYPE,FIELDLENGTH,FIELDLABEL," +
                        "LISTDISPLAY,FORMDISPLAY,QUERYDISPLAY,TABLENAME,QUERYSTATUSMAP)");
                sql.append(" VALUES(ID_SEQ.nextval,:fieldname,:fieldtype,:fieldlength," +
                        ":fieldlabel,:listdisplay,:formdisplay,:querydisplay,:tablename,:querystatusmap)");
                commonService.batchUpdate(result,sql.toString());
                flag = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        map.put("result",flag);
        return map;
    }
    @ResponseBody
    @RequestMapping(value ="/updateStatusMap",method = RequestMethod.POST)
    public Map<String,Object> updateStatusMap(HttpServletRequest request){
        Map<String,Object> map=new HashMap<>();
        StringBuilder sql=new StringBuilder("UPDATE T_BASE_INFO_SYS t");
        try {
            String id=request.getParameter("id");
            String type=request.getParameter("type");
            String content=request.getParameter("content");
            List<Object> params=new ArrayList<>();
            params.add(content);
            params.add(id);
            if("1".equals(type)){
                sql.append(" set t.STATUSMAP=?");
            }else{
                sql.append(" set t.URL=?");
            }
            sql.append(" WHERE t.ID=?");
            commonService.update(sql.toString(),params.toArray());
            map.put("status",true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status",false);
        }
        return map;
    }
}
