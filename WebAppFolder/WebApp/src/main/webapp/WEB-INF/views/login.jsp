<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="styles-login.css">
<title>Welcome</title>
</head>
<body>
	<div class="center">
		<button id="show-login">Login</button>
	</div>
	<div class="popup">
		<!-- <div class="close-btn">&times;</div> -->
		<div class="form">
		<form method="post" action="/login-submit">
			<h2>Log in</h2>
			<div class="form-element">
				<label for="email">Email</label> <input type="text" id="email"
					placeholder="Enter email" name="email">
			</div>
			<div class="form-element">
				<label for="password">Password</label> <input type="password"
					id="password" placeholder="Enter Password" name="password">
			</div>
			<a href="/login/create" style="color:black">Create an Account</a>
			<p>${error}</p>
			<div class="form-element">
				<button>Sign in</button>
			</div>
		</form>
		</div>
	</div>
</body>
</html>