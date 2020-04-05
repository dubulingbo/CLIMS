$('#informationContent').cleditor();

$(".modifyinfocancel").click(function(){
	modifyInfoCancel();
});

$("#informationuploadbtn").click(function(){
	informationFileUpload();
});
$("#informationuploadMbtn").click(function(){
	informationFileUploadM();
});

$("#docType").change(function(){
	$("#typeNamehide").val($("#docType").find("option:selected").text());
	//alert($("#typeNamehide").val());
});
$("#docTypeModity").change(function(){
	$("#typeNamehideM").val($("#docTypeModity").find("option:selected").text());
	//alert($("#typeNamehide").val());
});

//清空修改资讯面板上的内容
function modifyInfoCancel(){
	//id: modify_formtip
	$("#modify_formtip").html('');
	//id: informationTitleModify
	$("#informationTitleModify").val('');
	//id: docTypeModity
	$("#docTypeModity").val('');
	//id: uploadInformationFileM
	$("#uniform-uploadInformationFileM span:first").html('无文件');
	//id: infoIdModify
	$("#infoIdModify").val('');
	//id: uploadfilepathhideM
	$("#uploadfilepathhideM").val('');
	//id: uploadfilenamehideM
	$("#uploadfilenamehideM").val('');
	//id: typeNamehideM
	//$("#typeNamehideM").val('');
	//id: fileSizehideM
	$("#fileSizehideM").val('');
	//id: fileareaM
	$("#fileareaM").html('');
	//id: modifyinformationli
	$("#modifyinformationli").html('');
}


function delFileM(){
	if(confirm("您确定要删除永久此文件吗？")){
		$.ajax({
			url: 'information/delInfoFile.html',
			type: 'POST',
			data:{filePath:$("#uploadfilepathhideM").val(),flag:2},
			dataType: 'html',
			error: function(){
				alert("删除文件失败！error。");
			},
			success: function(result){
				if(result != "" && result == "success"){
					$("#uploadfilepathhideM").val('');
					$("#uploadfilenamehideM").val('');
					$("#fileSizehideM").val('');
					$("#informationuploadMbtn").show();
					$("#fileareaM").html("");
					$("#uniform-uploadInformationFileM span:first").html('无文件');
				}else if("failed" == result){
					alert("删除文件失败！请重试。");
				}else if("nodata" == result){
					alert("对不起，没有任何数据需要处理！请重试。");
				}
			}
		});
	}
}

function informationFileUploadM()
{   
	if($("#uploadInformationFileM").val() == "" || $("#uploadInformationFileM").val()  == null){
		alert("请选择上传文件！");
	}else{
		$.ajaxFileUpload
		({ 
			url:'information/uploadInfoFile.html', //处理上传文件的服务端
			secureuri:false,
			fileElementId:'uploadInformationFileM',
			dataType: 'json',
			success: function(data) { 
				data = data.replace(/(^\s*)|(\s*$)/g, "");
				if(data == "3"){
					alert("上传图片大小不得超过50M！");
				}else if(data == "1"){
					alert("上传失败，请重试。")
	        	}else if(data == "5"){
	        		alert("没有文件可上传。")
	        	}else{
					var oldFile = data.substring(0,data.indexOf("[[[]]]"));
					var newFile = data.substring(data.indexOf("[[[]]]")+6,data.indexOf("size:"));
					var fileSize = data.substring(data.indexOf("size:")+5);
					$("#uploadfilenamehideM").val(oldFile);
					$("#uploadfilepathhideM").val(newFile);
					$("#fileSizehideM").val(fileSize);
					$("#fileareaM").css("color","green");
					$("#fileareaM").html("上传文件：" + oldFile + "&emsp;大小："+(fileSize/1024)+" KB&emsp;&emsp;<a style=\"color:red;\" href=\"javascript:delFileM();\">X</a>");
					$("#informationuploadMbtn").hide();
					 $("#uploadInformationFileM").change(function(){
	        			   var fn = $("#uploadInformationFileM").val(); 
	        			   if($.browser.msie){
	        				   fn = fn.substring(fn.lastIndexOf("\\")+1);
	        			   }
	        			   $("#uniform-uploadInformationFileM span:first").html(fn);
	        		 });
				}
			},  
			error: function() {  
				alert("上传失败！error。");
			} 
		});
	}
}



/**
 * 修改资讯：
 * 1.查看资讯--显示到页面
 * 2.修改资讯--保存到后台
 */
$(".modifyinformation").click(function(e){
	modifyInfoCancel();
	id = $(this).attr("id");
	$.ajax({
		url: 'viewInformation.html',
		type: 'POST',
		data:{id:id},
		dataType: 'html',
		error: function(){
			alert("error!!!");
		},
		success: function(result){
			if("failed" == result){
				alert("获取"+id+"号资讯失败！请重试。");
			}else if("nodata" == result){
				alert("对不起，没有任何数据需要处理！请重试。");
			}else if(result != null && result != ""){
				
				jsonStrInfo = eval("("+result+")");
				//id
				$("#infoIdModify").val(jsonStrInfo.id);
				$("#informationTitleModify").val(jsonStrInfo.title);
				//typeID
				$("#docTypeModity").val(jsonStrInfo.typeId);
				//fileName
				$("#uploadfilenamehideM").val(jsonStrInfo.fileName);
				//filePath
				$("#uploadfilepathhideM").val(jsonStrInfo.filePath);
				//fileSize
				$("#fileSizehideM").val(jsonStrInfo.fileSize);
		
				
				if(jsonStrInfo.fileName != null && jsonStrInfo.fileName != ""){
					$("#fileareaM").css("color","green");
	     		    $("#fileareaM").html("已上传文件：" + jsonStrInfo.fileName + "&emsp;大小："+(jsonStrInfo.fileSize/1024)+" KB&emsp;&emsp;<a style=\"color:red;\" href=\"javascript:delFileM();\">X</a>");
				}else{
					$("#fileareaM").css("color","red");
	     		    $("#fileareaM").html("已上传文件：暂无");
	     		    $("#informationuploadMbtn").show();
				}
				$("#modifyinformationli").html("");
     		    $("#modifyinformationli").append("<span>资讯内容：</span> <br/><textarea id=\"infoContentModifyContent\" name=\"content\" rows=\"3\">"+jsonStrInfo.content+"</textarea>");
     		    $('#infoContentModifyContent').cleditor();
				
				e.preventDefault();
				$('#modifyInfoDiv').modal('show');
			}
		}
	});
});

function CoverFormXmlTag(value){
	value = value.replace("&","&amp;");
	value = value.replace("<","&lt;");
	value = value.replace(">","&gt;");
	value = value.replace("'\'","&quot;");
	value = value.replace("\r\n","<br>");
	value = value.replace("","");
	return value;
}

function modifyInfoFunction(){
	infoTitle = $("#informationTitleModify");
	docType = $("#docTypeModity");
	filePath = $("#uploadfilepathhideM");
	content = $("#infoContentModifyContent");
	modify_formtip = $("#modify_formtip");

	if( $.trim(infoTitle.val()) == "" || infoTitle.val() == null){
		modify_formtip.css("color","red");
		modify_formtip.html("对不起，资讯标题不能为空。");
		infoTitle.focus();
		return false;
	}else if( docType.val() == ""){
		modify_formtip.css("color","red");
		modify_formtip.html("对不起，资讯类型不能为空。");
		docType.focus();
		return false;
	}
	else if( content.val() == "" ||  content.val() == "<br>"){
		modify_formtip.css("color","red");
		modify_formtip.html("对不起，资讯内容不能为空。");
		return false;
	}else{
		return true;
	}
}






//删除资讯
$(".delinformation").click(function(){
	id = $(this).attr("id");
	if(confirm("您确定要删除" + id + "号资讯吗？" )){
		$.ajax({
			url: 'deleteInformation.html',
			type: 'POST',
			data:{id:id},
			dataType: 'html',
			error: function(){
				alert("删除失败！error。");
			},
			success: function(result){
				if(result != "" && "success" == result){
					alert("删除成功！");
					window.location.href="infoManage.html";
				}else if("failed" == result){
					alert("删除失败！请重试。");
				}else if("nodata" == result){
					alert("对不起，没有任何数据需要处理！请重试。");
				}
			}
		});
	}
});

//查看资讯
$(".viewinformation").click(function(e){
	id = $(this).attr("id");
	$.ajax({
		url: 'viewInformation.html',
		type: 'POST',
		data:{id:id},
		dataType: 'html',
		error: function(){
			alert("获取资讯失败！请重试。");
		},
		success: function(result){
			if(result != ""){
				
				jsonStrInfo = eval("("+result+")");
				state = "未发布";
				if(jsonStrInfo.state == 1){
					state = "发布";
				}
				$("#viewContent").html("");
				
				$("#viewContent").append("<li>标题：<input type=\"text\" style=\"border:0px;\" disabled=\"disabled\" value=\""+jsonStrInfo.title+"\"/></li>");
				$("#viewContent").append("<li>发布状态："+state+"</li>");
				$("#viewContent").append("<li>发布人："+jsonStrInfo.publisher+"</li>");
				$("#viewContent").append("<li>发布时间："+jsonStrInfo.publishDate+"</li>");
				if(jsonStrInfo.fileName != null && jsonStrInfo.fileName != "" && jsonStrInfo.filePath != null && jsonStrInfo.filePath != ""){
					$("#viewContent").append("<li>附件类型："+jsonStrInfo.typeName+"</li>");
					$("#viewContent").append("<li>附件名称："+jsonStrInfo.fileName+"</li>");
					$("#viewContent").append("<li>附件存放路径：<a href='"+jsonStrInfo.filePath+"' target='_blank'>下载(右键另存为...)</a></li>");
					$("#viewContent").append("<li>附件大小："+(jsonStrInfo.fileSize/1024)+" KB</li>");
				}else{
					$("#viewContent").append("<li>附件：暂无</li>");
				}
				$("#viewContent").append("<li>上传时间："+jsonStrInfo.uploadDate+"</li>");
				$("#viewContent").append("<li>资讯内容：<div>"+jsonStrInfo.content+"</div></li>");
				e.preventDefault();
				$('#viewInfoDiv').modal('show');
				
			}else if("failed" == result){
				alert("获取"+title+"失败！请重试。");
			}else if("nodata" == result){
				alert("对不起，没有任何数据需要处理！请重试。");
			}
		}
	});
});









//发布资讯
$(".modifyInformationState").click(function(){
	modify = $(this);
	id= modify.attr("id");
	state = modify.attr("inforstate");
	infoState = new Object();
	infoState.id = id;
	if(state == "1"){
		infoState.state = 0;
		modify.attr("inforstate",0);
	}else{
		infoState.state = 1;
		modify.attr("inforstate",1);
	}
	
	$.ajax({
		url: 'information/modifyInfoState.html',
		type: 'POST',
		data:{inforState:JSON.stringify(infoState)},
		dataType: 'html',
		error: function(){
			alert("开启或关闭发布状态操作时失败！请重试。");
		},
		success: function(result){
			if("failed" == result){
				alert("开启或关闭发布状态操作时失败！请重试。");
			}else if("nodata" == result){
				alert("对不起，没有任何数据需要处理！请重试。");
			}else if(result == 'success'){
				$.post("viewInformation.html",{'id':id},function(data){
					if(data == "failed"){
						alert("获取失败！");
					}else if(data == "nodata"){
						alert("id为空！");
					}else{
						alert('发表成功！');
						$(".t_publisher"+id).html(data.publisher);
						$(".t_publishDate"+id).html(data.publishDate);
						$(".t_action"+id).html('<li><a class=\"viewinformation\" href=\"javascript:void(0)\" id=\"'+id+
								'\"><i class=\"icon-zoom-in\"></i> 查看</a></li>');
					}
			    },'json');
			}
		}
	});
});