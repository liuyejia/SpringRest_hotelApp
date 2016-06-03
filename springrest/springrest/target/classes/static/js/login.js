//检查空值
function checkEmpty(str) {
	if (str == null || str.replace(/^\s+,""/).replace(/^\s+$/, "") == "") {
		return true;
	} else {
		return false;
	}
}
// 用户登录验证表单
$("#loginbtn").click(function() {
	
	$("#loginerror").empty();
	var msg="";
	if(checkEmpty($("#account").val())) 
		msg="账号不能为空!";
	else if(checkEmpty($("#password").val()))
		msg="密码不能为空";
	if(msg==""){
		$("#loginform").submit();
	}
	else{
		$("#login-alert").attr("style", "");
		$("#login-alert").html("<p>"+msg+"</P>");
	}
});