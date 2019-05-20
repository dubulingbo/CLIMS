<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>实验室仪器管理</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="本网站将实现高校实验室仪器设备的自动化，高效地管理.">
<meta name="author" content="Alikos DubLBo">

<!-- The styles -->
<link
	href="${pageContext.request.contextPath }/statics/css/bootstrap-cerulean.css"
	rel="stylesheet">
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
.custom-setting{}
.clear {
	clear: both;
}
div .modal-body label {
	color: black;
}
</style>
<link
	href="${pageContext.request.contextPath }/statics/css/bootstrap-responsive.css"
	rel="stylesheet">
<link
	href="${pageContext.request.contextPath }/statics/css/charisma-app.css"
	rel="stylesheet">
<link
	href="${pageContext.request.contextPath }/statics/css/jquery-ui-1.8.21.custom.css"
	rel="stylesheet">
<link
	href='${pageContext.request.contextPath }/statics/css/fullcalendar.css'
	rel='stylesheet'>
<link
	href='${pageContext.request.contextPath }/statics/css/fullcalendar.print.css'
	rel='stylesheet' media='print'>
<link href='${pageContext.request.contextPath }/statics/css/chosen.css'
	rel='stylesheet'>
<link
	href='${pageContext.request.contextPath }/statics/css/uniform.default.css'
	rel='stylesheet'>
<link
	href='${pageContext.request.contextPath }/statics/css/colorbox.css'
	rel='stylesheet'>
<link
	href='${pageContext.request.contextPath }/statics/css/jquery.cleditor.css'
	rel='stylesheet'>
<link
	href='${pageContext.request.contextPath }/statics/css/jquery.noty.css'
	rel='stylesheet'>
<link
	href='${pageContext.request.contextPath }/statics/css/noty_theme_default.css'
	rel='stylesheet'>
<link
	href='${pageContext.request.contextPath }/statics/css/elfinder.min.css'
	rel='stylesheet'>
<link
	href='${pageContext.request.contextPath }/statics/css/elfinder.theme.css'
	rel='stylesheet'>
<link
	href='${pageContext.request.contextPath }/statics/css/jquery.iphone.toggle.css'
	rel='stylesheet'>
<link
	href='${pageContext.request.contextPath }/statics/css/opa-icons.css'
	rel='stylesheet'>
<link
	href='${pageContext.request.contextPath }/statics/css/uploadify.css'
	rel='stylesheet'>

<!-- 自定义样式文件 start -->
<link
	href="${pageContext.request.contextPath }/statics/localcss/cssload-thecube.css"
	rel="stylesheet">
<link
	href='${pageContext.request.contextPath }/statics/localcss/userManage.css'
	rel='stylesheet'>
<link
	href='${pageContext.request.contextPath }/statics/localcss/clims_common.css'
	rel='stylesheet'>
<!-- 自定义样式文件 end -->

<!-- The HTML5 shim, for IE6-8 support of HTML5 elements -->
<!--[if lt IE 9]>
	  <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
	<![endif]-->

<!-- The fav icon -->
<link rel="shortcut icon"
	href="${pageContext.request.contextPath }/statics/img/logo.ico">
<script type="text/javascript"> var tt = '${mList}';</script>
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
				</a> <a class="brand" href="/main.html"> <img alt="CLIMS Logo"
					src="${pageContext.request.contextPath }/statics/img/logo001.jpg" />
					<span>仪器管理系统</span></a>

				<!-- user dropdown starts -->
				<div class="btn-group pull-right" style="padding-top: 10px;">
					<a class="btn dropdown-toggle" data-toggle="dropdown" href="#">
						<i class="icon-user"></i><span class="hidden-phone">
							${currentUser.userName }&nbsp;&nbsp;</span> <span class="caret"></span>
					</a>
					<ul class="dropdown-menu">
						<li style="padding-left: 20px;"><b style="color: green;">角色</b>&nbsp;&nbsp;<span
							class="label label-success">${currentUser.userRoleName }</span></li>
						<li class="divider"></li>
						<li><a href="/backend/modifyInfo.html">完善/查看个人信息</a></li>
						<li><a href="javascript:void();"
							class="btn-setting modifypwd">修改个人密码</a></li>
						<li><a href="/logout.html">注销</a></li>
					</ul>
				</div>
				<!-- user dropdown ends -->

				<div class="btn-group pull-right">
					<ul class="nav">
						<li><a>欢迎你，${currentUser.userName }</a></li>
						<li style="width: 260px;"><a>当前时间：<b
								id="currentTime_show" style="font-size: 15px;"></b></a></li>
					</ul>
				</div>

				<div class="modal hide fade" id="myModal">
					<div class="modal-header">
						<button type="button" class="close modifyPwdCancel"
							data-dismiss="modal">×</button>
						<h3>修改密码</h3>
					</div>
					<div class="modal-body">
						<p>
							<label>请输入原密码：</label><input id="oldpassword" type="password"><span
								style="color: red; font-weight: bold;">*</span> <label>请输入新密码：</label><input
								id="newpassword" type="password"><span
								style="color: red; font-weight: bold;">*新密码必须6位以上</span> <label>再次输入新密码：</label><input
								id="aginpassword" type="password"><span
								style="color: red; font-weight: bold;">*</span>
						</p>
						<p id="modifypwdtip"></p>
					</div>
					<div class="modal-footer">
						<a href="#" class="btn modifyPwdCancel" data-dismiss="modal">取消</a>
						<a href="#" id="modifySavePassword" class="btn btn-primary">修改</a>
					</div>
				</div>


			</div>
		</div>
	</div>
	<!-- topbar ends -->


	<div class="container-fluid">
		<div class="row-fluid">

			<!-- left menu starts -->
			<div class="span2 main-menu-span">
				<div class="well nav-collapse sidebar-nav">
					<ul class="nav nav-tabs nav-stacked main-menu" id="menus">
						<!-- menu content start -->


						<!-- menu content end -->
					</ul>
					<label class="hidden-tablet" for="is-ajax"><b>&emsp;Menu
							end</b></label>
				</div>
				<!--/.well -->
			</div>
			<!--/span-->
			<!-- left menu ends -->

			<noscript>
				<div class="alert alert-block span10">
					<h4 class="alert-heading">Warning!</h4>
					<p>
						You need to have <a href="http://en.wikipedia.org/wiki/JavaScript"
							target="_blank">JavaScript</a> enabled to use this site.
					</p>
				</div>
			</noscript>

			<div id="content" class="span10">
				<!-- content starts -->