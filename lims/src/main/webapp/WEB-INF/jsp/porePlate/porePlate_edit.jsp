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
								<form action="porePlate/${msg }.do" name="userForm" id="userForm" method="post">
                                    <input  type="hidden" name="pore_plate_type" id="pore_plate_type" value="${pd.pore_plate_type}"/>
                                    <input  type="hidden" name="id" id="id" value="${pd.id}"/>
                                    <input  type="hidden" name="msg" id="msg" value="${msg }"/>
									<c:if test="${msg == 'editPorePlate'}">
                                    <input  type="hidden" name="plate_project_id" id="plate_project_id" value="${pd.plate_project_id}"/>
									</c:if>
									<div id="zhongxin" style="padding-top: 13px;">
									<table id="table_report" class="table table-striped table-bordered table-hover">
                                     <c:if test="${msg != 'editPorePlate'}">
										<tr>
											<td style="width:79px;text-align: right;padding-top: 13px;">关联项目:</td>
											<td id="juese">
												<select class="chosen-select form-control" onchange="setPorePlateName()" name="plate_project_id" id="plate_project_id" data-placeholder="请选择项目" style="vertical-align:top;" style="width:98%;" >
													<option value=""></option>
													<c:forEach items="${projectList}" var="project">
														<option value="${project.id }" <c:if test="${project.id == pd.plate_project_id }">selected</c:if>>${project.PROJECT_NAME }</option>
													</c:forEach>
												</select>
											</td>
										</tr>
                                     </c:if>
										<tr>
											<td style="width:79px;text-align: right;padding-top: 13px;">孔板名称:</td>
											<td><input type="text" name="pore_plate_name" id="pore_plate_name" value="${pd.pore_plate_name }" maxlength="32" placeholder="这里输入孔板名称" title="孔板名称" style="width:98%;"/></td>
										</tr>
										<tr>
											<td style="text-align: center;" colspan="10">
												<%--<a class="btn btn-mini btn-primary" onclick="save();">保存</a>--%>
												<a class="btn btn-mini btn-primary" onclick="goToLayout();">确认</a>
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
	<input type="hidden" id="totalResult" value="${pd.totalResult}">
	<!-- /.main-container -->
	<!-- basic scripts -->
	<!-- 页面底部js¨ -->
	<%@ include file="../system/index/foot.jsp"%>
	<script type="text/javascript" src="static/js/myjs/head.js"></script>
	<script type="text/javascript" src="static/js/function.js"></script>
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

	});
	//保存
	function save(){
		if($("#plate_project_id").val()==""){
			$("#plate_project_id").tips({
				side:3,
	            msg:'请选择关联项目',
	            bg:'#AE81FF',
	            time:3
	        });
			$("#plate_project_id").focus();
			return false;
		}

        if($("#pore_plate_name").val()==""){
            $("#pore_plate_name").tips({
                side:3,
                msg:'请输入孔板名称',
                bg:'#AE81FF',
                time:3
            });
            $("#pore_plate_name").focus();
            return false;
        }

			$("#userForm").submit();
			$("#zhongxin").hide();
			$("#zhongxin2").show();
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

	 function setPorePlateName() {
		 var projectid =  $("#plate_project_id option:selected").val();
         //获取序号总数
         var totalResult =  $("#totalResult").val();
         var mydate = new Date();
         var myDate = new Date;
         var year = myDate.getFullYear();//获取当前年
         var yue = myDate.getMonth()+1;//获取当前月
         var date = myDate.getDate();//获取当前日
         //获取当前时间
         var onedate = year+""+yue;
         //alert(projectName+"-"+onedate+"-"+(parseInt(totalResult)+1));
         $.ajax({
             type : "POST",
             cache: false,
             url : '<%=basePath%>porePlate/setPorePlateName',
             data: { id:projectid},
             dataType : "json",
             success: function (data) {
				//alert(data.project_number_abbreviation);
				$("#pore_plate_name").val(data.project_number_abbreviation+"-"+onedate+"-"+(parseInt(data.count)+1));
             }
         });

	    //自动生成 项目缩写 没用了
	   /* //获取到项目的名称
        var projectName =  $("#plate_project_id option:selected").text();
        var pinyin = "";
         for(var i=0;i<projectName.length;i++) {
             pinyin += getPinYinByName(projectName[i]).substr(0, 1);
         }
        //获取序号总数
        var totalResult =  $("#totalResult").val();
         var mydate = new Date();
         var myDate = new Date;
         var year = myDate.getFullYear();//获取当前年
         var yue = myDate.getMonth()+1;//获取当前月
         var date = myDate.getDate();//获取当前日
		//获取当前时间
         var onedate = year+""+yue;
         //alert(projectName+"-"+onedate+"-"+(parseInt(totalResult)+1));
		 $("#pore_plate_name").val(pinyin+"-"+onedate+"-"+(parseInt(totalResult)+1))*/
	 }


	 function goToLayout() {
         if($("#plate_project_id").val()==""){
             $("#plate_project_id").tips({
                 side:3,
                 msg:'请选择关联项目',
                 bg:'#AE81FF',
                 time:3
             });
             $("#plate_project_id").focus();
             return false;
         }

         if($("#pore_plate_name").val()==""){
             $("#pore_plate_name").tips({
                 side:3,
                 msg:'请输入孔板名称',
                 bg:'#AE81FF',
                 time:3
             });
             $("#pore_plate_name").focus();
             return false;
         }
         var project = $("#plate_project_id").val();
         var plate_name = $("#pore_plate_name").val();
         var pore_plate_type = $("#pore_plate_type").val();
         var id = $("#id").val();
         var msg = $("#msg").val();
         if(msg == "editPorePlate"){
             siMenu('z153','lm144','智能布板','porePlate/goToLayoutEdit.do?id='+id+'&pore_plate_type='+pore_plate_type+"&pore_plate_name="+plate_name+'&plate_project_id='+project);
		 }else{
             siMenu('z153','lm144','智能布板','porePlate/goToLayout.do?pore_plate_type='+pore_plate_type+'&plate_project_id='+project+"&pore_plate_name="+plate_name);
         }
         //window.location.href="<%=basePath%>porePlate/goToLayout";
         top.Dialog.close();
     }

    //删除
    function porePlateSumConfirm(userId,msg){
        bootbox.confirm("确定要删除["+msg+"]吗?", function(result) {
            if(result) {
                top.jzts();
                var url = "<%=basePath%>Customer/deleteCustomer.do?id="+userId+"&tm="+new Date().getTime();
                $.get(url,function(data){
                    nextPage(${page.currentPage});
                });
            };
        });
    }

</script>
</html>