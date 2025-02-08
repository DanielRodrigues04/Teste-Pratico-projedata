package suaempresa;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.List;

@WebServlet("/editarFuncionario")
public class EditarFuncionarioServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

 // Método GET para mostrar os dados do funcionário na JSP
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idStr = request.getParameter("id"); // Mudando para ID, um identificador único

        if (idStr != null) {
            try {
                // Converte o id para um número (long ou int, dependendo do seu modelo)
                long id = Long.parseLong(idStr);

                // Obtém a lista de funcionários
                List<Funcionario> funcionarios = ListarFuncionariosServlet.getFuncionarios();

                // Procura o funcionário na lista
                for (Funcionario f : funcionarios) {
                    if (f.getId() == id) { // Comparando o ID
                        // Passa os dados do funcionário para a JSP
                        request.setAttribute("nome", f.getNome());
                        request.setAttribute("salario", f.getSalario());
                        request.setAttribute("funcao", f.getFuncao());
                        request.setAttribute("id", f.getId()); // Passa o ID também para ser usado no formulário
                        // Redireciona para a JSP de edição
                        request.getRequestDispatcher("/editarFuncionario.jsp").forward(request, response);
                        return;
                    }
                }
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Funcionário não encontrado.");
            } catch (NumberFormatException e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID inválido.");
            }
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID do funcionário ausente.");
        }
    }

 // Método POST para processar a edição do funcionário
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idStr = request.getParameter("id"); // Agora vamos obter o ID
        String novoSalarioStr = request.getParameter("salario");
        String novaFuncao = request.getParameter("funcao");

        // Verifica se todos os parâmetros necessários foram passados
        if (idStr == null || novoSalarioStr == null || novaFuncao == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Parâmetros ausentes.");
            return;
        }

        try {
            // Converte o ID e o salário
            long id = Long.parseLong(idStr);
            BigDecimal novoSalario = new BigDecimal(novoSalarioStr);

            // Obtém a lista de funcionários
            List<Funcionario> funcionarios = ListarFuncionariosServlet.getFuncionarios();

            // Procura o funcionário na lista
            boolean funcionarioEncontrado = false;
            for (Funcionario f : funcionarios) {
                if (f.getId() == id) { // Usando ID como identificação única
                    f.setSalario(novoSalario);
                    f.setFuncao(novaFuncao);
                    funcionarioEncontrado = true;
                    break;
                }
            }

            if (funcionarioEncontrado) {
                // Se o funcionário foi encontrado e editado, redireciona para a lista de funcionários
                response.sendRedirect("listarFuncionarios");
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Funcionário não encontrado.");
            }

        } catch (NumberFormatException e) {
            // Caso o salário não seja um número válido
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "O salário informado não é válido.");
        }
    }
}
