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
    @Override
    public Integer totalRecords(String sql, Object[] params) {
        return commonDao.totalRecords(sql,params);
    }

    @Override
    public List selectAllRecords(String sql, Object[] params) {
        return commonDao.selectAllRecords(sql,params);
    }

    @Override
    public Object selectOne(String sql, Object[] params, Class t) {
        return commonDao.selectOne(sql,params,t);
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
    public List<BaseInfoSys> selectBaseInfoSysBySql(String sql) {
        return commonDao.selectBaseInfoSysBySql(sql);
    }

    @Override
    public int update(String sql, Object[] params) {
        return commonDao.update(sql,params);
    }

    @Override
    public Map<String, List> selectListAndQueryInfo(String tableName) {
        return commonDao.selectListAndQueryInfo(tableName);
    }

}
