<html>
<body>
<h2>Hello World!</h2>

<%
    double num = Math.random();
    if (num > 0.95) {
%>
<h2>You'll have a luck day!</h2><p>(<%= num %>)</p>
<%
} else {
%>
<h2>Well, life goes on ... </h2><p>(<%= num %>)</p>
<%
    }
%>

</body>
</html>
