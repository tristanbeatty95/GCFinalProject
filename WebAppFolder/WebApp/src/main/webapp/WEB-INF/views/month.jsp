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
	<!-- Navbar at top of page --> 
	<nav class="navbar">
		<ul class="nav-links">
			<li style="background-color:#8D8D8D" class="nav-item">
				<a href="/weekly-calendar" style="color:#FFFFFF">Weekly View</a>
			</li>
			<li style="background-color:#8D8D8D" class="nav-item">
				<a href="/add-event" style="color:#FFFFFF">Add Event</a>
			</li>
		</ul>
		<ul class="nav-links">
			<li class="nav-item" id="companyNameText">
				<p style="color:#FFFFFF">[Company Name]</p>
			</li>
		</ul>
		<ul class="nav-links">
			<li style="background-color:#D15656" class="nav-item" id="logoutButton">
				<a href="/logout">Logout</a>
			</li>
		</ul>
	</nav>
	
	<!-- Calendar Grid --> 
	<div class="calendar-and-daily-info">
		<div>
			<h1><c:out value="${monthStr}"/> <c:out value="${year}"/></h1>
			<table>
				<!-- Table Header (row 6) -->
				<tr>
					<th>Sunday</th>
					<th>Monday</th>
					<th>Tuesday</th>
					<th>Wednesday</th>
					<th>Thursday</th>
					<th>Friday</th>
					<th>Saturday</th>
				</tr>
		
				<!-- Week 1 -->
				<tr>
					<td></td>
					<td>1</td>
					<td>2</td>
					<td>3</td>
					<td>4</td>
					<td>5</td>
					<td>6</td>
				</tr>
		
				<!-- Week 2 -->
				<tr>
					<td>7</td>
					<td>8</td>
					<td>9</td>
					<td>10</td>
					<td>11</td>
					<td>12</td>
					<td>13</td>
				</tr>
		
				<!-- Week 3 -->
				<tr>
					<td>14</td>
					<td>15</td>
					<td>16</td>
					<td>17</td>
					<td>18</td>
					<td>19</td>
					<td>20</td>
				</tr>
		
				<!-- Week 4 -->
				<tr>
					<td>21</td>
					<td>22</td>
					<td>23</td>
					<td>24</td>
					<td>25</td>
					<td>26</td>
					<td>27</td>
				</tr>
		
				<!-- Week 5 -->
				<tr>
					<td>28</td>
					<td>29</td>
					<td>30</td>
					<td>31</td>
					<td></td>
					<td></td>
					<td></td>
				</tr>
			</table>
		</div>
		<div class="daily-info-section">
			<h3 id="day-info-header">Day Info</h3>
			<a href="/delete-event" id="delete-button">Delete</a>
		</div>
	</div>
	
</body>
</html>