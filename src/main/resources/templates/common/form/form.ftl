<#macro form tableId formListMult formListOther buttonOper>
<#include "../frame/urlRoot.ftl"/>
<#include "formBody.ftl"/>
<!-- 引入bootstrap样式 -->
<link href="${ctx}/static/assets/css/bootstrap.min.css" rel="stylesheet">
<!-- jquery -->
<script src="${ctx}/static/assets/js/jquery.min.js"></script>
<script src="${ctx}/static/assets/js/bootstrap.min.js"></script>
<script src="${ctx}/static/assets/layer/layer.js"></script>
<script src="${ctx}/static/assets/js/common/common.js"></script>
<@formBody tableId formListMult formListOther buttonOper/>
<script type="text/javascript">
    function doCancel() {
        dealObj.doCloseLayer();
    }
    //校验数据的正确性
    function doCheck() {
        <#--处理开始时间和结束时间 -->
        <#assign startTimeLabel=''>
        <#assign endTimeLabel=''>
        <#assign startDate="">
        <#assign endDate="">
        <#--判断集合是否为空 -->
        <#if formListOther?? && (formListOther?size > 0) >
            <#--循环遍历 -->
            <#list formListOther as item>
                <#--字段名称 -->
                <#assign fieldname=item.fieldname?lower_case?replace("_","")>
                <#if item.queryformatter??>
                    <#if item.isnull==0><#--必须填写校验 -->
                        if($("#${fieldname}").val().length<=0){
                            layer.alert($("#${fieldname}_select").val(), {skin: 'layui-layer-molv',icon: 0});
                            return false;
                        }
                    </#if>
                <#elseif item.url??>
                    <#if item.isnull==0><#--必须填写校验 -->
                        if($("#${fieldname}").val().length<=0){
                            layer.alert($("#${fieldname}_select").val(), {skin: 'layui-layer-molv',icon: 0});
                            return false;
                        }
                    </#if>
                <#elseif item.fieldtype=="NUMBER">
                    <#if item.isnull==0><#--必须填写校验 -->
                        <#--数字正则表达式的校验 -->
                        if($("#${fieldname}").val().length<=0){
                            layer.alert("请输入${item.fieldlabel}", {skin: 'layui-layer-molv',icon: 0});
                            return false;
                        }
                        var reg=/^\d+(\.\d+)?$/;
                        if(!reg.test($("#${fieldname}").val())){
                            layer.alert("请输入正确的${item.fieldlabel}", {skin: 'layui-layer-molv',icon: 0});
                            return false;
                        }
                    </#if>
                 <#elseif item.fieldtype=='DATE'>
                       <#if item.isnull==0><#--必须填写校验 -->
                            if($("#${fieldname}").val().length<=0){
                                layer.alert("请输入${item.fieldlabel}", {skin: 'layui-layer-molv',icon: 0});
                                return false;
                            }
                            <#if item.fieldname?lower_case?contains("start")>
                                <#assign startDate=item.fieldname>
                                <#assign startTimeLabel=item.fieldlabel>
                            <#elseif item.fieldname?lower_case?contains("end")>
                                <#assign endDate=item.fieldname>
                                <#assign endTimeLabel=item.fieldlabel>
                            </#if>
                       </#if>
                <#else>
                    <#if item.isnull==0><#--必须填写校验 -->
                        if($("#${fieldname}").val().length<=0){
                            layer.alert("请输入${item.fieldlabel}", {skin: 'layui-layer-molv',icon: 0});
                            return false;
                        }
                    </#if>
                </#if>
            </#list>
       </#if>
        <#if formListMult?? && (formListMult?size > 0) >
            <#--循环遍历 -->
            <#list formListMult as item>
                <#--字段名称 -->
                <#assign fieldname=item.fieldname?lower_case?replace("_","")>
                 <#if item.isnull==0><#--必须填写校验 -->
                        if($("#${fieldname}").val().length<=0){
                            layer.alert("请输入${item.fieldlabel}", {skin: 'layui-layer-molv',icon: 0});
                            return false;
                        }
                 </#if>
            </#list>
      </#if>
        //判断开始时间是否大于结束时间
        if(($("#${startDate}").val())>($("#${endDate}").val())){
            layer.alert("${startTimeLabel}不能大于${endTimeLabel}", {skin: 'layui-layer-molv',icon: 0});
            return false;
        }
        return true;
    }
</script>
</#macro>