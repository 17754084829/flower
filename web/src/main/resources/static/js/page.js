var dataSrc=["/image/1.jpg","/image/2.jpg","/image/3.jpg","/image/4.jpg","/image/5.jpg"];
var indexsrc=1;
var control=undefined;
var timer=undefined;
$(document).ready(function () {
   init();
   loadSearch();
   loadGoods();
});
function loadGoods() {
    $.ajax({
        url:"/getTypes",
        success:function (data) {
            if(data.code==200){
                var datas=data.type;
                for(var i=0;i<datas.length;i++){
                    $.ajax({
                        url:"/getGoodsByType",
                        data:{"type":datas[i],"pageStart":0},
                        success:function (data) {
                            if(data.length==0)
                                return;
                            var html='<div class="row">' +
                                '        <div class="panel panel-info">' +
                                '            <div class="panel-heading">' +
                                '                '+(data[0].type)+'' +
                                '            </div>' +
                                '            <div class="panel-body">' +
                                '                <div id="div'+data[0].type+'" class="list-content">'
                            for(var i=0;i<data.length;i++){
                                html+='<div class="list-item">' +
                                    '                        <div class="item-img">' +
                                    '                            <img src="'+data[i].goods_image_url+'"/>' +
                                    '                        </div>' +
                                    '                        <div class="item-foot">' +
                                    '                            <a href="/getGoods_details?goods_id='+data[i].id+'"><div class="item-title">' +
                                    '                                '+data[i].goods_name+'' +
                                    '                            </div>' +'</a>'+
                                    '                            <div>' +
                                    '                                <div class="item-price">' +
                                    '                                    <span>￥'+data[i].goods_price+'</span>' +
                                    '                                </div>' +
                                    '                                <div class="item-time">' +
                                    '                                    <span>'+data[i].add_time+'</span>' +
                                    '                                </div>' +
                                    '                                <input/>' +
                                    '                            </div>' +
                                    '                        </div>' +
                                    '                    </div>'
                            }
                            html+='          </div>' +
                                '            </div>' +
                                '            <div class="panel-footer" onclick="loadMore(\''+data[0].type+'\')">' +
                                '                >>>>加载更多' +
                                '            </div>';
                            if(data.length<4)
                                html+='<input style="display: none;" id="more'+data[0].type+'" value="-1"/>';
                            else
                                html+='<input style="display: none;" id="more'+data[0].type+'" value="1"/>';
                            html+='        </div>' +
                                '' +
                                '    </div>';
                            $(".container").append(html);

                        }
                    });
                }
            }
        }
    });
}
function loadSearch() {
    $("#in").on("focus",function () {
        timer=window.setInterval(function () {
            if($("#in").val().trim()==""||$("#in").val()==undefined) {
                $("#indiv").addClass("appear");
                return;
            }
            $.ajax({
                url:"/search_key",
                data:{"keyWord":$("#in").val().trim()},
                success:function (data) {
                    $("#indiv").removeClass("appear");
                    var html='';
                    if(data.length==0)
                        html+=' <li class="list-group-item">暂无数据</li>';
                    else{
                        for (var i=0;i<(data.length>=4?4:data.length);i++){
                            html+=' <a href="/getGoods_details?goods_id='+data[i].id+'"><li class="list-group-item">'+data[i].goods_name+'</li></a>';
                        }
                    }
                    $("#rs").html(html);
                }
            });
        },1000);
        $("#in").on("blur",function () {
            window.setTimeout(function () {
                window.clearInterval(timer);
                $("#indiv").addClass("appear");
            },3000);
        });
    });
}
function init() {
    var html='';
    for(var i=0;i<dataSrc.length;i++){
        html+='<span id="'+i+'"></span>';
    }
    $(".back").removeClass("fadepage");
    $(".control").html(html);
    control_swiper();
    $(".control span").on("click",function () {
        var id=this.id;
        pre_src=0;
        if(indexsrc==0)
            pre_src=dataSrc.length-1;
        else
            pre_src=indexsrc-1;
        var pre_id="#"+(pre_src);
        $(pre_id).removeClass("active");
        $("#"+id).addClass("active");
        indexsrc=parseInt(id);
    });


}

function control_swiper() {
    $("#0").addClass("active");
    control=window.setInterval(function () {
        $(".back").addClass("fadepage");
        $(".fadepage").animate({opacity:'+=1'},2000,function () {
            var index=0;
            if(indexsrc==0)
                index=dataSrc.length-1;
            else
                index=indexsrc-1;
            var pre_id="#"+(index);
            $(pre_id).removeClass("active");
            $(".show img").attr("src",dataSrc[indexsrc]);
            var now_id="#"+indexsrc;
            $(now_id).addClass("active");
            indexsrc=(indexsrc+1)%dataSrc.length;
            $(".back").animate({opacity:"-=1"},1000,function () {
                $(".back").removeClass("fadepage")
            });
        });
    },4000);
}


function loadMore(type) {
    var start=parseInt($("#more"+type).val());
    if(start==-1)
        return;
    $.ajax({
        url:"/getGoodsByType",
        data:{"type":type,"pageStart":start},
        success:function (data) {
            if(data.length==0)
                return;
            var html='';
            for(var i=0;i<data.length;i++){
                html+='<div class="list-item">' +
                    '                        <div class="item-img">' +
                    '                            <img src="'+data[i].goods_image_url+'"/>' +
                    '                        </div>' +
                    '                        <div class="item-foot">' +
                    '                            <div class="item-title">' +
                    '                                '+data[i].goods_desc+'' +
                    '                            </div>' +
                    '                            <div>' +
                    '                                <div class="item-price">' +
                    '                                    <span>￥'+data[i].goods_price+'</span>' +
                    '                                </div>' +
                    '                                <div class="item-time">' +
                    '                                    <span>'+data[i].add_time+'</span>' +
                    '                                </div>' +
                    '                                <input/>' +
                    '                            </div>' +
                    '                        </div>' +
                    '                    </div>'
            }
            $("#div"+type).append(html);
            if(data.length<4)
                $("#more"+type).val("-1");
            else
                $("#more"+type).val(""+(start+1));

        }
    })
}