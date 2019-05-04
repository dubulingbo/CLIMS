
function loadingOpen(){
	    $('.theme-loadbg-popover-mask').fadeIn(100);
	    $('.theme-loadpage-popover').slideDown(200);
}

function loadingClose(){
    $('.theme-loadbg-popover-mask').fadeOut(100);
    $('.theme-loadpage-popover').slideUp(200);
}

//同意仪器调拨申请操作
$('.instAssignApplyAgree').click(function(){
	var status = $(this).parents('tr').find('#instAssignStatus').val();
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
				},2500);
			},
			error:function(){
				$("#loading_tip").html('<h3 style="color:red">请求失败，执行error回调函数！</h3>');
				
			},
			success:function(data){
				if(data == "success"){
					$("#loading_tip").html('<h3 style="color:green">操作成功  ^v^</h3>');
					setTimeout("window.location.reload();",2700);
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
	var status = $(this).parents('tr').find('#instAssignStatus').val();
	var id = $(this).attr('id');
	if(status == '1'){
		//开启loading动画
		//loadingOpen();
		$.ajax({
			url:'instApplyHandle.html',
			type:'POST',
			data:{id:id,flag:2},
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
				},2500);
			},
			error:function(){
				$("#loading_tip").html('<h3 style="color:red">请求失败，执行error回调函数！</h3>');
				
			},
			success:function(data){
				if(data == "success"){
					$("#loading_tip").html('<h3 style="color:green">操作成功  ^v^</h3>');
					setTimeout("window.location.reload();",2700);
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
});


