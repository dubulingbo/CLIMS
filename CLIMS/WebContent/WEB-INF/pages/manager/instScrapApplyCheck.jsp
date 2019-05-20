<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!-- 这里编辑页面要展示的内容  starts -->
<c:if test="${page.items != null }">
	<div class="center" style="font-size:20px;">仪器报废申请列表</div>
	<table class="table table-striped table-bordered">
		<thead>
			<tr>
				<th>仪器编号</th>
				<th>仪器名称</th>
				<th>报废数量</th>
				<th>报废详情</th>
				<th>申请日期</th>
				<th>申请人</th>
				<th>状态</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.items }" var="scrap">
				<tr>
					<td class="center">${scrap.assignId }</td>
					<td class="center">${scrap.instrumentName }</td>
					<td class="center">${scrap.scrapNumber }</td>
					<td><textarea class="autogrow" readonly="readonly">${scrap.scrapDetail }</textarea></td>
					<td class="center">
						<fmt:formatDate value="${scrap.creationDate }" pattern="yyyy-MM-dd HH:mm" />
					</td>
					<td class="center">${scrap.createdByName }
						<input type="hidden" id="instScrapStatus" value="${scrap.status }">
					</td>
					<td class="instScrapApplyStatus">
					<c:if test="${scrap.status == 12 }"><span class="label label-warning">${scrap.statusName }</span></c:if> 
					<c:if test="${scrap.status == 13 }"><span class="label label-success">${scrap.statusName }</span></c:if>
					<c:if test="${scrap.status == 14 }"><span class="label">${scrap.statusName }</span></c:if>
					</td>
					<td class="instScrapApplyCheck_action">
					<c:if test="${scrap.status == 12}">
						<a class="btn btn-success instScrapApplyAgree" href="javascript:void(0)" id="${scrap.id }">
							<i class="icon-ok icon-white"></i>同意
						</a>
						<a class="btn btn-danger instScrapApplyRefuse" href="javascript:void(0)" id="${scrap.id }">
							<i class="icon-remove icon-white"></i>拒绝
						</a>
					</c:if>
					<%-- <c:if test="${scrap.status == 13}"><b>你已同意报废申请.</b></c:if>
					<c:if test="${scrap.status == 14}"><b>经检查，未达报标准.</b></c:if> --%>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="pagination pagination-centered">
		<ul>
			<c:choose>
				<c:when test="${page.currentPageNo == 1}">
					<li class="active"><a href="javascript:void();" title="首页">首页</a></li>
				</c:when>
				<c:otherwise>
					<li><a href="javascript:instApplyCheck_pageJump('instScrapApplyCheck.html','1')" title="首页">首页</a></li>
				</c:otherwise>
			</c:choose>

			<c:if test="${page.prevPages != null}">
				<c:forEach items="${page.prevPages}" var="num">
					<li><a href="javascript:instApplyCheck_pageJump('instScrapApplyCheck.html','${num}')" title="${num}">${num}</a></li>
				</c:forEach>
			</c:if>

			<li class="active"><a href="javascript:void(0)" title="${page.currentPageNo}">${page.currentPageNo}</a></li>

			<c:if test="${page.nextPages != null}">
				<c:forEach items="${page.nextPages}" var="num">
					<li><a href="javascript:instApplyCheck_pageJump('instScrapApplyCheck.html','${num}')" title="${num}">${num}</a></li>
				</c:forEach>
			</c:if>
			<c:if test="${page.totalPageCount != null}">
				<c:choose>
					<c:when test="${page.currentPageNo == page.totalPageCount}">
						<li class="active"><a href="javascript:void();" title="尾页">尾页</a></li>
					</c:when>
					<c:otherwise>
						<li><a
							href="javascript:instApplyCheck_pageJump('instScrapApplyCheck.html','{page.totalPageCount}')" title="尾页">尾页</a></li>
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
	<div class="center">暂时还没有申请报废记录...</div>
</c:if>
<!-- 这里编辑页面要展示的内容  ends -->
<script type="text/javascript" src="/statics/localjs/instrumentApplyCheck.js"></script>