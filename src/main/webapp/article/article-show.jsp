<%@page pageEncoding="UTF-8" isELIgnored="false" %>
<!--页头-->

<ul class="nav nav-tabs">
    <li role="presentation" class="active"><a href="#">所有文章</a></li>
    <li role="presentation" class="bbb">
        <a type="button" onclick="openModal('')">添加文章</a>
    </li>
</ul>


<%--jqgrid--%>
<script>
    function delModal(id) {
        console.log(id);
        $.ajax({
            url: "${pageContext.request.contextPath}/article/delete?id=" + id,
            type: "get",
            dataType: "json",
            success: function () {
                //自动刷新jqgrid表格
                $("#article-show-table").trigger("reloadGrid");
            }
        });
    }

    function openModal(id) {
        if (id) {
            console.log(id);
            var article = $("#article-show-table").jqGrid("getRowData", id);
            console.log(article);
            $("#article-id").val(article.id);
            $("#article-title").val(article.title);
            $("#article-author").val(article.author);
            $("#article-brief").val(article.brief);
            $("#article-createDate").val(article.createDate);
            KindEditor.html("#editor_id", article.content);
        } else {
            $("#article-id").val("");
            $("#article-title").val("");
            $("#article-author").val("");
            $("#article-brief").val("");
            $("#article-createDate").val("");
            KindEditor.html("#editor_id", "");
        }
        $("#article-modal").modal("show");
    }

    function save() {
        let id = $("#article-id").val();
        var url = '';
        if (id) {
            url = "${pageContext.request.contextPath}/article/edit";
        } else {
            url = "${pageContext.request.contextPath}/article/add";
        }
        $.ajax({
            url: url,
            type: "post",
            data: $("#article-form").serialize(),
            dataType: "json",
            success: function () {
                //自动刷新jqgrid表格
                $("#article-show-table").trigger("reloadGrid");
            }
        });
    }

    KindEditor.create('#editor_id', {
        width: '700px',
        //点击图片空间按钮时发送的请求
        fileManagerJson: "${pageContext.request.contextPath}/article/browse",
        //展示图片空间按钮
        allowFileManager: true,
        //上传图片所对应的方法
        uploadJson: "${pageContext.request.contextPath}/article/upload",
        //上传图片是图片的形参名称
        filePostName: "articleImg",
        afterBlur: function () {
            this.sync();
        }
    });
    $(function () {
        $("#article-show-table").jqGrid({
            url: "${pageContext.request.contextPath}/article/selectAll",
            datatype: "json",
            height: 400,//指定表格高度
            colNames: ['编号', '标题', '作者', '简介', '内容', '创建时间', '操作'],
            colModel: [
                {name: 'id', hidden: true},
                {name: 'title', editable: true},
                {name: 'author', editable: true,},
                {name: 'brief', editable: true},
                {name: 'content', hidden: true, editable: true},
                {name: 'createDate'},
                {
                    name: 'operate', formatter: function (value, option, rows) {
                        return "<a class='btn btn-primary' onclick=\"openModal('" + rows.id + "')\"> 修改</a>" +
                            "<a class='btn btn-danger' onclick=\"delModal('" + rows.id + "')\"> 删除</a>"
                    }
                },
            ],
            styleUI: "Bootstrap",//使用bootstrap样式
            autowidth: true,//自动适应父容器
            rowNum: 3,
            rowList: [3, 5, 10],
            pager: '#article-page',//指定分页工具栏
            viewrecords: true,
            sortorder: "desc",
            caption: "所有的文章展示",
        }).navGrid("#article-page", {edit: false, add: false, del: false, search: false}, {})
    })
</script>


<!--创建表格-->
<table id="article-show-table"></table>

<!--分页-->
<div id="article-page"></div>
<div class="modal" id="article-modal">
    <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content">
            <!--头-->
            <div class="modal-header">
                <button class="close" data-dismiss="modal"><span>&times;</span></button>
                <div class="modal-title">文章操作</div>
            </div>
            <!--身体-->
            <div class="modal-body">
                <div class="row">
                    <div class="col-sm-10 col-sm-offset-1">
                        <form action="" class="form-horizontal" id="article-form">
                            <input type="hidden" name="id" id="article-id">
                            <input type="hidden" name="createDate" id="article-createDate">
                            <div class="form-group">
                                <label class="col-sm-2 control-label">文章标题</label>
                                <div class="col-sm-10">
                                    <input type="text" class="form-control" name="title" id="article-title"
                                           placeholder="请输入标题">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label">文章作者</label>
                                <div class="col-sm-10">
                                    <input type="text" class="form-control" name="author" id="article-author"
                                           placeholder="请输入作者">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label">文章简介</label>
                                <div class="col-sm-10">
                                    <input type="text" class="form-control" name="brief" id="article-brief"
                                           placeholder="请输入简介">
                                </div>
                            </div>
                            <textarea id="editor_id" name="content" style="width:700px;height:300px;">

                            </textarea>

                        </form>
                    </div>
                </div>
            </div>
            <!--脚-->
            <div class="modal-footer">
                <button class="btn btn-primary" data-dismiss="modal" onclick="save()">保存</button>
                <%--<button type="button" class="btn btn-danger">取消</button>--%>
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
            </div>
        </div>
    </div>
</div>
