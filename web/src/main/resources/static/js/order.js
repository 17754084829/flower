$(document).ready(function () {

    $.ajax({
        url:'/user/getOrderList',
        success:function(data){
            if(data.code==200){
                var datas=data.data;
                var html='';
                for(var index=0;index<datas.length;index++){
                    var add_time=datas[index].add_time;
                    var addr=datas[index].addr;
                    var all_count=datas[index].all_count;
                    var lis=datas[index].goods_cars[0];
                    html+='<span>订单号:'+datas[index].id+'</span><span style="margin-left: 20px;color: red">总金额:'+all_count+'</span><span style="margin-left: 20px">订单时间'+add_time+'</span><span style="margin-left: 20px">订单地址:'+addr+'</span>';
                    html+=' <ul class="list-group" id="goods_item">';
                    for(var j=0;j<lis.length;j++){
                        html+='                <li class="list-group-item">' +
                            '                    <form class="form-inline">';
                        html+=' <span class="">商品描述</span><span style="margin-left: 10px">'+lis[j].goods_desc+'</span>'+
                            '<span style="margin-left: 10px" class="">商品数量</span><span style="margin-left: 10px">'+lis[j].goods_num+'元</span>'+
                            '<span class="" style="margin-left: 10px">商品单价</span><span style="margin-left: 10px">'+lis[j].goods_price+'元</span>';
                        html+='</form> </li>';
                    }
                    html+='</ul>';
                }
                $(".container").html(html);








            }else
                layer.msg("登录状态异常");
        }
    });



















});


