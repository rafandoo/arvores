package com.arvore.avl;

public class ArvoreAVL {

    private class Nodo {
        private int chave, altdir, altesq;
        private Nodo dir, esq;

        public Nodo(int chave) {
            this.chave = chave;
            dir = esq = null;
            altdir = altesq = 0;
        }
    }

    private Nodo raiz;

    public ArvoreAVL() {
        raiz = null;
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
            if (raiz.esq.altdir > raiz.esq.altesq) {
                raiz.altesq = raiz.esq.altdir + 1;
            } else {
                raiz.altesq = raiz.esq.altesq + 1;
            }
            raiz = balanceamento(raiz);
        } else if (chave > raiz.chave) {
            raiz.dir = inserir(raiz.dir, chave);
            if (raiz.dir.altdir > raiz.dir.altesq) {
                raiz.altdir = raiz.dir.altdir + 1;
            } else {
                raiz.altdir = raiz.dir.altesq + 1;
            }
            raiz = balanceamento(raiz);
        }
        return raiz;
    }

    private Nodo balanceamento(Nodo raiz) {
        int fb = raiz.altdir - raiz.altesq;
        int fbSubArv;

        if (fb == 2) {
            fbSubArv = raiz.dir.altdir - raiz.dir.altesq;
            if (fbSubArv >= 0) {
                raiz = rotacaoEsquerda(raiz);
            } else {
                raiz.dir = rotacaoDireita(raiz.dir);
                raiz = rotacaoEsquerda(raiz);
            }
        } else if (fb == -2) {
            fbSubArv = raiz.esq.altdir - raiz.esq.altesq;
            if (fbSubArv <= 0) {
                raiz = rotacaoDireita(raiz);
            } else {
                raiz.esq = rotacaoEsquerda(raiz.esq);
                raiz = rotacaoDireita(raiz);
            }
        }
        return raiz;
    }

    private Nodo rotacaoEsquerda(Nodo raiz) {
        Nodo aux1, aux2;
        aux1 = raiz.dir;
        aux2 = aux1.esq;
        raiz.dir = aux2;
        aux1.esq = raiz;
        if (raiz.dir == null) {
            raiz.altdir = 0;
        } else if (raiz.dir.altesq > raiz.dir.altdir) {
            raiz.altdir = raiz.dir.altesq + 1;
        } else {
            raiz.altdir = raiz.dir.altdir + 1;
        }
        if (aux1.esq.altesq > aux1.esq.altdir) {
            aux1.altesq = aux1.esq.altesq + 1;
        } else {
            aux1.altesq = aux1.esq.altdir + 1;
        }
        return aux1;
    }

    private Nodo rotacaoDireita(Nodo raiz) {
        Nodo aux1, aux2;
        aux1 = raiz.esq;
        aux2 = aux1.dir;
        raiz.esq = aux2;
        aux1.dir = raiz;

        if (raiz.esq == null) {
            raiz.altesq = 0;
        } else if (raiz.esq.altesq > raiz.esq.altdir) {
            raiz.altesq = raiz.esq.altesq + 1;
        } else {
            raiz.altesq = raiz.esq.altdir + 1;
        }
        if (aux1.dir.altesq > aux1.dir.altdir) {
            aux1.altdir = aux1.dir.altesq + 1;
        } else {
            aux1.altdir = aux1.dir.altdir + 1;
        }
        return aux1;
    }

    public void excluir(int chave) {
        raiz = excluir(raiz, chave);
    }

    private Nodo excluir(Nodo raiz, int chave) {
        if (raiz == null) {
            return raiz;
        }

        if (chave < raiz.chave) {
            raiz.esq = excluir(raiz.esq, chave);
        } else if (chave > raiz.chave) {
            raiz.dir = excluir(raiz.dir, chave);
        } else {
            if (raiz.esq == null || raiz.dir == null) {
                Nodo aux = null;
                if (raiz.esq != null) {
                    aux = raiz.esq;
                } else {
                    aux = raiz.dir;
                }

                if (aux == null) {
                    aux = raiz;
                    raiz = null;
                } else {
                    raiz = aux;
                }
            } else {
                Nodo aux = encontrarMinimo(raiz.dir);
                raiz.chave = aux.chave;
                raiz.dir = excluir(raiz.dir, aux.chave);
            }
        }

        if (raiz == null) {
            return raiz;
        }

        raiz.altesq = altura(raiz.esq);
        raiz.altdir = altura(raiz.dir);
        raiz = balanceamento(raiz);

        return raiz;
    }

    private Nodo encontrarMinimo(Nodo raiz) {
        while (raiz.esq != null) {
            raiz = raiz.esq;
        }
        return raiz;
    }

    private int altura(Nodo nodo) {
        if (nodo == null) {
            return 0;
        }
        return Math.max(nodo.altesq, nodo.altdir) + 1;
    }

    public void mostrarEmOrdemCrescente() {
        mostrarEmOrdemCrescente(raiz);
    }

    private void mostrarEmOrdemCrescente(Nodo raiz) {
        if (raiz != null) {
            mostrarEmOrdemCrescente(raiz.esq);
            System.out.println(raiz.chave);
            mostrarEmOrdemCrescente(raiz.dir);
        }
    }

    public void mostrarArvore() {
        mostrarArvore(raiz, 0);
    }

    private void mostrarArvore(Nodo raiz, int nivel) {
        if (raiz != null) {
            mostrarArvore(raiz.dir, nivel + 1);
            for (int i = 0; i < nivel; i++) {
                System.out.print("   ");
            }
            System.out.println(raiz.chave);
            mostrarArvore(raiz.esq, nivel + 1);
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

    private int alturaAvl(Nodo raiz) {
        if (raiz == null) {
            return 0;
        }
        return Math.max(alturaAvl(raiz.esq), alturaAvl(raiz.dir)) + 1;
    }

    public int contarNosPrimos() {
        return contarNosPrimos(raiz);
    }

    private int contarNosPrimos(Nodo raiz) {
        if (raiz == null) {
            return 0;
        }
        if (ehPrimo(raiz.chave)) {
            return 1 + contarNosPrimos(raiz.esq) + contarNosPrimos(raiz.dir);
        } else {
            return contarNosPrimos(raiz.esq) + contarNosPrimos(raiz.dir);
        }
    }

    private boolean ehPrimo(int num) {
        if (num <= 1) {
            return false;
        }
        for (int i = 2; i * i <= num; i++) {
            if (num % i == 0) {
                return false;
            }
        }
        return true;
    }


    public void mostrarArvoreNivel(int nivel) {
        mostrarArvoreNivel(raiz, nivel, 1);
    }

    private void mostrarArvoreNivel(Nodo raiz, int nivelDesejado, int nivelAtual) {
        if (raiz == null) {
            return;
        }

        if (nivelAtual == nivelDesejado) {
            System.out.println("Chave: " + raiz.chave);
        } else {
            mostrarArvoreNivel(raiz.esq, nivelDesejado, nivelAtual + 1);
            mostrarArvoreNivel(raiz.dir, nivelDesejado, nivelAtual + 1);
        }
    }

    public int somarNodosNiveisImpares() {
        return somarNodosNiveisImpares(raiz, 0);
    }

    private int somarNodosNiveisImpares(Nodo raiz, int nivel) {
        if (raiz == null) {
            return 0;
        }
        int soma = 0;
        if (nivel % 2 != 0) {
            soma += raiz.chave;
        }
        soma += somarNodosNiveisImpares(raiz.esq, nivel + 1);
        soma += somarNodosNiveisImpares(raiz.dir, nivel + 1);

        return soma;
    }

}
