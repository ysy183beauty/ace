package com.ace.common.dao;

import com.ace.page.PageParam;
import com.ace.page.Pagination;
import com.ace.sysytem.entity.BaseInfoSys;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.stereotype.Component;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
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
