<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Editar Funcionário</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f9;
            margin: 0;
            padding: 20px;
        }
        h1 {
            color: #333;
            text-align: center;
        }
        form {
            width: 80%;
            margin: 0 auto;
            background-color: #fff;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            padding: 20px;
            border-radius: 8px;
        }
        label {
            font-weight: bold;
            margin-right: 10px;
        }
        input[type="text"] {
            padding: 8px;
            width: 100%;
            margin-bottom: 10px;
            border: 1px solid #ddd;
            border-radius: 4px;
            box-sizing: border-box;
        }
        button {
            background-color: #4CAF50;
            color: white;
            padding: 10px 15px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 16px;
        }
        button:hover {
            background-color: #45a049;
        }
        .back-btn {
            background-color: #f0ad4e;
            color: white;
            padding: 8px 15px;
            border-radius: 4px;
            text-decoration: none;
            font-size: 16px;
        }
        .back-btn:hover {
            background-color: #ec971f;
        }
    </style>
</head>
<body>

    <h1>Editar Funcionário</h1>

    <!-- Formulário de edição -->
    <form action="editarFuncionario" method="POST">
    <!-- Nome do Funcionário (não editável) -->
    <label for="nome">Nome:</label>
    <input type="text" id="nome" name="nome" value="${nome}" readonly />
    <br><br>

    <!-- ID do Funcionário (oculto) -->
    <input type="hidden" name="id" value="${id}" />

    <!-- Novo Salário -->
    <label for="salario">Salário:</label>
    <input type="text" id="salario" name="salario" value="${salario}" required />
    <br><br>

    <!-- Nova Função -->
    <label for="funcao">Função:</label>
    <input type="text" id="funcao" name="funcao" value="${funcao}" required />
    <br><br>

    <!-- Botão para enviar o formulário -->
    <button type="submit">Salvar Alterações</button>
</form>


    <br>
    <!-- Botão para voltar para a lista -->
    <a href="listarFuncionarios" class="back-btn">Voltar para a lista de funcionários</a>

</body>
</html>
