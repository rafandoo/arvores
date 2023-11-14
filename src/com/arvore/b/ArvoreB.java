package com.arvore.b;

import java.util.Stack;

public class ArvoreB {

    private int T;

    public class Nodo {
        int n;
        int chave[] = new int[2 * T - 1];
        Nodo filhos[] = new Nodo[2 * T];
        boolean folha = true;

        public int encontrar(int chave) {
            for (int i = 0; i < this.n; i++) {
                if (this.chave[i] == chave) {
                    return i;
                }
            }
            return -1;
        }
    }

    public ArvoreB(int t) {
        T = t;
        raiz = new Nodo();
        raiz.n = 0;
        raiz.folha = true;
    }

    private Nodo raiz;

    // Buscar a chave
    private Nodo buscar(Nodo raiz, int chave) {
        int i = 0;
        if (raiz == null)
            return raiz;
        for (i = 0; i < raiz.n; i++) {
            if (chave < raiz.chave[i]) {
                break;
            }
            if (chave == raiz.chave[i]) {
                return raiz;
            }
        }
        if (raiz.folha) {
            return null;
        } else {
            return buscar(raiz.filhos[i], chave);
        }
    }

    public boolean contem(int chave) {
        if (this.buscar(raiz, chave) != null) {
            return true;
        } else {
            return false;
        }
    }

    // Função de divisão
    private void dividir(Nodo x, int pos, Nodo y) {
        Nodo z = new Nodo();
        z.folha = y.folha;
        z.n = T - 1;
        for (int j = 0; j < T - 1; j++) {
            z.chave[j] = y.chave[j + T];
        }
        if (!y.folha) {
            for (int j = 0; j < T; j++) {
                z.filhos[j] = y.filhos[j + T];
            }
        }
        y.n = T - 1;
        for (int j = x.n; j >= pos + 1; j--) {
            x.filhos[j + 1] = x.filhos[j];
        }
        x.filhos[pos + 1] = z;

        for (int j = x.n - 1; j >= pos; j--) {
            x.chave[j + 1] = x.chave[j];
        }
        x.chave[pos] = y.chave[T - 1];
        x.n = x.n + 1;
    }

    public void inserir(int chave) {
        Nodo r = raiz;
        if (r.n == 2 * T - 1) {
            Nodo s = new Nodo();
            raiz = s;
            s.folha = false;
            s.n = 0;
            s.filhos[0] = r;
            dividir(s, 0, r);
            inserir(s, chave);
        } else {
            inserir(r, chave);
        }
    }
    
    private void inserir(Nodo raiz, int chave) {
        if (raiz.folha) {
            int i = 0;
            for (i = raiz.n - 1; i >= 0 && chave < raiz.chave[i]; i--) {
                raiz.chave[i + 1] = raiz.chave[i];
            }
            raiz.chave[i + 1] = chave;
            raiz.n = raiz.n + 1;
        } else {
            int i = 0;
            for (i = raiz.n - 1; i >= 0 && chave < raiz.chave[i]; i--) {
            }
            ;
            i++;
            Nodo tmp = raiz.filhos[i];
            if (tmp.n == 2 * T - 1) {
                dividir(raiz, i, tmp);
                if (chave > raiz.chave[i]) {
                    i++;
                }
            }
            inserir(raiz.filhos[i], chave);
        }
    }

    public void mostrar() {
        mostrar(raiz);
    }

    private void remover(Nodo x, int chave) {
        int pos = x.encontrar(chave);
        if (pos != -1) {
            if (x.folha) {
                int i = 0;
                for (i = 0; i < x.n && x.chave[i] != chave; i++) {
                }
                ;
                for (; i < x.n; i++) {
                    if (i != 2 * T - 2) {
                        x.chave[i] = x.chave[i + 1];
                    }
                }
                x.n--;
                return;
            }
            if (!x.folha) {

                Nodo pred = x.filhos[pos];
                int predChave = 0;
                if (pred.n >= T) {
                    for (;;) {
                        if (pred.folha) {
                            System.out.println(pred.n);
                            predChave = pred.chave[pred.n - 1];
                            break;
                        } else {
                            pred = pred.filhos[pred.n];
                        }
                    }
                    remover(pred, predChave);
                    x.chave[pos] = predChave;
                    return;
                }

                Nodo proximoNo = x.filhos[pos + 1];
                if (proximoNo.n >= T) {
                    int proximaChave = proximoNo.chave[0];
                    if (!proximoNo.folha) {
                        proximoNo = proximoNo.filhos[0];
                        for (;;) {
                            if (proximoNo.folha) {
                                proximaChave = proximoNo.chave[proximoNo.n - 1];
                                break;
                            } else {
                                proximoNo = proximoNo.filhos[proximoNo.n];
                            }
                        }
                    }
                    remover(proximoNo, proximaChave);
                    x.chave[pos] = proximaChave;
                    return;
                }

                int temp = pred.n + 1;
                pred.chave[pred.n++] = x.chave[pos];
                for (int i = 0, j = pred.n; i < proximoNo.n; i++) {
                    pred.chave[j++] = proximoNo.chave[i];
                    pred.n++;
                }
                for (int i = 0; i < proximoNo.n + 1; i++) {
                    pred.filhos[temp++] = proximoNo.filhos[i];
                }

                x.filhos[pos] = pred;
                for (int i = pos; i < x.n; i++) {
                    if (i != 2 * T - 2) {
                        x.chave[i] = x.chave[i + 1];
                    }
                }
                for (int i = pos + 1; i < x.n + 1; i++) {
                    if (i != 2 * T - 1) {
                        x.filhos[i] = x.filhos[i + 1];
                    }
                }
                x.n--;
                if (x.n == 0) {
                    if (x == raiz) {
                        raiz = x.filhos[0];
                    }
                    x = x.filhos[0];
                }
                remover(pred, chave);
                return;
            }
        } else {
            for (pos = 0; pos < x.n; pos++) {
                if (x.chave[pos] > chave) {
                    break;
                }
            }
            Nodo tmp = x.filhos[pos];
            if (tmp.n >= T) {
                remover(tmp, chave);
                return;
            }
            if (true) {
                Nodo nb = null;
                int divisor = -1;

                if (pos != x.n && x.filhos[pos + 1].n >= T) {
                    divisor = x.chave[pos];
                    nb = x.filhos[pos + 1];
                    x.chave[pos] = nb.chave[0];
                    tmp.chave[tmp.n++] = divisor;
                    tmp.filhos[tmp.n] = nb.filhos[0];
                    for (int i = 1; i < nb.n; i++) {
                        nb.chave[i - 1] = nb.chave[i];
                    }
                    for (int i = 1; i <= nb.n; i++) {
                        nb.filhos[i - 1] = nb.filhos[i];
                    }
                    nb.n--;
                    remover(tmp, chave);
                    return;
                } else if (pos != 0 && x.filhos[pos - 1].n >= T) {

                    divisor = x.chave[pos - 1];
                    nb = x.filhos[pos - 1];
                    x.chave[pos - 1] = nb.chave[nb.n - 1];
                    Nodo filho = nb.filhos[nb.n];
                    nb.n--;

                    for (int i = tmp.n; i > 0; i--) {
                        tmp.chave[i] = tmp.chave[i - 1];
                    }
                    tmp.chave[0] = divisor;
                    for (int i = tmp.n + 1; i > 0; i--) {
                        tmp.filhos[i] = tmp.filhos[i - 1];
                    }
                    tmp.filhos[0] = filho;
                    tmp.n++;
                    remover(tmp, chave);
                    return;
                } else {
                    Nodo lt = null;
                    Nodo rt = null;
                    boolean ultimo = false;
                    if (pos != x.n) {
                        divisor = x.chave[pos];
                        lt = x.filhos[pos];
                        rt = x.filhos[pos + 1];
                    } else {
                        divisor = x.chave[pos - 1];
                        rt = x.filhos[pos];
                        lt = x.filhos[pos - 1];
                        ultimo = true;
                        pos--;
                    }
                    for (int i = pos; i < x.n - 1; i++) {
                        x.chave[i] = x.chave[i + 1];
                    }
                    for (int i = pos + 1; i < x.n; i++) {
                        x.filhos[i] = x.filhos[i + 1];
                    }
                    x.n--;
                    lt.chave[lt.n++] = divisor;

                    for (int i = 0, j = lt.n; i < rt.n + 1; i++, j++) {
                        if (i < rt.n) {
                            lt.chave[j] = rt.chave[i];
                        }
                        lt.filhos[j] = rt.filhos[i];
                    }
                    lt.n += rt.n;
                    if (x.n == 0) {
                        if (x == raiz) {
                            raiz = x.filhos[0];
                        }
                        x = x.filhos[0];
                    }
                    remover(lt, chave);
                    return;
                }
            }
        }
    }

    public void remover(int chave) {
        Nodo x = buscar(raiz, chave);
        if (x == null) {
            return;
        }
        remover(raiz, chave);
    }

    public void realizarTarefa(int a, int b) {
        Stack<Integer> pilha = new Stack<>();
        encontrarChaves(a, b, raiz, pilha);
        while (pilha.isEmpty() == false) {
            this.remover(raiz, pilha.pop());
        }
    }

    private void encontrarChaves(int a, int b, Nodo x, Stack<Integer> pilha) {
        int i = 0;
        for (i = 0; i < x.n && x.chave[i] < b; i++) {
            if (x.chave[i] > a) {
                pilha.push(x.chave[i]);
            }
        }
        if (!x.folha) {
            for (int j = 0; j < i + 1; j++) {
                encontrarChaves(a, b, x.filhos[j], pilha);
            }
        }
    }

    // Mostrar o nó
    private void mostrar(Nodo raiz) {
        assert (raiz == null);
        for (int i = 0; i < raiz.n; i++) {
            System.out.print(raiz.chave[i] + " ");
        }
        if (!raiz.folha) {
            for (int i = 0; i < raiz.n + 1; i++) {
                mostrar(raiz.filhos[i]);
            }
        }
    }
}
