<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>实验室仪器管理系统</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description"
	content="Charisma, a fully featured, responsive, HTML5, Bootstrap admin template.">

<!-- The styles -->

<style type="text/css">
body {
	padding-bottom: 40px;
	background-image: url('/statics/img/bg_index.jpg');
	background-repeat: no-repeat;
	background-size: cover;
}

.sidebar-nav {
	padding: 9px 0;
}
</style>
<link
	href="${pageContext.request.contextPath }/statics/css/bootstrap-cerulean.css"
	rel="stylesheet">
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
<!-- The HTML5 shim, for IE6-8 support of HTML5 elements -->
<!--[if lt IE 9]>
	  <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
	<![endif]-->

<!-- The fav icon -->
<link rel="shortcut icon"
	href="${pageContext.request.contextPath }/statics/img/logo.ico">

</head>

<body>
	<div class="container-fluid">
		<div class="row-fluid">

			<div class="row-fluid">
				<div class="span12 center login-header">
					<h2 style="color: green;">College Laboratory Instrument Manage
						System</h2>
				</div>
				<!--/span-->
			</div>
			<!--/row-->

			<div class="row-fluid sortable">
				<div class="box-content">
					<div class="row-fluid">

						<div class="span4">
							<br>
							<br>
							<br>
							<br>
							<div
								style="background: rgba(0, 0, 0, 0.5) none repeat scroll; padding: 10px;">
								<h2>系统简介</h2>
								<p
									style="color: #F8F8FF; font-size: 15px; padding: 5px; text-align: justify;">
									高校实验室仪器管理系统主要任务是对设备进行综合管理，做到全面规划、合理选购、正确维护、科学检修、适时更新，使设备达到最佳状态，充分发挥设备的效能和利用效率。提高教育质量，加大管理人员对实验室设备的管理力度。
								</p>
							</div>
						</div>


						<div class="span7">
							<div class="row-fluid">
								<div class="well span7 center login-box">
									<div class="alert alert-info" style="color: green;">
										建议使用Chrome、Firefox浏览器以及IE11以上的版本.</div>
									<div class="form-horizontal">
										<fieldset>
											<div class="input-prepend" title="学生学号/教师工号"
												data-rel="tooltip">
												<span class="add-on"><i class="icon-user"></i></span> <input
													autofocus class="input-large span10" name="userCode"
													id="userCode" type="text" />
											</div>
											<div class="clearfix"></div>

											<div class="input-prepend" title="登录密码" data-rel="tooltip">
												<span class="add-on"><i class="icon-lock"></i></span> <input
													class="input-large span10" name="userPassword"
													id="userPassword" type="password" />
											</div>
											<div class="clearfix"></div>

											<ul id="formtip"></ul>
											<p class="center span5">
												<button type="submit" class="btn btn-primary" id="loginBtn">登
													录</button>
											</p>
										</fieldset>
									</div>
								</div>
								<!--/span-->
							</div>
							<!--/row-->

						</div>
					</div>
				</div>
			</div>





		</div>
		<!--/fluid-row-->
	</div>
	<!--/.fluid-container-->

	<!-- external javascript
	================================================== -->
	<!-- Placed at the end of the document so the pages load faster -->

	<!-- jQuery -->
	<script
		src="${pageContext.request.contextPath }/statics/js/jquery-1.7.2.min.js"></script>
	<!-- jQuery UI -->
	<script
		src="${pageContext.request.contextPath }/statics/js/jquery-ui-1.8.21.custom.min.js"></script>
	<!-- transition / effect library -->
	<script
		src="${pageContext.request.contextPath }/statics/js/bootstrap-transition.js"></script>
	<!-- alert enhancer library -->
	<script
		src="${pageContext.request.contextPath }/statics/js/bootstrap-alert.js"></script>
	<!-- modal / dialog library -->
	<script
		src="${pageContext.request.contextPath }/statics/js/bootstrap-modal.js"></script>
	<!-- custom dropdown library -->
	<script
		src="${pageContext.request.contextPath }/statics/js/bootstrap-dropdown.js"></script>
	<!-- scrolspy library -->
	<script
		src="${pageContext.request.contextPath }/statics/js/bootstrap-scrollspy.js"></script>
	<!-- library for creating tabs -->
	<script
		src="${pageContext.request.contextPath }/statics/js/bootstrap-tab.js"></script>
	<!-- library for advanced tooltip -->
	<script
		src="${pageContext.request.contextPath }/statics/js/bootstrap-tooltip.js"></script>
	<!-- popover effect library -->
	<script
		src="${pageContext.request.contextPath }/statics/js/bootstrap-popover.js"></script>
	<!-- button enhancer library -->
	<script
		src="${pageContext.request.contextPath }/statics/js/bootstrap-button.js"></script>
	<!-- accordion library (optional, not used in demo) -->
	<script
		src="${pageContext.request.contextPath }/statics/js/bootstrap-collapse.js"></script>
	<!-- carousel slideshow library (optional, not used in demo) -->
	<script
		src="${pageContext.request.contextPath }/statics/js/bootstrap-carousel.js"></script>
	<!-- autocomplete library -->
	<script
		src="${pageContext.request.contextPath }/statics/js/bootstrap-typeahead.js"></script>
	<!-- tour library -->
	<script
		src="${pageContext.request.contextPath }/statics/js/bootstrap-tour.js"></script>
	<!-- library for cookie management -->
	<script
		src="${pageContext.request.contextPath }/statics/js/jquery.cookie.js"></script>
	<!-- calander plugin -->
	<script
		src='${pageContext.request.contextPath }/statics/js/fullcalendar.min.js'></script>
	<!-- data table plugin -->
	<script
		src='${pageContext.request.contextPath }/statics/js/jquery.dataTables.min.js'></script>

	<!-- chart libraries start -->
	<script
		src="${pageContext.request.contextPath }/statics/js/excanvas.js"></script>
	<script
		src="${pageContext.request.contextPath }/statics/js/jquery.flot.min.js"></script>
	<script
		src="${pageContext.request.contextPath }/statics/js/jquery.flot.pie.min.js"></script>
	<script
		src="${pageContext.request.contextPath }/statics/js/jquery.flot.stack.js"></script>
	<script
		src="${pageContext.request.contextPath }/statics/js/jquery.flot.resize.min.js"></script>
	<!-- chart libraries end -->

	<!-- select or dropdown enhancer -->
	<script
		src="${pageContext.request.contextPath }/statics/js/jquery.chosen.min.js"></script>
	<!-- checkbox, radio, and file input styler -->
	<script
		src="${pageContext.request.contextPath }/statics/js/jquery.uniform.min.js"></script>
	<!-- plugin for gallery image view -->
	<script
		src="${pageContext.request.contextPath }/statics/js/jquery.colorbox.min.js"></script>
	<!-- rich text editor library -->
	<script
		src="${pageContext.request.contextPath }/statics/js/jquery.cleditor.min.js"></script>
	<!-- notification plugin -->
	<script
		src="${pageContext.request.contextPath }/statics/js/jquery.noty.js"></script>
	<!-- file manager library -->
	<script
		src="${pageContext.request.contextPath }/statics/js/jquery.elfinder.min.js"></script>
	<!-- star rating plugin -->
	<script
		src="${pageContext.request.contextPath }/statics/js/jquery.raty.min.js"></script>
	<!-- for iOS style toggle switch -->
	<script
		src="${pageContext.request.contextPath }/statics/js/jquery.iphone.toggle.js"></script>
	<!-- autogrowing textarea plugin -->
	<script
		src="${pageContext.request.contextPath }/statics/js/jquery.autogrow-textarea.js"></script>
	<!-- multiple file upload plugin -->
	<script
		src="${pageContext.request.contextPath }/statics/js/jquery.uploadify-3.1.min.js"></script>
	<!-- history.js for cross-browser state change on ajax -->
	<script
		src="${pageContext.request.contextPath }/statics/js/jquery.history.js"></script>
	<!-- application script for Charisma demo -->
	<script
		src="${pageContext.request.contextPath }/statics/js/charisma.js"></script>
	<script
		src="${pageContext.request.contextPath }/statics/localjs/index.js"></script>


</body>
</html>
