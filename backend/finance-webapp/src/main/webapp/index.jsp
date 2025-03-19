<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
    <title>Login</title>
</head>
<body>
    <h2>Login</h2>
    <form action="LoginServlet" method="post">
        Usuário: <input type="text" name="username" required><br>
        Senha: <input type="password" name="password" required><br>
        <input type="submit" value="Entrar">
    </form>
    <p>Não tem uma conta? <a href="register.jsp">Cadastre-se</a></p>
</body>
</html>
