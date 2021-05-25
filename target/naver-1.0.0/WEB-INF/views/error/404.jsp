<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr" />
<meta name="Robots" content="Noindex, Nofollow" />
<title>404</title>
<style>
.content_area .error_box { width: 550px; height: 269px; padding: 55px 0 0 0; border: 3px solid #e1e1e1; margin: 0 auto; text-align: center; }
.error_box .error_title { font-size: 32px; font-weight: bold; color: #0a47a2; margin: 0 0 16px 0; font-family: Myriad Pro,Tahoma,verdana, dotum; }
.error_box .error_message { font-size: 16px; color: #898989; font-weight: bold; }
</style>


<script type="text/javascript">

	function fnOnLoad(){
		
	}

</script>
</head>
<body oncontextmenu='return false' onLoad="fnOnLoad()">

	<div style="width:751px;">
		<div class="content_area">
			<div class="error_box">
				<img src="${pageContext.request.contextPath}/image/error_icon.gif" alt="" />
				<p class="error_title">404 ERROR</p>
				<p class="error_message">요청하신 페이지를 찾을 수 없습니다.</p>
			</div>
		</div>
	</div>
	
</body>
</html>
