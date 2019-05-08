<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
							
						<!-- 检索  -->
						<form action="confirmation/list.do" method="post" name="Form" id="Form">
						<table style="margin-top:5px;">
							<tr>

								<td>
									<div style=" padding-top: 20px; padding-bottom: 10px;">
										<select class="chosen-select form-control" id="sl1" name="sl1" data-placeholder="请选择项目" style="width: 170px">
											<option value=""></option>
											<option value="">全部</option>
											<c:forEach items="${projectAll}" var="user">
												<option value="${user.project_name}" <c:if test="${pd.sl1==user.project_name}">selected</c:if> >${user.project_name }</option>
											</c:forEach>
										</select>
									</div>
								</td>
								<td style="vertical-align:top;padding-top: 20px; padding-bottom: 10px;padding-left: 10px"><a class="btn btn-light btn-xs" onclick="tosearch();"  title="检索"><i id="nav-search-icon" class="ace-icon fa fa-search bigger-110 nav-search-icon blue"></i></a></td>
								<td style="vertical-align:top;padding-top: 20px; padding-bottom: 10px;padding-left: 10px">
								<a class="btn btn-xs  btn-success" onclick="flush();">刷新</a>
								</td>
							</tr>

						</table>
						<!-- 检索  -->
					
						<table id="simple-table" class="table table-striped table-bordered table-hover" style="margin-top:5px;">	
							<thead>
								<tr>
									<th class="center" style="width:50px;">序号</th>
									<th class="center">项目名称</th>
									<th class="center">客户名称</th>
									<th class="center">项目起止时间</th>
									<th class="center">试剂盒名称</th>
									<th class="center">扩增仪器</th>
									<th class="center">检测仪器</th>
									<th class="center">接收样本总数</th>
									<th class="center">检出样本总数</th>
									<th class="center">首轮检测样本数</th>
									<th class="center">检出样本数</th>
									<th class="center">复查样本数</th>
									<th class="center">复检出样本数</th>
									<th class="center">空样本数</th>
									<th class="center">未检出及问题样本</th>
									<%--<th class="center">导出</th>--%>
								</tr>
							</thead>
							<tbody>
							<!-- 开始循环 -->	
							<c:choose>
								<c:when test="${not empty list}">
									<c:forEach items="${list}" var="var" varStatus="vs">
										<tr>
											<td class='center' style="width: 30px;">${page.showCount*(page.currentPage-1)+vs.index+1}</td>
											<td class='center'>${var.st_projectname}</td>
											<td class='center'>${var.st_customer}</td>
											<td class='center'>${var.st_setime}</td>
											<td class='center'>${var.st_kitname}</td>
											<td class='center'>${var.st_ampname}</td>
											<td class='center'>${var.st_checkname}</td>
											<td class='center'>${var.st_recsum}</td>
											<td class='center'>${var.st_checkoutsum}</td>
											<td class='center'>${var.st_frist}</td>
											<td class='center'>${var.st_checkout}</td>
											<td class='center'>${var.st_re}</td>
											<td class='center'>${var.st_recheckout}</td>
											<td class='center'>${var.st_empty}</td>
											<td class='center'>${var.st_problem}</td>
											<%--<td class="center">--%>
												<%--<a href="<%=basePath%>confirmation/goExp?id=${var.id}" class="btn btn-xs btn-purple">导出</a>--%>
											<%--</td>--%>
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
							<td style="vertical-align:top;"><div class="pagination" style="float: right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div></td>

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
	<%@ include file="../../system/index/foot.jsp"%>
	<!-- 删除时确认窗口 -->
	<script src="static/ace/js/bootbox.js"></script>
	<!-- ace scripts -->
	<script src="static/ace/js/ace/ace.js"></script>
	<!-- 下拉框 -->
	<script src="static/ace/js/chosen.jquery.js"></script>
	<!-- 日期框 -->
	<script src="static/ace/js/date-time/bootstrap-datepicker.js"></script>
	<!--提示框-->
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
	<script type="text/javascript">
		$(top.hangge());//关闭加载状态
		//检索
		function tosearch(){
			top.jzts();
			$("#Form").submit();
		}
		$(function() {

            //日期框
            $('.date-picker').datepicker({
                autoclose: true,
                todayHighlight: true
            });

            //下拉框
            if (!ace.vars['touch']) {
                $('.chosen-select').chosen({allow_single_deselect: true});
                $(window)
                    .off('resize.chosen')
                    .on('resize.chosen', function () {
                        $('.chosen-select').each(function () {
                            var $this = $(this);
                            $this.next().css({'width': $this.parent().width()});
                        });
                    }).trigger('resize.chosen');
                $(document).on('settings.ace.chosen', function (e, event_name, event_val) {
                    if (event_name != 'sidebar_collapsed') return;
                    $('.chosen-select').each(function () {
                        var $this = $(this);
                        $this.next().css({'width': $this.parent().width()});
                    });
                });
                $('#chosen-multiple-style .btn').on('click', function (e) {
                    var target = $(this).find('input[type=radio]');
                    var which = parseInt(target.val());
                    if (which == 2) $('#form-field-select-4').addClass('tag-input-style');
                    else $('#form-field-select-4').removeClass('tag-input-style');
                });
            }

            //导出excel
            function exp() {
                window.location.href = '<%=basePath%>confirmation/exp.do';
            }


        })
		function flush() {
			bootbox.confirm("刷新时间较长，确认操作?", function(result) {
				if(result) {
					top.jzts();
					window.location.href = '<%=basePath%>confirmation/flush.do';
				}
			});

		}
	</script>


</body>
</html>