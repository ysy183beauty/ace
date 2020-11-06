<#macro form formList buttonOper>
<#include "../frame/urlRoot.ftl"/>
<#include "formBody.ftl"/>
<!-- 引入bootstrap样式 -->
<link href="${ctx}/static/assets/css/bootstrap.min.css" rel="stylesheet">
<!-- jquery -->
<script src="${ctx}/static/assets/js/jquery.min.js"></script>
<script src="${ctx}/static/assets/js/bootstrap.min.js"></script>
<script src="${ctx}/static/assets/layer/layer.js"></script>
<script src="${ctx}/static/assets/js/common/common.js"></script>
<@formBody formList buttonOper/>
<script type="text/javascript">
    function doCancel() {
        dealObj.doCloseLayer();
    }
</script>
</#macro>