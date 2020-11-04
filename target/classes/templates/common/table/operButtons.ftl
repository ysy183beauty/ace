<#macro operButtons operInfos>
<#include "../frame/urlRoot.ftl"/>
<#--引入css -->
<link rel="stylesheet" href="${ctx}/static/assets/commonCss/operButton.css" />
<#if operInfos?length gt 1>
    <#list operInfos?split(",") as item>
        <#if item=='query'>
            <button type="button" class="btn btn-default" onclick="doQuery();">
                <span class="glyphicon glyphicon-search" aria-hidden="true"></span>查询
            </button>
        <#elseif item=="reset">
            <button type="button" class="btn btn-default" onclick="doReset();">
                <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>重置
            </button>
        <#elseif item=='add'>
            <button type="button" class="btn btn-default" onclick="doAdd();">
                <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>添加
            </button>
        <#elseif item=='mulDel'>
            <button type="button" class="btn btn-default" onclick="doMulDel();">
                <span class="glyphicon glyphicon-trash" aria-hidden="true"></span>批量删除
            </button>
        <#elseif item=='transfer'>
            <button type="button" class="btn btn-default" onclick="doTransfer();">
                <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>跳转
            </button>
        <#elseif item=='relation'>
            <button type="button" class="btn btn-default" onclick="doRelation();">
                <span class="glyphicon glyphicon-check" aria-hidden="true"></span>关联
            </button>
        <#elseif item=='list'>
            <button type="button" class="btn btn-default" onclick="doList();">
                <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>列表显示
            </button>
        <#elseif item=='form'>
            <button type="button" class="btn btn-default" onclick="doForm();">
                <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>form显示
            </button>
        <#elseif item=='condition'>
            <button type="button" class="btn btn-default" onclick="doCondition();">
                <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>查询条件
            </button>
        </#if>
    </#list>
</#if>
</#macro>