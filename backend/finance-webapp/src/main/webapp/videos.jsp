<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.financialeducation.model.VideoBean" %>
<%@ page import="java.util.List" %>

<%
    List<VideoBean> videos = (List<VideoBean>) request.getAttribute("videos");
    VideoBean videoAtual = (VideoBean) request.getAttribute("videoAtual");
%>

<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Curso - Sicoob</title>
    <style>
        body { font-family: Arial, sans-serif; }
        .container { display: flex; }
        .sidebar { width: 250px; padding: 20px; background: #ddd; }
        .video-section { flex: 1; padding: 20px; text-align: center; }
        .video-container { width: 100%; max-width: 800px; margin: auto; }
        .controls { margin-top: 10px; }
        button { padding: 10px 15px; margin: 5px; cursor: pointer; }
    </style>
    <script>
	    function loadVideo(url, titulo) {
	        console.log("Alterando para:", titulo, url); // Debug no console
	        document.getElementById("videoFrame").src = url;
	        document.getElementById("videoTitle").innerText = titulo;
	    }
        function navigate(videoId, direction) {
            window.location.href = "videos?id=" + videoId + "&action=" + direction;
        }
    </script>
</head>
<body>
    <h1>Sicoob Instituto - Curso</h1>
    <div class="container">
        <div class="sidebar">
            <h2>Aulas</h2>
            <ul>
				<c:forEach var="video" items="${videos}">
				    <li>
				        <button onclick="loadVideo('${video.url}', `${video.titulo}`)">
				            ${video.titulo}
				        </button>
				    </li>
				</c:forEach>
            </ul>
            
        </div>

        <div class="video-section">
            <h2 id="videoTitle"><%= videoAtual != null ? videoAtual.getTitulo() : "Nenhum vídeo disponível" %></h2>
            <div class="video-container">
                <iframe id="videoFrame" width="800" height="450" 
                        src="<%= videoAtual != null ? videoAtual.getUrl() : "" %>" frameborder="0" allowfullscreen></iframe>
            </div>
            <div class="controls">
                <% if (videoAtual != null) { %>
                    <button onclick="navigate(<%= videoAtual.getId() %>, 'prev')">Retornar</button>
                    <button onclick="navigate(<%= videoAtual.getId() %>, 'next')">Avançar</button>
                <% } %>
            </div>
        </div>
    </div>
<a href="index.jsp">Home</a>
</body>
</html>
