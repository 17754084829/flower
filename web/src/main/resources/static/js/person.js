$(document).ready(function () {

    load();
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
        url:"/user/getUserById",
        success:function (data) {
            if(data.code==200)
                swal("提示消息",data.msg,"success",{timer:1000}).then(
                    function () {
                        var datas=data.data;
                        $("#name").val(datas.user_name);
                        $("#img").attr("src",datas.image_url);
                        if(datas.is_able==1) {
                            $("#able-11").addClass("checked");
                            $("#able-00").removeClass("checked");
                        }else{
                            $("#able-00").addClass("checked");
                            $("#able-11").removeClass("checked");
                        }
                        $("#password").val(datas.password);
                        $("#number").val(datas.account);
                        $("#type").val(datas.is_admin);
                        $("#email").val(datas.email);
                        $("#title").html("查看会员信息");
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
            url : '/user/delUser',
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


function submit() {
    var name=$("#name").val();
    var image_url=$("#img").attr("src");
    var is_able=$(".checked").attr("value");
    var is_admin=$("#type").val();
    var password=$("#password").val();
    var email=$("#email").val();
    var account=$("#number").val();
    if(!strisNotEmpty(name)){
        swal("提示信息","会员名称不能为空","error");
        return;
    }
    if(!strisNotEmpty(is_admin)){
        swal("提示信息","请选择会员类型","error");
        return
    }
    if(!strisNotEmpty(password)){
        swal("提示信息","密码不能为空","error");
        return;
    }
    if(!strisNotEmpty(email)){
        swal("提示信息","邮箱不能为空","error");
        return
    }
    if(!strisNotEmpty(account)){
        swal("提示信息","余额不能为空","error");
        return;
    }
    var flag="";
    var id="";
    if($("#title").text()=="修改会员信息") {
        flag = "alter";
        id=$("#goodsID").val();
    }
    var url="";
    if(flag==="alter")
        url="/user/updatePerson";
    else
        url="/user/addUser";
    $.ajax({
        url:url,
        type:"post",
        dataType:"json",
        data:{"id":id,"user_name":name,"image_url":image_url,"is_able":is_able,"account":account,"is_admin":is_admin,"password":password,"email":email},
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
        url:"/user/getUserById",
        success:function (data) {
            if(data.code==200)
                swal("提示消息",data.msg,"success",{timer:1000}).then(
                    function () {
                        var datas=data.data;
                        $("#name").val(datas.user_name);
                        $("#img").attr("src",datas.image_url);
                        $("#goodsID").val(datas.id);
                        if(datas.is_able==1) {
                            $("#able-11").addClass("checked");
                            $("#able-00").removeClass("checked");
                        }else{
                            $("#able-00").addClass("checked");
                            $("#able-11").removeClass("checked");
                        }
                        $("#type").val(datas.is_admin);
                        $("#number").val(datas.account);
                        $("#password").val(datas.password);
                        $("#email").val(datas.email);
                        $("#title").html("修改会员信息");
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
    $("#name").val();
    $("#img").attr("src","/image/login.jpg");
    $("#goodsID").val("");
    $("#able-11").addClass("checked");
    $("#able-00").removeClass("checked");
    $("#type").val("");
    $("#password").val();
    $("#number").val();
    $("#title").html("添加会员信息");
}

function load() {
    $('.table-sort').dataTable({
        "aaSorting": [[3, "asc"]],//默认第几个排序
        "bStateSave": false,//状态保存
        "pading": false,
        "showRowNumber": false,
        "aoColumnDefs": [
            //{"bVisible": false, "aTargets": [ 3 ]} //控制列的隐藏显示
            // {"orderable":false,"aTargets":[0,8]}// 不参与排序的列
        ],
        "ajax": {
            url: "/user/getAlluser",
            type: "get",
            dataSrc: function (data) {
                return data;
            },
        },
        "columns": [
            {"data": "user_name"},
            {
                "data": null,
                "sClass": "text-c",
                "render": function (data, type, row) {
                    return '<a style="text-decoration:none" class="btn btn-link" onClick="show_image(\'' +data.image_url + '\')" href="javascript:;" title="查看">' + '会员头像'+ '</a>'
                }
            },
            {"data": "email"},
            {"data": "password"},
            {"data": "account"},
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

}




