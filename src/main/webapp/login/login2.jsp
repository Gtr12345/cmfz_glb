<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Bootstrap Login Form Template</title>
    <!-- CSS -->
    <link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Roboto:400,100,300,500">
    <link rel="stylesheet" href="assets/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="assets/font-awesome/css/font-awesome.min.css">
    <link rel="stylesheet" href="assets/css/form-elements.css">
    <link rel="stylesheet" href="assets/css/style.css">
    <link rel="shortcut icon" href="assets/ico/favicon.png">
    <link rel="apple-touch-icon-precomposed" sizes="144x144" href="assets/ico/apple-touch-icon-144-precomposed.png">
    <link rel="apple-touch-icon-precomposed" sizes="114x114" href="assets/ico/apple-touch-icon-114-precomposed.png">
    <link rel="apple-touch-icon-precomposed" sizes="72x72" href="assets/ico/apple-touch-icon-72-precomposed.png">
    <link rel="apple-touch-icon-precomposed" href="assets/ico/apple-touch-icon-57-precomposed.png">
    <script src="assets/js/jquery-2.2.1.min.js"></script>
    <script src="assets/bootstrap/js/bootstrap.min.js"></script>
    <script src="assets/js/jquery.backstretch.min.js"></script>
    <script src="assets/js/scripts.js"></script>
    <script src="assets/js/jquery.validate.min.js"></script>
    <script type="text/javascript">
        var countdown = 60;

        function settime(val) {
            let phone = $("#phone").val();
            if (phone != "") {
                if (countdown == 60) {
                    $.ajax({
                        url: "${pageContext.request.contextPath}/send/sendCode?phone=" + phone,
                        type: "get",
                        dataType: "json",
                        success: function () {

                        }
                    })
                }
                if (countdown == 0) {
                    val.removeAttribute("disabled");
                    val.value = "获取验证码";
                    countdown = 60;
                } else {
                    val.setAttribute("disabled", true);
                    val.value = "重新发送(" + countdown + ")";
                    countdown--;
                    setTimeout(function () {
                        settime(val)
                    }, 1000)
                }
            } else {
                $("#errpr-message").html("<font color='red'>请输入完整手机号<font>");
            }

        }

        $(function () {
            $("#loginButtonId").click(function () {
                var phone = $("#phone").val();
                var oneCode = $("#oneCode").val();
                if (phone && oneCode) {
                    $.ajax({
                        url: "${pageContext.request.contextPath}/admin/login2",
                        type: "POST",
                        data: $("#loginForm").serialize(),
                        dataType: "json",
                        success: function (data) {
                            if (data.status) {
                                location.href = "${pageContext.request.contextPath}/back/main.jsp"
                            } else {
                                $("#errpr-message").html("<font color='red'>" + data.message + "<font>");
                            }
                        }
                    })
                } else {
                    $("#errpr-message").html("<font color='red'>请输入完整信息<font>");
                }

            })
        })
    </script>
</head>

<body>

<!-- Top content -->
<div class="top-content">

    <div class="inner-bg">
        <div class="container">
            <div class="row">
                <div class="col-sm-8 col-sm-offset-2 text">
                    <h1><strong>CMFZ</strong> Login Form</h1>
                    <div class="description">
                        <p>
                            <a href="#"><strong>CMFZ</strong></a>
                        </p>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-6 col-sm-offset-3 form-box">
                    <div class="form-top" style="width: 450px">
                        <div class="form-top-left">
                            <h3>Login to showall</h3>
                            <p>Enter your username and password to log on:</p>
                        </div>
                        <div class="form-top-right">
                            <i class="fa fa-key"></i>
                        </div>
                    </div>
                    <div class="form-bottom" style="width: 450px">
                        <form role="form" action="${pageContext.request.contextPath}/Admin/login" method="post"
                              class="login-form" id="loginForm">
                            <span id="errpr-message"></span>
                            <span id="msgDiv"></span>
                            <div class="form-group">
                                <label class="sr-only" for="phone">phone</label>
                                <input type="text" name="phone" placeholder="请输入手机号..."
                                       class="form-username form-control" id="phone" required>
                            </div>
                            <div class="form-group">
                                <input style="padding-left: 20px; width: 287px;height: 50px;border:3px solid #ddd;border-radius: 4px;"
                                       placeholder="请输入验证码..."
                                       type="test" name="oneCode" id="oneCode" required>
                                <input type="button" id="btn" value="发送验证码" onclick="settime(this)"/>
                            </div>
                            <input type="button" style="width: 400px;border:1px solid #9d9d9d;border-radius: 4px;"
                                   id="loginButtonId" value="登录">
                        </form>
                        <a href="${pageContext.request.contextPath}/login/login.jsp">用户名登陆</a>
                    </div>
                </div>
            </div>
        </div>
    </div>

</div>


</body>

</html>
