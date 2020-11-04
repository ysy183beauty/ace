package com.ace.page;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import java.util.List;

public class Pagination extends JdbcDaoSupport {
    @Autowired
    JdbcTemplate jdbcTemplate;
    //一页显示的记录数
    private int pageSize;
    //记录总数
    private int totalRows;
    //总页数
    private int totalPages;
    //当前页码
    private int currentPage;
    //起始行数
    private int startIndex;
    //结束行数
    private int lastIndex;
    //结果集存放List
    private List resultList;
    //JdbcTemplate jTemplate
    private JdbcTemplate jTemplate;

    /**
     * 分页构造函数
     *
     * @param sql       根据传入的sql语句得到一些基本分页信息
     * @param params    参数列表
     * @param pageParam 分页参数对象
     * @param jTemplate JdbcTemplate实例
     * @dbType 数据库类型：oracle或者mysql
     */
    public Pagination(String sql, Object[] params, PageParam pageParam, JdbcTemplate jTemplate,String dbType) {
        //设置每页显示记录数
        setPageSize(pageParam.getLimit());
        //设置要显示的页数
        setCurrentPage(pageParam.getPage());
        //计算总记录数
        StringBuffer totalSQL = new StringBuffer(" SELECT count(*) FROM ( ");
        totalSQL.append(sql);
        totalSQL.append(" ) totalTable ");
        //给JdbcTemplate赋值
        setJdbcTemplate(jTemplate);
        //总记录数
        setTotalRows(getJdbcTemplate().queryForObject(totalSQL.toString(), params, Integer.class));
        //计算总页数
        setTotalPages();
        //计算起始行数
        setStartIndex();
        //计算结束行数
        setLastIndex();
        //装入结果集
        setResultList(getJdbcTemplate().queryForList(getMySQLPageSQL(new StringBuilder(sql), pageParam,dbType), params));
    }


    /**
     * 构造MySQL数据分页SQL
     *
     * @param queryString
     * @return
     */
    public String getMySQLPageSQL(StringBuilder queryString, PageParam pageParam,String dbType) {
        String resultSql = null;
        if (pageParam != null && pageParam.getSort() != null && pageParam.getDir() != null) {
            queryString.append(" order by ").append(pageParam.getSort()).append(" ").append(pageParam.getDir());
        }
        if (0 != pageSize) {
            if("oracle".equals(dbType.toLowerCase())){
                Integer end=currentPage*pageSize+1;
                Integer start=(currentPage-1)*pageSize;
                resultSql=" select * from(select a.*,rownum rn from ("+queryString+") a where rownum < "+end+") where rn>"+start+"";
            }else if("mysql".equals(dbType.toLowerCase())){
                resultSql = queryString.append(" limit ").append(startIndex).append(",").append(pageSize).toString();
            }
        } else {
            resultSql = queryString.toString();
        }
        return resultSql;
    }


    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        //如果当前页码<1，则默认加载第一页数据
        if (currentPage < 0) {
            this.currentPage = 1;
        } else {
            this.currentPage = currentPage;
        }
    }


    public List getResultList() {
        return resultList;
    }

    public void setResultList(List resultList) {
        this.resultList = resultList;
    }

    public int getTotalPages() {
        return totalPages;
    }

    //计算总页数
    public void setTotalPages() {
        if (pageSize == 0) {
            totalPages = 0;
        } else {
            if (totalRows % pageSize == 0) {
                this.totalPages = totalRows / pageSize;
            } else {
                this.totalPages = (totalRows / pageSize) + 1;
            }
        }
    }

    public int getTotalRows() {
        return totalRows;
    }

    public void setTotalRows(int totalRows) {
        this.totalRows = totalRows;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public void setStartIndex() {
        //如果总页数>当前页,则默认加载最后一页
        if (currentPage > totalPages) {
            currentPage = totalPages;
        }
        this.startIndex = (currentPage - 1) * pageSize;
    }

    public int getLastIndex() {
        return lastIndex;
    }

    public JdbcTemplate getJTemplate() {
        return jTemplate;
    }

    public void setJTemplate(JdbcTemplate template) {
        jTemplate = template;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    //计算结束时候的索引
    public void setLastIndex() {
        if (pageSize != 0) {
            if (totalRows < pageSize) {
                this.lastIndex = totalRows;
            } else if ((totalRows % pageSize == 0) || (totalRows % pageSize != 0 && currentPage < totalPages)) {
                this.lastIndex = currentPage * pageSize;
            } else if (totalRows % pageSize != 0 && currentPage == totalPages) {//最后一页
                this.lastIndex = totalRows;
            }
        }
    }
}
