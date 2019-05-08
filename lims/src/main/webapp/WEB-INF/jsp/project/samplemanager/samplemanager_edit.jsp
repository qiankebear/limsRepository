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
					
					<form action="samplemanagerr/${msg }.do" name="Form" id="Form" method="post">
						<input type="hidden" name="SAMPLEMANAGERR_ID" id="SAMPLEMANAGERR_ID" value="${pd.SAMPLEMANAGERR_ID}"/>
						<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">样本编号:</td>
								<td><input type="text" name="SAMPLE_NUMBER" id="SAMPLE_NUMBER" value="${pd.SAMPLE_NUMBER}" maxlength="100" placeholder="这里输入样本编号" title="样本编号" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">收样时间:</td>
								<td><input class="span10 date-picker" name="SAMPLE_GENERATE_TIME" id="SAMPLE_GENERATE_TIME" value="${pd.SAMPLE_GENERATE_TIME}" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" placeholder="收样时间" title="收样时间" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">样本进程:</td>
								<td><input type="number" name="SAMPLE_COURSE" id="SAMPLE_COURSE" value="${pd.SAMPLE_COURSE}" maxlength="32" placeholder="这里输入样本进程" title="样本进程" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">轮数:</td>
								<td><input type="number" name="SAMPLE_SERIAL" id="SAMPLE_SERIAL" value="${pd.SAMPLE_SERIAL}" maxlength="32" placeholder="这里输入轮数" title="轮数" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">项目:</td>
								<td><input type="number" name="SAMPLE_PROJECT_ID" id="SAMPLE_PROJECT_ID" value="${pd.SAMPLE_PROJECT_ID}" maxlength="32" placeholder="这里输入项目" title="项目" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="text-align: center;" colspan="10">
									<a class="btn btn-mini btn-primary" onclick="save();">保存</a>
									<a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a>
								</td>
							</tr>
						</table>
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
		//保存
		function save(){
			if($("#SAMPLE_NUMBER").val()==""){
				$("#SAMPLE_NUMBER").tips({
					side:3,
		            msg:'请输入样本编号',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#SAMPLE_NUMBER").focus();
			return false;
			}
			if($("#SAMPLE_GENERATE_TIME").val()==""){
				$("#SAMPLE_GENERATE_TIME").tips({
					side:3,
		            msg:'请输入收样时间',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#SAMPLE_GENERATE_TIME").focus();
			return false;
			}
			if($("#SAMPLE_COURSE").val()==""){
				$("#SAMPLE_COURSE").tips({
					side:3,
		            msg:'请输入样本进程',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#SAMPLE_COURSE").focus();
			return false;
			}
			if($("#SAMPLE_SERIAL").val()==""){
				$("#SAMPLE_SERIAL").tips({
					side:3,
		            msg:'请输入轮数',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#SAMPLE_SERIAL").focus();
			return false;
			}
			if($("#SAMPLE_PROJECT_ID").val()==""){
				$("#SAMPLE_PROJECT_ID").tips({
					side:3,
		            msg:'请输入项目',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#SAMPLE_PROJECT_ID").focus();
			return false;
			}
			$("#Form").submit();
			$("#zhongxin").hide();
			$("#zhongxin2").show();
		}
		
		$(function() {
			//日期框
			$('.date-picker').datepicker({autoclose: true,todayHighlight: true});
			

		});
		</script>
</body>
</html>