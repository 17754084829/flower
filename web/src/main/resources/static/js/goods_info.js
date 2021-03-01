$(document).ready(function () {
    $('.table-sort').dataTable({
        "aaSorting": [[6, "asc"]],//默认第几个排序
        "bStateSave": false,//状态保存
        "pading": false,
        "showRowNumber": false,
        "aoColumnDefs": [
            //{"bVisible": false, "aTargets": [ 3 ]} //控制列的隐藏显示
            // {"orderable":false,"aTargets":[0,8]}// 不参与排序的列
        ],
        "ajax": {
            url: "/get_goods_info",
            type: "get",
            dataSrc: function (data) {
                return data.goods;
            },
        },
        "columns": [
            {"data": "goods_name"},
            {
                "data": null,
                "sClass": "text-c",
                "render": function (data, type, row) {
                    return '<a style="text-decoration:none" class="btn btn-link" onClick="show_image(\'' +data.goods_image_url + '\')" href="javascript:;" title="查看">' + '商品图片'+ '</a>'
                }
            },
            {"data": "add_time"},
            {"data": "drop_time"},
            {"data": "number"},
            {"data": "goods_price"},
            {"data": "goods_desc"},
            {"data": "type"},
            {
                "data": null,
                "sClass": "text-c",
                "render": function (data, type, row) {
                    var op = "查看";
                    return '<a style="text-decoration:none" class="btn btn-secondary radius" onClick="showData(\'' + data.id + '\')" href="javascript:;" title="查看">查看</a>' + '<a style="text-decoration:none" class="btn btn-warning radius" onClick="alterData(\'' + data.id + '\')" href="javascript:;" title="修改">修改</a>' +
                        '<a style="text-decoration:none" class="btn btn-danger radius" onClick="delData(\'' + data.id + '\')" href="javascript:;" title="删除">删除</a>';
                }
            }
        ]


    });


    $("#img").on("click", function () {
        $("#exampleInputFile").click();
    });

    $("#exampleInputFile").on("change", function () {
        var formData = new FormData();
        formData.append("file", $("#exampleInputFile")[0].files[0]);
        $.ajax({
            url: "/upload",
            data: formData,
            type: "post",
            dataType: "json",
            processData: false,
            contentType: false,
            success: function (data) {
                if (data.code == 200)
                    swal("提示信息", data.msg, "success", {timer: 3000}).then(function () {
                        $("#img").attr("src", data.image_url + "&time=" + Date())
                    });
                else
                    swal("提示信息", data.msg, "error", {timer: 3000});
            }

        });
    });
    $("#able-00").on("click", function () {
        $("#able-11").removeClass("checked");
        $("#able-00").addClass("checked");
    });
    $("#able-11").on("click", function () {
        $("#able-00").removeClass("checked");
        $("#able-11").addClass("checked");
    });

    $("#type").on("change", function () {
        if ($(this).val() === "add") {
            $("#modal-demo").modal('toggle');
            swal("添加商品类型", {
                buttons: {
                    cancel: "取消",
                    confirm: "确定",
                }
                ,
                content: {
                    element: "input",
                    attributes: {
                        placeholder: "请输入新的商品类型",
                        type: "text",
                    }
                }
            }).then(function (data) {
                if (!strisNotEmpty(data)) {
                    swal("提示信息", "添加失败", "error");
                    modaldemo();
                    return
                }
                $.ajax({
                    url: "/admin/insertType",
                    data: {"type": data},
                    dataType: "json",
                    type: "post",
                    success: function (res) {
                        swal("提示信息",res.msg,"success").then(function () {
                            modaldemo();
                            addOp();
                        })
                    }
                });

            });
        }

    });

    addOp();

    $('.form_datetime').datetimepicker({
      //language:  'fr',
        language : 'zh-CN',
        setStartDate:'2021-01-01',
         //format : 'yyyy-mm-dd hh:ii:ss',//日期格式。可以将日期格式，定成年月日时分秒。
     format : 'yyyy-mm-dd hh:00:00',//日期格式。可以将日期格式，定成年月日时，分秒为0。
                weekStart: 1,
                todayBtn:  1,
                autoclose: 1,
                 todayHighlight: 1,
                 startView: 2,
                 forceParse: 0,
                 showMeridian: 1,
    });

});

/* function news_add() {

    var index = layer.open({
        type: 2,
        title: "添加新闻公告",

        content: "tongzhigonggaoadd.jsp"
    });
    layer.full(index);


} */
$('.form_datetime').datetimepicker({
                language : 'zh-CN',
           //format : 'yyyy-mm-dd hh:ii:ss',//日期格式。可以将日期格式，定成年月日时分秒。
              format : 'yyyy-mm-dd hh:00:00',//日期格式。可以将日期格式，定成年月日时，分秒为0。
           weekStart: 1,
           todayBtn:  1,
           autoclose: 1,
           todayHighlight: 1,
          startView: 2,
            forceParse: 0,
            showMeridian: 1
   });

function showData(id) {
   $.ajax({
       type:"post",
       data:{"id":id},
       url:"/getGoodsById",
       success:function (data) {
           if(data.code==200)
                swal("提示消息",data.msg,"success",{timer:1000}).then(
                    function () {
                        var datas=data.goods;
                        $("#name").val(datas.goods_name);
                        $("#price").val(datas.goods_price);
                        $("#img").attr("src",datas.goods_image_url);
                        if(datas.goods_is_able==1) {
                            $("#able-11").addClass("checked");
                            $("#able-00").removeClass("checked");
                        }else{
                            $("#able-00").addClass("checked");
                            $("#able-11").removeClass("checked");
                        }
                        $("#type").val(datas.type);
                        $("#start_time").val(datas.add_time);
                        $("#end_time").val(datas.drop_time);
                        $("#desc").val(datas.goods_desc);
                        $("#number").val(datas.number);
                        $("#title").html("查看这件商品信息");
                        able();
                        modaldemo();
                    }
                );
           else
               swal("提示消息",data.msg,"error")
       }
   });
}

function delData(id) {
    layer.confirm('确认要删除吗？', function() {
        $.ajax({
            type : 'get',
            data : {
                id : id
            },
            url : '/admin/delGoods',
            success : function(data) {
                layer.msg('已删除!', {
                    icon : 1,
                    time : 2000
                });
                window.location.replace(location.href);
            },
            error : function(data) {
                console.log(data.msg);
            },
        });
    });
}

function show_image(url) {
    $("#show").click();
    $("#show_image").attr("src",url);
}
function add_goods() {
    recover();
    inits();
    $("#bu").click();
}
function modaldemo(){
    $("#modal-demo").modal('toggle');
    $(".modal-backdrop").removeClass("modal-backdrop");
    $(".modal-backdrop").removeClass("fade");
    $(".modal-backdrop").removeClass("in");

}
function modaldemos(){
    $("#add_type").modal('toggle');
    $(".modal-backdrop").removeClass("modal-backdrop");
    $(".modal-backdrop").removeClass("fade");
    $(".modal-backdrop").removeClass("in");

}
function commit() {
    $("#add_type").modal('toggle');
}

function addOp() {
    $.ajax({
        url:"/getTypes",
        dataType:"json",
        success:function (data) {
            var op='<option value="add" selected="">添加新类型</option>';
            for(var index in data.type){
                op+='<option value="'+data.type[index]+'" selected="">'+data.type[index]+'</option>';
            }
            op+='<option value="" selected="">请选择商品类型</option>';
            $("#type").empty();
            $("#type").html(op);
        }
    });
}

function submit() {
    var name=$("#name").val();
    var price=$("#price").val();
    var image_url=$("#img").attr("src");
    var is_able=$(".checked").attr("value");
    var type=$("#type").val();
    var start_time=$("#start_time").val();
    var end_time=$("#end_time").val();
    var desc=$("#desc").val();
    var number=$("#number").val();
    if(!strisNotEmpty(name)){
        swal("提示信息","商品名称不能为空","error");
        return;
    }
    if(!strisNotEmpty(price)){
        swal("提示信息","商品价格不能为空","error");
        return
    }
    if(!strisNotEmpty(number)){
        swal("提示信息","商品数量不能为空","error");
        return;
    }
    if(!strisNotEmpty(is_able)){
        swal("提示信息","请选择是否可用","error");
        return
    }
    if(!strisNotEmpty(type)){
        swal("提示信息","请选择商品类型","error");
        return;
    }
    if(!strisNotEmpty(start_time)){
        swal("提示信息","开始时间不能为空","error");
        return;
    }
    if(!strisNotEmpty(end_time)){
        swal("提示信息","结束时间不能为空","error");
        return;
    }
    if(start_time>end_time){
        swal("提示信息","开始时间不能晚于结束时间","error");
        return;
    }
    if(!strisNotEmpty(desc)){
        swal("提示信息","商品描述不能为空","error");
        return;
    }
    var flag="";
    var id="";
    if($("#title").text()=="修改这件商品信息") {
        flag = "alter";
        id=$("#goodsID").val();
    }
    $.ajax({
        url:"/admin/addGoods",
        type:"post",
        dataType:"json",
        data:{"id":id,"goods_name":name,"goods_price":price,"goods_image_url":image_url,"goods_desc":desc,"goods_is_able":is_able,"add_time":start_time,"drop_time":end_time,"number":number,"type":type,"flag":flag},
        success:function (data) {
            if(data.code==200){
                swal("提示信息",data.msg,"success",{timer:1000}).then(function () {
                    window.location.reload();
                });
            }else
                swal("提示信息",data.msg,"error");
        }
    });
}


function alterData(id) {
    $.ajax({
        type:"post",
        data:{"id":id},
        url:"/getGoodsById",
        success:function (data) {
            if(data.code==200)
                swal("提示消息",data.msg,"success",{timer:1000}).then(
                    function () {
                        var datas=data.goods;
                        $("#name").val(datas.goods_name);
                        $("#price").val(datas.goods_price);
                        $("#img").attr("src",datas.goods_image_url);
                        $("#goodsID").val(datas.id);
                        if(datas.goods_is_able==1) {
                            $("#able-11").addClass("checked");
                            $("#able-00").removeClass("checked");
                        }else{
                            $("#able-00").addClass("checked");
                            $("#able-11").removeClass("checked");
                        }
                        $("#type").val(datas.type);
                        $("#start_time").val(datas.add_time);
                        $("#end_time").val(datas.drop_time);
                        $("#desc").val(datas.goods_desc);
                        $("#number").val(datas.number);
                        $("#title").html("修改这件商品信息");
                        recover();
                        modaldemo();
                    }
                );
            else
                swal("提示消息",data.msg,"error")
        }
    });
}
function able() {
    $("#confirm").removeClass("btn-primary");
    $("#confirm").addClass("disabled");
}
function recover() {
    $("#confirm").addClass("btn-primary");
    $("#confirm").removeClass("disabled");
}
function inits() {
    $("#name").val("");
    $("#price").val("");
    $("#img").attr("src","/image/login.jpg");
    $("#goodsID").val("");
    $("#able-11").addClass("checked");
    $("#able-00").removeClass("checked");
    $("#type").val("");
    $("#start_time").val("");
    $("#end_time").val("");
    $("#desc").val("");
    $("#number").val("");
    $("#title").html("添加商品信息");
}




