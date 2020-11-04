<#--
tableId:表格的id
url:服务器地址
cols:显示的字段map集合,[{"fieldName":"中文名称"}]
isSingleSelect:boolean类型，是单选，还是可以复选框
operateMap:是否存在操作按钮组，[{"edit":true,"remove":true}]
queryFields:查询的字段集合，[{"fieldname":"字段名称","fieldtype","字段长度"}]
operInfos:操作按钮组，例如：add,query,reset
-->
<#macro table tableId url cols isSingleSelect operateMap queryFields operInfos>
<#include "../frame/urlRoot.ftl"/>
    <!-- 引入bootstrap样式 -->
<link href="${ctx}/static/assets/css/bootstrap.min.css" rel="stylesheet">
    <!-- 引入bootstrap-table样式 -->
<link href="${ctx}/static/assets/css/bootstrap-table.min.css" rel="stylesheet">
    <!-- jquery -->
<script src="${ctx}/static/assets/js/jquery.min.js"></script>
<script src="${ctx}/static/assets/js/bootstrap.min.js"></script>
    <!-- bootstrap-table.min.js -->
<script src="${ctx}/static/assets/js/bootstrap-table.min.js"></script>
    <!-- 引入中文语言包 -->
<script src="${ctx}/static/assets/js/bootstrap-table-zh-CN.min.js"></script>
<#--引入表格 -->
<#include "tableMain.ftl"/>
<@tableMain tableId=tableId url=url cols=cols isSingleSelect=isSingleSelect
operateMap=operateMap queryFields=queryFields operInfos=operInfos/>
</#macro>