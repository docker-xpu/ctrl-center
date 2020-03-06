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
            <table class="table table-striped">
                <thead>
                <tr>
                    <th>
                        Name
                    </th>
                    <th>
                        ID
                    </th>
                    <th>
                        contentType
                    </th>
                    <th>
                        size
                    </th>
                    <th>
                        uploadDate
                    </th>
                    <th>
                        md5
                    </th>
                    <th>

                    </th>
                </tr>
                </thead>
                <tbody>
                <#list files as file>
                    <tr>
                        <td>
                            <a href="/images/files/${file.id!}">${file.name}</a>
                        </td>
                        <td>
                            ${file.id!}
                        </td>
                        <td>
                            ${file.contentType}
                        </td>
                        <td>
                            ${file.sizeStr}
                        </td>
                        <td>
                            ${file.uploadDateStr}
                        </td>
                        <td>
                            ${file.md5}
                        </td>
                        <td>
                            <a onclick="deleteImg(this)" id="${file.id!}" style="text-align:center">删除</a>
                        </td>
                    </tr>
                </#list>
                </tbody>
            </table>
        </div>
    </div>
</div> <!-- /container -->
<script src="https://cdn.bootcss.com/jquery/1.12.4/jquery.min.js"></script>
<script type="text/javascript" src="/js/delete_image.js"></script>
<script>window.jQuery || document.write('<script src="https://cdn.bootcss.com/jquery/3.4.1/jquery.min.js"><\/script>')</script>
<script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script type="text/javascript">
    document.getElementById("nav_image").setAttribute("class", "active")
</script>
</body>
</html>