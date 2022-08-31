<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<!-- Import link for tailwind --> 
<link href="https://unpkg.com/tailwindcss@^1.0/dist/tailwind.min.css" rel="stylesheet">
<link rel="stylesheet" href="styles.css">
<title>Monthly Calendar</title>
</head>
<body>
	<nav style="background-color:#343333" class="navbar">
		<ul class="nav-links">
			<li style="background-color:#D9D9D9" class="nav-item">
				<a href="/weekly-calendar" style="color:#FFFFFF">Weekly View</a>
			</li>
			<li style="background-color:#D9D9D9" class="nav-item">
				<a href="/add-event" style="color:#FFFFFF">Add Event</a>
			</li>
			<li style="background-color:#D9D9D9" class="nav-item">
				<p style="color:#FFFFFF">[Company Name]</p>
			</li>
			<li style="background-color:#D15656" class="nav-item">
				<a href="/logout">Logout</a>
			</li>
		</ul>
	</nav>
	

</body>
</html>