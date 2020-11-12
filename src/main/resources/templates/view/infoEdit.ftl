<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    <meta charset="utf-8" />
    <meta name="description" content="overview &amp; stats" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />
    <#include "../common/frame/urlRoot.ftl"/>
    <#include "../common/form/form.ftl"/>
    <script type="text/javascript" src="${ctx}/static/assets/js/common/common.js"></script>
</head>
<body>
<#assign tableId="infoform">
<#assign formListOther=formListOther?eval/>
<#assign formListMult=formListMult?eval/>
<#assign buttonOper=buttonOper?eval/>
<@form tableId formListMult formListOther buttonOper/>
<script type="text/javascript">
    function doSave() {
        var result=doCheck();
        var url="/business/deal/saveInfo";
        if(result){//校验通过
            dealObj.addForm('${tableId}',url);
        }
    }
</script>
</body>
</html>