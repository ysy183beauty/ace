package com.ace.business.controller;

import com.ace.common.controller.CommonController;
import com.ace.common.entity.SelectEntity;
import com.ace.common.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

@Controller
@RequestMapping(value ="/business/query")
public class BusinessQueryController extends CommonController {
    @Autowired
    private CommonService commonService;
    private Map<String,Object> map;
    @ResponseBody
    @RequestMapping(value ="/selectAllInfos",method = RequestMethod.POST)
    public Map<String, Object> selectAllInfos(Integer offset, Integer limit){
        StringBuilder sb=new StringBuilder("SELECT * FROM T_INFO");
        List<Object> params=new ArrayList<>();
        try {
            map=super.queryCommonInfo(offset,limit,sb.toString(),params,"oracle");
        } catch (Exception e) {
            e.printStackTrace();
            map=new HashMap<>();
        }
        return map;
    }

    @ResponseBody
    @RequestMapping(value ="/selectOrgs",method = RequestMethod.POST)
    public Map<String, Object> selectOrgs(Integer offset, Integer limit,String org_name,String status,String provice_id){
        String sql="SELECT g.*,p.PRO_NAME FROM T_ORG g LEFT JOIN T_PROVICE p ON g.PROVICE_ID=p.PRO_ID";
        StringBuilder sb=new StringBuilder();
        List<Object> params=new ArrayList<>();
        if(!"".equals(org_name)){
            sb.append(" where org_name like ?");
            params.add("%"+org_name+"%");
        }
        if(!"".equals(status)){
            if(sb.length()>0){
                sb.append(" and status=?");
            }else{
                sb.append(" where status=?");
            }
            params.add(status);
        }
        if(!"".equals(provice_id)){
            if(sb.length()>0){
                sb.append(" and provice_id=?");
            }else{
                sb.append(" where provice_id=?");
            }
            params.add(provice_id);
        }
        try {
             if(sb.length()>0){
                 sql+=sb;
             }
            map=super.queryCommonInfo(offset,limit,sql,params,"oracle");
        } catch (Exception e) {
            e.printStackTrace();
            map=new HashMap<>();
        }
        return map;
    }

    @ResponseBody
    @RequestMapping(value ="/selectStudents",method = RequestMethod.POST)
    public Map<String, Object> selectStudents(Integer offset, Integer limit){
        StringBuilder sb=new StringBuilder("SELECT t.ID,t.NAME,g.GRADENAME,c.CLASSNAME,t.SEX,t.AGE,t.STARTDATE,t.ENDDATE,");
        sb.append("t.INTRODUCE,t.ADDRESS ");
        sb.append("FROM T_STUDENT t LEFT JOIN T_CLASS c on t.CID=c.ID LEFT JOIN T_GRADE g on t.GID=g.id ORDER BY t.ID asc");
        List<Object> params=new ArrayList<>();
        try {
            map=super.queryCommonInfo(offset,limit,sb.toString(),params,"oracle");
        } catch (Exception e) {
            e.printStackTrace();
            map=new HashMap<>();
        }
        return map;
    }

    /**
     * 查询所有省份数据信息
     */
    @ResponseBody
    @RequestMapping(value ="/selectAllProvice",method = RequestMethod.POST)
    public List<SelectEntity> selectAllProvice(){
        String sql="SELECT p.PRO_ID AS labValue,p.PRO_NAME AS labText FROM T_PROVICE p";
        List<Object> params=new ArrayList<>();
        List<SelectEntity> list=commonService.selectAllRecords(sql,params.toArray());
        return list;
    }

    /**
     * 查询班级信息
     */
    @ResponseBody
    @RequestMapping(value ="/selectAllClass",method = RequestMethod.POST)
    public List<SelectEntity> selectAllClass(){
        String sql="SELECT t.ID as labValue,t.CLASSNAME as labText FROM T_CLASS t";
        List<Object> params=new ArrayList<>();
        List<SelectEntity> list=commonService.selectAllRecords(sql,params.toArray());
        return list;
    }
    /**
     * 查询年级信息
     */
    @ResponseBody
    @RequestMapping(value ="/selectAllGrade",method = RequestMethod.POST)
    public List<SelectEntity> selectAllGrade(){
        String sql="SELECT t.ID as labValue,t.GRADENAME as labText FROM T_GRADE t";
        List<Object> params=new ArrayList<>();
        List<SelectEntity> list=commonService.selectAllRecords(sql,params.toArray());
        return list;
    }

}
