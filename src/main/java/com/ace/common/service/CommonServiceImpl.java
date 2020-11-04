package com.ace.common.service;

import com.ace.common.dao.CommonDao;
import com.ace.page.PageParam;
import com.ace.page.Pagination;
import com.ace.sqlxml.SqlEntity;
import com.ace.sqlxml.SqlXmlService;
import com.ace.sysytem.entity.BaseInfoSys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CommonServiceImpl implements CommonService {
    @Autowired
    private CommonDao commonDao;
    @Autowired
    private SqlXmlService sqlXmlService;
    @Override
    public Integer totalRecords(String sql, Object[] params) {
        return commonDao.totalRecords(sql,params);
    }

    @Override
    public List selectList(String sql, Object[] params) {
        return commonDao.selectList(sql,params);
    }

    @Override
    public Pagination selectListByPage(String sql, PageParam pageParam, List<Object> params, String dbType) {
        return commonDao.selectListByPage(sql,pageParam,params,dbType);
    }

    @Override
    public int batchUpdate(List list, String sql) {
        return commonDao.batchUpdate(list,sql);
    }

    @Override
    public List<BaseInfoSys> selectColumns(String sql) {
        return commonDao.selectColumns(sql);
    }

    @Override
    public int update(String sql, Object[] params) {
        return commonDao.update(sql,params);
    }

    @Override
    public String selectSql(String sqlId) {
        SqlEntity sqlEntity=sqlXmlService.getSqlEntity(sqlId);//获取sql对象
        if(sqlEntity==null){
            sqlEntity=new SqlEntity();
        }
        return sqlEntity.getValue();
    }

    @Override
    public List<SqlEntity> selectSqlAll() {
        return sqlXmlService.getSqlAll();
    }

    @Override
    public Map<String,List> selectTableDisplay(String tableName, Integer listDisplay,
                                                  Integer queryDisplay,String sqlListId, String sqlQueryId) {
        return commonDao.selectTableDisplay(tableName,listDisplay,queryDisplay,
                sqlXmlService.getSqlEntity(sqlListId).getValue(),sqlXmlService.getSqlEntity(sqlQueryId).getValue());
    }
}
