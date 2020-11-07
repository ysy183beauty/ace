package com.ace.business.controller;

import com.ace.business.entity.OperButton;
import com.ace.common.service.CommonService;
import com.ace.sysytem.entity.BaseInfoSys;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value ="/business/view")
public class BusinessViewController {
    @Autowired
    private CommonService commonService;
    @RequestMapping(value ="/selectBusinessInfo")
    public ModelAndView selectBusinessInfo(){
        ModelAndView mv=new ModelAndView();
        Map<String, List> map=commonService.selectTableDisplay("t_info",1,1,
                "sql6","sql7");
        List list=map.get("list");
        List query=map.get("query");
        //转换为json字符串
        String listJson= JSON.toJSONString(list);
        String queryJson=JSON.toJSONString(query);
        //转换为相应的集合
        List<BaseInfoSys> cols= JSONObject.parseArray(listJson,BaseInfoSys.class);
        List<BaseInfoSys> queryFields=JSONObject.parseArray(queryJson,BaseInfoSys.class);
        mv.setViewName("/view/infoList");
        mv.addObject("queryFields",JSON.toJSONString(queryFields));
        mv.addObject("cols",JSON.toJSONString(cols));
        return  mv;
    }

    //查询组织信息
    @RequestMapping(value ="/toOrgList")
    public ModelAndView toOrgList(){
        ModelAndView mv=new ModelAndView();
        Map<String, List> map=commonService.selectTableDisplay("t_org",1,1,
                "sql6","sql7");
        List list=map.get("list");
        List query=map.get("query");
        //转换为json字符串
        String listJson= JSON.toJSONString(list);
        String queryJson=JSON.toJSONString(query);
        //转换为相应的集合
        List<BaseInfoSys> cols= JSONObject.parseArray(listJson,BaseInfoSys.class);
        List<BaseInfoSys> queryFields=JSONObject.parseArray(queryJson,BaseInfoSys.class);
        mv.setViewName("/view/orgList");
        mv.addObject("queryFields",JSON.toJSONString(queryFields));
        mv.addObject("cols",JSON.toJSONString(cols));
        return  mv;
    }
    @RequestMapping(value ="/selectStudents")
    public ModelAndView selectStudents(){
        ModelAndView mv=new ModelAndView();
        Map<String, List> map=commonService.selectTableDisplay("t_student",1,1,
                "sql6","sql7");
        List list=map.get("list");
        List query=map.get("query");
        //转换为json字符串
        String listJson= JSON.toJSONString(list);
        String queryJson=JSON.toJSONString(query);
        //转换为相应的集合
        List<BaseInfoSys> cols= JSONObject.parseArray(listJson,BaseInfoSys.class);
        List<BaseInfoSys> queryFields=JSONObject.parseArray(queryJson,BaseInfoSys.class);
        mv.setViewName("/view/stuList");
        mv.addObject("queryFields",JSON.toJSONString(queryFields));
        mv.addObject("cols",JSON.toJSONString(cols));
        return  mv;
    }
    //新增
    @RequestMapping(value ="/toAddPage")
    public ModelAndView toAddPage(){
        //按钮信息
        List<OperButton> operButtons=new ArrayList<>();
        operButtons.add(new OperButton("保存","btn-success","doSave"));
        operButtons.add(new OperButton("取消","btn-danger","doCancel"));
        List<Object> params=new ArrayList<>();
        params.add("t_info");
        params.add("1");
        List list = commonService.selectList(commonService.selectSql("sql12"), params.toArray());
        String queryJson=JSON.toJSONString(list);
        List<BaseInfoSys> formList= JSONObject.parseArray(queryJson,BaseInfoSys.class);
        ModelAndView mv=new ModelAndView();
        mv.setViewName("/view/infoEdit");
        //多文本域与其它的分开
        List<BaseInfoSys> formListMult=new ArrayList<>();
        List<BaseInfoSys> formListOther=new ArrayList<>();
        for(BaseInfoSys b:formList){
            if("CLOB".equals(b.getFieldtype())||"BLOB".equals(b.getFieldtype())){
                formListMult.add(b);
            }else{
                formListOther.add(b);
            }
        }
        mv.addObject("buttonOper",JSON.toJSONString(operButtons));
        mv.addObject("formListMult",JSON.toJSONString(formListMult));
        mv.addObject("formListOther",JSON.toJSONString(formListOther));
        return mv;
    }

    //新增
    @RequestMapping(value ="/toAddStuPage")
    public ModelAndView toAddStuPage(){
        //按钮信息
        List<OperButton> operButtons=new ArrayList<>();
        operButtons.add(new OperButton("保存","btn-success","doSave"));
        operButtons.add(new OperButton("取消","btn-danger","doCancel"));
        List<Object> params=new ArrayList<>();
        params.add("t_student");
        params.add("1");
        List list = commonService.selectList(commonService.selectSql("sql12"), params.toArray());
        String queryJson=JSON.toJSONString(list);
        List<BaseInfoSys> formList= JSONObject.parseArray(queryJson,BaseInfoSys.class);
        ModelAndView mv=new ModelAndView();
        mv.setViewName("/view/stuEdit");
        //多文本域与其它的分开
        List<BaseInfoSys> formListMult=new ArrayList<>();
        List<BaseInfoSys> formListOther=new ArrayList<>();
        for(BaseInfoSys b:formList){
            if("CLOB".equals(b.getFieldtype())||"BLOB".equals(b.getFieldtype())){
                formListMult.add(b);
            }else{
                formListOther.add(b);
            }
        }
        mv.addObject("buttonOper",JSON.toJSONString(operButtons));
        mv.addObject("formListMult",JSON.toJSONString(formListMult));
        mv.addObject("formListOther",JSON.toJSONString(formListOther));
        return mv;
    }
}
