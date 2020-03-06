<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <link href="https://cdn.bootcss.com/twitter-bootstrap/3.0.0/css/bootstrap.css" rel="stylesheet">
    <link href="/css/navbar-fixed-top.css" rel="stylesheet">

</head>
<body>
<#include "../common/nav.ftl">
<div class="container">
    <div class="row clearfix">
        <div class="col-md-12 column">
            <h1>上传镜像文件</h1>
            <hr>
            <input id="fileInput" type="file" name="file"/><br><br>
            <a id="upload" class="btn btn-default">上传</a>
            <br><br>
            <div id="progress_div" class="progress progress-striped active" style="display: none">
                <div id="upload_progress" class="progress-bar progress-bar-success" role="progressbar"
                     aria-valuenow="60" aria-valuemin="0" aria-valuemax="100"
                     style="width: 1%">
                </div>
            </div>
            <div id="success_div" class="alert alert-success alert-dismissable" style="display: none">
                <button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
                <h4>
                    上传成功!
                </h4> <strong>Warning!</strong> Best check yo self, you're not looking too good. <a href="#" class="alert-link">alert link</a>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    upload = document.getElementById("upload");
    upload.addEventListener('click', function () {
        var file = fileInput.files[0];
        if (!file) {
            alert('请选择上传文件');
            return
        }
        document.getElementById("progress_div").setAttribute("style", "");
        var params = new FormData();
        params.append('file', file);
        //params.append('otherParams', 'xxx'); // 其他必要参数
        var xhr = new XMLHttpRequest();
        xhr.onerror = function () {
            alert('请求失败');
        };
        xhr.onreadystatechange = function () {
            if (xhr.readyState === 4) {
                if (xhr.status === 200) {
                    console.log(xhr.responseText);
                } else {
                    console.error(xhr.status)
                }
            }
        };
        xhr.upload.onprogress = function (e) {
            let upload_progress = document.getElementById("upload_progress");
            let progress_div = document.getElementById("progress_div");
            upload_progress.setAttribute("style", "width: " + Math.round(e.loaded / e.total * 100) + "%");
            if(e.loaded === e.total){
                progress_div.setAttribute("style", "display: none");
                document.getElementById("success_div").setAttribute("style", "");
                setTimeout(function () {
                    location.reload();
                }, 3000);
            }
        };
        xhr.open('POST', 'http://localhost:8080/images/upload', true);
        xhr.send(params);
    });
</script>
<script src="https://cdn.bootcss.com/jquery/1.12.4/jquery.min.js"></script>
<script>window.jQuery || document.write('<script src="https://cdn.bootcss.com/jquery/3.4.1/jquery.min.js"><\/script>')</script>
<script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script type="text/javascript">
    document.getElementById("nav_image").setAttribute("class", "active")
</script>
</body>
</html>