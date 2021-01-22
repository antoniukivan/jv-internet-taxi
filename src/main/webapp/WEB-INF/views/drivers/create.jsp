<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Registration</title>
</head>
<body>
<h1>Please, enter information about driver</h1>

<form method="post" action="${pageContext.request.contextPath}/drivers/create">
    Enter driver name: <input type="text" name="name" required>
    Enter driver license number: <input type="text" name="license_number" required>
    Enter login: <input type="text" name="login" required>
    Enter password: <input type="text" name="password" required>

    <button type="submit">Registration</button>
</form>
</body>
</html>
