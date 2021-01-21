<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add driver to the car</title>
</head>
<body>
<h1>Please, enter data</h1>

<form method="post" action="${pageContext.request.contextPath}/cars/drivers/add">
    Enter car id: <input type="number" name="car_id" required>
    Enter driver id: <input type="number" name="driver_id" required>

    <button type="submit">Add</button>
</form>
</body>
</html>
