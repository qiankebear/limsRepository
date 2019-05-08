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
    <!-- jsp文件头和头部 -->
    <%@ include file="../index/top.jsp"%>
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
                        <form action="punching/list.do" method="post" name="userForm" id="userForm">
                            <table style="margin-top:20px;margin-bottom: 10px">
                                <tr>

                                    <td>
                                        <select class="chosen-select form-control"name="projectName" id="projectName" data-placeholder="请选择项目名称"style="vertical-align:top;width:170px;" >
                                            <%--<option value="" ></option>--%>
                                            <option value="">请选择项目名称</option>
                                            <c:forEach var="list" items="${projectNameList}">
                                                <option value="${list.id}" <c:if test="${list.id == pd.projectName}">selected</c:if>>
                                                        ${list.project_name}</option>
                                            </c:forEach>
                                        </select>
                                    </td>
                                    <td style="padding-left:20px;">
                                        <div class="nav-search">
									        <span class="input-icon">
										    <input class="nav-search-input" autocomplete="off" id="nav-search-input" value="${pd.keywords}" type="text" name="keywords" placeholder="请输检索关键字" />
										    <input value="${pd.current_procedure}" type="hidden" name="current_procedure" id="current_procedure" />
										    <i class="ace-icon fa fa-search nav-search-icon"></i>
									        </span>
                                        </div>
                                    </td>
                                    <%--<c:if test="${QX.cha == 1 }">--%>
                                        <td style="vertical-align:top;padding-left:10px;"><a class="btn btn-light btn-xs" onclick="searchs();"  title="检索"><i id="nav-search-icon" class="ace-icon fa fa-search bigger-110 nav-search-icon blue"></i></a></td>
                                    <%--</c:if>--%>
                                </tr>
                            </table>

                            <!-- 检索  -->

                            <table id="simple-table" class="table table-striped table-bordered table-hover"  style="margin-top:5px;">
                                <thead>
                                <tr>
                                    <%-- <th class="center" style="width:35px;">
                                         <label class="pos-rel"><input type="checkbox" class="ace" id="zcheckbox" /><span class="lbl"></span></label>
                                     </th>--%>
                                    <th class="center" style="width:50px;">序号</th>
                                    <th class="center">孔板名称</th>
                                    <th class="center">项目</th>
                                    <c:if test="${pd.current_procedure == 2 }">
                                        <th class="center">操作</th>
                                    </c:if>
                                    <c:if test="${pd.current_procedure == 3 }">
                                        <th class="center">操作</th>
                                    </c:if>
                                </tr>
                                </thead>

                                <tbody>

                                <!-- 开始循环 -->
                                <c:choose>
                                    <c:when test="${not empty list}">
                                        <c:if test="${QX.cha == 1 }">
                                            <c:forEach items="${list}" var="list" varStatus="vs">
                                                <tr>
                                                    <td class='center' style="width: 30px;">${page.showCount*(page.currentPage-1)+vs.index+1}</td>
                                                    <td class="center"><a onclick="goToLayout('${list.id}','${list.pore_plate_type}','${list.NAME}','${list.pID}')" style="cursor:pointer;"><i id="nav-search-icon" class="ace-icon fa fa-search bigger-110 nav-search-icon blue"></i>${list.NAME}</a></td>
                                                    <td class="center">${list.projectName }</td>
                                                    <c:if test="${pd.current_procedure == 2 }">
                                                        <td class="center">
                                                            <a class="btn btn-mini btn-info" title="扩增" onclick="amplification('${list.id}','${list.pID}','${list.NAME}');">
                                                                    完成扩增
                                                            </a>
                                                        </td>
                                                    </c:if>
                                                    <c:if test="${pd.current_procedure == 3 }">
                                                        <td class="center">
                                                            <a class="btn btn-mini btn-info" title="检测" onclick="check('${list.id}');">
                                                                完成检测
                                                            </a>
                                                        </td>
                                                    </c:if>
                                                </tr>
                                            </c:forEach>
                                        </c:if>
                                        <%--<c:if test="${QX.cha == 0 }">--%>
                                            <%--<tr>
                                                <td colspan="10" class="center">您无权查看</td>
                                            </tr>--%>
                                        <%--</c:if>--%>
                                    </c:when>
                                    <c:otherwise>
                                        <tr class="main_info">
                                            <td colspan="10" class="center ">没有相关数据</td>
                                        </tr>
                                    </c:otherwise>
                                </c:choose>
                                </tbody>
                            </table>
                            <input type="hidden" id="Id"/>
                            <div class="page-header position-relative">
                                <table style="width:100%;">
                                    <tr>
                                        <%--<td style="vertical-align:top;">
                                            <c:if test="${QX.adds == 1 }">
                                                <a class="btn btn-mini btn-success" onclick="add();">新增</a>
                                            </c:if>
                                        </td>--%>
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
<%@ include file="../index/foot.jsp"%>
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
<script type="text/javascript" src="static/js/myjs/head.js"></script>
</body>

<script type="text/javascript">
    $(top.hangge());
    // 页面跳转自动执行
    $(window).load(function(){
        $("#userForm").submit;
    });

    // 完成扩增
    function amplification(poreId,pID,Name) {
        var sampleNumber = 0 ;
        top.jzts();
        var diag = new top.Dialog();
        diag.Drag=true;
        diag.Title ="完成扩增";
        diag.URL = '<%=basePath%>porePlate/goSelectInstrument.do?current_procedure=3&plate_project_id='+pID+"&sample_sum="+sampleNumber+"&id="+poreId+"&pore_plate_name="+Name;
        diag.Width = 600;
        diag.Height = 450;
        diag.CancelEvent = function(){ //关闭事件
            if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
                diag.close();
            }
            diag.close();
        };
        diag.show();
    }

    // 完成检测
    function check(poreId) {
        top.jzts();
        var diag = new top.Dialog();
        diag.Drag=true;
        diag.Title ="完成检测";
        diag.URL = '<%=basePath%>porePlate/goSelectInstrumentForTest.do?id='+poreId;
        diag.Width = 600;
        diag.Height = 450;
        diag.CancelEvent = function(){ //关闭事件
            if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
                diag.close();
            }
            diag.close();
        };
        diag.show();
    }

    //检索
    function searchs(){
        top.jzts();
        $("#userForm").submit();
    }

    //跳转智能布板页面
    function goToLayout(id,pore_plate_type,plate_name,project) {
        var keyWord = $("#nav-search-input").val();
        var current_procedure = $("#current_procedure").val();
        siMenu('z150','lm144','智能布板','porePlate/goToLayoutEdit.do?id='+id+'&pore_plate_type='+pore_plate_type+"&pore_plate_name="+plate_name+'&plate_project_id='+project+'&keyWord='+keyWord+'&current_procedure='+current_procedure);
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
</script>
</html>
