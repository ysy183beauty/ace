package com.ace.business.controller;

import com.ace.business.entity.OperButton;
import com.ace.common.controller.CommonController;
import com.ace.sysytem.entity.BaseInfoSys;
import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value ="/business/view")
public class BusinessViewController extends CommonController {
    @RequestMapping(value ="/selectBusinessInfo")
    public ModelAndView selectBusinessInfo(){
        ModelAndView mv=super.selectView("t_info");
        mv.setViewName("/view/orgList");
        return  mv;
    }

    //查询组织信息
    @RequestMapping(value ="/toOrgList")
    public ModelAndView toOrgList(){
        ModelAndView mv=super.selectView("t_org");
        mv.setViewName("/view/orgList");
        return  mv;
    }
    @RequestMapping(value ="/selectStudents")
    public ModelAndView selectStudents(){
        ModelAndView mv=super.selectView("t_student");
        mv.setViewName("/view/stuList");
        return  mv;
    }
    //新增
    @RequestMapping(value ="/toAddPage")
    public ModelAndView toAddPage(){
        ModelAndView mv=super.ForView(null,"t_info");
        mv.setViewName("/view/infoEdit");

        return mv;
    }

    //新增
    @RequestMapping(value ="/toAddStuPage")
    public ModelAndView toAddStuPage(){
        ModelAndView mv=super.ForView(null,"t_student");
        mv.setViewName("/view/stuEdit");
        return mv;
    }
}
