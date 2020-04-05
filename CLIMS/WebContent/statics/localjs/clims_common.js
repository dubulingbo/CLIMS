/**
 * 该文件用来存放本系统的一些公共js文件，将在foot.jsp文件中引用，
 * 故要使用本文件，就必须在页面上包含foot.jsp文件
 */
//==========================加载当前时间 start==========================//
function showTime(){ 
	var show_day=new Array('星期日','星期一','星期二','星期三','星期四','星期五','星期六'); 
	var time=new Date(); 
	var year=time.getFullYear(); 
	var month=time.getMonth() + 1; 
	var date=time.getDate(); 
	var day=time.getDay(); 
	var hour=time.getHours(); 
	var minutes=time.getMinutes();
	var _hour=(hour<10?'0'+hour:hour); 
	var _minutes=(minutes<10?'0'+minutes:minutes); 
	var now_time=year+'-'+month+'-'+date+' '+_hour+':'+_minutes+'，'+show_day[day]; 
	document.getElementById('currentTime_show').innerHTML=now_time; 
}
setInterval("showTime();",1000);  //加载当前时间
//==========================加载当前时间 end==========================//
	
//===============================查看用户信息 start===============================//
/**
 * 调用前提：
 * 该组件必须有点击事件，且class="viewUser",id="要查看的用户ID（不能为空！）"
 */
$('.viewUser').click(function(e){
	var v_id = $(this).attr('id');
	//ajax
	$.ajax({
		url:'viewUser.html',
		type:'POST',
		data:{id:v_id},
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
				$('#v_userRole').text(result.userRoleName);
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
//===============================查看用户信息 end===============================//
	
	
//===============================查看仪器信息弹出框 start===============================//
/**
 * 调用前提：
 * 该组件必须有点击事件，且class="viewInstrument",id="要查看的仪器ID（不能为空！）"
 */
$('.viewInstrument').click(function(e){
	var v_id = $(this).attr('id');
	//ajax
	$.ajax({
		url:'/backend/viewInstrument.html',
		type:'POST',
		data:{id:v_id},
		dataType:'json',
		timeout:1000,
		error:function(){
			alert('error! 2333');
		},
		success:function(result){
			if(result == "failed"){
				alert('操作超时！');
			}else if(result == "nodata"){
				alert('没有数据传到后台！');
			}else{
				//v_instrumentName
				$('#v_instrumentName').text(result.instrumentName);
				//v_instrumentNo
				$('#v_instrumentNo').text(result.instrumentNo);
				//v_instrumentType
				$('#v_instrumentType').text(result.instrumentType);
				//v_className
				$('#v_className').text(result.className);
				//v_manufacturer
				$('#v_manufacturer').text(result.manufacturer);
				//v_price
				$('#v_price').text(result.price1);
				//v_productionDate
				$('#v_productionDate').text(result.productionDate);
				//v_country
				$('#v_country').text(result.country);
				//v_instrumentManager
				$('#v_instrumentManager').text(result.stockManager);
				//v_managerTel
				$('#v_managerTel').text(result.managerTel);
				//v_dept
				$('#v_dept').text(result.dept);
				//v_locName
				$('#v_locName').text(result.locName);
				
				e.preventDefault(); //屏蔽默认样式
				$("#viewInstrumentDiv").modal('show');
			}
		}
	});
});
//===============================查看仪器信息弹出框 end===============================//