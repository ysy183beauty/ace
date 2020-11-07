<#macro formBody formList buttonOper>
<#include "../frame/urlRoot.ftl"/>
<#include "forButton.ftl"/>
<!-- 引入bootstrap样式 -->
<link href="${ctx}/static/assets/css/bootstrap.min.css" rel="stylesheet">
<!-- jquery -->
<script src="${ctx}/static/assets/js/jquery.min.js"></script>
<script src="${ctx}/static/assets/js/bootstrap.min.js"></script>
<script src="${ctx}/static/assets/js/InputSpinner.js"></script>
<script src="${ctx}/static/assets/layer/layer.js"></script>
<script src="${ctx}/static/assets/js/common/common.js"></script>
<form class="form-horizontal" role="form" style="margin: 5px;width: 95%;">
<#--如果集合不为空 -->
<#if formList?? && (formList?size > 0) >
    <#--获取集合的长度 -->
    <#assign size=formList?size>
    <#assign index=0>
    <#assign smIndex=4>
    <#--循环遍历 -->
    <#list formList as item>
        <#if index%2==0>
            <div class="form-group">
        </#if>
        <#--判断是否为最后一个并且个数为奇数 -->
        <#if (index==(size-1))&&(index%2>0)>
            <div class="form-group">
           <#assign smIndex=10>
        </#if>
        <#if item.fieldtype=="VARCHAR2">
            <#if item.querystatusmap??>
                <label class="col-sm-2 control-label">${item.fieldlabel}</label>
                <div class="col-sm-${smIndex}">
                    <select id="${item.fieldname}" name="${item.fieldname}" class="form-control">
                        <option value="">请选择</option>
                        <#assign labText=''/>
                        <#assign labValue=''/>
                        <#assign index=0 />
                        <#--以分号分割 -->
                        <#list item.querystatusmap?split(";") as it>
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
                    <div class="form-group">
                        <label class="col-sm-2 control-label" >${item.fieldlabel}</label>
                        <div class="col-sm-${smIndex}">
                            <select class="form-control" name="${item.fieldname}" id="${item.fieldname}">
                            </select>
                        </div>
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
                <#else>
                <label class="col-sm-2 control-label">${item.fieldlabel}</label>
                <div class="col-sm-${smIndex}">
                    <input class="form-control" id="${item.fieldname}" name="${item.fieldname}" type="text" placeholder="请输入${item.fieldlabel}"/>
                </div>
            </#if>
          <!--多文本域 -->
          <#elseif (item.fieldtype=='BLOB')||(item.fieldtype=='CLOB')>
            <label class="col-sm-2 control-label">${item.fieldlabel}</label>
            <div class="col-sm-${smIndex}">
                <textarea class="form-control" rows="6" id="${item.fieldname}" name="${item.fieldname}"
                          placeholder="请输入${item.fieldlabel}"></textarea>
            </div>
          <#elseif item.fieldtype=="NUMBER">
              <label class="col-sm-2 control-label">${item.fieldlabel}</label>
              <div class="col-sm-${smIndex}">
                  <input class="form-control" id="${item.fieldname}" name="${item.fieldname}" type="text" placeholder="请输入${item.fieldlabel}"/>
              </div>
           <!--判断是否为时间类型 -->
            <#elseif item.fieldtype=='DATE'>

        </#if>
         <#assign index=index+1>
         <#--偶数个 -->
         <#if index%2==0>
            </div>
         </#if>
        <#--判断是否为最后一个并且个数为奇数 -->
        <#if (index==(size-1))&&(index%2>0)>
            </div>
        </#if>
    </#list>
    <#--引入按钮信息 -->
    <@formButton buttonOper/>
</#if>
</form>
</#macro>