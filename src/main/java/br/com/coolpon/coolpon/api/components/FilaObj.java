package br.com.coolpon.coolpon.api.components;

import java.util.List;

public class FilaObj<T> {
    private int tamanho;
    private T[] fila;

    public Boolean isEmpty() {
        return tamanho == 0;
    }

    public Boolean isFull() {
        return tamanho == fila.length;
    }

    public void insert(T info) {
        if (!isFull()) {
            fila[tamanho] = info;
            tamanho++;

        } else {
            System.out.println("A fila está cheia!");
        }
    }

    public T peek() {
        if(!isEmpty()) {
            return fila[0];

        } else {
            return null;
        }
    }

    public T pool() {
        if(!isEmpty()) {
            T first = fila[0];
            tamanho--;

            for (int i = 0; i < tamanho; i++) {
                fila[i] = fila[i+1];
            }

            return first;

        } else {
            return null;
        }
    }

    public void exibe() {
        if(!isEmpty()) {
            String valoresFila = "";

            for (int i = 0; i < tamanho; i++) {
                valoresFila += fila[i] + "\n";
            }
            System.out.println(valoresFila);

        }else {
            System.out.println("Fila vazia");
        }
    }

//    public void transformToQueue(List<T> list){
//
//        for(int i = 0; i < fila.length; i++){
//            this.insert(list.get(i));
//        }
//    }

    public FilaObj(Integer capacidade) {
        tamanho = 0;
        fila = (T[]) new Object[capacidade];
    }

    // Transforma a lista em uma fila
    public FilaObj(List<T> tList) {
        tamanho = 0;
        fila = (T[]) new Object[tList.size()];

        for(T t : tList) {
            this.insert(t);
        }
    }
}
