<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create manufacturer</title>
</head>
<body>
<h1>Please, enter information about new manufacturer</h1>

<form method="post" action="${pageContext.request.contextPath}/manufacturers/create">
    Enter manufacturer name: <input type="text" name="name" required>
    Enter manufacturer country: <input type="text" name="country" required>

    <button type="submit">Create</button>
</form>
</body>
</html>
