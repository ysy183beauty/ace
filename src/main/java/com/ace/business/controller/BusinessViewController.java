package com.ace.business.controller;

import com.ace.common.service.CommonService;
import com.ace.sysytem.entity.BaseInfoSys;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

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
}
