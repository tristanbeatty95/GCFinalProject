<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="styles-register.css">
<title>Welcome</title>
</head>
<body>
	<!-- <div class="center">
		<button id="show-login">Login</button>
	</div> -->
	<div class="popup">
		<!-- <div class="close-btn">&times;</div> -->
		<div class="form">
		<form method="post" action="/new-login-submit">
			<h2>Create Account</h2>
			<div class="form-element">
				<label for="email">Email</label> <input type="text" id="email"
					placeholder="Enter email" name="email">
			</div>
			<div class="form-element">
				<label for="Name">Name</label> <input type="text" id="name"
					placeholder="Enter name" required name="name">
			</div>
			<div class="form-element">
				<label for="password">Password</label> <input type="password"
					id="password" placeholder="Enter Password" name="password">
			</div>
			<div class="form-element">
				<label for="retypePassword">Re-Type Password</label> <input type="password"
					id="retypePassword" placeholder="Enter Password Again" name="retypePassword">
			</div>
			<a href="/login">I already have an account</a>
			<p>${error}</p>
			<div class="form-element">
				<button>Create Account</button>
			</div>
		</form>
		</div>
	</div>
</body>
</html>