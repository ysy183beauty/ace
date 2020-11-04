package com.ace.business.controller;

import com.ace.business.entity.TInfo;
import com.ace.common.service.CommonService;
import com.ace.page.PageParam;
import com.ace.page.Pagination;
import com.ace.sysytem.entity.BaseInfoSys;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

@Controller
@RequestMapping(value ="/business/query")
public class BusinessQueryController {
    @Autowired
    private CommonService commonService;
    @ResponseBody
    @RequestMapping(value ="/selectAllInfos",method = RequestMethod.POST)
    public Map<String, Object> selectAllInfos(Integer offset, Integer limit, BaseInfoSys baseInfoSys){
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
        StringBuilder sb=new StringBuilder("SELECT * FROM T_INFO");
        List<Object> params=new ArrayList<>();
        Pagination p = commonService.selectListByPage(sb.toString(), pageParam, params, "oracle");
        List resultList = p.getResultList();
        String json=JSON.toJSONString(resultList);
        List<TInfo> list=JSON.parseArray(json,TInfo.class);
        map.put("rows",list);
        map.put("total",p.getTotalRows());
        return map;
    }

}
