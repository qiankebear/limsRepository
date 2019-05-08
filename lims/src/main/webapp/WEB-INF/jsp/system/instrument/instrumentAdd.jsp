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
<!-- 下拉框 -->
<link rel="stylesheet" href="static/ace/css/chosen.css" />
<!-- jsp文件头和头部 -->
<%@ include file="../index/top.jsp"%>
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
							<form action="instrument/${msg }.do" name="userForm" id="userForm" method="post">
									<input type="hidden" name="id" value="${pd.id}" id="id" />
									<div id="zhongxin" style="padding-top: 13px;">
									<table id="table_report" class="table table-striped table-bordered table-hover">
										<tr>
											<td style="width:79px;text-align: right;padding-top: 13px;">仪器型号:</td>
											<td><input type="text" name="INSTRUMENT_TYPE" placeholder="必填项" maxlength="100" id="Model"value="${pd.instrument_type}" style="width:98%;"/></td>
										</tr>
										<tr>
											<td style="width:79px;text-align: right;padding-top: 13px;">仪器编号</td>
											<td><input type="text" name="INSTRUMENT_NUMBER" placeholder="必填项" maxlength="100" id="number"value="${pd.instrument_number}" style="width:98%;"/></td>
										</tr>
										<tr>
											<td style="width:79px;text-align: right;padding-top: 13px;">仪器状态:</td>
											<td>
												<select  name="INSTRUMENT_STATUS" id="state" style="vertical-align:top;width:98%;" >
													<option value ="" disabled selected style='display:none;'>请选择仪器状态</option>
													<option value ="1" <c:if test="${pd.instrument_status == '1'}">selected</c:if>>运行</option>
													<option value ="2" <c:if test="${pd.instrument_status == '2'}">selected</c:if>>保养</option>
													<option value ="3" <c:if test="${pd.instrument_status == '3'}">selected</c:if>>检修</option>
													<option value ="4" <c:if test="${pd.instrument_status == '4'}">selected</c:if>>封存</option>
													<option value ="5" <c:if test="${pd.instrument_status == '5'}">selected</c:if>>报废</option>
												</select>
											</td>
										</tr>
										<tr>
											<td style="width:70px;text-align: right;padding-top: 13px;">类型:</td>
											<td>
												<select  name="INSTRUMENT_CLASSIFY" id="type"style="vertical-align:top;width:98%;" >
													<option value='' disabled selected style='display:none;'>请选择仪器类型</option>
													<option value ="1" <c:if test="${pd.instrument_classify == '1'}">selected</c:if>>PCR仪</option>
													<option value ="2" <c:if test="${pd.instrument_classify == '2'}">selected</c:if>>测序仪</option>
													<option value="3"  <c:if test="${pd.instrument_classify == '3'}">selected</c:if>>移液仪</option>
													<option value="4"  <c:if test="${pd.instrument_classify == '4'}">selected</c:if>>其他设备</option>
												</select>
											</td>
										</tr>
										<tr>
											<td style="width:79px;text-align: right;padding-top: 13px;">其他信息:</td>
											<td><input type="text"name="INSTRUMENT_OTHER" placeholder="必填项" maxlength="100" id="information"value="${pd.instrument_other}"  style="width:98%;"/></td>
										</tr>
										<tr>
											<td style="width:100px;text-align: right;padding-top: 13px;">保存的程序:</td>
											<td><input type="text" name="SAVE_PROCEDURE"placeholder="必填项"maxlength="100" id="save"value="${pd.save_procedure}" style="width:98%;"/></td>
										</tr>
										<tr>
											<td style="text-align: center;" colspan="10">
												<a class="btn btn-mini btn-primary" onclick="save();">保存</a>
												<a class="btn btn-mini btn-danger" id="censel" onclick="top.Dialog.close();">取消</a>
											</td>
										</tr>
									</table>
									</div>
									<div id="zhongxin2" class="center" style="display:none"><br/><br/><br/><br/><img src="static/images/jiazai.gif" /><br/><h4 class="lighter block green"></h4></div>
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
	<!-- basic scripts -->
	<!-- 页面底部js¨ -->
	<%@ include file="../index/foot.jsp"%>
	<!-- ace scripts -->
	<script src="static/ace/js/ace/ace.js"></script>
	<!-- inline scripts related to this page -->
	<!-- 下拉框 -->
	<script src="static/ace/js/chosen.jquery.js"></script>
	<!--提示框-->
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
</body>
<script type="text/javascript">
	$(top.hangge());
	$(document).ready(function(){
		$('#form-field-select-4').addClass('tag-input-style');
		if($("#user_id").val()!=""){
			$("#loginname").attr("readonly","readonly");
			$("#loginname").css("color","gray");
		}
	});
	//保存

	function save(){
        if($("#Model").val()==""){
            $("#Model").tips({
                side:3,
                msg:'输入仪器型号',
                bg:'#AE81FF',
                time:3
            });
            $("#Model").focus();
            return false;
        }
        if($("#number").val()==""){
            $("#number").tips({
                side:3,
                msg:'输入编号',
                bg:'#AE81FF',
                time:3
            });
            $("#number").focus();
            return false;
        }
        if($("#state").val()==null){
            $("#state").tips({
                side:3,
                msg:'输入仪器状态 ',
                bg:'#AE81FF',
                time:3
            });
            $("#state").focus();
            return false;
        }
        if($("#type").val()==null){
            $("#type").tips({
                side:3,
                msg:'输入类型',
                bg:'#AE81FF',
                time:3
            });
            $("#type").focus();
            return false;
        }
        if($("#information").val()==""){
            $("#information").tips({
                side:3,
                msg:'输入其他信息',
                bg:'#AE81FF',
                time:3
            });
            $("#information").focus();
            return false;
        }
        if($("#save").val()==""){
            $("#save").tips({
                side:3,
                msg:'输入保存的程序',
                bg:'#AE81FF',
                time:3
            });
            $("#save").focus();
            return false;
        }
        $("#userForm").submit();
        $("#zhongxin").hide();
        $("#zhongxin2").show();
	}
	function ismail(mail){
		return(new RegExp(/^(?:[a-zA-Z0-9]+[_\-\+\.]?)*[a-zA-Z0-9]+@(?:([a-zA-Z0-9]+[_\-]?)*[a-zA-Z0-9]+\.)+([a-zA-Z]{2,})+$/).test(mail));
		}

	//判断用户名是否存在
	function hasU(){
		var USERNAME = $.trim($("#loginname").val());
		$.ajax({
			type: "POST",
			url: '<%=basePath%>user/hasU.do',
	    	data: {USERNAME:USERNAME,tm:new Date().getTime()},
			dataType:'json',
			cache: false,
			success: function(data){
				 if("success" == data.result){
					$("#userForm").submit();
					$("#zhongxin").hide();
					$("#zhongxin2").show();
				 }else{
					$("#loginname").css("background-color","#D16E6C");
					setTimeout("$('#loginname').val('此用户名已存在!')",500);
				 }
			}
		});
	}

	//判断邮箱是否存在
	function hasE(USERNAME){
		var EMAIL = $.trim($("#EMAIL").val());
		$.ajax({
			type: "POST",
			url: '<%=basePath%>user/hasE.do',
	    	data: {EMAIL:EMAIL,USERNAME:USERNAME,tm:new Date().getTime()},
			dataType:'json',
			cache: false,
			success: function(data){
				 if("success" != data.result){
					 $("#EMAIL").tips({
							side:3,
				            msg:'邮箱 '+EMAIL+' 已存在',
				            bg:'#AE81FF',
				            time:3
				        });
					 $("#EMAIL").val('');
				 }
			}
		});
	}

	//判断编码是否存在
	function hasN(USERNAME){
		var NUMBER = $.trim($("#NUMBER").val());
		$.ajax({
			type: "POST",
			url: '<%=basePath%>user/hasN.do',
	    	data: {NUMBER:NUMBER,USERNAME:USERNAME,tm:new Date().getTime()},
			dataType:'json',
			cache: false,
			success: function(data){
				 if("success" != data.result){
					 $("#NUMBER").tips({
							side:3,
				            msg:'编号 '+NUMBER+' 已存在',
				            bg:'#AE81FF',
				            time:3
				        });
					 $("#NUMBER").val('');
				 }
			}
		});
	}
	$(function() {
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
	});

	//移除副职角色
	function removeRoleId(ROLE_ID){
		var OROLE_IDS = $("#ROLE_IDS");
		var ROLE_IDS = OROLE_IDS.val();
		ROLE_IDS = ROLE_IDS.replace(ROLE_ID+",fh,","");
		OROLE_IDS.val(ROLE_IDS);
	}
	//添加副职角色
	function addRoleId(ROLE_ID){
		var OROLE_IDS = $("#ROLE_IDS");
		var ROLE_IDS = OROLE_IDS.val();
		if(!isContains(ROLE_IDS,ROLE_ID)){
			ROLE_IDS = ROLE_IDS + ROLE_ID + ",fh,";
			OROLE_IDS.val(ROLE_IDS);
		}
	}
	function isContains(str, substr) {
	     return str.indexOf(substr) >= 0;
	 }
</script>
</html>