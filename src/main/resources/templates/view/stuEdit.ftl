<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    <meta charset="utf-8" />
    <meta name="description" content="overview &amp; stats" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />
    <#include "../common/frame/urlRoot.ftl"/>
    <#include "../common/form/form.ftl"/>
</head>
<body>
<#assign tableId="studentform">
<#assign formListOther=formListOther?eval/>
<#assign formListMult=formListMult?eval/>
<#assign buttonOper=buttonOper?eval/>
<@form tableId formListMult formListOther buttonOper/>
<script type="text/javascript">
    function doSave() {
        var result=doCheck();
        if(result){//校验通过
            var data=dealObj.getFormData('${tableId}');
            //数据传递到后台
            var params={
                "data":data
            };
            dealObj.doAjax("/business/deal/saveStudentInfo",params,function (json) {
                if(json.status){
                    top.dealObj.info="success";
                    dealObj.doCloseLayer();
                }else{
                    layer.alert("保存失败！", {skin: 'layui-layer-molv',icon: 0});
                }
            });
        }
    }
</script>
</body>
</html>