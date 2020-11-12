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
        var result=[];
        var fieldInfo={};
        var data = {};
        var t = $('#'+domId+'').serializeArray();
        $.each(t, function() {
            fieldInfo[this.name]=$('#'+this.name+'').attr("dataType");
            data [this.name] = this.value;
        });
        result.push(data);
        result.push(fieldInfo);
        return JSON.stringify(result);
    },
    //关闭跳出层
    doCloseLayer:function () {
        var index = parent.layer.getFrameIndex(window.name);
        top.layer.close(index);
    },
    //删除数据信息
    delSingleRow:function (domId,row,url) {
        layer.confirm('您确定要删除数据吗?',{btn: ['确定', '取消'],title:"提示",skin: 'layui-layer-molv',icon: 3}, function(){
             var params={
                 "data":JSON.stringify(row)
             };
            dealObj.doAjax(url,params,function (json) {
                if(json.status){
                    layer.alert("删除成功！", {skin: 'layui-layer-molv',icon: 1});
                    dealObj.doQuery(domId);//再次执行查询
                }else{
                    layer.alert("删除失败！", {skin: 'layui-layer-molv',icon: 0});
                }
            });
        });
    },
    //删除多行数据信息
    delMultiRows:function (domId,url) {
        // 获取当前行
        var rows=$('#'+domId+'').bootstrapTable('getSelections');
        if(rows.length==0){
            layer.alert("请选择数据信息", {skin: 'layui-layer-molv',icon: 0});
            return false;
        }
        layer.confirm('您确定要删除数据吗?',{btn: ['确定', '取消'],title:"提示",skin: 'layui-layer-molv',icon: 3}, function(){
            var params={
                "data":JSON.stringify(rows)
            };
            dealObj.doAjax(url,params,function (json) {
                if(json.status){
                    layer.alert("删除成功！", {skin: 'layui-layer-molv',icon: 1});
                    this.doQuery();
                }else{
                    layer.alert("删除失败！", {skin: 'layui-layer-molv',icon: 0});
                }
            });
        });
    },
    //列表新增信息
    addList:function (url,widthX,widthY,title) {
        dealObj.doOpenLayer(url,widthX,widthY,title,function () {
            layer.alert("保存成功！", {skin: 'layui-layer-molv',icon: 1});
            top.dealObj.info=undefined;
            dealObj.doQuery();
        });
    },
    //form表单新增信息
    addForm:function (domId,url) {
        var data=dealObj.getFormData(domId);
        //数据传递到后台
        var params={
            "data":data
        };
        dealObj.doAjax(url,params,function (json) {
            if(json.status){
                top.dealObj.info="success";
                dealObj.doCloseLayer();
            }else{
                layer.alert("保存失败！", {skin: 'layui-layer-molv',icon: 0});
            }
        });
    }
};