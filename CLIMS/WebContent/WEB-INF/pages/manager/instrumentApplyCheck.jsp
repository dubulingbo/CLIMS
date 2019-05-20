<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/pages/common/head.jsp"%>
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
			<h2>
				<i class="icon-search"></i> 仪器变动审核管理
			</h2>
		</div>
		<div class="box-content" style="padding-top: 0px;">
			<ul class="nav nav-tabs" style="background: #EEE685;">
				<li><a href="javascript:void(0)"
					onclick="instApplyCheck_pageJump('instAssignApplyCheck.html','')">仪器调拨申请审核</a></li>
				<li><a href="javascript:void(0)"
					onclick="instApplyCheck_pageJump('instRepairApplyCheck.html','')">仪器维修申请审核</a></li>
				<li><a href="javascript:void(0)"
					onclick="instApplyCheck_pageJump('instScrapApplyCheck.html','')">仪器报废申请审核</a></li>
			</ul>
		</div>
		<div class="box-content" id="instApplyCheck_show">
			<div class="center">这里将展示仪器调拨、维修以及报废申请列表.</div>
		</div>
	</div>
	<!--/span-->
</div>
<!--/row-->

<!-- loading frame starts -->
<div class="theme-loadpage-popover">
	<div class="cssload-thecube">
		<div class="cssload-cube cssload-c1"></div>
		<div class="cssload-cube cssload-c2"></div>
		<div class="cssload-cube cssload-c4"></div>
		<div class="cssload-cube cssload-c3"></div>
	</div>
	<br>
	<div class="center" id="loading_tip"></div>
</div>
<div class="theme-loadbg-popover-mask"></div>
<!-- loading frame ends -->

<!-- 这里编辑页面要展示的内容  ends -->

<%@include file="/WEB-INF/pages/common/foot.jsp"%>
<script type="text/javascript">
//加载仪器调拨、维修和报废审核列表
function instApplyCheck_pageJump(url,pageIndex){
	$("#instApplyCheck_show").load(url,{'pageIndex':pageIndex},function (response,status,xhr) {
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


