<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    <meta charset="utf-8" />
    <meta name="description" content="overview &amp; stats" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />
    <#include "../common/frame/urlRoot.ftl"/>
    <#include "../common/table/table.ftl"/>
    <script src="${ctx}/static/assets/js/jquery-1.11.3.min.js"></script>
    <script src="${ctx}/static/assets/layer/layer.js"></script>
    <script src="${ctx}/static/assets/js/common/common.js"></script>
</head>
<body>
<#assign tableId="table-student-id"/>
<#assign url="/business/query/selectStudents"/>
<#assign isSingleSelect=true/>
<#assign operateMap={"edit":true,"remove":true}/>
<#assign queryFields=queryFields?eval/>
<#assign operInfos="query,reset,add"/>
<#assign cols=cols?eval/>
<@table tableId url cols isSingleSelect operateMap queryFields operInfos/>
<script type="text/javascript">
    //编辑信息
    function doEdit(row) {
        console.log(row);
    }
    //删除信息
    function doDel(row) {
        layer.confirm('您确定要删除数据吗?',{btn: ['确定', '取消'],title:"提示",skin: 'layui-layer-molv',icon: 3}, function(){
            layer.msg("确定");
        });
    }
    //重置
    function doReset() {
        dealObj.doReset('queryForm');
    }
    //点击查询按钮
    function doQuery() {
        dealObj.doQuery('${tableId}');
    }
    //添加
    function doAdd() {
        var url="/business/view/toAddStuPage";
        var widthX="90%";
        var widthY="90%";
        var title="添加数据信息";
        dealObj.doOpenLayer(url,widthX,widthY,title,function () {
            layer.alert("保存成功！", {skin: 'layui-layer-molv',icon: 1});
            top.dealObj.info=undefined;
            this.doQuery();
        });
    }
</script>
</body>
</html>