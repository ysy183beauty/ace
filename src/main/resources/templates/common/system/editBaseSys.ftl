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
    <script type="text/javascript">
        function doCancel() {
            var index = parent.layer.getFrameIndex(window.name);
            top.layer.close(index);
        }
        function doSave() {
            var content=$("#content").val();
            if(content.length==0){
                layer.alert("${title}不能为空！", {skin: 'layui-layer-molv',icon: 1});
                return;
            }else{
                var url="/sys/deal/updateBaseInfo";
                var params={
                    id:"${id}",
                    type:"${type}",
                    content:content
                };
                dealObj.doAjax(url,params,function (data) {
                    if(data.status){
                        top.dealObj.info="success";
                    }
                    doCancel();//关闭
                });
            }
        }
        $(function () {
            var content=${content};
            $("#content").html(content);
        });
    </script>
</head>
<body>
<div style="margin: 5px;width: 95%;">
    <form class="form-horizontal" role="form">
        <div class="form-group">
            <label class="col-sm-2 control-label" for="ds_host">${title}</label>
            <div class="col-sm-8">
                <textarea class="form-control" rows="10" id="content"></textarea>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label"></label>
            <div class="col-sm-8">
                <input type="button" id="login" value="保存" class="btn btn-success" style="width: 120px;" onclick="doSave();">
                <input type="button" id="logout" value="取消" class="btn btn-danger" style="width: 120px;" onclick="doCancel();">
            </div>
        </div>
    </form>
</div>
</body>
</body>
</html>