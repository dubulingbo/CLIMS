

// 验证中文名称
function isChinaName(name) {
	var pattern = /^[\u4E00-\u9FA5]{1,6}$/;
	return pattern.test(name);
}

// 验证手机号
function isPhoneNo(phone) {

	var pattern = /^1[34578]\d{9}$/;
	return pattern.test(phone);
}

// 验证身份证
function isCardNo(card) {
	var pattern = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;
	return pattern.test(card);
}

//验证邮箱号
function isEmail(sEmail) {
    var pattern = /^(\w)+(\.\w+)*@(\w)+((\.\w+)+)$/;
    return pattern.test(sEmail);
}

//验证学号
function isNumber(number){
	var pattern = /^1\d{10}$/;
	return pattern.test(number);
}


// 验证函数
function modifyInfoFunction(){
	
	$('#modTip').html('');  //将提示框置空
	
	var userName = $.trim($('#userName').val());
	var gender = $.trim($('.gender').val());
	var dept = $.trim($('.dept').val());
	var email = $.trim($('#email').val());
	var number = $.trim($('#number').val());
	var phone = $.trim($('#phone').val());
	var idCard = $.trim($('#idCard').val());
	
	
	alert('userName:'+userName+
		  '\ngender:'+gender+
		  '\ndept:'+dept+
		  '\nemail:'+email+
		  '\nnumber:'+number+
		  '\nphone:'+phone+
		  '\nidCard:'+idCard+
		  '\nmodTip.text:'+modTip);
	var f = true;
	
	// userName
	if (userName.length == 0) {
		$('#modTip').append('<li>姓名不能为空！</li>');
		$('#userName').focus();
		f = false;
	} else {
		if (userName.length > 20) {
			$('#modTip').append('<li>姓名长度过长！</li>');
			$('#userName').focus();
			f = false;
		}
	}
	
	//gender
	if(gender.length == 0){
		$('#modTip').append('<li>性别不能为空！</li>');
		$('.gender').focus();
		f = false;
	}
	
	//dept
	if(dept.length == 0){
		$('#modTip').append('<li>所在单位不能为空！</li>');
		$('.dept').focus();
		f = false;
	}
	
	//number
	if(number.length == 0){
		$('#modTip').append('<li>学号不能为空！</li>');
		$('#number').focus();
		f = false;
	} else {
		if (isNumber(number) == false) {
			$('#modTip').append('<li>请检查学号是否输入正确！</li>');
			$('#number').focus();
			f = false;
		}
	}
	
	//email
	if (email.length == 0) {
		$('#modTip').append('<li>邮箱号不能为空！</li>');
		$('#email').focus();
		f = false;
	} else {
		if (isEmail(email) == false) {
			$('#modTip').append('<li>请输入正确的邮箱！</li>');
			$('#email').focus();
			f = false;
		}
	}
	
	
	//phone
	if (phone.length == 0) {
		$('#modTip').append('<li>手机号不能为空！</li>');
		$('#phone').focus();
		f = false;
	} else {
		if (isPhoneNo(phone) == false) {
			$('#modTip').append('<li>请检查手机号是否输入正确！</li>');
			$('#phone').focus();
			f = false;
		}
	}
	
	//idCard
	if (idCard.length == 0) {
		$('#modTip').append('<li>身份证号码不能为空！</li>');
		$('#idCard').focus();
		f = false;
	} else {
		if (isCardNo(idCard) == false) {
			$('#modTip').append('<li>请输入正确的身份证号码！</li>');
			$('#idCard').focus();
			f = false;
		}
	}


	// 如果没有错误则提交
	if (!f) {
		//e.preventDefault(); //屏蔽默认样式
		$("#modTipDiv").modal('show');
		return false;
	} else {
		//e.preventDefault(); //屏蔽默认样式
		//"mustTurePhone"
//		$("#mustTureUserName").val(userName);
//		$("#mustTureGender").val(gender);
//		$("#mustTureDept").val(dept);
//		$("#mustTurePhone").val(phone);
//		$("#mustTureEmail").val(email);
//		$("#mustTureNumber").val(number);
//		$("#mustTureIdCard").val(idCard);
//		
//		$("#userName").val('');
//		$(".gender").val('');
//		$(".dept").val('');
//		$("#phone").val('');
//		$("#email").val('');
//		$("#number").val('');
//		$("#idCard").val('');
		//$('#modTip').html('<h3>所有更改已提交 ^_^ </h3>');
		//$("#modTipDiv").modal('show');
		alert('所有更改已提交 ^_^ ');
		return true;
		//$('.modifyInfoForm').submit();
	}
	
}

//取消时将提示框中的信息置空
$('.modifyInfoCancel').click(function(e){
	$('#modTip').html('');
});


