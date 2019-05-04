<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/pages/common/head.jsp" %>

<div>
	<ul class="breadcrumb">
		<li>
			导航：主页
		</li>
	</ul>
</div>
	
<div class="row-fluid sortable">
	<div class="box span7">
		<div class="box-header well" data-orginal-title>
			<h2><i class="icon-envelope"></i></h2>
			<div class="box-icon">
				<a style="display:block;width:60px;padding-right:1px;font-size:14px;" href="/informanage/portalafficheList.html">more >></a>
			</div>
		</div>
		<div class="box-content">
			<h1>资讯 <small>主要展示本网站的最新消息以及后续系统更新和维护的最新消息...</small></h1>
			<hr>
			<div class="row-fluid" style="height:40px;">
				<div style="display:inline-block;">
					<ul>
						<a href="#">
							<i class="icon-arrow-up"></i>
							<b style="font-size:15px;">title</b>
						</a>
					</ul>
				</div>
				<div style="display:inline-block;margin-left:10px;font-size:15px;">2019-03-21</div>
			</div>		
		</div><!-- /box-content -->
	</div><!--/box span7-->
	
	<div class="box span5">
		<div class="box-header well" data-orginal-title>
			<h2><i class="icon-bullhorn"></i></h2>
			<div class="box-icon">
				<a style="display:block;width:60px;padding-right:1px;font-size:14px;" href="/informanage/portalafficheList.html">more >></a>
			</div>
		</div>
		<div class="box-content">
			<h1>公告 <small>展示仪器申购消息最新消息、仪器申请调拨及审核进度的最新消息，仪器维修最新消息...</small></h1>
			<hr>
			<c:if test="${afficheList != null }">
				<c:forEach items="${afficheList }" var="affiche">
					<div class="box-content alerts">
						<div class="alert alert-info">${affiche.content }</div>
					</div>
				</c:forEach>
			</c:if>
			
			<c:if test="${afficheList == null }">
				<div class="center">
					<p><b>暂无最新公告！</b></p>
				</div>
			</c:if>
		</div>
	</div><!--/span-->
</div>

<%@include file="/WEB-INF/pages/common/foot.jsp" %>
    