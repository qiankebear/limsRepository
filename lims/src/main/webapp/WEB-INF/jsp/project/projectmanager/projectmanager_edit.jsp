<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="width" uri="http://www.springframework.org/tags/form" %>
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
						<input type="hidden" name="id" id="id" value="${pd.id}"/>
						<input style="display: none;" type="hidden" name="TESTER" id="TESTER" value="" />
						<input style="display: none;" type="hidden" name="VISITOR" id="VISITOR" value="" />
						<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">项目编号:</td>
								<td><input type="text" name="PROJECT_NUMBER" id="PROJECT_NUMBER" value="${pd.project_number}" onblur="hasNumber()" maxlength="255" placeholder="这里输入项目编号" title="项目编号" style="width:100%;height: 29px"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">项目名称:</td>
								<td><input type="text" name="PROJECT_NAME" id="PROJECT_NAME" value="${pd.project_name}" onblur="hasName()" onkeyup="checkLength(this, 100);" maxlength="255" placeholder="这里输入项目名称" title="项目名称" style="width:100%;height: 29px"/></td>
							</tr>
							<tr>
								<td style="width:75px;height: 29px;text-align: right;padding-top: 13px;">客户:</td>
								<td>
									<select style="height: 31px;width: 100%" class="chosen-select form-control" id="client" name="client">
										<option value="">请选择</option>
										<c:forEach items="${customerList}" var="customer">
											<option value="${customer.id }" <c:if test="${customer.id == pd.project_client}">selected</c:if>>${customer.client_name}</option>
										</c:forEach>
									</select> </td>
							</tr>

							<tr>
								<td style="width:80px;text-align: right;padding-top: 13px;">项目负责人:</td>
								<td>
									<select style="height: 29px" class="chosen-select form-control" id="sl" name="sl">
										<option value="">请选择</option>
										<c:forEach items="${varList}" var="user">
											<option value="${user.USER_ID }" <c:if test="${user.USER_ID == admin.user_id}">selected</c:if>>${user.NAME}</option>
										</c:forEach>
									</select> </td>
							</tr>


							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">实验员:</td>
								<%--<td><input  type="text" name="member_kind1" id="member_kind1" value="${pd.member_kind1}" maxlength="255" placeholder="请选择实验员" title="实验员" />--%>
								<td class="containerOptionss">
									<div>
									<select multiple class="form-control form-control-chosen containerPtion " id="sl1" name="sl1" data-placeholder="选择实验员">

										<c:forEach items="${varList}" var="user">
											<option data-id="${user.USER_ID }" value="${user.USER_ID}" <c:if test="${msg=='edit'}"><c:if test="${user.verify == '1'}">selected</c:if></c:if>>${user.NAME }</option>
										</c:forEach>

									</select>
									</div>
									</td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">访客:</td>
								<td class="containerOptions">
									<div>
									<select class="form-control form-control-chosen containerPtions" id="sl2" name="sl2" multiple data-placeholder="选择访客">
										<c:forEach items="${varList}" var="user">
											<option data-id="${user.USER_ID }" value="${user.USER_ID}" <c:if test="${msg=='edit'}"><c:if test="${user.verify1 == '2'}">selected</c:if></c:if>>${user.NAME }</option>
										</c:forEach>
									</select>
									</div>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">项目缩写:</td>
								<td><input type="text" name="PROJECT_NUMBER_ABBREVIATION" id="PROJECT_NUMBER_ABBREVIATION" value="${pd.project_number_abbreviation}" maxlength="255" placeholder="这里输入项目缩写" title="项目缩写" style="width:100%;height: 29px"/></td>
							</tr>
<%--							<c:if test="${msg=='edit'}">--%>
<%--							<tr>--%>
<%--								<td style="width:75px;text-align: right;padding-top: 13px;">项目状态:</td>--%>
<%--								<td>--%>
<%--									<select class="chosen-select form-control" name="PROJECT_STATUS" id="PROJECT_STATUS">--%>
<%--									<option value ="">请选择</option>--%>
<%--									<option value ="1" <c:if test="${pd.project_status=='1'}">selected</c:if>>准备中</option>--%>
<%--									<option value ="2" <c:if test="${pd.project_status=='2'}">selected</c:if>>进行中</option>--%>
<%--									<option value ="4" <c:if test="${pd.project_status=='4'}">selected</c:if>>其他</option>--%>
<%--								</select></td>--%>
<%--							</tr>--%>
<%--							</c:if>--%>
							<tr>
								<td style="width:95px;text-align: right;padding-top: 13px;">复核孔个数:</td>
								<td><input type="number" name="RECHECK_HOLE_AMOUNT" id="RECHECK_HOLE_AMOUNT" value="${pd.recheck_hole_amount}" maxlength="10" placeholder="这里输入复核孔个数" title="复核孔个数" style="width:100%;height: 29px"/></td>
							</tr>
							<tr>
								<td style="text-align: center;" colspan="10">
									<a class="btn btn-mini btn-primary" onclick="emp();save()">保存</a>
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
	<script src="static/ace/js/chosen.jquery1.js"></script>
	<!-- 日期框 -->
	<script src="static/ace/js/date-time/bootstrap-datepicker.js"></script>
	<!--提示框-->
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
	<%--<script type="text/javascript" src="static/ace/js/jquery-1.11.0.min.js"></script>--%>

		<script type="text/javascript">

            $("input").change(function(){
                $(this).val($(this).val().trim());
            });

            String.prototype.trim=function(){
                return this.replace(/(^\s*)|(\s*$)/g, "");
            }
            $('.form-control-chosen').chosen({
                allow_single_deselect: true,
                width: '100% !important',
                height: '29px'
            });

            var option = [];
            var options = [];
            var choice = [];
            var choices = [];
            for(var i1 = 0 ;i1<$('.containerPtion option').length;i1++){
                var optionss ={};
                optionss.name = $('.containerPtion option').eq(i1).html();
                optionss.id = $('.containerPtion option').eq(i1).attr('data-id');
                option.push(optionss)
            }
            for(var i2 = 0 ;i2<$('.containerPtions option').length;i2++){
                var optionss ={};
                optionss.name = $('.containerPtions option').eq(i2).html();
                optionss.id = $('.containerPtions option').eq(i2).attr('data-id');
                options.push(optionss)
            }

            $('.containerOptionss').on('click','.chosen-drop',function(){
                var searchChoice = $(this).parent().find('.search-choice span')
                for(var j = 0 ;j<searchChoice.length;j++){
                    for(var k = 0 ;k<option.length;k++){
                        if(searchChoice.eq(j).html()==option[k].name){
                            searchChoice.parent().eq(j).attr("data-id",option[k].id)
                        }
                    }
                }
            })
            $('.containerOptions').on('click','.chosen-drop',function(){
                var searchChoice = $(this).parent().find('.search-choice span')
                for(var j = 0 ;j<searchChoice.length;j++){
                    for(var k = 0 ;k<options.length;k++){
                        if(searchChoice.eq(j).html()==options[k].name){
                            searchChoice.parent().eq(j).attr("data-id",options[k].id)
                        }
                    }
                }
            })
            function emp() {
                choice=[];
                var searchChoice = $('.containerOptionss').find('.search-choice span');
                for(var j = 0 ;j<searchChoice.length;j++){
                    for(var k = 0 ;k<option.length;k++){
                        if(searchChoice.eq(j).html()==option[k].name){
                            choice.push(option[k].id)
                        }
                    }
                }
                $.unique(choice);
                // 第一个的值
                document.getElementById("TESTER").value = choice;

                choices=[]
                var searchChoice = $('.containerOptions').find('.search-choice span')
                for(var j = 0 ;j<searchChoice.length;j++){
                    for(var k = 0 ;k<options.length;k++){
                        if(searchChoice.eq(j).html()==options[k].name){
                            choices.push(options[k].id)
                        }
                    }
                }
                $.unique(choices);
                // 第二个的值
                document.getElementById("VISITOR").value = choices;
            }

            $(top.hangge());

        // 自动加载的方法
        $(function () {
            //日期框
            $('.date-picker').datepicker({autoclose: true,todayHighlight: true});
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
                    alert(target);
                    var which = parseInt(target.val());
                    if(which == 2) $('#form-field-select-4').addClass('tag-input-style');
                    else $('#form-field-select-4').removeClass('tag-input-style');
                });
            }
        });
            $(document).ready(function(){
                $('#sl1').addClass('tag-input-style');
                if($("#user_id").val()!=""){
                    $("#loginname").attr("readonly","readonly");
                    $("#loginname").css("color","gray");
                }
            });
            $(document).ready(function(){
                $('#sl2').addClass('tag-input-style');
                if($("#user_id").val()!=""){
                    $("#loginname").attr("readonly","readonly");
                    $("#loginname").css("color","gray");
                }
            });
            //项目名称汉字不能超过50个
            var checkLength = function(dom, maxLength){
                var l = 0;
                for(var i=0; i<dom.value.length; i++) {
                    if (/[\u4e00-\u9fa5]/.test(dom.value[i])) {
                        l+=2;
                    } else {
                        l++;
                    }
                    if (l > maxLength) {
                        dom.value = dom.value.substr(0,i);
                        break;
                    }
                }
            };
            //保存
            function save() {
				if ($("#PROJECT_NUMBER").val() == "") {
					$("#PROJECT_NUMBER").tips({
						side: 3,
						msg: '请输入项目编号',
						bg: '#AE81FF',
						time: 2
					});
					$("#PROJECT_NUMBER").focus();
					return false;
				}
				var myReg =/^[a-zA-Z0-9_]{0,}$/;
				// if ((myReg.test($("#PROJECT_NUMBER").val()))) {
				// 	$("#PROJECT_NUMBER").tips({
				// 		side: 3,
				// 		msg: '项目编号不能为中文',
				// 		bg: '#AE81FF',
				// 		time: 2
				// 	});
				// 	$("#PROJECT_NUMBER").focus();
				// 	return false;
				// }

				if (!myReg.test($("PROJECT_NUMBER").val())) {
					$("#PROJECT_NUMBER").tips({
						side: 3,
						msg: '请输入字母与数字组合',
						bg: '#AE81FF',
						time: 2
					});
					$("#PROJECT_NUMBER").focus();
					return false;
				}

				if ($("#PROJECT_NAME").val() == "") {
					$("#PROJECT_NAME").tips({
						side: 3,
						msg: '请输入项目名称',
						bg: '#AE81FF',
						time: 2
					});
					$("#PROJECT_NAME").focus();
					return false;
				}

                var myReg = /[\u4E00-\u9FA5\uF900-\uFA2D]/;
                if ($("#client").val() == "") {
                    $("#client_chosen").tips({
                        side: 3,
                        msg: '请选择客户',
                        bg: '#AE81FF',
                        time: 2
                    });
                    $("#client").focus();
                    return false;
                }
                if ($("#sl").val() == "") {
                    $("#sl_chosen").tips({
                        side: 3,
                        msg: '请选择项目负责人',
                        bg: '#AE81FF',
                        time: 2
                    });
                    $("#sl").focus();
                    return false;
                }
				<c:if test="${msg=='save'}">
				if ($("#TESTER").val() == "") {
					$("#sl1_chosen").tips({
						side: 3,
						msg: '请选择实验员',
						bg: '#AE81FF',
						time: 2
					});
					$("#sl1").focus();
					return false;
				}
				</c:if>

				<c:if test="${msg=='edit'}">
				if ($("#sl1").val() == "") {
					$("#sl1_chosen").tips({
						side: 3,
						msg: '请选择实验员',
						bg: '#AE81FF',
						time: 2
					});
					$("#sl1").focus();
					return false;
				}
				if ($("#TESTER").val() == "") {
					$("#sl1_chosen").tips({
						side: 3,
						msg: '请选择实验员',
						bg: '#AE81FF',
						time: 2
					});
					$("#sl1").focus();
					return false;
				}
				</c:if>
				<c:if test="${msg=='edit'}">
				if ($("#sl2").val() == "") {
					$("#sl2_chosen").tips({
						side: 3,
						msg: '请选择访客',
						bg: '#AE81FF',
						time: 2
					});
					$("#sl2").focus();
					return false;
				}
				if ($("#VISITOR").val() == "") {
					$("#sl2_chosen").tips({
						side: 3,
						msg: '请选择访客',
						bg: '#AE81FF',
						time: 2
					});
					$("#sl2").focus();
					return false;
				}
				</c:if>
				<c:if test="${msg=='save'}">
					if ($("#VISITOR").val() == "") {
						$("#sl2_chosen").tips({
							side: 3,
							msg: '请选择访客',
							bg: '#AE81FF',
							time: 2
						});
						$("#sl2").focus();
						return false;
					}
				</c:if>
				if ($("#PROJECT_NUMBER_ABBREVIATION").val() == "") {
					$("#PROJECT_NUMBER_ABBREVIATION").tips({
						side: 3,
						msg: '请输入项目缩写',
						bg: '#AE81FF',
						time: 2
					});
					$("#PROJECT_NUMBER_ABBREVIATION").focus();
					return false;
				}
				if ($("#RECHECK_HOLE_AMOUNT").val() == "") {
					$("#RECHECK_HOLE_AMOUNT").tips({
						side: 3,
						msg: '请输入复核孔个数',
						bg: '#AE81FF',
						time: 2
					});
					$("#RECHECK_HOLE_AMOUNT").focus();
					return false;
				}
				if (!/^\d+(\.\d*)?$|^\.\d+$/.test($("#RECHECK_HOLE_AMOUNT").val())) {
					$("#RECHECK_HOLE_AMOUNT").tips({
						side: 3,
						msg: '请输入整数',
						bg: '#AE81FF',
						time: 2
					});
					$("#RECHECK_HOLE_AMOUNT").focus();
					return false;
				}
                    if (parseInt($("#RECHECK_HOLE_AMOUNT").val()) > 5) {
                        $("#RECHECK_HOLE_AMOUNT").tips({
                            side: 3,
                            msg: '请输入1-5个复核孔个数',
                            bg: '#AE81FF',
                            time: 2
                        });
                        $("#RECHECK_HOLE_AMOUNT").focus();
                        return false;
                    }
                if (parseInt($("#RECHECK_HOLE_AMOUNT").val()) < 1) {
                    $("#RECHECK_HOLE_AMOUNT").tips({
                        side: 3,
                        msg: '请输入1-5个复核孔个数',
                        bg: '#AE81FF',
                        time: 2
                    });
                    $("#RECHECK_HOLE_AMOUNT").focus();
                    return false;
                }

                // if ($("#PROJECT_STATUS").val() == "") {
                //     $("#PROJECT_STATUS_chosen").tips({
                //         side: 3,
                //         msg: '请选择项目状态',
                //         bg: '#AE81FF',
                //         time: 2
                //     });
                //     $("#PROJECT_STATUS_chosen").focus();
                //     return false;
                // }


                $("#Form").submit();
                $("#zhongxin").hide();
                $("#zhongxin2").show();
			}

            //判断编号是否存在
            function hasNumber(){
                var NUMBER = $.trim($("#PROJECT_NUMBER").val());
                var NUMBER1 = "${pd.project_number}";
					$.ajax({
						type: "POST",
						url: '<%=basePath%>projectmanager/hasNumber.do',
						data: {NUMBER: NUMBER,NUMBER1: NUMBER1,tm: new Date().getTime()},
						dataType: 'json',
						cache: false,
						success: function (data) {
							if ("success" != data.result) {
								$("#PROJECT_NUMBER").tips({
									side: 3,
									msg: '编号 ' + NUMBER + ' 已存在',
									bg: '#AE81FF',
									time: 3
								});
								$("#PROJECT_NUMBER").val('');
							}
						}
					});
            }
            //判断项目名称是否存在
            function hasName(){
                var NAME = $.trim($("#PROJECT_NAME").val());
				var NAME1 = "${pd.project_name}";
                $.ajax({
                    type: "POST",
                    url: '<%=basePath%>projectmanager/hasName.do',
                    data: {NAME:NAME,NAME1:NAME1,tm:new Date().getTime()},
                    dataType:'json',
                    cache: false,
                    success: function(data){
                        if("success" != data.result){
                            $("#PROJECT_NAME").tips({
                                side:3,
                                msg:'名称 '+NAME+' 已存在',
                                bg:'#AE81FF',
                                time:3
                            });
                            $("#PROJECT_NAME").val('');
                        }
                    }
                });
            }
		</script>
</body>
</html>