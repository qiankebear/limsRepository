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

					<form action="projectmanager/${msg }.do" name="Form" id="Form" method="post">
						<%--<input type="hidden" name="id" id="id" value="${pd1.id}"/>--%>
						<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">产品名称:</td>
								<td>
									<select class="chosen-select form-control" name="sl" id="sl" >
										<option value="">请选择</option>
										<c:forEach items="${pd1}" var="pd1">
											<option value="${pd1.kit_id}">${pd1.kit_name}</option>
										</c:forEach>
									</select> </td>
							</tr>

							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">数量:</td>
								<td><input type="number" name="change_count" id="change_count" value="" maxlength="11" placeholder="这里输入数量" title="调整数量" style="width:98%;"/></td>
								<td style="display: none"><input type="number" name="id"  id="id" value="${pd2.id}"></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">当前库存:</td>
								<td style="display: none">
									<select  class="chosen-select form-control" name="sl1" id="sl1" data-placeholder="选择产品后显示当前库存">
									<option value=""></option>
									<c:forEach items="${pd1}" var="pd1">
										<option value="${pd1.kit_id}">${pd1.kit_num}</option>
									</c:forEach>
								</select>
								</td>
								<td><input readonly="readonly" type="number" name="kit_num" id="kit_num" value="" maxlength="32" placeholder="" title="库存" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">出库原因:</td>
								<td><input type="text" name="outcurse" id="outcurse" value="" maxlength="11" placeholder="这里输入原因(非必录)" title="出库原因" style="width:98%;"/></td>
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

            $("#sl").change(function () {
				var sl = document.getElementById("sl");
                var sl1 = document.getElementById("sl1");
                var kit_num = document.getElementById("kit_num");
				var value = sl.value;
				sl1.value=value;
				var index = sl1.selectedIndex;
				var selectedIndex = sl1[index].text;
				kit_num.value= selectedIndex;
            });

		$(top.hangge());
        $(function() {
            $("sl1").hide();
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
        //保存
        function save(){

            if ($("#kit_num").val() == '') {
                $("#sl_chosen").tips({
                    side: 3,
                    msg: '请选择产品',
                    bg: '#AE81FF',
                    time: 2
                });
                $("#sl").focus();
                return false;
            }
            if ($("#change_count").val() == '') {
                $("#change_count").tips({
                    side: 3,
                    msg: '请输入数量',
                    bg: '#AE81FF',
                    time: 2
                });
                $("#change_count").focus();
                return false;
            }
            if (parseInt($("#change_count").val()) > parseInt($("#kit_num").val())) {
                $("#change_count").tips({
                    side: 3,
                    msg: '出库数量不能大于现有库存数量',
                    bg: '#AE81FF',
                    time: 2
                });
                $("#change_count").focus();
                return false;
            }
			if (!/^(?!(0[0-9]{0,}$))[0-9]{1,}[.]{0,}[0-9]{0,}$/.test($("#change_count").val())) {
				$("#change_count").tips({
					side: 3,
					msg: '请输入大于0的数量',
					bg: '#e85cff',
					time: 2
				});
				$("#change_count").focus();
				return false;
			}
			var xsd=($("#change_count").val().split("."));
			if (!(xsd[1]==undefined)) {
				if (xsd[1].length > 1) {
					$("#change_count").tips({
						side: 3,
						msg: '不能大于一位小数',
						bg: '#e85cff',
						time: 2
					});
					$("#change_count").focus();
					return false;
				}
			}
            $("#Form").submit();
            $("#zhongxin").hide();
            $("#zhongxin2").show();
        }
		</script>
</body>
</html>