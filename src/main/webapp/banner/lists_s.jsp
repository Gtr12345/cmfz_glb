<%@page pageEncoding="UTF-8" isELIgnored="false" %>
<!--页头-->
<div class="page-header" style="margin-top: -20px;">
    <h1>所有轮播图数据</h1>
</div>
<%--jqgrid--%>
<script>
    $(function () {
        //创建jqgrid
        $("#banneTable").jqGrid({
            styleUI: "Bootstrap",//使用bootstrap样式
            autowidth: true,//自动适应父容器
            height: 400,//指定表格高度
            url: "${pageContext.request.contextPath}/banner/query",//请求数据
            datatype: "json",//指定请求数据格式 json格式
            colNames: ["名称", "图片", "描述", "状态", "创建时间"],//用来指定显示表格列
            pager: "#pager",//指定分页工具栏
            rowNum: 2,//每页展示2条
            rowList: [2, 10, 15, 20, 50],//下拉列表
            viewrecords: true,//显示总条数
            editurl: "${pageContext.request.contextPath}/banner/edit",//编辑时url(保存 删除 和 修改)
            caption: "所有轮播图数据",
            colModel: [
                {name: "name", editable: true},
                {
                    name: "cover", editable: true,
                    edittype: "file",
                    formatter: function (value, option, rows) {
                        return "<img style='width:100px;height:60px;' src='${pageContext.request.contextPath}/banner/img/" + rows.cover + "'>";
                    }
                },
                {name: "description", editable: true,},
                {
                    name: "status", editable: true,
                    edittype: "select", editoptions: {
                        value: "正常:正常;封印:封印"
                    }
                },
                {name: "create_date"},
            ],//用来对当前列进行配置

        }).navGrid("#pager", {edit: true, add: true, del: true, search: false}, {
            //控制修改
            closeAfterEdit: true,
            beforeShowForm: function (fmt) {
                fmt.find("#cover").attr("disabled", true);
            }
        }, {
            //控制添加
            closeAfterAdd: true,
            afterSubmit: function (data) {
                console.log(data);
                var status = data.responseJSON.status;
                var id = data.responseJSON.message;
                if (status) {
                    $.ajaxFileUpload({
                        url: "${pageContext.request.contextPath}/banner/upload",
                        type: "post",
                        fileElementId: "cover",
                        data: {id: id},
                        success: function (response) {
                            //自动刷新jqgrid表格
                            $("#banneTable").trigger("reloadGrid");
                        }
                    });
                }
                return "123";
            }
        });
        //参数1:navGrid 参数2:分页工具栏选择器   参数3: 编辑的options({edit:false,add:false....})
        //参数4:编辑form表单配置
    })
</script>


<!--创建表格-->
<table id="banneTable"></table>

<!--分页-->
<div id="pager"></div>
