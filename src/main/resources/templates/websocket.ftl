<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
<form action="/licence/delete" method="post">
    <input name="licenseId" type="number">
    <input type="submit">
</form>

<script>
    let websocket = null;
    if('WebSocket' in window){
        websocket = new WebSocket("ws://127.0.0.1:8080/getHostInfoWebSocket");
    }else {
        alert('该浏览器不支持WebSocket')
    }

    websocket.onopen = function (event) {
        console.log('建立连接');
    };

    websocket.onclose = function (event) {
        console.log('关闭连接');
    };

    websocket.onmessage = function (event) {
        console.log('收到消息'+event.data);
    };
    websocket.onerror = function (event) {
        alert('websocket发生错误');
    };
    window.onbeforeunload = function () {
        websocket.close();
    };
</script>
</body>
</html>