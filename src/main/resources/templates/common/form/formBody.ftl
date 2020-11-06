<#macro formBody formList buttonOper>
<#include "../frame/urlRoot.ftl"/>
<#include "forButton.ftl"/>
<!-- 引入bootstrap样式 -->
<link href="${ctx}/static/assets/css/bootstrap.min.css" rel="stylesheet">
<!-- jquery -->
<script src="${ctx}/static/assets/js/jquery.min.js"></script>
<script src="${ctx}/static/assets/js/bootstrap.min.js"></script>
<script src="${ctx}/static/assets/layer/layer.js"></script>
<script src="${ctx}/static/assets/js/common/common.js"></script>
<form class="form-horizontal" role="form" style="margin: 5px;width: 98%;">
<#--如果集合不为空 -->
<#if formList?? && (formList?size > 0) >
    <#assign index=0>
    <#--循环遍历 -->
    <#list formList as item>
        <#if index%2==0>
            <div class="form-group">
        </#if>
        <#if item.fieldtype=="VARCHAR2">
            <label class="col-sm-2 control-label">${item.fieldlabel}</label>
            <div class="col-sm-4">
                <input class="form-control" id="${item.fieldname}" name="${item.fieldname}" type="text" placeholder="请输入${item.fieldlabel}"/>
            </div>
          <#elseif item.fieldtype=="NUMBER">
              <label class="col-sm-2 control-label">${item.fieldlabel}</label>
              <div class="col-sm-4">
                  <input class="form-control" id="${item.fieldname}" name="${item.fieldname}" type="text" placeholder="请输入${item.fieldlabel}"/>
              </div>
        </#if>
        <#assign index=index+1>
        <#if index%2==0>
            </div>
        </#if>
    </#list>
    <#--引入按钮信息 -->
    <@formButton buttonOper/>
</#if>
</form>
</#macro>