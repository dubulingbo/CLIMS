<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/pages/common/head.jsp" %>
<!-- 这里编辑每个页面的导航条  starts -->

<div>
	<ul class="breadcrumb">
		<li>导航：<a href="/main.html">主页</a> <span class="divider">/</span></li>
		<li>仪器变动审核</li>
	</ul>
</div>
<!-- 这里编辑每个页面的导航条  ends -->


<!-- 这里编辑页面要展示的内容  starts -->
<div class="row-fluid sortable">
	<div class="box span12">
		<div class="box-header well" data-original-title>
			<h2><i class="icon-search"></i> 仪器变动审核管理</h2>
		</div>
		<div class="box-content" style="padding-top:0px;">
			<ul class="nav nav-tabs" style="background:#EEE685;">
				<li><a href="instAssignApplyCheck.html" target="_showTable">仪器调拨申请审核</a></li>
				<li><a href="instRepairApplyCheck.html" target="_showTable">仪器维修申请审核</a></li>
				<li><a href="instScrapApplyCheck.html" target="_showTable">仪器报废申请审核</a></li>
			</ul>
		</div >
		<div class="box-content" style="padding-top:0px;">
			<iframe style="width:100%;height:450px;margin:0;padding:0;border:0;" name="_showTable"></iframe>       
		</div>
	</div><!--/span-->
</div><!--/row-->



<!-- 这里编辑页面要展示的内容  ends -->


<%@include file="/WEB-INF/pages/common/foot.jsp" %>
<script type="text/javascript" src="/statics/localjs/instrumentApplyCheck.js"></script>


