<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
	<head>
		<title>User Form</title>
	</head>
	<body>
		<div>
			Viewing user: ${user.name} (updates: ${user.updates})
		</div>
		<form:form action="." modelAttribute="user" method="POST" htmlEscape="true">
			<form:hidden path="updates"/>

			<table>
				<tr>
					<td><form:label path="name">Name:</form:label></td>
					<td><form:input path="name" maxlength="50" size="30"/></td>
				</tr>
				<tr>
					<td><form:label path="age">Age:</form:label></td>
					<td><form:input path="age" maxlength="50" size="30"/></td>
				</tr>
			</table>

			<form:button>Submit</form:button>
		</form:form>
	</body>
</html>
