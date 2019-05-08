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
					
					<form action="otherkitsmanager/${msg }.do" name="Form" id="Form" method="post">
						<input type="hidden" name="id" id="id" value="${pd.id}"/>
						<div id="zhongxin" style="padding-top: 13px;">
							<table id="table_report" class="table table-striped table-bordered table-hover">
								<tr>
									<td style="width:75px;text-align: right;padding-top: 13px;">名称:</td>
									<td><input type="text" name="KIT_NAME" id="KIT_NAME" value="${pd.KIT_NAME}" maxlength="255" placeholder="这里输入名称" title="名称" style="width:100%;"/></td>
								</tr>
								<tr>
									<td style="width:75px;text-align: right;padding-top: 13px;">所属分类:</td>
									<td><select class="chosen-select form-control" name="KIT_TYPE" id="KIT_TYPE">
										<option value ="" >请选择</option>
										<option value ="10" <c:if test="${pd.KIT_TYPE=='10'}">selected</c:if>>采血卡</option>
										<option value ="11" <c:if test="${pd.KIT_TYPE=='11'}">selected</c:if>>一代测序仪的耗材</option>
										<option value ="12" <c:if test="${pd.KIT_TYPE=='2'}">selected</c:if>>其他耗材</option>
									</select></td>
								</tr>
								<tr>
									<td style="width:75px;text-align: right;padding-top: 13px;">备注:</td>
									<td><input type="text" name="KIT_REMARK" id="KIT_REMARK" value="${pd.KIT_REMARK}" maxlength="255" placeholder="这里输入备注" title="备注" style="width:100%;"/></td>
								</tr>
								<tr>
									<td style="width:75px;text-align: right;padding-top: 13px;">规格:</td>
									<td><input type="text" name="SPECIFICATION" id="SPECIFICATION" value="${pd.SPECIFICATION}" maxlength="255" placeholder="这里输入规格" title="规格" style="width:100%;"/></td>
								</tr>
								<tr>
									<td style="width:75px;text-align: right;padding-top: 13px;">品牌:</td>
									<td><input type="text" name="BRAND" id="BRAND" value="${pd.BRAND}" maxlength="255" placeholder="这里输入品牌" title="品牌" style="width:100%;"/></td>
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
        $("input").change(function(){
            $(this).val($(this).val().trim());
        });

        String.prototype.trim=function(){
            return this.replace(/(^\s*)|(\s*$)/g, "");
        }
		//保存
		function save(){
			if($("#KIT_NAME").val()==""){
				$("#KIT_NAME").tips({
					side:3,
		            msg:'请输入名称',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#KIT_NAME").focus();
			return false;
			}
			if($("#KIT_TYPE").val()==""){
				$("#KIT_TYPE").tips({
					side:3,
		            msg:'请选择所属分类',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#KIT_TYPE").focus();
			return false;
			}
			if($("#KIT_REMARK").val()==""){
				$("#KIT_REMARK").tips({
					side:3,
		            msg:'请输入备注',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#KIT_REMARK").focus();
			return false;
			}
			if($("#SPECIFICATION").val()==""){
				$("#SPECIFICATION").tips({
					side:3,
		            msg:'请输入规格',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#SPECIFICATION").focus();
			return false;
			}
			if($("#BRAND").val()==""){
				$("#BRAND").tips({
					side:3,
		            msg:'请输入品牌',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#BRAND").focus();
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