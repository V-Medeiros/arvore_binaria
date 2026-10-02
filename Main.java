import java.util.Scanner;

class Node {
    char letra;
    Node esquerda;
    Node direita;
}

class ArvoreMorse {
    Node raiz = new Node();

    void carregar() {
        String letras = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        String[] codigos = {
            ".-", "-...", "-.-.", "-..", ".", "..-.", "--.", "....", "..",
            ".---", "-.-", ".-..", "--", "-.", "---", ".--.", "--.-", ".-.",
            "...", "-", "..-", "...-", ".--", "-..-", "-.--", "--..",
            "-----", ".----", "..---", "...--", "....-", ".....", "-....",
            "--...", "---..", "----."
        };

        for (int i = 0; i < letras.length(); i++) {
            inserir(codigos[i], letras.charAt(i));
        }
    }

    // Ponto vai para a esquerda. Traco vai para a direita.
    void inserir(String codigo, char letra) {
        Node atual = raiz;

        for (int i = 0; i < codigo.length(); i++) {
            if (codigo.charAt(i) == '.') {
                if (atual.esquerda == null) {
                    atual.esquerda = new Node();
                }
                atual = atual.esquerda;
            } else {
                if (atual.direita == null) {
                    atual.direita = new Node();
                }
                atual = atual.direita;
            }
        }

        atual.letra = Character.toUpperCase(letra);
    }

    // Procura a letra e retorna o caminho ate ela.
    String procurar(Node no, char letra, String caminho) {
        if (no == null) {
            return null;
        }
        if (no.letra != '\0' && no.letra == letra) {
            return caminho;
        }

        String codigo = procurar(no.esquerda, letra, caminho + ".");
        if (codigo != null) {
            return codigo;
        }
        return procurar(no.direita, letra, caminho + "-");
    }

    String codificar(String texto) {
        texto = texto.toUpperCase();
        String resultado = "";

        for (int i = 0; i < texto.length(); i++) {
            if (i > 0) {
                resultado = resultado + " ";
            }
            if (texto.charAt(i) == ' ') {
                resultado = resultado + "/";
            } else {
                resultado = resultado + procurar(raiz, texto.charAt(i), "");
            }
        }
        return resultado;
    }

    char buscarLetra(String codigo) {
        Node atual = raiz;

        for (int i = 0; i < codigo.length(); i++) {
            if (codigo.charAt(i) == '.') {
                atual = atual.esquerda;
            } else {
                atual = atual.direita;
            }
            if (atual == null) {
                return '?';
            }
        }
        if (atual.letra == '\0') {
            return '?';
        }
        return atual.letra;
    }

    String decodificar(String morse) {
        String[] codigos = morse.split(" ");
        String resultado = "";

        for (int i = 0; i < codigos.length; i++) {
            if (codigos[i].equals("/")) {
                resultado = resultado + " ";
            } else {
                resultado = resultado + buscarLetra(codigos[i]);
            }
        }
        return resultado;
    }

    // O recuo mostra o nivel. O sinal mostra o lado de cada filho.
    void exibir(Node no, String recuo, String sinal) {
        if (no != null) {
            if (no.letra == '\0') {
                System.out.println(recuo + sinal + " (vazio)");
            } else {
                System.out.println(recuo + sinal + " " + no.letra);
            }
            exibir(no.esquerda, recuo + "    ", ".");
            exibir(no.direita, recuo + "    ", "-");
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        ArvoreMorse arvore = new ArvoreMorse();
        arvore.carregar();
        String opcao = "";

        while (!opcao.equals("0")) {
            System.out.println("1 - Texto para Morse");
            System.out.println("2 - Morse para texto");
            System.out.println("3 - Buscar letra ou numero");
            System.out.println("4 - Inserir caractere");
            System.out.println("5 - Mostrar arvore");
            System.out.println("6 - Exemplo SOS");
            System.out.println("0 - Sair");
            System.out.print("Escolha: ");
            opcao = teclado.nextLine();

            if (opcao.equals("1")) {
                System.out.print("Digite o texto sem acentos: ");
                String texto = teclado.nextLine();
                System.out.println(arvore.codificar(texto));
            } else if (opcao.equals("2")) {
                System.out.print("Digite o Morse (espaco entre letras e / entre palavras): ");
                String morse = teclado.nextLine();
                System.out.println(arvore.decodificar(morse));
            } else if (opcao.equals("3")) {
                System.out.print("Digite a letra ou numero: ");
                String letra = teclado.nextLine().toUpperCase();
                System.out.println(arvore.procurar(arvore.raiz, letra.charAt(0), ""));
            } else if (opcao.equals("4")) {
                System.out.print("Digite o caractere: ");
                char letra = teclado.nextLine().charAt(0);
                System.out.print("Digite o codigo Morse: ");
                String codigo = teclado.nextLine();
                arvore.inserir(codigo, letra);
                System.out.println("Inserido.");
            } else if (opcao.equals("5")) {
                arvore.exibir(arvore.raiz, "", "RAIZ");
            } else if (opcao.equals("6")) {
                System.out.println(arvore.decodificar("..."));
                System.out.println(arvore.decodificar("---"));
                System.out.println(arvore.decodificar("... --- ..."));
                System.out.println(arvore.codificar("SOS"));
            }
        }
        teclado.close();
    }
}
