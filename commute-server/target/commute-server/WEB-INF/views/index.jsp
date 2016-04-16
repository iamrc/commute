<%@page pageEncoding="UTF-8" contentType="text/html; UTF-8" %>
<!DOCTYPE html>
<html lang="zh-CN">

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>学生考勤管理</title>
    <!-- Bootstrap -->
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="css/dashboard.css">
    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <!--<script src="//cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>-->
    <!--<script src="//cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>-->
    <%--<![endif]-->--%>
</head>

<body>
<nav class="navbar navbar-inverse navbar-fixed-top">
    <div class="container-fluid">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#">学生考勤管理</a>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
            <ul class="nav navbar-nav navbar-right">
                <li><a href="#">设置</a></li>
                <li><a href="#">帮助</a></li>
            </ul>
        </div>
    </div>
</nav>
<!-- end nav -->
<div class="container-fluid">
    <div class="row">
        <div class="col-sm-3 col-md-2 sidebar">
            <ul class="nav nav-sidebar">
                <li class="active"><a href="#">到席情况<span class="sr-only">(current)</span></a></li>
                <li><a href="#">历史记录</a></li>
            </ul>
        </div>
        <!-- end sidebar -->
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <h1 class="page-header commute-headr">Dashboard</h1>
            <div class="btn-group">
                <a href="#" class="btn btn-info">上课</a>
                <a href="#" class="btn btn-danger" id="stop">下课</a>
            </div>
            <div class="table-responsive">
                <table class="table table-striped">
                    <thead>
                    <tr>
                        <th>学号</th>
                        <th>姓名</th>
                        <th>状态</th>
                    </tr>
                    </thead>
                    <tbody id="state-body">
                    <tr>
                        <td>201120630219</td>
                        <td>沈达</td>
                        <td class="state5">在席情况</td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
<script src="js/jquery-min-2.1.14.js"></script>
<script src="js/bootstrap.min.js"></script>
<script type="text/javascript">
    var colorArray = ["state0","state1","state2","state3","state4","state5"];
    $(document).ready(function() {
        getState();
        setInterval(getState,8000);
    });

    $("#stop").click(function(){
        $("#state-body").html("");
        $.ajax({
            url:"/stu/clearState.do",
            type:"GET",
            dataType:"json",
            success:function(data){
                alert("下课成功");
            },
            error:function(){
                alert("error");
            }
        });

    });
    function getState(){
        $.ajax({
            url:"/stu/getState.do",
            type:"GET",
            dataType:"json",
            success:function(data){
                refreshState(data);
            },
            error:function(){
                alert("error");
            }
        });
    }
    function refreshState(data){
        $("#state-body").html("");
        var json = eval(data);
        $.each(json,function(index,item){
            var id = json[index].id;
            var name = json[index].name;
            var state = json[index].state;
            var color = colorArray[state];
            var htm = "<tr><td>"+id+"<td>"+name+"</td>"+"<td class="+color+">在席情况</td></tr>";
            $("#state-body").append(htm);
        });
    }
</script>
</body>
</html>