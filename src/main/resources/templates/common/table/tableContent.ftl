<#macro tableContent tableId url cols isSingleSelect operateMap>
<#include "../frame/urlRoot.ftl"/>
<!-- 引入bootstrap样式 -->
<link href="${ctx}/static/assets/css/bootstrap.min.css" rel="stylesheet">
<!-- 引入bootstrap-table样式 -->
<link href="${ctx}/static/assets/css/bootstrap-table.min.css" rel="stylesheet">
<!-- jquery -->
<script src="${ctx}/static/assets/js/jquery.min.js"></script>
<script src="${ctx}/static/assets/js/bootstrap.min.js"></script>
<!-- bootstrap-table.min.js -->
<script src="${ctx}/static/assets/js/bootstrap-table.min.js"></script>
<!-- 引入中文语言包 -->
<script src="${ctx}/static/assets/js/bootstrap-table-zh-CN.min.js"></script>
 <#assign primaryKey=0>
 <script type="text/javascript">
 //处理列表显示信息
 var columns=[];
 //加载参数
 function loadSetting(){
     <#if cols?? && (cols?size > 0) >
         <#--设置复选框 -->
         var checkObj={
             field:"checked",
             checkbox:true
         };
         columns.push(checkObj);
         //设置行号
         var rowIdex={
             title: '行号',
             align: 'center',
             valign: 'bottom',
             formatter: function(value, row, index) {
                 return index + 1;
             }
         };
     columns.push(rowIdex);
     <#list cols as item>
         var info;
         <#assign field=item.fieldname?lower_case>
         <#--判断是否为主键，状态信息 -->
         <#if field?contains("id")>
         <#assign primaryKey=field>
         info={
             field:"${field}",
             title:"主键",
             visible: false
         };
         <#else>
         info={
             field:"${field}",
             title:"${item.fieldlabel}",
             align:"center",
             valign:"middle",
             sortable:"true",
             <#if item.listformatter??>
                <#assign formatter=item.listformatter>
                ${formatter}
             </#if>
         };
         </#if>
         columns.push(info);
     </#list>
         <#--判断是否含有操作按钮 -->
         <#if operateMap?? && (operateMap?size > 0)>
         var operatInfo={
             field: 'operate',
             title: '操作',
             align: 'center',
             colspan: 1,
             events: operateEvents,
             formatter:operateFormatter
         };
         columns.push(operatInfo);
         </#if>
     <#else>
     layer.msg("字段集合不能为空！")
     </#if>
 }
 $(function () {
     loadSetting();
     loadTable();//加载数据信息
 });
 window.operateEvents = {
     'click .edit': function (e, value, row, index) {
         doEdit(row);
     },'click .remove': function (e, value, row, index) {
         doDel(row);
     },
 };
 function operateFormatter(value, row, index) {
     var html="";
     <#--判断是否含有操作按钮 -->
      <#if operateMap?? && (operateMap?size > 0)>
          <#assign editInfo=operateMap["edit"]/>
          <#assign removeInfo=operateMap["remove"]/>
          <#if editInfo==true>
              html+='<a class="edit" href="javascript:void(0)" data-toggle="modal" data-target="#editModal" style="text-decoration:none">';
              html+='<i class="glyphicon glyphicon-pencil"></i>编辑</a>';
          </#if>
          if (html!=''){
              html+=' | ';
          }
          <#if removeInfo==true>
             html+='<a class="remove" href="javascript:void(0)"  title="Delete Item" style="text-decoration:none">';
             html+='<i class="glyphicon glyphicon-remove-circle">删除</i></a>';
          </#if>
      </#if>
      return html;
 }
 //加载数据
 function loadTable() {
       var setting={
           url : '${url}', // 请求后台的URL（*）
           method : 'post', // 请求方式（*）post/get
           contentType: "application/x-www-form-urlencoded",//post请求的话就加上这个句话
           toolbar : '#toolbar', // 工具按钮用哪个容器
           striped : true, // 是否显示行间隔色
           cache : false, // 是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
           pagination : true, // 是否显示分页（*）
           sortable : false, // 是否启用排序
           sortOrder : "asc", // 排序方式
           <#if isSingleSelect==true>
           singleSelect:true,//设置只选中一行
           <#else>
           singleSelect:false,//设置选中多行
           </#if>
           sidePagination : "server", // 分页方式：client客户端分页，server服务端分页（*）
           queryParams : {},// 传递参数（*）
           pageNumber : 1, // 初始化加载第一页，默认第一页
           pageSize : 10, // 每页的记录行数（*）
           pageList : [ 10, 25, 50, 100 ], // 可供选择的每页的行数（*）
           smartDisplay: false,
           search : false, // 是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
           strictSearch : true,
           showColumns : false, // 是否显示所有的列
           showRefresh : false, // 是否显示刷新按钮
           minimumCountColumns :2, // 最少允许的列数
           clickToSelect : true, // 是否启用点击选中行
           height : "",// 行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
           uniqueId : "${primaryKey}", // 每一行的唯一标识，一般为主键列
           showToggle : false, // 是否显示详细视图和列表视图的切换按钮
           cardView : false, // 是否显示详细视图
           detailView : false, // 是否显示父子表
           locale: "zh-CN", //中文支持
           queryParams: queryParams,//传递参数
           columns:columns
       }
       $('#${tableId}').bootstrapTable(setting);
   }
   //加载form表单下的所有参数信息
     function queryParams(params) {
         var data = {};
         var t = $('#queryForm').serializeArray();
         $.each(t, function() {
             data [this.name] = this.value;
         });
         data['offset']=params.offset;
         data['limit']=params.limit;
         return data;
     }
 </script>
</#macro>
