package valetinho;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Estacionamento {
    private String[] placas;
    private int totalVagas;
    private ArrayList<Integer> vagasLivres;
    String csvFile = "src/placas.csv";

    public Estacionamento(int n) {
    	
        totalVagas = n;
        placas = new String[n];
        vagasLivres = new ArrayList<Integer>();
        for (int i = 0; i < n; i++) {
            placas[i] = "livre";
            vagasLivres.add(i + 1);
        }
    }

    public void entrar(String placa, int vaga) {
        if (vaga >= 1 && vaga <= totalVagas && placas[vaga - 1].equals("livre")) {
            placas[vaga - 1] = placa;
            vagasLivres.remove(Integer.valueOf(vaga));
            gravarDados();
            System.out.println("Veículo com placa " + placa + " entrou na vaga " + vaga);
        } else {
            System.out.println("Vaga inválida ou ocupada.");
        }
    }

    public void sair(int vaga) {
        if (vaga >= 1 && vaga <= totalVagas && !placas[vaga - 1].equals("livre")) {
            String placa = placas[vaga - 1];
            placas[vaga - 1] = "livre";
            vagasLivres.add(vaga);
            gravarDados();
            System.out.println("Veículo com placa " + placa + " saiu da vaga " + vaga);
        } else {
            System.out.println("Vaga inválida ou vazia.");
        }
    }

    public int consultarPlaca(String placa) {
        for (int i = 0; i < totalVagas; i++) {
            if (placas[i].equals(placa)) {
                return i + 1;
            }
        }
        return -1;
    }

    public void transferir(int vaga1, int vaga2) {
        if (vaga1 >= 1 && vaga1 <= totalVagas && vaga2 >= 1 && vaga2 <= totalVagas &&
            !placas[vaga1 - 1].equals("livre") && placas[vaga2 - 1].equals("livre")) {
            String placa = placas[vaga1 - 1];
            placas[vaga1 - 1] = "livre";
            placas[vaga2 - 1] = placa;
            vagasLivres.remove(Integer.valueOf(vaga2));
            vagasLivres.add(vaga1);
            gravarDados();
            System.out.println("Veículo transferido da vaga " + vaga1 + " para a vaga " + vaga2);
        } else {
            System.out.println("Vaga inválida, ocupada ou transferência não permitida.");
        }
    }

    public String[] listarGeral() {
        return placas;
    }

    public ArrayList<Integer> listarLivres() {
        return vagasLivres;
    }

    public void gravarDados() {
        try {
            FileWriter arquivo = new FileWriter(new File(csvFile));
            for (int i = 0; i < totalVagas; i++	) {
            	String placa = placas[i];
            	int vaga = i + 1;
            	arquivo.write(placa + "," + vaga + "\n");	
            }
            arquivo.close();
        } catch (IOException e) {
            System.out.println("Erro ao gravar dados no arquivo placas.csv: " + e.getMessage());
        }
    }

    public void lerDados() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(csvFile));
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] dados = linha.split(",");
                if (dados.length == 2) {
                    String placa = dados[0];
                    int vaga = Integer.parseInt(dados[1]);
                    
                    placas[vaga - 1] = placa;
                    vagasLivres.remove(Integer.valueOf(vaga));
                }
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("Erro ao ler dados do arquivo placas.csv: " + e.getMessage());
        }
    }

    public void imprimirEstacionamento() {
        System.out.println("Estado atual do estacionamento:");
        for (int i = 0; i < totalVagas; i++) {
            System.out.println("Vaga " + (i + 1) + ": " + placas[i]);
        }
    }

}