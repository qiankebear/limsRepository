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
							<form action="rebuild/${msg }.do" name="userForm" id="userForm" method="post">
									<%--<input type="hidden" name="id" value="${pd.id}" id="id" />--%>
									<input type="hidden" name="mun" value="${pd.mun}" id="mun" />
									<input type="hidden" name="pore_plate_name" value="${pd.pore_plate_name}" id="pore_plate_name" />
									<input type="hidden" name="lims_pore_serial" value="${pd.lims_pore_serial}" id="lims_pore_serial" />
									<input type="hidden" name="ids" value="${pd.ids}" id="ids" />
									<input type="hidden" name="projectNumber" value="${pd.projectNumber}" id="projectNumber" />
									<div id="zhongxin" style="padding-top: 13px;">
									<table id="table_report" class="table table-striped table-bordered table-hover">
										<tr>
											<td style="width:79px;text-align: right;padding-top: 13px;">孔板名称:</td>
											<td><input type="text" name="REBUILDNAME" id="REBUILDNAME" style="width:98%;"/></td>
										</tr>
										<tr>
											<td style="text-align: center;" colspan="10">
												<a class="btn btn-mini btn-primary" onclick="save();">确认</a>
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
	<script type="text/javascript" src="static/js/myjs/head.js"></script>
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
    $(window).load(function(){
        // 重扩轮数
        var lims_pore_serial = $("#lims_pore_serial").val();
        // 孔板名称
        var pore_plate_name = $("#pore_plate_name").val();
        if (lims_pore_serial == "1"){
            $("#REBUILDNAME").val(pore_plate_name + "B");
		}
        if (lims_pore_serial == "2"){
            $("#REBUILDNAME").val(pore_plate_name + "C");
        }
        if (lims_pore_serial == "3"){
            $("#REBUILDNAME").val(pore_plate_name + "D");
        }
        if (lims_pore_serial == "4"){
            $("#REBUILDNAME").val(pore_plate_name + "E");
        }
        if (lims_pore_serial == "5"){
            $("#REBUILDNAME").val(pore_plate_name + "F");
        }/*else {
            $("#REBUILDNAME").val(pore_plate_name);
		}*/
	});
	//保存
	function save(){
        top.jzts();
        if($("#REBUILDNAME").val()==""){
            $("#REBUILDNAME").tips({
                side:3,
                msg:'输入孔板名称',
                bg:'#AE81FF',
                time:3
            });
            $("#REBUILDNAME").focus();
            return false;
        }
        // 跳转页面、传递五个参数
		var mun = $("#mun").val(); // 重扩总数
        var REBUILDNAME = $("#REBUILDNAME").val(); // 重扩名称
        var ids = $("#ids").val(); //IDS
        var projectNumber = $("#projectNumber").val(); // 项目id
        var serial = $("#lims_pore_serial").val(); // 重扩轮数
        siMenu('z152','lm144','智能布板','rebuild/goToLayoutEdit.do?mun='+mun+'&REBUILDNAME='+REBUILDNAME+'&ids='+ids+'&projectID='+projectNumber+'&serial='+serial);
        top.Dialog.close();
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
	function isContains(str, substr) {
	     return str.indexOf(substr) >= 0;
	 }
</script>
</html>