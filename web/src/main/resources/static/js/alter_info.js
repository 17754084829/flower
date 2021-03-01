$("#img").on("click",function () {
    $("#exampleInputFile").click();
});
$("#exampleInputFile").on("change",function () {
    var formData = new FormData();
    formData.append("file",$("#exampleInputFile")[0].files[0]);
    $.ajax({
       url:"/upload",
       data:formData,
        type:"post",
        dataType:"json",
        processData: false,
        contentType: false,
        success:function (data) {
           if(data.code==200)
                swal("提示信息",data.msg,"success",{timer:3000}).then(function () {
                    $("#img").attr("src",data.image_url+"&time="+Date())
                });
           else
               swal("提示信息",data.msg,"error",{timer:3000});
        }

    });
});
$(window).ready(function () {
   $("#bu").click();
    document.querySelector("input.widget_switch_checkbox").addEventListener("click", function() {
        if(document.querySelector("input.widget_switch_checkbox").checked) {
            $.ajax({
                url:"/admin/able_verify_code",
                type:"post",
                dataType:"json",
                success:function(data){
                    if(data.code==200){
                        swal("提示信息",data.msg,"success",{timer:3000}).then(
                            function () {
                                $("#verify_code").css("display","");
                                $("#verify_code").text("授权码: "+data.verify_code);
                            }
                        );
                    }else{
                        swal("提示信息",data.msg,"error",{timer:3000});
                    }
                }
            });
        }else{
            swal("确定关闭授权码?",{buttons:{
                cancel: "取消",
                    confirm: "确定",
            }}).then(
               function (data) {
                   if(data==null) {
                       document.querySelector("input.widget_switch_checkbox").checked="checked";
                       return;
                   }
                   $.ajax({
                       url:"/admin/shutdown_verify_code",
                       type:"post",
                       dataType:"json",
                       success:function (data) {
                           swal("提示信息",data.msg,"success",{timer:3000}).then(
                               function () {
                                   $("#verify_code").css("display","none");
                               }
                           );
                       }
                   });
               }
            );
        }
    });

    $.ajax({
        url:"/admin/is_able_verify",
        type:"post",
        dataType:"json",
        success:function (data) {
            if(data.able){
                $("input.widget_switch_checkbox").attr("checked","checked");
                $("#verify_code").css("display","");
                $("#verify_code").text(data.verify_code);
            }else{
                $("#verify_code").css("display","none");
            }
        }
    });

    $.ajax({
        url:"/admin/getAdminInfo",
        type:"post",
        dataType:"json",
        success:function (data) {
            if(data.code==200){
                $("#name").val(data.user.user_name);
                $("#password").val(data.user.password);
                $("#email").val(data.user.email);
                $("#show_img").attr("src",data.user.image_url);
                $("#alter_name").val(data.user.user_name);
                $("#alter_password").val(data.user.password);
                $("#alter_confirm_password").val(data.user.password);
                $("#alter_email").val(data.user.email);
                $("#img").attr("src",data.user.image_url);
                $("#account").val(data.user.account);
            }
        }
    });

    $("#next").on("click",function () {
       $("#cancel").click();
       $("#alter_bu").click();
    });



    $("#submit").on("click",function () {
        var name=$("#alter_name").val();
        var password=$("#alter_password").val();
        var email=$("#alter_email").val();
        var image_url=$("#img").attr("src");
        if(!strisNotEmpty(name)) {
            swal("用户名不能为空");
            return;
        }
        if(!strisNotEmpty(password)) {
            swal("密码不能为空");
            return;
        }
        /*if(!strisNotEmpty(confirm_password)) {
            swal("确认密码不能为空");
            return;
        }
        if(password!=confirm_password) {
            swal("两次密码不一致");
            return;
        }*/
        if(!checkMail(email)) {
            swal("电子邮箱不符合格式");
            return;
        }
        $.ajax({
            url:"/admin/update_admin_info",
            type:"post",
            dataType:"json",
            data:{"user_name":name,"password":password,"email":email,"image_url":image_url},
            success:function(data){
                if(data.code==200)
                    swal("提示信息","更新成功","success",{timer:3000}).then(function () {
                        window.parent.location.href="/user/exit";
                    });
            }
        });
    });


});