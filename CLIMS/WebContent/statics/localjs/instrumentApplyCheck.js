
function loadingOpen(){
	    $('.theme-loadbg-popover-mask').fadeIn(100);
	    $('.theme-loadpage-popover').slideDown(200);
}

function loadingClose(){
    $('.theme-loadbg-popover-mask').fadeOut(100);
    $('.theme-loadpage-popover').slideUp(200);
}



//=============================== instrument assign apply operation starts ===============================//

//同意仪器调拨申请操作
$('.instAssignApplyAgree').click(function(){
	var statusInputComponent = $(this).parents('tr').find('#instAssignStatus');
	var statusComponent = $(this).parents('tr').find('.instAssignApplyStatus');
	var actionComponent = $(this).parents('tr').find('.instAssignApplyCheck_action');
	var status = statusInputComponent.val();
	var id = $(this).attr('id');
	if(status == '1'){
		$.ajax({
			url:'instApplyHandle.html',
			type:'POST',
			data:{id:id,flag:1},
			dataType:'html',
			beforeSend:function(){       //ajax发送请求时的操作，得到请求结果前有效
				//开启loading动画
				$("#loading_tip").html('<h3>请等待，正在处理请求...</h3>');
				loadingOpen();
				setTimeout(2000);//延时两秒
			},
			complete:function(){
				setTimeout(function(){
					loadingClose();
				},2400);
			},
			error:function(){
				$("#loading_tip").html('<h3 style="color:red">请求失败，执行error回调函数！</h3>');
			},
			success:function(data){
				if(data == "success"){
					$("#loading_tip").html('<h3 style="color:green">操作成功  ^v^</h3>');
					statusInputComponent.val('2');
					statusComponent.html('<span class="label label-info">审核通过</span>');
					actionComponent.html('<b>请等待系统管理员处理..</b>');
				}
				else if(data == "nodata"){
					$("#loading_tip").html('<h3 style="color:red">操作失败，没有数据传到后台！</h3>');
				}
				else if(data == "failed"){
					$("#loading_tip").html('<h3 style="color:red">系统错误！!!</h3>');
				}
			}
		});
	}else{
		alert('已受理或者已拒绝，不能再操作！');
	}
});

//拒绝仪器调拨申请操作
$('.instAssignApplyRefuse').click(function(){
	if(confirm("您确定要拒绝本次调拨吗？")){
		var statusInputComponent = $(this).parents('tr').find('#instAssignStatus');
		var statusComponent = $(this).parents('tr').find('.instAssignApplyStatus');
		var actionComponent = $(this).parents('tr').find('.instAssignApplyCheck_action');
		var status = statusInputComponent.val();
		var id = $(this).attr('id');
		if(status == '1'){
			$.ajax({
				url:'instApplyHandle.html',
				type:'POST',
				data:{id:id,flag:2},
				dataType:'html',
				beforeSend:function(){       //ajax发送请求时的操作，得到请求结果前有效
					//开启loading动画
					$("#loading_tip").html('<h3>请等待，正在处理请求...</h3>');
					loadingOpen();
					setTimeout(2000);//延时两秒
				},
				complete:function(){
					setTimeout(function(){
						loadingClose();
					},2400);
				},
				error:function(){
					$("#loading_tip").html('<h3 style="color:red">请求失败，执行error回调函数！</h3>');
				},
				success:function(data){
					if(data == "success"){
						$("#loading_tip").html('<h3 style="color:green">操作成功  ^v^</h3>');
						statusInputComponent.val('3');
						statusComponent.html('<span class="label label-important">审核未通过</span>');
						actionComponent.html('<b>您已拒绝调拨申请！</b>');
					}
					else if(data == "nodata"){
						$("#loading_tip").html('<h3 style="color:red">操作失败，没有数据传到后台！</h3>');
					}
					else if(data == "failed"){
						$("#loading_tip").html('<h3 style="color:red">系统错误！!!</h3>');
					}
				}
			});
			//loadingClose();
		}else{
			alert('已受理或者已拒绝，不能再操作！');
		}
	}
});

//签收仪器  
$('.instAssignApplyReceive').click(function(){
	if(confirm("请仔细检查仪器无误后，再签收！")){
		var id = $(this).attr('id');
		var statusInputComponent = $(this).parents('tr').find('#instAssignStatus');
		var statusComponent = $(this).parents('tr').find('.instAssignApplyStatus');
		var actionComponent = $(this).parents('tr').find('.instAssignApplyCheck_action');
		var status = statusInputComponent.val();
		if(status == '4'){
			$.ajax({
				url:'instApplyHandle.html',
				type:'POST',
				data:{id:id,flag:13},
				async:true,
				dataType:'html',
				beforeSend:function(){       //ajax发送请求时的操作，得到请求结果前有效
					//开启loading动画
					$("#loading_tip").html('<h3>请等待，正在处理请求...</h3>');
					loadingOpen();
					setTimeout(2000);//延时两秒
				},
				complete:function(){
					setTimeout(function(){
						loadingClose();
					},2400);
				},
				error:function(){
					$("#loading_tip").html('<h3 style="color:red">请求失败，执行error回调函数！</h3>');
				},
				success:function(data){
					if(data == "success"){
						$("#loading_tip").html('<h3 style="color:green">操作成功  ^v^</h3>');
						statusInputComponent.val('6');
						statusComponent.html('<span class="label label-success">已调拨</span>');
						actionComponent.html('<b>已签收，无需操作！</b>');
					}
					else if(data == "nodata"){
						$("#loading_tip").html('<h3 style="color:red">操作失败，没有数据传到后台！</h3>');
					}
					else if(data == "failed"){
						$("#loading_tip").html('<h3 style="color:red">系统错误！!!</h3>');
					}
				}
			});
		}else{
			alert('未到签收时间，请勿操作！');
		}
	}
});

//=============================== instrument assign apply operation ends ===============================//





//=============================== instrument repair apply operation starts ===============================//
//同意仪器维修申请操作
$('.instRepairApplyAgree').click(function(){
	var selectComponent = $(this).parents('tr').find('.instRepairmanId');
	var selectval = $(this).parents('tr').find('.instRepairmanId select option:selected').val();
	var statusInputComponent = $(this).parents('tr').find('#instRepairStatus');
	var statusComponent = $(this).parents('tr').find('.instRepairApplyStatus');
	var actionComponent = $(this).parents('tr').find('.instRepairApplyCheck_action');
	var status = statusInputComponent.val();
	var id = $(this).attr('id');
	console.log(selectval);
	if(selectval != null && selectval != ''){
		if(status == '8'){
			$.ajax({
				url:'instApplyHandle.html',
				type:'POST',
				data:{id:id,flag:3,repairmanId:selectval},
				dataType:'html',
				beforeSend:function(){       //ajax发送请求时的操作，得到请求结果前有效
					//开启loading动画
					$("#loading_tip").html('<h3>请等待，正在处理请求...</h3>');
					loadingOpen();
					setTimeout(1000);//延时一秒
				},
				complete:function(){
					setTimeout(function(){
						loadingClose();
					},2000);
				},
				error:function(){
					$("#loading_tip").html('<h3 style="color:red">请求失败，执行error回调函数！</h3>');
				},
				success:function(data){
					if(data == "success"){
						$("#loading_tip").html('<h3 style="color:green">操作成功，请尽快安排维修人员维修  ^v^</h3>');
						statusInputComponent.val('9');
						statusComponent.html('<span class="label label-important">维修中</span>');
						selectComponent.html('');
						actionComponent.html('<b>请尽快安排维修人员上门维修.</b>');
					}
					else if(data == "nodata"){
						$("#loading_tip").html('<h3 style="color:red">操作失败，没有数据传到后台！</h3>');
					}
					else if(data == "failed"){
						$("#loading_tip").html('<h3 style="color:red">系统错误！!!</h3>');
					}
				}
			});
		}else{
			alert('已受理或者已拒绝，不能再操作！');
		}
	}else{
		alert('请选择维修员！')
	}
});

//拒绝仪器维修申请操作
$('.instRepairApplyRefuse').click(function(){
	if(confirm("您确定要拒绝本次维修申请吗？")){
		var statusInputComponent = $(this).parents('tr').find('#instRepairStatus');
		var statusComponent = $(this).parents('tr').find('.instRepairApplyStatus');
		var selectComponent = $(this).parents('tr').find('.instRepairmanId');
		var actionComponent = $(this).parents('tr').find('.instRepairApplyCheck_action');
		var status = statusInputComponent.val();
		var id = $(this).attr('id');
		if(status == '8'){
			$.ajax({
				url:'instApplyHandle.html',
				type:'POST',
				data:{id:id,flag:4},
				dataType:'html',
				beforeSend:function(){       //ajax发送请求时的操作，得到请求结果前有效
					//开启loading动画
					$("#loading_tip").html('<h3>请等待，正在处理请求...</h3>');
					loadingOpen();
					setTimeout(1000);//延时一秒
				},
				complete:function(){
					setTimeout(function(){
						loadingClose();
					},2000);
				},
				error:function(){
					$("#loading_tip").html('<h3 style="color:red">请求失败，执行error回调函数！</h3>');
				},
				success:function(data){
					if(data == "success"){
						$("#loading_tip").html('<h3 style="color:green">操作成功  ^v^</h3>');
						statusInputComponent.val('10');
						statusComponent.html('<span class="label">无需维修</span>');
						selectComponent.html('');
						actionComponent.html('<b>您已拒绝本次维修申请.</b>');
					}
					else if(data == "nodata"){
						$("#loading_tip").html('<h3 style="color:red">操作失败，没有数据传到后台！</h3>');
					}
					else if(data == "failed"){
						$("#loading_tip").html('<h3 style="color:red">系统错误！!!</h3>');
					}
				}
			});
		}else{
			alert('已受理或者已拒绝，不能再操作！');
		}
	}
});

//确认已维修仪器 
$('.manager_confirmRepair').click(function(){
	if(confirm("是否该仪器已维修？")){
		alert(id);
		var statusInputComponent = $(this).parents('tr').find('#instRepairStatus');
		var statusComponent = $(this).parents('tr').find('.instRepairApplyStatus');
		var actionComponent = $(this).parents('tr').find('.instRepairApplyCheck_action');
		//post 提交  $(selector).post(URL,data,function(data,status,xhr),dataType)
		$.post('instApplyHandle.html',{'id':id,'flag':9},function(data){
			if(data == "success"){
				alert('操作成功');
				statusInputComponent.val('11');
				statusComponent.html('<span class=\"label label-success\">已维修</span>');
				actionComponent.html('<b>仪器已维修完成.</b>');
			}else if(data == "nodata"){
				alert('没有数据传到后台！');
			}else{
				alert('系统内部错误，请稍后重试！');
			}
		},'html');
	}
});

//查看维修详情
function showRepairDetail(id){
	//console.log(id);
	if(id == null || id == '')return;
	$("#showRepairDetail_body").load('showRepairDetail.html',{'id':id},function(result,status){
		if(status == "success" || status == "notmodified"){
			$('#showRepairDetail_div').modal('show');
		}else{
			alert('查询失败！');
		}
	});
	
}
//=============================== instrument repair apply operation ends ===============================//





//=============================== instrument scrap apply operation starts ===============================//
//同意仪器报废申请操作
$('.instScrapApplyAgree').click(function(){
	var statusInputComponent = $(this).parents('tr').find('#instScrapStatus');
	var statusComponent = $(this).parents('tr').find('.instScrapApplyStatus');
	var actionComponent = $(this).parents('tr').find('.instScrapApplyCheck_action');
	var status = statusInputComponent.val();
	var id = $(this).attr('id');
	if(status == '12'){
		$.ajax({
			url:'instApplyHandle.html',
			type:'POST',
			data:{id:id,flag:5},
			dataType:'html',
			beforeSend:function(){       //ajax发送请求时的操作，得到请求结果前有效
				//开启loading动画
				$("#loading_tip").html('<h3>请等待，正在处理请求...</h3>');
				loadingOpen();
				setTimeout(1000);//延时一秒
			},
			complete:function(){
				setTimeout(function(){
					loadingClose();
				},2000);
			},
			error:function(){
				$("#loading_tip").html('<h3 style="color:red">请求失败，执行error回调函数！</h3>');
			},
			success:function(data){
				if(data == "success"){
					$("#loading_tip").html('<h3 style="color:green">操作成功   ^v^</h3>');
					statusInputComponent.val('13');
					statusComponent.html('<span class="label label-success">已报废</span>');
					actionComponent.html('');
				}
				else if(data == "nodata"){
					$("#loading_tip").html('<h3 style="color:red">操作失败，没有数据传到后台！</h3>');
				}
				else if(data == "failed"){
					$("#loading_tip").html('<h3 style="color:red">系统错误！!!</h3>');
				}
			}
		});
	}else{
		alert('已受理或者已拒绝，不能再操作！');
	}
});

//拒绝仪器报废申请操作
$('.instScrapApplyRefuse').click(function(){
	if(confirm("您确定要拒绝本次维修申请吗？")){
		var statusInputComponent = $(this).parents('tr').find('#instScrapStatus');
		var statusComponent = $(this).parents('tr').find('.instScrapApplyStatus');
		var actionComponent = $(this).parents('tr').find('.instScrapApplyCheck_action');
		var status = statusInputComponent.val();
		var id = $(this).attr('id');
		if(status == '12'){
			$.ajax({
				url:'instApplyHandle.html',
				type:'POST',
				data:{id:id,flag:6},
				dataType:'html',
				beforeSend:function(){       //ajax发送请求时的操作，得到请求结果前有效
					//开启loading动画
					$("#loading_tip").html('<h3>请等待，正在处理请求...</h3>');
					loadingOpen();
					setTimeout(1000);//延时一秒
				},
				complete:function(){
					setTimeout(function(){
						loadingClose();
					},2000);
				},
				error:function(){
					$("#loading_tip").html('<h3 style="color:red">请求失败，执行error回调函数！</h3>');
				},
				success:function(data){
					if(data == "success"){
						$("#loading_tip").html('<h3 style="color:green">操作成功  ^v^</h3>');
						statusInputComponent.val('14');
						statusComponent.html('<span class="label">未报废</span>');
						actionComponent.html('');
					}
					else if(data == "nodata"){
						$("#loading_tip").html('<h3 style="color:red">操作失败，没有数据传到后台！</h3>');
					}
					else if(data == "failed"){
						$("#loading_tip").html('<h3 style="color:red">系统错误！!!</h3>');
					}
				}
			});
		}else{
			alert('已受理或者已拒绝，不能再操作！');
		}
	}
});
//=============================== instrument scrap apply operation starts ===============================//

