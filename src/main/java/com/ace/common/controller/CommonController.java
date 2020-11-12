package com.ace.common.controller;
import com.ace.business.entity.OperButton;
import com.ace.common.service.CommonService;
import com.ace.page.PageParam;
import com.ace.page.Pagination;
import com.ace.sysytem.entity.BaseInfoSys;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Controller
public class CommonController {
    @Autowired
    private CommonService commonService;

    /**
     * 查询带上分页和查询条件信息
     * @param dataObj 字段名称：字段值的JsonObject
     * @param fieldObj 字段名称：类型的JsonObject
     * @param sql sql语句
     * @param orderSql 排序字段
     * @return
     */
    public Map<String,Object> query(JSONObject dataObj,JSONObject fieldObj,String sql,String orderSql){
        String format=null;
        String condition;
        List<Object> params=new ArrayList<>();//存放参数信息
        StringBuilder sb=new StringBuilder();
        String dataValue;
        Map<String,Object> map=new HashMap<>();
        String dataType;
        try {
            for(String key:dataObj.keySet()){
                //分页参数不需要处理
                if(!"offset".equals(key)&&!"limit".equals(key)){
                    dataValue=dataObj.get(key)+"";
                    if(!"".equals(dataValue)&&dataValue!=null){
                        dataType=fieldObj.get(key).toString();//获取数据类型
                        if("VARCHAR2".equals(dataType)){
                             if(sb.length()>0){
                                 condition=" and ";
                             }else{
                                 condition=" where ";
                             }
                             sb.append(" "+condition+""+key+" like ?");
                             params.add("%"+dataValue+"%");//支持模糊查询
                        }else if("NUMBER".equals(dataType)){
                            if(sb.length()>0){
                                condition=" and ";
                            }else{
                                condition=" where ";
                            }
                            sb.append(" "+condition+""+key+" =?");
                            params.add(dataValue);//直接等于
                        }else if("DATE".equals(dataType)){
                            if(dataValue.length()==19){
                                format="yyyy-mm-dd hh24:mi:ss";
                            }else if(dataValue.length()==10){
                                format="yyyy-mm-dd";
                            }
                            if(sb.length()>0){
                                condition=" and ";
                            }else{
                                condition=" where ";
                            }
                            if(key.contains("start")){
                                sb.append(" "+condition+" "+key+" >=to_date(?,"+format+")");
                            }else if(key.contains("end")){
                                sb.append(" "+condition+" "+key+" <=to_date(?,"+format+")");
                            }else{
                                sb.append(" "+condition+" "+key+" =to_date(?,"+format+")");
                            }
                            params.add(dataValue);
                        }else if("TIMESTAMP".equals(dataType)){
                            if(sb.length()>0){
                                condition=" and ";
                            }else{
                                condition=" where ";
                            }
                            if(key.contains("start")){
                                sb.append(" "+condition+" "+key+" >=TO_TIMESTAMP(?,'yyyy-mm-dd hh24:mi:ss.ff')");
                            }else if(key.contains("end")){
                                sb.append(" "+condition+" "+key+" <=TO_TIMESTAMP(?,'yyyy-mm-dd hh24:mi:ss.ff')");
                            }else{
                                sb.append(" "+condition+" "+key+" =TO_TIMESTAMP(?,'yyyy-mm-dd hh24:mi:ss.ff')");
                            }
                            params.add(dataValue);
                        }else{
                            if(sb.length()>0){
                                condition=" and ";
                            }else{
                                condition=" where ";
                            }
                            sb.append(" "+condition+""+key+" =?");
                            params.add(dataValue);//直接等于
                        }
                    }
                }
            }
            sql=sql+sb+orderSql;//拼接成查询数据的sql语句
            Integer offset=Integer.parseInt(dataObj.get("offset").toString());
            Integer limit=Integer.parseInt(dataObj.get("limit").toString());
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
            Pagination p = commonService.selectListByPage(sql, pageParam, params,"oracle");
            List<Map<String,Object>> resultList = p.getResultList();
            List<JSONObject> rows=new ArrayList<>();
            for(int i=0;i<resultList.size();i++){
                JSONObject obj=new JSONObject();
                Map<String,Object> m=resultList.get(i);
                for(String key:m.keySet()){
                    //替换所有的下划线和转换为小写
                    obj.put(key.toLowerCase(),m.get(key));
                }
                rows.add(obj);
            }
            map.put("rows",rows);
            map.put("total",p.getTotalRows());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return map;
    }



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
                    obj.put(key.toLowerCase(),m.get(key));
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

    /**
     * 转换list<Map<String,Object>>
     * @param list
     * @return
     */
    public List changeList(List<Map<String,Object>> list){
        List<JSONObject> rows=new ArrayList<>();
        for(int i=0;i<list.size();i++){
            JSONObject obj=new JSONObject();
            Map<String,Object> m=list.get(i);
            for(String key:m.keySet()){
                //替换所有的下划线和转换为小写
                obj.put(key.replaceAll("_","").toLowerCase(),m.get(key));
            }
            rows.add(obj);
        }
        return rows;
    }

    /**
     * 通过表名查询列表和查询条件信息
      * @param tableName 表名
     * @return
     */
   public Map<String,List<BaseInfoSys>> selectListAndQueryInfo(String tableName){
       Map<String,List<BaseInfoSys>> data=new HashMap<>();
       Map<String, List> map=commonService.selectListAndQueryInfo(tableName);
       List<BaseInfoSys> list=map.get("list");
       List<BaseInfoSys> queryList=map.get("queryList");
       data.put("list",list);
       data.put("queryList",queryList);
       return data;
   }

    /**
     * 通过表名查询表单信息
     * @param tableName 表名
     * @return
     */
    public Map<String,List<BaseInfoSys>> selectFormInfo(String tableName){
        Map<String,List<BaseInfoSys>> data=new HashMap<>();
        List<Object> params=new ArrayList<>();
        params.add(tableName);
        params.add("1");
        String sql="SELECT * FROM T_BASE_INFO_SYS f where f.TABLENAME=? and f.FORMDISPLAY=?";
        List<Map<String,Object>> resultList=commonService.selectAllRecords(sql,params.toArray());
        List<BaseInfoSys> formList=JSONObject.parseArray(JSON.toJSONString(resultList),BaseInfoSys.class);
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
        data.put("formListMult",formListMult);
        data.put("formListOther",formListOther);
        return data;
    }

    public ModelAndView selectView(String tableName){
        ModelAndView mv=new ModelAndView();
        Map<String,List<BaseInfoSys>> data=this.selectListAndQueryInfo(tableName);
        mv.addObject("queryFields", JSON.toJSONString(data.get("queryList")));
        mv.addObject("cols",JSON.toJSONString(data.get("list")));
        return mv;
    }

    public ModelAndView ForView(List<OperButton> buttons,String tableName){
        ModelAndView mv=new ModelAndView();
        //默认按钮为保存和取消按钮
        List<OperButton> operButtons=new ArrayList<>();
        operButtons.add(new OperButton("保存","btn-success","doSave"));
        operButtons.add(new OperButton("取消","btn-danger","doCancel"));
        if(buttons!=null&&buttons.size()>0){
            for(OperButton b:buttons){
                operButtons.add(b);
            }
        }
        Map<String,List<BaseInfoSys>> data=this.selectFormInfo(tableName);
        mv.addObject("buttonOper",JSON.toJSONString(operButtons));
        mv.addObject("formListMult",JSON.toJSONString(data.get("formListMult")));
        mv.addObject("formListOther",JSON.toJSONString(data.get("formListOther")));
        return mv;
    }

    /**
     * form表单保存数据信息
     * @param data json字符串
     * @param sql sql语句
     */
    public void saveInfo(String data,String sql) throws Exception {
        JSONArray arr=JSONArray.parseArray(data);
        Map<String,Object> fieldInfo=(Map<String,Object>)arr.get(0);
        Map<String,Object> fieldType=(Map<String,Object>)arr.get(1);
        commonService.update(sql,fieldInfo,fieldType);
    }

}
