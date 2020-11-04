package com.ace.common.dao;

import com.ace.page.PageParam;
import com.ace.page.Pagination;
import com.ace.sysytem.entity.BaseInfoSys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.SQLException;
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
    public List selectList(String sql, Object[] params) {
        return jdbcTemplate.queryForList(sql,params);
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
    public List<BaseInfoSys> selectColumns(String sql) {
        List<BaseInfoSys> columns=new ArrayList<>();
        SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet(sql.replaceAll("[?]","'0'"));
        String[] columnNames = sqlRowSet.getMetaData().getColumnNames();
        for(String s:columnNames){
            BaseInfoSys baseInfoSys=new BaseInfoSys();
            baseInfoSys.setFieldname(s);
            columns.add(baseInfoSys);
        }
        return columns;
    }

    @Override
    public Map<String, List> selectTableDisplay(String tableName,Integer listDisplay,
                                                  Integer queryDisplay,String sqlList,String sqlQuery) {
        Map<String, List> map=new HashMap<>();
        //查询列表显示的字段
        List<Object> paramsList=new ArrayList<>();
        paramsList.add(tableName);
        paramsList.add(listDisplay);
        List list1=this.selectList(sqlList,paramsList.toArray());
        //情况清空数据信息
        paramsList.clear();
        paramsList.add(tableName);
        paramsList.add(queryDisplay);
        List list2=this.selectList(sqlQuery,paramsList.toArray());
        map.put("list",list1);
        map.put("query",list2);
        return map;
    }
}
