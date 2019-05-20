<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!-- content ends -->
</div>
<!--/#content.span10-->
</div>
<!--/fluid-row-->



<!-- 查看用户信息弹出框  start-->
<div class="modal hide fade" id="viewUserDiv">
	<div class="modal-header">
		<h3>查看用户信息</h3>
	</div>
	<div class="modal-body">
		<ul class="dashboard-list">
			<li><a style="font-size: 15px;"> <i class="icon-tags"></i> <span
					class="green" style="width: 88px;">姓名</span> <b id="v_userName"></b>
			</a></li>
			<li><a style="font-size: 15px;"> <i class="icon-tags"></i> <span
					class="green" style="width: 88px;">学号</span> <b id="v_number"></b>
			</a></li>
			<li><a style="font-size: 15px;"> <i class="icon-tags"></i> <span
					class="red" style="width: 88px;">账号</span> <b id="v_userCode"></b>
			</a></li>
			<li><a style="font-size: 15px;"> <i class="icon-tags"></i> <span
					class="yellow" style="width: 88px;">性别</span> <b id="v_gender"></b>
			</a></li>

			<li><a style="font-size: 15px;"> <i class="icon-tags"></i> <span
					class="blue" style="width: 88px;">身份证号</span> <b id="v_idCard"></b>
			</a></li>
			<li><a style="font-size: 15px;"> <i class="icon-tags"></i> <span
					class="green" style="width: 88px;">邮箱</span> <b id="v_email"></b>
			</a></li>
			<li><a style="font-size: 15px;"> <i class="icon-tags"></i> <span
					class="red" style="width: 88px;">手机号码</span> <b id="v_phone"></b>
			</a></li>
			<li><a style="font-size: 15px;"> <i class="icon-tags"></i> <span
					class="blue" style="width: 88px;">所在单位</span> <b id="v_dept"></b>
			</a></li>
			<li><a style="font-size: 15px;"> <i class="icon-tags"></i> <span
					class="yellow" style="width: 88px;">角色</span> <b id="v_userRole"></b>
			</a></li>
			<li><a style="font-size: 15px;"> <i class="icon-tags"></i> <span
					class="green" style="width: 88px;">注册时间</span> <b
					id="v_creationDate"></b>
			</a></li>
		</ul>
	</div>
	<div class="modal-footer">
		<a href="#" class="btn" data-dismiss="modal"> 关 闭 </a>
	</div>
</div>
<!-- 查看用户信息弹出框  end-->


<!-- 查看仪器设备信息弹出界面 start -->
<div class="modal hide fade viewInstrumentTableCss"
	id="viewInstrumentDiv">
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal">×</button>
		<h3 style="text-align: center;">仪器详细信息</h3>
	</div>

	<table border="1" cellspacing="0" cellpadding="0">
		<tr>
			<td class="InstrumentTitleCss">仪器名称</td>
			<td class="InstrumentContentCss" id="v_instrumentName"></td>
			<td class="InstrumentTitleCss">仪器编号</td>
			<td class="InstrumentContentCss" id="v_instrumentNo"></td>
		</tr>
		<tr>
			<td class="InstrumentTitleCss">仪器型号</td>
			<td class="InstrumentContentCss" id="v_instrumentType"></td>
			<td class="InstrumentTitleCss">所属类别</td>
			<td class="InstrumentContentCss" id="v_className"></td>
		</tr>
		<tr>
			<td class="InstrumentTitleCss">生产厂商</td>
			<td class="InstrumentContentCss" id="v_manufacturer"></td>
			<td class="InstrumentTitleCss">单价（元）</td>
			<td class="InstrumentContentCss" id="v_price"></td>
		</tr>
		<tr>
			<td class="InstrumentTitleCss">出厂日期</td>
			<td class="InstrumentContentCss" id="v_productionDate"></td>
			<td class="InstrumentTitleCss">国别</td>
			<td class="InstrumentContentCss" id="v_country"></td>
		</tr>
		<tr>
			<td class="InstrumentTitleCss">库存负责人</td>
			<td class="InstrumentContentCss" id="v_instrumentManager"></td>
			<td class="InstrumentTitleCss">联系方式</td>
			<td class="InstrumentContentCss" id="v_managerTel"></td>
		</tr>
		<tr>
			<td class="InstrumentTitleCss">库存所在单位</td>
			<td class="InstrumentContentCss" id="v_dept"></td>
			<td class="InstrumentTitleCss">具体存放地点</td>
			<td class="InstrumentContentCss" id="v_locName"></td>
		</tr>
	</table>
	<div class="modal-footer">
		<a href="#" class="btn viewInstrumentCancel" data-dismiss="modal">关闭</a>
	</div>
</div>
<!-- 查看仪器设备信息弹出界面 end -->



</div>
<!--/.fluid-container-->
<!-- footer starts -->
<footer class="clims_footer">
	<p class="pull-left">
		<span>CLIMS开发团队&nbsp;&nbsp;&copy;2019&nbsp;&nbsp;DubLBo&emsp;&emsp;版权所有</span>
	</p>
	<p class="pull-right">
		Contact us: <b>&nbsp;dubulingbo@163.com</b>
	</p>
</footer>
<!-- footer ends -->



<!-- external javascript
	================================================== -->
<!-- Placed at the end of the document so the pages load faster -->

<!-- jQuery -->
<script
	src="${pageContext.request.contextPath }/statics/js/jquery-1.7.2.min.js"></script>
<!-- jQuery UI -->
<script
	src="${pageContext.request.contextPath }/statics/js/jquery-ui-1.8.21.custom.min.js"></script>
<!-- transition / effect library -->
<script
	src="${pageContext.request.contextPath }/statics/js/bootstrap-transition.js"></script>
<!-- alert enhancer library -->
<script
	src="${pageContext.request.contextPath }/statics/js/bootstrap-alert.js"></script>
<!-- modal / dialog library -->
<script
	src="${pageContext.request.contextPath }/statics/js/bootstrap-modal.js"></script>
<!-- custom dropdown library -->
<script
	src="${pageContext.request.contextPath }/statics/js/bootstrap-dropdown.js"></script>
<!-- scrolspy library -->
<script
	src="${pageContext.request.contextPath }/statics/js/bootstrap-scrollspy.js"></script>
<!-- library for creating tabs -->
<script
	src="${pageContext.request.contextPath }/statics/js/bootstrap-tab.js"></script>
<!-- library for advanced tooltip -->
<script
	src="${pageContext.request.contextPath }/statics/js/bootstrap-tooltip.js"></script>
<!-- popover effect library -->
<script
	src="${pageContext.request.contextPath }/statics/js/bootstrap-popover.js"></script>
<!-- button enhancer library -->
<script
	src="${pageContext.request.contextPath }/statics/js/bootstrap-button.js"></script>
<!-- accordion library (optional, not used in demo) -->
<script
	src="${pageContext.request.contextPath }/statics/js/bootstrap-collapse.js"></script>
<!-- carousel slideshow library (optional, not used in demo) -->
<script
	src="${pageContext.request.contextPath }/statics/js/bootstrap-carousel.js"></script>
<!-- autocomplete library -->
<script
	src="${pageContext.request.contextPath }/statics/js/bootstrap-typeahead.js"></script>
<!-- tour library -->
<script
	src="${pageContext.request.contextPath }/statics/js/bootstrap-tour.js"></script>
<!-- library for cookie management -->
<script
	src="${pageContext.request.contextPath }/statics/js/jquery.cookie.js"></script>
<!-- calander plugin -->
<script
	src='${pageContext.request.contextPath }/statics/js/fullcalendar.min.js'></script>
<!-- data table plugin -->
<script
	src='${pageContext.request.contextPath }/statics/js/jquery.dataTables.min.js'></script>

<!-- chart libraries start -->
<script src="${pageContext.request.contextPath }/statics/js/excanvas.js"></script>
<script
	src="${pageContext.request.contextPath }/statics/js/jquery.flot.min.js"></script>
<script
	src="${pageContext.request.contextPath }/statics/js/jquery.flot.pie.min.js"></script>
<script
	src="${pageContext.request.contextPath }/statics/js/jquery.flot.stack.js"></script>
<script
	src="${pageContext.request.contextPath }/statics/js/jquery.flot.resize.min.js"></script>
<!-- chart libraries end -->

<!-- select or dropdown enhancer -->
<script
	src="${pageContext.request.contextPath }/statics/js/jquery.chosen.min.js"></script>
<!-- checkbox, radio, and file input styler -->
<script
	src="${pageContext.request.contextPath }/statics/js/jquery.uniform.min.js"></script>
<!-- plugin for gallery image view -->
<script
	src="${pageContext.request.contextPath }/statics/js/jquery.colorbox.min.js"></script>
<!-- rich text editor library -->
<script
	src="${pageContext.request.contextPath }/statics/js/jquery.cleditor.min.js"></script>
<!-- notification plugin -->
<script
	src="${pageContext.request.contextPath }/statics/js/jquery.noty.js"></script>
<!-- file manager library -->
<script
	src="${pageContext.request.contextPath }/statics/js/jquery.elfinder.min.js"></script>
<!-- star rating plugin -->
<script
	src="${pageContext.request.contextPath }/statics/js/jquery.raty.min.js"></script>
<!-- for iOS style toggle switch -->
<script
	src="${pageContext.request.contextPath }/statics/js/jquery.iphone.toggle.js"></script>
<!-- autogrowing textarea plugin -->
<script
	src="${pageContext.request.contextPath }/statics/js/jquery.autogrow-textarea.js"></script>
<!-- multiple file upload plugin -->
<script
	src="${pageContext.request.contextPath }/statics/js/jquery.uploadify-3.1.min.js"></script>
<!-- history.js for cross-browser state change on ajax -->
<script
	src="${pageContext.request.contextPath }/statics/js/jquery.history.js"></script>
<!-- application script for Charisma demo -->
<script src="${pageContext.request.contextPath }/statics/js/charisma.js"></script>

<!--  start 2019-->
<script src="${pageContext.request.contextPath }/statics/js/json2.js"></script>
<!-- add by DubLBo 2019-04-18 multiple file ajaxfileupload plugin-->
<script type="text/javascript"
	src="${pageContext.request.contextPath }/statics/js/ajaxfileupload.js"></script>
<!-- add by DubLBo 2019-04-18 WdatePicker plugin-->
<script type="text/javascript"
	src="${pageContext.request.contextPath }/statics/medire/WdatePicker.js"></script>

<script type="text/javascript"
	src="${pageContext.request.contextPath }/statics/localjs/clims_common.js"></script>

<!-- 外部引用的js文件 end 2019-->
</html>