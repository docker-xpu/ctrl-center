<!-- Fixed navbar -->
<nav class="navbar navbar-default navbar-fixed-top">
    <div class="container">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="/">Docker Dashboard</a>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
            <ul class="nav navbar-nav">
                <li id="nav_physics" class="dropdown">
                    <a class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">
                        物理机管理<span class="caret"></span></a>
                    <ul class="dropdown-menu">
                        <li><a href="/physics/create-page">注册新主机</a></li>
                        <li><a href="/physics/index">主机列表</a></li>
                        <li role="separator" class="divider"></li>
                        <li><a href="#">异常实例</a></li>
                    </ul>
                </li>
                <li class="dropdown">
                    <a class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">
                        容器管理<span class="caret"></span></a>
                    <ul class="dropdown-menu">
                        <li><a href="#">运行中</a></li>
                        <li><a href="#">已停止</a></li>
                        <li role="separator" class="divider"></li>
                        <li><a href="#">异常退出</a></li>
                    </ul>
                </li>
                <li id="nav_image" class="dropdown">
                    <a class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">
                        镜像管理<span class="caret"></span></a>
                    <ul class="dropdown-menu">
                        <li><a href="/images/index">浏览镜像</a></li>
                        <li role="separator" class="divider"></li>
                        <li><a href="/images/upload-page">上传镜像</a></li>
                    </ul>
                </li>
                <li><a href="/"></a></li>

            </ul>
<#--            <ul class="nav navbar-nav navbar-right">-->
<#--                <li><a href="../navbar/">Default</a></li>-->
<#--                <li><a href="../navbar-static-top/">Static top</a></li>-->
<#--                <li class="active"><a href="./">Fixed top <span class="sr-only">(current)</span></a></li>-->
<#--            </ul>-->
        </div><!--/.nav-collapse -->
    </div>
</nav>