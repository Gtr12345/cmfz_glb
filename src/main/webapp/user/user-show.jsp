<%@page pageEncoding="UTF-8" isELIgnored="false" %>
<!--页头-->
<ul class="nav nav-tabs">
    <li role="presentation" class="active"><a href="#">所有用户</a></li>
    <li role="presentation" class="bbb">
        <a href="${pageContext.request.contextPath}/user/export" type="button">导出用户</a>
    </li>
</ul>
<%--jqgrid--%>
<script>
    $(function () {
        //创建jqgrid
        $("#user-show-table").jqGrid({
            styleUI: "Bootstrap",//使用bootstrap样式
            autowidth: true,//自动适应父容器
            height: 400,//指定表格高度
            url: "${pageContext.request.contextPath}/user/queryAll",//请求数据
            datatype: "json",//指定请求数据格式 json格式
            colNames: ['编号', '用户名', '密码', '盐', '昵称', '电话', '省', '市', '签名', '照片', '性别', '创建时间', '明星编号'],//用来指定显示表格列
            pager: "#user-page",//指定分页工具栏
            rowNum: 2,//每页展示2条
            rowList: [2, 10, 15, 20, 50],//下拉列表
            viewrecords: true,//显示总条数
            caption: "所有用户信息",
            colModel: [
                {name: 'id', hidden: true},
                {name: 'username', editable: true},
                {name: 'password', editable: true},
                {name: 'salt', hidden: true},
                {name: 'nickname'},
                {name: 'phone'},
                {name: 'province'},
                {name: 'city'},
                {name: 'sign'},
                {
                    name: 'photo', editable: true,
                    edittype: "file",
                    formatter(values, option, rows) {
                        return "<img style='width:70px;height:70px' src='${pageContext.request.contextPath}/user/img/" + rows.photo + "'>";
                    }
                },
                {name: 'sex'},
                {name: 'createDate'},
                {name: 'starId'},
            ],//用来对当前列进行配置

        }).navGrid("#user-page", {edit: false, add: false, del: false, search: false}, {});
        //参数1:navGrid 参数2:分页工具栏选择器   参数3: 编辑的options({edit:false,add:false....})
        //参数4:编辑form表单配置
    })
</script>


<!--创建表格-->
<table id="user-show-table"></table>

<!--分页-->
<div id="user-page"></div>
