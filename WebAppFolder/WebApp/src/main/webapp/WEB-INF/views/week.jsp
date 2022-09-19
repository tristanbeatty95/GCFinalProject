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
<link href='https://fonts.googleapis.com/css?family=Poppins' rel='stylesheet'>
<link rel="stylesheet" href="styles.css">
<script src="https://kit.fontawesome.com/aa77e8e357.js"
	crossorigin="anonymous"></script>
</head>
<body>
	<!-- Navbar at top of page -->
	<nav class="navbar">
		<ul class="nav-links">

			<li class="nav-item" id="nav1"><a href="/monthly-calendar">Monthly
					View</a></li>
			<li class="nav-item" id="nav2" class="button"><a href="#divOne">Add
					Event</a></li>
			<li class="nav-item" id="nav3"><a href="/schedule-finder">Search
					Events</a></li>
		</ul>
		<ul class="nav-links2">
			<li class="nav-item" id="companyNameText">
				<p>Weekly Calendar</p>
			</li>
		</ul>
		<ul class="nav-links3">
			<li style="background-color: #e3f0ff" class="nav-item"
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
							id="eventName" placeholder="event name" required> <label
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

			<a id="prevButton" href="/weekly-calendar?date=${prevWeekDate}"><i
				class="fa-solid fa-arrow-left"></i></a>

			<!-- Gets current month and year as a String -->
			<div id="month-header-text">
				Week of:<br> ${curWeekMonthString} ${curWeekDate.dayOfMonth},<br>
				${curWeekDate.year}
			</div>

			<a id="nextButton" href="/weekly-calendar?date=${nextWeekDate}"><i
				class="fa-solid fa-arrow-right"></i></a>

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

						<td><a href="/weekly-calendar?date=${date}">
								${date.dayOfMonth}<br> <c:set var="dateString">${date.toString()}</c:set>
								<c:forEach var="dayEvent" items="${events[dateString]}">								
									${dayEvent.eventName}
							
								</c:forEach>
						</a>
							</td>
					</c:forEach>
				</tr>
			</table>
		</div>


		<div class="daily-info-section">
			<h3 id="day-info-header">${curDayMonthString}
				${curDayDate.dayOfMonth}</h3>
			<ul>
				<c:forEach var="event" items="${dayEvents}">
					<li>${event.eventName} <a href="/delete/${event.id}"> <i
							class="fa fa-trash" aria-hidden="true"></i></a>
							 <a href="#divOne1"> <i
							class="fa fa-pencil" aria-hidden="true"></i></a>
							<ul> 
							<li>(${event.startTime} - ${event.endTime})</li>
						<li>Attendees: ${event.employeesString}</li>
						</ul>
					</li>
					
					
					
					
					<div class="overlay" id="divOne1">
		<div class="wrapper">

			<a href="#" class="close">&times;</a>
			<div class="content">
				<div class="container">
	
<form class="form" action="/postEvent/${event.id }" method="post">
<input type="hidden" value="${event.id}" name="id">
Event Name: <input type="text" value="${event.eventName}" name="eventName" required /><br>
Start Time: <input type="datetime-local" value="${event.start}" name="start" required /><br>
End Time: <input type="datetime-local" value="${event.end}" name="end" required /><br>
Employees: <input type="text" value="${event.employeesString}" name="employees" required />
<input type="submit" class="btn-success" value="Update">

</form>
</div>
			</div>
		</div>
	</div>
					
				</c:forEach>
			</ul>
			<br><br>
			<div class ="center"><a href="${dayEventUrl }" target="_blank" rel="noopener noreferrer">${dayEventName} <i class="far fa-caret-square-right"></i></a></div>
			

			
		</div>
	</div>
	<div class="center">
		Powered by <a title="Days Of The Year"
			href="https://www.daysoftheyear.com" target="_blank" rel="noopener noreferrer">Days Of The Year</a>
	</div>


</body>
</html>