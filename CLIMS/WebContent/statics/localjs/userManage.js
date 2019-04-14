
//弹出页面，做一些初始化操作
$('.addUser').click(function(e){
	$("#add_formtip").html('');
	e.preventDefault(); //屏蔽默认样式
	$("#addUserDiv").modal('show');
	
});


//检查邮箱格式是否正确
function checkEmail(str){
	var reg = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
	if(str == null || str == "" || reg.test(str))
		return true;
	else
		return false;
}
//检查是否为正确的手机号码
function checkPhone(pattern){
	var pattern = /^1[34578]\d{9}$/;
	return pattern.test(phone);
}

//取消之后需要做一些清空操作
$('.addUserCancel').click(function(e){
	$("#userName").val('');
	$("#userPassword").val('');
	$("#userCode").val('');
	$("#selectRole").val('');
	//$("#selectRole").html('<option value=\"\" selected=\"selected\">--请选择--</option>');
	$("#selectDept").val('');
	//$("#selectDept").html('<option value=\"\" selected=\"selected\">--请选择--</option>');
	$('#add_formtip').html('');
});


//判断用户是否存在，采用异步加载，仅用于增加用户时
$('#userCode').blur(function(){
	var un = $.trim($('#userCode').val());
	if(un!=null && un!=''){
		//异步判断
		$.post("userCodeIsExist.html",{'userCode':un,'id':'-1'},function(result){
			
			if(result == 'repeat'){
				$('#add_formtip').css("color","red");
				$('#add_formtip').html('<li>该学号已存在！</li>');
				$('#add_formtip').attr("key",1);
			}else if(result == 'failed'){
				alert('操作超时！')
			}else if(result == 'only'){
				$('#add_formtip').css("color","green");
				$('#add_formtip').html('<li>该学号可以正常使用  ^v^</li>');
				$('#add_formtip').attr("key",2);
			}
			
			
		},'html');
	}
	else{
		$('#add_formtip').css("color","red");
		$('#add_formtip').html('<li>该学号不合法！！</li>');
		$('#add_formtip').attr("key",1);
	}
});

$("#a_email").blur(function(){
	var flag = checkEmail($("#a_email").val());
	if(flag == false){
		$("#add_formtip").css("color","red");
		$("#add_formtip").html("<li>email格式不正确</li>");
	}else{
		$("#add_formtip").html("");
	}
});

//添加用户信息验证
function addUserFunction(){
	$('add_formtip').html('');
	var result  = true;
	if($.trim($('#userName').val()) == "" ||$('#userName').val() == null){
		$("#add_formtip").css("color","red");
		$("#add_formtip").append("<li>姓名不能为空！</li>");
		result = false;
	}
	if($.trim($('#userCode').val()) == "" ||$('#userCode').val() == null){
		$("#add_formtip").css("color","red");
		$("#add_formtip").append("<li>学号不能为空！</li>");
		result = false;
	}else{//若重名，则不能提交
		if($('#add_formtip').attr("key")=="1"){
			$("#add_formtip").css("color","red");
			$("#add_formtip").append("<li>学号已存在！</li>");
			result = false;
		}
	}
	
	if($.trim($('#userPassword').val()) == "" ||$('#userPassword').val() == null){
		$("#add_formtip").css("color","red");
		$("#add_formtip").append("<li>身份证号不能为空！</li>");
		result = false;
	}else{
		if($.trim($('#userPassword').val()).length < 7){
			$("#add_formtip").css("color","red");
			$("#add_formtip").append("<li>请输入正确的身份证号！</li>");
			result = false;
		}
	}
	//gender
	if($('#gender').val() == "" ||$('#gender').val() == null){
		$("#add_formtip").css("color","red");
		$("#add_formtip").append("<li>性别不能为空！</li>");
		result = false;
	}
	
	//selectRole
	if($('#selectRole').val() == "" ||$('#selectRole').val() == null){
		$("#add_formtip").css("color","red");
		$("#add_formtip").append("<li>角色框为必选！！</li>");
		result = false;
	}
	//selectDept
	if($('#selectDept').val() == "" ||$('#selectDept').val() == null){
		$("#add_formtip").css("color","red");
		$("#add_formtip").append("<li>所在单位框为必选！！</li>");
		result = false;
	}
	
	if(result == true) alert('添加成功 ^_^ ')
	return result;
}















//查看用户信息 start
$('.viewUser').click(function(e){
	var m_id = $(this).attr('id');
	//ajax
	$.ajax({
		url:'viewUser.html',
		type:'POST',
		data:{id:m_id},
		dataType:'json',
		timeout:1000,
		error:function(){
			alert('error');
		},
		success:function(result){
			if(result == "failed"){
				alert('操作超时！');
			}else if(result == "nodata"){
				alert('没有数据传到后台！');
			}else{
				$('#v_userName').text(result.userName);
				$('#v_userCode').text(result.userCode);
				$('#v_userPassword').text(result.userPassword);
				if(result.gender == "1"){
					$('#v_gender').text('男');
				}else if(result.gender == "2"){
					$('#v_gender').text('女');
				}else{
					alert('性别显示有误！')
				}
				$('#v_email').text(result.email);
				$('#v_phone').text(result.phone);
				$('#v_number').text(result.number);
				$('#v_idCard').text(result.idCard);
				$('#v_dept').text(result.dept);
				if(result.userRole == "1"){
					$('#v_userRole').text('系统管理员');
				}else if(result.userRole == "2"){
					$('#v_userRole').text('院系管理员');
				}else if(result.userRole == "3"){
					$('#v_userRole').text('普通使用者');
				}else{
					alert('此用户角色有误！');
				}
				$('#v_creationDate').text(result.creationDate);
				
				e.preventDefault(); //屏蔽默认样式
				$("#viewUserDiv").modal('show');
			}
		}
	});
});
//取消之后需要做一些清空操作
$('.viewUserCancel').click(function(e){
	$('#v_userName').text('');
	$('#v_userCode').text('');
	$('#v_userPassword').text('');
	$('#v_gender').text('');
	$('#v_email').text('');
	$('#v_phone').text('');
	$('#v_dept').text('');
	$('#v_userRole').text('');
	$('#v_creationDate').text('');
});








//修改用户信息 start
$('.perfectUser').click(function(e){
	
	var p_id = $(this).attr('id');
	$.ajax({
		url:'viewUser.html',
		type:'POST',
		data:{id:p_id},
		dataType:'json',
		timeout:1000,
		error:function(){
			alert('error');
		},
		success:function(result){
			if(result == "failed"){
				alert('操作超时！');
			}else if(result == "nodata"){
				alert('没有数据传到后台！');
			}else{
				$('#p_id').val(p_id)
				$('#p_userName').val(result.userName);
				$('#p_userCode').val(result.userCode);
				if(result.gender == "1"){
					$('#p_gender').val('男');
					$('#p_genderNumber').val('1');
				}else if(result.gender == "2"){
					$('#p_gender').val('女');
					$('#p_genderNumber').val('2');
				}else{
					alert('性别字段加载失败！')
				}
				$('#p_email').val(result.email);
				$('#p_phone').val(result.phone);
				$('#p_dept').val(result.dept);
				if(result.userRole == "1" || result.userRole == "2" || result.userRole == "3"){
					$('#p_userRole').val(result.userRole);
				}else{
					alert('加载角色字段失败！');
				}
				e.preventDefault(); //屏蔽默认样式
				$("#perfectUserDiv").modal('show');
			}
		}
		
	});
	
});

//perfectUserCancel

//取消之后需要做一些清空操作
$('.perfectUserCancel').click(function(e){
	$('#p_userName').val('');
	$('#p_userCode').val('');
	$('#p_gender').val('');
	$('#p_email').val('');
	$('#p_phone').val('');
	$('#p_dept').val('');
	$('#p_userRole').val('');
	$('#p_genderNumber').val('');

});

function perfectUserFunction(){
	var result  = true;
	
	
	//p_email checkEmail
	if($.trim($('#p_email').val()).length == 0){
		alert("邮箱不能为空！");
		$('#p_email').focus();
		result = false;
	} else {
		if(checkEmail($.trim($('#p_email').val())) == false){
			alert("邮箱格式不正确！");
			$('#p_email').focus();
			return false;
		}
	}
	
	//p_phone
	if($.trim($('#p_phone').val()).length == 0){
		alert("手机号不能为空！");
		$('#p_phone').focus();
		result = false;
	} else {
		if(checkPhone($.trim($('#p_phone').val())) == false){
			alert("手机号不正确！");
			$('#p_phone').focus();
			return false;
		}
	}
	
	//p_dept
	if($('#p_dept').val() == "" ||$('#p_dept').val() == null){
		alert("所在单位框为必选！");
		result = false;
	}
	
	//userRole
	if($('#p_userRole').val() == "" ||$('#p_userRole').val() == null){
		alert("角色框为必选！");
		result = false;
	}
	
	
	if(result == true) alert('修改成功 ^_^ ');
	return result;
}

$('.deleteUser').click(function(e){
	var d = $(this);
	var d_id = d.attr('id');
	var d_userCode = d.attr('usercode');
	var d_userRole = d.attr('userrole');
	var d_roleName = '';
	if(d_userRole = '1')d_roleName='系统管理员';
	else if(d_userRole = '2')d_roleName='院系管理员';
	else d_roleName='系统管理员';
	
	if(confirm("您确定要删除【"+d_userCode+"】这个用户吗？")){
		$.post("deleteUser.html",{'delId':d_id},function(result){
			if(result == "success"){
				alert('删除成功！');
				window.location.href="userManage.html";
			}else if(result == "noallow"){
				alert('该用户类型为【'+d_roleName+'】，不允许被删除！')
			}else{
				alert('删除失败！')
			}
			
		},'html');
	}
	
});










