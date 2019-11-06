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
            url: "${pageContext.request.contextPath}/star/selectAll",//请求数据
            datatype: "json",//指定请求数据格式 json格式
            colNames: ['编号', '人送外号', '真名', '头像', '性别', '生日'],//用来指定显示表格列
            pager: "#pager",//指定分页工具栏
            rowNum: 2,//每页展示2条
            rowList: [2, 10, 15, 20, 50],//下拉列表
            viewrecords: true,//显示总条数
            subGrid: true,//开启表链表
            editurl: "${pageContext.request.contextPath}/star/edit",//编辑时url(保存 删除 和 修改)
            caption: "所有轮播图数据",
            colModel: [
                {name: 'id', hidden: true},
                {name: 'nickname', editable: true},
                {name: 'realname', editable: true},
                {
                    name: 'photo', editable: true,
                    edittype: "file",
                    formatter(values, option, rows) {
                        return "<img style='width:70px;height:70px' src='${pageContext.request.contextPath}/star/img/" + rows.photo + "'>";
                    }
                },
                {
                    name: 'sex', editable: true,
                    edittype: "select", editoptions: {
                        value: "男:男;女:女"
                    }
                },
                {name: 'bir'},
            ],//用来对当前列进行配置
            subGridRowExpanded: function (subgrid_id, id) {
                var subgrid_table_id, pager_id;
                subgrid_table_id = subgrid_id + "_t";
                pager_id = "p_" + subgrid_table_id;
                $("#" + subgrid_id).html(
                    "<table id='" + subgrid_table_id + "' class='scroll'></table>" +
                    "<div id='" + pager_id + "' class='scroll'></div>");
                $("#" + subgrid_table_id).jqGrid(
                    {
                        url: "${pageContext.request.contextPath}/user/selectAll?starId=" + id,
                        datatype: "json",
                        colNames: ['编号', '用户名', '昵称', '头像', '电话', '性别', '地址', '签名'],
                        colModel: [
                            {name: "id"},
                            {name: "username"},
                            {name: "nickname"},
                            {
                                name: "photo", editable: true,
                                edittype: "file",
                                formatter(values, option, rows) {
                                    return "<img style='width:70px;height:70px' src='${pageContext.request.contextPath}/star/img/" + rows.photo + "'>";
                                }
                            },
                            {name: "phone"},
                            {name: "sex"},
                            {name: "province"},
                            {name: "sign"}
                        ],
                        styleUI: "Bootstrap",
                        rowNum: 2,
                        pager: pager_id,
                        autowidth: true,
                        height: '100%'
                    });
                jQuery("#" + subgrid_table_id).jqGrid('navGrid',
                    "#" + pager_id, {
                        edit: false,
                        add: false,
                        del: false,
                        search: false
                    }
                );
            },
        }).navGrid("#pager", {edit: false, add: true, del: false, search: false}, {
            //控制修改
            closeAfterEdit: true,
            beforeShowForm: function (fmt) {
                fmt.find("#photo").attr("disabled", true);
            }
        }, {
            //控制添加
            closeAfterAdd: true,
            afterSubmit: function (data) {
                console.log(data);
                var status = data.responseJSON.status;
                var id = data.responseJSON.message;
                if (status) {
                    //alert("11111");
                    $.ajaxFileUpload({
                        url: "${pageContext.request.contextPath}/star/upload",
                        type: "post",
                        fileElementId: "photo",
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
