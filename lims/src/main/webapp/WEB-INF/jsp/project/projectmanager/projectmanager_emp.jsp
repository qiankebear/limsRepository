<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
	<base href="<%=basePath%>">
	<!-- 下拉框 -->
	<link rel="stylesheet" href="static/ace/css/chosen.css" />
	<!-- jsp文件头和头部 -->
	<%@ include file="../../system/index/top.jsp"%>
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

						<form action="projectmanager/emp.do" name="Form" id="Form" method="post">
							<input type="hidden" name="id" id="id" value=""/>
							<input type="hidden" name="isup" id="isup" value=""/>
							<div id="zhongxin" style="padding-top: 13px;">
								<c:if test="${user == name}">
								<table id="table_report" class="table table-striped table-bordered table-hover">

										<thead>
										<tr>
											<th class="center">姓名</th>
											<th class="center">当前权限</th>
											<th class="center">操作</th>
										</tr>
										</thead>
										<tbody>
										<!-- 开始循环 -->
										<c:choose>
											<c:when test="${not empty varList}">
													<c:forEach items="${varList}" var="var" varStatus="vs">
														<tr>
															<td class='center'>${var.NAME}</td>
															<c:if test="${var.project_permission==1}">
															<td class='center'>可修改
															</td>
															</c:if>
															<c:if test="${var.project_permission==2}">
															<td class='center'>不可修改
															</td>
															</c:if>
															<td class="center">
																<c:if test="${var.project_permission==2}">
																<a class="btn btn-xs btn-success" title="赋权" onclick="saveemp('${var.id}');">
																赋权</a>
																</c:if>
																<c:if test="${var.project_permission==1}">
																<a class="btn btn-xs btn-danger" title="取消权限" onclick="cancelemp('${var.id}');">
																	取消权限</a>
																</c:if>
															</td>
														</tr>

													</c:forEach>
												<%--<c:if test="${QX.cha == 0 }">--%>
													<%--<tr>--%>
														<%--<td colspan="100" class="center">您无权查看</td>--%>
													<%--</tr>--%>
												<%--</c:if>--%>
											</c:when>
											<c:otherwise>
												<tr class="main_info">
													<td colspan="100" class="center" >没有相关数据</td>
												</tr>
											</c:otherwise>
										</c:choose>
										</tbody>
								</table>
								</c:if>
								<c:if test="${user != name}">
									<h3 align="center" style="color:red" >您没有权限操作！</h3>
								</c:if>
							</div>
							<div id="zhongxin2" class="center" style="display:none"><br/><br/><br/><br/><br/><img src="static/images/jiazai.gif" /><br/><h4 class="lighter block green">提交中...</h4></div>
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
</div>
<!-- /.main-container -->


<!-- 页面底部js¨ -->
<%@ include file="../../system/index/foot.jsp"%>
<!-- 下拉框 -->
<script src="static/ace/js/chosen.jquery.js"></script>
<!-- 日期框 -->
<script src="static/ace/js/date-time/bootstrap-datepicker.js"></script>
<!--提示框-->
<script type="text/javascript" src="static/js/jquery.tips.js"></script>

<script type="text/javascript">
    $(top.hangge());
    //授权
    function saveemp(Id) {
        $("#id").val(Id);
        $("#isup").val(1);
        $("#Form").submit();
    }
    //取消授权
    function cancelemp(Id) {
        $("#id").val(Id);
        $("#isup").val(2);
        $("#Form").submit();
    }


    $(function() {
        //日期框
        $('.date-picker').datepicker({autoclose: true,todayHighlight: true});


    });
</script>
</body>
</html>