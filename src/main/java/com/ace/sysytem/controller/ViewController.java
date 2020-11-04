package com.ace.sysytem.controller;

import com.ace.common.service.CommonService;
import com.ace.sysytem.entity.BaseInfoSys;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value ="/sys/view")
public class ViewController {
    @Autowired
    private CommonService commonService;
    //跳转到配置列表信息
    @RequestMapping(value = "/toSettingList")
    public ModelAndView toSettingList(){
        ModelAndView mv=new ModelAndView();
        mv.setViewName("/common/system/settingList");
        return mv;
    }
    //查询配置表信息
    @RequestMapping(value = "/toSelectSetting")
    public ModelAndView toSelectSetting(){
        ModelAndView mv=new ModelAndView();
        mv.setViewName("/common/system/selectSettingList");
        return mv;
    }
    //修改状态信息
    @RequestMapping(value = "/updateStatusMap")
    public ModelAndView updateStatusMap(HttpServletRequest request){
        String content="";
        String id=request.getParameter("id");
        String type=request.getParameter("type");
        String title=request.getParameter("title");
        List<Object> params=new ArrayList<>();
        params.add(id);
        List list= commonService.selectList(commonService.selectSql("sql10"), params.toArray());
        String json=JSON.toJSONString(list);
        List<BaseInfoSys> data = JSONObject.parseArray(json, BaseInfoSys.class);
        if(data.size()>0){
            if("1".equals(type)){
                content=data.get(0).getStatusmap();
            }else{
                content=data.get(0).getUrl();
            }
        }
        ModelAndView mv=new ModelAndView();
        mv.setViewName("/common/system/editStatusMap");
        mv.addObject("id",id);
        mv.addObject("type",type);
        mv.addObject("title",title);
        mv.addObject("content",JSON.toJSONString(content));
        return mv;
    }
}
