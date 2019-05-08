<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <base href="<%=basePath%>">
    <meta charset="utf-8" />
    <title></title>
    <meta name="description" content="overview & stats" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <link href="static/css/bootstrap.min.css" rel="stylesheet" />
    <link href="static/css/bootstrap-responsive.min.css" rel="stylesheet" />
    <link rel="stylesheet" href="static/css/font-awesome.min.css" />
    <link rel="stylesheet" href="static/css/ace.min.css" />
    <link rel="stylesheet" href="static/css/ace-responsive.min.css" />
    <link rel="stylesheet" href="static/css/ace-skins.min.css" />
    <script type="text/javascript" src="static/js/jquery-1.7.2.js"></script>

    <script language="javascript" src="static/js/jquery.jqprint-0.3.js"></script>

    <style type="text/css">

        table{
            border-collapse: collapse;/* 边框合并属性  */
        }
        th{
            border: 1px solid #666666;
        }
        td{
            border: 1px solid #666666;
        }

    </style>

</head>
<body>
<div id="zhongxin">
    <table width="1500" height="30" border="0" align="center" cellpadding="0" cellspacing="0" class="table table-striped table-bordered table-hover">
        <tr>
            <td align="center">${pd.pore_plate_name}</td>
            <c:if test="${pd.pore_plate_quality1 == 1}">
                 <td align="center">复核质检☑ </td>
            </c:if>
            <c:if test="${pd.pore_plate_quality1 != 1}">
                <td align="center">复核质检□</td>
            </c:if>
            <td align="center">数据导出:${pd.userName}</td>
        </tr>
        <tr>
            <td align="center"></td>
            <td align="center">重复样本□</td>
            <td align="center">审核人:${pd.name1}</td>
        </tr>
    </table>
    <table id="simple-table" class="table table-striped table-bordered table-hover"  style="margin-top:5px;">
        <thead>
        <%--A1-A6--%>
        <tr>
            <c:forEach var="x" begin="1" end="6" step="1">
                <c:if test="${x < 10}">
                    <th class="center tableW" >A0${x}</th>

                </c:if>
                <c:if test="${x>=10}">
                    <th class="center tableW">A${x}</th>
                </c:if>

                <c:if test="${msg != 'editPorePlate'}">
                    <c:if test="${x == 1}">
                        <th class="center tableW">
                            <input style="width: 100%; " type="text" id="a${x}" value="LADDER">
                        </th>
                    </c:if>
                </c:if>
                <c:if test="${msg == 'editPorePlate'}">
                    <c:if test="${x == 1}">
                        <th class="center tableW">
                            <input style="width: 100%; " type="text" id="a${x}" value="">
                        </th>
                    </c:if>
                </c:if>
                <c:if test="${x != 1}">
                    <th class="center tableW">
                        <input style="width: 100%; " type="text" id="a${x}" value="">
                    </th>
                </c:if>

            </c:forEach>
        </tr>

        <%--A1-A6--%>

        <%--B1-B6--%>
        <tr>
            <c:forEach var="x" begin="1" end="6" step="1">
                <c:if test="${x < 10}">
                    <th class="center tableW">B0${x}</th>
                </c:if>
                <c:if test="${x>=10}">
                    <th class="center tableW">B${x}</th>
                </c:if>
                <th class="center tableW">
                    <input style="width: 100%;" type="text" id="b${x}" value="">
                </th>
            </c:forEach>
        </tr>

        <%--B1-B6--%>
        <%--C1-C6--%>
        <tr>
            <c:forEach var="x" begin="1" end="6" step="1">
                <c:if test="${x < 10}">
                    <th class="center tableW">C0${x}</th>
                </c:if>
                <c:if test="${x>=10}">
                    <th class="center tableW">C${x}</th>
                </c:if>
                <th class="center tableW">
                    <input style="width: 100%;" type="text" id="c${x}" value="">
                </th>
            </c:forEach>
        </tr>

        <%--C1-C6--%>
        <%--D1-D6--%>
        <tr>
            <c:forEach var="x" begin="1" end="6" step="1">

                <c:if test="${x < 10}">
                    <th class="center tableW">D0${x}</th>
                </c:if>
                <c:if test="${x>=10}">
                    <th class="center tableW">D${x}</th>
                </c:if>
                <c:if test="${x==5}">
                    <th class="center tableW">
                        <input style="width: 100%;" type="text" id="d${x}" value="">
                    </th>
                </c:if>
                <c:if test="${x!=5}">
                    <th class="center tableW">
                        <input style="width: 100%;" type="text" id="d${x}" value="">
                    </th>
                </c:if>
            </c:forEach>
        </tr>

        <%--D1-D6--%>
        <%--E1-E6--%>
        <tr>
            <c:forEach var="x" begin="1" end="6" step="1">
                <c:if test="${x < 10}">
                    <th class="center tableW">E0${x}</th>
                </c:if>
                <c:if test="${x>=10}">
                    <th class="center tableW">E${x}</th>
                </c:if>
                <c:if test="${x==8}">
                    <th class="center tableW">
                        <input style="width: 100%;" type="text" id="e${x}" value="">
                    </th>
                </c:if>
                <c:if test="${x!=8 && x!=12}">
                    <th class="center tableW">
                        <input style="width: 100%;" type="text" id="e${x}" value="">
                    </th>
                </c:if>
                <c:if test="${msg == 'editPorePlate'}">
                    <c:if test="${x==12}">
                        <th class="center tableW">
                            <input style="width: 100%;" type="text" id="e${x}" value="">
                        </th>
                    </c:if>
                </c:if>
                <c:if test="${msg != 'editPorePlate'}">
                    <c:if test="${x==12}">
                        <th class="center tableW">
                            <input style="width: 100%;" type="text" id="e${x}" value="">
                        </th>
                    </c:if>
                </c:if>
            </c:forEach>
        </tr>

        <%--E1-E6--%>
        <%--F1-F6--%>
        <tr>
            <c:forEach var="x" begin="1" end="6" step="1">
                <c:if test="${x < 10}">
                    <th class="center tableW">F0${x}</th>
                </c:if>
                <c:if test="${x>=10}">
                    <th class="center tableW">F${x}</th>
                </c:if>
                <c:if test="${x!=12}">
                    <th class="center tableW">
                        <input style="width: 100%;" type="text"  id="f${x}" value="">
                    </th>
                </c:if>
                <c:if test="${msg == 'editPorePlate'}">
                    <c:if test="${x==12}">
                        <th class="center tableW">
                            <input style="width: 100%;" type="text"  id="f${x}" value="">
                        </th>
                    </c:if>
                </c:if>
                <c:if test="${msg != 'editPorePlate'}">
                    <c:if test="${x==12}">
                        <th class="center tableW">
                            <input style="width: 100%;" type="text"  id="f${x}" value="">
                        </th>
                    </c:if>
                </c:if>
            </c:forEach>
        </tr>

        <%--F1-F6--%>
        <%--G1-G6--%>
        <tr>
            <c:forEach var="x" begin="1" end="6" step="1">
                <c:if test="${x < 10}">
                    <th class="center tableW">G0${x}</th>
                </c:if>
                <c:if test="${x>=10}">
                    <th class="center tableW">G${x}</th>
                </c:if>
                <c:if test="${x!=12}">
                    <th class="center tableW">
                        <input style="width: 100%;" type="text" id="g${x}" value="">
                    </th>
                </c:if>
                <c:if test="${msg == 'editPorePlate'}">
                    <c:if test="${x==12}">
                        <th class="center tableW">
                            <input style="width: 100%;" type="text" id="g${x}" value="">
                        </th>
                    </c:if>
                </c:if>
                <c:if test="${msg != 'editPorePlate'}">
                    <c:if test="${x==12}">
                        <th class="center tableW">
                            <input style="width: 100%;" type="text" id="g${x}" value="">
                        </th>
                    </c:if>
                </c:if>
            </c:forEach>
        </tr>

        <%--G1-G6--%>
        <%--H1-H6--%>
        <tr>
            <c:forEach var="x" begin="1" end="6" step="1">
                <c:if test="${x < 10}">
                    <th class="center tableW">H0${x}</th>
                </c:if>
                <c:if test="${x>=10}">
                    <th class="center tableW">H${x}</th>
                </c:if>
                <c:if test="${msg == 'editPorePlate'}">
                    <c:if test="${x==6}">
                        <th class="center tableW">
                            <input style="width: 100%;" type="text" id="h${x}" value="">
                        </th>
                    </c:if>
                </c:if>
                <c:if test="${msg != 'editPorePlate'}">
                    <c:if test="${x==6}">
                        <th class="center tableW">
                            <input style="width: 100%;" type="text" id="h${x}" value="LADDER">
                        </th>
                    </c:if>
                </c:if>
                <c:if test="${x!=6 && x!=12}">
                    <th class="center tableW">
                        <input style="width: 100%;" type="text" id="h${x}" value="">
                    </th>
                </c:if>
                <c:if test="${msg == 'editPorePlate'}">
                    <c:if test="${x==12}">
                        <th class="center tableW">
                            <input style="width: 100%;" type="text" id="h${x}" value="">
                        </th>
                    </c:if>
                </c:if>
                <c:if test="${msg != 'editPorePlate'}">
                    <c:if test="${x==12}">
                        <th class="center tableW">
                            <input style="width: 100%;" type="text" id="h${x}" value="">
                        </th>
                    </c:if>
                </c:if>
            </c:forEach>
        </tr>
        <tr> <th class="center tableW">
        </th></tr>
        <%--H1-H6--%>


        </thead>
        <tr> <th class="center tableW">
        </th></tr>
        <thead>
        <%--A7-A12--%>
        <tr>
            <c:forEach var="x" begin="7" end="12" step="1">
                <c:if test="${x < 10}">
                    <th class="center tableW" >A0${x}</th>

                </c:if>
                <c:if test="${x>=10}">
                    <th class="center tableW">A${x}</th>
                </c:if>

                <c:if test="${msg != 'editPorePlate'}">
                    <c:if test="${x == 1}">
                        <th class="center tableW">
                            <input style="width: 100%; " type="text" id="a${x}" value="LADDER">
                        </th>
                    </c:if>
                </c:if>
                <c:if test="${msg == 'editPorePlate'}">
                    <c:if test="${x == 1}">
                        <th class="center tableW">
                            <input style="width: 100%; " type="text" id="a${x}" value="">
                        </th>
                    </c:if>
                </c:if>
                <c:if test="${x != 1}">
                    <th class="center tableW">
                        <input style="width: 100%; " type="text" id="a${x}" value="">
                    </th>
                </c:if>

            </c:forEach>
        </tr>

        <%--A7-A12--%>
        <%--B7-B12--%>
        <tr>
            <c:forEach var="x" begin="7" end="12" step="1">
                <c:if test="${x < 10}">
                    <th class="center tableW">B0${x}</th>
                </c:if>
                <c:if test="${x>=10}">
                    <th class="center tableW">B${x}</th>
                </c:if>
                <th class="center tableW">
                    <input style="width: 100%;" type="text" id="b${x}" value="">
                </th>
            </c:forEach>
        </tr>

        <%--B7-B12--%>
        <%--C7-C12--%>
        <tr>
            <c:forEach var="x" begin="7" end="12" step="1">
                <c:if test="${x < 10}">
                    <th class="center tableW">C0${x}</th>
                </c:if>
                <c:if test="${x>=10}">
                    <th class="center tableW">C${x}</th>
                </c:if>
                <th class="center tableW">
                    <input style="width: 100%;" type="text" id="c${x}" value="">
                </th>
            </c:forEach>
        </tr>

        <%--C7-C12--%>
        <%--D7-D12--%>
        <tr>
            <c:forEach var="x" begin="7" end="12" step="1">

                <c:if test="${x < 10}">
                    <th class="center tableW">D0${x}</th>
                </c:if>
                <c:if test="${x>=10}">
                    <th class="center tableW">D${x}</th>
                </c:if>
                <c:if test="${x==5}">
                    <th class="center tableW">
                        <input style="width: 100%;" type="text" id="d${x}" value="">
                    </th>
                </c:if>
                <c:if test="${x!=5}">
                    <th class="center tableW">
                        <input style="width: 100%;" type="text" id="d${x}" value="">
                    </th>
                </c:if>
            </c:forEach>
        </tr>

        <%--D7-D12--%>
        <%--E7-E12--%>
        <tr>
            <c:forEach var="x" begin="7" end="12" step="1">
                <c:if test="${x < 10}">
                    <th class="center tableW">E0${x}</th>
                </c:if>
                <c:if test="${x>=10}">
                    <th class="center tableW">E${x}</th>
                </c:if>
                <c:if test="${x==8}">
                    <th class="center tableW">
                        <input style="width: 100%;" type="text" id="e${x}" value="">
                    </th>
                </c:if>
                <c:if test="${x!=8 && x!=12}">
                    <th class="center tableW">
                        <input style="width: 100%;" type="text" id="e${x}" value="">
                    </th>
                </c:if>
                <c:if test="${msg == 'editPorePlate'}">
                    <c:if test="${x==12}">
                        <th class="center tableW">
                            <input style="width: 100%;" type="text" id="e${x}" value="">
                        </th>
                    </c:if>
                </c:if>
                <c:if test="${msg != 'editPorePlate'}">
                    <c:if test="${x==12}">
                        <th class="center tableW">
                            <input style="width: 100%;" type="text" id="e${x}" value="O">
                        </th>
                    </c:if>
                </c:if>
            </c:forEach>
        </tr>

        <%--E7-E12--%>
        <%--F7-F12--%>
        <tr>
            <c:forEach var="x" begin="7" end="12" step="1">
                <c:if test="${x < 10}">
                    <th class="center tableW">F0${x}</th>
                </c:if>
                <c:if test="${x>=10}">
                    <th class="center tableW">F${x}</th>
                </c:if>
                <c:if test="${x!=12}">
                    <th class="center tableW">
                        <input style="width: 100%;" type="text" id="f${x}" value="">
                    </th>
                </c:if>
                <c:if test="${msg == 'editPorePlate'}">
                    <c:if test="${x==12}">
                        <th class="center tableW">
                            <input style="width: 100%;" type="text"  id="f${x}" value="">
                        </th>
                    </c:if>
                </c:if>
                <c:if test="${msg != 'editPorePlate'}">
                    <c:if test="${x==12}">
                        <th class="center tableW">
                            <input style="width: 100%;" type="text"  id="f${x}" value="P">
                        </th>
                    </c:if>
                </c:if>
            </c:forEach>
        </tr>

        <%--F7-F12--%>
        <%--G7-G12--%>
        <tr>
            <c:forEach var="x" begin="7" end="12" step="1">
                <c:if test="${x < 10}">
                    <th class="center tableW">G0${x}</th>
                </c:if>
                <c:if test="${x>=10}">
                    <th class="center tableW">G${x}</th>
                </c:if>
                <c:if test="${x!=12}">
                    <th class="center tableW">
                        <input style="width: 100%;" type="text" id="g${x}" value="">
                    </th>
                </c:if>
                <c:if test="${msg == 'editPorePlate'}">
                    <c:if test="${x==12}">
                        <th class="center tableW">
                            <input style="width: 100%;" type="text" id="g${x}" value="">
                        </th>
                    </c:if>
                </c:if>
                <c:if test="${msg != 'editPorePlate'}">
                    <c:if test="${x==12}">
                        <th class="center tableW">
                            <input style="width: 100%;" type="text" id="g${x}" value="">
                        </th>
                    </c:if>
                </c:if>
            </c:forEach>
        </tr>

        <%--G7-G12--%>
        <%--H7-H12--%>
        <tr>
            <c:forEach var="x" begin="7" end="12" step="1">
                <c:if test="${x < 10}">
                    <th class="center tableW">H0${x}</th>
                </c:if>
                <c:if test="${x>=10}">
                    <th class="center tableW">H${x}</th>
                </c:if>
                <c:if test="${msg == 'editPorePlate'}">
                    <c:if test="${x==6}">
                        <th class="center tableW">
                            <input style="width: 100%;" type="text" id="h${x}" value="">
                        </th>
                    </c:if>
                </c:if>
                <c:if test="${msg != 'editPorePlate'}">
                    <c:if test="${x==6}">
                        <th class="center tableW">
                            <input style="width: 100%;" type="text" id="h${x}" value="LADDER">
                        </th>
                    </c:if>
                </c:if>
                <c:if test="${x!=6 && x!=12}">
                    <th class="center tableW">
                        <input style="width: 100%;" type="text" id="h${x}" value="">
                    </th>
                </c:if>
                <c:if test="${msg == 'editPorePlate'}">
                    <c:if test="${x==12}">
                        <th class="center tableW">
                            <input style="width: 100%;" type="text" id="h${x}" value="">
                        </th>
                    </c:if>
                </c:if>
                <c:if test="${msg != 'editPorePlate'}">
                    <c:if test="${x==12}">
                        <th class="center tableW">
                            <input style="width: 100%;" type="text" id="h${x}" value="">
                        </th>
                    </c:if>
                </c:if>
            </c:forEach>
        </tr>

        <%--H7-H12--%>
        </thead>
        <tbody>
        </tbody>
    </table>
    <table width="1500" height="30" border="0" align="center" cellpadding="0" cellspacing="0" class="table table-striped table-bordered table-hover">
    <tr>
            <td>打孔人:${pd.dakongren}</td>
            <td>扩增人:${pd.kuozhengren}</td>
            <td>分析人:${pd.fenxiren}</td>
            <td>CODIS导出:</td>
            <td>备注:</td>
        </tr>
    </table>
    <table width="1000" height="30" border="0" align="center" cellpadding="0" cellspacing="0">
        <tr>
            <td align="right"><button onClick="window.print()">打印</button>&nbsp;</td>
        </tr>
    </table>
    <hr/>
    如果点打印按钮无反应,请用google 火狐等浏览器试试


</div>

<div id="zhongxin2" class="center" style="display:none"><br/><br/><br/><br/><br/><img src="static/images/jiazai.gif" /><br/><h4 class="lighter block green">提交中...</h4></div>

<!-- 引入 -->
<script type="text/javascript">window.jQuery || document.write("<script src='static/js/jquery-1.9.1.min.js'>\x3C/script>");</script>
<script src="static/js/bootstrap.min.js"></script>
<script src="static/js/ace-elements.min.js"></script>
<script src="static/js/ace.min.js"></script>

<script type="text/javascript">
    $(document).ready(function(){
        if('${poreList}'!=""){
            var poreList = '${poreList}';
            var reg=/=/g;
            poreList = poreList.replace(reg,":");
            var jsonData = eval(poreList);
            //遍历jsonData：
            for (var i = 0; i < jsonData.length; i++) {
                console.log(jsonData[i].hole_number);
                var inputId = jsonData[i].hole_number;
                //将大写转成小写
                inputId = inputId.toLowerCase();
                //获取结尾数字   将01转成1
                var idNum = inputId.substring(1,inputId.length);
                inputId = inputId.substring(0,1);
                idNum = parseInt(idNum);
                //获取最终的input框id
                inputId = inputId+idNum;
                $("#"+inputId).val(jsonData[i].poreNum);
                $("#"+inputId).next().val(jsonData[i].hole_type);
                //如果是复核孔  变黄
                if(jsonData[i].hole_type == "4"){
                    var z = $("#"+inputId).parent().index();
                    $("#"+inputId).parent().parent().prev().children().eq(parseInt(z)).css('background-color','yellow');
                }
                $("#"+inputId).next().next().val(jsonData[i].hole_special_sample);
            }
        }
    });
</script>
</body>
</html>