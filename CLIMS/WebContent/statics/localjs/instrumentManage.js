//resetQueryInstrument
$('#resetQueryInstrument').click(function(){
	alert($('.s_classNo').val()+" "+$('.s_dept').val());
	$('#s_instrumentName').val('');
	$('#s_instrumentNo').val('');
	$('.s_classNo').val('');
	$('.s_dept').val('');
});


//queryInstrument
function _queryInstrumentFunction(){
	//先判断是否都为空
	var instrumentName = $.trim($('#s_instrumentName').val());
	var instrumentNo = $.trim($('#s_instrumentNo').val());
	var classNo = $.trim($('.s_classNo').val());
	var dept = $.trim($('.s_dept').val());
	if(instrumentName.length==0 && instrumentNo.length==0 && 
			classNo.length==0 && dept.length==0){
		alert('请输入信息后再查询！');
		return false;
	}else{
		return true;
	}
}











//清空信息框
function init(){
	//instrumentName
	$('#a_instrumentName').val('');
	//instrumentType
	$('#a_instrumentType').val('');
	//classNo
	$('.a_classNo').val('');
	//country
	$('.a_country').val('');
	//price
	$('#a_h_price').val('0.00');
	$('#a_int_price').val('');
	$('#a_float_price').val('00');
	//manufacturer
	$('#a_manufacturer').val('');
	//productionDate
	$('#a_productionDate').val('');
	//number
	$('#a_number').html('');
	$('#a_number').append('<option selected="selected" value="1"> 1</option>');
	for(var i=2;i<11;i++){
		$('#a_number').append('<option  value="'+i+'"> '+i+'</option>');
	}
	//note
	$('#a_note').html('');
	
	$('#addInstrumentTip').html('');
}

$('.addInstrument').click(function(e){
	init();
	//是用于取消事件的默认行为，由于我使用的是a标签，他本身有很多事件，比如action、href等
	//在此我们要屏蔽掉他所有的默认方法
	e.preventDefault();
	$('#addInstrumentDiv').modal('show');
});

//---取消按钮的点击事件
$('.addInstrumentCancel').click(function(e){
	init();	
});

//验证输入的是浮点数或者整数
function isNumber(s)
{
    var regu = "^[0-9]+\.?[0-9]*$";
//    var regu = "^[0-9]*$";
    var re = new RegExp(regu);
    if (re.test(s)) 
        return true;
    else 
        return false;
}


//instrumentNameIsExist
//判断仪器是否存在，采用异步加载
function instrumentNameIsExist_post(){
	var instName = $.trim($('#a_instrumentName').val());
	var instType = $.trim($('#a_instrumentType').val());
	if(instName!=null && instName!=''){
		if(instType == null || instType ==''){
			instType = "*";
		}
		//异步判断
		$.post("userCodeIsExist.html",{'instrumentName':instName,'instrumentType':instType},function(result){
			
			if(result == 'exist'){
				$('#addInstrumentTip').attr("key",1);
				alert('该类仪器已存在，不能添加！');
			}else if(result == 'failed'){
				alert('操作超时！');
			}else if(result == 'noexist'){
//				$('#addInstrumentTip').css("color","green");
//				$('#addInstrumentTip').html('<li>该学号可以正常使用  ^v^</li>');
				$('#addInstrumentTip').attr("key",2);
				alert('该类仪器可以添加！');
			}else if(result == 'empty'){
				alert('没有数据传到后台！');
			}			
		},'html');
	}
	else{
		$('#addInstrumentTip').attr("key",1);
		alert('仪器名称不能为空！');
	}
}

$('#a_instrumentName').blur(function(){
	instrumentNameIsExist_post();
});

$('#a_instrumentType').blur(function(){
	instrumentNameIsExist_post();
});


//--- 验证函数
function addInstrumentFunction(){
	var instrumentName = $.trim($('#a_instrumentName').val());
	var instrumentType = $.trim($('#a_instrumentType').val());
	var classNo = $('.a_classNo').val();
	var country = $('.a_country').val();
	var priceInt = $.trim($('#a_int_price').val());
	var priceFloat = $.trim($('#a_float_price').val());
	var manufacturer = $.trim($('#a_manufacturer').val());
	var productionDate = $('#a_productionDate').val();
	var number = $.trim($('#a_number').val());
	var note = $.trim($('#a_note').val());
	var flag = true;
	
	$('#addInstrumentTip').html('');
	
	alert('instrumentName : '+instrumentName+"\n"
		 +'instrumentType : '+instrumentType+"\n"
		 +'classNo : '+classNo+"\n"
		 +'country : '+country+"\n"
		 +'priceInt : '+priceInt+"\n"
		 +'priceFloat : '+priceFloat+"\n"
		 +'manufacturer : '+manufacturer+"\n"
		 +'productionDate : '+productionDate+"\n"
		 +'number : '+number+"\n"
		 +'note : '+note+"\n");
	
	if(instrumentName.length == 0){
		$("#addInstrumentTip").css("color","red");
		$("#addInstrumentTip").append("<li>仪器名称不能为空！</li>");
		flag = false;
	}else{
		if($('#addInstrumentTip').attr("key")=="1"){
			$("#addInstrumentTip").css("color","red");
			$("#addInstrumentTip").append("<li>该仪器已存在！</li>");
			flag = false;
		}
	}
	
//	if(instrumentType.length ==0){
//		$("#addInstrumentTip").css("color","red");
//		$("#addInstrumentTip").append("<li>仪器型号不能为空！</li>");
//		return false;
//	}
	
	if(classNo.length == 0){
		$("#addInstrumentTip").css("color","red");
		$("#addInstrumentTip").append("<li>类别号不能为空！</li>");
		flag = false;
	}
	
	if(country.length == 0){
		$("#addInstrumentTip").css("color","red");
		$("#addInstrumentTip").append("<li>所属国别不能为空！</li>");
		flag = false;
	}
	
	if(priceInt.length == 0){
		$("#addInstrumentTip").css("color","red");
		$("#addInstrumentTip").append("<li>单价不能为空！</li>");
		flag = false;
	}
	
	if(priceFloat.length == 0){
		$("#addInstrumentTip").css("color","red");
		$("#addInstrumentTip").append("<li>单价不能为空！</li>");
		flag = false;
	}
	
	if(manufacturer.length == 0){
		$("#addInstrumentTip").css("color","red");
		$("#addInstrumentTip").append("<li>生产商不能为空！</li>");
		flag = false;
	}
	
	if(productionDate.length == 0){
		$("#addInstrumentTip").css("color","red");
		$("#addInstrumentTip").append("<li>生产日期不能为空！</li>");
		flag = false;
	}else{
		//验证日期是否是过去日期
		
	}

	if(number.length == 0){
		$("#addInstrumentTip").css("color","red");
		$("#addInstrumentTip").append("<li>数量不能为空！</li>");
		flag = false;
	}
	
	if(flag){
		$('#a_note').val(note);
		if(instrumentType == null || instrumentType == '')
			$('#a_instrumentType').val('*');
		$('#a_h_price').val(priceInt+'.'+priceFloat);
		alert($('#a_h_price').val() + '\t' + $('#a_instrumentType').val());
		alert('验证通过，所有数据均以提交！');
	}
	
	return flag;
}






