<%@ page language="java" isThreadSafe="true" pageEncoding="utf8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="zh-CN" dir="ltr">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
</head>
<body>
<h2>上传实例</h2>
<% //=request.getAttribute("message") %>
<form action="filesUpload.do" method="post" enctype="multipart/form-data">

    <input type="file" name="files"><br/>
    <input type="file" name="files"><br/>
    <input type="file" name="files"><br/>
    <input type="submit" value="提交">
</form>
<hr>
<h2>上传Excel并解析为JSON</h2>
<form action="fileResovel.do" method="post" enctype="multipart/form-data">

    <input type="file" name="file"><br/>
    <input type="submit" value="提交">
</form>

<hr>
<h2>
<a href="downloadExcel.do">下载Excel</a>
</h2>
<hr>
<h2>
    下载文件
</h2>
<form action="downloadFile.do" method="post" >

    <input type="text" name="filePath"><br/><br/>
    <input type="submit" value="提交">
</form>
</body>
</html>
