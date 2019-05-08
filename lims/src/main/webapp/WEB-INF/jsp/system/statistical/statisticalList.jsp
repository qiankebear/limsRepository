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
                        <form action="statistical/list.do" method="post" name="userForm" id="userForm">
                            <table style="margin-top:20px;margin-bottom: 10px">
                                <tr>
                                    <td>
                                        <div class="nav-search">
									        <span class="input-icon">
										    <input class="nav-search-input" autocomplete="off" id="nav-search-input" value="${pd.keywords}" type="text" name="keywords" placeholder="请输输入项目名称" />
										    <i class="ace-icon fa fa-search nav-search-icon"></i>
									        </span>
                                        </div>
                                    </td>
                                    <td style="padding-left:20px;">
                                        <select class="chosen-select form-control" name="type" id="type"style="vertical-align:top;width:98%;" >
                                            <option value = "" disabled selected style='display:none;'>请选择步骤</option>
                                            <option value = "" <c:if test="${pd.type==''}">selected</c:if>>全部</option>
                                            <option value = "1" <c:if test="${pd.type==1}">selected</c:if>>布板</option>
                                            <option value = "2" <c:if test="${pd.type==2}">selected</c:if>>打孔</option>
                                            <option value = "3" <c:if test="${pd.type==3}">selected</c:if>>扩增</option>
                                            <option value = "4" <c:if test="${pd.type==4}">selected</c:if>>分析</option>
                                        </select>
                                    </td>
                                        <td style="vertical-align:top;padding-left:10px;"><a class="btn btn-light btn-xs" onclick="searchs();"  title="检索"><i id="nav-search-icon" class="ace-icon fa fa-search bigger-110 nav-search-icon blue"></i></a></td>
                                </tr>
                            </table>

                            <!-- 检索  -->

                            <table id="simple-table" class="table table-striped table-bordered table-hover"  style="margin-top:5px;">
                                <thead>
                                    <tr>
                                        <th class="center" style="width:50px;">序号</th>
                                        <th class="center">孔板名称</th>
                                        <th class="center">项目</th>
                                        <th class="center">样本坐标</th>
                                        <th class="center">步骤</th>
                                        <th class="center">旧孔类型</th>
                                        <th class="center">新孔类型</th>
                                        <th class="center">旧样本类型</th>
                                        <th class="center">新样本类型</th>
                                        <th class="center">操作人</th>
                                        <th class="center">操作时间</th>
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
                                                    <td class="center">${list.pore_plate_name }</td>
                                                    <td class="center">${list.project_name }</td>
                                                    <td class="center">${list.hole_number }</td>
                                                    <td class="center">
                                                        <c:if test="${list.update_type == 1}">布板</c:if>
                                                        <c:if test="${list.update_type == 2}">打孔</c:if>
                                                        <c:if test="${list.update_type == 3}">扩增</c:if>
                                                        <c:if test="${list.update_type == 4}">分析</c:if>
                                                    </td>
                                                    <td class="center">
                                                        <c:if test="${list.oldhole_type == 1}">样本编号</c:if>
                                                        <c:if test="${list.oldhole_type == 2}">P</c:if>
                                                        <c:if test="${list.oldhole_type == 3}">O</c:if>
                                                        <c:if test="${list.oldhole_type == 4}">复核孔</c:if>
                                                        <c:if test="${list.oldhole_type == 5}">ladder</c:if>
                                                        <c:if test="${list.oldhole_type == 6}">空孔</c:if>
                                                    </td>
                                                    <td class="center">
                                                        <c:if test="${list.newhole_type == 1}">样本编号</c:if>
                                                        <c:if test="${list.newhole_type == 2}">P</c:if>
                                                        <c:if test="${list.newhole_type == 3}">O</c:if>
                                                        <c:if test="${list.newhole_type == 4}">复核孔</c:if>
                                                        <c:if test="${list.newhole_type == 5}">ladder</c:if>
                                                        <c:if test="${list.newhole_type == 6}">空孔</c:if>
                                                    </td>
                                                    <td class="center">
                                                        <c:if test="${list.oldspecial_sample == 0}">正常</c:if>
                                                        <c:if test="${list.oldspecial_sample == 1}">微变异</c:if>
                                                        <c:if test="${list.oldspecial_sample == 2}">稀有等位、2</c:if>
                                                        <c:if test="${list.oldspecial_sample == 3}">三等位</c:if>
                                                        <c:if test="${list.oldspecial_sample == 4}">重复分型</c:if>
                                                        <c:if test="${list.oldspecial_sample == 5}">失败</c:if>
                                                        <c:if test="${list.oldspecial_sample == 6}">其它</c:if>
                                                        <c:if test="${list.oldspecial_sample == 7}">空卡</c:if>
                                                        <c:if test="${list.oldspecial_sample == 8}">其它问题</c:if>
                                                    </td>
                                                    <td class="center">
                                                        <c:if test="${list.newspecial_sample == 0}">正常</c:if>
                                                        <c:if test="${list.newspecial_sample == 1}">微变异</c:if>
                                                        <c:if test="${list.newspecial_sample == 2}">稀有等位</c:if>
                                                        <c:if test="${list.newspecial_sample == 3}">三等位</c:if>
                                                        <c:if test="${list.newspecial_sample == 4}">重复分型</c:if>
                                                        <c:if test="${list.newspecial_sample == 5}">失败</c:if>
                                                        <c:if test="${list.newspecial_sample == 6}">其它</c:if>
                                                        <c:if test="${list.newspecial_sample == 7}">空卡</c:if>
                                                        <c:if test="${list.newspecial_sample == 8}">其它问题</c:if>
                                                    </td>
                                                    <td class="center">${list.NAME }</td>
                                                    <td class="center">${list.update_time }</td>
                                                </tr>
                                            </c:forEach>
                                        </c:if>
                                       <%-- <c:if test="${QX.cha == 0 }">
                                            <tr>
                                                <td colspan="10" class="center">您无权查看</td>
                                            </tr>
                                        </c:if>--%>
                                    </c:when>
                                    <c:otherwise>
                                        <tr class="main_info">
                                            <td colspan="11" class="center">没有相关数据</td>
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
</body>

<script type="text/javascript">
    $(top.hangge());

    //检索
    function searchs(){
        top.jzts();
        $("#userForm").submit();
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
