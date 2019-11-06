<%@page pageEncoding="UTF-8" isELIgnored="false" %>
<!--页头-->
<div class="page-header" style="margin-top: -20px;">
    <h1>所有明星数据</h1>
</div>
<%--jqgrid--%>
<script>
    $(function () {
        jQuery("#star-show-table").jqGrid({
            url: "${pageContext.request.contextPath}/star/selectAll",
            datatype: "json",
            height: 300,
            colNames: ['编号', '人送外号', '真名', '头像', '性别', '生日'],
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
                {name: 'bir'}
            ],
            styleUI: "Bootstrap",
            autowidth: true,
            rowNum: 3,
            rowList: [3, 10, 20, 30],
            pager: '#star-page',
            viewrecords: true,
            subGrid: true,
            caption: "所有明星列表",
            editurl: "${pageContext.request.contextPath}/star/edit",
        }).navGrid("#star-page", {edit: true, add: true, del: true, search: false}, {
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
                    $.ajaxFileUpload({
                        url: "${pageContext.request.contextPath}/star/upload",
                        type: "post",
                        fileElementId: "photo",
                        data: {id: id},
                        success: function (response) {
                            //自动刷新jqgrid表格
                            $("#star-show-table").trigger("reloadGrid");
                        }
                    });
                }
                return "dfds";
            }
        });
    })
</script>


<!--创建表格-->
<table id="star-show-table"></table>

<!--分页-->
<div id="star-page"></div>
