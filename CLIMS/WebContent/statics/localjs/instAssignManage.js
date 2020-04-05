
//允许调拨
$(".agreeAssignCheck").click(function(e){
	var id = $(this).attr('id');
	var str1 = "#changeStatus"+id;
	var str2 = "#instAssignApplyStatus"+id;
	var status = $(str1).val();
	if(status == "2"){
		qingkong();
		$('.tip_content').html('<p>正在处理请求，请等待...</p>');
		e.preventDefault();
		$('#actionDiv_admin').modal('show');
		
		$.ajax({
			url:'instApplyHandle.html',
			type:'POST',
			data:{id:id,flag:7},
			dataType:'html',
			error:function(data){
				$('.tip_content').html('<p style="color:red;">提交失败，具体信息为 : '+data+'</p>');
				$('.tip_action').html('<a href="#" class="btn actionCancel_admin" data-dismiss="modal">关闭</a>');
			},
			success:function(data){
				if(data == "success"){
					$(str1).val('4');
					$(str2).html('<span class="label label-important">调拨中</span>');
					$('.tip_content').html('<p style="color:green;">操作成功，请您尽快安排人员派遣该设备，谢谢！</p>');
					$('.tip_action').html('<a href="#" class="btn actionCancel_admin" data-dismiss="modal">关闭</a>');
				}else if(data == "nodata"){
					$('.tip_content').html('<p style="color:red;">提交失败，请求参数为空！</p>');
					$('.tip_action').html('<a href="#" class="btn actionCancel_admin" data-dismiss="modal">关闭</a>');
				}else if(data == "failed"){
					$('.tip_content').html('<p style="color:red;">系统错误，请重试！</p>');
					$('.tip_action').html('<a href="#" class="btn actionCancel_admin" data-dismiss="modal">关闭</a>');
				}
			}
		});
	}else{
		alert('已处理，不能重复操作！');
	}
});

//不允许调拨 refuseAssignCheck

function refuse_tijiao(id){
	var reason = $.trim($('#r_reason').val());
	if(reason.length == 0){
		$('#refuseInstAssignCheck_tip').css('color','red');
		$('#refuseInstAssignCheck_tip').html('请输入原因！');
	}else{
		qingkong();
		$('.tip_content').html('<p>正在处理请求，请等待...</p>');
		$.ajax({
			url:'instApplyHandle.html',
			type:'POST',
			data:{id:id,flag:8,reason:reason},
			dataType:'html',
			error:function(data){
				$('.tip_content').html('<p style="color:red;">提交失败，具体信息为 : '+data+'</p>');
				$('.tip_action').html('<a href="#" class="btn actionCancel_admin" data-dismiss="modal">关闭</a>');
			},
			success:function(data){
				if(data == "success"){
					$("#changeStatus"+id).val('5');
					$("#instAssignApplyStatus"+id).html('<span class="label">调拨失败</span>');
					$('.tip_content').html('<p style="color:green;">操作成功</p>');
					$('.tip_action').html('<a href="#" class="btn actionCancel_admin" data-dismiss="modal">关闭</a>');
				}else if(data == "nodata"){
					$('.tip_content').html('<p style="color:red;">提交失败，请求参数为空！</p>');
					$('.tip_action').html('<a href="#" class="btn actionCancel_admin" data-dismiss="modal">关闭</a>');
				}else if(data == "failed"){
					$('.tip_content').html('<p style="color:red;">系统错误，请重试！</p>');
					$('.tip_action').html('<a href="#" class="btn actionCancel_admin" data-dismiss="modal">关闭</a>');
				}
			}
		});
	}
}

$(".refuseAssignCheck").click(function(e){
	var id = $(this).attr('id');
	var status = $("#changeStatus"+id).val();
	if(status == "2"){
		qingkong();
		$('.tip_content').append('<label>请简述拒绝的原因</label><textarea class="autogrow" id="r_reason" title="此为必输项."></textarea>');
		$('.tip_content').append('<p id="refuseInstAssignCheck_tip"></p>');
		$('.tip_action').append('<a href="#" class="btn actionCancel_admin" data-dismiss="modal">关闭</a>');
		$('.tip_action').append('<a href="javascript:refuse_tijiao('+id+');" class="btn btn-primary">提交</a>');
		e.preventDefault();
		$('#actionDiv_admin').modal('show');
	}else{
		alert('已处理，不能重复操作！');
	}
});

function qingkong(){
	$(".tip_content").html('');  //清空modal-body
	$('.tip_action').html('');   //清空modal-footer
}

$(".actionCancel_admin").click(function(){
	//$(".tip_title").html('');    //清空modal-header
	qingkong();
	
});