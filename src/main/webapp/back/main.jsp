<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
    <!--bootstrapcss样式-->
    <link rel="stylesheet" href="../statics/boot/css/bootstrap.min.css">
    <!--引入jquery核心js-->
    <script src="../statics/boot/js/jquery-3.3.1.min.js"></script>
    <!--引入bootstrap核心js-->
    <script src="../statics/boot/js/bootstrap.min.js"></script>
    <%--引入jqgrid核心基础样式--%>
    <link rel="stylesheet" href="../statics/jqgrid/css/trirand/ui.jqgrid.css">
    <%--引入jqgrid的bootstra皮肤--%>
    <link rel="stylesheet" href="../statics/jqgrid/css/trirand/ui.jqgrid-bootstrap.css">
    <%--引入jqgrid核心js--%>
    <script src="../statics/jqgrid/js/trirand/jquery.jqGrid.min.js"></script>
    <%--引入i18njs--%>
    <script src="../statics/jqgrid/js/trirand/i18n/grid.locale-cn.js"></script>
    <script src="../statics/jqgrid/js/ajaxfileupload.js"></script>
    <script charset="utf-8" src="../kindeditor/kindeditor-all-min.js"></script>
    <script charset="utf-8" src="../kindeditor/lang/zh-CN.js"></script>
    <script charset="utf-8" src="../echarts/echarts.min.js"></script>

    <script>
        $(function () {
            $("#a").click(function () {
                $.ajax({
                    url: "${pageContext.request.contextPath}/admin/clear",
                    type: "GET",
                    success: function () {
                        location.href = "${pageContext.request.contextPath}/login/login.jsp"
                    }
                })
            })
        })
    </script>
    <style>
        body {
            padding-top: 70px;
        }
    </style>

</head>
<body>
<!--创建导航-->
<nav class="navbar navbar-inverse navbar-fixed-top">
    <!--容器-->
    <div class="container-fluid">
        <!--导航标题-->
        <div class="navbar-header">
            <div class="navbar-brand"><a href="">持明法州管理系统<small>v1.0</small></a></div>
        </div>
        <ul class="nav navbar-nav navbar-right">
            <li><a href="">欢迎:${sessionScope.loginAdmin.username}</a></li>
            <li>
                <a href="" id="a">退出登陆 <span class="glyphicon glyphicon-log-out"></span> </a>
            </li>
        </ul>
    </div>
</nav>
<!--页面主体内容-->
<div class="container-fluid">
    <!--row-->
    <div class="row">

        <!--菜单-->
        <div class="col-sm-2">

            <!--手风琴控件-->
            <div class="panel-group" id="accordion">

                <!--面板-->
                <div class="panel panel-default">
                    <div class="panel-heading" role="tab" id="userPanel">
                        <h4 class="panel-title text-center">
                            <a role="button" data-toggle="collapse" data-parent="#accordion" href="#userLists"
                               aria-expanded="true" aria-controls="collapseOne">
                                轮播图管理
                            </a>
                        </h4>
                    </div>
                    <div id="userLists" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingOne">
                        <div class="panel-body text-center">
                            <%--<li class="list-group-item"><a href="javascript:$('#centerLayout').load('${pageContext.request.contextPath}/banner/lists_s.jsp');" id="btn">全部轮播图</a></li>--%>
                            <%--<button type="button" class="btn btn-default  btn-smk">全部轮播图</button>--%>
                            <a href="javascript:$('#centerLayout').load('${pageContext.request.contextPath}/banner/lists_s.jsp')"
                               class="btn btn-default">全部轮播图</a>
                        </div>

                    </div>
                </div>

                <!--类别面板-->
                <div class="panel panel-default">
                    <div class="panel-heading" role="tab" id="categoryPanel">
                        <h4 class="panel-title text-center">
                            <a role="button" data-toggle="collapse" data-parent="#accordion" href="#categoryLists"
                               aria-expanded="true" aria-controls="collapseOne">
                                专辑管理
                            </a>
                        </h4>
                    </div>
                    <div id="categoryLists" class="panel-collapse collapse" role="tabpanel"
                         aria-labelledby="headingOne">
                        <div class="panel-body text-center">
                            <a href="javascript:$('#centerLayout').load('${pageContext.request.contextPath}/album/album-show.jsp')"
                               class="btn btn-default">全部专辑</a>
                        </div>
                    </div>
                </div>

                <!--面板-->
                <div class="panel panel-default">
                    <div class="panel-heading" role="tab" id="bookPanel">
                        <h4 class="panel-title text-center">
                            <a role="button" data-toggle="collapse" data-parent="#accordion" href="#bookLists"
                               aria-expanded="true" aria-controls="collapseOne">
                                文章管理
                            </a>
                        </h4>
                    </div>
                    <div id="bookLists" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingOne">
                        <div class="panel-body text-center">
                            <a href="javascript:$('#centerLayout').load('${pageContext.request.contextPath}/article/article-show.jsp')"
                               class="btn btn-default">所有文章</a>
                        </div>
                    </div>
                </div>


                <!--面板-->
                <div class="panel panel-default">
                    <div class="panel-heading" role="tab" id="orderPanel">
                        <h4 class="panel-title text-center">
                            <a role="button" data-toggle="collapse" data-parent="#accordion" href="#orderLists"
                               aria-expanded="true" aria-controls="collapseOne">
                                用户管理
                            </a>
                        </h4>
                    </div>
                    <div id="orderLists" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingOne">
                        <div class="panel-body text-center">
                            <a href="javascript:$('#centerLayout').load('${pageContext.request.contextPath}/user/user-show.jsp')"
                               class="btn btn-default">所有用户</a>
                            <a href="javascript:$('#centerLayout').load('${pageContext.request.contextPath}/user/echarts.jsp')"
                               class="btn btn-default">用户添加趋势</a>
                        </div>
                    </div>
                </div>
                <!--面板-->
                <div class="panel panel-default">
                    <div class="panel-heading" role="tab" id="headingFive">
                        <h4 class="panel-title text-center">
                            <a role="button" data-toggle="collapse" data-parent="#accordion" href="#bookListss"
                               aria-expanded="true" aria-controls="collapseOne">
                                明星管理
                            </a>
                        </h4>
                    </div>
                    <div id="bookListss" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingFive">
                        <div class="panel-body text-center">
                            <a href="javascript:$('#centerLayout').load('${pageContext.request.contextPath}/star/star_show.jsp')"
                               class="btn btn-default">所有明星</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-sm-10" id="centerLayout">
            <!--剧目-->
            <div class="jumbotron">
                <p>欢迎来到我的世界</p>
            </div>
            <%--<div class="thumbnail">
                <img src="../img/2.jpg" alt="...">

            </div>--%>
            <div id="carousel-example-generic" class="carousel slide" data-ride="carousel">
                <ol class="carousel-indicators">
                    <li data-target="#carousel-example-generic" data-slide-to="0" class="active"></li>
                    <li data-target="#carousel-example-generic" data-slide-to="1" class=""></li>
                    <li data-target="#carousel-example-generic" data-slide-to="2" class=""></li>
                    <li data-target="#carousel-example-generic" data-slide-to="3" class=""></li>
                    <li data-target="#carousel-example-generic" data-slide-to="4" class=""></li>
                </ol>
                <div class="carousel-inner" role="listbox">
                    <div class="item active">
                        <img data-src="holder.js/900x500/auto/#666:#444/text:Second slide" alt="Second slide [900x500]"
                             src="../img/11.jpg" data-holder-rendered="true" class="img-thumbnail">
                    </div>
                    <div class="item">
                        <img data-src="holder.js/900x500/auto/#666:#444/text:Second slide" alt="Second slide [900x500]"
                             src="../img/22.jpg" data-holder-rendered="true" class="img-thumbnail">
                    </div>
                    <div class="item">
                        <img data-src="holder.js/900x500/auto/#666:#444/text:Second slide" alt="Second slide [900x500]"
                             src="../img/33.jpg" data-holder-rendered="true" class="img-thumbnail">
                    </div>
                    <div class="item">
                        <img data-src="holder.js/900x500/auto/#666:#444/text:Second slide" alt="Second slide [900x500]"
                             src="../img/44.jpg" data-holder-rendered="true" class="img-thumbnail">
                    </div>
                    <div class="item">
                        <img data-src="holder.js/900x500/auto/#666:#444/text:Second slide" alt="Second slide [900x500]"
                             src="../img/55.jpg" data-holder-rendered="true" class="img-thumbnail">
                    </div>
                </div>
                <a class="left carousel-control" href="#carousel-example-generic" role="button" data-slide="prev">
                    <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
                    <span class="sr-only">Previous</span>
                </a>
                <a class="right carousel-control" href="#carousel-example-generic" role="button" data-slide="next">
                    <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
                    <span class="sr-only">Next</span>
                </a>
            </div>

        </div>
    </div>
</div>
</body>
</html>