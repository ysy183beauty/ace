<#include "urlRoot.ftl"/>
<style type="text/css">
    #tipDiv {
        display: none;
        position: absolute;
        left: 40%;
        top: 230px;
        z-index: 9999;
        background: #d9d9d9;
        padding: 10px;
        border-radius: 5px;
    }
    #tipInfo {
        margin-top: 10px;
    }
</style>
<script type="text/javascript">
    //显示
    function showTip(info) {
        $('#tipInfo').html(info);
        $('#tipDiv').show();
    }
    //加载结束关闭
    function closeTip() {
        $('#tipDiv').hide();
    }
</script>
<div id="tipDiv">
    <center><img style="width:25px;" src="${ctx}/static/assets/images/common/123.gif"></center>
    <div id="tipInfo"></div>
</div>