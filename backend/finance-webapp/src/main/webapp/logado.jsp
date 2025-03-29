<%
    Integer points = (Integer) session.getAttribute("points");
    String nivel = (String) session.getAttribute("nivel");
    if (points == null) points = 0;
    if (nivel == null) nivel = "Iniciante";
%>

<p>Seus pontos: <%= points %></p>

<p>Nível: <%= nivel %></p>

<form action="PontosServlet" method="post">
    <button type="submit">Ganhar Pontos</button>
</form>
<a href="videos">curso</a>
<a href="professores.jsp">Area dos professores</a>
<a href="index.jsp">Sair</a>
