<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/pages/common/head.jsp"%>
<!-- <link href="/statics/localcss/instrumentApply.css" rel="stylesheet"> -->
<!-- 这里编辑每个页面的导航条  starts -->
<div>
	<ul class="breadcrumb">
		<li><a href="/main.html">主页</a> <span class="divider">/</span></li>
		<li>仪器维修申请</li>
	</ul>
</div>
<!-- 这里编辑每个页面的导航条  ends -->


<!-- 这里编辑页面要展示的内容  starts -->
<div class="row-fluid sortable">
	<div class="box span12">
		<div class="box-header well" data-original-title>
			<h2><i class="icon-list-alt"></i> 可申请维修的仪器信息</h2>
			<div class="box-icon">
				<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>			
			</div>
		</div>

		<div class="box-content">
			<form action="instrumentRepairApply.html" method="post">
				仪器编号:&emsp;<input class="input-xlarge focused" type="text" name="s_instrumentNo" required="required"> 
				&emsp;
				<button type="submit" class="btn btn-small btn-primary">
					查询 <i class="icon-chevron-right icon-white"></i>
				</button>
			</form>
			<hr>
			<c:if test="${page.items != null }">
				<table class="table table-bordered">
					<thead>
						<tr>
							<th>仪器编号</th>
							<th>库存编号</th>
							<th>仪器名称</th>
							<th>申请调拨时间</th>
							<th>保修截至日期</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${page.items }" var="inst">
							<tr>
								<td class="center">${inst.assignNo }</td>
								<td class="center">${inst.instrumentNo }</td>
								<td class="center">${inst.instrumentName }</td>
								<td class="center">
									<fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${inst.creationDate }" />
								</td>
								<td class="center">
									<fmt:formatDate pattern="yyyy-MM-dd" value="${inst.expireDate }" />
								</td>
								<td class="center" id="repairApplyAction${inst.id }">
									<c:if test="${inst.status == 7 }">
										<a class="btn btn-success repairApply" href="javascript:void(0)" id="${inst.id }" instId="${inst.instrumentId }">
											<i class="icon-share icon-white"></i> 申请维修
										</a>
									</c:if>
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
								<li><a href='instrumentRepairApply.html?pageIndex=1&s_instrumentName=${s_instrumentName}&s_classNo=${s_classNo}'
									title="首页">首页</a></li>
							</c:otherwise>
						</c:choose>
						<c:if test="${page.prevPages != null}">
							<c:forEach items="${page.prevPages}" var="num">
								<li><a
									href='instrumentRepairApply.html?pageIndex=${num }&s_instrumentName=${s_instrumentName}&s_classNo=${s_classNo}'
									class="number" title="${num}">${num}</a></li>
							</c:forEach>
						</c:if>

						<li class="active"><a href="#" title="${page.currentPageNo}">${page.currentPageNo}</a></li>

						<c:if test="${page.nextPages != null}">
							<c:forEach items="${page.nextPages}" var="num">
								<li><a
									href='instrumentRepairApply.html?pageIndex=${num }&s_instrumentName=${s_instrumentName}&s_classNo=${s_classNo}'
									class="number" title="${num}">${num}</a></li>
							</c:forEach>
						</c:if>

						<c:if test="${page.totalPageCount != null}">
							<c:choose>
								<c:when test="${page.currentPageNo == page.totalPageCount}">
									<li class="active"><a href="javascript:void();" title="尾页">尾页</a></li>
								</c:when>
								<c:otherwise>
									<li><a
										href='instrumentRepairApply.html?pageIndex=${page.totalPageCount}&s_instrumentName=${s_instrumentName}&s_classNo=${s_classNo}'
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
		
		
		<div class="box-header well" data-original-title>
			<h2><i class="icon-list-alt"></i> 我的申请</h2>
			<div class="box-icon">
				<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>			
			</div>
		</div>
		<div class="box-content my_repairApplyInfo_div">
			<c:if test="${my_repairApplyList != null}">
				<table class="table table-bordered">
					<thead>
						<tr>
							<th>仪器编号</th>
							<th>仪器名称</th>
							<th>报修详情</th>
							<th>申请时间</th>
							<th>预约时间</th>
							<th>处理进度</th>
						</tr>
					</thead>
					<tbody>
					<c:forEach items="${my_repairApplyList }" var="myInst">
					<tr>
						<td class="center">${myInst.instrumentNo }</td>
						<td class="center">${myInst.instrumentName }</td>
						<td class="center">${myInst.repairDetail }</td>
						<td class="center">
							<fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${myInst.creationDate }" />
						</td>
						<td class="center">
							<fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${myInst.bookDate }" />
						</td>
						<td class="center">
							<c:if test="${myInst.status == 8}"><span class="label label-info">${myInst.statusName }</span></c:if>
							<c:if test="${myInst.status == 9}"><span class="label label-warning">${myInst.statusName }</span></c:if>
							<c:if test="${myInst.status == 10}"><span class="label label-important">${myInst.statusName }</span></c:if>
							<c:if test="${myInst.status == 11}"><span class="label label-success">${myInst.statusName }</span></c:if>
						</td>
					</tr>
					</c:forEach>
					</tbody>
				</table>
			</c:if>
			<c:if test="${my_repairApplyList == null }">
				<div class="center">暂时没有维修记录！</div>
			</c:if>
		</div>
		
		
		
		
	</div><!--/span-->
</div><!--/row-->
<!-- 这里编辑页面要展示的内容  ends -->



<div class="modal hide fade" id="instrumentRepairApplyDiv">
	<div class="modal-header">
		<h3 style="text-align: center;">报修申请</h3>
	</div>
	<div class="modal-body">
		<button class="btn btn-small btn-success rep_quickWriteInfo"
			id="${currentUser.id }">用自己的信息填写</button>
		<hr style="height: 0px; border: none; border-top: 1px solid #555555;">
		<ul id="instrumentRepairApply_tip"></ul>
		<div class="box-content">
			<div class="form-horizontal">

				<div class="control-group">
					<label class="control-label">报修项目</label>
					<div class="controls">
						<input type="hidden" id="rep_instrumentId" value=""> <input
							type="hidden" id="rep_assignId" value=""> <input
							id="rep_instrumentName" type="text" readonly="readonly">
					</div>
				</div>

				<div class="control-group">
					<label class="control-label">报修单位</label>
					<div class="controls">
						<select id="rep_dept">
							<option value="">--请选择--</option>
							<c:if test="${deptList != null }">
								<c:forEach items="${deptList }" var="dept">
									<option value="${dept.deptName }">${dept.deptName }</option>
								</c:forEach>
							</c:if>
						</select> <span style="color: red; font-weight: bold;"> *</span>
					</div>
				</div>

				<div class="control-group">
					<label class="control-label">详细地址</label>
					<div class="controls">
						<input id="rep_address" type="text" required="required"> <span
							style="color: red; font-weight: bold;"> *</span>
					</div>
				</div>

				<div class="control-group">
					<label class="control-label">报修详细</label>
					<div class="controls">
						<textarea class="autogrow" id="rep_detail" required="required"></textarea>
						<span style="color: red; font-weight: bold;"> *</span>
					</div>
				</div>


				<div class="control-group">
					<label class="control-label">预约时间</label>
					<div class="controls">
						<!-- <input type="text" id="rep_bookDate" class="Wdate"
							   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',minDate:'%y-%M-{%d+1}',minTime:'09:00:00',maxTime:'21:10:00'})" 
							   readonly="readonly" style="background-color:white;cursor: pointer;"> -->
						<input type="text" id="rep_bookDate" class="rep_bookDate" readonly="readonly" style="background-color:white;cursor:pointer;">
						<span style="color: red; font-weight: bold;"> *</span>
					</div>
				</div>

				<div class="control-group">
					<label class="control-label">报修人</label>
					<div class="controls">
						<input id="rep_applyPerson" type="text" required="required">
						<span style="color: red; font-weight: bold;"> *</span>
					</div>
				</div>

				<div class="control-group">
					<label class="control-label">联系方式</label>
					<div class="controls">
						<input id="rep_applyPhone" type="text">
						<span style="color: red; font-weight: bold;"> *</span>
					</div>
				</div>



				<!-- <div class="control-group">
					<label class="control-label">上传图片</label> &emsp;
					<span style="color: red; font-weight: bold; font-size: 8px;">
						*最多上传2张图片，每张图片大小不超过5M
					</span>
					<div class="controls">
						<input id="rep_repairImgPath_file1" name="rep_repairImgPath_file" type="file">
						<input id="rep_repairImgPath_file2" name="rep_repairImgPath_file" type="file">
					</div>
				</div> -->
			</div>
		</div>
	</div>
	<div class="modal-footer">
		<a href="#" class="btn instrumentRepairApplyCancel"
			data-dismiss="modal"> 关闭</a> <a href="#"
			class="btn btn-primary instrumentRepairApplySave"> 提交</a>
	</div>
</div>

<%@include file="/WEB-INF/pages/common/foot.jsp"%>
<script type="text/javascript" src="/statics/localjs/instrumentApply.js"></script>



