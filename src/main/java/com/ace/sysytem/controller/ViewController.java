package com.ace.sysytem.controller;

import com.ace.common.service.CommonService;
import com.ace.sysytem.entity.BaseInfoSys;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value ="/sys/view")
public class ViewController {
    @Autowired
    private CommonService commonService;
    //跳转到配置列表信息
    @RequestMapping(value = "/toBaseSettingPage")
    public ModelAndView toSettingList(){
        ModelAndView mv=new ModelAndView();
        mv.setViewName("/common/system/baseSettingList");
        return mv;
    }
    //查询配置表信息
    @RequestMapping(value = "/toSettingListPage")
    public ModelAndView toSettingListPage(){
        ModelAndView mv=new ModelAndView();
        mv.setViewName("/common/system/settingList");
        return mv;
    }
    //修改状态信息
    @RequestMapping(value = "/updateBaseSys")
    public ModelAndView updateBaseSys(HttpServletRequest request){
        //通过主键查询数据
        String sql="select * from T_BASE_INFO_SYS t WHERE t.ID=?";
        String content="";
        String id=request.getParameter("id");
        String type=request.getParameter("type");
        String title=request.getParameter("title");
        List<Object> params=new ArrayList<>();
        params.add(id);
        BaseInfoSys baseInfoSys=(BaseInfoSys)commonService.selectOne(sql, params.toArray(), BaseInfoSys.class);
        if("1".equals(type)){
            content=baseInfoSys.getListformatter();
        }else{
            content=baseInfoSys.getUrl();
        }
        ModelAndView mv=new ModelAndView();
        mv.setViewName("/common/system/editBaseSys");
        mv.addObject("id",id);
        mv.addObject("type",type);
        mv.addObject("title",title);
        mv.addObject("content",JSON.toJSONString(content));
        return mv;
    }
}
