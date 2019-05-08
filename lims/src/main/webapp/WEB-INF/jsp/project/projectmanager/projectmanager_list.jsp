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
						<form action="projectmanager/list.do" method="post" name="Form" id="Form">
						<table style="margin-top:5px;">
							<tr>
								<td>
									<div style="padding-top: 20px; padding-bottom: 10px;" class="nav-search">
										<span class="input-icon">
											<input type="text" placeholder="这里输入项目编号" class="nav-search-input" id="nav-search-input1" autocomplete="off" name="keywords1" value="${pd.keywords1}" placeholder="这里输入关键词"/>
											<i class="ace-icon fa fa-search nav-search-icon"></i>
										</span>
									</div>
								</td>
								<td>
									<div style=" padding-top: 20px; padding-bottom: 10px;padding-left: 20px">
										<select class="chosen-select form-control" id="sl1" name="sl1" data-placeholder="请选择项目" style="vertical-align:top;width: 170px;">
											<option value=""></option>
											<option value="">全部</option>
											<c:forEach items="${projectAll}" var="user">
												<option value="${user.project_name}" <c:if test="${pd.sl1==user.project_name}">selected</c:if> >${user.project_name }</option>
											</c:forEach>
										</select>
									</div>
								</td>

								<td style="vertical-align:top;padding-top: 20px; padding-bottom: 10px;padding-left: 20px">
								 	<select class="chosen-select form-control" name="PROJECT_STATUS1" id="PROJECT_STATUS1" data-placeholder="请选择状态" style="vertical-align:top;width: 120px;">
									<option value=""></option>
									<option value="" >全部</option>
									<option value="1" <c:if test="${pd.PROJECT_STATUS1=='1'}">selected</c:if>>准备中</option>
									<option value="2" <c:if test="${pd.PROJECT_STATUS1=='2'}">selected</c:if>>进行中</option>
									<option value="3" <c:if test="${pd.PROJECT_STATUS1=='3'}">selected</c:if>>已完成</option>
									<option value="4" <c:if test="${pd.PROJECT_STATUS1=='4'}">selected</c:if>>其他</option>
								  	</select>
								</td>
								<td style="vertical-align:top;padding-top: 20px; padding-bottom: 10px;padding-left: 10px"><a class="btn btn-light btn-xs" onclick="tosearch();"  title="检索"><i id="nav-search-icon" class="ace-icon fa fa-search bigger-110 nav-search-icon blue"></i></a></td>

							</tr>
						</table>
						<!-- 检索  -->
					
						<table id="simple-table" class="table table-striped table-bordered table-hover" style="margin-top:5px;">	
							<thead>
								<tr>
									<th class="center" style="width:50px;">序号</th>
									<th class="center">项目编号</th>
									<th class="center">项目名称</th>
									<th class="center">项目缩写</th>
									<th class="center">项目状态</th>
									<th class="center">复核孔个数</th>
									<th class="center">项目开始时间</th>
									<th class="center">操作</th>
								</tr>
							</thead>
							<tbody>
							<!-- 开始循环 -->	
							<c:choose>
								<c:when test="${not empty varList}">
									<c:forEach items="${varList}" var="var" varStatus="vs">
										<tr>
											<td class='center' style="width: 30px;">${page.showCount*(page.currentPage-1)+vs.index+1}</td>
											<td class='center'>${var.project_number}</td>
											<td class='center'>${var.project_name}</td>
											<td class='center'>${var.project_number_abbreviation}</td>
											<c:if test="${var.project_status==1}">
											<td class='center'>准备中</td>
											</c:if>
											<c:if test="${var.project_status==2}">
												<td class='center'>进行中</td>
											</c:if>
											<c:if test="${var.project_status==3}">
												<td class='center'>已完成</td>
											</c:if>
											<c:if test="${var.project_status==4}">
												<td class='center'>其他</td>
											</c:if>
											<td class='center'>${var.recheck_hole_amount}</td>
											<td class='center'>${var.newDate}</td>
											<td class="center">
												<%--<c:if test="${QX.edit != 1 && QX.del != 1 }">--%>
												<%--<span class="label label-large label-grey arrowed-in-right arrowed-in"><i class="ace-icon fa fa-lock" title="无权限"></i></span>--%>
												<%--</c:if>--%>
												<%--<div class="hidden-sm hidden-xs btn-group">--%>
                                                    <c:if test="${var.USERNAME == user}">
                                                        <a class="btn btn-xs btn-purple" onclick="emp('${var.id}');">赋权</a>
                                                    </c:if>
                                                    <a class="btn btn-xs  btn-yellow" onclick="showRep('${var.id}');">库存列表</a>
                                                    <c:if test="${var.member_kind==1 || var.member_kind==2}">
													<c:if test="${var.project_status!=3}">
                                                        <a class="btn btn-xs btn-success" onclick="inPut('${var.id}');">入库</a>
                                                        <a class="btn btn-xs btn-danger" onclick="outPut('${var.id}');">出库</a>
													</c:if>
                                                    </c:if>
                                                    <c:if test="${var.USERNAME == user}">
                                                        <c:if test="${var.project_status!=3}">
                                                            <a class="btn btn-xs btn-danger" onclick="endProject('${var.id}','${var.project_status}');">结束项目</a>
                                                        </c:if>
                                                    </c:if>
                                                    <a class="btn btn-xs  btn-success" onclick="sampleCheck('${var.id}');">样本总数确认</a>
													<a class="btn btn-xs  btn-success" onclick="listPerson('${var.id}');">相关人员</a>

													<c:if test="${var.project_status != 3}">

                                                        <c:if test="${var.USERNAME == user}">
													<a class="btn btn-xs btn-success" title="编辑" onclick="edit('${var.id}');">
														<i class="ace-icon fa fa-pencil-square-o bigger-120" title="编辑"></i>
													</a>
													</c:if>
													<c:if test="${var.roleName == 'R20170000000001'}">
													<a class="btn btn-xs btn-danger" onclick="del('${var.id}');">
														<i class="ace-icon fa fa-trash-o bigger-120" title="删除"></i>
													</a>

													</c:if>


												</c:if>

											</td>
										</tr>
									</c:forEach>
									<%--<c:if test="${QX.cha == 0 }">--%>
										<%--<tr>--%>
											<%--<td colspan="100" class="center">您无权查看</td>--%>
										<%--</tr>--%>
									<%--</c:if>--%>
								</c:when>
								<c:otherwise>
									<tr class="main_info">
										<td colspan="100" class="center" >没有相关数据</td>
									</tr>
								</c:otherwise>
							</c:choose>
							</tbody>
						</table>
						<div class="page-header position-relative">
						<table style="width:100%;">
							<tr>
								<td style="vertical-align:top;">
									<a class="btn btn-mini btn-success" onclick="add();">新增</a>
									<c:if test="${var.USERNAME == user}">
									<a class="btn btn-mini btn-danger" onclick="makeAll('确定要删除选中的数据吗?');" title="批量删除" ><i class='ace-icon fa fa-trash-o bigger-120'></i></a>

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
	<%@ include file="../../system/index/foot.jsp"%>
	<!-- 删除时确认窗口 -->
	<script src="static/ace/js/bootbox.js"></script>
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
		//检索
		function tosearch(){
			top.jzts();
			$("#Form").submit();
		}
		$(function() {
		
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
		
		//新增
		function add(){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="新增";
			 diag.URL = '<%=basePath%>projectmanager/goAdd.do';
			 diag.Width = 450;
			 diag.Height = 530;
			 diag.Modal = true;				//有无遮罩窗口
			 diag. ShowMaxButton = true;	//最大化按钮
		     diag.ShowMinButton = true;		//最小化按钮
			 diag.CancelEvent = function(){ //关闭事件
				 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
					 if('${page.currentPage}' == '0'){
						 tosearch();
					 }else{
						 tosearch();
					 }
				}
				diag.close();
			 };
			 diag.show();
		}
		
		//删除
		function del(Id){
			bootbox.confirm("确定要删除吗?", function(result) {
				if(result) {
					// top.jzts();
					var url = "<%=basePath%>projectmanager/delete.do?id="+Id+"&tm="+new Date().getTime();
					$.get(url,function(data){
					    if ("faild"==data){
                            bootbox.confirm("删除失败！当前项目已关联孔板，请先将关联孔板删除！",function (result) {
                                if (result){}
                            });
						}else{
							tosearch();
						}

					});
				}
			});
		}

        function  sampleCheck(Id){
            $.ajax({
                type: "POST",
                url: '<%=basePath%>projectmanager/sampleCheck.do?id='+Id,
                dataType:'json',
                cache: false,
                success: function(data){
                    // alert(data.data);
                    bootbox.confirm(data.data,function (result) {
						if (result){
                            tosearch();
						}
                    });
                }
            });

            <%--bootbox.confirm("进行样本总数确认?", function(result) {--%>
                <%--if(result) {--%>
                    <%--top.jzts();--%>
                    <%--var url = "<%=basePath%>projectmanager/sampleCheck.do?id="+Id+"&tm="+new Date().getTime();--%>
                    <%--$.get(url,function(data){--%>
                            <%--alert(data);--%>
                        <%--// bootbox.confirm(data);--%>
                        <%--tosearch();--%>
                    <%--});--%>
                <%--}--%>
            <%--});--%>
		}

         function endProject(Id,projectStatus) {
             {
                 bootbox.confirm("确定要结束项目吗?", function (result) {
                     if (result) {
                         //验证项目下是否有关联孔板
                         $.ajax({
                             type: "POST",
                             url: '<%=basePath%>endProject/findNopassplate.do',
                             data: {projectId: Id},
                             dataType: 'json',
                             //beforeSend: validateData,
                             cache: false,
                             success: function (data) {
                                 if (projectStatus != "2") {
                                     result = false;
                                     bootbox.dialog({
                                         message: "<span class='bigger-110'>只能结束进行中的项目!</span>",
                                         buttons:
                                             {"button": {"label": "确定", "className": "btn-sm btn-success"}}
                                     });
                                 } else {
                                     if (data.status) {
                                         top.jzts();
                                         var url = "<%=basePath%>endProject/save.do?id=" + Id;
                                         $.get(url, function () {
                                             tosearch();
                                         });
                                     } else {
                                         bootbox.dialog({
                                             message: "<span class='bigger-110'>该项目下还有孔板没有通过复核质检,不能结束项目!</span>",
                                             buttons:
                                                 {"button": {"label": "确定", "className": "btn-sm btn-success"}}
                                         });
                                     }
                                 }

                             }
                         });
                     }
                 });
             }
         }
        function emp(Id){
            top.jzts();
            var diag = new top.Dialog();
            diag.Drag=true;
            diag.Title ="赋权";
            diag.URL = '<%=basePath%>projectmanager/goEmp.do?id='+Id;
            diag.Width = 450;
            diag.Height = 355;
            diag.Modal = true;				//有无遮罩窗口
            diag. ShowMaxButton = true;	//最大化按钮
            diag.ShowMinButton = true;		//最小化按钮
            diag.CancelEvent = function(){ //关闭事件
                diag.close();
            };
            diag.show();
        }
		
		//修改
		function edit(Id){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="编辑";
			 diag.URL = '<%=basePath%>projectmanager/goEdit.do?id='+Id;
			 diag.Width = 450;
			 diag.Height = 530;
			 diag.Modal = true;				//有无遮罩窗口
			 diag. ShowMaxButton = true;	//最大化按钮
		     diag.ShowMinButton = true;		//最小化按钮 
            diag.CancelEvent = function(){ //关闭事件
                if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
                    if('${page.currentPage}' == '0'){
                        tosearch();
                    }else{
                        tosearch();
                    }
                }
                diag.close();
            };
			 diag.show();
		}
		// 入库
        function inPut(Id){
            top.jzts();
            var diag = new top.Dialog();
            diag.Drag=true;
            diag.Title ="入库";
            diag.URL = '<%=basePath%>projectmanager/goInPut.do?id='+Id+'&type=0';
            diag.Width = 450;
            diag.Height = 300;
            diag.Modal = true;				//有无遮罩窗口
            diag. ShowMaxButton = true;	//最大化按钮
                diag.ShowMinButton = true;		//最小化按钮
                diag.CancelEvent = function(){ //关闭事件
                diag.close();
            };
            diag.show();
        }

        // 出库
        function outPut(Id){
            top.jzts();
            var diag = new top.Dialog();
            diag.Drag=true;
            diag.Title ="出库";
            diag.URL = '<%=basePath%>projectmanager/goOutPut.do?id='+Id+'&type=1';
            diag.Width = 450;
            diag.Height = 300;
            diag.Modal = true;				//有无遮罩窗口
            diag. ShowMaxButton = true;	//最大化按钮
            diag.ShowMinButton = true;		//最小化按钮
            diag.CancelEvent = function(){ //关闭事件
                diag.close();
            };
            diag.show();
        }
        //查看库存列表
        function showRep(Id){
            top.jzts();
            var diag = new top.Dialog();
            diag.Drag=true;
            diag.Title ="库存列表";
            diag.URL = '<%=basePath%>projectmanager/listRep.do?id='+Id;
            diag.Width = 550;
            diag.Height = 355;
            diag.Modal = true;				//有无遮罩窗口
            diag. ShowMaxButton = true;	//最大化按钮
            diag.ShowMinButton = true;		//最小化按钮
            diag.CancelEvent = function(){ //关闭事件
                diag.close();
            };
            diag.show();
        }
        // 相关人员
        function listPerson(Id){
            top.jzts();
            var diag = new top.Dialog();
            diag.Drag=true;
            diag.Title ="相关人员";
            diag.URL = '<%=basePath%>projectmanager/listPerson.do?id='+Id;
            diag.Width = 650;
            diag.Height = 100;
            diag.Modal = true;				//有无遮罩窗口
            diag. ShowMaxButton = true;	//最大化按钮
            diag.ShowMinButton = true;		//最小化按钮
            diag.CancelEvent = function(){ //关闭事件
                diag.close();
            };
            diag.show();
        }

		//批量操作
		function makeAll(msg){
			bootbox.confirm(msg, function(result) {
				if(result) {
					var str = '';
					for(var i=0;i < document.getElementsByName('ids').length;i++){
					  if(document.getElementsByName('ids')[i].checked){
					  	if(str=='') str += document.getElementsByName('ids')[i].value;
					  	else str += ',' + document.getElementsByName('ids')[i].value;
					  }
					}
					if(str==''){
						bootbox.dialog({
							message: "<span class='bigger-110'>您没有选择任何内容!</span>",
							buttons: 			
							{ "button":{ "label":"确定", "className":"btn-sm btn-success"}}
						});
						$("#zcheckbox").tips({
							side:1,
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
								url: '<%=basePath%>projectmanager/deleteAll.do?tm='+new Date().getTime(),
						    	data: {DATA_IDS:str},
								dataType:'json',
								//beforeSend: validateData,
								cache: false,
								success: function(data){
									 $.each(data.list, function(i, list){
											tosearch();
									 });
								}
							});
						}
					}
				}
			});
		};
		
		//导出excel
		function toExcel(){
			window.location.href='<%=basePath%>projectmanager/excel.do';
		 }
	</script>
</body>
</html>