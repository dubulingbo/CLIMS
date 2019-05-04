//表单验证函数
function instrumentAssignApplyFunction(){
	//先判断是否都为空
	var instrumentName = $.trim($('#s_instrumentName').val());
	var instrumentNo = $.trim($('#s_instrumentNo').val());
	var classNo = $.trim($('.s_classNo').val());
	if(instrumentName.length==0 && instrumentNo.length==0 && 
			classNo.length==0){
		alert('请输入信息后再查询！');
		return false;
	}else{
		return true;
	}
}

//assignApply
$('.assignApply').click(function(){
	var instId = $(this).attr('id');
	var instName = $(this).attr('instName');
	var instNo = $(this).attr('instNo');
	//var instNumber = $('.selectNumber').val();
	var instNumber = $(this).parents('tr').find('.selectNumber').val();
	if(confirm('确定申请调拨【'+
			   '\n     名称: '+instName+
			   '\n     编号: '+instNo+
			   '\n     数量: '+instNumber+
			   '\n】设备吗？')){
		//ajax 异步实现申请调拨操作
		instAssign = new Object();
		instAssign.instrumentId = instId;
		instAssign.assignNumber = instNumber;
		$.ajax({
			url:'instrumentAssignSave.html',
			type:'POST',
			data:{instAssign:JSON.stringify(instAssign)},
			dataType:'html',
			error:function(){
				alert('申请失败，与服务器连接异常！');
			},
			success:function(result){
				if(result != "" && result == "success"){
					alert('申请成功！管理员会尽快处理，请耐心等待 ^_^');
					window.location.href='instrumentAssignApply.html';
				}else if(result == "failed"){
					alert('系统错误，请重试！');
				}else if(result =="nodata"){
					alert('没有数据传到后台！');
				}else{
					alert('申请失败，请稍后重试！');
				}
			}
		});
	}
});

//$(".selectNumber").change(function(){
//	sNumber = $(this);
//	var n = sNumber.val();
//	$('.assignApply').attr('instNumber',n);
//	alert(n);
//});

//<option value="2">-- 2 --</option>
//$(".selectNumber").click(function(){
//	sop = $(this);
//	var n = sop.attr('maxApplyNum');
//	for(var i=2;i<=n;i++){
//		sop.append('<option value="'+i+'">-- '+i+' --</option>');
//	}
//	sop.attr('maxApplyNum',0);
//});
