<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
	<script type="text/javascript" src="static/js/myjs/head.js"></script>
<!-- jsp文件头和头部 -->
	<%@ include file="../system/index/top.jsp"%>
<!-- 日期框 -->
<link rel="stylesheet" href="static/ace/css/datepicker.css" />
<title>porePlate_list.jsp</title>
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
						<form action="porePlate/list.do" method="post" name="userForm" id="userForm">
						<table style="margin-top:20px;">
							<tr>
								<td>

										<select class="chosen-select form-control" name="project_name" id="project_name" data-placeholder="请选择项目名称"style="vertical-align:top;width:170px;" >
											<%--<option value="" ></option>--%>
											<option value="">请选择项目名称</option>
											<c:forEach var="list" items="${projectNameList}">
												<option value="${list.project_name}" <c:if test="${list.project_name == pd.project_name}">selected</c:if>>
														${list.project_name}</option>
											</c:forEach>
										</select>


								</td>
                                <td>
									<%--<span class="input-icon">
										<input class="nav-search-input" autocomplete="off" id="nav-search-input" type="text" name="project_name" value="${pd.project_name }" placeholder="请输入项目名称" />
										<i class="ace-icon fa fa-search nav-search-icon"></i>
									</span>--%>
										<div class="nav-search" style="margin-left: 20px;">
									<span class="input-icon">
										<input class="nav-search-input" autocomplete="off" id="nav-search-input" type="text" name="pore_plate_name" value="${pd.pore_plate_name }" placeholder="请输入孔板名称" />
										<i class="ace-icon fa fa-search nav-search-icon"></i>
									</span>
										</div>
								</td>
                                <td>&nbsp;
                                    <select name="current_procedure" id="current_procedure" style="margin-left: 20px;">
                                        <option value="" <c:if test="${pd.current_procedure == ''}">selected</c:if>>请选择步骤</option>
                                        <option value="0" <c:if test="${pd.current_procedure == '0'}">selected</c:if>>准备</option>
                                        <option value="1" <c:if test="${pd.current_procedure == '1'}">selected</c:if>>布板</option>
                                        <option value="2" <c:if test="${pd.current_procedure == '2'}">selected</c:if>>打孔</option>
                                        <option value="3" <c:if test="${pd.current_procedure == '3'}">selected</c:if>>扩增</option>
                                        <option value="4" <c:if test="${pd.current_procedure == '4'}">selected</c:if>>分析</option>
                                        <option value="5" <c:if test="${pd.current_procedure == '5'}">selected</c:if>>检测</option>
                                        <option value="6" <c:if test="${pd.current_procedure == '6'}">selected</c:if>>完成</option>
                                    </select>
                                </td>
                                <td>&nbsp;
                                    <select name="pore_plate_quality" id="pore_plate_quality" style="margin-left: 20px;">
                                        <option value="" <c:if test="${pd.pore_plate_quality == ''}">selected</c:if>>请选择质检状态</option>
                                        <option value="1" <c:if test="${pd.pore_plate_quality == '1'}">selected</c:if>>通过</option>
                                        <option value="2" <c:if test="${pd.pore_plate_quality == '2'}">selected</c:if>>不通过</option>
                                    </select>
                                </td>
								<c:if test="${QX.cha == 1 }">
								<td style="vertical-align:top;padding-left:10px;"><a class="btn btn-light btn-xs" onclick="searchs();"  title="检索"><i id="nav-search-icon" class="ace-icon fa fa-search bigger-110 nav-search-icon blue"></i></a></td>
                               <td style="vertical-align:top;padding-left:10px;"><a class="btn btn-light btn-xs" onclick="cleanForm();"  title="重置">重置<i id="nav-search-icon" class="ace-icon fa  bigger-110 nav-search-icon blue"></i></a></td>
                                </c:if>
							</tr>
						</table>
						<!-- 检索  -->

						<table id="simple-table" class="table table-striped table-bordered table-hover"  style="margin-top:10px;">
							<thead>
								<tr>
									<th class="center" style="width:35px;">
									<label class="pos-rel"><input type="checkbox" class="ace" id="zcheckbox" /><span class="lbl"></span></label>
									</th>
									<th class="center" style="width:50px;">序号</th>
									<th class="center">孔板名称</th>
									<th class="center">建板人</th>
									<th class="center">建板时间</th>
									<th class="center">步骤</th>
									<th class="center">孔板类型</th>
									<th class="center">质检状态</th>
									<th class="center">所属项目</th>
									<th class="center">样本个数</th>
									<th class="center">操作</th>
								</tr>
							</thead>

							<tbody>

							<!-- 开始循环 -->
							<c:choose>
								<c:when test="${not empty porePlateList}">
									<c:if test="${QX.cha == 1 }">
									<c:forEach items="${porePlateList}" var="porePlate" varStatus="vs">

										<tr>
											<td class='center' style="width: 30px;">
												<label><input type='checkbox' name='ids' value="${porePlate.id }" id="${porePlate.id }" alt="${porePlate.current_procedure }" title="${porePlate.pore_plate_quality}" class="ace"/><span class="lbl"></span></label>
											</td>
											<td class='center' style="width: 30px;">${page.showCount*(page.currentPage-1)+vs.index+1}</td>
<%--
											<td class="center"><a onclick="viewUser('${porePlate.id}')" style="cursor:pointer;"><i id="nav-search-icon" class="ace-icon fa fa-search bigger-110 nav-search-icon blue"></i>${porePlate.pore_plate_name }</a></td>
--%>
											<td><a onclick=" goToLayout('${porePlate.id}','${porePlate.pore_plate_type}','${porePlate.pore_plate_name}','${porePlate.pid}')" style="cursor:pointer;"><i id="nav-search-icon" class="ace-icon fa fa-search bigger-110 nav-search-icon blue"></i>${porePlate.pore_plate_name }</a></td>
											<td class="center">${porePlate.username}</td>
											<%--建板时间--%>
											<td class="center" id="formatTime" >${porePlate.plate_createtime}</td>
                                            <c:if test="${porePlate.current_procedure == 0}">
                                                <td class="center"  name="${porePlate.current_procedure}">准备</td>
                                            </c:if>
                                            <c:if test="${porePlate.current_procedure == 1}">
                                                <td class="center"  name="${porePlate.current_procedure}">布板</td>
                                            </c:if>
                                            <c:if test="${porePlate.current_procedure == 2}">
                                                <td class="center"  name="${porePlate.current_procedure}">打孔</td>
                                            </c:if>
                                            <c:if test="${porePlate.current_procedure == 3}">
                                                <td class="center"  name="${porePlate.current_procedure}">扩增</td>
                                            </c:if>
                                            <c:if test="${porePlate.current_procedure == 4}">
                                                <td class="center" name="${porePlate.current_procedure}">分析</td>
                                            </c:if>
											<c:if test="${porePlate.current_procedure == 5}">
                                                <td class="center"  name="${porePlate.current_procedure}">检测</td>
                                            </c:if>
                                            <c:if test="${porePlate.current_procedure == 6}">
                                                <td class="center"  name="${porePlate.current_procedure}">完成</td>
                                            </c:if>

                                           <c:if test="${porePlate.pore_plate_type == 1}">
                                               <td class="center">普通板</td>
                                           </c:if>
                                            <c:if test="${porePlate.pore_plate_type == 2}">
                                               <td class="center">质检板</td>
                                           </c:if>
                                            <c:if test="${porePlate.pore_plate_quality == 1}">
                                                <td class="center">通过</td>
                                            </c:if>
                                            <c:if test="${porePlate.pore_plate_quality == 2}">
                                                <td class="center">不通过</td>
                                            </c:if>
                                            <c:if test="${porePlate.pore_plate_quality == 3}">
                                                <td class="center">准备状态</td>
                                            </c:if>
											<td class="center">${porePlate.project_name}</td>
											<td class="center">${porePlate.sample_sum}</td>
											<td class="center">
												<c:if test="${QX.edit != 1 && QX.del != 1 }">
												<span class="label label-large label-grey arrowed-in-right arrowed-in"><i class="ace-icon fa fa-lock" title="无权限"></i></span>
												</c:if>
												<div class="hidden-sm hidden-xs btn-group">
													<c:if test="${QX.edit == 1 }">
													<a class="btn btn-xs btn-success" title="编辑" onclick="editPorePlate('${porePlate.id}');">
														<i class="ace-icon fa fa-pencil-square-o bigger-120" title="编辑"></i>
													</a>
													</c:if>
													<c:if test="${QX.del == 1 }">
													<a class="btn btn-xs btn-danger" onclick="delPorePlate('${porePlate.id }','${porePlate.pore_plate_name }');">
														<i class="ace-icon fa fa-trash-o bigger-120" title="删除"></i>
													</a>
													</c:if>
                                                    <a class="btn btn-xs btn-warning" onclick="showCourse('${porePlate.id }')">重做样本</a>
                                                    <a class="btn btn-xs btn-primary" id="a${porePlate.id}" onclick="toExcel('${porePlate.sample_sum}','${porePlate.id }','${porePlate.pore_plate_name}','${porePlate.pore_plate_quality}','${porePlate.username}')">生成板标</a>
												</div>
												<div class="hidden-md hidden-lg">
													<div class="inline pos-rel">
														<button class="btn btn-minier btn-primary dropdown-toggle" data-toggle="dropdown" data-position="auto">
															<i class="ace-icon fa fa-cog icon-only bigger-110"></i>
														</button>
														<ul class="dropdown-menu dropdown-only-icon dropdown-yellow dropdown-menu-right dropdown-caret dropdown-close">
															<c:if test="${QX.edit == 1 }">
															<li>
																<a style="cursor:pointer;" onclick="editUser('${porePlate.id}');" class="tooltip-success" data-rel="tooltip" title="修改">
																	<span class="green">
																		<i class="ace-icon fa fa-pencil-square-o bigger-120"></i>
																	</span>
																</a>
															</li>
															</c:if>
															<c:if test="${QX.del == 1 }">
															<li>
																<a style="cursor:pointer;"  onclick="delPorePlate('${porePlate.id }','${porePlate.pore_plate_name }');" class="tooltip-error" data-rel="tooltip" title="删除">
																	<span class="red">
																		<i class="ace-icon fa fa-trash-o bigger-120"></i>
																	</span>
																</a>
															</li>
															</c:if>
														</ul>
													</div>
												</div>
											</td>
										</tr>

									</c:forEach>
									</c:if>
									<c:if test="${QX.cha == 0 }">
										<tr>
											<td colspan="10" class="center">您无权查看</td>
										</tr>
									</c:if>
								</c:when>
								<c:otherwise>
									<tr class="main_info">
										<td colspan="10" class="center">没有相关数据</td>
									</tr>
								</c:otherwise>
							</c:choose>
							</tbody>
						</table>

					<div class="page-header position-relative">
					<table style="width:100%;">
						<tr>
							<td style="vertical-align:top;">
								<c:if test="${QX.add == 1 }">
								<a class="btn btn-mini btn-success" onclick="add(${page.totalResult},1);">新增孔板</a>
								</c:if>
                                <c:if test="${QX.add == 1 }">
								<a class="btn btn-mini btn-primary" style="background-color:rgba(153, 51, 204, 1)" onclick="add(${page.totalResult},2);">新增质孔板</a>
								</c:if>
                                <a class="btn btn-mini btn-warning" onclick="make('确定要通过选中数据的复核质控状态吗?');">复核质控</a>
                                <c:if test="${QX.del == 1 }">
								<a title="批量删除" class="btn btn-mini btn-danger" onclick="makeAll('确定要删除选中的数据吗?');" ><i class='ace-icon fa fa-trash-o bigger-120'></i></a>
								</c:if>
							</td>
							<td style="vertical-align:top;"><div class="pagination" style="float: right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div></td>
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
	<%@ include file="../system/index/foot.jsp"%>
	<!-- 删除时确认窗口 -->
	<script src="static/ace/js/bootbox.js"></script>
	<!-- ace scripts -->
	<script src="static/ace/js/ace/ace.js"></script>
	<!-- 日期框 -->
	<script src="static/ace/js/date-time/bootstrap-datepicker.js"></script>
	<!-- 下拉框 -->
	<script src="static/ace/js/chosen.jquery.js"></script>
	<!--提示框-->
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
	</body>

<script type="text/javascript">
$(top.hangge());

//检索
function searchs(){
	top.jzts();
	$("#userForm").submit();
}

//删除
function delPorePlate(id,msg){
	bootbox.confirm("确定要删除["+msg+"]吗?", function(result) {
		if(result) {
			top.jzts();
			var url = "<%=basePath%>porePlate/deletePorePlate.do?id="+id+"&tm="+new Date().getTime();
			$.get(url,function(data){
			    console.log(data.msg);
			    if(data.msg=="no"){
                    var zzz = "无法删除,该孔板下已有样本";
                    bootbox.dialog({
                        message: "<span class='bigger-110'>"+zzz+"</span>",
                        buttons:
                            {
                                "button" :
                                    {
                                        "label" : "确定",
                                        "className" : "btn-sm btn-success"
                                    }
                            }
                    });
                    top.hangge();
			        return;
                }else{
                    nextPage(${page.currentPage});
                }
			});
		};
	});
}

//新增
function add(totalResult,type){
	 top.jzts();
	 var diag = new top.Dialog();
	 diag.Drag=true;
     if(type==1){
        diag.Title ="新增孔板";
     }
     if(type==2){
        diag.Title ="新增质孔板";
     }
	 diag.URL = '<%=basePath%>porePlate/goAddPorePlate.do?totalResult='+totalResult+"&pore_plate_type="+type;
	 diag.Width = 469;
	 diag.Height = 250;
	 diag.CancelEvent = function(){ //关闭事件
		 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
			 if('${page.currentPage}' == '0'){
				 top.jzts();
				 setTimeout("self.location=self.location",100);
			 }else{
				 nextPage(${page.currentPage});
			 }
		}
		diag.close();
	 };
	 diag.show();
}

//修改
function editPorePlate(id){
	 top.jzts();
	 var diag = new top.Dialog();
	 diag.Drag=true;
	 diag.Title ="修改孔板";
	 diag.URL = '<%=basePath%>porePlate/goEditPorePlate.do?id='+id;
	 diag.Width = 469;
	 diag.Height = 580;
	 diag.CancelEvent = function(){ //关闭事件
		 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
			nextPage(${page.currentPage});
		}
		diag.close();
	 };
	 diag.show();
}

//批量操作
function make(msg){
	bootbox.confirm(msg, function(result) {
		if(result) {
			var str = '';
			var emstr = '';
			var phones = '';
			var username = '';
			for(var i=0;i < document.getElementsByName('ids').length;i++)
			{
				  if(document.getElementsByName('ids')[i].checked){
				  	if(str=='') str += document.getElementsByName('ids')[i].value;
				  	else str += ',' + document.getElementsByName('ids')[i].value;

				  	if(emstr=='') emstr += document.getElementsByName('ids')[i].id;
				  	else emstr += ';' + document.getElementsByName('ids')[i].id;

				  	if(phones=='') phones += document.getElementsByName('ids')[i].alt;
				  	else phones += ';' + document.getElementsByName('ids')[i].alt;

				  	if(username=='') username += document.getElementsByName('ids')[i].title;
				  	else username += ';' + document.getElementsByName('ids')[i].title;
				  }
			}
			if(str==''){
                bootbox.dialog({
                    message: "<span class='bigger-110'>您没有选择任何内容!</span>",
                    buttons:
                        { "button":{ "label":"确定", "className":"btn-sm btn-success"}}
                });
                $("#zcheckbox").tips({
                    side:3,
                    msg:'点这里全选',
                    bg:'#AE81FF',
                    time:8
                });
                return;
            }else if(phones.indexOf("0")>=0||phones.indexOf("1")>=0||phones.indexOf("2")>=0||phones.indexOf("3")>=0||phones.indexOf("5")>=0){
                bootbox.dialog({
                    message: "<span class='bigger-110'>请选择步骤在分析状态的孔板!</span>",
                    buttons:
                        { "button":{ "label":"确定", "className":"btn-sm btn-success"}}
                });
            }else if(username.indexOf("1")>=0||username.indexOf("2")>=0){
                bootbox.dialog({
                    message: "<span class='bigger-110'>请选择质检状态为准备状态的孔板!</span>",
                    buttons:
                        { "button":{ "label":"确定", "className":"btn-sm btn-success"}}
                });
            }else{
				if(msg == '确定要通过选中数据的复核质控状态吗?'){
					top.jzts();

					$.ajax({
						type: "POST",
						url: '<%=basePath%>porePlate/completeAllFuHe.do?tm='+new Date().getTime(),
				    	data: {ids:str},
						dataType:'json',
						//beforeSend: validateData,
						cache: false,
						success: function(data){
							nextPage(${page.currentPage});
						}
					});
				}else if(msg == '确定要给选中的用户发送邮件吗?'){
					sendEmail(emstr);
				}else if(msg == '确定要给选中的用户发送短信吗?'){
					sendSms(phones);
				}else if(msg == '确定要给选中的用户发送站内信吗?'){
					sendFhsms(username);
				}
			}
		}
	});
}

function makeAll(msg){
    bootbox.confirm(msg, function(result) {
        if(result) {
            var str = '';
            var emstr = '';
            var phones = '';
            var username = '';
            for(var i=0;i < document.getElementsByName('ids').length;i++)
            {
                if(document.getElementsByName('ids')[i].checked){
                    if(str=='') str += document.getElementsByName('ids')[i].value;
                    else str += ',' + document.getElementsByName('ids')[i].value;

                    if(emstr=='') emstr += document.getElementsByName('ids')[i].id;
                    else emstr += ';' + document.getElementsByName('ids')[i].id;

                    if(phones=='') phones += document.getElementsByName('ids')[i].alt;
                    else phones += ';' + document.getElementsByName('ids')[i].alt;

                    if(username=='') username += document.getElementsByName('ids')[i].title;
                    else username += ';' + document.getElementsByName('ids')[i].title;
                }
            }
            if(str==''){
                bootbox.dialog({
                    message: "<span class='bigger-110'>您没有选择任何内容!</span>",
                    buttons:
                        { "button":{ "label":"确定", "className":"btn-sm btn-success"}}
                });
                $("#zcheckbox").tips({
                    side:3,
                    msg:'点这里全选',
                    bg:'#AE81FF',
                    time:8
                });
                return;
            }else{
                if(msg == '确定要删除选中的数据吗?'){
                    top.jzts();
                    $.ajax({
                        type: "POST",
                        url: '<%=basePath%>porePlate/deleteAllPorePlate.do?tm='+new Date().getTime(),
                        data: {ids:str},
                        dataType:'json',
                        //beforeSend: validateData,
                        cache: false,
                        success: function(data){
                            var noDelete =  data.noDelete;
                            if(noDelete!=""){
                                alert("无法删除["+noDelete+"],该孔板下已有样本");
                            }
                            nextPage(${page.currentPage});
                        }
                    });
                }else if(msg == '确定要给选中的用户发送邮件吗?'){
                    sendEmail(emstr);
                }else if(msg == '确定要给选中的用户发送短信吗?'){
                    sendSms(phones);
                }else if(msg == '确定要给选中的用户发送站内信吗?'){
                    sendFhsms(username);
                }
            }
        }
    });
}


$(function() {
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
    function cleanForm(){$('form')[0].reset();}
     function goToLayout(id,pore_plate_type,plate_name,project) {
		 siMenu('z153','lm144','智能布板','porePlate/goToLayoutEdit.do?id='+id+'&pore_plate_type='+pore_plate_type+"&pore_plate_name="+plate_name+'&plate_project_id='+project);
	 }

    function showCourse(id){
        top.jzts();
        var diag = new top.Dialog();
        diag.Drag=true;
        diag.Title ="重做样本列表";
        diag.URL = '<%=basePath%>porePlate/showCourse.do?id='+id;
        diag.Width = 620;
        diag.Height = 520;
        diag.CancelEvent = function(){ //关闭事件
            diag.close();
        };
        diag.show();
    }

//导出excel
function toExcel(sum,id,pore_plate_name,pore_plate_quality,username){
    if( sum=="0"){
        $("#a"+id).tips({
            side:3,
            msg:'样本数为0,无法生成板标',
            bg:'#AE81FF',
            time:3
        });
        $("#a"+id).focus();
        return false;
    }
    window.location.href='<%=basePath%>porePlate/excel.do?id='+id+"&pore_plate_name="+pore_plate_name+"&pore_plate_quality="+pore_plate_quality+"&NAME="+username;
}




</script>
</html>
