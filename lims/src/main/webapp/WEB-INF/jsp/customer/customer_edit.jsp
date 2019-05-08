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
	<%@ include file="../system/index/top.jsp"%>
	<!-- 日期框 (带小时分钟)-->
	<link rel="stylesheet" href="static/ace/css/bootstrap-datetimepicker.css" />
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
								<form action="customer/${msg }.do" name="userForm" id="userForm" method="post">
									<textarea style="display: none;" name="id" id="id" >${pd.id }</textarea>
									<div id="zhongxin" style="padding-top: 13px;">
									<table id="table_report" class="table table-striped table-bordered table-hover">
										<tr>
											<td style="width:120px;text-align: right;padding-top: 13px;">客户名称:</td>
											<td><input type="text" name="client_name" id="client_name" value="${pd.client_name }" maxlength="32" placeholder="这里输入客户名称" title="客户名称" style="width:98%;"/></td>
										</tr>
										<tr>
											<td style="width:79px;text-align: right;padding-top: 13px;">客户编号:</td>
											<td><input type="text" name="client_number" id="client_number" value="${pd.client_number }" maxlength="32" placeholder="这里输入客户编号" title="客户编号" style="width:98%;"/></td>
										</tr>
										<tr>
											<td style="width:79px;text-align: right;padding-top: 13px;">客户联系人姓名:</td>
											<td><input type="text" name="client_linkman" id="client_linkman" value="${pd.client_linkman }" maxlength="32" placeholder="这里输入客户联系人姓名" title="客户联系人姓名" style="width:98%;"/></td>
										</tr>
										<tr>
											<td style="width:79px;text-align: right;padding-top: 13px;">客户联系人电话:</td>
											<td><input type="text" name="client_phone" id="client_phone" value="${pd.client_phone }" maxlength="32" placeholder="这里输入客户联系人电话" title="客户联系人电话" onblur="hasN('${pd.USERNAME }')" style="width:98%;"/></td>
										</tr>
										<tr>
											<td style="width:79px;text-align: right;padding-top: 13px;">客户联系人邮箱:</td>
											<td><input type="text" name="client_emil" id="client_emil"  value="${pd.client_emil }" maxlength="32" placeholder="这里输入客户联系人邮箱" title="客户联系人邮箱" style="width:98%;"/></td>
										</tr>
										<tr>
											<td style="width:79px;text-align: right;padding-top: 13px;">地址:</td>
											<td><input type="text" name="client_address" id="client_address" value="${pd.client_address }" maxlength="32" placeholder="这里输入地址" style="width:98%;"/></td>
										</tr>
										<tr>
										<td style="width:75px;text-align: right;padding-top: 13px;">成立时间:</td>
										<td>
											<div class="input-group bootstrap-timepicker">
											<input readonly="readonly" class="form-control" type="text" name="client_jointime" id="client_jointime" value="${pd.client_jointime}" maxlength="100" placeholder="这里输入成立时间" title="成立时间" style="width:100%;"/>
											<span class="input-group-addon"><i class="fa fa-clock-o bigger-110"></i></span>
											</div>
										</td>
										</tr>
										<tr>
											<td style="text-align: center;" colspan="10">
												<a class="btn btn-mini btn-primary" onclick="save();">保存</a>
												<a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a>
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
	<%@ include file="../system/index/foot.jsp"%>
	<!-- ace scripts -->
	<script src="static/ace/js/ace/ace.js"></script>
	<!-- inline scripts related to this page -->
	<!-- 下拉框 -->
	<script src="static/ace/js/chosen.jquery.js"></script>
	<!--提示框-->
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
	<!-- 日期框(带小时分钟) -->
	<script src="static/ace/js/date-time/moment.js"></script>
	<script src="static/ace/js/date-time/locales.js"></script>
	<script src="static/ace/js/date-time/bootstrap-datetimepicker.js"></script>
</body>
<script type="text/javascript">
    $(function() {
        //日期框(带时间)
        $('.form-control').datetimepicker().next().on(ace.click_event, function(){
            $(this).prev().focus();
        });
    });
	$(top.hangge());
	$(document).ready(function(){
		$('#form-field-select-4').addClass('tag-input-style');

	});
	//保存
	function save(){
		if($("#client_name").val()==""){
			$("#client_name").tips({
				side:3,
	            msg:'输入客户名称',
	            bg:'#AE81FF',
	            time:3
	        });
			$("#client_name").focus();
			return false;
		}
		if($("#client_number").val()==""){
			$("#client_number").tips({
				side:3,
	            msg:'请输入客户编号',
	            bg:'#AE81FF',
	            time:3
	        });
			$("#client_number").focus();
			return false;
		}
		if($("#client_linkman").val()==""){
			$("#client_linkman").tips({
				side:3,
	            msg:'请输入客户联系人姓名',
	            bg:'#AE81FF',
	            time:3
	        });
			$("#client_linkman").focus();
			return false;
		}

		var myreg = /^(((13[0-9]{1})|159)+\d{8})$/;
		if($("#client_phone").val()==""){
			
			$("#client_phone").tips({
				side:3,
	            msg:'输入手机号',
	            bg:'#AE81FF',
	            time:3
	        });
			$("#client_phone").focus();
			return false;
		}else if($("#client_phone").val().length != 11 && !myreg.test($("#client_phone").val())){
			$("#client_phone").tips({
				side:3,
	            msg:'手机号格式不正确',
	            bg:'#AE81FF',
	            time:3
	        });
			$("#client_phone").focus();
			return false;
		}
		if($("#client_emil").val()==""){
			
			$("#client_emil").tips({
				side:3,
	            msg:'输入邮箱',
	            bg:'#AE81FF',
	            time:3
	        });
			$("#client_emil").focus();
			return false;
		}else if(!ismail($("#client_emil").val())){
			$("#client_emil").tips({
				side:3,
	            msg:'邮箱格式不正确',
	            bg:'#AE81FF',
	            time:3
	        });
			$("#client_emil").focus();
			return false;
		}
        if($("#client_address").val()==""){
            $("#client_address").tips({
                side:3,
                msg:'请输入地址',
                bg:'#AE81FF',
                time:3
            });
            $("#client_address").focus();
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