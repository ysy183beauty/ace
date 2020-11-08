package com.ace.sysytem.controller;
import com.ace.common.controller.CommonController;
import com.ace.common.service.CommonService;
import com.ace.page.PageParam;
import com.ace.page.Pagination;
import com.ace.sqlxml.SqlEntity;
import com.ace.sqlxml.SqlXmlService;
import com.ace.sysytem.entity.BaseInfoSys;
import com.alibaba.fastjson.JSON;
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
@RequestMapping(value ="/sys/query")
public class QueryController extends CommonController {
    @Autowired
    private CommonService commonService;
    @Autowired
    private  Map<String,Object> map;
    //查询sql语句要查询的列名信息
    @ResponseBody
    @RequestMapping(value ="/selectBaseInfoSysBySql",method = RequestMethod.POST)
    public Map<String,Object> selectColumns(HttpServletRequest request){
        Map<String,Object> map=new HashMap<>();
        //获取传递过来的参数
        String sql=request.getParameter("sql");
        List<BaseInfoSys> list=new ArrayList<>();
        try {
            list= commonService.selectBaseInfoSysBySql(sql);
            map.put("rows",list);
            map.put("total",list.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 通过表名查询是否存在
     */
    @ResponseBody
    @RequestMapping(value ="/selectBaseInfoSysList",method = RequestMethod.POST)
    public List selectBaseInfoSysList(HttpServletRequest request){
        String sql="SELECT * FROM T_BASE_INFO_SYS f where f.TABLENAME= ?";
        String tableName=request.getParameter("tableName");
        List<Object> params=new ArrayList<>();
        params.add(tableName);
        List<BaseInfoSys> data=commonService.selectAllRecords(sql,params.toArray());
        return data;
    }

    @ResponseBody
    @RequestMapping(value ="/selectAllBaseInfo",method = RequestMethod.POST)
    public Map<String, Object> selectAllBaseInfo(Integer offset, Integer limit,BaseInfoSys baseInfoSys){
        StringBuilder sb=new StringBuilder("SELECT * FROM T_BASE_INFO_SYS");
        List<Object> params=new ArrayList<>();
        if(baseInfoSys.getTablename().length()>0){
            sb.append(" where TABLENAME like ?");
            params.add("%"+baseInfoSys.getTablename()+"%");
        }
        if(baseInfoSys.getFieldname().length()>0){
            sb.append(" and FIELDNAME like ?");
            params.add("%"+baseInfoSys.getFieldname()+"%");
        }
        try {
            map=super.queryCommonInfo(offset,limit,sb.toString(),params,"oracle");
        } catch (Exception e) {
            e.printStackTrace();
            map=new HashMap<>();
        }
        return map;
    }
}
