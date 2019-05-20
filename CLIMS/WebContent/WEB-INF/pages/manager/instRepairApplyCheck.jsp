<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!-- 这里编辑页面要展示的内容  starts -->
<c:if test="${page.items != null }">
	<div class="center" style="font-size:20px;">仪器维修申请列表</div>
	<table class="table table-striped table-bordered">
		<thead>
			<tr>
				<th>仪器编号</th>
				<th>仪器名称</th>
				<th>申请人</th>
				<th>报修详情</th>
				<th>当前状态</th>
				<th>选择维修员</th>
				<th>操作/进度</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.items }" var="repair">
				<tr>
					<td class="center">${repair.assignId }</td>
					<td class="center">${repair.instrumentName }</td>
					<td class="center">
						${repair.createdByName } 
						<input type="hidden" id="instRepairStatus" value="${repair.status }">
					</td>
					<td class="center"><a href="javascript:showRepairDetail('${repair.id }')" title="点击查看报修详情" style="font-size:14px;">......</a></td>
					<td class="instRepairApplyStatus">
						<c:if test="${repair.status == 8}"><span class="label label-warning">${repair.statusName }</span></c:if>
						<c:if test="${repair.status == 9}"><span class="label label-important">${repair.statusName }</span></c:if>
						<c:if test="${repair.status == 10}"><span class="label">${repair.statusName }</span></c:if>
						<c:if test="${repair.status == 11}"><span class="label label-success">${repair.statusName }</span></c:if>
					</td>
					<td class="instRepairmanId">
						<c:if test="${repair.status == 8}">
						<select style="width:108px;">
							<option value="">-- 请选择 --</option>
							<c:if test="${repair.repairmanList != null}">
								<c:forEach items="${repair.repairmanList }" var="repairman">
									<option value="${repairman.id}">${repairman.repairmanName}</option>
								</c:forEach>
							</c:if>
						</select>
						</c:if>
					</td>
					<td class="instRepairApplyCheck_action">
						<c:if test="${repair.status == 8}">
						<a class="btn btn-success instRepairApplyAgree" href="javascript:void(0)" id="${repair.id }"><i class="icon-ok icon-white"></i>同意</a>
						<a class="btn btn-danger instRepairApplyRefuse" href="javascript:void(0)" id="${repair.id }"><i class="icon-remove icon-white"></i>拒绝</a>
						</c:if>
						<c:if test="${repair.status == 9}"><b>请尽快安排维修人员上门维修.</b></c:if>
						<c:if test="${repair.status == 10}"><b>您已拒绝本次维修申请.</b></c:if>
						<c:if test="${repair.status == 11}"><b>已维修，无需操作.</b></c:if>
						<c:if test="${repair.isConfirm == 1 && repair.status == 9}">
							<button class="btn btn-small manager_confirmRepair" id="${repair.id}">确认已维修</button>
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
					<li class="active"><a href="javascript:void(0)" title="首页">首页</a></li>
				</c:when>
				<c:otherwise>
					<li><a href="javascript:instApplyCheck_pageJump('instRepairApplyCheck.html','1')" title="首页">首页</a></li>
				</c:otherwise>
			</c:choose>

			<c:if test="${page.prevPages != null}">
				<c:forEach items="${page.prevPages}" var="num">
					<li><a href="javascript:instApplyCheck_pageJump('instRepairApplyCheck.html','${num}')" title="${num}">${num}</a></li>
				</c:forEach>
			</c:if>

			<li class="active"><a href="javascript:void(0)" title="${page.currentPageNo}">${page.currentPageNo}</a></li>

			<c:if test="${page.nextPages != null}">
				<c:forEach items="${page.nextPages}" var="num">
					<li><a href="javascript:instApplyCheck_pageJump('instRepairApplyCheck.html','${num}')" title="${num}">${num}</a></li>
				</c:forEach>
			</c:if>
			<c:if test="${page.totalPageCount != null}">
				<c:choose>
					<c:when test="${page.currentPageNo == page.totalPageCount}">
						<li class="active"><a href="javascript:void(0)" title="尾页">尾页</a></li>
					</c:when>
					<c:otherwise>
						<li><a href="javascript:instApplyCheck_pageJump('instRepairApplyCheck.html','${page.totalPageCount}')" title="尾页">尾页</a></li>
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
	<div class="center">暂时还没有申请维修记录...</div>
</c:if>


<!-- 查看申请维修的仪器的详情弹出界面 -->

	<div class="modal hide fade" id="showRepairDetail_div">
		<div class="modal-header">
			<h3>报修详情</h3>
		</div>
		<div class="modal-body" id="showRepairDetail_body">
			
		</div>
		<div class="modal-footer">
			<a href="#" class="btn" data-dismiss="modal">Close</a>
		</div>
	</div>
<!-- 这里编辑页面要展示的内容  ends -->
<script type="text/javascript" src="/statics/localjs/instrumentApplyCheck.js"></script>





