<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>资料瞎下载</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="本网站将实现高校实验室仪器设备的自动化，高效地管理.">
<meta name="author" content="Alikos DubLBo">

<!-- The styles -->
<link href="/statics/css/bootstrap-cerulean.css" rel="stylesheet">
<style type="text/css">
	body {
		padding-bottom: 40px;
	}
	
	.sidebar-nav {
		padding: 9px 0;
	}
	
	.navbar .nav li a {
		border: 0px;
	}
	
	.custom-setting {
		
	}
	
	.clear {
		clear: both;
	}
	
	div .modal-body label {
		color: black;
	}
</style>
<link href="/statics/css/bootstrap-responsive.css" rel="stylesheet">
<link href="/statics/css/charisma-app.css" rel="stylesheet">
<link href="/statics/css/jquery-ui-1.8.21.custom.css" rel="stylesheet">
<link href='/statics/css/fullcalendar.css' rel='stylesheet'>
<link href='/statics/css/fullcalendar.print.css' rel='stylesheet' media='print'>
<link href='/statics/css/chosen.css' rel='stylesheet'>
<link href='/statics/css/uniform.default.css' rel='stylesheet'>
<link href='/statics/css/colorbox.css' rel='stylesheet'>
<link href='/statics/css/jquery.cleditor.css' rel='stylesheet'>
<link href='/statics/css/jquery.noty.css' rel='stylesheet'>
<link href='/statics/css/noty_theme_default.css' rel='stylesheet'>
<link href='/statics/css/elfinder.min.css' rel='stylesheet'>
<link href='/statics/css/elfinder.theme.css' rel='stylesheet'>
<link href='/statics/css/jquery.iphone.toggle.css' rel='stylesheet'>
<link href='/statics/css/opa-icons.css' rel='stylesheet'>
<link href='/statics/css/uploadify.css' rel='stylesheet'>

<!-- 自定义样式文件 start -->
<link href="/statics/localcss/cssload-thecube.css" rel="stylesheet">
<link href='/statics/localcss/userManage.css' rel='stylesheet'>
<link href='/statics/localcss/clims_common.css' rel='stylesheet'>
<link href='/statics/localcss/datumDownload.css' rel='stylesheet'>
<!-- 自定义样式文件 end -->

<link rel="shortcut icon" href="/statics/img/logo.ico">
<script type="text/javascript">var tt = '${mList}';</script>
</head>
<body>
	<!-- topbar starts -->
	<div class="navbar">
		<div class="navbar-inner">
			<div class="container-fluid">
				<a class="btn btn-navbar" data-toggle="collapse"
					data-target=".top-nav.nav-collapse,.sidebar-nav.nav-collapse">
					<span class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
				</a>
				<a class="brand" href="/main.html"> <img alt="CLIMS Logo" src="/statics/img/logo001.jpg" />
					<span>仪器管理系统</span></a>

				<div class="btn-group pull-right">
					<ul class="nav">
						<li><a>欢迎你，${currentUser.userName }</a></li>
						<li style="width: 260px;"><a>当前时间：<b id="currentTime_show" style="font-size: 15px;"></b></a></li>
					</ul>
				</div>
			</div>
		</div>
	</div>
	<!-- topbar ends -->

	<div class="container-fluid">
		<div class="row-fluid">
			
			<noscript>
				<div class="alert alert-block span10">
					<h4 class="alert-heading">Warning!</h4>
					<p>You need to have <a href="http://en.wikipedia.org/wiki/JavaScript" target="_blank">JavaScript</a> enabled to use this site.</p>
				</div>
			</noscript>
			
			
			<!-- content starts -->
			<div class="app">
				<div class="box span8" id="infoListDiv" style="float:left;">
				<c:if test="${page.items != null }">
					<table class="table">
						<tbody>
						<c:forEach items="${page.items }" var="info">
							<c:if test="${info.filePath != null }">
							<tr class="ziliao_row_css">
								<td><a href="${info.filePath }" class="ziliao_a_css" target="_blank">${info.fileName }</a></td>
								<td class="center" class="ziliao_time_css">
									<fmt:formatDate value="${info.publishDate}" pattern="yyyy-MM-dd"/>
								</td>                               
							</tr>
							</c:if>
						</c:forEach>
						</tbody>
					</table>  
					<div class="pagination pagination-centered">
					<ul>
						<c:choose>
							<c:when test="${page.currentPageNo == 1}">
								<li class="active"><a href="javascript:void(0)" title="首页">首页</a></li>
							</c:when>
							<c:otherwise>
								<li><a href="javascript:load_infopage('datumDownload.html','1')" title="首页">首页</a></li>
							</c:otherwise>
						</c:choose>
						
						<c:if test="${page.prevPages != null}">
							<c:forEach items="${page.prevPages}" var="num">
								<li><a href="javascript:load_infopage('datumDownload.html','${num}')" title="${num}">${num}</a></li>
							</c:forEach>
						</c:if>

						<li class="active"><a href="javascript:void(0)" title="${page.currentPageNo}">${page.currentPageNo}</a></li>

						<c:if test="${page.nextPages != null}">
							<c:forEach items="${page.nextPages}" var="num">
								<li><a href="javascript:load_infopage('datumDownload.html','${num}')" title="${num}">${num}</a></li>
							</c:forEach>
						</c:if>

						<c:if test="${page.totalPageCount != null}">
							<c:choose>
								<c:when test="${page.currentPageNo == page.totalPageCount}">
									<li class="active"><a href="javascript:void(0)" title="尾页">尾页</a></li>
								</c:when>
								<c:otherwise>
									<li><a href="javascript:load_infopage('datumDownload.html','${page.totalPageCount}')" title="尾页">尾页</a></li>
								</c:otherwise>
							</c:choose>
						</c:if>

						<c:if test="${page.totalPageCount == null}">
							<li class="active"><a href="javascript:void(0)" title="尾页">尾页</a></li>
						</c:if>
					</ul>
					</div>     
				</c:if>
				<c:if test="${page.items == null }">
					<div class="center">暂时没有资料下载！</div>
				</c:if>
				</div><!--/span-->
			
			
				<div class="box span4" style="float:left;margin-left:20px;">
					<div class="box-header well" style="cursor:default;">
						<h2>下载排行榜</h2>
					</div>
					<ul>
					<c:if test="${infoListhot != null }">
						<c:forEach items="${infoListhot }" var="infohot">
							<li><a href="${infohot.filePath }" target="_blank">${infohot.fileName }</a></li>
						</c:forEach>
					</c:if>
					</ul>
				</div>
			</div>
			<!-- content ends -->
			
		</div><!--/fluid-row-->
					
		<hr>
		<!-- footer starts -->
		<footer>
			<p class="center">
				<span>CLIMS开发团队&nbsp;&nbsp;&copy;2019&nbsp;&nbsp;DubLBo&emsp;版权所有</span>
			</p>
			<p class="center">
				Contact us: <b>&nbsp;dubulingbo@163.com</b>
			</p>
		</footer>
		<!-- footer ends -->

	</div>
	<!--/.fluid-container-->



	<!-- external javascript
	================================================== -->
	<!-- Placed at the end of the document so the pages load faster -->

	<!-- jQuery -->
	<script src="/statics/js/jquery-1.7.2.min.js"></script>
	<!-- jQuery UI -->
	<script src="/statics/js/jquery-ui-1.8.21.custom.min.js"></script>
	<!-- transition / effect library -->
	<script src="/statics/js/bootstrap-transition.js"></script>
	<!-- alert enhancer library -->
	<script src="/statics/js/bootstrap-alert.js"></script>
	<!-- modal / dialog library -->
	<script src="/statics/js/bootstrap-modal.js"></script>
	<!-- custom dropdown library -->
	<script src="/statics/js/bootstrap-dropdown.js"></script>
	<!-- scrolspy library -->
	<script src="/statics/js/bootstrap-scrollspy.js"></script>
	<!-- library for creating tabs -->
	<script src="/statics/js/bootstrap-tab.js"></script>
	<!-- library for advanced tooltip -->
	<script src="/statics/js/bootstrap-tooltip.js"></script>
	<!-- popover effect library -->
	<script src="/statics/js/bootstrap-popover.js"></script>
	<!-- button enhancer library -->
	<script src="/statics/js/bootstrap-button.js"></script>
	<!-- accordion library (optional, not used in demo) -->
	<script src="/statics/js/bootstrap-collapse.js"></script>
	<!-- carousel slideshow library (optional, not used in demo) -->
	<script src="/statics/js/bootstrap-carousel.js"></script>
	<!-- autocomplete library -->
	<script src="/statics/js/bootstrap-typeahead.js"></script>
	<!-- tour library -->
	<script src="/statics/js/bootstrap-tour.js"></script>
	<!-- library for cookie management -->
	<script src="/statics/js/jquery.cookie.js"></script>
	<!-- calander plugin -->
	<script src='/statics/js/fullcalendar.min.js'></script>
	<!-- data table plugin -->
	<script src='/statics/js/jquery.dataTables.min.js'></script>

	<!-- chart libraries start -->
	<script src="/statics/js/excanvas.js"></script>
	<script src="/statics/js/jquery.flot.min.js"></script>
	<script src="/statics/js/jquery.flot.pie.min.js"></script>
	<script src="/statics/js/jquery.flot.stack.js"></script>
	<script src="/statics/js/jquery.flot.resize.min.js"></script>
	<!-- chart libraries end -->

	<!-- select or dropdown enhancer -->
	<script src="/statics/js/jquery.chosen.min.js"></script>
	<!-- checkbox, radio, and file input styler -->
	<script src="/statics/js/jquery.uniform.min.js"></script>
	<!-- plugin for gallery image view -->
	<script src="/statics/js/jquery.colorbox.min.js"></script>
	<!-- rich text editor library -->
	<script src="/statics/js/jquery.cleditor.min.js"></script>
	<!-- notification plugin -->
	<script src="/statics/js/jquery.noty.js"></script>
	<!-- file manager library -->
	<script src="/statics/js/jquery.elfinder.min.js"></script>
	<!-- star rating plugin -->
	<script src="/statics/js/jquery.raty.min.js"></script>
	<!-- for iOS style toggle switch -->
	<script src="/statics/js/jquery.iphone.toggle.js"></script>
	<!-- autogrowing textarea plugin -->
	<script src="/statics/js/jquery.autogrow-textarea.js"></script>
	<!-- multiple file upload plugin -->
	<script src="/statics/js/jquery.uploadify-3.1.min.js"></script>
	<!-- history.js for cross-browser state change on ajax -->
	<script src="/statics/js/jquery.history.js"></script>
	<!-- application script for Charisma demo -->
	<script src="/statics/js/charisma.js"></script>

	<!--  start 2019-->
	<script src="/statics/js/json2.js"></script>
	<!-- add by DubLBo 2019-04-18 multiple file ajaxfileupload plugin-->
	<script type="text/javascript" src="/statics/js/ajaxfileupload.js"></script>
	<!-- add by DubLBo 2019-04-18 WdatePicker plugin-->
	<script type="text/javascript" src="/statics/medire/WdatePicker.js"></script>
	<script type="text/javascript" src="/statics/localjs/clims_common.js"></script>
	<!-- 外部引用的js文件 end 2019-->
	
	<script type="text/javascript">
	$(document).ready(function(){
		$("#infoListDiv").load("loadInfoDownload.html #datumDownloadTable",{'pageIndex':0},function(data,status){
			if(data == "nodata"){
				alert('没有数据传到后台，请重试。');
			}else if(data == "failed"){
				alert('获取列表失败，请重试！');
			}
		});
	});
	
	function load_infopage(pageIndex){
		$("#infoListDiv").load("loadInfoDownload.html #datumDownloadTable",{'pageIndex':pageIndex},function(data){
			switch (status){ //"success", "notmodified", "error", "timeout" 或 "parsererror"
	            case 'success' :case 'notmodified' :
	            	break;
	            case 'error' :case 'parsererror' :
	                alert('加载失败！');
	                break;
	            case 'timeout':
	            	alert('加载超时，请检查网络连接，并重新登录！');
	        }
		});
	}
	
	</script>
</html>