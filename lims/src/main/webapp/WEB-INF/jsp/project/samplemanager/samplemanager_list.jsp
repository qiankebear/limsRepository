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
						<form action="samplemanager/list.do" method="post" name="Form" id="Form">
						<table style="margin-top:5px;">
							<tr>
								<td>
									<div style="padding-top: 20px; padding-bottom: 10px;" class="nav-search">
										<span class="input-icon">
											<input type="text" placeholder="这里输入样本编号" class="nav-search-input" id="nav-search-input1" autocomplete="off" name="keywords1" value="${pd.keywords1}" placeholder="这里输入样本编号"/>
											<i class="ace-icon fa fa-search nav-search-icon"></i>
										</span>
									</div>
								</td>
								<td>
									<div style=" padding-top: 20px; padding-bottom: 10px;padding-left: 20px">
										<select class="chosen-select form-control" id="sl1" name="sl1" data-placeholder="请选择项目" style="width: 170px">
											<option value=""></option>
											<option value="">全部</option>
											<c:forEach items="${projectAll}" var="user">
											<option value="${user.project_name}" <c:if test="${pd.sl1==user.project_name}">selected</c:if> >${user.project_name }</option>
											</c:forEach>
										</select>
									</div>
								</td>
								<td>
									<div style=" padding-top: 20px; padding-bottom: 10px;padding-left: 20px">
										<select class="chosen-select form-control" id="sl2" name="sl2" data-placeholder="请选择剪板人" style="width: 170px">
											<option value=""></option>
											<option value="">全部</option>
											<c:forEach items="${userAll}" var="user">
												<option value="${user.USER_ID}" <c:if test="${pd.sl2==user.USER_ID}">selected</c:if> >${user.NAME }</option>
											</c:forEach>
										</select>
									</div>
								</td>
								<%--<td style="padding-left:2px;"><input class="span10 date-picker" name="lastStart" id="lastStart"  value="" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" style="width:88px;" placeholder="开始日期" title="开始日期"/></td>--%>
								<%--<td style="padding-left:2px;"><input class="span10 date-picker" name="lastEnd" id="lastEnd"  value="" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" style="width:88px;" placeholder="结束日期" title="结束日期"/></td>--%>
								<td style="vertical-align:top;padding-top: 20px; padding-bottom: 10px;padding-left: 20px">
								 	<select class="chosen-select form-control" name="course" id="course" data-placeholder="请选择当前进程" style="vertical-align:top;width: 135px;">
									<option value=""></option>
									<option value="">全部</option>
									<option value="1" <c:if test="${pd.course=='1'}">selected</c:if>>成功</option>
									<option value="2" <c:if test="${pd.course=='2'}">selected</c:if>>重做</option>
										<option value="3" <c:if test="${pd.course=='3'}">selected</c:if>>问题样本</option>
										<option value="4" <c:if test="${pd.course=='4'}">selected</c:if>>放弃重做</option>
										<option value="5" <c:if test="${pd.course=='5'}">selected</c:if>>待确定</option>
								  	</select>
								</td>
								<td style="vertical-align:top;padding-top: 20px; padding-bottom: 10px;padding-left: 20px">
									<select class="chosen-select form-control" name="serial" id="serial" data-placeholder="请选择轮数" style="vertical-align:top;width: 120px;">
										<option value=""></option>
										<option value="">全部</option>
										<option value="1" <c:if test="${pd.serial=='1'}">selected</c:if>>一轮</option>
										<option value="2" <c:if test="${pd.serial=='2'}">selected</c:if>>二轮</option>
										<option value="3" <c:if test="${pd.serial=='3'}">selected</c:if>>三轮</option>
										<option value="4" <c:if test="${pd.serial=='4'}">selected</c:if>>四轮</option>
									</select>
								</td>
								<td style="vertical-align:top;padding-top: 20px; padding-bottom: 10px;padding-left: 10px"><a class="btn btn-light btn-xs" onclick="tosearch();"  title="检索"><i id="nav-search-icon" class="ace-icon fa fa-search bigger-110 nav-search-icon blue"></i></a></td>

								<%--<c:if test="${QX.toExcel == 1 }"><td style="vertical-align:top;padding-left:2px;"><a class="btn btn-light btn-xs" onclick="toExcel();" title="导出到EXCEL"><i id="nav-search-icon" class="ace-icon fa fa-download bigger-110 nav-search-icon blue"></i></a></td></c:if>--%>
							</tr>
						</table>
						<!-- 检索  -->
					
						<table id="simple-table" class="table table-striped table-bordered table-hover" style="margin-top:5px;">	
							<thead>
								<tr>
									<th class="center" style="width:50px;">序号</th>
									<th class="center">样本编号</th>
									<th class="center">收样时间</th>
									<th class="center">样本进程</th>
									<th class="center">轮数</th>
									<th class="center">项目名称</th>
									<th class="center">第一次所在孔板的孔板编号</th>
									<th class="center">剪板人</th>
								</tr>
							</thead>
													
							<tbody>
							<!-- 开始循环 -->	
							<c:choose>
								<c:when test="${not empty varList}">
									<c:forEach items="${varList}" var="var" varStatus="vs">
										<tr>
											<td class='center' style="width: 30px;">${page.showCount*(page.currentPage-1)+vs.index+1}</td>
											<td class='center'>${var.sample_number}</td>
											<td class='center'>${var.newDate}</td>
											<c:if test="${var.sample_course==1}">
											<td class='center'>成功</td>
											</c:if>
											<c:if test="${var.sample_course==2}">
												<td class='center'>重做</td>
											</c:if>
											<c:if test="${var.sample_course==3}">
												<td class='center'>问题样本</td>
											</c:if>
											<c:if test="${var.sample_course==4}">
												<td class='center'>放弃重做</td>
											</c:if>
											<c:if test="${var.sample_course==5}">
												<td class='center'>待确定</td>
											</c:if>

											<c:if test="${var.sample_serial==1}">
											<td class='center'>第一轮</td>
											</c:if>
											<c:if test="${var.sample_serial==2}">
												<td class='center'>第二轮</td>
											</c:if>
											<c:if test="${var.sample_serial==3}">
												<td class='center'>第三轮</td>
											</c:if>
											<c:if test="${var.sample_serial==4}">
												<td class='center'>第四轮</td>
											</c:if>
											<td class='center'>${var.project_name}</td>
											<td class='center'>${var.pore_plate_name}</td>
											<td class='center'>${var.name}</td>
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
										<td colspan="100" class="center" style="color:#ff1213;" >进行检索后展示数据</td>
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
	<script src="static/ace/js/chosen.jquery1.js"></script>
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
			if(!ace.vars['touch']) {
				$('.chosen-select').chosen({allow_single_deselect:true}); 
				$(window)
				.off('resize.chosen')
				.on('resize.chosen', function() {
					$('.chosen-select').each(function() {
						 var $this = $(this);
						 $this.next().css({'width': $this.parent().width()});
					});
				}).trigger('resize.chosen');
				$(document).on('settings.ace.chosen', function(e, event_name, event_val) {
					if(event_name != 'sidebar_collapsed') return;
					$('.chosen-select').each(function() {
						 var $this = $(this);
						 $this.next().css({'width': $this.parent().width()});
					});
				});
				$('#chosen-multiple-style .btn').on('click', function(e){
					var target = $(this).find('input[type=radio]');
					var which = parseInt(target.val());
					if(which == 2) $('#form-field-select-4').addClass('tag-input-style');
					 else $('#form-field-select-4').removeClass('tag-input-style');
				});
			}
			
			
			//复选框全选控制
			var active_class = 'active';
			$('#simple-table > thead > tr > th input[type=checkbox]').eq(0).on('click', function(){
				var th_checked = this.checked;//checkbox inside "TH" table header
				$(this).closest('table').find('tbody > tr').each(function(){
					var row = this;
					if(th_checked) $(row).addClass(active_class).find('input[type=checkbox]').eq(0).prop('checked', true);
					else $(row).removeClass(active_class).find('input[type=checkbox]').eq(0).prop('checked', false);
				});
			});
		});
		

		//导出excel
		function toExcel(){
			window.location.href='<%=basePath%>samplemanagerr/excel.do';
		}
	</script>


</body>
</html>