package com.ace.common.dao;

import com.ace.page.PageParam;
import com.ace.page.Pagination;
import com.ace.sysytem.entity.BaseInfoSys;

import java.util.List;
import java.util.Map;

public interface CommonDao {
    /**
     * 查询总数
     * @param sql sql语句
     * @param params 参数
     * @return
     */
    Integer totalRecords(String sql, Object[] params);

    /**
     * 查询所有数据信息
     * @param sql sql语句
     * @param params 参数
     * @return
     */
    List selectAllRecords(String sql, Object[] params);

    /**
     * 查询某个对象
     * @param sql sql语句
     * @param params 参数
     * @param t 类
     * @return
     */
    Object selectOne(String sql, Object[] params,Class t);
    /**
     * 分页查询
     * @param sql sql语句
     * @param pageParam 分页参数
     * @param params 参数
     * @param dbType 数据库类型，现在支持:oracle和mysql
     * @return
     */
    Pagination selectListByPage(String sql, PageParam pageParam, List<Object> params,String dbType);

    /**
     * 批量插入数据信息
     * @param list 数据集合
     * @param sql sql语句
     * @return
     */
   int batchUpdate(List list, String sql);

    /**
     * 修改删除数据信息
     * @param sql sql语句
     * @param params 参数集合
     * @return
     */
    int update(String sql,Object[] params);
    /**
     * 通过sql语句获取列名信息
     */
    List<BaseInfoSys> selectBaseInfoSysBySql(String sql);

    /**
     * 查询列表和查询条件的信息
     * @param tableName 通过表名
     * @return
     */
    Map<String,List> selectListAndQueryInfo(String tableName);

    /**
     * 修改数据含有时间和clob类型
     * @param sql sql语句
     * @param data 数据格
     * @param types 字段类型,fieldname:date
     * @return
     */
    int update(String sql,Map<String,Object> data,Map<String,Object> types) throws Exception;
}
