<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@include file="/WEB-INF/pages/manager/common/instApplyCheck-head.jsp" %>
<!-- 这里编辑页面要展示的内容  starts -->
<c:if test="${page.items != null }">
<div style="margin:0 auto;width:80%;height:400px;">
<h2>仪器报废申请审核</h2>
<table class="table table-striped table-bordered">
	<thead>
		<tr>
			<th>仪器名称</th>
			<th>报废数量</th>
			<th>申请日期</th>
			<th>申请人</th>
			<th>状态</th>
			<th>操作</th>
		</tr>
	</thead>  
	<tbody>
		<c:forEach items="${page.items }" var="scrap">
			<tr>
				<td class="center">${scrap.instrumentName }</td>
				<td class="center">${scrap.scrapNumber }</td>
				<td class="center">
					<fmt:formatDate value="${scrap.creationDate }" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td class="center">
					${scrap.createdByName }
					<input type="hidden" id="instAssignStatus" value="${scrap.status }">
				</td>
				<td class="instAssignApplyStatus">
					<c:if test="${scrap.status == 4 }"><span class="label label-warning">申请中</span></c:if>
					<c:if test="${scrap.status == 5 }"><span class="label label-success">维修中</span></c:if>
				</td>
				<td class="center">
					<a class="btn btn-success instScrapApplyAgree" href="#" id="${scrap.id }"><i class="icon-ok icon-white"></i>同意</a>
					<a class="btn btn-danger instScrapApplyRefuse" href="#" id="${scrap.id }"><i class="icon-remove icon-white"></i>拒绝</a>
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
				<li><a href='instAssignApplyCheck.html?pageIndex=1' title="首页">首页</a></li>                                                                      
			</c:otherwise>
		</c:choose>
		
		<c:if test="${page.prevPages != null}">
			<c:forEach items="${page.prevPages}" var="num">
				<li><a href='instAssignApplyCheck.html?pageIndex=${num}' title="${num}">${num}</a></li>
			</c:forEach>
		</c:if>
				
		<li class="active"><a href="#" title="${page.currentPageNo}">${page.currentPageNo}</a></li>
		
		<c:if test="${page.nextPages != null}">
			<c:forEach items="${page.nextPages}" var="num">
				<li><a href='instAssignApplyCheck.html?pageIndex=${num}' title="${num}">${num}</a></li>
			</c:forEach>
		</c:if>
		<c:if test="${page.totalPageCount != null}">
			<c:choose>
				<c:when test="${page.currentPageNo == page.totalPageCount}">
					<li class="active"><a href="javascript:void();" title="尾页">尾页</a></li>              
				</c:when>
				<c:otherwise>
					<li><a href='instAssignApplyCheck.html?pageIndex=${page.totalPageCount}' title="尾页">尾页</a></li>                                                                      
				</c:otherwise>
			</c:choose>
		</c:if>
				
		<c:if test="${page.totalPageCount == null}">
			<li class="active"><a href="javascript:void();" title="尾页">尾页</a></li>
		</c:if>
	</ul>
</div>
</div>
</c:if>
<c:if test="${page.items == null }">
	<div class="center">暂时还没有申请报废记录...</div>
</c:if>
<!-- 这里编辑页面要展示的内容  ends -->
<%@include file="/WEB-INF/pages/manager/common/instApplyCheck-foot.jsp" %>




