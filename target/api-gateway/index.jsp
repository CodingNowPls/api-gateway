<html>

<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<script src="http://libs.baidu.com/jquery/2.0.0/jquery.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript">


    function invokeApi() {
        var  params={userId:"5"};
        var jsonStr= JSON.stringify(params);
        $.ajax({
            type: 'GET',
            url: "/api",
            data: {"method":"com.cn.striverfeng.service.userService",params:jsonStr},
            success: function(data) {
                alert(data);
                console.info(data);
            },
            dataType: "text"
        });
    }
</script>
<body>
<%--<h2>index!</h2>--%>
<button onclick="invokeApi()">invokeApi</button>
</body>
</html>
