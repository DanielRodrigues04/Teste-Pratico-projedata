package suaempresa;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.util.Comparator;

@WebServlet("/listarFuncionarios")
public class ListarFuncionariosServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static List<Funcionario> funcionarios = new ArrayList<>();

    static {
        funcionarios.add(new Funcionario("Maria", LocalDate.of(2000, 10, 18), new BigDecimal("2009.44"), "Operador"));
        funcionarios.add(new Funcionario("João", LocalDate.of(1990, 5, 12), new BigDecimal("2284.38"), "Operador"));
        funcionarios.add(new Funcionario("Caio", LocalDate.of(1961, 5, 2), new BigDecimal("9836.14"), "Coordenador"));
        funcionarios.add(new Funcionario("Miguel", LocalDate.of(1988, 10, 14), new BigDecimal("19119.88"), "Diretor"));
        funcionarios.add(new Funcionario("Alice", LocalDate.of(1995, 1, 5), new BigDecimal("2234.68"), "Recepcionista"));
        funcionarios.add(new Funcionario("Heitor", LocalDate.of(1999, 11, 19), new BigDecimal("1582.72"), "Operador"));
        funcionarios.add(new Funcionario("Arthur", LocalDate.of(1993, 3, 31), new BigDecimal("4071.84"), "Contador"));
        funcionarios.add(new Funcionario("Laura", LocalDate.of(1994, 7, 8), new BigDecimal("3017.45"), "Gerente"));
        funcionarios.add(new Funcionario("Heloísa", LocalDate.of(2003, 5, 24), new BigDecimal("1606.85"), "Eletricista"));
        funcionarios.add(new Funcionario("Helena", LocalDate.of(1996, 9, 2), new BigDecimal("2799.93"), "Gerente"));

        // Remover "João" da lista
        funcionarios.removeIf(f -> f.getNome().equals("João"));
    }

    public static List<Funcionario> getFuncionarios() {
        return funcionarios;
    }

    private Funcionario getFuncionarioMaisVelho(List<Funcionario> funcionarios) {
        // Encontrar o funcionário mais velho, ou seja, o com a data de nascimento mais antiga
        Funcionario funcionarioMaisVelho = funcionarios.stream()
            .min(Comparator.comparingInt(f -> f.getDataNascimento().getYear()))
            .orElse(null);

        // Se encontrou o funcionário mais velho, calcula a idade dele
        if (funcionarioMaisVelho != null) {
            LocalDate dataAtual = LocalDate.now();
            Period periodo = Period.between(funcionarioMaisVelho.getDataNascimento(), dataAtual);
            int idade = periodo.getYears(); // Idade em anos

            // Atribuir a idade ao funcionário
            funcionarioMaisVelho.setIdade(idade);
        }

        return funcionarioMaisVelho;
    }

    // Método que lida com a requisição GET
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BigDecimal salarioMinimo = new BigDecimal("1212.00");

        // Agrupar os funcionários por função
        Map<String, List<Funcionario>> agrupadosPorFuncao = funcionarios.stream()
            .collect(Collectors.groupingBy(Funcionario::getFuncao));

        // Ordenar os funcionários dentro de cada função por nome
        for (Map.Entry<String, List<Funcionario>> entry : agrupadosPorFuncao.entrySet()) {
            entry.setValue(entry.getValue().stream()
                .sorted(Comparator.comparing(Funcionario::getNome)) // Ordenar por nome
                .collect(Collectors.toList()));
        }

        // Filtrar os funcionários que nasceram entre 1990 e 2000
        int anoInicio = 1990;
        int anoFim = 2000;
        List<Funcionario> filtradosPorData = funcionarios.stream()
            .filter(f -> f.getDataNascimento().getYear() >= anoInicio && f.getDataNascimento().getYear() <= anoFim)
            .collect(Collectors.toList());

        // Filtrar os aniversariantes de Outubro ou Dezembro
        Map<String, List<Funcionario>> aniversariantesAgrupados = funcionarios.stream()
            .filter(f -> f.getDataNascimento().getMonthValue() == 10 || f.getDataNascimento().getMonthValue() == 12)
            .collect(Collectors.groupingBy(Funcionario::getFuncao));

        // Calcular o total de salários
        BigDecimal totalSalarios = funcionarios.stream()
            .map(Funcionario::getSalario)  // Obter o salário de cada funcionário
            .reduce(BigDecimal.ZERO, BigDecimal::add);  // Somar todos os salários

        // Encontrar o funcionário mais velho
        Funcionario funcionarioMaisVelho = getFuncionarioMaisVelho(funcionarios);

        // Passar os dados para o JSP
        request.setAttribute("salarioMinimo", salarioMinimo);
        request.setAttribute("funcionarios", funcionarios);
        request.setAttribute("agrupadosPorFuncao", agrupadosPorFuncao);
        request.setAttribute("filtradosPorData", filtradosPorData);
        request.setAttribute("aniversariantesAgrupados", aniversariantesAgrupados);
        request.setAttribute("totalSalarios", totalSalarios);  // Enviar o total para o JSP
        request.setAttribute("funcionarioMaisVelho", funcionarioMaisVelho); // Enviar o funcionário mais velho
        request.getRequestDispatcher("/pagina.jsp").forward(request, response);
    }

    // Método que lida com a requisição POST para aumentar o salário
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Receber o ID do funcionário para aumentar o salário
        String idParam = request.getParameter("id");
        if (idParam != null) {
            try {
                int id = Integer.parseInt(idParam);
                
                // Verificar se o funcionário existe
                Funcionario funcionario = funcionarios.stream()
                    .filter(f -> f.getId() == id)
                    .findFirst()
                    .orElse(null);

                if (funcionario != null) {
                    // Aumentar o salário em 10%
                    funcionario.aumentarSalario(new BigDecimal("10"));
                }

            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        // Redirecionar para o método GET após atualizar o salário
        response.sendRedirect("listarFuncionarios");
    }
}
