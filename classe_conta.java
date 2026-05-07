import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
    private final int MAX_TENTATIVAS = 3;
    
    private List<String> extrato;
    
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
        
        this.extrato = new ArrayList<>();
        registrarTransacao("Abertura de conta. Saldo inicial: R$ " + String.format("%.2f", saldo));
    }
    
    private void registrarTransacao(String mensagem) {
        LocalDateTime agora = LocalDateTime.now();
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        this.extrato.add("[" + agora.format(formato) + "] " + mensagem);
    }
    
    public boolean validarSenha(String senhaInformada) {
        if (!contaAtivada) {
            System.out.println("\n[ERRO] Esta conta encontra-se BLOQUEADA.");
            return false;
        }
        
        if (this.senha.equals(senhaInformada)) {
            this.tentativasAcesso = 0; 
            return true;
        } else {
            this.tentativasAcesso++;
            if (tentativasAcesso >= MAX_TENTATIVAS) {
                this.contaAtivada = false;
                System.out.println("\n[ALERTA] Senha incorreta pela " + MAX_TENTATIVAS + "ª vez. Conta BLOQUEADA por segurança.");
            } else {
                System.out.println("\n[AVISO] Senha incorreta! Tentativa " + tentativasAcesso + " de " + MAX_TENTATIVAS + ".");
            }
            return false;
        }
    }

    public void depositar(double valor) {
        if (valor > 0) {
            this.saldo += valor;
            registrarTransacao("Depósito recebido: + R$ " + String.format("%.2f", valor));
            System.out.println("\nDepósito de R$ " + String.format("%.2f", valor) + " realizado com sucesso!");
        } else {
            System.out.println("\n[ERRO] Valor de depósito deve ser maior que zero.");
        }
    }

    public boolean sacar(double valor) {
        if (valor <= 0) {
            System.out.println("\n[ERRO] Valor de saque inválido.");
            return false;
        }
        
        double saldoDisponivel = this.saldo + this.limite;
        
        if (valor <= saldoDisponivel) {
            this.saldo -= valor;
            registrarTransacao("Saque efetuado:   - R$ " + String.format("%.2f", valor));
            System.out.println("\nSaque autorizado. Por favor, retire seu dinheiro.");
            return true;
        } else {
            System.out.println("\n[ERRO] Saldo e limite insuficientes para este saque.");
            return false;
        }
    }

    public boolean transferir(Conta contaDestino, double valor) {
        if (contaDestino == null) {
            System.out.println("\n[ERRO] Conta de destino não encontrada.");
            return false;
        }
        if (this == contaDestino) {
            System.out.println("\n[ERRO] Você não pode transferir dinheiro para a própria conta.");
            return false;
        }
        if (valor <= 0) {
            System.out.println("\n[ERRO] Valor inválido para transferência.");
            return false;
        }

        double saldoDisponivel = this.saldo + this.limite;

        if (valor <= saldoDisponivel) {
            this.saldo -= valor;
            this.registrarTransacao("PIX enviado para " + contaDestino.getTitular() + ": - R$ " + String.format("%.2f", valor));
            
            contaDestino.saldo += valor;
            contaDestino.registrarTransacao("PIX recebido de " + this.titular + ": + R$ " + String.format("%.2f", valor));

            System.out.println("\nTransferência de R$ " + String.format("%.2f", valor) + " para " + contaDestino.getTitular() + " realizada!");
            return true;
        } else {
            System.out.println("\n[ERRO] Saldo insuficiente para realizar a transferência.");
            return false;
        }
    }

    public void exibirExtrato() {
        System.out.println("\n================ EXTRATO BANCÁRIO ================");
        System.out.println("Titular: " + this.titular + " | Ag: " + this.agencia + " | CC: " + this.numeroConta);
        System.out.println("--------------------------------------------------");
        for (String transacao : this.extrato) {
            System.out.println(transacao);
        }
        System.out.println("--------------------------------------------------");
        System.out.println("Saldo Atual: R$ " + String.format("%.2f", this.saldo));
        System.out.println("Limite Disp: R$ " + String.format("%.2f", this.limite));
        System.out.println("==================================================\n");
    }
    
    public double getSaldo() { return saldo; }
    public double getLimite() { return limite; }
    public String getTitular() { return titular; }
    public boolean isContaAtivada() { return contaAtivada; }
}
