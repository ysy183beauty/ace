<#macro tableQuery queryFields>
<#include "../frame/urlRoot.ftl"/>
<#--引入Jquery -->
<script type="text/javascript" src="${ctx}/static/assets/js/jquery.min.js"></script>
<script type="text/javascript" src="${ctx}/static/assets/js/common/common.js"></script>
    <#--判断集合是否为空 -->
    <#if queryFields?? && (queryFields?size > 0)>
        <#--遍历集合 -->
       <#list queryFields as item>
           <#if item.fieldtype='NUMBER'>
               <div class="form-group">
                   <label class="sr-only" for="msg_type">${item.fieldlabel}</label>
                   <div class="input-group">
                       <div class="input-group-addon">${item.fieldlabel}</div>
                       <input type="text" class="form-control" maxlength="${item.fieldlength}" name="${item.fieldname}" id="${item.fieldname}">
                   </div>
               </div>
            <#elseif item.fieldtype='BLOB'>
                <div class="form-group">
                    <label class="sr-only" for="msg_type">${item.fieldlabel}</label>
                    <div class="input-group">
                        <div class="input-group-addon">${item.fieldlabel}</div>
                        <textarea class="form-control" name="${item.fieldname}" id="${item.fieldname}" maxlength="${item.fieldlength}"></textarea>
                    </div>
                </div>
            <#elseif item.fieldtype='VARCHAR2'>
               <div class="form-group">
                   <label class="sr-only" for="msg_type">${item.fieldlabel}</label>
                   <div class="input-group">
                       <div class="input-group-addon">${item.fieldlabel}</div>
                       <input type="text" class="form-control" name="${item.fieldname}" id="${item.fieldname}" maxlength="${item.fieldlength}">
                   </div>
               </div>
               <#--固定值的下拉框 -->
             <#elseif item.statusMap!=''>
               <div class="form-group">
                   <label class="sr-only" for="product_line">${item.fieldlabel}</label>
                   <div class="input-group">
                       <div class="input-group-addon">${item.fieldlabel}</div>
                       <select class="form-control" name="${item.fieldname}" id="${item.fieldname}">
                           <option value="">请选择</option>
                           <#assign labText=''/>
                           <#assign labValue=''/>
                           <#assign index=0 />
                             <#--以分号分割 -->
                            <#list item.statusMap?split(";") as it>
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
               </div>
             <#elseif item.fieldtype='DATE'>
             <!--url的下拉框 -->
             <#elseif item.url!=''>
               <div class="form-group">
                   <label class="sr-only" for="product_line">${item.fieldlabel}</label>
                   <div class="input-group">
                       <div class="input-group-addon">${item.fieldlabel}</div>
                       <select class="form-control" name="${item.fieldname}" id="${item.fieldname}" onchange="getData('${item.url}','${item.fieldname}');">
                       </select>
                   </div>
               </div>
            <#else>
               <div class="form-group">
                   <label class="sr-only" for="msg_type">${item.fieldlabel}</label>
                   <div class="input-group">
                       <div class="input-group-addon">${item.fieldlabel}</div>
                       <input type="text" class="form-control" name="${item.fieldname}" id="${item.fieldname}" maxlength="${item.fieldlength}">
                   </div>
               </div>
           </#if>
       </#list>
        <script type="text/javascript">
            //获取后台数据信息
            function getData(url,domId) {
                if(url!=''){
                    var params={};
                    var data=dealObj.doAjax(url,params);
                    if(data!=undefined&&data.length>0){
                        var html='<option value="">请选择</option>';
                        for(var i=0;i<data.length;i++){
                            html+='<option value="'+data[i].labValue+'">'+data[i].labText+'</option>';
                        }
                        $("#"+domId).append(html);
                    }
                }
            }
        </script>
    </#if>
</#macro>