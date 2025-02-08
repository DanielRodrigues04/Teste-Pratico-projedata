package suaempresa;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.Locale;

public class Funcionario extends Pessoa {
    private BigDecimal salario;
    private String funcao;
    private int idade; // Campo de idade

    public Funcionario(String nome, LocalDate dataNascimento, BigDecimal salario, String funcao) {
        super(nome, dataNascimento);
        this.salario = salario;
        this.funcao = funcao;
        this.idade = calcularIdade(); // Calcula a idade ao criar o objeto
    }

    public BigDecimal getSalario() {
        return salario;
    }

    public void setSalario(BigDecimal salario) {
        this.salario = salario.setScale(2, RoundingMode.HALF_UP);  // Utiliza RoundingMode para garantir 2 casas decimais
    }

    public String getFuncao() {
        return funcao;
    }

    public void setFuncao(String funcao) {
        this.funcao = funcao;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    // Método para calcular a quantidade de salários mínimos
    public BigDecimal getQuantidadeSalariosMinimos(BigDecimal salarioMinimo) {
        return this.salario.divide(salarioMinimo, 2, RoundingMode.HALF_UP);  // Utiliza RoundingMode para garantir 2 casas decimais
    }

    public void aumentarSalario(BigDecimal percentual) {
        BigDecimal aumento = this.salario.multiply(percentual).divide(new BigDecimal("100"));
        this.salario = this.salario.add(aumento).setScale(2, RoundingMode.HALF_UP);
    }

    // Método que calcula a idade do funcionário baseado na data de nascimento
    private int calcularIdade() {
        LocalDate dataAtual = LocalDate.now();
        Period periodo = Period.between(super.getDataNascimento(), dataAtual);
        return periodo.getYears(); // Retorna a idade em anos
    }

    @Override
    public String toString() {
        // Usando NumberFormat para formatar o salário com a moeda local
        @SuppressWarnings("deprecation")
        NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
        return super.toString() + ", Funcionario [salario=" + nf.format(salario) + ", funcao=" + funcao + ", idade=" + idade + "]";
    }
}
