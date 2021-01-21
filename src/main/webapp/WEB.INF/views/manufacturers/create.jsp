<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create manufacturer</title>
</head>
<body>
<h1>Please, enter information about new manufacturer</h1>

<h4 style="color: red">${message}</h4>

<form method="post" action="${pageContext.request.contextPath}/manufacturers/create">
    Enter manufacturer name: <input required="" type="text" name="name">
    Enter manufacturer country: <input required="" type="text" name="country">

    <button type="submit">Create</button>
</form>
</body>
</html>
