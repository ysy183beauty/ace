<#macro formBody formListMult formListOther buttonOper>
<#include "../frame/urlRoot.ftl"/>
<#include "forButton.ftl"/>
<!-- 引入bootstrap样式 -->
<link href="${ctx}/static/assets/css/bootstrap.min.css" rel="stylesheet">
<!-- jquery -->
<script src="${ctx}/static/assets/js/jquery.min.js"></script>
<script src="${ctx}/static/assets/js/bootstrap.min.js"></script>
<script src="${ctx}/static/assets/layer/layer.js"></script>
<script src="${ctx}/static/assets/My97DatePicker/WdatePicker.js"></script>
<script src="${ctx}/static/assets/js/common/common.js"></script>
<form class="form-horizontal" role="form" style="margin: 5px;width: 95%;">
<#--如果集合不为空 -->
<#if formListOther?? && (formListOther?size > 0) >
    <#--获取集合的长度 -->
    <#assign size=formListOther?size>
    <#assign k=1>
    <#assign smIndex=4>
    <#assign color=''>
    <#--循环遍历 -->
    <#list formListOther as item>
        <#-- 首次要创建一个 -->
        <#if k==1>
           <div class="form-group">
       </#if>
       <#if (k==size)&&(k%2!=0)>
          <#assign smIndex=10>
       </#if>
        <#--判断是否为必填 -->
        <#assign required=false>
        <#if item.isnull==0>
              <#assign color='red'>
              <#assign required=true>
            <#else>
              <#assign color='black'>
        </#if>
       <#if item.queryformatter??>
               <label class="col-sm-2 control-label"><span style="color: ${color}">*</span>${item.fieldlabel}</label>
               <div class="col-sm-${smIndex}">
               <select id="${item.fieldname}" name="${item.fieldname}" class="form-control" required='${required?string ("true","false")}'>
                   <option value="">请选择</option>
                   <#assign labText=''/>
                   <#assign labValue=''/>
                   <#assign index=0 />
                   <#--以分号分割 -->
                   <#list item.queryformatter?split(";") as it>
                   <#--以冒号分割,1:正常 -->
                       <#list it?split(":") as st>
                           <#if index==1>
                               <#assign labText=st/>
                           </#if>
                           <#assign labValue=st/>
                           <#assign index=index+1>
                       </#list>
                       <option value="${labValue}">${labText}</option>
                       <#assign index=0 />
                   </#list>
               </select>
           </div>
        <#elseif item.url??>
               <label class="col-sm-2 control-label" ><span style="color: ${color}">*</span>${item.fieldlabel}</label>
               <div class="col-sm-${smIndex}">
               <select class="form-control" name="${item.fieldname}" id="${item.fieldname}" required='${required?string ("true","false")}'>
               </select>
           </div>
               <script type="text/javascript">
               var url='${item.url}';
               if(url.length>0){
                   var params={};
                   var data=dealObj.doAjax(url,params,function (data) {
                       if(data!=undefined&&data.length>0){
                           var html='<option value="">请选择</option>';
                           for(var i=0;i<data.length;i++){
                               html+='<option value="'+data[i].LABVALUE+'">'+data[i].LABTEXT+'</option>';
                           }
                           $("#${item.fieldname}").append(html);
                       }
                   });
               }
           </script>
        <#elseif item.fieldtype=="VARCHAR2">
              <label class="col-sm-2 control-label"><span style="color: ${color}">*</span>${item.fieldlabel}</label>
              <div class="col-sm-${smIndex}">
               <input class="form-control" required='${required?string ("true","false")}'
                      id="${item.fieldname}" name="${item.fieldname}" type="text"
                      placeholder="请输入${item.fieldlabel}" maxlength="${item.fieldlength}"/>
           </div>
        <#elseif item.fieldtype=="NUMBER">
              <label class="col-sm-2 control-label"><span style="color: ${color}">*</span>${item.fieldlabel}</label>
              <div class="col-sm-${smIndex}">
               <input class="form-control" id="${item.fieldname}" required='${required?string ("true","false")}'
                      name="${item.fieldname}" type="text"
                      placeholder="请输入${item.fieldlabel}" maxlength="${item.fieldlength}"/>
           </div>
        <#elseif item.fieldtype=='DATE'>
             <label class="col-sm-2 control-label"><span style="color: ${color}">*</span>${item.fieldlabel}</label>
             <div class="col-md-${smIndex}">
                 <input class="form-control Wdate" readonly id="${item.fieldname}"
                        onClick="WdatePicker({dateFmt: 'yyyy-MM-dd HH:mm:ss'});"
                        name="${item.fieldname}" type="text"
                        placeholder="请输入${item.fieldlabel}" style="background-color: white;"
                        required='${required?string ("true","false")}'/>
            </div>
        <#else>
             <label class="col-sm-2 control-label"><span style="color: ${color}">*</span>${item.fieldlabel}</label>
             <div class="col-sm-${smIndex}">
               <input class="form-control" id="${item.fieldname}" required='${required?string ("true","false")}' name="${item.fieldname}" type="text"
                      placeholder="请输入${item.fieldlabel}" maxlength="${item.fieldlength}"/>
           </div>
       </#if>
         <#--以2个结束 -->
             <#if k%2==0>
                    </div>
                    <#if k!=size>
                        <div class="form-group">
                    </#if>
            <#elseif (k==size)&&(k%2!=0)><#--最后一个 -->
                </div>
            </#if>
            <#assign k=k+1>
    </#list>
</#if>
<#--遍历多文本域 -->
<#if formListMult?? && (formListMult?size > 0) >
       <#assign color=''>
       <#--判断是否为必填 -->
      <#assign required=false>
    <#list formListMult as item>
        <#if item.isnull==0>
            <#assign color='red'>
            <#assign required=true>
        <#else>
            <#assign color='black'>
        </#if>
      <#if (item.fieldtype=='BLOB')||(item.fieldtype=='CLOB')>
          <div class="form-group">
              <label class="col-sm-2 control-label"><span style="color: ${color}">*</span>${item.fieldlabel}</label>
              <div class="col-sm-10">
                <textarea class="form-control" rows="4" id="${item.fieldname}" name="${item.fieldname}"
                          placeholder="请输入${item.fieldlabel}" required='${required?string ("true","false")}' maxlength="${item.fieldlength}"></textarea>
              </div>
          </div>
      </#if>
    </#list>
</#if>
<@formButton buttonOper/>
</form>
</#macro>