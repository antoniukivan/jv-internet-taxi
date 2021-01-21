<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add driver to the car</title>
</head>
<body>
<h1>Please, enter data</h1>

<h4 style="color: red">${message}</h4>

<form method="post" action="${pageContext.request.contextPath}/cars/drivers/add">
    Enter car id: <input required="" type="number" name="car_id">
    Enter driver id: <input required="" type="number" name="driver_id">

    <button type="submit">Add</button>
</form>
</body>
</html>
