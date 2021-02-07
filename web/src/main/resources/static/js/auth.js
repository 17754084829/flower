var Auth = {
	vars: {
		lowin: document.querySelector('.lowin'),
		lowin_brand: document.querySelector('.lowin-brand'),
		lowin_wrapper: document.querySelector('.lowin-wrapper'),
		lowin_login: document.querySelector('.lowin-login'),
		lowin_wrapper_height: 0,
		login_back_link: document.querySelector('.login-back-link'),
		forgot_link: document.querySelector('.forgot-link'),
		login_link: document.querySelector('.login-link'),
		login_btn: document.querySelector('.login-btn'),
		register_link: document.querySelector('.register-link'),
		password_group: document.querySelector('.password-group'),
		password_group_height: 0,
		lowin_register: document.querySelector('.lowin-register'),
		lowin_footer: document.querySelector('.lowin-footer'),
		box: document.getElementsByClassName('lowin-box'),
		option: {}
	},
	register:function(e) {
		Auth.vars.lowin_login.className += ' lowin-animated';
		setTimeout(function () {
			Auth.vars.lowin_login.style.display = 'none';
		}, 500);
		Auth.vars.lowin_register.style.display = 'block';
		Auth.vars.lowin_register.className += ' lowin-animated-flip';

		Auth.setHeight(Auth.vars.lowin_register.offsetHeight + Auth.vars.lowin_footer.offsetHeight);

		e.preventDefault();
	},
	login:function(e) {
		Auth.vars.lowin_register.classList.remove('lowin-animated-flip');
		Auth.vars.lowin_register.className += ' lowin-animated-flipback';
		Auth.vars.lowin_login.style.display = 'block';
		Auth.vars.lowin_login.classList.remove('lowin-animated');
		Auth.vars.lowin_login.className += ' lowin-animatedback';
		setTimeout(function (){
			Auth.vars.lowin_register.style.display = 'none';
		}, 500);
		
		setTimeout(function () {
			Auth.vars.lowin_register.classList.remove('lowin-animated-flipback');
			Auth.vars.lowin_login.classList.remove('lowin-animatedback');
		},1000);

		Auth.setHeight(Auth.vars.lowin_login.offsetHeight + Auth.vars.lowin_footer.offsetHeight);

		e.preventDefault();
	},
	forgot:function(e) {
		Auth.vars.password_group.classList += ' lowin-animated';
		Auth.vars.login_back_link.style.display = 'block';

		setTimeout(function(){
			Auth.vars.login_back_link.style.opacity = 1;
			Auth.vars.password_group.style.height = 0;
			Auth.vars.password_group.style.margin = 0;
		}, 100);
		
		Auth.vars.login_btn.innerText = 'Forgot Password';

		Auth.setHeight(Auth.vars.lowin_wrapper_height - Auth.vars.password_group_height);
		Auth.vars.lowin_login.querySelector('form').setAttribute('action', Auth.vars.option.forgot_url);

		e.preventDefault();
	},
	loginback:function(e) {
		Auth.vars.password_group.classList.remove('lowin-animated');
		Auth.vars.password_group.classList += ' lowin-animated-back';
		Auth.vars.password_group.style.display = 'block';

		setTimeout(function(){
			Auth.vars.login_back_link.style.opacity = 0;
			Auth.vars.password_group.style.height = Auth.vars.password_group_height + 'px';
			Auth.vars.password_group.style.marginBottom = 30 + 'px';
		}, 100);

		setTimeout(function() {
			Auth.vars.login_back_link.style.display = 'none';
			Auth.vars.password_group.classList.remove('lowin-animated-back');
		}, 1000);

		Auth.vars.login_btn.innerText = 'Sign In';
		Auth.vars.lowin_login.querySelector('form').setAttribute('action', Auth.vars.option.login_url);

		Auth.setHeight(Auth.vars.lowin_wrapper_height);
		
		e.preventDefault();
	},
	setHeight:function(height) {
		Auth.vars.lowin_wrapper.style.minHeight = height + 'px';
	},
	brand:function() {
		Auth.vars.lowin_brand.classList += ' lowin-animated';
		setTimeout(function(){
			Auth.vars.lowin_brand.classList.remove('lowin-animated');
		}, 1000);
	},
	init:function(option) {
		Auth.setHeight(Auth.vars.box[0].offsetHeight + Auth.vars.lowin_footer.offsetHeight);

		Auth.vars.password_group.style.height = Auth.vars.password_group.offsetHeight + 'px';
		Auth.vars.password_group_height = Auth.vars.password_group.offsetHeight;
		Auth.vars.lowin_wrapper_height = Auth.vars.lowin_wrapper.offsetHeight;

		Auth.vars.option = option;
		Auth.vars.lowin_login.querySelector('form').setAttribute('action', option.login_url);

		var len = Auth.vars.box.length - 1;

		for(var i = 0; i <= len; i++) {
			if(i !== 0) {
				Auth.vars.box[i].className += ' lowin-flip';
			}
		}

		Auth.vars.forgot_link.addEventListener("click", function(e){
			swal({
					buttons:{
						cancel: "取消",
						confirm: "确定",
					}
					,
					content: {
						element: "input",
						attributes: {
							placeholder: "请输入接受邮件的邮箱号",
							type: "text",
						},
					},
				}
			).then(function (data) {
				deal_email(data);
			});
			//Auth.forgot(e);
		});

		Auth.vars.register_link.addEventListener("click", function(e){
			Auth.brand();
			Auth.register(e);
		});

		Auth.vars.login_link.addEventListener("click", function (e){
			Auth.brand();
			Auth.login(e);
		});

		Auth.vars.login_back_link.addEventListener("click", function(e){
			Auth.loginback(e);
		});
	}
};
var alert_name=undefined;
var alert_password=undefined;
var  alert_confirm_password=undefined;

function checkMail(val){
	var email = val;
	var reg = /^([a-zA-Z]|[0-9])(\w|\-)+@[a-zA-Z0-9]+\.([a-zA-Z]{2,4})$/;
	if(reg.test(email)){
		return true;
	}else{
		return false;
	}
}
function strisNotEmpty(obj) {
	if(obj==null || obj==undefined || obj=="")
		return false;
	else
		return true;
}
function check_submit() {
	var userName=$("#userName").val();
	var password=$("#password").val();
	if(userName.indexOf("@")!=-1){
		if(!checkMail(userName)) {
			swal("邮箱格式错误");
			return false;
		}
	}else {
		if(!strisNotEmpty(userName)) {
			swal("账号不能为空");
			return false;
		}
	}
	if(!strisNotEmpty(password)){
		swal("密码不能为空");
		return false;
	}
	$.ajax({
		url:"/user/login",
		data:{"userName":userName,"password":password},
		type:"post",
		dataType:"json",
		success:function (data) {
			if(data.code==200){
				swal("提示信息",data.msg,"success");
				window.location="/admin/index";
			}else
				swal("提示信息",data.msg,"error");
		}
	});
	return false;
}

function register_user() {
	var user_Name=$("#name").val();
	var password=$("#re_password").val();
	var  confirm_password=$("#confirm_password").val();
	var email=$("#email").val();
	if(user_Name==undefined||user_Name==null||user_Name.trim()=="") {
		swal("用户名不能为空");
		return false;
	}
	if((password==null||password==undefined||password=="")||(confirm_password==null||confirm_password==undefined||confirm_password=="")) {
		swal("密码不能为空")
		return false;
	}
	else
		if(password!=confirm_password) {
			swal("两次密码不一致");
			return false;
		}
	if(!checkMail(email)) {
		swal("邮箱不符合格式");
		return false;
	}
	$.ajax({
		url:"/user/addUser",
		data:{"username":user_Name,"password":password,"email":email,"is_able":1,"id":"1","image_url":"/image/login.jpg","role":"1","verify_code":"1","user_name":user_Name},
		dataType:"json",
		type:"post",
		success:function (data) {
			if(data.code==200){
				swal("提示信息","注册成功","success");
				window.location.href="/login";
			}else{
				swal("提示信息",data.msg,"error");
				window.location.reload();
			}
		}
	});
	return false;

}
var name_timer=undefined;
var email_timer=undefined;
$("#name").on("focus",function () {
	name_timer=window.setInterval(verify_username,2000);
});
$("#name").on("blur",function () {
	window.clearInterval(name_timer);
	verify_username();
});
function verify_username() {
	var user_name=$("#name").val();
	if(!strisNotEmpty(user_name))
		return;
	$.ajax({
		url:"/user/verify_user_name",
		type:"post",
		dataType:"json",
		data:{"user_name":user_name},
		success:function (data) {
			if(data.code==200) {
				$("#name").removeClass("verify_false");
				$("#name").addClass("verify_true");
			}else {
				$("#name").removeClass("verify_true");
				$("#name").addClass("verify_false");
			}
		}
	});
}
function verify_email() {
	var user_name=$("#email").val();
	if(!strisNotEmpty(user_name))
		return;
	if(!checkMail(user_name)) {
		$("#email").addClass("verify_false");
		$("#email").removeClass("verify_true");
		return false;
	}
	$.ajax({
		url:"/user/verify_user_name",
		type:"post",
		dataType:"json",
		data:{"user_name":user_name},
		success:function (data) {
			if(data.code==200) {
				$("#email").removeClass("verify_false");
				$("#email").addClass("verify_true");
			}else {
				$("#email").removeClass("verify_true");
				$("#email").addClass("verify_false");
			}
		}
	});
}

$("#email").on("focus",function () {
	email_timer=window.setInterval(verify_email,2000);
});
$("#email").on("blur",function () {
	window.clearInterval(email_timer);
	verify_email();
});


function deal_email(value){
	if(!strisNotEmpty(value)){
		swal(
			"提示信息",
			"请输入您的邮箱地址",
			"error",{
				error:2000,
			}
		)
	}else{
		if(!checkMail(value))
			swal(
				"提示信息",
				"请输入正确的邮箱地址",
				"error",{
					timer:3000,
				}
			);
		else {
			$.ajax({
				url: "/sendMail",
				dataType: "json",
				type: "post",
				data:{"email":value},
				success:function (data) {
					if(data.code==200){
						swal(
							"提示信息",
							"您的验证码已发送至邮箱",
							"success", {
								timer: 3000,
							}
						).then(function () {
							swal(
								{
									buttons:{
										cancel: "取消",
										confirm: "确定",
									}
									,
									content: {
										element: "input",
										attributes: {
											placeholder: "请输入验证码",
											type: "text",
										},
									},
								}
							).then(function (data) {
								if(!strisNotEmpty(data))
									swal("提示信息","验证码不能为空","error",{timer:3000});
								else{
								$.ajax({
									url:"/verifyCode",
									data:{"code":data},
									dataType:"json",
									type:"post",
									success:function (rs) {
										if(rs.code==200){
											swal("提示信息",rs.msg,"success",{timer:3000}).then(
												function () {
													swal({buttons:{
															cancel: "取消",
															confirm: "下一步",
														}
														,
														content: {
															element: "input",
															attributes: {
																placeholder: "请输入需要改变的账号",
																type: "text",
															},
														},}).then(function (data) {
															if(!strisNotEmpty(data)){
																swal("提示信息",'账号不能为空',"error",{timer:3000});
																return;
															}
															alert_name=data;
														swal({
															buttons:{
																cancel: "取消",
																confirm: "下一步",
															}
															,
															content: {
																element: "input",
																attributes: {
																	placeholder: "请输入修改的密码",
																	type: "text",
																},
															},
														}).then(function (data) {
															if(!strisNotEmpty(data)){
																swal("提示信息",'密码不能为空',"error",{timer:3000});
																return;
															}
															alert_password=data;
															swal({
																buttons:{
																	cancel: "取消",
																	confirm: "确定修改",
																}
																,
																content: {
																	element: "input",
																	attributes: {
																		placeholder: "请输入确认修改的密码",
																		type: "text",
																	},
																},
															}).then(function (data) {
																if(!strisNotEmpty(data)){
																	swal("提示信息",'确认不能为空',"error",{timer:3000});
																	return;
																}
																alert_confirm_password=data;
																if(alert_password!=alert_confirm_password){
																	swal("提示信息",'两次密码不一致',"error",{timer:3000});
																	return;
																}
																$.ajax({
																	url:"/user/alter_password",
																	dataType:"json",
																	type:"post",
																	data:{"user_name":alert_name,"new_password":alert_password},
																	success:function (data) {
																		if(data.code==200){
																			swal("提示信息",data.msg,"success",{timer:3000}).then(
																				function () {
																					window.location.href="/login";
																				}
																			);
																		}else{
																			swal("提示信息",data.msg,"error",{timer:3000});
																		}
																	}
																});
															});

														});
													});
												}
											);
										}else{
											swal("提示信息",rs.msg,"error",{timer:3000});
										}
									}
								})
								}

							})
						})
					}else{
						swal("提示信息",data.msg,"error",{timer:3000});
					}
				}
			});
		}
	}
}