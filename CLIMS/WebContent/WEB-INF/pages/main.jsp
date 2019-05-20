<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/pages/common/head.jsp"%>
<style>
.info-title-css {
	overflow: hidden;
	text-overflow: ellipsis;
	white-space: nowrap;
}

.info-title-css a {
	text-decoration: none;
}
</style>
<div>
	<ul class="breadcrumb">
		<li>导航：主页</li>
	</ul>
</div>

<div class="sortable row-fluid">
	<a data-rel="tooltip" title="6 new members."
		class="well span3 top-block" href="#"> <span
		class="icon32 icon-red icon-user"></span>
		<div>系统用户量</div>
		<div>507</div> <span class="notification">6</span>
	</a> <a data-rel="tooltip" title="1231312312312 new messages."
		class="well span3 top-block" href="#"> <span
		class="icon32 icon-color icon-envelope-closed"></span>
		<div>信息</div>
		<div>23423423423423432</div> <span class="notification red">1231312312312</span>
	</a>
</div>


<div class="row-fluid sortable">
	<div class="box span7">
		<div class="box-header well" data-orginal-title>
			<h2>
				<i class="icon-bullhorn"></i>
			</h2>
			<div class="box-icon">
				<a
					style="display: block; width: 60px; padding-right: 1px; font-size: 14px;"
					href="/informanage/portalafficheList.html">more >></a>
			</div>
		</div>
		<div class="box-content">
			<h1>
				公告 <small style="font-size: 12px;">展示仪器申购消息最新消息、仪器申请调拨及审核进度的最新消息，仪器维修最新消息等</small>
			</h1>
			<hr>
			<c:if test="${afficheList != null }">
				<ul style="font-size: 15px;">
					<c:forEach items="${afficheList }" var="affiche">
						<li style="padding-bottom: 5px;">${affiche.content }</li>
					</c:forEach>
				</ul>
			</c:if>
			<c:if test="${afficheList == null }">
				<div class="center">
					<p>
						<b>暂无最新公告！</b>
					</p>
				</div>
			</c:if>
		</div>
		<!-- /box-content -->
	</div>
	<!--/box span7-->

	<div class="box span5">
		<div class="box-header well" data-orginal-title>
			<h2>
				<i class="icon-envelope"></i>
			</h2>
			<div class="box-icon">
				<a
					style="display: block; width: 60px; padding-right: 1px; font-size: 14px;"
					href="#">more >></a>
			</div>
		</div>
		<div class="box-content">
			<h1>
				资讯 <small style="font-size: 12px;">展示本网站的最新消息以及后续系统更新和维护的最新消息等</small>
			</h1>
			<hr>

			<div class="row-fluid info-title-css">
				<a href="#" title="关于仪器变动申请及管理员处理过程说明情况，你知道吗">
					》关于仪器变动申请及管理员处理过程说明情况，你知道吗 </a>
			</div>

			<div class="row-fluid info-title-css">
				<a href="#" title="3213121变动申请及管理员处理过程说明情况，你知道吗123123">
					》3213121变动申请及管理员处理过程说明情况，你知道吗12312324232141241241431241241412413341231243
				</a>
			</div>


		</div>
	</div>
	<!--/span-->
</div>

<%@include file="/WEB-INF/pages/common/foot.jsp"%>
