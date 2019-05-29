<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/pages/common/head.jsp"%>
<!-- 这里编辑每个页面的导航条  starts -->
<div>
	<ul class="breadcrumb">
		<li>导航：<a href="/main.html">主页</a> <span class="divider">/</span></li>
		<li>仪器库存管理</li>
	</ul>
</div>
<!-- 这里编辑每个页面的导航条  ends -->



<!-- 这里编辑页面要展示的内容  starts -->
<div class="row-fluid sortable">
	<div class="box span12">

		<div class="box-header well" data-original-title>
			<h2>
				<i class="icon-list-alt"></i> 仪器信息列表
			</h2>
			<div class="box-icon">
				<a href="#" class="btn btn-minimize btn-round"><i
					class="icon-chevron-up"></i></a>
			</div>
		</div>


		<div class="box-content">
			<a href="javascript:void();" class="btn btn-large addInstrument"><i
				class="icon-plus"></i> 添加仪器</a>
			<hr>
			<c:if test="${page.items != null }">
				<table class="table">
					<thead>
						<tr>
							<th>仪器名称(Name)</th>
							<th>型号(Type)</th>
							<th>单价(Unit Price)</th>
							<th>所在单位(School)</th>
							<th>所属类别(Class)</th>
							<th>操作(Action)</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${page.items }" var="inst">
							<tr>
								<td style="padding-top: 12px;">${inst.instrumentName }</td>
								<td class="center" style="padding-top: 12px;">${inst.instrumentType }</td>
								<td class="center" style="padding-top: 12px;">
									<fmt:formatNumber value="${inst.price }" maxFractionDigits="2"/>
								</td>
								<td class="center" style="padding-top: 12px;">${inst.dept }</td>
								<td class="center" style="padding-top: 12px;">${inst.className }</td>
								<td class="center" style="width: 250px;">
									<p class="btn-group" style="width: 240px; height: 10px;">
										<button class="btn viewInstrument" id="${inst.id }">查看</button>
										<button class="btn">调拨</button>
										<button class="btn">维修</button>
										<button class="btn">报废</button>
									</p>
								</td>
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
								<li><a
									href='instrumentManage.html?pageIndex=1&s_instrumentName=${s_instrumentName}&s_classNo=${s_classNo}&s_dept=${s_dept}'
									title="首页">首页</a></li>
							</c:otherwise>
						</c:choose>
						<c:if test="${page.prevPages != null}">
							<c:forEach items="${page.prevPages}" var="num">
								<li><a
									href='instrumentManage.html?pageIndex=${num }&s_instrumentName=${s_instrumentName}&s_classNo=${s_classNo}&s_dept=${s_dept}'
									class="number" title="${num}">${num}</a></li>
							</c:forEach>
						</c:if>

						<li class="active"><a href="#" title="${page.currentPageNo}">${page.currentPageNo}</a>
						</li>

						<c:if test="${page.nextPages != null}">
							<c:forEach items="${page.nextPages}" var="num">
								<li><a
									href='instrumentManage.html?pageIndex=${num }&s_instrumentName=${s_instrumentName}&s_classNo=${s_classNo}&s_dept=${s_dept}'
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
										href='instrumentManage.html?pageIndex=${page.totalPageCount}&s_instrumentName=${s_instrumentName}&s_classNo=${s_classNo}&s_dept=${s_dept}'
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
			<h2>
				<i class="icon-search"></i> 按条件搜索
			</h2>
			<div class="box-icon">
				<a href="#" class="btn btn-minimize btn-round"><i
					class="icon-chevron-up"></i></a>
			</div>
		</div>

		<div class="box-content">
			<form action="instrumentManage.html" method="post"
				onsubmit="return _queryInstrumentFunction();">
				<div class="form-horizontal">

					<div class="control-group">
						<label class="control-label" for="focusedInput">仪器名称</label>
						<div class="controls">
							<input class="input-xlarge focused" type="text"
								id="s_instrumentName" value="${s_instrumentName}"
								name="s_instrumentName">
						</div>
					</div>

					<div class="control-group">
						<label class="control-label" for="focusedInput">仪器编号</label>
						<div class="controls">
							<input class="input-xlarge focused" type="text"
								id="s_instrumentNo" value="${s_instrumentNo}"
								name="s_instrumentNo">
						</div>
					</div>

					<div class="control-group">
						<label class="control-label" for="selectError">类别</label>
						<div class="controls">
							<select id="selectError s_classNo" data-rel="chosen"
								style="width: 150px;" class="s_classNo" name="s_classNo">
								<option value="">--请选择--</option>
								<c:if test="${speciesList != null }">
									<c:forEach items="${speciesList }" var="spec">
										<option
											<c:if test="${spec.id == s_classNo }">selected="selected"</c:if>
											value="${spec.id }">${spec.speciesName }</option>
									</c:forEach>
								</c:if>
							</select>
						</div>
					</div>

					<div class="control-group">
						<label class="control-label" for="selectError">所在单位</label>
						<div class="controls">
							<select id="selectError s_dept" data-rel="chosen"
								style="width: 150px;" class="s_dept" name="s_dept">
								<option value="">--请选择--</option>
								<c:if test="${deptList != null }">
									<c:forEach items="${deptList }" var="dept">
										<option
											<c:if test="${dept.deptName == s_dept }">selected="selected"</c:if>
											value="${dept.deptName }">${dept.deptName }</option>
									</c:forEach>
								</c:if>
							</select>
						</div>
					</div>

				</div>

				<hr>

				<div class="center">
					<a href="#" class="btn btn-large btn-primary"
						id="resetQueryInstrument"><i
						class="icon-chevron-left icon-white"></i> 重置</a>&emsp;&emsp;
					<button type="submit" class="btn btn-large btn-primary">
						查询 <i class="icon-chevron-right icon-white"></i>
					</button>
				</div>
				<div class="clearfix"></div>
			</form>
		</div>

	</div>
	<!--/span-->
</div>
<!--/row-->











<!-- 添加仪器设备信息弹出界面 start -->
<div class="modal hide fade" id="addInstrumentDiv">

	<div class="modal-header">
		<button type="button" class="close addInstrumentCancel"
			data-dismiss="modal">×</button>
		<h3>添加仪器信息</h3>
	</div>

	<div class="modal-body">
		<form action="addInstrument.html" method="post"
			enctype="multipart/form-data"
			onsubmit="return addInstrumentFunction();">
			<ul id="addInstrumentTip"></ul>
			<div class="box-content">
				<div class="form-horizontal">

					<div class="control-group">
						<label class="control-label">仪器名称</label>
						<div class="controls">
							<input class="input-xlarge focused" type="text"
								id="a_instrumentName" name="instrumentName" required="required">
							<span style="color: red; font-weight: bold;">*</span>
						</div>
					</div>

					<div class="control-group">
						<label class="control-label">仪器型号</label>
						<div class="controls">
							<input class="input-xlarge focused" type="text"
								id="a_instrumentType" name="instrumentType">
						</div>
					</div>

					<div class="control-group">
						<label class="control-label" for="selectError3">所属类别</label>
						<div class="controls">
							<select id="selectError3 a_classNo" class="a_classNo"
								name="classNo" style="width: 140px;">
								<option value="" selected="selected">--请选择--</option>
								<c:if test="${speciesList != null }">
									<c:forEach items="${speciesList}" var="spec">
										<option value="${spec.id}">${spec.speciesName}</option>
									</c:forEach>
								</c:if>
							</select>
						</div>
					</div>

					<div class="control-group">
						<label class="control-label" for="selectError2">国别</label>
						<div class="controls">
							<select id="selectError2 a_country" class="a_country"
								data-rel="chosen" name="country" style="width: 140px;">
								<option value="" selected="selected">--请选择--</option>
								<c:if test="${countryList != null }">
									<c:forEach items="${countryList}" var="cty">
										<option value="${cty.countryName}">${cty.countryName}</option>
									</c:forEach>
								</c:if>
							</select>
						</div>
					</div>

					<input type="hidden" id="a_h_price" value="0.00" name="price">
					<div class="control-group">
						<label class="control-label">单价</label>
						<div class="controls">
							<input class="input-xlarge focused" type="text" id="a_int_price"
								required="required" style="width: 100px;"
								onkeyup="value=value.replace(/[^\w\.\/]/ig,'')"> . <input
								class="input-xlarge focused" type="text" id="a_float_price"
								maxlength="2" required="required" style="width: 60px;"
								value="00" onkeyup="value=value.replace(/[^\w\.\/]/ig,'')">
							<span style="color: red; font-weight: bold;">*</span>
						</div>
					</div>

					<div class="control-group">
						<label class="control-label">生产商</label>
						<div class="controls">
							<input class="input-xlarge focused" type="text"
								id="a_manufacturer" name="manufacturer" required="required">
							<span style="color: red; font-weight: bold;">*</span>
						</div>
					</div>

					<div class="control-group">
						<label class="control-label">生产日期</label>
						<div class="controls">
							<input type="text" class="Wdate" size="15" readonly="readonly"
								onClick="WdatePicker();" id="a_productionDate"
								name="productionDate" required="required"
								style="cursor: pointer;"> <span
								style="color: red; font-weight: bold;">*</span>
						</div>
					</div>

					<div class="control-group">
						<label class="control-label">数量</label>
						<div class="controls">
							<select name="number" id="a_number" style="width: 60px;"></select>
						</div>
					</div>

					<div class="control-group">
						<label class="control-label">备注</label>
						<div class="controls">
							<textarea class="autogrow" style="height: 30px;" id="a_note"
								name="note"></textarea>
						</div>
					</div>

				</div>
			</div>
			<div class="modal-footer">
				<a href="#" class="btn addInstrumentCancel" data-dismiss="modal">关闭</a>
				<input type="submit" class="btn btn-primary" value="保存">
			</div>
		</form>
	</div>
</div>
<!-- 添加仪器设备信息弹出界面 end -->




<!-- 这里编辑页面要展示的内容  ends -->

<%@include file="/WEB-INF/pages/common/foot.jsp"%>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/statics/localjs/instrumentManage.js"></script>


