<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    <meta charset="utf-8" />
    <meta name="description" content="overview &amp; stats" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />
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
    <script src="${ctx}/static/assets/layer/layer.js"></script>
    <script src="${ctx}/static/assets/js/common/common.js"></script>
</head>
<body>
<div class="container">
    <div id="toolbar" class="btn-group">
        <form class="form-inline" id="myFrom">
            <div class="form-group">
                <label class="sr-only" for="msg_type">表名</label>
                <div class="input-group">
                    <div class="input-group-addon">表名</div>
                    <input type="text" class="form-control" maxlength="150" name="tablename" id="tablename">
                </div>
            </div>
            <div class="form-group">
                <label class="sr-only" for="msg_type">字段名称</label>
                <div class="input-group">
                    <div class="input-group-addon">字段名称</div>
                    <input type="text" class="form-control" maxlength="150" name="fieldname" id="fieldname">
                </div>
            </div>
            <button type="button" class="btn btn-default" onclick="doQuery();">
                <span class="glyphicon glyphicon-search" aria-hidden="true"></span>查询
            </button>
            <button type="button" class="btn btn-default" onclick="doReset();">
                <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>重置
            </button>
        </form>
    </div>
    <!--引入查询条件 -->
    <table id="setting-list-id"></table>
</div>
<script type="text/javascript">
    $(function () {
        var columns=[
            {
                field:"checked",
                checkbox:true
            },{
                title: '行号',
                align: 'center',
                valign: 'bottom',
                formatter: function(value, row, index) {
                    return index + 1;
                }
            },{
                field:"id",
                title:"主键",
                visible: false
            },{
                field:"tablename",
                title:"表名",
                align:"center",
                valign:"middle",
                sortable:"true"
            },
            {
                field:"fieldname",
                title:"字段名称",
                align:"center",
                valign:"middle",
                sortable:"true"
            },{
                field:"fieldtype",
                title:"字段类型",
                align:"center",
                valign:"middle",
                sortable:"true"
            },{
                field:"fieldlength",
                title:"字段长度",
                align:"center",
                valign:"middle",
                sortable:"true"
            },{
                field:"fieldlabel",
                title:"中文名称",
                align:"center",
                valign:"middle",
                sortable:"true"
            },{
                field:"listdisplay",
                title:"列表是否显示",
                align:"center",
                valign:"middle",
                sortable:"true",
                formatter:function (value, row, index) {
                    if(value==1){
                        return '<span class="label label-success radius">是</span>';
                    }else{
                        return '<span class="label label-waring radius">否</span>';
                    }
                }
            },
            {
                field:"formdisplay",
                title:"表单是否显示",
                align:"center",
                valign:"middle",
                sortable:"true",
                formatter:function (value, row, index) {
                    if(value==1){
                        return '<span class="label label-success radius">是</span>';
                    }else{
                        return '<span class="label label-waring radius">否</span>';
                    }
                }
            },
            {
                field:"querydisplay",
                title:"查询是否显示",
                align:"center",
                valign:"middle",
                sortable:"true",
                formatter:function (value, row, index) {
                    if(value==1){
                        return '<span class="label label-success radius">是</span>';
                    }else{
                        return '<span class="label label-waring radius">否</span>';
                    }
                }
            },{
                field:"statusmap",
                title:"状态信息",
                align:"center",
                valign:"middle",
                sortable:"true",
                formatter: function (value, row, index){
                    return '<a href="javascript:void(0);" onclick="doUpdate(this,'+row.id+',1);">状态信息</a>';
                }
            },
            {
                field:"url",
                title:"服务器地址",
                align:"center",
                valign:"middle",
                sortable:"true",
                formatter: function (value, row, index){
                    return '<a href="javascript:void(0);" onclick="doUpdate(this,'+row.id+',2);">服务器地址</a>';
                }
            }
        ]
        var setting={
            url : '/sys/query/selectAllBaseInfo', // 请求后台的URL（*）
            method : 'post', // 请求方式（*）post/get
            contentType: "application/x-www-form-urlencoded",//post请求的话就加上这个句话
            toolbar : '#toolbar', // 工具按钮用哪个容器
            striped : true, // 是否显示行间隔色
            cache : false, // 是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
            pagination : true, // 是否显示分页（*）
            sortable : false, // 是否启用排序
            sortOrder : "asc", // 排序方式
            singleSelect:true,//设置只选中一行
            sidePagination : "server", // 分页方式：client客户端分页，server服务端分页（*）
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
            uniqueId : "id", // 每一行的唯一标识，一般为主键列
            showToggle : false, // 是否显示详细视图和列表视图的切换按钮
            cardView : false, // 是否显示详细视图
            detailView : false, // 是否显示父子表
            locale: "zh-CN", //中文支持
            queryParams:queryParams,
            columns:columns
        }
        $('#setting-list-id').bootstrapTable(setting);
    });
    //清空
    function doReset() {
        dealObj.doReset('myFrom');
    }
    function queryParams(params) {
        var data = {};
        var t = $('#myFrom').serializeArray();
        $.each(t, function() {
            data [this.name] = this.value;
        });
        data['offset']=params.offset;
        data['limit']=params.limit;
        return data;
    }
    //获取表单下所有数据信息
    function doQuery() {
        dealObj.doQuery('setting-list-id');
    }
    //修改
    function doUpdate(obj,id,type) {
        var widthX="60%";
        var widthY="60%";
        var title=$(obj).text();
        var url="/sys/view/updateStatusMap?id="+id+"&type="+type+"&title="+title;
        dealObj.doOpenLayer(url,widthX,widthY,title,function () {
            layer.alert("保存成功！", {skin: 'layui-layer-molv',icon: 1});
            top.dealObj.info=undefined;
        });
    }
</script>
</body>
</html>