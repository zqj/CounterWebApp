<%@ page language="java" isThreadSafe="true" pageEncoding="utf8" import="java.lang.*,java.util.*" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="zh-CN" dir="ltr">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
</head>
<body>
<h2>文件:</h2>

<%//=request.getAttribute("show") %>
<% Map<String,String> result = (Map)request.getAttribute("result"); %>

<%
    for (String v : result.values()) {
        out.println("value= " + v + "<br/>");
    }
%>

<br/>
<%= request.getAttribute("json") %>
</body>
</html>
