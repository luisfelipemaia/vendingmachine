package maquina_venda;

import java.util.*;
import java.io.*;
import java.nio.charset.StandardCharsets;

public class VendingMachine {
    static int[] precovetor;
    static int[] quantidadevetor;
    static String[] nomesvetor;
    static String caminho = "C:\\Users\\Dyego\\Documents\\LOG_TURMA_A\\PI\\src\\maquina_venda\\produtos.csv";

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        Scanner scn = new Scanner(System.in);
        int menu = 0;
//        lercsv();
//        String[] data = {caminho}; // Cria um array com o caminho
//        pegarqtd(data);
//        pegarpreco(data); // Chama pegarpreco passando o array
//        pegarprodutos(data);

        do {
        	lercsv();
            String[] data = {caminho}; // Cria um array com o caminho
            pegarqtd(data);
            pegarpreco(data); // Chama pegarpreco passando o array
            pegarprodutos(data);
            menu = 0;
            System.out.println("Faça seu pedido: \r\n"
                    + "Cód 	Produtos			Preço		Qnt\r\n"
                    + "1	Batata Frita Rufles 90g		R$5,00		"+quantidadevetor[0]+"\r\n"
                    + "2	Doritos 400g			R$20,00		"+quantidadevetor[1]+"\r\n"
                    + "3	Provolone desidratado 100g	R$35,00		"+quantidadevetor[2]+"\r\n"
                    + "4	Bala de algas marinhas 200g	R$15,00		"+quantidadevetor[3]+"\r\n"
                    + "5	Ovinhos de amendoim 200g	R$15,00		"+quantidadevetor[4]+"\r\n"
                    + "6	Snack mandiopã 80g		R$9,00		"+quantidadevetor[5]+"\r\n"
                    + "7	Pit stop snack 80g		R$5,00		"+quantidadevetor[6]+"\r\n"
                    + "8	Coca-cola zero 220ml		R$5,00		"+quantidadevetor[7]+"\r\n"
                    + "9	Fanta uva 350ml			R$5,00		"+quantidadevetor[8]+"\r\n"
                    + "10	Guaraná Kuat 2l			R$5,00		"+quantidadevetor[9]+"\r\n"
                    + "0    	Cancelar pedido"
                    + "\r\n"
                    + "Digite o Código do produto:");

            menu = scn.nextInt();

            if (menu > 0 && menu <= precovetor.length) { // Verifica se o menu está dentro dos limites
                pagamento(precovetor, menu - 1); // Passa o vetor e ajusta o índice
            } else if (menu != 0 && menu != 10000) {
                System.out.println("Opção inválida");
            } else if (menu == 0) {
                System.out.println("--------------------------------------------------------------------------------------");
                System.out.println("--------------------------------------------------------------------------------------");
                System.out.println("--------------------------------------------------------------------------------------");
            }

        } while (menu != 10000);
        
        System.out.println("MAQUINA DESLIGADA");

        scn.close();
    }

    static void pagamento(int[] precoArray, int menu) {

        Scanner scn = new Scanner(System.in);

        System.out.println("Quantidade: ");
        int pedido = scn.nextInt();

        if (pedido <= quantidadevetor[menu] && pedido > 0) {
            System.out.println("Forma de pagamento");
            System.out.println("1 - Dinheiro");
            System.out.println("2 - PIX");
            System.out.println("3 - Crédito");
            System.out.println("4 - Cancelar compra");
            int formapagam = scn.nextInt();

            int cedulas[] = {100, 50, 20, 10, 5, 2, 1};
            int devolucao[] = new int[7];
            boolean verifPag = false;
            int TotalCompra = precoArray[menu] * pedido;;
            switch (formapagam) {
                case 1 :

                    System.out.println("Valor do produto: R$" + precoArray[menu]);
                    int pagamento = 0;
                    int totpagamento = 0;

                    if (pedido <= quantidadevetor[menu]) {
                        do {
                            System.out.println("Valor total da compra: R$" + TotalCompra + ",00");
                            System.out.println("Insira o dinheiro");
                            pagamento = scn.nextInt();

                            // Verifica se o valor pago é uma cédula válida
                            boolean valorValido = false;
                            for (int cedula : cedulas) {
                                if (pagamento == cedula) {
                                    valorValido = true;
                                    break;
                                }
                            }

                            if (valorValido) {
                                totpagamento += pagamento;
                                System.out.println("Valor pago até o momento: R$" + totpagamento);
                            } else {
                                System.out.println("Valor inválido. Tente novamente!");
                            }

                        } while (totpagamento < TotalCompra);


                        int troco = totpagamento - TotalCompra;
                        System.out.println("Troco total: R$" + troco);

                        for (int i = 0; i < cedulas.length; i++) {
                            while (troco >= cedulas[i]) {
                                devolucao[i]++;
                                troco -= cedulas[i];
                            }
                        }

                        for (int i = 0; i < devolucao.length; i++) {
                            if (devolucao[i] != 0 && devolucao[i] < 7) {
                                System.out.println(devolucao[i] + " Cédula(s) de R$" + cedulas[i]);
                            } else if (devolucao[i] != 0){
                                System.out.println(devolucao[i] + " Moédas(s) de R$" + cedulas[i]);
                            }
                        }
                        System.out.println("--------------------------------------------------------------------------------------");

                        verifPag = true;
                    }

                    if (verifPag) {
                        AtualizadorCsv.atualizarQuantidade(menu + 1, pedido);
                        notafiscal(pedido, TotalCompra, menu);
                    }
                    break;

                case 2 :
                    int randomNum = (int)(Math.random() * 1000000001);
                    System.out.println("Insira o codigo PIX: "+ randomNum);
                    System.out.println("Codigo válido por 3 minutos");

                    String pagamentopix = scn.next();
                    if (pagamentopix.equals("sim")) {
                        System.out.println("Pagamento efetuado com sucesso.");
                        verifPag = true;
                    } else {
                        System.out.println("Pagamento não efetuado.");
                        System.out.println("--------------------------------------------------------------------------------------");
                        System.out.println("--------------------------------------------------------------------------------------");
                        System.out.println("--------------------------------------------------------------------------------------");
                    }

                    if (verifPag) {
                        AtualizadorCsv.atualizarQuantidade(menu + 1, pedido);
                        notafiscal(pedido, TotalCompra, menu);
                    }

                    break;

                case 3 :

                    System.out.println("Insira ou aproxime o cartão de debito na maquininha.");
                    scn.nextLine();

                    System.out.println("Insira a senha de 4 digitos: ");
                    String senha = scn.nextLine();

                    for(int i =0; i<3; i++) {
                        if (senha.equals("1357")) {
                            System.out.println("Pagamento efetuado com sucesso.");
                            verifPag = true;
                            i = i + 2;
                        } else {
                            System.out.println("Senha inválida. Tente novamente");
                        }
                    }


                    if (verifPag) {
                        AtualizadorCsv.atualizarQuantidade(menu + 1, pedido);
                        notafiscal(pedido, TotalCompra, menu);
                    } else {
                        System.out.println("Cartão bloqueado, entre em contato com seu banco!");
                        System.out.println("--------------------------------------------------------------------------------------");
                        System.out.println("--------------------------------------------------------------------------------------");
                        System.out.println("--------------------------------------------------------------------------------------");
                    }
                    break;

                case 4 :
                    System.out.println("Compra cancelada.");
                    break;

                default :
                    System.out.println("Opção invalida.");
                    break;
            }
        } else {
            System.out.println("Quantidade inválida ou fora de estoque");
        }
    }

    static void pegarpreco(String data[]) {
        File file = new File(data[0]);
        List<Integer> precoList = new ArrayList<>();

        try {
            Scanner scanner = new Scanner(file);
            if (scanner.hasNextLine()) {
                scanner.nextLine(); // Ignora cabeçalho
            }

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] columns = line.split(",");

                if (columns.length > 2) {
                    String precoStr = columns[2].trim().replace("R$", "").replace(",", "."); // Remove R$ e substitui vírgula por ponto
                    try {
                        int preco = (int) Double.parseDouble(precoStr); // Converte para inteiro
                        precoList.add(preco);
                    } catch (NumberFormatException e) {
                        System.err.println("Erro ao converter para inteiro: " + precoStr);
                    }
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        precovetor = new int[precoList.size()]; // Inicializa o vetor de preços
        for (int i = 0; i < precoList.size(); i++) {
            precovetor[i] = precoList.get(i);
        }

    }

    static void lercsv() {
        
        ArrayList<String[]> data = new ArrayList<>();

        // Verifica se o caminho do arquivo é válido
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(caminho), StandardCharsets.UTF_8))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(","); // Ajuste o delimitador conforme necessário
                data.add(values);
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo: " + e.getMessage());
        }
    }

    static void pegarqtd(String data[]) {
    	File file = new File(data[0]);
        List<Integer> quantidadeList = new ArrayList<>();

        try {
            Scanner scanner = new Scanner(file);
            // Ignora a primeira linha (cabeçalho)
            if (scanner.hasNextLine()) {
                scanner.nextLine();
            }

            // Lê cada linha do arquivo
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                // Divide a linha em colunas usando a vírgula como delimitador
                String[] columns = line.split(",");

                // Adiciona o valor da última coluna à lista após conversão para inteiro
                if (columns.length > 0) {
                    String quantidadeStr = columns[columns.length - 1].trim();
                    try {
                        int quantidade = Integer.parseInt(quantidadeStr);
                        quantidadeList.add(quantidade);
                    } catch (NumberFormatException e) {
                        System.err.println("Erro ao converter para inteiro: " + quantidadeStr);
                    }
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // Converte a lista para um vetor
        quantidadevetor = new int[quantidadeList.size()];
        for (int i = 0; i < quantidadeList.size(); i++) {
            quantidadevetor[i] = quantidadeList.get(i);
        }
    }

    public class AtualizadorCsv {

        public static void atualizarQuantidade(int codigoProduto, int pedido) {
            List<String> linhas = new ArrayList<>();

            try (BufferedReader br = new BufferedReader(new FileReader(caminho))) {
                String linha;
                // Lê todas as linhas do arquivo CSV
                while ((linha = br.readLine()) != null) {
                    linhas.add(linha);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Atualiza a quantidade do produto correspondente
            for (int i = 1; i < linhas.size(); i++) { // Começa em 1 para ignorar o cabeçalho
                String[] colunas = linhas.get(i).split(",");
                int codigo = Integer.parseInt(colunas[0].trim());
                int quantidadeAtual = Integer.parseInt(colunas[3].trim());

                if (codigo == codigoProduto) {
                    // Diminui a quantidade
                    int novaQuantidade = quantidadeAtual - pedido;
                    colunas[3] = String.valueOf(novaQuantidade); // Atualiza a quantidade na linha
                    linhas.set(i, String.join(",", colunas)); // Recria a linha com a nova quantidade
                    break; // Sai do loop após encontrar e atualizar o produto
                }
            }

            // Grava as alterações de volta no arquivo CSV
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(caminho))) {
                for (String novaLinha : linhas) {
                    bw.write(novaLinha);
                    bw.newLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    static void pegarprodutos(String data[]) {
    	File file = new File(data[0]);
        List<String> nomesList = new ArrayList<>();

        try {
            Scanner scanner = new Scanner(file);
            if (scanner.hasNextLine()) {
                scanner.nextLine(); // Ignora cabeçalho
            }

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] columns = line.split(",");
                if (columns.length > 1) { // Verifica se há pelo menos duas colunas
                    String nomeProduto = columns[1].trim(); // A coluna de nome é a segunda (índice 1)
                    nomesList.add(nomeProduto);
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // Converte a lista para um vetor
        nomesvetor = new String[nomesList.size()];
        for (int i = 0; i < nomesList.size(); i++) {
            nomesvetor[i] = nomesList.get(i);
        }
    }

    static void notafiscal(int pedido, int TotalCompra, int menu) {

        // Verifica se o menu está dentro dos limites
        if (menu +1 > 0 && menu + 1 <= nomesvetor.length) {
            // Cálculo do valor total
        	System.out.println("Nota Fiscal: ");
            System.out.println("Produto: " + nomesvetor[menu] + " 	Valor: R$" + precovetor[menu] + ",00  Quantidade: " + (pedido) + " 	Valor total: R$" + TotalCompra + ",00");
            System.out.println("--------------------------------------------------------------------------------------");
            System.out.println("--------------------------------------------------------------------------------------");
            System.out.println("--------------------------------------------------------------------------------------");
        } else {
            System.out.println("\nOpção inválida.\n");
        }
    }
}

