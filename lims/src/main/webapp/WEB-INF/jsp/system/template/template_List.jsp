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
                        <form action="resources/list.do" method="post" name="userForm" id="userForm">

                            <!-- 检索  -->
                            <table id="simple-table" class="table table-striped table-bordered table-hover"  style="margin-top:5px;width: 400px;margin: 0 auto">
                                <thead>
                                    <tr>
                                        <td style="width:140px;text-align: right;padding-top: 13px;">选择项目:</td>
                                        <td>
                                            <select class="chosen-select form-control" name="ProjectName" id="ProjectName" onchange="palateName()" style="vertical-align:top;width:98%;" >
                                                <option value='' disabled selected style='display:none;'>请选择项目名</option>
                                                <c:forEach var="list" items="${projectNameList}">
                                                    <option value="${list.id}">${list.project_name}</option>
                                                </c:forEach>
                                            </select>
                                        </td>
                                    </tr>

                                    <tr>
                                        <td style="width:75px;text-align: right;padding-top: 13px;">选择孔板:</td>
                                        <td>
                                            <select name="INSTRUMENT_USER" id="plate" style="vertical-align:top;width:98%;" >
                                                <option value='' disabled selected style='display:none;'>请选择孔板</option>
                                            </select>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td style="width:75px;text-align: right;padding-top: 13px;">选择模板:</td>
                                        <td>
                                            <select class="chosen-select form-control" onchange="changeSelect()" name="INSTRUMENT_USER" id="template" style="vertical-align:top;width:98%;" >
                                                <option value='' disabled selected style='display:none;'>请选择模板</option>
                                                <option value="3130">3130</option>
                                                <option value="3500">3500</option>
                                                <option value="3730">3730</option>
                                            </select>
                                        </td>
                                    </tr>
                                    <tr id="Application" style="display: none">
                                        <td style="width:75px;text-align: right;padding-top: 13px;">Application Type</td>
                                        <td>
                                            <input type="radio" name="HID"id="hid" value="HID" />HID
                                            <input type="radio" name="HID"id="fragment" value="fragment" />fragment
                                        </td>
                                    </tr>
                                    <tr id="Capillary" style="display: none">
                                        <td style="width:75px;text-align: right;padding-top: 13px;">Capillary Length (cm)</td>
                                        <td>
                                            <input type="radio" name="Capillary" id="36" value="36" />36
                                            <input type="radio" name="Capillary" id="50" value="50" />50
                                        </td>
                                    </tr>
                                    <tr id="Polymer" style="display: none">
                                        <td style="width:75px;text-align: right;padding-top: 13px;">Polymer</td>
                                        <td>
                                            <input type="radio" name="Polymer" id="POP4" value="POP4" />POP4
                                            <input type="radio" name="Polymer" id="POP7" value="POP7" />POP7
                                        </td>
                                    </tr>
                                    <tr id="Assay" style="display: none">
                                        <td style="width:79px;text-align: right;padding-top: 13px;">Assay</td>
                                        <td>
                                            <input type="text" name="Assay"  id="assa_y" style="width:98%;" placeholder="必填选项" maxlength="100"onkeyup="this.value=this.value.replace(/[^\w_-]/g,'');">
                                        </td>
                                    </tr>
                                    <tr id="FileName" style="display: none">
                                        <td style="width:79px;text-align: right;padding-top: 13px;">File Name</td>
                                        <td>
                                            <input type="text" name="FileName"  id="file_name" style="width:98%;" placeholder="必填选项" maxlength="100"onkeyup="this.value=this.value.replace(/[^\w_-]/g,'');">
                                        </td>
                                    </tr>
                                    <tr id="ResultsGroup" style="display: none">
                                        <td style="width:79px;text-align: right;padding-top: 13px;">Results Group</td>
                                        <td>
                                            <input type="text" name="ResultsGroup"  id="Results_Group" style="width:98%;" placeholder="必填选项" maxlength="100"onkeyup="this.value=this.value.replace(/[^\w_-]/g,'');">
                                        </td>
                                    </tr>
                                    <tr id="Group">
                                        <td style="width:79px;text-align: right;padding-top: 13px;">results group</td>
                                        <td>
                                            <input type="text" name="results group" id="results_group1" style="width:98%;"placeholder="必填选项" maxlength="100"  onkeyup="this.value=this.value.replace(/[^\w_-]/g,'');">
<%--
                                            <input type="text" name="results group" id="results_group1" style="width:98%;"placeholder="必填选项" maxlength="100" onkeyup="value=value.replace(/[^\a-\z\A-\Z0-9]/g,'')" onpaste="value=value.replace(/[^\a-\z\A-\Z0-9]/g,'')" oncontextmenu = "value=value.replace(/[^\a-\z\A-\Z0-9]/g,'')">
--%>
                                        </td>
                                    </tr>
                                    <tr id="instrument">
                                        <td style="width:79px;text-align: right;padding-top: 13px;">instrument_protocol</td>
                                        <td>
                                            <input type="text" name="instrument" id="instrument_protocol" style="width:98%;"placeholder="必填选项" maxlength="100" onkeyup="this.value=this.value.replace(/[^\w_-]/g,'');">
                                        </td>
                                    </tr>
                                </thead>
                            </table>
                            <input type="hidden" id="Id"/>
                            <div style="display: flex;justify-content: center;margin-top: 20px">
                                <a class="btn btn-mini btn-success"style="width: 100px" onclick="add();">导出</a>
                            </div>
                           <%-- <div class="page-header position-relative" style="margin: 0 auto;position: relative;margin: auto">
                                <table style="width:100%;">
                                    <tr>
                                        <td style="vertical-align:top;">
                                                <a class="btn btn-mini btn-success"style="margin-left: 475px;width: 100px" onclick="add();">导出</a>
                                        </td>
                                        <td style="vertical-align:top;"><div class="pagination" style="float: right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div></td>
                                    </tr>
                                </table>
                            </div>--%>
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

    /*下拉框联动*/
    function palateName() {
        $.ajax({
            type:"POST",
            url:'<%=basePath%>template/getPalateName.do',
            data:{projectId : $("#ProjectName").val()},
            dataType:'json',
            cache: false,
            success:function(data){
                $("#plate").empty();
                $.each(data.list,function (i,list) {
                    $("#plate").append("<option value='"+list.id+"'>"+list.pore_plate_name+"</option>");
                    /*$("#plate").html("<option value='"+list.id+"'>"+list.pore_plate_name+"</option>");*/
                });
            }
        });
    }



    //文本导出
    function add(){
        var template = $("#template").val();
        var HID = null; // Application Type 单选框值
        var obj = document.getElementsByName("HID");
        for (var i = 0; i < obj.length; i++) { //遍历Radio
            if (obj[i].checked) {
                HID = obj[i].value;
            }
        }
        var Capillary = null; // Capillary Length (cm)单选框的值
        var obj = document.getElementsByName("Capillary");
        for (var i = 0; i < obj.length; i++) { //遍历Radio
            if (obj[i].checked) {
                Capillary = obj[i].value;
            }
        }
        var Polymer = null; //Polymer单选框值
        var obj = document.getElementsByName("Polymer");
        for (var i = 0; i < obj.length; i++) { //遍历Radio
            if (obj[i].checked) {
                Polymer = obj[i].value;
            }
        }
        if (template == "3130"){
            var plate = $("#plate").val(); // 孔板id
            var plateName = $("#plate option:selected").text(); // 孔板名称
            if (!plate || plate == null) {
                alert("孔板不能为空!");
                return;
            }
            var results_group1 = $("#results_group1").val(); // results group
            if (!results_group1){
                alert("输入框不能为空!");
                return;
            }
            var instrument_protocol = $("#instrument_protocol").val(); // instrument_protocol
            if (!instrument_protocol){
                alert("输入框不能为空!");
                return;
            }
             window.location.href='<%=basePath%>template/export.do?plate='+plate+'&results_group1='+results_group1+'&instrument_protocol='+instrument_protocol+'&template='+template+'&plateName='+plateName;
        }
        if (template == "3500"){
            var plate = $("#plate").val(); // 孔板id
            var plateName = $("#plate option:selected").text(); // 孔板名称
            if (!plate || plate == null) {
                alert("孔板不能为空!");
                return;
            }
            HID;
            Capillary;
            Polymer;
            var assay = $("#assa_y").val();
            if (!assay){
                alert("输入框不能为空!");
                return;
            }
            var file_name = $("#file_name").val();
            if (!file_name){
                alert("输入框不能为空!");
                return;
            }
            var Results_Group = $("#Results_Group").val();
            if (!Results_Group){
                alert("输入框不能为空!");
                return;
            }
             window.location.href='<%=basePath%>template/export.do?HID='+HID+'&Capillary='+Capillary+'&Polymer='+Polymer+'&assay='+assay+'&file_name='+file_name+'&Results_Group='+Results_Group+'&plate='+plate+'&template='+template+'&plateName='+plateName;
        }
        if (template == "3730"){
            var plate = $("#plate").val(); // 孔板id
            var plateName = $("#plate option:selected").text(); // 孔板名称
            if (!plate || plate == null) {
                alert("孔板不能为空!");
                return;
            }
            var results_group1 = $("#results_group1").val(); // results group
            if (!results_group1){
                alert("results_group1输入框不能为空!");
                return;
            }
            var instrument_protocol = $("#instrument_protocol").val(); // instrument_protocol
            if(!instrument_protocol){
                alert("instrument_protocol输入框不能为空!");
                return;
            }
            window.location.href='<%=basePath%>template/export.do?plate='+plate+'&results_group1='+results_group1+'&instrument_protocol='+instrument_protocol+'&template='+template+'&plateName='+plateName;
        }
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

    function changeSelect() {
        var type = $("#template").val();
        if (type == "3130"){
            $("#Assay").hide();
            $("#FileName").hide();
            $("#ResultsGroup").hide();
            $("#Application").hide();
            $("#Capillary").hide();
            $("#Polymer").hide();
            $("#Group").show();
            $("#instrument").show();
        }
        if (type == "3500"){
            $("#Assay").show();
            $("#FileName").show();
            $("#ResultsGroup").show();
            $("#Application").show();
            $("#Capillary").show();
            $("#Polymer").show();
            $("#Group").hide();
            $("#instrument").hide();
        }
        if (type == "3730"){
            $("#Assay").hide();
            $("#FileName").hide();
            $("#ResultsGroup").hide();
            $("#Application").hide();
            $("#Capillary").hide();
            $("#Polymer").hide();
            $("#Group").show();
            $("#instrument").show();
        }
    }
</script>
</html>
