<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Inserir V�deo</title>
    <style>
        body { font-family: Arial, sans-serif; text-align: center; }
        .container { max-width: 500px; margin: auto; padding: 20px; border: 1px solid #ccc; border-radius: 10px; }
        input, button { width: 100%; padding: 10px; margin: 10px 0; }
    </style>
</head>
<body>
    <div class="container">
        <h2>Adicionar Novo V�deo</h2>
        
        <% String mensagem = (String) request.getAttribute("mensagem"); %>
        <% if (mensagem != null) { %>
            <p style="color: red;"><%= mensagem %></p>
        <% } %>
        
        <form action="videos" method="post">
            <label for="titulo">T�tulo:</label>
            <input type="text" id="titulo" name="titulo" required>
            
            <label for="url">URL do V�deo:</label>
            <input type="text" id="url" name="url" required>
            
            <button type="submit">Adicionar V�deo</button>
        </form>
    </div>
</body>
</html>