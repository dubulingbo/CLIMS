<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/pages/common/head.jsp"%>
<link href="/statics/localcss/cssload-thecube.css" rel="stylesheet">
<!-- 这里编辑每个页面的导航条  starts -->

<div>
	<ul class="breadcrumb">
		<li>导航：<a href="main.html">主页</a> <span class="divider">/</span></li>
		<li>仪器变动审核</li>
	</ul>
</div>
<!-- 这里编辑每个页面的导航条  ends -->




<!-- 这里编辑页面要展示的内容  starts -->
<div class="row-fluid sortable">
	<div class="box span12">
		<div class="box-header well">
			<h2>
				<i class="icon-th"></i> 仪器变动申请审核
			</h2>
		</div>
		<div class="box-content">
			<ul class="nav nav-tabs" id="myTab">
				<li class="active"><a href="#info">仪器调拨申请审核</a></li>
				<li><a href="#custom">仪器维修申请审核</a></li>
				<li><a href="#messages">仪器报废申请审核</a></li>
			</ul>

			<!-- 调拨列表 -->
			<div id="myTabContent" class="tab-content">
				<div class="tab-pane active" id="info">
					<div class="box-content">
						<c:if test="${assignList != null }">
							<table class="table table-striped table-bordered">
								<thead>
									<tr>
										<th>仪器名称</th>
										<th>申请数量</th>
										<th>申请日期</th>
										<th>申请人</th>
										<th>状态</th>
										<th>操作</th>
									</tr>
								</thead>
								<tbody id="instAssignApplyTable">
									<c:forEach items="${assignList }" var="assign">
										<tr>
											<td>${assign.instrumentName }</td>
											<td>${assign.assignNumber }</td>
											<td class="center"><fmt:formatDate
													value="${assign.creationDate }"
													pattern="yyyy-MM-dd HH:mm:ss" /></td>
											<td class="center">${assign.createdByName }</td>
											<td class="center" id="instAssignApplyCheckStatus"><c:if
													test="${assign.status == 1 }">
													<span class="label label-warning">申请中</span>
												</c:if> <c:if test="${assign.status == 2 }">
													<span class="label label-success">已审核</span>
												</c:if> <c:if test="${assign.status == 10 }">
													<span class="label label-important">已拒绝</span>
												</c:if></td>
											<td class="center"><a
												class="btn btn-success instAssignApplyAgree"
												href="javascript:void();" id="${assign.id }"
												status="${assign.status }"><i class="icon-ok icon-white"></i>同意</a>
												<a class="btn btn-danger instAssignApplyRefuse" href="#"
												id="${assign.id }" status="${assign.status }"><i
													class="icon-remove icon-white"></i>拒绝</a></td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
							<div class="pagination pagination-centered"
								id="instAssignApplyTable_pagination">
								<ul>
									<c:choose>
										<c:when test="${pageIndex >= 1 }">
											<li><a
												href="instAssignApplyTable.html?pageIndex=${pageIndex-1 }">上一页</a></li>
										</c:when>
										<c:otherwise>
											<li class="active"><a href="#">上一页</a></li>
										</c:otherwise>
									</c:choose>

									<li class="active"><a href="#">${pageIndex}</a></li>
									<li><a
										href="instAssignApplyTable.html?pageIndex=${pageIndex+1 }">下一页</a></li>
								</ul>
							</div>
						</c:if>
						<c:if test="${assignList == null }">
							<div class="center">没有要处理的调拨申请！</div>
						</c:if>
					</div>
				</div>



				<div class="tab-pane" id="custom">
					<div class="box-content">
						<table class="table table-striped table-bordered">
							<thead>
								<tr>
									<th>仪器名称</th>
									<th>申请数量</th>
									<th>申请日期</th>
									<th>申请人</th>
									<th>状态</th>
									<th>操作</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td>Brown Blue</td>
									<td>Worth Name</td>
									<td class="center">2012/03/01</td>
									<td class="center">Member</td>
									<td class="center"><span class="label label-warning">申请中</span>
									</td>
									<td class="center"><a class="btn btn-success" href="#"><i
											class="icon-ok icon-white"></i>同意</a> <a class="btn btn-danger"
										href="#"><i class="icon-remove icon-white"></i>拒绝</a></td>
								</tr>
							</tbody>
						</table>
						<div class="pagination pagination-centered">
							<ul>
								<li><a href="#">Prev</a></li>
								<li class="active"><a href="#">1</a></li>
								<li><a href="#">2</a></li>
								<li><a href="#">3</a></li>
								<li><a href="#">4</a></li>
								<li><a href="#">Next</a></li>
							</ul>
						</div>
					</div>
				</div>



				<div class="tab-pane" id="messages">
					<div class="box-content">
						<table class="table table-striped table-bordered">
							<thead>
								<tr>
									<th>仪器名称</th>
									<th>申请数量</th>
									<th>申请日期</th>
									<th>申请人</th>
									<th>状态</th>
									<th>操作</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td>David R</td>
									<td>2</td>
									<td class="center">2012/01/01</td>
									<td class="center">Member</td>
									<td class="center"><span class="label label-success">已审核</span>
									</td>
									<td class="center"><a class="btn btn-success" href="#"><i
											class="icon-ok icon-white"></i>同意</a> <a class="btn btn-danger"
										href="#"><i class="icon-remove icon-white"></i>拒绝</a></td>
								</tr>
								<tr>
									<td>Brown Blue</td>
									<td>Worth Name</td>
									<td class="center">2012/03/01</td>
									<td class="center">Member</td>
									<td class="center"><span class="label label-warning">申请中</span>
									</td>
									<td class="center"><a class="btn btn-success" href="#"><i
											class="icon-ok icon-white"></i>同意</a> <a class="btn btn-danger"
										href="#"><i class="icon-remove icon-white"></i>拒绝</a></td>
								</tr>
								<tr>
									<td>Worth Name</td>
									<td>Worth Name</td>
									<td class="center">2012/03/01</td>
									<td class="center">Member</td>
									<td class="center"><span class="label label-important">已拒绝</span>
									</td>
									<td class="center"><a class="btn btn-success" href="#"><i
											class="icon-ok icon-white"></i>同意</a> <a class="btn btn-danger"
										href="#"><i class="icon-remove icon-white"></i>拒绝</a></td>
								</tr>
							</tbody>
						</table>
						<div class="pagination pagination-centered">
							<ul>
								<li><a href="#">Prev</a></li>
								<li class="active"><a href="#">1</a></li>
								<li><a href="#">2</a></li>
								<li><a href="#">3</a></li>
								<li><a href="#">4</a></li>
								<li><a href="#">Next</a></li>
							</ul>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!--/span-->
</div>

<!-- loading弹出界面 -->
<div class="theme-loadpage-popover">
	<div class="cssload-thecube">
		<div class="cssload-cube cssload-c1"></div>
		<div class="cssload-cube cssload-c2"></div>
		<div class="cssload-cube cssload-c4"></div>
		<div class="cssload-cube cssload-c3"></div>
	</div>
	<br>
	<div class="center">
		<h3>请等待，正在处理请求...</h3>
	</div>
</div>
<div class="theme-loadbg-popover-mask"></div>


<!-- 这里编辑页面要展示的内容  ends -->


<%@include file="/WEB-INF/pages/common/foot.jsp"%>
<script type="text/javascript"
	src="/statics/localjs/instrumentApplyCheck.js"></script>



