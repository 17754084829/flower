var car_id=undefined;
function addcar(){
    var cart = $("#cart").offset();
    var cartx = cart.left;
    var carty = cart.top;
    var clone = $("#item").clone();
    clone.appendTo('#items');
    clone.css({
        "position": "absolute",
        "left": $("#item").offset().left,
        "top": $("#item").offset().top
    }).addClass('addAnim');
    // clone.removeClass('addAnim');
    clone.animate({
        top: carty - 125,
        left: cartx - 95
    }, 300,function () {

    });
    var url=""
    var data={};
    if(car_id==undefined){
        url="/admin/addGoodsCar";
        data.goods_id=$("#goods_id").val().trim();
    }
    else{
        url="/admin/addCarnum";
        data.id=car_id;
    }
    data.goods_num=parseInt($("#car_num").html()) + 1;
    $.ajax({
       url:url,
        type:'post',
        data:data,
        success:function (data) {
            if(data.code==200){
                $("#car_num").html(parseInt($("#car_num").html()) + 1);
                if(car_id==undefined)
                    layer.open({
                        title: '提示信息'
                        ,content: '商品已加入购物车',
                    });
                if(data.id!=undefined)
                    car_id=data.id;
            }else{
                swal("提示信息","添加到购物车失败,请检查下登录状态","error");
            }
        }
    });
}

$(document).ready(function () {
    loadContent(0);

    $.ajax({
        url:"/admin/initGoodsCar",
        data:{"goods_id":$("#goods_id").val().trim()},
        success:function (data) {
            if(data.code==200){
                if(data.data.id==undefined||data.data.id=="")
                    return;
                $("#car_num").html(data.data.goods_num);
                car_id=data.data.id;
            }else{
                swal("提示信息","转态异常","error");
            }
        }
    });

});


function pre() {
    var val=parseInt($("#preValue").val());
    loadContent(val);
}
function next() {
    var val=parseInt($("#nextValue").val());
    loadContent(val);
}



function loadContent(start) {
    $.ajax({
        url:"/getContentList",
        type:"post",
        data:{"pageStart":start,"goods_id":$("#goods_id").val().trim()},
        success:function(data){
            if(!data.hasNext)
                $("#next").addClass("disabled");
            else{
                $("#next").removeClass("disabled");
                $("#nextValue").val(data.nextPage);
            }

            if(!data.hasPre)
                $("#pre").addClass("disabled");
            else{
                $("#pre").removeClass("disabled");
                $("#preValue").val(data.prePage);
            }
            var html='';
            if(data.data.length<=0)
                html+='<p class="container" style="text-align: center">~~~~空空如也~~~~~</p>';
            else
                for(var index=0;index<data.data.length;index++){
                    if(index==0){
                        html+='<li class="item cl"> <a href="#"><i class="avatar size-L radius"><img alt="" src="'+data.data[index].image_url+'"></i></a>' +
                            '                            <div class="comment-main">' +
                            '                                <header class="comment-header">' +
                            '                                    <div class="comment-meta"><a class="comment-author" href="#">'+data.data[index].user_name+'</a> 评论于' +
                            '                                        <time title="" datetime="2014-08-31T03:54:20">'+data.data[index].add_time+'</time>' +
                            '                                    </div>' +
                            '                                </header>' +
                            '                                <div class="comment-body">' +
                            '                                    <p>'+data.data[index].content+'</p>' +
                            '                                    <button onclick="reply(\''+data.data[index].id+'\',\''+data.data[index].group_id+'\',\''+data.data[index].content_subject_id+'\',\''+data.data[index].user_name+'\')" class="btn btn-primary" style="float: right">回复</button>' +
                            '<input style="display: none;clear: both"/>'+
                            '                                </div>' +
                            '                            </div>' +
                            '                        </li>';
                    }else{
                        html+=' <li class="item cl comment-flip"> <a href="#"><i class="avatar size-L radius"><img alt="" src="'+data.data[index].image_url+'"></i></a>' +
                            '                            <div class="comment-main">' +
                            '                                <header class="comment-header">' +
                            '                                    <div class="comment-meta"><a class="comment-author" href="#">'+data.data[index].user_name+'</a>-><a class="comment-author" href="#">'+data.data[index].object_name+'</a>回复于' +
                            '                                        <time title="" datetime="2014-08-31T03:54:20">'+data.data[index].add_time+'</time>' +
                            '                                    </div>' +
                            '                                </header>' +
                            '                                <div class="comment-body">' +
                            '                                    <p>'+data.data[index].content+'</p>' +
                            '                                    <button onclick="reply(\''+data.data[index].id+'\',\''+data.data[index].group_id+'\',\''+data.data[index].content_subject_id+'\',\''+data.data[index].user_name+'\')" class="btn btn-primary"  style="float: right">回复</button>' +
                            '<input style="display: none;clear: both"/>'+
                            '                                </div>' +
                            '                            </div>' +
                            '                        </li>';
                    }
                }
                $("#content_body").empty();
                $("#content_body").html(html);

        }
    });

}


function add_content() {
    swal({
        buttons:{
            cancel: "取消",
            confirm: "确定留言",
        }
        ,
        content: {
            element: "input",
            attributes: {
                placeholder: "快来说些话吧",
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
            data:{"content":text,"goods_id":$("#goods_id").val().trim()},
            success:function (data){
                if(data.code==200){
                    swal("提示信息","留言成功","success",{timer:1000}).then(function () {
                        loadContent(0);
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



function reply(id,group_id,content_object_id,user_name) {

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
            data:{"content":text,"goods_id":$("#goods_id").val().trim(),"content_object_id":content_object_id.trim(),"group_id":group_id.trim(),"id":id.trim()},
            success:function (data){
                if(data.code==200){
                    swal("提示信息","留言成功","success",{timer:1000}).then(function () {
                        loadContent(0);
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


function gotoCar() {
    window.location.href="/admin/getGoodsCars.html";
}

