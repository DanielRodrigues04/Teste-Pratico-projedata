<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Teste Prático</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;500&display=swap" rel="stylesheet">
    <style>
        body {
            font-family: 'Roboto', sans-serif;
            background-color: #f4f7fa;
        }
        .container {
            margin-top: 40px;
        }
        .table {
            border: 1px solid #ddd;
        }
        .table th {
            background-color: #007bff;
            color: white;
        }
        .table td {
            text-align: center;
        }
        .table-striped tbody tr:nth-of-type(odd) {
            background-color: #f2f2f2;
        }
        .card {
            border-radius: 8px;
        }
        .card-header {
            background-color: #007bff;
            color: white;
            font-size: 1.25rem;
        }
        .table-footer {
            margin-top: 20px;
        }
        .action-buttons a {
            margin: 0 5px;
            padding: 5px 10px;
            color: white;
            text-decoration: none;
            border-radius: 5px;
        }
        .action-buttons .edit {
            background-color: #ffc107;
        }
        .action-buttons .delete {
            background-color: #dc3545;
        }
    </style>
</head>
<body>

<div class="container">
    <div class="card">
        <div class="card-header">
            <h4>Lista de Funcionários</h4>
        </div>
        <div class="card-body">
            <table class="table table-striped">
                <thead>
                    <tr>
                        <th>Função</th>
                        <th>Nome</th>
                        <th>Data de Nascimento</th>
                        <th>Salário</th>
                        <th>Salários Mínimos</th>
                        <th>Ações</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="entry" items="${agrupadosPorFuncao}">
                        <!-- Agrupando funcionários por função -->
                        <c:forEach var="funcionario" items="${entry.value}">
                            <tr>
                                <!-- Exibindo a função apenas na primeira linha do grupo -->
                                <c:if test="${funcionario == entry.value[0]}">
                                    <td rowspan="${entry.value.size()}">${entry.key}</td>
                                </c:if>
                                <td>${funcionario.nome}</td>
                                <td>${funcionario.dataNascimento}</td>
                                <td>${funcionario.salario}</td>
                                <td>
                                    <c:out value="${funcionario.getQuantidadeSalariosMinimos(salarioMinimo)}"/>
                                </td>
                                <td class="action-buttons">
                                    <!-- Botão Editar -->
                                    <a href="editarFuncionario?id=${funcionario.id}" class="btn btn-warning">Editar</a>

                                    <!-- Formulário para excluir funcionário -->
                                    <form action="excluirFuncionario" method="POST" style="display:inline;">
                                        <input type="hidden" name="id" value="${funcionario.id}">
                                        <button type="submit" class="btn btn-danger" onclick="return confirm('Tem certeza que deseja excluir este funcionário?');">Excluir</button>
                                    </form>

                                    <!-- Formulário para aumentar salário -->
                                    <form action="listarFuncionarios" method="post" style="display:inline;">
                                        <input type="hidden" name="id" value="${funcionario.id}">
                                        <button type="submit" class="btn btn-success" onclick="return confirm('Tem certeza que deseja aumentar o salário deste funcionário em 10%?');">Aumentar 10%</button>
                                    </form>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:forEach>
                    <!-- Linha de Total de Salários -->
                    <tr>
                        <td colspan="3"><strong>Total de Salários:</strong></td>
                        <td><strong>${totalSalarios}</strong></td>
                        <td colspan="2"></td>
                    </tr>
                </tbody>
            </table>
        </div>
        <div class="card-footer table-footer">
            <div class="text-center">
                <p><strong>Total de Funcionários:</strong> ${funcionarios.size()}</p>
                <p><strong>Salário Mínimo:</strong> ${salarioMinimo}</p>
            </div>
        </div>
    </div>

    <!-- Tabela de Funcionários com Aniversário em Outubro ou Dezembro -->
    <div class="card mt-4">
        <div class="card-header">
            <h4>Funcionários com Aniversário em Outubro e Dezembro</h4>
        </div>
        <div class="card-body">
            <table class="table table-striped">
                <thead>
                    <tr>
                        <th>Função</th>
                        <th>Nome</th>
                        <th>Data de Nascimento</th>
                        <th>Salário</th>
                        <th>Salários Mínimos</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="entry" items="${aniversariantesAgrupados}">
                        <c:forEach var="funcionario" items="${entry.value}">
                            <tr>
                                <!-- Exibindo a função apenas na primeira linha do grupo -->
                                <c:if test="${funcionario == entry.value[0]}">
                                    <td rowspan="${entry.value.size()}">${entry.key}</td>
                                </c:if>
                                <td>${funcionario.nome}</td>
                                <td>${funcionario.dataNascimento}</td>
                                <td>${funcionario.salario}</td>
                                <td>
                                    <c:out value="${funcionario.getQuantidadeSalariosMinimos(salarioMinimo)}"/>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>

    <!-- Exibir o funcionário mais velho -->
    <div class="card mt-4">
        <div class="card-header">
            <h4>Funcionário Mais Velho</h4>
        </div>
        <div class="card-body">
            <p><strong>Nome:</strong> ${funcionarioMaisVelho.nome}</p>
            <p><strong>Idade:</strong> ${funcionarioMaisVelho.idade} anos</p>
        </div>
    </div>

</div>

<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.min.js"></script>

</body>
</html>
