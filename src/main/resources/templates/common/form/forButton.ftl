<#--
 buttonOper:为对象数组，格式为:[{"label":"保存","className":"btn-success","clickName":"doSave()"}]
 -->
<#macro formButton buttonOper>
<#include "../frame/urlRoot.ftl"/>
<!-- 引入bootstrap样式 -->
<link href="${ctx}/static/assets/css/bootstrap.min.css" rel="stylesheet">
<!-- jquery -->
<script src="${ctx}/static/assets/js/jquery.min.js"></script>
<script src="${ctx}/static/assets/js/bootstrap.min.js"></script>
<script src="${ctx}/static/assets/layer/layer.js"></script>
<script src="${ctx}/static/assets/js/common/common.js"></script>
<div class="form-group" style="margin-left: -3px;margin-top: 40px;">
    <label class="col-sm-2 control-label"></label>
    <div class="col-sm-8">
        <#if buttonOper?? && (buttonOper?size > 0) >
            <#--循环遍历 -->
            <#list buttonOper as item>
                <input type="button" value="${item.label}" class="btn ${item.className}"
                       style="width: 120px;" onclick="${item.clickName+'()'};">
            </#list>
        </#if>
    </div>
</div>
</#macro>