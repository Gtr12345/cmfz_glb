<%@page pageEncoding="UTF-8" isELIgnored="false" %>
<!--页头-->
<div class="page-header" style="margin-top: -20px;">
    <h1>所有专辑数据</h1>
</div>
<%--jqgrid--%>
<script>
    function getOptionValue() {
        var options = "";
        var i = 0;
        $.ajax({
            async: false,  //千万要记住加这个属性配置
            type: "post",
            url: "${pageContext.request.contextPath}/star/queryAll",
            success: function (data) {
                for (i; i < data.length; i++) {
                    if (i != data.length - 1) {
                        options += data[i].realname + ":" + data[i].realname + ";";
                    } else {
                        options += data[i].realname + ":" + data[i].realname;
                    }
                }
            }
        });
        return options;
    }

    $(function () {
        $("#album-show-table").jqGrid({
            url: "${pageContext.request.contextPath}/album/selectAll",
            datatype: "json",
            height: 400,//指定表格高度
            colNames: ['编辑', '专辑名称', '作者', '封面', '专辑数量', '评分', '简介', '播音员', '创建时间'],
            colModel: [
                {name: 'id', hidden: true},
                {name: 'title', editable: true},
                {
                    name: 'author', editable: true,
                    edittype: "select", editoptions: {
                        value: getOptionValue()
                    }
                },
                {
                    name: 'cover', editable: true,
                    edittype: "file",
                    formatter(values, option, rows) {
                        return "<img style='width:70px;height:70px' src='${pageContext.request.contextPath}/album/img/" + rows.cover + "'>";
                    }
                },
                {name: 'count'},
                {name: 'score', editable: true},
                {name: 'brief', editable: true},
                {name: 'broadcast', editable: true},
                {name: 'createDate'},
            ],
            styleUI: "Bootstrap",//使用bootstrap样式
            autowidth: true,//自动适应父容器
            rowNum: 3,
            rowList: [3, 5, 10],
            pager: '#album-page',//指定分页工具栏
            viewrecords: true,
            sortorder: "desc",
            subGrid: true,
            editurl: "${pageContext.request.contextPath}/album/edit",
            caption: "所有专辑列表",
            subGridRowExpanded: function (subgrid_id, id) {
                var subgrid_table_id, pager_id;
                subgrid_table_id = subgrid_id + "_t";
                pager_id = "p_" + subgrid_table_id;
                $("#" + subgrid_id).html(
                    "<table id='" + subgrid_table_id + "' class='scroll'></table>" +
                    "<div id='" + pager_id + "' class='scroll'></div>");
                $("#" + subgrid_table_id).jqGrid(
                    {
                        url: "${pageContext.request.contextPath}/chapter/selectAll?albumId=" + id,
                        datatype: "json",
                        colNames: ['编号', '名字', '歌手', '大小', '时长', '创建时间', '在线播放'],
                        colModel: [
                            {name: "id", hidden: true},
                            {name: "title", editable: true, edittype: "file"},
                            {name: "name", editable: true},
                            {name: "size"},
                            {name: "duration"},
                            {name: "createDate"},
                            {
                                name: "operation", width: 300, formatter: function (value, option, rows) {
                                    return "<audio controls>\n" +
                                        "  <source src='${pageContext.request.contextPath}/album/music/" + rows.title + "' >\n" +
                                        "</audio>";
                                }
                            }
                        ],
                        styleUI: "Bootstrap",
                        rowNum: 3,
                        rowList: [3, 5, 10],
                        pager: pager_id,
                        autowidth: true,
                        height: '100%',
                        editurl: "${pageContext.request.contextPath}/chapter/edit?albumId=" + id
                    });
                jQuery("#" + subgrid_table_id).jqGrid('navGrid',
                    "#" + pager_id, {
                        edit: false,
                        add: true,
                        del: false,
                        search: false
                    }, {}, {
                        //添加
                        closeAfterAdd: true,
                        afterSubmit: function (response) {
                            var status = response.responseJSON.status;
                            if (status) {
                                var cid = response.responseJSON.message;
                                $.ajaxFileUpload({
                                    url: "${pageContext.request.contextPath}/chapter/upload",
                                    type: "post",
                                    fileElementId: "title",
                                    data: {id: cid, albumId: id},
                                    success: function () {
                                        $("#" + subgrid_table_id).trigger("reloadGrid");
                                    }
                                })
                            }
                            return "1213";
                        }
                    }
                );
            },
        }).navGrid("#album-page", {edit: false, add: true, del: false, search: false}, {
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
                        url: "${pageContext.request.contextPath}/album/upload",
                        type: "post",
                        fileElementId: "cover",
                        data: {id: id},
                        success: function (response) {
                            //自动刷新jqgrid表格
                            $("#album-show-table").trigger("reloadGrid");
                        }
                    });
                }
                return "123";
            }
        });
    })
</script>


<!--创建表格-->
<table id="album-show-table"></table>

<!--分页-->
<div id="album-page"></div>
