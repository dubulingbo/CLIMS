
$("#informationuploadbtn").click(function(){
	informationFileUpload();
});

$(".addInfoSubmitBtn").click(function(){
	if(addInfoFormValidate()){
		//提交表单
		$("#addInfo_form").onsubmit();
		clearAddInfoForm();
	}
});

function informationFileUpload()
{
	if($("#uploadInformationFile").val() == "" || $("#uploadInformationFile").val()  == null){
		alert("请选择上传文件！");
	}else{
		$.ajaxFileUpload({ 
           url:'information/uploadInfoFile.html', //处理上传文件的服务端
           secureuri:false,
           fileElementId:'uploadInformationFile',
           dataType: 'json',
           success: function(data) { 
        	   data = data.replace(/(^\s*)|(\s*$)/g, "");
        	   if(data == "3"){
        		   alert("上传图片大小不得超过50M！");
        		   $("#uniform-uploadInformationFile span:first").html('无文件');
        		   $("#uploadInformationFile").change(function(){
        			   var fn = $("#uploadInformationFile").val(); 
        			   if($.browser.msie){
        				   fn = fn.substring(fn.lastIndexOf("\\")+1);
        			   }
        			   $("#uniform-uploadInformationFile span:first").html(fn);
        		   });
        	   }else if(data == "1"){
        		   alert("上传失败，请重试。")
        	   }else if(data == "5"){
        		   alert("没有文件可上传。")
        	   }else{
        		   var oldFile = data.substring(0,data.indexOf("[[[]]]"));
        		   var newFile = data.substring(data.indexOf("[[[]]]")+6,data.indexOf("size:"));
        		   var fileSize = data.substring(data.indexOf("size:")+5);
        		   $("#uploadfilenamehide").val(oldFile);
        		   $("#uploadfilepathhide").val(newFile);
        		   $("#uploadfilesizehide").val(fileSize);
        		   $("#filearea").css("color","green");
        		   $("#filearea").html("已上传文件：" + oldFile + "&emsp;大小："+(fileSize/1024)+
        				   " KB&emsp;&emsp;<a href=\"javascript:delFile_addInfo();\"> <li class=\"icon-trash\"></li></a>");
        		   $("#filearea").show();
        		   $("#informationuploadbtn").hide();
        		   $("#uploadInformationFile").change(function(){
        			   var fn = $("#uploadInformationFile").val(); 
        			   if($.browser.msie){
        				   fn = fn.substring(fn.lastIndexOf("\\")+1);
        			   }
        			   $("#uniform-uploadInformationFile span:first").html(fn);
        		   });
        	   }
           },  
           error: function() {  
              alert("上传失败！error。");
           } 
		});
	}
}

function delFile_addInfo(){
	$.ajax({
		url: 'information/delInfoFile.html',
		type: 'POST',
		data:{filePath:$("#uploadfilepathhide").val(),flag:1},
		dataType: 'html',
		timeout: 1000,
		error: function(){
			alert("删除文件失败！请重试。");
		},
		success: function(result){
			if(result != "" && result == "success"){
				$("#informationuploadbtn").show();
				$("#filearea").html("");
				$("#filearea").hide();
       		    $("#uploadfilenamehide").val('');
    		    $("#uploadfilepathhide").val('');
    		    $("#uploadfilesizehide").val('');
    		    $("#uniform-uploadInformationFile span:first").html('无文件');
			}else if("failed" == result){
				alert("删除文件失败！请重试。");
			}else if("nodata" == result){
				alert("对不起，没有任何数据需要处理！请重试。");
			}
		}
	});
}

function clearAddInfoForm(){
	$("#informationTitle").val('');
	$("#docType").val('');
	$("#uniform-uploadInformationFile span:first").html('无文件');
	$("#informationuploadbtn").show();
	$("#uploadfilepathhide").val('');
	$("#uploadfilenamehide").val('');
    $("#uploadfilesizehide").val('');
    $("#filearea").html('');
    $("#filearea").hide();
	$("#informationContent").val('');
}

function addInfoFormValidate(){
	infoTitle = $("#informationTitle");
	docType = $("#docType");
	filePath = $("#uploadfilepathhide");
	content = $("#informationContent");
//	infoTitle.val(CoverFormXmlTag(infoTitle.val()));
	alert('filePath : ' + filePath.val());
	if($.trim(infoTitle.val()) == "" || infoTitle.val() == null){
		alert('资讯标题不能为空。');
		infoTitle.focus();
		return false;
	}else if($.trim(docType.val()) == null || $.trim(docType.val()) == ""){
		alert('资讯类型不能为空。');
		docType.focus();
		return false;
	}
//	else if( filePath.val() == ""){
//		add_formtip.css("color","red");
//		add_formtip.html("对不起，您还没有上传文件。");
//		return false;
//	}
	else if( content.val() == "" ||  content.val() == "<br>"){
		alert('资讯内容不能为空。');
		return false;
	}else{
		return true;
	}
}
