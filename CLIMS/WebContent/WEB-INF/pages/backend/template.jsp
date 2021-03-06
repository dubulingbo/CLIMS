<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!-- The styles -->
<link href="/statics/css/bootstrap-cerulean.css" rel="stylesheet">
<style type="text/css">
body {
	padding-bottom: 40px;
}
.sidebar-nav {
	padding: 9px 0;
}
.navbar .nav li a {
	border: 0px;
}
.custom-setting {
	
}
.clear {
	clear: both;
}
div .modal-body label {
	color: black;
}
</style>
<link href="/statics/css/bootstrap-responsive.css" rel="stylesheet">
<link href="/statics/css/charisma-app.css" rel="stylesheet">
<link href="/statics/css/jquery-ui-1.8.21.custom.css" rel="stylesheet">
<link href='/statics/css/fullcalendar.css' rel='stylesheet'>
<link href='/statics/css/fullcalendar.print.css' rel='stylesheet' media='print'>
<link href='/statics/css/chosen.css' rel='stylesheet'>
<link href='/statics/css/uniform.default.css' rel='stylesheet'>
<link href='/statics/css/colorbox.css' rel='stylesheet'>
<link href='/statics/css/jquery.cleditor.css' rel='stylesheet'>
<link href='/statics/css/jquery.noty.css' rel='stylesheet'>
<link href='/statics/css/noty_theme_default.css' rel='stylesheet'>
<link href='/statics/css/elfinder.min.css' rel='stylesheet'>
<link href='/statics/css/elfinder.theme.css' rel='stylesheet'>
<link href='/statics/css/jquery.iphone.toggle.css' rel='stylesheet'>
<link href='/statics/css/opa-icons.css' rel='stylesheet'>
<link href='/statics/css/uploadify.css' rel='stylesheet'>
<link href='/statics/localcss/datumDownload.css' rel='stylesheet'>
	

<!-- 院系管理员查看仪器维修详情  repairInfo  starts -->
<c:if test="${repairInfo !=  null}">
<div class="box-content" id="repairInfoTable">
	<table>
		<tr style="height:30px;">
			<td style="color:green;width:150px;">仪器库存编号</td>
			<td>${repairInfo.instrumentNo }</td>
		</tr>
		<tr style="height:30px;">
			<td style="color:green;width:150px;">仪器名称</td>
			<td>${repairInfo.instrumentName }</td>
		</tr>
		<tr style="height:30px;">
			<td style="color:green;width:150px;">报修地址</td>
			<td>${repairInfo.dept }&emsp;${repairInfo.address }</td>
		</tr>
		<tr style="height:30px;">
			<td style="color:green;width:150px;">报修详细</td>
			<td><textarea class="autogrow" readonly="readonly">${repairInfo.repairDetail }</textarea></td>
		</tr>
		<tr style="height:30px;">
			<td style="color:green;width:150px;">报修时间/预约时间</td>
			<td>
			<fmt:formatDate pattern="yyyy-MM-dd" value="${repairInfo.creationDate }"/>/
			<fmt:formatDate pattern="yyyy-MM-dd" value="${repairInfo.bookDate }"/>
			</td>
		</tr>
		<tr style="height:30px;">
			<td style="color:green;width:150px;">报修人</td>
			<td>${repairInfo.applyPerson }</td>
		</tr>
		<tr style="height:30px;">
			<td style="color:green;width:150px;">联系方式</td>
			<td>${repairInfo.applyPhone }</td>
		</tr>        
		<tr>
		<td style="color:green;width:150px;">报修图片</td>
		<c:if test="${repairInfo.rImgList != null}">
		<td>
			<ul class="thumbnails gallery">
			<c:forEach items="${repairInfo.rImgList}" var="rImg">
				<li class="thumbnail">
					<a style="background:url(${rImg })" href="${rImg }"><img class="grayscale" src="${rImg }"></a>
				</li>
			</c:forEach>
			</ul>
		</td>
		
		</c:if>
		</tr>
	</table>
</div>
</c:if>
<!-- 院系管理员查看仪器维修详情  repairInfo  ends -->

<div id="datumDownloadTable">
<c:if test="${page.items != null }">
<table class="table">
	<tbody>
	<c:forEach items="${page.items}" var="info">
		<c:if test="${info.filePath != null }">
		<tr class="ziliao_row_css">
			<td><a href="${info.filePath }" class="ziliao_a_css" target="_blank">${info.fileName }</a></td>
			<td class="center" class="ziliao_time_css">
				<fmt:formatDate value="${info.publishDate}" pattern="yyyy-MM-dd"/>
			</td>                               
		</tr>
		</c:if>
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
				<li><a href="javascript:load_infopage('1')" title="首页">首页</a></li>
			</c:otherwise>
		</c:choose>
		<c:if test="${page.prevPages != null}">
			<c:forEach items="${page.prevPages}" var="num">
				<li><a href="javascript:load_infopage('${num}')" title="${num}">${num}</a></li>
			</c:forEach>
		</c:if>

		<li class="active"><a href="javascript:void(0)" title="${page.currentPageNo}">${page.currentPageNo}</a>
		</li>

		<c:if test="${page.nextPages != null}">
			<c:forEach items="${page.nextPages}" var="num">
				<li><a href="javascript:load_infopage('${num}')" title="${num}">${num}</a></li>
			</c:forEach>
		</c:if>

		<c:if test="${page.totalPageCount != null}">
			<c:choose>
				<c:when test="${page.currentPageNo == page.totalPageCount}">
					<li class="active"><a href="javascript:void(0)" title="尾页">尾页</a></li>
				</c:when>
				<c:otherwise>
					<li><a href="javascript:load_infopage('${page.totalPageCount}')" title="尾页">尾页</a></li>
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
	<div class="center">暂时没有资料下载！</div>
</c:if>    
</div>


<script src="/statics/js/jquery-1.7.2.min.js"></script>
<script src="/statics/js/jquery-ui-1.8.21.custom.min.js"></script>
<script src="/statics/js/bootstrap-transition.js"></script>
<script src="/statics/js/bootstrap-alert.js"></script>
<script src="/statics/js/bootstrap-modal.js"></script>
<script src="/statics/js/bootstrap-dropdown.js"></script>
<script src="/statics/js/bootstrap-scrollspy.js"></script>
<script src="/statics/js/bootstrap-tab.js"></script>
<script src="/statics/js/bootstrap-tooltip.js"></script>
<script src="/statics/js/bootstrap-popover.js"></script>
<script src="/statics/js/bootstrap-button.js"></script>
<script src="/statics/js/bootstrap-collapse.js"></script>
<script src="/statics/js/bootstrap-carousel.js"></script>
<script src="/statics/js/bootstrap-typeahead.js"></script>
<script src="/statics/js/bootstrap-tour.js"></script>
<script src="/statics/js/jquery.cookie.js"></script>
<script src="/statics/js/fullcalendar.min.js"></script>
<script src='/statics/js/jquery.dataTables.min.js'></script>
<script src="/statics/js/excanvas.js"></script>
<script src="/statics/js/jquery.flot.min.js"></script>
<script src="/statics/js/jquery.flot.pie.min.js"></script>
<script src="/statics/js/jquery.flot.stack.js"></script>
<script src="/statics/js/jquery.flot.resize.min.js"></script>
<script src="/statics/js/jquery.chosen.min.js"></script>
<script src="/statics/js/jquery.uniform.min.js"></script>
<script src="/statics/js/jquery.colorbox.min.js"></script>
<script src="/statics/js/jquery.cleditor.min.js"></script>
<script src="/statics/js/jquery.noty.js"></script>
<script src="/statics/js/jquery.elfinder.min.js"></script>
<script src="/statics/js/jquery.raty.min.js"></script>
<script src="/statics/js/jquery.iphone.toggle.js"></script>
<script src="/statics/js/jquery.autogrow-textarea.js"></script>
<script src="/statics/js/jquery.uploadify-3.1.min.js"></script>
<script src="/statics/js/jquery.history.js"></script>
<script src="/statics/localjs/template.js"></script>



