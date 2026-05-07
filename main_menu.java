public class Main {
    public static void main(String[] args) {
        java.util.Scanner teclado = new java.util.Scanner(System.in);
        
        Conta contaVitor = new Conta("Vitor Ferreira", "0001", "Corrente", 12345, 1500.0, 500.0, "1234");
        Conta contaThiago = new Conta("Thiago Cavalcante", "0001", "Corrente", 54321, 200.0, 100.0, "4321");

        while (true) {
            System.out.println("\n=====================================");
            System.out.println("      SISTEMA DE CAIXA ELETRÔNICO    ");
            System.out.println("=====================================");

            // 1. Etapa de Autenticação
            boolean autenticado = false;
            while (!autenticado) {
                if (!contaVitor.isContaAtivada()) {
                    System.out.println("\n[SISTEMA DESLIGANDO] Conta permanentemente bloqueada.");
                    teclado.close();
                    return; 
                }

                System.out.print("Digite sua senha para acessar (ou 'sair'): ");
        
                String entrada = teclado.nextLine().trim();
                
                if (entrada.equalsIgnoreCase("sair")) {
                    System.out.println("Encerrando sistema fisicamente...");
                    teclado.close();
                    return; 
                }
                
                autenticado = contaVitor.validarSenha(entrada);
            }

            // 2. Menu Principal
            int opcao = 0;
            do {
                try {
                    System.out.println("\n=== MENU PRINCIPAL ===");
                    System.out.println("Olá, " + contaVitor.getTitular() + "!");
                    System.out.println("1 - Ver Saldo e Limite");
                    System.out.println("2 - Sacar");
                    System.out.println("3 - Depositar");
                    System.out.println("4 - Transferir");
                    System.out.println("5 - Ver Extrato");
                    System.out.println("6 - Sair da Conta");
                    System.out.print("Escolha uma operação: ");
                    
                    // .trim() resolve espaços em branco invisíveis
                    opcao = Integer.parseInt(teclado.nextLine().trim());
                    
                    switch (opcao) {
                        case 1:
                            System.out.println("\n--- SALDO ---");
                            System.out.println("Saldo disponível: R$ " + String.format("%.2f", contaVitor.getSaldo()));
                            System.out.println("Limite disponível: R$ " + String.format("%.2f", contaVitor.getLimite()));
                            break;
                            
                        case 2:
                            System.out.print("\nDigite o valor para sacar: R$ ");
                            double valorSaque = Double.parseDouble(teclado.nextLine().replace(",", ".").trim());
                            contaVitor.sacar(valorSaque);
                            break;
                            
                        case 3:
                            System.out.print("\nDigite o valor para depositar: R$ ");
                            double valorDeposito = Double.parseDouble(teclado.nextLine().replace(",", ".").trim());
                            contaVitor.depositar(valorDeposito);
                            break;
                            
                        case 4:
                            System.out.print("\nDigite o valor para transferir para " + contaThiago.getTitular() + ": R$ ");
                            double valorTransferencia = Double.parseDouble(teclado.nextLine().replace(",", ".").trim());
                            contaVitor.transferir(contaThiago, valorTransferencia);
                            break;
                            
                        case 5:
                            contaVitor.exibirExtrato();
                            break;
                            
                        case 6:
                            System.out.println("\nEfetuando Logoff... Obrigado por usar nosso banco!");
                            break;
                            
                        default:
                            System.out.println("\nOpção inválida! Digite um número de 1 a 6.");
                    }
                    
                } catch (NumberFormatException e) { 
                    System.out.println("\n[ERRO DE SISTEMA] Entrada inválida! Por favor, digite apenas números.");
                    opcao = 0; 
                }
                
            } while (opcao != 6);
            
            System.out.println("\n--- TELA INICIAL (AGUARDANDO PRÓXIMO CLIENTE) ---");
        }
    }
}
