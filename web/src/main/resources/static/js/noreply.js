$(document).ready(function () {

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
            url: "/admin/getNotReply",
            type: "get",
            dataSrc: function (data) {
                return data.data;
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
            {"data": "user_name"},
            {
                "data": null,
                "sClass":"text-c",
                "render":function (data,type,row) {
                    var content=data.content;
                    return '<button type="button" onclick="active()" class="btn btn-default radius" title="" data-container="body" data-toggle="popover" data-placement="top" data-content="'+content+'" data-original-title="留言内容" aria-describedby="popover951271">查看留言内容</button>';
                }
            },
            {
                "data": null,
                "sClass": "text-c",
                "render": function (data, type, row) {
                    var op = "回复";
                    return '<a style="text-decoration:none" class="btn btn-secondary radius" onClick="reply(\''+data.id+'\',\'' + data.group_id + '\',\''+data.content_subject_id+'\',\''+data.user_name+'\',\''+data.goods_id+'\')" href="javascript:;" title="回复">回复</a>';
                }
            }
        ]


    });





});


function show_image(url) {
    $("#show").click();
    $("#show_image").attr("src",url);
}


function reply(id,group_id,content_object_id,user_name,goods_id) {

    swal({
        buttons:{
            cancel: "取消",
            confirm: "回复",
        }
        ,
        content: {
            element: "input",
            attributes: {
                placeholder: "回复:"+user_name,
                type: "text",
            },
        },
    }).then(function (text) {
        if(!strisNotEmpty(text)){
            return
        }


        $.ajax({
            url:"/admin/addContent",
            type:"post",
            data:{"content":text,"goods_id":goods_id.trim(),"content_object_id":content_object_id.trim(),"group_id":group_id.trim(),"id":id.trim()},
            success:function (data){
                if(data.code==200){
                    swal("提示信息","留言成功","success",{timer:1000}).then(function () {
                        window.parent.location.reload();
                    });
                }else{
                    var msg="";
                    if(data.msg==undefined)
                        msg="请登录后留言";
                    else
                        msg=data.msg;
                    swal("提示信息",msg,"error",{timer:2000});
                }
            }
        });



    });

}

function modaldemos(){
    $("#add_type").modal('toggle');
    $(".modal-backdrop").removeClass("modal-backdrop");
    $(".modal-backdrop").removeClass("fade");
    $(".modal-backdrop").removeClass("in");

}
var flag=false;
function active() {
    if(!flag) {
        $("[data-toggle='popover']").popover();
        flag=true;
    }
    else
        return;
}
