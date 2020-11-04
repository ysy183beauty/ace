<#macro tableMain tableId url cols isSingleSelect operateMap queryFields operInfos>
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
<#include "tableContent.ftl"/>
<#include "tableQuery.ftl"/>
<#include "operButtons.ftl"/>
<div class="container">
    <div id="toolbar" class="btn-group">
        <form class="form-inline" id="queryForm">
            <#--引入查询条件 -->
            <@tableQuery queryFields=queryFields/>
            <#--引入按钮信息 -->
            <@operButtons operInfos=operInfos/>
        </form>
    </div>
    <!--引入查询条件 -->
    <table id="${tableId}"></table>
</div>
<!--引入列表 -->
<@tableContent tableId=tableId url=url
cols=cols isSingleSelect=isSingleSelect operateMap=operateMap/>
</#macro>
