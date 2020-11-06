package com.ace.common.controller;
import com.ace.common.service.CommonService;
import com.ace.page.PageParam;
import com.ace.page.Pagination;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Controller
public class CommonController {
    @Autowired
    private CommonService commonService;
    /**
     * 分页数据新宁县
     * @param offset 当前页数
     * @param limit 每页显示的条数
     * @param sql sql语句
     * @param params 参数
     * @param dbType 数据库类型:mysql oracle
     * @return
     */
    public Map<String,Object> queryCommonInfo(Integer offset, Integer limit, String sql, List<Object> params,String dbType){
        Map<String,Object> map=new HashMap<>();
        try {
            Integer pageIndex=0;
            if(offset==0){
                pageIndex=1;
            }else{
                pageIndex=(offset/limit)+1;
            }
            //分页参数设置
            PageParam pageParam=new PageParam();
            pageParam.setPage(pageIndex);
            pageParam.setLimit(limit);
            //查询分页信息
            Pagination p = commonService.selectListByPage(sql, pageParam, params,dbType);
            List<Map<String,Object>> resultList = p.getResultList();
            List<JSONObject> rows=new ArrayList<>();
            for(int i=0;i<resultList.size();i++){
                JSONObject obj=new JSONObject();
                Map<String,Object> m=resultList.get(i);
                for(String key:m.keySet()){
                    //替换所有的下划线和转换为小写
                    obj.put(key.replaceAll("_","").toLowerCase(),m.get(key));
                }
                rows.add(obj);
            }
            map.put("rows",rows);
            map.put("total",p.getTotalRows());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }
}
