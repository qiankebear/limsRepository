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
	<script src="static/ace/js/jquery-1.11.0.min.js"></script>
	<link rel='stylesheet' text="type/css" href="static/ace/css/input.css">
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
						<form style="padding-top: 20px" action="confirmation/goExp.do"  method="post" name="Form" id="Form">
							<input style="display: none;" type="hidden" name="checked" id="checked" value="" />
							<table id="table_report"  style="width: 40%;margin:0 auto" class="table table-striped table-bordered table-hover">
								<tr>
									<h1 style="text-align: center;color: #438eb9">导出确认函</h1>
								</tr>
								<tr>
									<td style="width:75px;text-align: right;padding-top: 13px;">当前项目:</td>
									<td>
										<select style="width: 100%" class="chosen-select form-control" id="project" name="project">
											<option value="">请选择项目</option>
											<c:forEach items="${endProject}" var="ep">
											<option value='${ep.id}' <c:if test="${ep.id == pd.id}">selected</c:if>>${ep.project_name}</option>
											</c:forEach>
										</select>
										</td>
								</tr>
								<tr>
									<td style="width:75px;text-align: right;padding-top: 13px;">请选择:</td>
									<td class="center">
										<p>

											<lable>
												<i class='input_style checkbox_bg'><input type="checkbox" id="yssj" value="1"></i>
												原始数据
											</lable>
											<lable>
												<i class='input_style checkbox_bg'><input type="checkbox" id="Project1" value="2"></i>
												Project
											</lable>
											<lable>
												<i class='input_style checkbox_bg'><input type="checkbox" id="CODIS" value="3"></i>
												CODIS
											</lable>
											<lable>
												<i class='input_style checkbox_bg'><input type="checkbox" id="zzbb" value="4"></i>
												纸质板标
											</lable>
											<lable>
												<i class='input_style checkbox_bg'><input type="checkbox" id="hzb" value="5"></i>
												汇总表
											</lable>
											<lable>
												<i class='input_style checkbox_bg'><input type="checkbox" id="fhk" value="6"></i>
												复核孔
											</lable>
										</p>
									</td>
								</tr>

							</table>
							<div class="page-header position-relative">
								<table style="width:100%;">
									<tr>
										<td style="vertical-align:top; " class="center">
												<a class="btn btn-xs btn-purple" onclick="exp();">导出</a>

										</td>
									</tr>
								</table>
							</div>
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
<script text="type/javascript" src="static/js/input.js"></script>
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
                if (event_name !== 'sidebar_collapsed') return;
                $('.chosen-select').each(function () {
                    var $this = $(this);
                    $this.next().css({'width': $this.parent().width()});
                });
            });
            $('#chosen-multiple-style .btn').on('click', function (e) {
                var target = $(this).find('input[type=radio]');
                var which = parseInt(target.val());
                if (which === 2) $('#form-field-select-4').addClass('tag-input-style');
                else $('#form-field-select-4').removeClass('tag-input-style');
            });
        }

    });
    //获取选中的 checkbox的值
	$(".checkbox_bg").on("click",function () {
        var arr = [];
        $("input[type='checkbox']:checked").each(function (index, item) {//
            arr.push($(this).val());
        });
        document.getElementById("checked").value = arr;

    });

    function exp() {
        var projectId = $('#project option:selected') .val();//选中的值
        if (projectId === "") {
            $("#project_chosen").tips({
                side: 3,
                msg: '请选择项目',
                bg: '#AE81FF',
                time: 2
            });
            $("#project").focus();
            return false;
        }
		var checked = $("#checked").val();
        window.location.href = '<%=basePath%>confirmation/exp.do?id='+projectId+'&checked='+checked;
    }
</script>
</body>
</html>