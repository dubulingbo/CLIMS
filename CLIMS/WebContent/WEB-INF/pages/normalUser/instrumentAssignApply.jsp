<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/pages/common/head.jsp" %>

<!-- 这里编辑每个页面的导航条  starts -->
<div>
	<ul class="breadcrumb">
		<li><a href="main.html">主页</a> <span class="divider">/</span></li>
		<li>仪器调拨申请</li>
	</ul>
</div>
<!-- 这里编辑每个页面的导航条  ends -->


<!-- 这里编辑页面要展示的内容  starts -->
<div class="row-fluid sortable">
	<div class="box span12">
		<div class="box-header well" data-original-title>
			<h2><i class="icon-list-alt"></i> 可申请调拨的仪器信息</h2>
			<div class="box-icon">
				<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
			</div>
		</div>
		<div class="box-content">
		<c:if test="${page.items != null }">
			<table class="table table-bordered table-striped table-condensed">
				<thead>
					<tr>
						<th>仪器名称(Name)</th>
						<th>仪器型号(Type)</th>
						<th>所属类别(Species)</th>
						<th>单价(Price)</th>
						<th>选择数量(Number)</th>
						<th>操作(Action)</th>
					</tr>
				</thead>
				<tbody>
				<c:forEach items="${page.items }" var="inst">
					<tr>
						<td class="center">${inst.instrumentName }</td>
						<td class="center">${inst.instrumentNo }</td>
						<td class="center">${inst.className }</td>
						<td class="center">${inst.price }</td>
						<td class="center">
							<select style="width:100px;" class="selectNumber">
								<c:if test="${inst.stockNumber > 0 }">
								<c:forEach begin="1" end="${inst.stockNumber }" var="i">
									<option value="${i }">--- ${i } ---</option>
								</c:forEach>
								</c:if>
							</select>
						</td>
						<td class="center">
							<a class="btn btn-success assignApply" href="#" id="${inst.id }" instName="${inst.instrumentName }" instNo="${inst.instrumentNo }">
								<i class="icon-share icon-white"></i>  
								申请调拨                                            
							</a>
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
							<li>
								<a href='instrumentAssignApply.html?pageIndex=1&s_instrumentName=${s_instrumentName}&s_classNo=${s_classNo}' title="首页">首页</a>
							</li>                                                                  
						</c:otherwise>
					</c:choose>
					<c:if test="${page.prevPages != null}">
						<c:forEach items="${page.prevPages}" var="num">
							<li>
								<a href='instrumentAssignApply.html?pageIndex=${num }&s_instrumentName=${s_instrumentName}&s_classNo=${s_classNo}' class="number" title="${num}">${num}</a>
							</li>
						</c:forEach>
					</c:if>
				
					<li class="active">
						<a href="#" title="${page.currentPageNo}">${page.currentPageNo}</a>
					</li>
				
					<c:if test="${page.nextPages != null}">
						<c:forEach items="${page.nextPages}" var="num">
							<li>
								<a href='instrumentAssignApply.html?pageIndex=${num }&s_instrumentName=${s_instrumentName}&s_classNo=${s_classNo}' class="number" title="${num}">${num}</a>
							</li>
						</c:forEach>
					</c:if>
				
					<c:if test="${page.totalPageCount != null}">
						<c:choose>
							<c:when test="${page.currentPageNo == page.totalPageCount}">
								<li class="active"><a href="javascript:void();" title="尾页">尾页</a></li>              
							</c:when>
							<c:otherwise>
								<li>
									<a href='instrumentAssignApply.html?pageIndex=${page.totalPageCount}&s_instrumentName=${s_instrumentName}&s_classNo=${s_classNo}' title="尾页">尾页</a>
	   							</li>                                                                      
							</c:otherwise>
						</c:choose>
					</c:if>
				
					<c:if test="${page.totalPageCount == null}">
						<li class="active"><a href="javascript:void();" title="尾页">尾页</a></li>
					</c:if>
				</ul>
			</div>
		</c:if>
		<c:if test="${page.items == null }"><div class="center">未查找到数据！</div></c:if>
		</div>
		
		
		
		<div class="box-header well" data-original-title>
			<h2><i class="icon-search"></i> 按条件搜索</h2>
			<div class="box-icon">
				<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
			</div>
		</div>
		<div class="box-content">
		<form action="instrumentAssignApply.html" method="post" onsubmit="return instrumentAssignApplyFunction();">
			<div class="form-horizontal">
			
				<div class="control-group">
					<label class="control-label">仪器名称</label>
					<div class="controls">
						<input class="input-xlarge focused" type="text" id="s_instrumentName" value="${s_instrumentName}" name="s_instrumentName">
					</div>
				</div>
			
				<div class="control-group">
					<label class="control-label" >仪器编号</label>
					<div class="controls">
					  <input class="input-xlarge focused" type="text" id="s_instrumentNo" value="${s_instrumentNo}" name="s_instrumentNo">
					</div>
				</div>
			
				<div class="control-group">
					<label class="control-label" for="selectError">类别</label>
					<div class="controls">
						<select id="selectError" data-rel="chosen" class="s_classNo" style="width:150px;" name="s_classNo">
							<option value="">--请选择--</option>
							<c:if test="${speciesList != null }">
								<c:forEach items="${speciesList }" var="spec">
									<option <c:if test="${spec.id == s_classNo }">selected="selected"</c:if> value="${spec.id }">${spec.speciesName }</option>
								</c:forEach>
							</c:if>
						</select>
					</div>
				</div>
			
			</div>
		
			<hr>
			<div class="center">
				<button type="submit" class="btn btn-large btn-primary">查询  <i class="icon-chevron-right icon-white"></i></button>
			</div>
		</form>
		</div>
		
		
	</div><!--/span-->
</div><!--/row-->
<!-- 这里编辑页面要展示的内容  ends -->

<%@include file="/WEB-INF/pages/common/foot.jsp" %>
<script type="text/javascript" src="${pageContext.request.contextPath }/statics/localjs/instrumentAssignApply.js"></script>


