import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        
        //  Criando duas contas falsas na memória apenas para podermos testar o sistema
        Conta contaVitor = new Conta("Vitor Ferreira", "0001", "Corrente", 12345, 1500.0, 500.0, "1234");
        Conta contaThiago = new Conta("Thiago Cavalcante", "0001", "Corrente", 54321, 200.0, 100.0, "4321");

        System.out.println("===================================");
        System.out.println("   BEM-VINDO AO CAIXA ELETRONICO     ");
        System.out.println("===================================");

        // Etapa de Login
        boolean autenticado = false;
        int tentativas = 0;
        
        while (!autenticado && tentativas < 3) {
            System.out.print("Digite sua senha para acessar (Dica: 1234): ");
            String senhaDigitada = teclado.nextLine();
            
            autenticado = contaVitor.validarSenha(senhaDigitada);
            tentativas++;
            
            if (!autenticado && tentativas >= 3) {
                 System.out.println("Sistema encerrado por segurança.");
                 teclado.close();
                 return; // Sai do programa
            }
        }

        // Menu Interativo Principal 
        int opcao = 0;
        do {
            try {
                System.out.println("\n=== MENU PRINCIPAL ===");
                System.out.println("1 - Ver Saldo e Limite");
                System.out.println("2 - Sacar");
                System.out.println("3 - Depositar");
                System.out.println("4 - Transferir");
                System.out.println("5 - Ver Extrato");
                System.out.println("6 - Sair");
                System.out.print("Escolha uma operação: ");
                
                opcao = teclado.nextInt();
                
                switch (opcao) {
                    case 1: // Saldo
                        System.out.println("\n--- SALDO ---");
                        System.out.println("Saldo disponível: R$ " + String.format("%.2f", contaVitor.getSaldo()));
                        System.out.println("Limite disponível: R$ " + String.format("%.2f", contaVitor.getLimite()));
                        break;
                        
                    case 2: // Sacar
                        System.out.print("\nDigite o valor para sacar: R$ ");
                        double valorSaque = teclado.nextDouble();
                        contaVitor.sacar(valorSaque);
                        break;
                        
                    case 3: // Depositar
                        System.out.print("\nDigite o valor para depositar: R$ ");
                        double valorDeposito = teclado.nextDouble();
                        contaVitor.depositar(valorDeposito);
                        break;
                        
                    case 4: // Transferir
                        System.out.print("\nDigite o valor para transferir para " + contaThiago.getTitular() + ": R$ ");
                        double valorTransferencia = teclado.nextDouble();
                        contaVitor.transferir(contaThiago, valorTransferencia);
                        break;
                        
                    case 5: // Extrato
                        contaVitor.exibirExtrato();
                        break;
                        
                    case 6: // Sair
                        System.out.println("\nObrigado por usar nosso banco. Volte sempre!");
                        break;
                        
                    default:
                        System.out.println("\nOpção inválida! Digite um número de 1 a 6.");
                }
                
            } catch (InputMismatchException e) {
                // Se o usuário digitar uma letra, o programa não quebra, ele cai aqui:
                System.out.println("\n[ERRO DE SISTEMA] Entrada inválida! Por favor, digite apenas números.");
                teclado.nextLine();
            }
            
        } while (opcao != 6);

        teclado.close();
    }
}
