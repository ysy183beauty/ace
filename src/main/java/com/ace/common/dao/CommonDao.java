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
     * 查询集合
     * @param sql sql语句
     * @param params 参数
     * @return
     */
    List selectList(String sql,Object[] params);
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
    List<BaseInfoSys> selectColumns(String sql);

    /**
     * 列表和查询条件显示的字段
     * @param tableName 表名
     * @param listDisplay 列表状态(1或0)
     * @param queryDisplay 查询状态(1或0)
     * @return
     */
    Map<String,List> selectTableDisplay(String tableName, Integer listDisplay,
                                          Integer queryDisplay,String sqlList,String sqlQuery);
}
