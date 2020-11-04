package com.ace.sysytem.controller;
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
public class QueryController {
    @Autowired
    private CommonService commonService;
    //查询所有sql语句信息
    @ResponseBody
    @RequestMapping(value ="/selectAllSql",method = RequestMethod.POST)
    public List<SqlEntity> selectAllSql(){
        return commonService.selectSqlAll();
    }
    //查询sql语句要查询的列名信息
    @ResponseBody
    @RequestMapping(value ="/selectColumns",method = RequestMethod.POST)
    public List selectColumns(HttpServletRequest request){
        List<BaseInfoSys> list=null;
        try {
            String sqlId=request.getParameter("sqlId");
            list= commonService.selectColumns(commonService.selectSql(sqlId));
        } catch (Exception e) {
            e.printStackTrace();
            list=new ArrayList<>();
        }
        return list;
    }

    /**
     * 通过表名查询是否存在
     */
    @ResponseBody
    @RequestMapping(value ="/selectBaseInfoSysList",method = RequestMethod.POST)
    public List selectBaseInfoSysList(HttpServletRequest request){
        String tableName=request.getParameter("tableName");
        String sql=commonService.selectSql("sql3");
        List<Object> params=new ArrayList<>();
        params.add(tableName);
        List list=commonService.selectList(sql,params.toArray());
        String json= JSON.toJSONString(list);
        List<BaseInfoSys> data= JSONObject.parseArray(json,BaseInfoSys.class);
        return data;
    }

    @ResponseBody
    @RequestMapping(value ="/selectAllBaseInfo",method = RequestMethod.POST)
    public Map<String, Object> selectAllBaseInfo(Integer offset, Integer limit,BaseInfoSys baseInfoSys){
        Map<String,Object> map= new HashMap<>();
        Integer pageIndex=0;
        if(offset==0){
            pageIndex=1;
        }else{
            pageIndex=(offset/limit)+1;
        }
        PageParam pageParam=new PageParam();
        pageParam.setPage(pageIndex);
        pageParam.setLimit(limit);
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
        Pagination p = commonService.selectListByPage(sb.toString(), pageParam, params, "oracle");
        List list=p.getResultList();
        String json=JSON.toJSONString(list);
        List<BaseInfoSys> queryData= JSONObject.parseArray(json,BaseInfoSys.class);
        map.put("rows",queryData);
        map.put("total",p.getTotalRows());
        return map;
    }
}
