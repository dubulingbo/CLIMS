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
	<div class="box span12">
		<div class="box-header well" data-orginal-title>
			<h2><i class="icon-info-sign"></i> 公告栏</h2>
			<div class="box-icon">
				<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
			</div>
		</div>
		<div class="box-content">
			暂无公告，
		</div>
	</div><!--/span-->
</div>		

<div class="row-fluid sortable">
	<div class="box span12">
		<div class="box-header well" data-orginal-title>
			<h2><i class="icon-list"></i> 资讯栏</h2>
			<div class="box-icon">
				<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
			</div>
		</div>
		<div class="box-content">
			暂无最新资讯！
			<ul class="dashboard-list">
				<li>
					<a href="test.html">
						<i class="icon-arrow-up"></i>                               
						<span class="green">92</span>
						New Comments                                    
					</a>
				</li>
			</ul>
		</div>
	</div><!--/span-->
</div>	

<%@include file="/WEB-INF/pages/common/foot.jsp" %>
    