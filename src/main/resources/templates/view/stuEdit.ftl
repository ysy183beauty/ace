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
<#assign tableId="studentTable-info-id">
<#assign formListOther=formListOther?eval/>
<#assign formListMult=formListMult?eval/>
<#assign buttonOper=buttonOper?eval/>
<@form tableId formListMult formListOther buttonOper/>
<script type="text/javascript">
    function doSave() {
        var result=doCheck();
        if(result){//校验通过

        }
    }
</script>
</body>
</html>