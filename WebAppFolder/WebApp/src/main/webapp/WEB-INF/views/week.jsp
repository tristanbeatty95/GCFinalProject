<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Weekly Calendar</title>
<!-- Import link for tailwind -->
<link href="https://unpkg.com/tailwindcss@^1.0/dist/tailwind.min.css"
	rel="stylesheet">
<link rel="stylesheet" href="styles.css">
<script src="https://kit.fontawesome.com/aa77e8e357.js"
	crossorigin="anonymous"></script>
</head>
<body>
	<!-- Navbar at top of page -->
	<nav class="navbar">
		<ul class="nav-links">

			<li style="background-color: #8D8D8D" class="nav-item"><a
				href="/monthly-calendar" style="color: #FFFFFF">Monthly View</a></li>
			<li style="background-color: #8D8D8D" class="nav-item" class="button">
				<a href="#divOne" style="color: #FFFFFF">Add Event</a>
			</li>
			<li style="background-color: #8D8D8D" class="nav-item"><a
				href="/schedule-finder" style="color: #FFFFFF">Search Events</a></li>
		</ul>
		<ul class="nav-links">
			<li class="nav-item" id="companyNameText">
				<p style="color: #FFFFFF">Timely - Weekly Calendar</p>
			</li>
		</ul>
		<ul class="nav-links">
			<li style="background-color: #D15656" class="nav-item"
				id="logoutButton"><a href="/logout">Logout</a></li>
		</ul>
	</nav>
	<div class="overlay" id="divOne">
		<div class="wrapper">

			<a href="#" class="close">&times;</a>
			<div class="content">
				<div class="container">
					<form>
						<label for="eventName">Event Name</label> <input type="text"
							id="eventName" placeholder="event name"> <label
							for="startTime">Start Time</label> <input type="datetime-local"
							id="startTime"> <br> <label for="endTime">End
							Time</label> <input type="datetime-local" id="endTime"> <br>
						<label for="addEmployees">Add Employees</label>
						<textarea id="addEmployees" name="addEmployees"
							placeholder="Add multiple employees by separating with a comma"
							rows="3" cols="30"></textarea>
						<input type="submit" value="Submit">
					</form>
				</div>
			</div>
		</div>
	</div>


	<!-- Calendar Grid -->
	<div class="calendar-and-daily-info">
		<div class="month-navigation">
			
			<a id="prevButton" href="/weekly-calendar?date=${prevWeekDate}"><i class="fa-solid fa-arrow-left"></i></a>

			<!-- Gets current month and year as a String -->
			<div id="month-header-text">
				Week of:<br>
				${curWeekMonthString} ${curWeekDate.dayOfMonth},<br>
				${curWeekDate.year}
			</div>

			<a id="nextButton" href="/weekly-calendar?date=${nextWeekDate}"><i class="fa-solid fa-arrow-right"></i></a>
		
		</div>
		<div>


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

				<!-- Week -->

				<tr>
					<c:forEach var="date" items="${dates}" begin="0" end="6"
						varStatus="loop">

						<td>
							<a href="/weekly-calendar?date=${date}">
								${date.dayOfMonth}<br>
								<c:forEach var="events" items="${events[loop.index]}">								
									${events.eventName}
							
								</c:forEach>
							</a>
								<p class="center">
									
									<a href="/weekly-day-event?date=${date }&month=${dayMonthString}&year=2022&day=${date.dayOfMonth}"><i class="far fa-caret-square-right"></i></a>
								</p>
						</td>
					</c:forEach>
				</tr>

			</table>
		</div>


		<div class="daily-info-section">
			<h3 id="day-info-header">${curDayMonthString} ${curDayDate.dayOfMonth}</h3>
			<ul>
				<c:forEach var="event" items="${dayEvents}">
					<li>
						${event.eventName}	(${event.startTime} - ${event.endTime})<a href="/delete/${event.id}">   <i class="fa fa-trash" aria-hidden="true"></i></a>
					</li>
				</c:forEach>
			</ul>

			<a href="${dayEventUrl }" target="_blank" rel="noopener noreferrer">${dayEventName}</a>
			
			<a href="/delete-event" id="delete-button">Delete</a>
		</div>
	</div>
	<div class="center">
	<p>Click on a <i class="far fa-caret-square-right"></i> button to see more weird, funny, unknown and bizarre daily events!</p>
		Powered by <a title="Days Of The Year"
			href="https://www.daysoftheyear.com">Days Of The Year</a>
	</div>
	

</body>
</html>