<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Registration</title>
</head>
<body>
<h1>Please, enter information about driver</h1>

<h4 style="color: red">${errorMsg}</h4>

<form method="post" action="${pageContext.request.contextPath}/drivers/create">
    Enter driver name: <input type="text" name="name" required>
    Enter driver license number: <input type="text" name="license_number" required>
    Enter login: <input type="text" name="login" required>
    Enter your password: <input type="password" name="password" required>

    <button type="submit">Registration</button>
</form>
</body>
</html>
