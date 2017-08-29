<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<input type="text" name="symbol" value="Input Stock Symbol"><br>
<input type="submit" name="subscribe" value="Subscribe"><br> 
<input type="submit" name = "unsubscribe" value="unSubscribe">
<!-- COPY THIS CODE TO PASTE INTO ANY HTML PAGE -->
<div style="width: 158px; border: #000000 1px solid; background-color: #006699; text-align: center; padding: 0px 2px 7px 2px;">
<div style="color: #FFFFFF; font: 13px arial, sans-serif; font-weight: bold; text-align: center; padding: 3px;">
News Window<br>
</div>
<iframe id="NewsWindow" seamless src="news_win.htm" width="150" height="204" marginwidth="0" marginheight="0" frameborder="0" scrolling="no" style="display: block; margin: 0 auto; padding: 0; border: #000000 1px solid;"></iframe>
</div>
<!-- END CODE -->

</body>
</html>