<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create car</title>
</head>
<body>
<h1>Please, enter information about new car</h1>

<h4 style="color: red">${message}</h4>

<form method="post" action="${pageContext.request.contextPath}/cars/create">
    Enter car model: <input required="" type="text" name="model">
    Enter car manufacturer id: <input required="" type="number" name="manufacturerId">

    <button type="submit">Create</button>
</form>
</body>
</html>
