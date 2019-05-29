
//执行一个laydate实例
laydate.render({
	elem: document.getElementById('rep_bookDate'), //指定元素
	type: 'datetime',
	format: 'yyyy-MM-dd HH:mm',
	min: 1
});


//============================ assign apply starts ============================//
//表单验证函数
//function instrumentAssignApplyFunction(){
//	//先判断是否都为空
//	var instrumentName = $.trim($('#s_instrumentName').val());
//	var instrumentNo = $.trim($('#s_instrumentNo').val());
//	var classNo = $.trim($('.s_classNo').val());
//	if(instrumentName.length==0 && instrumentNo.length==0 && classNo.length==0){
//		alert('请输入信息后再查询！');
//		return false;
//	}else{
//		return true;
//	}
//}

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
//============================ assign apply ends ============================//










//============================ repair apply starts ============================//
//弹出申请维修窗口
$('.repairApply').click(function(e){
	clearRepairDiv();
	var id = $(this).attr('id');
	var instId = $(this).attr('instId');
	var instName = $(this).parents('tr').find('td:nth-child(3)').text();
	$(".instrumentRepairApplySave").attr('id',id);
	//rep_assignId
	$("#rep_assignId").val(id);
	$("#rep_instrumentName").val(instName);
	$("#rep_instrumentId").val(instId)
	//是用于取消事件的默认行为，由于我使用的是a标签，他本身有很多事件，比如action、href等
	//在此我们要屏蔽掉他所有的默认方法
	e.preventDefault();
	$('#instrumentRepairApplyDiv').modal('show');
});

//快速填写个人信息 rep_quickWriteInfo
$(".rep_quickWriteInfo").click(function(){
	var u_id = $(this).attr('id');
	$.ajax({
		url:'writeSelfInfo.html',
		type:'POST',
		data:{u_id:u_id},
		dataType:'json',
		timeout:1000,
		error:function(){
			alert('error，请自己填写信息！');
		},
		success:function(result){
			if(result == "failed"){
				alert('获取失败，请自己填写准确信息！');
			}else if(result != null && result != ""){
				$('#rep_dept').val(result.dept);
				$('#rep_address').val(result.locName);
				$('#rep_applyPerson').val(result.userName);
				$('#rep_applyPhone').val(result.phone);
			}else{
				alert('其他错误，请自己填写准确信息!');
			}
		}
	});
});
function clearRepairDiv(){
	$("#instrumentRepairApply_tip").html('');
	$('#rep_assignId').val('');
	$('#rep_instrumentId').val('');
	$('#rep_instrumentName').val('');
	$('#rep_dept').val('');
	$('#rep_address').val('');
	$('#rep_detail').val('');
	$('#rep_bookDate').val('');
	$('#rep_applyPerson').val('');
	$('#rep_applyPhone').val('');
	//$('#rep_repairImgPath').val(''); 
	//$('.rep_imgShow').html('');
}
//.instrumentRepairApplyCancel
$('.instrumentRepairApplyCancel').click(function(){
	clearRepairDiv();
});
//js 两个日期比较（yyyy-MM-dd）若s>e return >0,若s<e return <0,若s=e return 0;
function comtime(s,e){
	var arr1 = s.split("-");
	var starttime = new Date(arr1[0],arr1[1],arr1[2]);
	var starttimes = starttime.getTime();
	var arr2 = e.split("-");
	var endtime = new Date(arr2[0],arr2[1],arr2[2]);
	var endtimes = endtime.getTime();
	if(starttimes > endtimes)
		return 3;
	else if(starttimes < endtimes)
		return -3;
	else
		return 0;
}
// 与当前时间比较 yyyy-MM-dd 
function comCurrentTime(s){
	var cur=new Date(); 
	var year=cur.getFullYear(); 
	var month=cur.getMonth() + 1; 
	var date=cur.getDate(); 
	var estr = "";
	
	var _month= (month<10?"0"+month:month);
	var _date= (date<10?"0"+date:date);
	estr += (year + "-" + _month + "-" +_date);
	return comtime(s,estr)
}
//验证手机号
function isPhoneNo(phone) {

	var pattern = /^1[34578]\d{9}$/;
	return pattern.test(phone);
}



//.instrumentRepairApplySave
$('.instrumentRepairApplySave').click(function(){
	var id = $(this).attr('id');
	var inst = new Object();
	inst.assId = id;
	inst.instrumentId = $('#rep_instrumentId').val();
	inst.instrumentName = $('#rep_instrumentName').val();
	inst.dept = $.trim($('#rep_dept').val());
	inst.address = $.trim($('#rep_address').val());
	inst.repairDetail = $.trim($('#rep_detail').val());
	inst.bookDate = $.trim($('#rep_bookDate').val());
	inst.applyPerson = $.trim($('#rep_applyPerson').val());
	inst.applyPhone = $.trim($('#rep_applyPhone').val());
	inst.repairImgPath = $.trim($('#rep_repairImgPath').val());
	
	var flag = true;
	var tip = $("#instrumentRepairApply_tip");
	tip.html('');
	if(inst.dept == null || inst.dept == ""){
		tip.css("color","red");
		tip.append("<li>请选择报修单位！</li>");
		flag = false;
	}
	
	if(inst.address == null || inst.address == ""){
		tip.css("color","red");
		tip.append("<li>请填写详细地址！</li");
		flag = false;
	}
	
	if(inst.repairDetail == null || inst.repairDetail == ""){
		tip.css("color","red");
		tip.append("<li>请填写报修详细！</li>");
		flag = false;
	}
	
	if(inst.bookDate == null || inst.bookDate == ""){
		tip.css("color","red");
		tip.append("<li>请选择预约时间！</li>");
		flag = false;
	}
//	else if(comCurrentTime(inst.bookDate) <= 0){
//		tip.css("color","red");
//		tip.append("<li>预约时间不正确！</li>");
//		flag = false;
//	}
	
	if(inst.applyPerson == null || inst.applyPerson == ""){
		tip.css("color","red");
		tip.append("<li>申请人不能为空！</li>");
		flag = false;
	}
	
	if(inst.applyPhone == null || inst.applyPhone == ""){
		tip.css("color","red");
		tip.append("<li>联系方式不能为空！</li>");
		flag = false;
	}else if(!isPhoneNo(inst.applyPhone)){
		tip.css("color","red");
		tip.append("<li>请输入正确的联系方式！</li>");
		flag = false;
	}
	
//	if(inst.repairImgPath == null || inst.repairImgPath == ""){
//		tip.css("color","red");
//		tip.append("<li>请上传图片！</li>");
//		flag = false;
//	}
	
	
	if(flag){
		$.ajax({
			url:'instrumentRepairSave.html',
			type:'post',
			data:{instRepair:JSON.stringify(inst)},
			dataType:'json',
			beforeSend:function(){
				$("#instrumentRepairApplyDiv").modal("hide");
			},
			error:function(){
				alert('error，提交失败！');
				$(".my_repairApplyInfo_div").html('<div class=\"center\">加载列表错误！</div>');
			},
			success:function(result){
				if(result == "failed"){
					alert('系统错误，请稍后重试！');
					$(".my_repairApplyInfo_div").html('<div class=\"center\">加载列表错误！</div>');
				}else if(result == "nodata"){
					alert('服务器未收到数据！');
					$(".my_repairApplyInfo_div").html('<div class=\"center\">加载列表错误！</div>');
				}else{
					
					alert('申请成功！');
					$('#repairApplyAction'+id).html('<b>已申请维修！</b>');
					//开始加载已申请维修的记录
					var div = $(".my_repairApplyInfo_div");
					console.log(result);
					var str = "";
					str = str + '<table class=\"table table-bordered\">'+
								'<thead><tr>'+
								'<th>仪器编号</th>'+
								'<th>仪器名称</th>'+
								'<th>报修详情</th>'+
								'<th>申请日期</th>'+
								'<th>预约日期</th>'+
								'<th>处理进度</th>'+
								'</tr></thead>';
					str = str + '<tbody>';
					for(var i=0;i<result.length;i++){
						str = str + '<tr>'+
									'<td>'+result[i].assignId+'</td>'+
									'<td>'+result[i].instrumentName+'</td>'+
									'<td>'+result[i].repairDetail+'</td>'+
									'<td>'+result[i].creationDate+'</td>'+
									'<td>'+result[i].bookDate+'</td>';
						if(result[i].status == 8){
							str = str + '<td><span class=\"label label-info\">'+
									    '申请维修'+
									    '</span></td>';
						}
						str = str + '</tr>';
					}
					str = str + '</tbody></table>';
					div.html(str);
				}
			}
		});
	}else{
	}
	
});

//'0','a_fileInputID','a_uploadbtnID','a_idPic','a_fileInputIDPath'
function TajaxFileUpload(){  
	var t1 = $("#rep_repairImgPath_file").val();
	alert(t1);
	$.ajaxFileUpload({
		url : '/backend/normaluser/uploadImgToServer.html', // 处理上传文件的服务端
		secureuri : false,
		fileElementId : 'rep_repairImgPath_file',
		dataType : 'json',
		success : function(data) {
			data = data.replace(/(^\s*)|(\s*$)/g, "");
			alert(data);
			if (data == "3") {
				alert("上传图片大小不得超过5M ！");
				$("#rep_repairImgPath_file").val('');
			} else if (data == "2") {
				alert("上传图片格式不正确！");
				$("#rep_repairImgPath_file").val('');
			} else if (data == "4") {
				alert("上传图片数量不能超过 2 张！");
				$("#rep_repairImgPath_file").val('');
			} else { //上传成功
				/*<ul class="thumbnails gallery rep_imgShow">
				<li class="thumbnail"><a style="background:url(/statics/img/gallery/thumbs/1.jpg)" href="/statics/img/gallery/1.jpg"><img class="grayscale" src="/statics/img/gallery/thumbs/1.jpg" alt=""></a></li>
				<li class="thumbnail">
					<a style="background:url(/statics/img/gallery/thumbs/2.jpg)" href="/statics/img/gallery/2.jpg"><img class="grayscale" src="/statics/img/gallery/thumbs/2.jpg" alt=""></a>
				</li>
			</ul>*/
				//?m="+Math.random() 是为了解决浏览器缓存
				var str = '<li class=\"clims_thumbnail\"><a style=\"background:url(\''+data+'?m='+Math.random()
						 +'\')" href=\"'+data+'?m='+Math.random()+'\"><img class=\"grayscale\" src=\"'
						 +data+'?m='+Math.random()+'\"></a></li>';
				$("#rep_imgShow").load('/loadImg.html',{'imgStr':str},function(result){
					if(result == "failed"){
						alert("图片加载失败！");
					}else{
						alert(result);
					}
				});
				$("#rep_repairImgPath").val('2333');
			}
		},
		error : function() {
			alert("上传失败！");
		}
	});
}
//============================ repair apply ends ============================//










//============================ scrap apply starts ============================//
//判断输入的仪器编号是否已存在
$('#sc_assignId').blur(function(){
	var assignId = $.trim($('#sc_assignId').val());
	if(assignId == null || assignId == '')return;
	else
		$.post('assignIdIsExist.html',{'assignId':assignId},function(data){
			if(data == "noexist"){
				alert('该仪器不存在，不可申请.');
			}else if(data == "nodata"){
				alert('没有数据传到后台！');
			}else if(data == "failed"){
				alert('系统错误！');
			}else{
				alert('该仪器存在，可以申请.');
				$('#sc_instrumentName').val(data.instrumentName);
				//alert(data.assignNumber);
				if(data.assignNumber > 0){
					$('#sc_scrapNumber').html('<option value="">-请选择-</option>')
					for(var i=1;i<=data.assignNumber;i++){
						$('#sc_scrapNumber').append('<option value="'+i+'"> '+i+'</option>')
					}
				}
				$('#sc_applyPerson').val(data.createdByName);
			}
		});
});
function clearRepairApplyForm(){
	
	$('#sc_assignId').val('');
	$('#sc_instrumentName').val('');
	$('#sc_scrapNumber').html('<option value="">-请选择-</option>');
	$('#sc_scrapDetail').val('');
	$('#sc_applyPerson').val('');
}
$('.instScrapApplySave').click(function(){
	//判断是否为空
	//sc_assignId sc_instrumentName sc_scrapNumber sc_scrapDetail sc_applyPerson
	var assignId = $.trim($('#sc_assignId').val());
	var instrumentName = $.trim($('#sc_instrumentName').val());
	var scrapNumber = $.trim($('#sc_scrapNumber').val());
	var scrapDetail = $.trim($('#sc_scrapDetail').val());
	var applyPerson = $.trim($('#sc_applyPerson').val());
	if(assignId == null || assignId == ''){
		alert('仪器编号不能为空！');return;
	}
	if(instrumentName == null || instrumentName == ''){
		alert('仪器名称不能为空！');return;
	}
	else if(scrapNumber == null || scrapNumber == ''){
		alert('申请数量不能为空！');return;
	}
	if(scrapDetail == null || scrapDetail == ''){
		alert('报废原因不能为空！');return;
	}
	if(applyPerson == null || applyPerson == ''){
		alert('申请人不能为空！');return;
	}
	//提交到后台
	$.ajax({
		url:'instrumentScrapSave.html',
		type:'post',
		data:{assignId:assignId,scrapNumber:scrapNumber,scrapDetail:scrapDetail},
		dataType:'html',
		timeout:1000,
		success:function(result){
			if(result == "success"){
				clearRepairApplyForm();
				alert('申请成功！');
			}else{
				alert('操作失败，系统错误！');
			}
		},
		error:function(){
			alert('error,请求失败！');
		}
	});
});
	


//============================ scrap apply ends ============================//







