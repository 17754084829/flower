$.ajax({
    url:"/admin/getAdminInfo",
    type:'post',
    dataType:"json",
    success:function (data) {
        if(data.code==200){
            var dts=$("#left_drop dl dt");
            var dds=$("#left_drop dl dd");
            $("#img").attr("src",data.image_url);
            var dt_list=data.menu_list.dt;
            var dd_list=data.menu_list.dd;
            for(var index in dt_list){
                var obj=dt_list[index];
                for(var i=0;i<obj.size;i++){
                    $(dts[index]).html(obj.arrayList[i])
                }
            }
            for(var index in dd_list){
                var obj=dd_list[index];
                for(var i=0;i<obj.size;i++){
                    $(dds[index]).html(obj.arrayList[i]);
                }
            }
        }
    }
});

$(document).ready(function () {


    $.ajax({
        url:"/admin/getAdminInfo",
        type:'post',
        dataType:"json",
        success:function (data) {
            if(data.code==200){
                var dts=$("#left_drop dl dt");
                var dds=$("#left_drop dl dd");
                $("#img").attr("src",data.image_url);
                var dt_list=data.menu_list.dt;
                var dd_list=data.menu_list.dd;
                for(var index in dt_list){
                    var obj=dt_list[index];
                    for(var i=0;i<obj.size;i++){
                        $(dts[index]).html(obj.arrayList[i])
                    }
                }
                for(var index in dd_list){
                    var obj=dd_list[index];
                    for(var i=0;i<obj.size;i++){
                        $(dds[index]).html(obj.arrayList[i]);
                    }
                }

                $.ajax({
                    url:"/admin/getNotReplyCount",
                    success:function(data){
                        if(data.code==200)
                            $("#msgID").html(data.data);
                    }
                });

            }
        }
    });


});