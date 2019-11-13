<%@page pageEncoding="UTF-8" isELIgnored="false" %>
<script>
    $(function () {
        $("#article-search").click(function () {
            $.ajax({
                url: "${pageContext.request.contextPath}/article/search",
                type: "post",
                datatype: "json",
                data: "content=" + $("#article-input").val(),
                success: function (data) {
                    $("#article-search-show").empty();
                    var tr = $("<tr>" +
                        "<td>标题</td>" +
                        "<td>作者</td>" +
                        "<td>简介</td>" +
                        "<td>详情</td>" +
                        "</tr>");
                    $("#article-search-show").append(tr);
                    $.each(data, function (i, article) {
                        var tr = $("<tr>" +
                            "<td>" + article.title + "</td>" +
                            "<td>" + article.author + "</td>" +
                            "<td>" + article.brief + "</td>" +
                            "<td><a class='btn btn-primary' onclick=\"openModal('" + article.content + "')\"> 详情</a></td>" +
                            "</tr>");
                        $("#article-search-show").append(tr);
                    })
                }
            })
        })
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

    })

    function openModal(content) {
        KindEditor.html("#editor_id", content);
        $("#article-modal").modal("show");

    }
</script>
<div class="row">
    <div class="col-sm-2"></div>
    <div class="col-sm-6">
        <div class="input-group">
            <input type="text" class="form-control" id="article-input" placeholder="请输入关键字...">
            <span class="input-group-btn ">
        <button class="btn btn-primary" type="button" id="article-search"><span
                class="glyphicon glyphicon-search"></span> 百度一下</button>
      </span>
        </div><!-- /input-group -->
    </div>
    <div class="col-sm-4"></div>
    <table class='table table-bordered' id="article-search-show"></table>


</div>
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
                            <textarea id="editor_id" name="content" style="width:700px;height:300px;">

                            </textarea>

                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
