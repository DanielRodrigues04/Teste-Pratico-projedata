package suaempresa;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Principal {
    @SuppressWarnings("deprecation")
	public static void main(String[] args) {
        // 3.1 – Inserir todos os funcionários
        List<Funcionario> funcionarios = new ArrayList<>();
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

        // 3.2 – Remover o funcionário João da lista
        funcionarios.removeIf(f -> f.getNome().equals("João"));

        // 3.3 – Imprimir todos os funcionários
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();  // Formatação de moeda
        funcionarios.forEach(f -> {
            String salarioFormatado = currencyFormat.format(f.getSalario());
            System.out.println(f.getNome() + " | " + f.getDataNascimento().format(dateFormatter) + " | " + salarioFormatado);
        });

        // 3.4 – Aumento de 10% no salário
        funcionarios.forEach(f -> f.setSalario(f.getSalario().multiply(new BigDecimal("1.10"))));

        // 3.5 – Agrupar os funcionários por função
        Map<String, List<Funcionario>> funcionariosPorFuncao = funcionarios.stream()
                .collect(Collectors.groupingBy(Funcionario::getFuncao));

        // 3.6 – Imprimir os funcionários agrupados por função
        System.out.println("\nFuncionários agrupados por função:");
        funcionariosPorFuncao.forEach((funcao, lista) -> {
            System.out.println("Função: " + funcao);
            lista.forEach(f -> System.out.println(f.getNome()));
        });

     // 3.8 – Imprimir os funcionários que fazem aniversário no mês 10 e 12, agrupados por função
        System.out.println("\nFuncionários que fazem aniversário no mês 10 e 12, agrupados por função:");

        Map<String, List<Funcionario>> funcionariosPorFuncaoAniversario = funcionarios.stream()
            .filter(f -> f.getDataNascimento().getMonthValue() == 10 || f.getDataNascimento().getMonthValue() == 12)
            .collect(Collectors.groupingBy(Funcionario::getFuncao));

        funcionariosPorFuncaoAniversario.forEach((funcao, lista) -> {
            System.out.println("Função: " + funcao);
            lista.forEach(f -> System.out.println(f.getNome() + " - " + f.getDataNascimento()));
        });

        // 3.9 – Imprimir o funcionário com a maior idade
        Funcionario maisVelho = funcionarios.stream()
                .min(Comparator.comparing(f -> f.getDataNascimento()))
                .orElse(null);

        if (maisVelho != null) {
            System.out.println("\nFuncionário mais velho: " + maisVelho.getNome() + " - " + maisVelho.getDataNascimento().getYear());
        }

        // 3.10 – Imprimir a lista de funcionários por ordem alfabética
        System.out.println("\nFuncionários por ordem alfabética:");
        funcionarios.stream()
                .sorted(Comparator.comparing(Funcionario::getNome))
                .forEach(f -> System.out.println(f.getNome()));

        // 3.11 – Imprimir o total dos salários dos funcionários
        BigDecimal totalSalarios = funcionarios.stream()
                .map(Funcionario::getSalario)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        System.out.println("\nTotal dos salários: " + currencyFormat.format(totalSalarios));

        // 3.12 – Imprimir quantos salários mínimos ganha cada funcionário
        final BigDecimal salarioMinimo = new BigDecimal("1212.00");
        System.out.println("\nFuncionários e seus salários em salários mínimos:");
        funcionarios.forEach(f -> {
            BigDecimal salariosMinimos = f.getQuantidadeSalariosMinimos(salarioMinimo);
            System.out.println(f.getNome() + " ganha " + salariosMinimos.setScale(2, BigDecimal.ROUND_HALF_UP) + " salários mínimos");
        });
    }
}
