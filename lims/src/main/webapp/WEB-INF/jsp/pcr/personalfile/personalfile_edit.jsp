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
    <!-- 下拉框 -->
    <link rel="stylesheet" href="static/ace/css/chosen.css"/>
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
                        <form action="manual/${msg}.do" name="Form" id="Form" method="post"  enctype="multipart/form-data">
                            <input type="hidden" name="ID" id="ID" value="${pd.id}"/>
                            <div id="zhongxin" style="padding-top: 13px;">
                                <table id="table_report" class="table table-striped table-bordered table-hover">
                                    <tr>
                                        <td style="width:79px;text-align: right;padding-top: 13px;">上传:</td>
                                        <td style="vertical-align:top;"   style="width:98%;"><input type="file" id="personalfile" name="personalfile"  /></td>
                                    </tr>
                                    <tr>
                                        <td style="width:79px;text-align: right;padding-top: 13px;">文件说明:</td>
                                        <td style="vertical-align:top;"   style="width:98%;">
                                            <textarea name="lims_personalfile_explain" id="lims_personalfile_explain" cols="45" rows="4" onkeyup="checkLength(this, 230);"> </textarea>
                                        </td>
                                    </tr>

                                    <tr>
                                        <td style="text-align: center;" colspan="10">
                                            <a class="btn btn-mini btn-primary" onclick="save();">保存</a>
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
<!-- /.main-container -->
<!-- basic scripts -->
<!-- 页面底部js¨ -->
<%@ include file="../../system/index/foot.jsp"%>
<!-- ace scripts -->
<script src="static/ace/js/ace/ace.js"></script>
<!-- 上传控件 -->
<script src="static/ace/js/ace/elements.fileinput.js"></script>
<!-- inline scripts related to this page -->
<!-- 下拉框 -->
<script src="static/ace/js/chosen.jquery.js"></script>
<!--提示框-->
<script type="text/javascript" src="static/js/jquery.tips.js"></script>
</body>
<script type="text/javascript">
    $(top.hangge());
    $(document).ready(function(){
        $('#form-field-select-4').addClass('tag-input-style');
        if($("#user_id").val()!=""){
            $("#loginname").attr("readonly","readonly");
            $("#loginname").css("color","gray");
        }
    });

    $(function() {
        //上传
        $('#personalfile').ace_file_input({
            no_file:'请选择 ...',
            btn_choose:'选择',
            btn_change:'更改',
            droppable:false,
            onchange:null,
            thumbnail:false, //| true | large
            whitelist:'pdf|pff',
            blacklist:'gif|png|jpg|jpeg'
            //onchange:''
        });
    });
    //保存
    function save(){
        if($("#personalfile").val()==""){
            $("#personalfile").tips({
                side:3,
                msg:'请选择文件',
                bg:'#AE81FF',
                time:3
            });
            return false;
        }
        $("#Form").submit();
        $("#zhongxin").hide();
        $("#zhongxin2").show();
    }

    //项目名称汉字不能超过140个
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


</script>
</html>