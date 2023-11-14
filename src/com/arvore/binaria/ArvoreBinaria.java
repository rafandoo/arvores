package com.arvore.binaria;

import java.util.LinkedList;
import java.util.Queue;

public class ArvoreBinaria {

    private class Nodo {

        // chave = pk
        private int chave;
        // dir, esq = referencia para os nodos filhos
        private Nodo dir,esq;

        public Nodo (int item) {
            this.chave = item;
            dir = esq = null;
        }
    }

    // raiz do metodo, não da classe
    Nodo raiz = null;

    // METODOS

    private boolean ehVazia(Nodo raiz) {
        return raiz == null;
    }

    public void inserir(int chave) {
        raiz = inserir(raiz, chave);
    }

    private Nodo inserir(Nodo raiz, int chave) {
        if (raiz == null) {
            raiz = new Nodo(chave);
            return raiz;
        }
        if (chave < raiz.chave) {
            raiz.esq = inserir(raiz.esq, chave);
        } else if (chave > raiz.chave) {
            raiz.dir = inserir(raiz.dir, chave);
        }
        return raiz;
    }

    public void remover(int chave) {
        raiz = remover(raiz, chave);
    }

    private Nodo remover(Nodo raiz, int chave) {
        if (raiz == null) {
            // arvore vazia
            return null;
        }
        if (chave < raiz.chave) {
            // chave a ser removida ta a esquerda
            raiz.esq = remover(raiz.esq, chave);
        } else if (chave > raiz.chave) {
            // chave a ser removida ta a direita
            raiz.dir = remover(raiz.dir, chave);
        } else {
            // encontramos o nodo a ser removido
            if (raiz.esq == null) {
                // caso em que o nodo não possui filhos a esquerda
                return raiz.dir;
            } else if (raiz.dir == null) {
                // caso em que o nodo não possui filhos a direita
                return raiz.esq;
            } else {
                // caso em que o nodo tem ambos os filhos
                // nodo sucessor sera o menor da subarvore a direita
                Nodo sucessor = encontrarSucessor(raiz.dir);
                // substituimos o valor do nodo a ser removido pelo valor do sucessor
                raiz.chave = sucessor.chave;
                raiz.dir = remover(raiz.dir, sucessor.chave);
            }
        }
        return raiz;
    }

    // CONSULTAS
    public void mostrarEmOrdemCrescente() {
        mostrarCrescente(raiz);
    }

    private void mostrarCrescente(Nodo raiz) {
        if (raiz != null) {
            mostrarCrescente(raiz.esq);
            System.out.println(raiz.chave);
            mostrarCrescente(raiz.dir);
        }
    }

    public void mostrarEmOrdemDecrescente() {
        mostrarDecrescente(raiz);
    }

    private void mostrarDecrescente(Nodo raiz) {
        if (raiz != null) {
            mostrarDecrescente(raiz.dir);
            System.out.println(raiz.chave);
            mostrarDecrescente(raiz.esq);
        }
    }

    public void mostrarPorNivel() {
        if (raiz == null) {
            System.out.println("Arvore vazia");
            return;
        }
        Queue<Nodo> fila = new LinkedList<>();
        fila.add(raiz);
        while (!fila.isEmpty()) {
            int tamanhoNivel = fila.size();
            for (int i = 0; i < tamanhoNivel; i++) {
                Nodo nodoAtual = fila.poll();
                if (nodoAtual != null) {
                    System.out.print(nodoAtual.chave + " ");
                    fila.add(nodoAtual.esq);
                    fila.add(nodoAtual.dir);
                } else {
                    System.out.print("- ");
                }
            }
            System.out.println();
        }
    }

    private Nodo encontrarSucessor(Nodo nodo) {
        while (nodo.esq != null) {
            nodo = nodo.esq;
        }
        return nodo;
    }

    public boolean buscar(int chave) {
        return buscarChave(raiz, chave);
    }

    private boolean buscarChave(Nodo raiz, int chave) {
        if (this.ehVazia(raiz)) {
            return false;
        }
        if (chave < raiz.chave) {
            return buscarChave(raiz.esq, chave);
        } else if (chave > raiz.chave) {
            return buscarChave(raiz.dir, chave);
        }
        return true;
    }

    public void mostrarArvore() {
        mostrar(raiz, 0);
    }

    private void mostrar(Nodo raiz, int nivel) {
        if (raiz != null) {
            mostrar(raiz.dir, nivel + 1);
            for (int i = 0; i < nivel; i++) {
                System.out.print("   ");
            }
            System.out.println(raiz.chave);
            mostrar(raiz.esq, nivel + 1);
        }
    }

    // ATV 1
    public int maior() {
        return maior(raiz);
    }

    private int maior(Nodo raiz) {
        if (raiz == null) {
            return 0;
        }
        int maior = raiz.chave;
        int maiorEsq = maior(raiz.esq);
        int maiorDir = maior(raiz.dir);
        if (maiorEsq > maior) {
            maior = maiorEsq;
        }
        if (maiorDir > maior) {
            maior = maiorDir;
        }
        return maior;
    }

    public int menor() {
        return menor(raiz);
    }

    private int menor(Nodo raiz) {
        if (raiz == null) {
            return 999999999;
        }
        int menor = raiz.chave;
        int menorEsq = menor(raiz.esq);
        int menorDir = menor(raiz.dir);
        if (menorEsq < menor) {
            menor = menorEsq;
        }
        if (menorDir < menor) {
            menor = menorDir;
        }
        return menor;
    }

    public void folhas() {
        folhas(raiz);
    }

    private void folhas(Nodo raiz) {
        if (raiz != null) {
            if (raiz.esq == null && raiz.dir == null) {
                System.out.println(raiz.chave);
            }
            folhas(raiz.esq);
            folhas(raiz.dir);
        }
    }

    public void ancestrais(int chave) {
        ancestrais(raiz, chave);
    }

    private boolean ancestrais(Nodo raiz, int chave) {
        if (raiz == null) {
            return false;
        }
        if (raiz.chave == chave) {
            return true;
        }
        if (ancestrais(raiz.esq, chave) || ancestrais(raiz.dir, chave)) {
            System.out.println(raiz.chave);
            return true;
        }
        return false;
    }

    public void descendentes(int chave) {
        descendentes(raiz, chave);
    }

    private boolean descendentes(Nodo raiz, int chave) {
        if (raiz == null) {
            return false;
        }

        if (raiz.chave == chave) {
            mostrarDescendentesRec(raiz);
            return true;
        }
        return descendentes(raiz.esq, chave) || descendentes(raiz.dir, chave);
    }

    private void mostrarDescendentesRec(Nodo nodo) {
        if (nodo == null) {
            return;
        }

        if (nodo.esq != null) {
            System.out.println(nodo.esq.chave);
            mostrarDescendentesRec(nodo.esq);
        }

        if (nodo.dir != null) {
            System.out.println(nodo.dir.chave);
            mostrarDescendentesRec(nodo.dir);
        }
    }

    public void subarvoreDireita(int chave) {
        subarvoreDireita(raiz, chave);
    }

    private void subarvoreDireita(Nodo raiz, int chave) {
        if (raiz == null) {
            return;
        }
        if (raiz.chave == chave) {
            mostrar(raiz.dir, 0);
            return;
        }
        subarvoreDireita(raiz.esq, chave);
        subarvoreDireita(raiz.dir, chave);
    }

    public void subarvoreEsquerda(int chave) {
        subarvoreEsquerda(raiz, chave);
    }

    private void subarvoreEsquerda(Nodo raiz, int chave) {
        if (raiz == null) {
            return;
        }
        if (raiz.chave == chave) {
            mostrar(raiz.esq, 0);
            return;
        }
        subarvoreEsquerda(raiz.esq, chave);
        subarvoreEsquerda(raiz.dir, chave);
    }

    public void converteEmListaEncadeada() {
        transformarEmLista(raiz);
        ListaEncadeada atual = listaHead;
        while (atual != null) {
            System.out.print(atual.valor + " ");
            atual = atual.prox;
        }
    }

    private static class ListaEncadeada {
        private int valor;
        private ListaEncadeada prox;

        public ListaEncadeada(int valor) {
            this.valor = valor;
            this.prox = null;
        }
    }

    private static ListaEncadeada listaHead = null;
    private static ListaEncadeada listaTail = null;

    private void addLista(int valor) {
        ListaEncadeada novo = new ListaEncadeada(valor);
        if (listaHead == null) {
            listaHead = listaTail = novo;
        } else {
            listaTail.prox = novo;
            listaTail = novo;
        }
    }

    private void transformarEmLista(Nodo raiz) {
        if (raiz == null) {
            return;
        }
        transformarEmLista(raiz.esq);
        addLista(raiz.chave);
        transformarEmLista(raiz.dir);
    }

    public void mostrarPares() {
        mostrarPares(raiz);
    }

    private void mostrarPares(Nodo raiz) {
        if (raiz != null) {
            if (raiz.chave % 2 == 0) {
                System.out.println(raiz.chave);
            }
            mostrarPares(raiz.esq);
            mostrarPares(raiz.dir);
        }
    }

    public int nivelChave(int chave) {
        return nivelChave(raiz, chave, 1);
    }

    private int nivelChave(Nodo raiz, int chave, int nivel) {
        if (raiz == null) {
            return 0;
        }
        if (raiz.chave == chave) {
            return nivel;
        }
        int nivelEsq = nivelChave(raiz.esq, chave, nivel + 1);
        if (nivelEsq != 0) {
            return nivelEsq;
        }
        return nivelChave(raiz.dir, chave, nivel + 1);
    }

    public int altura() {
        return altura(raiz);
    }

    private int altura(Nodo raiz) {
        if (raiz == null) {
            return 0;
        }
        int alturaEsq = altura(raiz.esq);
        int alturaDir = altura(raiz.dir);
        if (alturaEsq > alturaDir) {
            return alturaEsq + 1;
        } else {
            return alturaDir + 1;
        }
    }

    public int tamanho() {
        return tamanho(raiz);
    }

    private int tamanho(Nodo raiz) {
        if (raiz == null) {
            return 0;
        }
        return tamanho(raiz.esq) + 1 + tamanho(raiz.dir);
    }

    public void inserirNaoRecursivo(int chave) {
        inserirNaoRecursivo(raiz, chave);
    }

    private void inserirNaoRecursivo(Nodo raiz, int chave) {
        if (raiz == null) {
            this.raiz = new Nodo(chave);
            return;
        }
        Nodo nodoAtual = raiz;
        while (true) {
            if (chave < nodoAtual.chave) {
                if (nodoAtual.esq == null) {
                    nodoAtual.esq = new Nodo(chave);
                    return;
                } else {
                    nodoAtual = nodoAtual.esq;
                }
            } else if (chave > nodoAtual.chave) {
                if (nodoAtual.dir == null) {
                    nodoAtual.dir = new Nodo(chave);
                    return;
                } else {
                    nodoAtual = nodoAtual.dir;
                }
            } else {
                return;
            }
        }
    }

    public boolean ehAVL() {
        return ehAVL(raiz);
    }

    private boolean ehAVL(Nodo raiz) {
        if (raiz == null) {
            return true;
        }

        int diferencaAltura = Math.abs(alturaAvl(raiz.esq) - alturaAvl(raiz.dir));
        if (diferencaAltura > 1) {
            return false;
        }

        return ehAVL(raiz.esq) && ehAVL(raiz.dir);
    }

    private int alturaAvl(Nodo nodo) {
        if (nodo == null) {
            return 0;
        }
        return Math.max(alturaAvl(nodo.esq), alturaAvl(nodo.dir)) + 1;
    }
}
