package com.ace.common.dao;

import com.ace.page.PageParam;
import com.ace.page.Pagination;
import com.ace.sysytem.entity.BaseInfoSys;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.jdbc.core.support.SqlLobValue;
import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.stereotype.Component;
import javax.sql.DataSource;
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CommonDaoImpl implements CommonDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private  NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Override
    public Integer totalRecords(String sql, Object[] params) {
        return jdbcTemplate.queryForObject(sql, params, Integer.class);
    }

    @Override
    public List selectAllRecords(String sql, Object[] params) {
        return jdbcTemplate.queryForList(sql,params);
    }

    @Override
    public Object selectOne(String sql, Object[] params,Class t) {
        return jdbcTemplate.queryForObject(sql,params,t);
    }

    @Override
    public Pagination selectListByPage(String sql, PageParam pageParam, List<Object> params,String dbType) {
        return new Pagination(sql, params.toArray(), pageParam,jdbcTemplate,dbType);
    }

    @Override
    public int batchUpdate(List list, String sql) {
        SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch(list.toArray());
        int[] updatedCountArray =namedParameterJdbcTemplate.batchUpdate(sql,batch);
        int sumInsertedCount = 0;
        for(int a: updatedCountArray){
            sumInsertedCount+=a;
        }
        return sumInsertedCount;
    }

    @Override
    public int update(String sql, Object[] params) {
        return jdbcTemplate.update(sql,params);
    }

    @Override
    public List<BaseInfoSys> selectBaseInfoSysBySql(String sql) {
        List<BaseInfoSys> columns=new ArrayList<>();
        try {
            DataSource dataSource = jdbcTemplate.getDataSource();
            Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
            ResultSetMetaData metadata = (ResultSetMetaData) result.getMetaData();
            for(int i = 1; i <= metadata.getColumnCount();i++){
                BaseInfoSys baseInfoSys=new BaseInfoSys();
                baseInfoSys.setFieldname(metadata.getColumnName(i));//字段名称
                baseInfoSys.setFieldtype(metadata.getColumnTypeName(i));//字段类型(数字、字符串、时间等)
                baseInfoSys.setIsnull(0);//默认不能为空
                baseInfoSys.setFieldlength(metadata.getPrecision(i)==0?metadata.getColumnDisplaySize(i):metadata.getPrecision(i));//字段允许的长度
                baseInfoSys.setListdisplay(1);//是否显示在列表上
                baseInfoSys.setFormdisplay(1);//是否显示在form表单上
                baseInfoSys.setQuerydisplay(2);//是否作为查询条件
                baseInfoSys.setOrdernum(i);//排序码
                columns.add(baseInfoSys);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return columns;
    }

    @Override
    public Map<String, List> selectListAndQueryInfo(String tableName) {
        StringBuilder sb=new StringBuilder();
        Map<String, List> map=new HashMap<>();
        try {
            //查询列表显示的字段
            List<Object> paramsList=new ArrayList<>();
            paramsList.add(tableName);
            paramsList.add(1);
            sb.append("SELECT  * FROM T_BASE_INFO_SYS f where f.TABLENAME=? and f.LISTDISPLAY=?");
            List list = this.selectAllRecords(sb.toString(), paramsList.toArray());
            //清空数据信息
            sb.setLength(0);
            sb.append("SELECT  * FROM T_BASE_INFO_SYS f where f.TABLENAME=? and f.QUERYDISPLAY=?");
            List queryList=this.selectAllRecords(sb.toString(),paramsList.toArray());
            map.put("list",changeList(list));
            map.put("queryList",changeList(queryList));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    @Override
    public int update(String sql,Map<String, Object> data, Map<String, Object> types){
        DateFormat sdf=null;
        String dataType=null;
        NamedParameterJdbcTemplate jdbcTemplateObject = new NamedParameterJdbcTemplate(jdbcTemplate.getDataSource());
        MapSqlParameterSource in = new MapSqlParameterSource();
        try {
            for(String key:data.keySet()){
                dataType=types.get(key).toString();
                if("DATE".equals(dataType)){
                    if(data.get(key)!=null){
                        //yyyy-MM-dd HH:mm:ss格式的时间
                        if(data.get(key).toString().length()==19){
                            sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        }else if(data.get(key).toString().length()==10){
                            sdf = new SimpleDateFormat("yyyy-MM-dd");
                        }
                        in.addValue(key,sdf.parse(data.get(key).toString()));
                    }
                }else if("CLOB".equals(dataType)){
                    in.addValue(key,new SqlLobValue(data.get(key)+"", new DefaultLobHandler()), Types.CLOB);
                }else if("BLOB".equals(dataType)){
                    in.addValue(key,new SqlLobValue(data.get(key)+"", new DefaultLobHandler()), Types.BLOB);
                }else if("TIMESTAMP".equals(dataType)){
                    Timestamp time = Timestamp.valueOf(data.get(key)+"");
                    in.addValue(key,time);
                }else{
                    in.addValue(key,data.get(key));
                }
            }
            jdbcTemplateObject.update(sql, in);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

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
}
