package br.com.coolpon.coolpon.api.components;

import java.util.List;

public class PilhaObj<T> {
    private int topo;
    private T[] pilha;

    public PilhaObj(Integer tamanhoPilha) {
        topo = -1;
        pilha = (T[]) new Object[tamanhoPilha];
    }

    public PilhaObj(List<T> tList) {
        topo = -1;
        pilha = (T[]) new Object[tList.size()];

        for (T t : tList) {
            this.push(t);
        }

    }

    public int getCapacity() {
        return pilha.length;
    }

    public boolean isEmpty() {
        return topo == -1;
    }

    public boolean isFull() {
        return topo == (pilha.length - 1);
    }

    public void push(T info) {
        if(!isFull()) {
            pilha[++topo] = info;

        } else {
            System.out.println("A pilha está cheia!");
        }
    }

    public T pop() {
        if (!isEmpty()) {
            return pilha[topo--];
        }

        return null;
    }

    public T peek() {

        if(!isEmpty()) {
            return pilha[topo];
        }

        return null;
    }

    public void exibe() {
        StringBuilder mensagem;

        if(isEmpty()) {
            mensagem = new StringBuilder("A pilha está vazia!");

        } else {
            mensagem = new StringBuilder("Pilha { \n");

            for (int i = 0; i <= topo; i++) {
                if(i == topo) {
                    mensagem.append(String.format("%s ", pilha[i]));

                } else {
                    mensagem.append(String.format("%s, ", pilha[i]));

                }
            }

            mensagem.append("}");
        }

        System.out.println(mensagem);
    }

    public void exibeReverso() {
        StringBuilder mensagem;

        if(isEmpty()) {
            mensagem = new StringBuilder("A pilha está vazia!");

        } else {
            mensagem = new StringBuilder("Pilha { ");

            for (int i = topo; i >= 0; i--) {
                if(i == 0) {
                    mensagem.append(String.format("%s ", pilha[i]));

                } else {
                    mensagem.append(String.format("%s, ", pilha[i]));

                }
            }

            mensagem.append("}");
        }

        System.out.println(mensagem);
    }

    public T[] getPilha() {
        return pilha;
    }
}
