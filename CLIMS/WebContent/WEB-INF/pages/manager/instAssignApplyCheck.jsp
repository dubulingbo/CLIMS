<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!-- 这里编辑页面要展示的内容  starts -->

<c:if test="${page.items != null }">
	<div class="center" style="font-size:20px;">仪器调拨申请列表</div>
	<table class="table table-striped table-bordered">
		<thead>
			<tr>
				<th>仪器名称</th>
				<th>申请数量</th>
				<th>申请日期</th>
				<th>申请人</th>
				<th>状态</th>
				<th>操作/进度</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.items }" var="assign">
				<tr>
					<td class="center">${assign.instrumentName }</td>
					<td class="center">${assign.assignNumber }</td>
					<td class="center"><fmt:formatDate
							value="${assign.creationDate }" pattern="yyyy-MM-dd HH:mm:ss" />
					</td>
					<td class="center">${assign.createdByName } <input
						type="hidden" id="instAssignStatus" value="${assign.status }">
					</td>
					<td class="instAssignApplyStatus"><c:if
							test="${assign.status == 1 }">
							<span class="label label-warning">${assign.statusName }</span>
						</c:if> <c:if test="${assign.status == 2 }">
							<span class="label label-info">${assign.statusName }</span>
						</c:if> <c:if test="${assign.status == 3 }">
							<span class="label label-important">${assign.statusName }</span>
						</c:if> <c:if test="${assign.status == 4 }">
							<span class="label">${assign.statusName }</span>
						</c:if> <c:if test="${assign.status == 5 }">
							<span class="label">${assign.statusName }</span>
						</c:if> <c:if test="${assign.status >= 6 }">
							<span class="label label-success">已调拨</span>
						</c:if></td>
					<td class="instAssignApplyCheck_action"><c:if
							test="${assign.status == 1 }">
							<a class="btn btn-success instAssignApplyAgree"
								href="javascript:void(0);" id="${assign.id }"><i
								class="icon-ok icon-white"></i> 同意</a>
							<a class="btn btn-danger instAssignApplyRefuse" href="#"
								id="${assign.id }"><i class="icon-remove icon-white"></i> 拒绝</a>
						</c:if> <c:if test="${assign.status == 2 }">
							<b>请等待系统管理员处理..</b>
						</c:if> <c:if test="${assign.status == 3 }">
							<b>您已拒绝调拨申请！</b>
						</c:if> <c:if test="${assign.status == 4 }">
							<a class="btn btn-info instAssignApplyReceive" href="#"
								id="${assign.id }"><i class="icon-edit icon-white"></i> 签收</a>
						</c:if> <c:if test="${assign.status == 5 }">
							<b>系统管理员已拒绝！</b>
						</c:if> <c:if test="${assign.status >= 6 }">
							<b>已签收，无需操作！</b>
						</c:if></td>
				</tr>
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
					<li><a href="javascript:instApplyCheck_pageJump('instAssignApplyCheck.html','1')" title="首页">首页</a></li>
				</c:otherwise>
			</c:choose>

			<c:if test="${page.prevPages != null}">
				<c:forEach items="${page.prevPages}" var="num">
					<li><a href="javascript:instApplyCheck_pageJump('instAssignApplyCheck.html','${num}')" title="${num}">${num}</a></li>
				</c:forEach>
			</c:if>

			<li class="active"><a href="javascript:void(0)" title="${page.currentPageNo}">${page.currentPageNo}</a></li>

			<c:if test="${page.nextPages != null}">
				<c:forEach items="${page.nextPages}" var="num">
					<li><a href="javascript:instApplyCheck_pageJump('instAssignApplyCheck.html','${num}')" title="${num}">${num}</a></li>
				</c:forEach>
			</c:if>
			<c:if test="${page.totalPageCount != null}">
				<c:choose>
					<c:when test="${page.currentPageNo == page.totalPageCount}">
						<li class="active"><a href="javascript:void(0)" title="尾页">尾页</a></li>
					</c:when>
					<c:otherwise>
						<li><a href="javascript:instApplyCheck_pageJump('instAssignApplyCheck.html','${page.totalPageCount}')" title="尾页">尾页</a></li>
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
	<div class="center">暂时还没有申请调拨记录...</div>
</c:if>
<!-- 这里编辑页面要展示的内容  ends -->
<script type="text/javascript" src="/statics/localjs/instrumentApplyCheck.js"></script>




