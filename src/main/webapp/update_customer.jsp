<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Update Customer</title>
<!-- Include Bootstrap CSS -->
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css"
	rel="stylesheet">
</head>
<body>
	<div class="container">
		<h2>Update Customer</h2>
		<form action="UpdateCustomerServlet" method="post">
			<input type="hidden" name="uuid"
				value="<%=request.getParameter("uuid")%>">
			<div class="row mb-3">
				<div class="col">
					<label for="first_name">First Name:</label> <input type="text"
						class="form-control" id="first_name" name="first_name" required>
				</div>
				<div class="col">
					<label for="last_name">Last Name:</label> <input type="text"
						class="form-control" id="last_name" name="last_name" required>
				</div>
			</div>
			<div class="row mb-3">
				<div class="col">
					<label for="street">Street:</label> <input type="text"
						class="form-control" id="street" name="street" required>
				</div>
				<div class="col">
					<label for="address">Address:</label> <input type="text"
						class="form-control" id="address" name="address" required>
				</div>
			</div>
			<div class="row mb-3">
				<div class="col">
					<label for="city">City:</label> <input type="text"
						class="form-control" id="city" name="city" required>
				</div>
				<div class="col">
					<label for="state">State:</label> <input type="text"
						class="form-control" id="state" name="state" required>
				</div>
			</div>
			<div class="row mb-3">
				<div class="col">
					<label for="email">Email:</label> <input type="email"
						class="form-control" id="email" name="email" required>
				</div>
				<div class="col">
					<label for="phone">Phone:</label> <input type="text"
						class="form-control" id="phone" name="phone" required>
				</div>
			</div>
			<button type="submit" class="btn btn-primary">Update</button>
		</form>
	</div>

	<!-- Include Bootstrap JS and jQuery -->
	<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.1/dist/umd/popper.min.js"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.min.js"></script>
</body>
</html>
