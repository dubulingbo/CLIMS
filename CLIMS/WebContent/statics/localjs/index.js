$("#loginBtn").click(function(){
	var user = new Object();
	user.userCode = $.trim($("#userCode").val());
    user.userPassword = $.trim($("#userPassword").val());
    if(user.userCode == "" || user.userCode == null){
    	$("#userCode").focus();
    	$("#formtip").css("color","red");
    	$("#formtip").html("账号不能为空。");
    }else if(user.userPassword == "" || user.userPassword == null){
    	$("#userPassword").focus();
    	$("#formtip").css("color","red");
    	$("#formtip").html("密码不能为空。");
    }else{
    	$("#formtip").html("");
    	$.ajax({
    		type:'POST',
    		url:'/login.html',
    		data:{user:JSON.stringify(user)},
    		dataType:'html',
    		error:function(){
    			$("#formtip").css("color","red");
    	    	$("#formtip").html("登录失败！请重试。");
    		},
    		success:function(result){
    			if(result == "success"){//若登录成功，跳转到"main.html"
    				window.location.href='/main.html';
    			}else if(result == "failed"){
    				$("#formtip").css("color","red");
        	    	$("#formtip").html("登录失败，系统错误！");
        	    	$("#userCode").val('');
        	    	$("#userPassword").val('');
    			}else if(result == "nologincode"){
    				$("#formtip").css("color","red");
        	    	$("#formtip").html("登录账号不存在！请重试。");
    			}else if(result == "pwderror"){
    				$("#formtip").css("color","red");
        	    	$("#formtip").html("登录密码错误！请重试。");
    			}else if("nodata" == result){
    				$("#formtip").css("color","red");
        	    	$("#formtip").html("对不起，没有任何数据需要处理！请重试。");
    			}else{
    				$("#formtip").css("color","red");
        	    	$("#formtip").html("登录失败，未知原因!");
    			}
    		}
    	});
    }
});