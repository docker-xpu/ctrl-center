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
    <h1>注册新主机</h1><hr>
    <div class="row clearfix">
        <div class="col-md-12 column">
            <form role="form">
                <div class="form-group">
                    <label for="exampleInputEmail1">IP Address</label><input type="text" class="form-control" id="exampleInputEmail1" />
                </div>
                <div class="form-group">
                    <label for="exampleInputPassword1">Password</label><input type="password" class="form-control" id="exampleInputPassword1" />
                </div>
                <button type="submit" class="btn btn-default">添加主机</button>
            </form>
        </div>
    </div>
</div> <!-- /container -->
<script src="https://cdn.bootcss.com/jquery/1.12.4/jquery.min.js"></script>
<script>window.jQuery || document.write('<script src="https://cdn.bootcss.com/jquery/3.4.1/jquery.min.js"><\/script>')</script>
<script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script type="text/javascript">
    document.getElementById("nav_physics").setAttribute("class", "active")
</script>
</body>
</html>