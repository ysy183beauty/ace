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
<#assign formList=formList?eval/>
<#assign buttonOper=buttonOper?eval/>
<@form formList buttonOper />
</body>
</html>