<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
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
								<form action="porePlate/${msg }.do" name="userForm" id="userForm" method="post">
                                    <input  type="hidden" name="pore_plate_type" id="pore_plate_type" value="${pd.pore_plate_type}"/>
                                    <input  type="hidden" name="poreId" id="poreId" value="${pd.id}"/>
                                    <input  type="hidden" name="keyWord" id="keyWord" value="${pd.keyWord}"/>
                                    <input  type="hidden" name="current_procedureTest" id="current_procedureTest" value="${pd.current_procedureTest}"/>
                                    <input  type="hidden" name="msg" id="msg" value="${msg }"/>
									<textarea style="display: none;" name="ROLE_IDS" id="ROLE_IDS" >${pcrRecord.instrument_info }</textarea>
									<c:if test="${msg == 'editPorePlate'}">
                                    <input  type="hidden" name="plate_project_id" id="plate_project_id" value="${pd.plate_project_id}"/>
									</c:if>
									<div id="zhongxin" style="padding-top: 13px;">
									<table id="table_report" class="table table-striped table-bordered table-hover">

										<tr>
											<td style="width:85px;text-align: right;padding-top: 13px;">选择仪器:</td>
											<td>
											<div>
												<select multiple="" class="chosen-select" id="form-field-select-4" data-placeholder="选择仪器">
													<option value=""></option>
													<c:forEach items="${instrumentList}" var="instrument">
														<option value="${instrument.id }" <c:if test="${instrument.right == '1' }">selected</c:if> >${instrument.instrument_number }(${instrument.instrument_type})</option>
													</c:forEach>
												</select>
											</div>
											</td>
										</tr>

										<tr>
											<td style="width:85px;text-align: right;padding-top: 13px;">上机时间:</td>
											<td>
												<div class="input-group bootstrap-timepicker">
													<input readonly="readonly" class="form-control" type="text" name="operation_time" id="operation_time" value="${fn:substring(pcrRecord.operation_time, 0, 16)}" maxlength="100" placeholder="这里输入上机时间" title="上机时间" style="width:100%;"/>
													<span class="input-group-addon"><i class="fa fa-clock-o bigger-110"></i></span>
												</div>
											</td>
										</tr>
										<tr>
											<td style="width:85px;text-align: right;padding-top: 13px;">下机时间:</td>
											<td>
												<div class="input-group bootstrap-timepicker">
													<input readonly="readonly" class="form-control" type="text" name="deplane_time" id="deplane_time" value="${fn:substring(pcrRecord.deplane_time, 0, 16)}" maxlength="100" placeholder="这里输入下机时间" title="下机时间" style="width:100%;"/>
													<span class="input-group-addon"><i class="fa fa-clock-o bigger-110"></i></span>
												</div>
											</td>
										</tr>
										<tr>
											<td style="text-align: center;" colspan="10">
												<%--<a class="btn btn-mini btn-primary" onclick="save();">保存</a>--%>
												<a class="btn btn-mini btn-primary" onclick="save();">确认</a>
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
	<!-- 日期框(带小时分钟) -->
	<script src="static/ace/js/date-time/moment.js"></script>
	<script src="static/ace/js/date-time/locales.js"></script>
	<script src="static/ace/js/date-time/bootstrap-datetimepicker.js"></script>
</body>
<script type="text/javascript">

	$(top.hangge());
    $(document).ready(function(){
        $('#form-field-select-4').addClass('tag-input-style');

    });
    $(function() {
        //日期框(带时间)
        $('.form-control').datetimepicker().next().on(ace.click_event, function(){
            $(this).prev().focus();
        });
    });
    // 自动加载的方法
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
        ROLE_IDS = ROLE_IDS.replace(ROLE_ID+",","");
        OROLE_IDS.val(ROLE_IDS);
    }
    //添加副职角色
    function addRoleId(ROLE_ID){
        var OROLE_IDS = $("#ROLE_IDS");
        var ROLE_IDS = OROLE_IDS.val();
        if(!isContains(ROLE_IDS,ROLE_ID)){
            ROLE_IDS = ROLE_IDS + ROLE_ID + ",";
            OROLE_IDS.val(ROLE_IDS);
        }
    }
    function isContains(str, substr) {
        return str.indexOf(substr) >= 0;
    }



	//保存
	function save(){
		if($("#form-field-select-4").val()==""){
			$("#form-field-select-4").tips({
				side:3,
	            msg:'请选择仪器',
	            bg:'#AE81FF',
	            time:3
	        });
			$("#form-field-select-4").focus();
			return false;
		}

        if($("#operation_time").val()==""){
            $("#operation_time").tips({
                side:3,
                msg:'请输入上机时间',
                bg:'#AE81FF',
                time:3
            });
            $("#operation_time").focus();
            return false;
        }

        if($("#deplane_time").val()==""){
            $("#deplane_time").tips({
                side:3,
                msg:'请输入下机时间',
                bg:'#AE81FF',
                time:3
            });
            $("#deplane_time").focus();
            return false;
        }
       var time =  checkEndTime($("#operation_time").val(),$("#deplane_time").val());
		if(!time){
            $("#deplane_time").tips({
                side:3,
                msg:'下机时间不能小于上机时间',
                bg:'#AE81FF',
                time:3
            });
            $("#deplane_time").focus();
            return false;
		}

		var pcr_time = getTime($("#operation_time").val(),$("#deplane_time").val());
		var keyword = $("#keyWord").val();
		var current_procedure = 2;
        $.ajax({
            type : "POST",
            cache: false,
            url : '<%=basePath%>porePlate/completeOther',
            data: {pcr_time:pcr_time,id:$("#poreId").val(),current_procedure:3,instrument_info:$("#ROLE_IDS").val(),operation_time:$("#operation_time").val(),deplane_time:$("#deplane_time").val()},
            dataType : "json",
            success: function () {
                siMenu('z150','lm144','扩增待办','punching/list.do?keywords='+keyword+'&current_procedure='+current_procedure);
                top.Dialog.close();

            }
        });
        $.ajax({
            type : "POST",
            cache: false,
            url : '<%=basePath%>porePlate/saveInstrumentRecord',
            data: {pcr_time:pcr_time,id:$("#poreId").val(),current_procedure:3,instrument_info:$("#ROLE_IDS").val(),operation_time:$("#operation_time").val(),deplane_time:$("#deplane_time").val()},
            dataType : "json",
            success: function () {
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

    function checkEndTime(startTime,endTime){
            var start=new Date(startTime.replace("-", "/").replace("-", "/"));
            var end=new Date(endTime.replace("-", "/").replace("-", "/"));
            if(end<start){
                return false;
            }
            return true;
    }

    function getTime(startTime,endTime) {

        var time1 = Date.parse(startTime);

        var time2 = Date.parse(endTime);

        var time3=time2-time1;

        var hour=Math.floor(time3/1000/60/60);

        var minute=Math.floor(time3/1000/60-hour*60);

        var second=time3/1000-hour*60*60-minute*60;

        return hour+"小时"+minute+"分"+second+"秒";
    }

</script>
</html>