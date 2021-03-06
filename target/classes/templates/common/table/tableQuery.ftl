<#macro tableQuery queryFields>
<#include "../frame/urlRoot.ftl"/>
<#include "../frame/msgTip.ftl"/>
<#--引入Jquery -->
<script type="text/javascript" src="${ctx}/static/assets/js/jquery.min.js"></script>
<script src="${ctx}/static/assets/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${ctx}/static/assets/js/common/common.js"></script>
    <#--判断集合是否为空 -->
    <#if queryFields?? && (queryFields?size > 0)>
        <#--遍历集合 -->
       <#list queryFields as item>
           <#--字段名称 -->
           <#assign fieldname=item.fieldname?lower_case>
          <#--判断queryformatter是否为空 -->
          <#if item.queryformatter??>
                  <div class="form-group">
                  <label class="sr-only" for="product_line">${item.fieldlabel}</label>
                  <div class="input-group">
                      <div class="input-group-addon">${item.fieldlabel}</div>
                      <select class="form-control" name="${fieldname}" id="${fieldname}" dataType="${item.fieldtype}">
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
                                  <#if index==0>
                                      <#assign labValue=st/>
                                  </#if>
                                  <#assign index=index+1>
                              </#list>
                              <option value="${labValue}">${labText}</option>
                              <#assign index=0 />
                          </#list>
                      </select>
                  </div>
              </div>
              <#elseif item.url??>
                  <div class="form-group">
                      <label class="sr-only" for="product_line">${item.fieldlabel}</label>
                      <div class="input-group">
                          <div class="input-group-addon">${item.fieldlabel}</div>
                          <select class="form-control" name="${fieldname}" id="${fieldname}" dataType="${item.fieldtype}">
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
                                  $("#${fieldname}").append(html);
                              }
                          },false);
                      }
                  </script>
              <#elseif item.fieldtype=='NUMBER'>
                   <div class="form-group">
                  <label class="sr-only" for="msg_type">${item.fieldlabel}</label>
                  <div class="input-group">
                      <div class="input-group-addon">${item.fieldlabel}</div>
                      <input type="text" class="form-control" dataType="${item.fieldtype}" maxlength="${item.fieldlength}" name="${fieldname}" id="${fieldname}">
                  </div>
              </div>
              <#elseif item.fieldtype=='BLOB'||item.fieldtype=='CLOB'>
                   <div class="form-group">
                  <label class="sr-only" for="msg_type">${item.fieldlabel}</label>
                  <div class="input-group">
                      <div class="input-group-addon">${item.fieldlabel}</div>
                      <textarea class="form-control" dataType="${item.fieldtype}" name="${fieldname}" id="${fieldname}" maxlength="${item.fieldlength}"></textarea>
                  </div>
              </div>
              <#elseif item.fieldtype=='VARCHAR2'>
                   <div class="form-group">
                  <label class="sr-only" for="msg_type">${item.fieldlabel}</label>
                  <div class="input-group">
                      <div class="input-group-addon">${item.fieldlabel}</div>
                      <input type="text" class="form-control" dataType="${item.fieldtype}" name="${fieldname}" id="${fieldname}" maxlength="${item.fieldlength}">
                  </div>
              </div>
               <#--时间-->
              <#elseif item.fieldtype=='DATE'>
                 <div class="form-group">
                     <label class="sr-only" for="msg_type">${item.fieldlabel}</label>
                     <div class="input-group">
                         <div class="input-group-addon">${item.fieldlabel}</div>
                         <input type="text" dataType="${item.fieldtype}" class="form-control Wdate" name="${fieldname}"
                                id="${fieldname}" readonly maxlength="${item.fieldlength}" onClick="WdatePicker();"
                                style="background-color: white;">
                     </div>
                 </div>
              <#else>
                   <div class="form-group">
                  <label class="sr-only" for="msg_type">${item.fieldlabel}</label>
                  <div class="input-group">
                      <div class="input-group-addon">${item.fieldlabel}</div>
                      <input type="text" class="form-control" dataType="${item.fieldtype}" name="${fieldname}" id="${fieldname}" maxlength="${item.fieldlength}">
                  </div>
              </div>
          </#if>
       </#list>
    </#if>
</#macro>