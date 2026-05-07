import java.util.ArrayList;
import java.util.List;

public class Conta {
    private String titular;
    private String agencia;
    private String tipoConta;
    private int numeroConta;
    
 
    private double saldo;
    private double limite;
    
    private String senha;
    private boolean contaAtivada;
    private int tentativasAcesso;
    
    private List<String> extrato;
    
    // Construtor
    public Conta(String titular, String agencia, String tipoConta, int numeroConta, double saldo, double limite, String senha) {
        this.titular = titular;
        this.agencia = agencia;    
        this.tipoConta = tipoConta;
        this.numeroConta = numeroConta;
        this.saldo = saldo;
        this.limite = limite;
        this.senha = senha;
        this.contaAtivada = true;
        this.tentativasAcesso = 0;
        
        // Uso correto do operador diamante <>
        this.extrato = new ArrayList<>();
        
        registrarTransacao("Abertura de conta com saldo inicial de R$ " + String.format("%.2f", saldo));
    }
    
    // Registra a movimentacao na lista do extrato
    private void registrarTransacao(String mensagem) {
        this.extrato.add(mensagem);
    }
    
    // Validaca de segurança
    public boolean validarSenha(String senhaInformada) {
        if (!contaAtivada) {
            System.out.println("Conta bloqueada, procure um gerente.");
            return false;
        }
        
        if (this.senha.equals(senhaInformada)) {
            this.tentativasAcesso = 0; // Reseta as tentativas se acertar
            return true;
        } else {
            this.tentativasAcesso++;
            if (tentativasAcesso >= 3) {
                this.contaAtivada = false;
                System.out.println("Senha incorreta pela 3ª vez. Conta BLOQUEADA.");
            } else {
                // Espaçamento corrigido para a mensagem não ficar grudada
                System.out.println("Senha incorreta. Tentativas: " + tentativasAcesso + " de 3 para bloquear.");
            }
            return false;
        }
    }

    // Operacoes Bancarias
    
    public void depositar(double valor) {
        if (valor > 0) {
            this.saldo += valor;
            registrarTransacao("Depósito: + R$ " + String.format("%.2f", valor));
            System.out.println("Depósito realizado com sucesso!");
        } else {
            System.out.println("Valor de depósito inválido.");
        }
    }

    public boolean sacar(double valor) {
        if (valor <= 0) {
            System.out.println("Valor de saque inválido.");
            return false;
        }
        
        double saldoDisponivel = this.saldo + this.limite;
        
        if (valor <= saldoDisponivel) {
            this.saldo -= valor;
            registrarTransacao("Saque: - R$ " + String.format("%.2f", valor));
            System.out.println("Saque realizado. Retire seu dinheiro.");
            return true;
        } else {
            System.out.println("Saldo e limite insuficientes para este saque.");
            return false;
        }
    }

    public boolean transferir(Conta contaDestino, double valor) {
        if (valor <= 0) {
            System.out.println("Valor inválido para transferência.");
            return false;
        }

        double saldoDisponivel = this.saldo + this.limite;

        if (valor <= saldoDisponivel) {
            this.saldo -= valor;
            this.registrarTransacao("Transferência enviada para " + contaDestino.getTitular() + ": - R$ " + String.format("%.2f", valor));
            
            contaDestino.saldo += valor;
            contaDestino.registrarTransacao("Transferência recebida de " + this.titular + ": + R$ " + String.format("%.2f", valor));

            System.out.println("Transferência realizada com sucesso!");
            return true;
        } else {
            System.out.println("Saldo insuficiente para realizar a transferência.");
            return false;
        }
    }

    public void exibirExtrato() {
        System.out.println("\n=== EXTRATO BANCÁRIO ===");
        System.out.println("Titular: " + this.titular + " | Ag: " + this.agencia + " | CC: " + this.numeroConta);
        System.out.println("------------------------");
        for (String transacao : this.extrato) {
            System.out.println(transacao);
        }
        System.out.println("------------------------");
        System.out.println("Saldo Atual: R$ " + String.format("%.2f", this.saldo));
        System.out.println("Limite Disponível: R$ " + String.format("%.2f", this.limite));
        System.out.println("========================\n");
    }
    
    // GETTERS 
    
    
    public double getSaldo() {
        return saldo;
    }
    
    public double getLimite() {
        return limite;
    }
    
    public String getTitular() {
        return titular;
    }
    
    public String getTipoconta() {
        return tipoConta;
    }
}
