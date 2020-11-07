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
    public Map<String, Object> selectOrgs(Integer offset, Integer limit){
        StringBuilder sb=new StringBuilder("SELECT * FROM T_ORG");
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
    @RequestMapping(value ="/selectStudents",method = RequestMethod.POST)
    public Map<String, Object> selectStudents(Integer offset, Integer limit){
        StringBuilder sb=new StringBuilder("SELECT * FROM T_STUDENT");
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
        String sql=commonService.selectSql("sql11");
        List<Object> params=new ArrayList<>();
        List<SelectEntity> list=commonService.selectList(sql,params.toArray());
        return list;
    }

    /**
     * 查询班级信息
     */
    @ResponseBody
    @RequestMapping(value ="/selectAllClass",method = RequestMethod.POST)
    public List<SelectEntity> selectAllClass(){
        String sql=commonService.selectSql("sql14");
        List<Object> params=new ArrayList<>();
        List<SelectEntity> list=commonService.selectList(sql,params.toArray());
        return list;
    }
    /**
     * 查询年级信息
     */
    @ResponseBody
    @RequestMapping(value ="/selectAllGrade",method = RequestMethod.POST)
    public List<SelectEntity> selectAllGrade(){
        String sql=commonService.selectSql("sql15");
        List<Object> params=new ArrayList<>();
        List<SelectEntity> list=commonService.selectList(sql,params.toArray());
        return list;
    }

}
