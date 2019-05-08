<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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

<!-- jsp文件头和头部 -->
<%@ include file="../index/top.jsp"%>
<!-- 百度echarts -->
<script src="plugins/echarts/echarts.min.js"></script>
<script type="text/javascript">
	/*setTimeout("top.hangge();",500);*/
	setTimeout(function(){
		top.hangge();
	},2000);

</script>
</head>
<body class="no-skin">

	<!-- /section:basics/navbar.layout -->
	<div class="main-container" id="main-container">
		<!-- /section:basics/sidebar -->
		<div class="main-content">
			<div class="main-content-inner">
				<div class="page-content">
					<div class="hr hr-18 dotted hr-double"></div>
					<div class="row">
						<div class="col-xs-12">

							<div class="alert alert-block alert-success">
								<button type="button" class="close" data-dismiss="alert">
									<i class="ace-icon fa fa-times"></i>
								</button>
								<i class="ace-icon fa fa-check green"></i>
								欢迎使用 阅微基因 LIMS 系统&nbsp;&nbsp;
							</div>
							<form action="login_default.do" method="post" name="Form" id="Form">
							<table id="simple-table" class="table table-striped table-bordered table-hover" style="margin-top:5px;">
								<thead>
								<tr>
									<th class="center" style="width:50px;">序号</th>
									<th class="center">项目编号</th>
									<th class="center">项目名称</th>
									<th class="center">项目缩写</th>
									<%--<th class="center">接收样本总数</th>--%>
									<%--<th class="center">检出样本总数</th>--%>
									<th class="center">项目状态</th>
									<th class="center">操作</th>
								</tr>
								</thead>
								<tbody>
								<!-- 开始循环 -->
								<c:choose>
									<c:when test="${not empty varList}">
										<%--<c:if test="${QX.cha == 1 }">--%>
											<c:forEach items="${varList}" var="var" varStatus="vs">
												<tr>
													<td class='center' style="width: 30px;">${page.showCount*(page.currentPage-1)+vs.index+1}</td>
													<td class='center'>${var.project_number}</td>
													<td class='center'>${var.project_name}</td>
													<td class='center'>${var.project_number_abbreviation}</td>
													<%--<td class='center'>${var.allsample}</td>--%>
													<%--<td class='center'>${var.normalsample}</td>--%>
													<c:if test="${var.project_status==1}">
														<td class='center'>准备中</td>
													</c:if>
													<c:if test="${var.project_status==2}">
														<td class='center'>进行中</td>
													</c:if>
													<c:if test="${var.project_status==3}">
														<td class='center'>已完成</td>
													</c:if>
													<c:if test="${var.project_status==4}">
														<td class='center'>其他</td>
													</c:if>
													<td class="center">
														<a class="btn btn-xs  btn-yellow" onclick="showRep('${var.id}');">库存列表</a>
														<a class="btn btn-xs  btn-success" onclick="listPerson('${var.id}');">相关人员</a>

														<a style="display: none" class="btn btn-xs  btn-success" onclick="sampleCheck('${var.id}');">样本总数确认</a>

													</td>
												</tr>
											</c:forEach>
										<%--</c:if>--%>
										<c:if test="${QX.cha == 0 }">
											<tr>
												<td colspan="100" class="center">您无权查看</td>
											</tr>
										</c:if>
									</c:when>
									<c:otherwise>
										<tr class="main_info">
											<td colspan="100" class="center" >没有相关数据</td>
										</tr>
									</c:otherwise>
								</c:choose>
								</tbody>
							</table>
							</form>
							<td style="vertical-align:top;"><div class="pagination" style="float: right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div></td>
							<!-- 删除时确认窗口 -->
							<script src="static/ace/js/bootbox.js"></script>
							<!-- ace scripts -->
							<script src="static/ace/js/ace/ace.js"></script>
							<script type="text/javascript">


                                // 相关人员
                                function listPerson(Id){
                                    top.jzts();
                                    var diag = new top.Dialog();
                                    diag.Drag=true;
                                    diag.Title ="相关人员";
                                    diag.URL = '<%=basePath%>projectmanager/listPerson.do?id='+Id+"&tm="+new Date().getTime();
                                    diag.Width = 650;
                                    diag.Height = 100;
                                    diag.Modal = true;				//有无遮罩窗口
                                    diag. ShowMaxButton = true;	//最大化按钮
                                    diag.ShowMinButton = true;		//最小化按钮
                                    diag.CancelEvent = function(){ //关闭事件
                                        diag.close();
                                    };
                                    diag.show();
                                }
                                //查看库存列表
                                function showRep(Id){
                                    top.jzts();
                                    var diag = new top.Dialog();
                                    diag.Drag=true;
                                    diag.Title ="库存列表";
                                    diag.URL = '<%=basePath%>projectmanager/listRep.do?id='+Id+"&tm="+new Date().getTime();
                                    diag.Width = 550;
                                    diag.Height = 355;
                                    diag.Modal = true;				//有无遮罩窗口
                                    diag. ShowMaxButton = true;	//最大化按钮
                                    diag.ShowMinButton = true;		//最小化按钮
                                    diag.CancelEvent = function(){ //关闭事件
                                        diag.close();
                                    };
                                    diag.show();
                                }
								function  sampleCheck(Id){
									$.ajax({
										type: "POST",
										url: '<%=basePath%>projectmanager/sampleCheck.do?id='+Id+"&tm="+new Date().getTime(),
										dataType:'json',
										cache: false,
										success: function(data){
											// alert(data.data);
											bootbox.confirm(data.data,function (result) {
												if (result){
													tosearch();
												}
											});
										}
									});}
						    </script>
							
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
	<%@ include file="../index/foot.jsp"%>
	<!-- ace scripts -->
	<script src="static/ace/js/ace/ace.js"></script>
	<!-- inline scripts related to this page -->
	<script type="text/javascript">
		$(top.hangge());
	</script>
<script type="text/javascript" src="static/ace/js/jquery.js"></script>
</body>
</html>