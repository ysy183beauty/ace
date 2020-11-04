<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    <meta charset="utf-8" />
    <meta name="description" content="overview &amp; stats" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />
    <#include "../frame/urlRoot.ftl"/>
    <#include "../frame/msgTip.ftl" />
    <!---bootstrap使用的是3-->
    <link href="${ctx}/static/assets/css/bootstrap.min.css" rel="stylesheet" />
    <link href="${ctx}/static/assets/css/bootstrap-editable.css" rel="stylesheet" />
    <link href="${ctx}/static/assets/css/bootstrap-table.min.css" rel="stylesheet" />
    <link href="${ctx}/static/assets/css/bootstrap-select.css" rel="stylesheet" />
    <script src="${ctx}/static/assets/js/jquery-1.10.2.js"></script>
    <script src="${ctx}/static/assets/js/bootstrap.min.js"></script>
    <!---行内编辑使用的 1.1.5-->
    <script src="${ctx}/static/assets/js/bootstrap-editable.js"></script>
    <!--bootstrap-table中文包及js-->
    <script src="${ctx}/static/assets/js/bootstrap-table.min.js"></script>
    <script src="${ctx}/static/assets/js/bootstrap-table-zh-CN.min.js"></script>
    <script src="${ctx}/static/assets/js/bootstrap-table-editable.js"></script>
    <script src="${ctx}/static/assets/js/bootstrap-select.js"></script>
    <script type="text/javascript" src="${ctx}/static/assets/js/common/common.js"></script>
    <script type="text/javascript" src="${ctx}/static/assets/layer/layer.js"></script>
</head>
<body>
<div class="container">
    <div id="toolbar" class="btn-group" style="margin-bottom: 5px;">
        <form class="form-inline">
            <div class="form-group">
                <label class="sr-only" for="msg_type">表名</label>
                <div class="input-group">
                    <div class="input-group-addon">表名</div>
                    <input type="text" class="form-control" maxlength="150" name="tableName" id="tableName">
                </div>
            </div>
            <div class="form-group">
                <label class="sr-only" for="product_line">sql语句集合</label>
                <div class="input-group">
                    <div class="input-group-addon">sql语句集合</div>
                    <select class="form-control selectpicker bla bla bli" name="" id="sqlSelect" data-live-search="true">

                    </select>
                </div>
            </div>
            <button type="button" class="btn btn-default" onclick="doQuery();">
                <span class="glyphicon glyphicon-search" aria-hidden="true"></span>查询
            </button>
            <button type="button" class="btn btn-default" onclick="doTestSql();">
                <span class="glyphicon glyphicon-link" aria-hidden="true"></span>测试sql
            </button>
            <button type="button" class="btn btn-default" onclick="doSave();">
                <span class="glyphicon glyphicon-saved" aria-hidden="true"></span>保存
            </button>
        </form>
    </div>
    <!--引入查询条件 -->
    <table id="table-edit-id"></table>
</div>
<script type="text/javascript">
    $(function () {
        loadSqlData();
    });
    //加载sql语句集合
    function loadSqlData() {
        var url="/sys/query/selectAllSql";
        var params={};
        var isAsync=false;
        dealObj.doAjax(url,params,function (data) {
             var html='<option value="" selected>请选择</option>';
             if(data.length>0){
                 for(var i=0;i<data.length;i++){
                     html+='<option value="'+data[i].id+'">'+data[i].description+'</option>';
                 }
                 $("#sqlSelect").html(html);//追加到下拉框中
             }
        },isAsync);
        $('.selectpicker').selectpicker({
            'selectedText': ''
        });
    }
    //点击查询sql语句按钮
    function doTestSql() {
       var sqlSelect=$("#sqlSelect").val();
       if(sqlSelect==''){
           layer.alert("请选中sql语句集合！", {skin: 'layui-layer-molv',icon: 0});
           return;
       }else{
           showTip("数据正在加载");
           var url="/sys/query/selectColumns";
           var params={
               "sqlId":sqlSelect
           };
           dealObj.doAjax(url,params,function (data) {
               loadTableData(data);
           });
       }
    }
    //加载表格信息
    function loadTableData(json) {
        $('#table-edit-id').bootstrapTable('destroy');
        showTip("数据正在加载");
        $("#table-edit-id").bootstrapTable({
            data:json,
            method: 'get',                      //请求方式（*）
            cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
            pagination:false,
            sidePagination: "server",
            pageNumber: 1,
            contentType: "application/x-www-form-urlencoded",
            datatype: 'json',
            formatNoMatches: function(){
                return "没有相关的匹配结果";
            },
            formatLoadingMessage: function(){
                return "请稍等，正在加载中....";
            },
            columns: [
                {
                    title: '行号',
                    align: 'center',
                    valign: 'bottom',
                    formatter: function(value, row, index) {
                        return index + 1;
                    }
                },
                {
                    field: 'id',
                    title: '主键',
                    visible: false
                },
                {
                    field: 'fieldname',
                    title: '字段名称'
                },
                {
                    field: 'fieldtype',
                    title: '字段类型',
                    editable:{
                        type: "text",                //编辑框的类型。支持text|textarea|select|date|checklist等#}
                        title: "字段类型",              //编辑框的标题#}
                        type: "select",              //编辑框的类型。支持text|textarea|select|date|checklist等
                        source:[
                            {value:'NUMBER', text: "NUMBER"}, {value:'VARCHAR2', text: "VARCHAR2"},
                            {value:'BLOB', text: "BLOB"},{value:'DATE', text: "DATE"},
                            {value:'DECIMAL', text: "DECIMAL"}
                         ],
                        title: "字段类型",           //编辑框的标题
                        disabled: false,             //是否禁用编辑
                        emptytext: "空文本",          //空值的默认文本
                        mode: "inline",              //编辑框的模式：支持popup和inline两种模式，默认是popup
                        validate: function (value) { //字段验证
                            if (!$.trim(value)) {
                                return '字段类型不能为空';
                            }
                        }
                    }
                },
                {
                    field: 'fieldlength',
                    title: '字段长度',
                    editable:{
                        type: 'text',
                        title: '字段长度',
                        validate:  function (v) {
                            var reg=/(^[1-9]\d*$)/;
                            if(!reg.test(v)){
                                return '必须是整数';
                            }
                        }
                    }
                },
                {
                    field: 'fieldlabel',
                    title: '中文名称',
                    editable:{
                        type: "text",
                        title: "中文名称",
                        disabled: false,
                        emptytext: "空文本",
                        mode: "inline",//编辑框的模式：支持popup和inline两种模式，默认是popup
                        validate: function (value) { //字段验证
                            if (!$.trim(value)) {
                                return '中文名称不能为空';
                            }
                        }
                    }
                },
                {
                    field: 'listdisplay',
                    title: '列表是否显示',
                    editable:{
                        type: "text",                //编辑框的类型。支持text|textarea|select|date|checklist等#}
                        title: "列表是否显示",              //编辑框的标题#}
                        type: "select",              //编辑框的类型。支持text|textarea|select|date|checklist等
                        source: [{value:1, text: "是"}, {value: 2, text: "否"}],
                        title: "列表是否显示",           //编辑框的标题
                        disabled: false,             //是否禁用编辑
                        emptytext: "空文本",          //空值的默认文本
                        mode: "inline",              //编辑框的模式：支持popup和inline两种模式，默认是popup
                        validate: function (value) { //字段验证
                            if (!$.trim(value)) {
                                return '请选中列表是否显示';
                            }
                        }
                    }
                },
                {
                    field: 'formdisplay',
                    title: '表单是否显示',
                    editable:{
                        type: "text",                //编辑框的类型。支持text|textarea|select|date|checklist等#}
                        title: "表单是否显示",              //编辑框的标题#}
                        type: "select",              //编辑框的类型。支持text|textarea|select|date|checklist等
                        source: [{value:1, text: "是"}, {value: 2, text: "否"}],
                        title: "表单是否显示",           //编辑框的标题
                        disabled: false,             //是否禁用编辑
                        emptytext: "空文本",          //空值的默认文本
                        mode: "inline",              //编辑框的模式：支持popup和inline两种模式，默认是popup
                        validate: function (value) { //字段验证
                            if (!$.trim(value)) {
                                return '请选中表单是否显示';
                            }
                        }
                    }
                },
                {
                    field: 'querydisplay',
                    title: '是否查询条件',
                    editable:{
                        type: "text",                //编辑框的类型。支持text|textarea|select|date|checklist等#}
                        title: "是否查询条件",              //编辑框的标题#}
                        type: "select",              //编辑框的类型。支持text|textarea|select|date|checklist等
                        source: [{value:1, text: "是"}, {value: 2, text: "否"}],
                        title: "是否查询条件",           //编辑框的标题
                        disabled: false,             //是否禁用编辑
                        emptytext: "空文本",          //空值的默认文本
                        mode: "inline",              //编辑框的模式：支持popup和inline两种模式，默认是popup
                        validate: function (value) { //字段验证
                            if (!$.trim(value)) {
                                return '请选中是否查询条件';
                            }
                        }
                    }
                }
            ]
        });
        closeTip();
    }
    //点击保存
    function doSave() {
        var tableName=$("#tableName").val();
        if(tableName==''){
            layer.alert("请填写表名！", {skin: 'layui-layer-molv',icon: 0});
            return;
        }else{
            //获取数据信息
            var data=$("#table-edit-id").bootstrapTable("getData");
            //转换为json
            var json=JSON.stringify(data);
            var url="/sys/deal/batchSaveBaseInfo";
            var params={
                "data":json,
                "tableName":tableName
            };
            dealObj.doAjax(url,params,function (data) {
               if(data.result){
                   layer.alert("保存成功！", {skin: 'layui-layer-molv',icon: 1});
               }else{
                   layer.alert("保存失败！", {skin: 'layui-layer-molv',icon: 0});
               }
            });
        }
    }
    //点击查询按钮
    function doQuery() {
        var tableName=$("#tableName").val();
        if(tableName==''){
            layer.alert("请填写表名！", {skin: 'layui-layer-molv',icon: 0});
            return;
        }else{
            showTip("数据正在加载");
            var url="/sys/query/selectBaseInfoSysList";
            var params={
                "tableName":tableName
            };
            dealObj.doAjax(url,params,function (data) {
                loadTableData(data);
            },false);
        }
    }
</script>
</body>
</html>