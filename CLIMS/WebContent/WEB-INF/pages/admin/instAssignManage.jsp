<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/pages/common/head.jsp"%>
<!-- 这里编辑每个页面的导航条  starts -->
<div>
	<ul class="breadcrumb">
		<li>导航：<a href="/main.html">主页</a> <span class="divider">/</span></li>
		<li>仪器调拨管理</li>
	</ul>
</div>
<!-- 这里编辑每个页面的导航条  ends -->



<!-- 这里编辑页面要展示的内容  starts -->
<div class="row-fluid sortable">
	<div class="box span12">

		<div class="box-header well" data-original-title>
			<h2>
				<i class="icon-list-alt"></i> 仪器调拨信息列表
			</h2>
		</div>


		<div class="box-content">
			<c:if test="${page.items != null }">
				<table class="table">
					<thead>
						<tr>
							<th>仪器名称</th>
							<th>申请单位</th>
							<th>申请数量</th>
							<th>申请时间</th>
							<th>审核员</th>
							<th>当前状态</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${page.items }" var="inst">
							<tr>
								<td>${inst.instrumentName }</td>
								<td>${inst.dept }</td>
								<td style="padding-left: 30px;">${inst.assignNumber }</td>
								<td><fmt:formatDate value="${inst.creationDate }"
										pattern="yyyy-MM-dd HH:mm:ss" /></td>
								<td><a href="#" class="viewUser"
									id="${inst.assignManagerId }">${inst.assignManager }</a></td>
								<td id="instAssignApplyStatus${inst.id }"><c:if
										test="${inst.status == 2 }">
										<span class="label label-warning">${inst.statusName }</span>
									</c:if> <c:if test="${inst.status == 3 }">
										<span class="label label-success">${inst.statusName }</span>
									</c:if> <c:if test="${inst.status == 9 }">
										<span class="label">${inst.statusName }</span>
									</c:if> <c:if test="${inst.status == 11 }">
										<span class="label label-important">${inst.statusName }</span>
									</c:if></td>
								<td style="width: 250px;"><input type="hidden"
									value="${inst.status }" id="changeStatus${inst.id }">
									<p class="btn-group" style="width: 240px; height: 10px;">
										<button class="btn agreeAssignCheck" id="${inst.id }">允许调拨</button>
										<button class="btn refuseAssignCheck" id="${inst.id }">拒绝调拨</button>
									</p></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>

				<!-- 分页选项 -->
				<div class="pagination pagination-centered">
					<ul>
						<c:choose>
							<c:when test="${page.currentPageNo == 1}">
								<li class="active"><a href="javascript:void();" title="首页">首页</a></li>
							</c:when>
							<c:otherwise>
								<li><a href='instAssignManage.html?pageIndex=1' title="首页">首页</a></li>
							</c:otherwise>
						</c:choose>
						<c:if test="${page.prevPages != null}">
							<c:forEach items="${page.prevPages}" var="num">
								<li><a href='instAssignManage.html?pageIndex=${num }'
									title="${num}">${num}</a></li>
							</c:forEach>
						</c:if>

						<li class="active"><a href="#" title="${page.currentPageNo}">${page.currentPageNo}</a>
						</li>

						<c:if test="${page.nextPages != null}">
							<c:forEach items="${page.nextPages}" var="num">
								<li><a href='instAssignManage.html?pageIndex=${num }'
									title="${num}">${num}</a></li>
							</c:forEach>
						</c:if>

						<c:if test="${page.totalPageCount != null}">
							<c:choose>
								<c:when test="${page.currentPageNo == page.totalPageCount}">
									<li class="active"><a href="javascript:void();" title="尾页">尾页</a></li>
								</c:when>
								<c:otherwise>
									<li><a
										href='instAssignManage.html?pageIndex=${page.totalPageCount}'
										title="尾页">尾页</a></li>
								</c:otherwise>
							</c:choose>
						</c:if>

						<c:if test="${page.totalPageCount == null}">
							<li class="active"><a href="javascript:void();" title="尾页">尾页</a></li>
						</c:if>
					</ul>
				</div>
			</c:if>
			<c:if test="${page.items == null }">
				<div class="center">未查找到数据！</div>
			</c:if>
		</div>


	</div>
	<!--/span-->
</div>
<!--/row-->


<!-- 操作弹出的提示弹出界面 start -->
<div class="modal hide fade" id="actionDiv_admin">
	<div class="modal-header">
		<h3>Loading</h3>
	</div>
	<div class="modal-body tip_content"></div>
	<div class="modal-footer tip_action"></div>
</div>
<!-- 操作弹出的提示弹出界面 end -->





<!-- 这里编辑页面要展示的内容  ends -->

<%@include file="/WEB-INF/pages/common/foot.jsp"%>
<script type="text/javascript"
	src="/statics/localjs/instAssignManage.js"></script>

