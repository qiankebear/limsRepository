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
                        <form action="rebuild/list.do" method="post" name="userForm" id="userForm">
                            <table style="margin-top:20px;margin-bottom: 10px">
                                <tr>
                                    <td>
                                        <div class="nav-search" id="sampleofSum" style="display: none">
                                            <span >重扩样本总数</span>
										    <input class="nav-search-input" autocomplete="off" id="sum" value="${pd.keywords}" type="text" name="keywords" placeholder="重扩样本总数" />
										   <%-- <i class="ace-icon fa fa-search nav-search-icon"></i>--%>
									        </span>
                                        </div>
                                    </td>
                                    <td style="padding-left:20px;">
                                        <select class="chosen-select form-control"name="projectName" id="projectName" data-placeholder="请选择项目名称"style="vertical-align:top;width:170px;" >
                                            <%--<option value="" ></option>--%>
                                            <option value="">请选择项目名称</option>
                                            <c:forEach var="list" items="${projectNameList}">
                                                <option value="${list.id}" <c:if test="${list.id == pd.projectName}">selected</c:if>>
                                                        ${list.project_name}</option>
                                            </c:forEach>
                                        </select>
                                    </td>
                                    <td style="padding-left:20px;>
                                        <div class="nav-search">
									        <span class="input-icon">
										    <input class="nav-search-input" autocomplete="off" id="nav-search-input" value="${pd.plateName}" type="text" name="plateName" placeholder="请输入孔板名称" />
										    <i class="ace-icon fa fa-search nav-search-icon"></i>
									        </span>
                                        </div>
                                    </td>
                                    <%--<td style="padding-left:20px;>
                                        <div class="nav-search">
									        <span class="input-icon">
										    <input class="nav-search-input" autocomplete="off" id="project" value="${pd.projectName}" type="text" name="projectName" placeholder="请输入项目名称" />
										    <i class="ace-icon fa fa-search nav-search-icon"></i>
									        </span>
                                        </div>
                                    </td>--%>
                                   <td style="padding-left:20px;">
                                       <select name="rebuild" id="state" style="vertical-align:top;width:98%;" >
                                           <option value = "" disabled selected style='display:none;'>请选是否整版重扩</option>
                                           <option value = "" <c:if test="${pd.rebuild == ''}">selected</c:if>>全部</option>
                                           <option value = "1" <c:if test="${pd.rebuild == 1}">selected</c:if>>是</option>
                                           <option value = "2" <c:if test="${pd.rebuild == 2}">selected</c:if>>否</option>
                                       </select>
                                   </td>
                                    <td style="padding-left:20px;">
                                        <select name="lims_pore_serial" id="lims_pore_serial" style="vertical-align:top;width:98%;" >
                                            <option value = "1" <c:if test="${pd.lims_pore_serial == 1}">selected</c:if>>第一轮</option>
                                            <option value = "2" <c:if test="${pd.lims_pore_serial == 2}">selected</c:if>>第二轮</option>
                                            <option value = "3" <c:if test="${pd.lims_pore_serial == 3}">selected</c:if>>第三轮</option>
                                            <option value = "4" <c:if test="${pd.lims_pore_serial == 4}">selected</c:if>>第四轮</option>
                                            <option value = "5" <c:if test="${pd.lims_pore_serial == 5}">selected</c:if>>第五轮</option>
                                        </select>
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
                                    <th class="center" style="width:35px;">
                                        <label class="pos-rel"><input type="checkbox"  class="ace" id="zcheckbox" /><span class="lbl"></span></label>
                                    </th>
                                    <th class="center" style="width:50px;">序号</th>
                                    <th class="center">孔板名称</th>
                                    <th class="center">项目</th>
                                    <th class="center" >需要重扩的样本个数</th>
                                    <th class="center">是否整版重扩</th>
                                   <%-- <th class="center">操作</th>--%>
                                </tr>
                                </thead>

                                <tbody>

                                <!-- 开始循环 -->
                                <c:choose>
                                    <c:when test="${not empty list}">
                                        <%--<c:if test="${QX.cha == 1 }">--%>
                                            <c:set value="1" var="num"></c:set>
                                            <c:forEach items="${list}" var="list" varStatus="vs">

                                                <tr id="tr${num}">
                                                    <c:set value="${num+1}" var="num"></c:set>
                                                    <td class='center' style="width: 30px;" id="check">
                                                        <c:if test="${list.entirety == 1}">
                                                            <c:if test="${user.USERNAME != 'admin'}"><label><input   type='checkbox' onclick="change('${list.entirety}')"name='ids' value="${list.id }" id="${list.id }"class="ace"/><span class="lbl"></span></label></c:if>
                                                            <c:if test="${user.USERNAME == 'admin'}"><label><input   type='checkbox' disabled="disabled" class="ace" /><span class="lbl"></span></label></c:if>
                                                        </c:if>
                                                        <c:if test="${list.entirety == 2}">
                                                            <c:if test="${user.USERNAME != 'admin'}"><label><input type='checkbox' onclick="change('${list.entirety}')"name='ids' value="${list.id }" id="${list.id }"class="ace"/><span class="lbl"></span></label></c:if>
                                                            <c:if test="${user.USERNAME == 'admin'}"><label><input type='checkbox' disabled="disabled" class="ace" /><span class="lbl"></span></label></c:if>
                                                        </c:if>
                                                        <c:if test="${list.entirety == 3}">
                                                            <c:if test="${user.USERNAME != 'admin'}"><label><input type='checkbox' onclick="change('${list.entirety}')"name='ids' value="${list.id }" id="${list.id }"class="ace"/><span class="lbl"></span></label></c:if>
                                                            <c:if test="${user.USERNAME == 'admin'}"><label><input type='checkbox' disabled="disabled" class="ace" /><span class="lbl"></span></label></c:if>
                                                        </c:if>
                                                    </td>
                                                    <td class='center' style="width: 30px;">${page.showCount*(page.currentPage-1)+vs.index+1}</td>
                                                    <td class="center">${list.pore_plate_name }</td>
                                                    <td class="center">${list.project_name } <input type="hidden" value="${list.projectID }" id="project${num}"/><input type="hidden" id="number${num}" value="${list.projectID }"/></td>
                                                    <td class="center" id="count">${list.COUNT }</td>
                                                    <td class="center">
                                                        <c:if test="${list.entirety == 1}">是</c:if>
                                                        <c:if test="${list.entirety == 2}">否</c:if>
                                                        <c:if test="${list.entirety == 3}">待定</c:if>
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                        <%--</c:if>--%>
                                        <%--<c:if test="${QX.cha == 0 }">
                                            <tr>
                                                <td colspan="10" class="center">您无权查看</td>
                                            </tr>
                                        </c:if>--%>
                                    </c:when>
                                    <c:otherwise>
                                        <tr class="main_info">
                                            <td colspan="10" class="center">没有相关数据</td>
                                        </tr>
                                    </c:otherwise>
                                </c:choose>
                                </tbody>
                            </table>
                            <input type="hidden" id="Id"/>
                            <div class="page-header position-relative">
                                <table style="width:100%;">
                                    <tr>
                                        <td style="vertical-align:top;">
                                           <%-- <c:if test="${QX.adds == 1}">--%>
                                                <a class="btn btn-mini btn-success" onclick="rebuild();">重扩</a>
                                            <%--</c:if>--%>
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
    
    function change(obj) {
            // 判断是否选中复选框
            var count = 0
            var checkArray = document.getElementsByName("ids")
            var sum = 0;
            for (var i = 0; i < checkArray.length; i++) {
                if (checkArray[i].checked == true) {
                    $("#sampleofSum").css("display", "block");
                    var id = $("#tr" + (i + 1)).children("td").eq(4).text();
                    var value = parseInt(id);
                    sum += value;
                    count++;
                }
            }
            $("#sum").val(sum);
            if (count == 0) {
                $("#sampleofSum").css("display", "none");
            }
    }
    //检索
    function searchs(){
        top.jzts();
        $("#userForm").submit();
    }
    //重扩
    function rebuild(){

        var checkArray = document.getElementsByName("ids")
        var arr= [];
        for ( var i = 0; i < checkArray.length; i++) {
            if(checkArray[i].checked == true){
                 var id =  $("#project"+(i+2)).val();
                 arr.push(id);
            }
        }
        // 获取孔板名称
        for ( var i = 0; i < checkArray.length; i++) {
            if(checkArray[i].checked == true){
                var name = $("#tr" + (i + 1)).children("td").eq(2).text();
            }
        }
        // 判断是否有相同元素
     /*   var nary = arr.sort();
        for(var i = 0;i<arr.length;i++){
            if (nary[i] == nary[i+1]){
                alert("相同项目名");
                return;
            }
        }*/
        //判断用户身份
        /*var kindID = '';
        for ( var i = 0; i < checkArray.length; i++) {
            if(checkArray[i].checked == true){
                kindID =  $("#kind"+(i+2)).val();
            }
        }*/
        //获取项目编号id
        var number = ''
        for ( var i = 0; i < checkArray.length; i++) {
            if(checkArray[i].checked == true){
                number =  $("#number"+(i+2)).val();
            }
        }
        /*if(kindID == 3){
            alert("权限不够！");
            return;
        }*/
        // 获取孔板重扩总数，判断是否超过94
        var sum = $("#sum").val();
        if (sum > "94" || sum > 94){
            alert("孔板重扩数量不能超过94个！")
            return;
        }
        // 获取是否重扩
        var arry = [];
        var first = 0;
        var entiretyIdFirst;
        for ( var i = 0; i < checkArray.length; i++) {
            if(checkArray[i].checked == true){
                if(first == 0){
                    entiretyIdFirst = $("#tr" + (i + 1)).children("td").eq(5).text();
                    first++
                    continue;
                }
               var entiretyId = $("#tr" + (i + 1)).children("td").eq(5).text();
                // 第一个为是，判断length
                if(entiretyIdFirst.indexOf("是")>0){
                    if(checkArray.length > 0){
                        alert("不能有多条整版重扩");
                        return;
                    }
                }if(entiretyIdFirst.indexOf("否")>0){
                    if(entiretyId.indexOf("是")>0){
                        alert("整版重扩与非整版重扩不能同时重扩");
                        return;
                    }
                }if(entiretyIdFirst.indexOf("否")>0){
                    if(entiretyId.indexOf("否")>0){
                        var nary = arr.sort();
                        for(var i = 0;i<nary.length-1;i++){
                        if (nary[i] == nary[i+1]){
                             }
                             else {
                            alert("不是相同项目名");
                            return;
                             }
                        }
                        //return;
                    }
                }
                arry.push(entiretyId);
                break;
            }
        }
        var mun = $("#sum").val();
        if($("#sum").val() == 0|| mun == "0"){
            $("#sum").tips({
                side:3,
                msg:'重扩样本总数不能为零 ',
                bg:'#AE81FF',
                time:3
            });
            $("#sum").focus();
            return false;
        }

        var ids = makeAll();
        //alert(ids);
        // 获取重获轮数
        var lims_pore_serial = $("#lims_pore_serial").val();
        top.jzts();
        var diag = new top.Dialog();
        diag.Drag=true;
        diag.Title ="重扩";
        diag.URL = '<%=basePath%>rebuild/goAddU.do?mun='+mun+'&pore_plate_name='+name+'&lims_pore_serial='+lims_pore_serial+'&ids='+ids+'&projectNumber='+number;
        diag.Width = 469;
        diag.Height = 130;
        diag.CancelEvent = function(){ //关闭事件
            if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
                if('${page.currentPage}' == '0'){
                    top.jzts();
                    setTimeout("self.location =  self.location",100);
                }else{
                    nextPage(${page.currentPage});
                }
            }
            diag.close();
        };
        diag.show();
    }

    function makeAll(){
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
            }
        }
        return str;
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
                var count = 0
                if(th_checked){
                    $(row).addClass(active_class).find('input[type=checkbox]').eq(0).prop('checked', true);
                    var checkArray = document.getElementsByName("ids")
                    var sum = 0;
                    for (var i = 0; i < checkArray.length; i++) {
                        if (checkArray[i].checked == true) {
                            $("#sampleofSum").css("display", "block");
                            var id = $("#tr" + (i + 1)).children("td").eq(4).text();
                            var value = parseInt(id);
                            sum += value;
                            count++;
                        }
                    }
                    $("#sum").val(sum);
                }
                else {
                    $(row).removeClass(active_class).find('input[type=checkbox]').eq(0).prop('checked', false);
                    if (count == 0) {
                        $("#sampleofSum").css("display", "none");
                    }
                }
            });
        });
    });
</script>
</html>
