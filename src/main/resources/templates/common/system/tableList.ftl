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
</head>
<body>
 <#assign tableId="table-list-id"/>
 <#assign url="/sys/query/selectAllTables"/>
 <#assign isSingleSelect=true/>
 <#assign operateMap=[]/>
 <#assign queryFields=[{"fieldname":"tableName","fieldtype":"VARCHAR2","fieldlabel":"表名","fieldlength":"150"}]/>
 <#assign operInfos="query,reset,relation"/>
 <#assign cols=[{"fieldname":"id","fieldtype":"NUMBER","fiedlength":"20","fieldlabel":"主键"},
 {"fieldname":"tablename","fieldtype":"VARCHAR2","fiedlength":"150","fieldlabel":"表名"}]/>
 <@table tableId url cols isSingleSelect operateMap queryFields operInfos/>
 <script type="text/javascript">
     function doRelation(){
         //获取当前选中行的数据
         var tableId='${tableId}';
         var info='请选中一行！';
         var data=dealObj.getSelectRows(tableId,info);
         if(data.length>0){
             //调用open方法
             var url="/sys/view/getFieldList?tablename="+data[0].tablename;
             var widthX="90%";
             var widthY="90%";
             var title="字段列表信息";
             dealObj.doOpenLayer(url,widthX,widthY,title);
         }
     }
 </script>    
</body>
</html>