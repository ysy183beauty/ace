/**
 * 公共处理类
 * @type {{}}
 */
var dealObj={
    info:undefined,//标识
    doAjax:function (url,params,fn,isAsync) {//ajax处理数据信息
        $.ajax({
            type: "POST",
            url: url,
            data: params,
            dataType: "json",
            cache: false,
            async: isAsync == undefined ? true : isAsync,
            beforeSend: function () {
                if(isAsync!=undefined){
                    showTip("数据正在处理....");
                }
            },
            success: function (data) {
                 if(isAsync!=undefined){
                    closeTip();
                 }
                if (fn!=undefined&&fn instanceof Function){
                    fn(data);
                }
            },error: function (e, jqxhr, settings, exception) {
                console.log(e);
            },complete: function() {

            }
        });
    },
    //获取选中的行数据信息
    getSelectRows:function (domId,info) {
        // 获取当前行
        var rows=$('#'+domId+'').bootstrapTable('getSelections');
        if(rows.length==0){
            layer.alert(info, {skin: 'layui-layer-molv',icon: 0});
        }
        return rows;
    },
    cancelAllCheck:function(domId){
        $('#'+domId+'').bootstrapTable("uncheckAll");
    },
    //跳出层信息
    doOpenLayer:function (url,widthX,widthY,title,fn) {
        top.layer.open({
            type: 2,
            skin: 'layui-layer-molv', //样式类名
            title:title, //不显示标题
            area: [widthX,widthY],
            content:encodeURI(url), //捕获的元素，注意：最好该指定的元素要存放在body最外层，否则可能被其它的相对元素所影响
            end:function () {
               if(fn!=undefined&&fn instanceof Function){
                   if(top.dealObj.info!=undefined) {
                       fn();//执行回调函数
                   }
               }
            }
        });
    },
    //清空form表单数据信息
    doReset:function (domId) {
        $('#'+domId+'').find('input[type=text],select,textarea,input[type=hidden]').each(function() {
            $(this).val('');
        });
    },
    //查询功能
    doQuery:function(domId){
        $('#'+domId+'').bootstrapTable(('refresh'));
    },
    //获取表单数据信息
    getFormData:function (domId) {
        var data = {};
        var t = $('#'+domId+'').serializeArray();
        $.each(t, function() {
            data [this.name] = this.value;
        });
        return JSON.stringify(data);
    }
};