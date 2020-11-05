<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    <meta charset="utf-8" />
    <meta name="description" content="overview &amp; stats" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />
    <#include "../common/frame/urlRoot.ftl"/>
    <#include "../common/table/table.ftl"/>
    <script src="${ctx}/static/assets/js/jquery-1.11.3.min.js"></script>
    <script src="${ctx}/static/assets/layer/layer.js"></script>
    <script src="${ctx}/static/assets/js/common/common.js"></script>
</head>
<body>
<#assign tableId="table-orglist-id"/>
<#assign url="/business/query/selectOrgs"/>
<#assign isSingleSelect=true/>
<#assign operateMap={"edit":true,"remove":true}/>
<#assign queryFields=queryFields?eval/>
<#assign operInfos="query,reset"/>
<#assign cols=cols?eval/>
<@table tableId url cols isSingleSelect operateMap queryFields operInfos/>
</body>
</html>