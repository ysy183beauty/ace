<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    <meta charset="utf-8" />
    <meta name="description" content="overview &amp; stats" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />
    <#include "../frame/urlRoot.ftl"/>
    <#include "../table/table.ftl"/>
    <script src="${ctx}/static/assets/js/jquery-1.11.3.min.js"></script>
    <script src="${ctx}/static/assets/layer/layer.js"></script>
    <script src="${ctx}/static/assets/js/common/common.js"></script>
    <#include "../frame/msgTip.ftl"/>
</head>
<body>
<#assign tableId="field-list-id"/>
<#assign url="/sys/query/selectAllFieldS?tableName=${tableName}"/>
<#assign isSingleSelect=false/>
<#assign operateMap=[]/>
<#assign queryFields=[{"fieldname":"columnname","fieldtype":"VARCHAR2","fieldlabel":"字段名称","fieldlength":"150"}]/>
<#assign operInfos="query,reset,list,form,condition"/>
<#assign cols=[{"fieldname":"columnid","fieldtype":"NUMBER","fiedlength":"20","fieldlabel":"主键","statusMap":""},
{"fieldname":"columnname","fieldtype":"VARCHAR2","fiedlength":"150","fieldlabel":"字段名称","statusMap":""},
{"fieldname":"comments","fieldtype":"VARCHAR2","fiedlength":"100","fieldlabel":"字段中文名","statusMap":""},
{"fieldname":"datatype","fieldtype":"VARCHAR2","fiedlength":"100","fieldlabel":"字段类型","statusMap":""},
{"fieldname":"datalength","fieldtype":"NUMBER","fiedlength":"20","fieldlabel":"字段长度","statusMap":""},
{"fieldname":"nullable","fieldtype":"VARCHAR2","fiedlength":"50","fieldlabel":"是否为空","statusMap":""}]/>
<@table tableId url cols isSingleSelect operateMap queryFields operInfos/>
<script type="text/javascript">
  function doList(){//列表显示字段
      commDeal("/sys/deal/dealListField");
  }
  function doForm() {//form表单需要的数据
      commDeal("/sys/deal/dealFormField");
  }
  function doCondition() {//查询条件的数据
      commDeal("/sys/deal/dealQueryField");
  }
  function commDeal(url) {
      //获取当前选中行的数据
      var tableId='${tableId}';
      var info='请选中数据信息！';
      var data=dealObj.getSelectRows(tableId,info);
      if(data.length>0){
          //提交到后台数据
          var json=JSON.stringify(data);
          var params={
              "tableName":'${tableName}',
              "data":json
          };
          var isAsync=false;
          dealObj.doAjax(url,params,isAsync,function (obj) {
              if(obj.status){
                  layer.alert("保存成功！", {skin: 'layui-layer-molv',icon: 1});
              }else{
                  layer.alert("处理失败！", {skin: 'layui-layer-molv',icon: 0});
              }
              dealObj.cancelAllCheck('${tableId}');
          });
      }
  }
</script>
</body>
</html>