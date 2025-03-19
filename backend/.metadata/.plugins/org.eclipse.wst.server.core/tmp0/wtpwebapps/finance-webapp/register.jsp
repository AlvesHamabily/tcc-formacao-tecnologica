<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
    <title>Cadastro</title>
</head>
<body>
    <h2>Cadastro</h2>
    <form action="RegisterServlet" method="post">
        Usuário: <input type="text" name="username" required><br>
        Senha: <input type="password" name="password" required><br>
        <input type="submit" value="Cadastrar">
    </form>
    <p>Já tem uma conta? <a href="index.jsp">Faça login</a></p>
</body>
</html>
