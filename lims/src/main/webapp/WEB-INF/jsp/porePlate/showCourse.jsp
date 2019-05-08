<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
	<base href="<%=basePath%>">
	<!-- 下拉框 -->
	<link rel="stylesheet" href="static/ace/css/chosen.css" />
	<!-- jsp文件头和头部 -->
	<%@ include file="../system/index/top.jsp"%>
	<!-- 日期框 -->
	<link rel="stylesheet" href="static/ace/css/datepicker.css" />
</head>
<body class="no-skin">

<!-- /section:basics/navbar.layout -->
<div class="main-container" id="main-container">
	<!-- /section:basics/sidebar -->
	<div class="main-content">
		<div class="main-content-inner">
			<div class="page-content">
				<div class="row">
					<div class="col-xs-12">

						<!-- 检索  -->
						<form action="customer/projectList.do?project_client=${pd.project_client}" method="post" name="userForm" id="userForm">
							<!-- 检索  -->
							<table id="simple-table" class="table table-striped table-bordered table-hover"  style="margin-top:5px;">
								<thead>
								<tr>
									<th class="center" style="width:50px;">序号</th>
									<th class="center">板号</th>
									<th class="center">样本编号</th>
									<th class="center">坐标</th>
									<th class="center">重做原因</th>
									<th class="center">备注</th>
								</tr>
								</thead>
								<tbody>

								<!-- 开始循环 -->
								<c:choose>
									<c:when test="${not empty sampleCourse}">
											<c:forEach items="${sampleCourse}" var="sample" varStatus="vs">

												<tr>
													<td class='center' style="width: 30px;">${page.showCount*(page.currentPage-1)+vs.index+1}</td>
													<td class="center">${sample.pore_plate_name }</td>
													<td class="center">${sample.sample_number }</td>
													<td class="center">${sample.hole_number }</td>
													<c:if test="${sample.pore_plate_entirety == 1}">
														<td class="center">整版重扩</td>
													</c:if>
													<c:if test="${sample.pore_plate_entirety != 1}">
													<c:if test="${sample.hole_special_sample == 4}">
														<td class="center">重复分型</td>
													</c:if>
													<c:if test="${sample.hole_special_sample == 5}">
														<td class="center">失败</td>
													</c:if>
													<c:if test="${sample.hole_special_sample == null || sample.hole_special_sample ==''}">
														<td class="center"></td>
													</c:if>
													</c:if>
													<td class="center">${sample.hole_sample_remark}</td>
												</tr>

											</c:forEach>
									</c:when>
									<c:otherwise>
										<tr class="main_info">
											<td colspan="10" class="center">没有相关数据</td>
										</tr>
									</c:otherwise>
								</c:choose>
								</tbody>
							</table>

							<div class="page-header position-relative">
								<table style="width:100%;">
									<tr>
										<td style="vertical-align:top;"><div class="pagination" style="float: right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div></td>
									</tr>
								</table>
							</div>
						</form>

					</div>
					<!-- /.col -->
				</div>
				<!-- /.row -->
			</div>
			<!-- /.page-content -->
		</div>
	</div>
	<!-- /.main-content -->

	<!-- 返回顶部 -->
	<a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
		<i class="ace-icon fa fa-angle-double-up icon-only bigger-110"></i>
	</a>

</div>
<!-- /.main-container -->

<!-- basic scripts -->
<!-- 页面底部js¨ -->
<%@ include file="../system/index/foot.jsp"%>
<!-- 删除时确认窗口 -->
<script src="static/ace/js/bootbox.js"></script>
<!-- ace scripts -->
<script src="static/ace/js/ace/ace.js"></script>
<!-- 日期框 -->
<script src="static/ace/js/date-time/bootstrap-datepicker.js"></script>
<!-- 下拉框 -->
<script src="static/ace/js/chosen.jquery.js"></script>
<!--提示框-->
<script type="text/javascript" src="static/js/jquery.tips.js"></script>
</body>

<script type="text/javascript">
    $(top.hangge());


</script>
</html>
