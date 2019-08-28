<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script language="javascript">
function check(){
if(document.form1.a[0].checked==true)
document.form1.action="1.htm"
else
document.form1.action="2.htm"
}
</script>
</head>
<body>
<form name="form1" method="post" action="" onSubmit="check();">
转到页面一<input type="radio" name="a">
转到页面二<input type="radio" name="a">
<input name="" type="submit" value="提交">
</form>
</body>
</html> 