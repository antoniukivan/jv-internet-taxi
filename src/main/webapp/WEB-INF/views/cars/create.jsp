<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create car</title>
</head>
<body>
<h1>Please, enter information about new car</h1>

<form method="post" action="${pageContext.request.contextPath}/cars/create">
    Enter car model: <input type="text" name="model" required>
    Enter car manufacturer id: <input type="number" name="manufacturer_id" required>

    <button type="submit">Create</button>
</form>
</body>
</html>
