package suaempresa;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Iterator;
import java.util.List;

@WebServlet("/excluirFuncionario")
public class ExcluirFuncionarioServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Recupera o ID do funcionário a ser excluído
        String idStr = request.getParameter("id");

        // Verifica se o ID foi passado
        if (idStr == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Parâmetro id ausente.");
            return;
        }

        try {
            // Converte o ID para um número inteiro
            long id = Long.parseLong(idStr);

            // Obtém a lista de funcionários
            List<Funcionario> funcionarios = ListarFuncionariosServlet.getFuncionarios();

            // Usando Iterator para remover o funcionário
            boolean funcionarioRemovido = false;
            Iterator<Funcionario> iterator = funcionarios.iterator();
            while (iterator.hasNext()) {
                Funcionario f = iterator.next();
                if (f.getId() == id) { // Verifica pelo ID
                    iterator.remove(); // Remove o funcionário
                    funcionarioRemovido = true;
                    break;
                }
            }

            if (funcionarioRemovido) {
                response.sendRedirect("listarFuncionarios"); // Redireciona de volta para a lista
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Funcionário não encontrado.");
            }

        } catch (NumberFormatException e) {
            // Caso o ID não seja válido
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID inválido.");
        }
    }
}

