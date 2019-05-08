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
<!-- jsp文件头和头部 -->
<%@ include file="../../system/index/top.jsp"%>
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
							
						<table style="margin-top:10px;">
							<tr>
								<td>
								<span class="label label-lg label-success arrowed-right">${dbtype}</span>
								
								<span class="label label-lg label-yellow arrowed-in arrowed-in-right" id="backupts">${databaseName}</span>
								</td>
								<td>
								<div id="backuping1" class="center" style="display:none"><img src="static/images/jzx.gif" width="50" /></div>
								</td>
								<td>
								<div id="backuping2" style="padding-top: 8px; display:none"><h4 class="lighter block red">正在备份……</h4></div>
								</td>
							</tr>
						</table>
					

						<div class="page-header position-relative">
						<table style="width:100%;">
							<tr>
								<td style="vertical-align:top;">
									<a class="btn btn-sm btn-info" onclick="backupall();">备份整个库</a>
								</td>
								<td style="vertical-align:top;"><div class="pagination" style="float: right;padding-top: 0px;margin-top: 0px;"></div></td>
							</tr>
						</table>
						</div>
					
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
	<!--提示框-->
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
	<script type="text/javascript">
		$(top.hangge());//关闭加载状态
		$(function() {
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
		
		//备份全库
		function backupall(){
			bootbox.confirm("确定要备份整个数据库吗?", function(result) {
				if(result) {
					$("#backuping1").show();
					$("#backuping2").show();
					 $.ajax({
							type: "POST",
							url: '<%=basePath%>brdb/backupAll.do?tm='+new Date().getTime(),
					    	data: {},
							dataType:'json',
							//beforeSend: validateData,
							cache: false,
							success: function(data){
								 $.each(data.list, function(i, list){
									 if("ok" == list.msg){
										 $("#backupts").tips({
												side:3,
									            msg:'备份成功,在数据还原里面查看记录',
									            bg:'#009933',
									            time:15
									        });
									 }else{
										 $("#backupts").tips({
												side:3,
									            msg:'备份失败,检查配置文件及备份保存路径',
									            bg:'#cc0033',
									            time:15
									        });
									 }
									$("#backuping1").hide();
									$("#backuping2").hide();
								 });
							}
						});
				}
			});
		}
		
		//备份单表
		function backupTable(fhtable){
			bootbox.confirm("确定要备份这表[ "+fhtable+" ]吗?", function(result) {
				if(result) {
					backupAjax(fhtable,"backupts");
				}
			});
		}
		
		/**请求备份
		* fhtable：表名
		* id ：提示对象的ID
		*/
		function backupAjax(fhtable,id){
			$("#backuping1").show();
			$("#backuping2").show();
				 $.ajax({
						type: "POST",
						url: '<%=basePath%>brdb/backupTable.do?tm='+new Date().getTime(),
				    	data: {fhtable:fhtable},
						dataType:'json',
						//beforeSend: validateData,
						cache: false,
						success: function(data){
							 $.each(data.list, function(i, list){
								 if("ok" == list.msg){
									 $("#"+id).tips({
											side:2,
								            msg:'备份成功',
								            bg:'#009933',
								            time:5
								        });
								 }else{
									 $("#"+id).tips({
											side:3,
								            msg:'备份失败,检查配置文件及备份保存路径',
								            bg:'#cc0033',
								            time:15
								        });
								 }
								$("#backuping1").hide();
								$("#backuping2").hide();
							 });
						}
					});
		}
		
		//批量操作
		function makeAll(msg){
			bootbox.confirm(msg, function(result) {
				if(result) {
					var str = '';
					var fid = '';
					for(var i=0;i < document.getElementsByName('ids').length;i++){
					  if(document.getElementsByName('ids')[i].checked){
					  	if(str=='') str += document.getElementsByName('ids')[i].value;
					  	else str += ',fh,' + document.getElementsByName('ids')[i].value;
					  	
					  	if(fid=='') fid += document.getElementsByName('ids')[i].id;
					  	else fid += ',fh,' + document.getElementsByName('ids')[i].id;
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
						if(msg == '确定要批量备份这些表吗？'){
							var arrTable = str.split(',fh,');
							var arrFid = fid.split(',fh,');
							for(var fhi=0;fhi<arrTable.length;fhi++){
								backupAjax(arrTable[fhi],arrFid[fhi]);
							}
						}
					}
				}
			});
		};
		
	</script>


</body>
</html>