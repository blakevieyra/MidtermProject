<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<div class="head">
			<form action="login.do" method="POST">
				<input type="text" name="username" />
				<input type="text" name="password" />  <input type="submit"
					value="Login" />
			</form>
		</div>
	${username}
</body>
</html>