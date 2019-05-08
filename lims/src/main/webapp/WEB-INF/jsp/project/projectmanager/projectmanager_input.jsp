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

					<form action="projectmanager/inPut.do" name="Form" id="Form" method="post">
						<%--<input type="hidden" name="id" id="id" value="${pd1.id}"/>--%>
						<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">产品分类:</td>
								<td>
									<select class="chosen-select form-control" name="sl1" id="sl1">
										<option value="">请选择产品分类</option>
										<option value="1">试剂盒</option>
										<option value="2">其余试剂和耗材</option>
									</select> </td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">产品名称:</td>
								<td>
									<select class="form-control" name="sl2" id="sl2" >
										<option value="">请选择产品</option>
										<%--<c:forEach items="${pd1}" var="pd1">--%>
											<%--<option value="${pd1.id}">${pd1.kit_name}</option>--%>
										<%--</c:forEach>--%>
									</select> </td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">数量:</td>
								<td><input type="number" name="change_count" id="change_count" value="" maxlength="11" placeholder="这里输入数量" title="调整数量" style="width:98%;"/></td>
								<td style="display: none"><input type="number" name="id"  id="id" value="${pd2.pid}"></td>
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
            $('#sl1').change(function(){
                var sl1 = $("#sl1").val();
                var url='<%=basePath%>projectmanager/listKits.do?type='+sl1;
                $.ajax({
                    url: url,
                    type:"post",
                    dataType: "json",
                    success:function (data){
                        var ddl = $("#sl2");
                        $("#sl2").empty();
                        //转成Json对象
                        var result = eval(data.pd1);
                        //循环遍历 下拉框绑定
                        $(result).each(function (key) {
                            // var opt = $("<option></option>").text(result[key].kit_name).val(result[key].id);
							var opt ="<option value='"+result[key].id+"'>"+result[key].kit_name+"</option>";
                            // alert(opt.val());
                            ddl.append(opt);
                        });
                    }
                })
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
        function save() {
            if ($("#sl1").val() == '') {
					$("#sl1").tips({
						side: 3,
						msg: '请选择分类',
						bg: '#ff51db',
						time: 2
					});
					$("#sl1").focus();
					return false;
            }


            if ($("#sl2").val() == '') {
                $("#sl2").tips({
                    side: 3,
                    msg: '请选择产品',
                    bg: '#ed1cff',
                    time: 2
                });
                $("#sl2").focus();
                return false;
            }
            if ($("#sl2").val() == null) {
                $("#sl2").tips({
                    side: 3,
                    msg: '请选择产品',
                    bg: '#ff50d4',
                    time: 2
                });
                $("#sl2").focus();
                return false;
            }
            if ($("#change_count").val() == '') {
                $("#change_count").tips({
                    side: 3,
                    msg: '请输入数量',
                    bg: '#ff1213',
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
				if ((xsd[1].length>1)) {
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