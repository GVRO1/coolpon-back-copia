package br.com.coolpon.coolpon.exportar;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class TesteExportarAquivo {
    public static void main(String[] args) throws IOException {
        ExportarArquivo exportarArquivo = new ExportarArquivo();
        ImportarArquivo importarArquivo = new ImportarArquivo();
        File file = new File("C:\\Users\\Gabriel Vieira\\Desktop\\teste.txt");
        importarArquivo.executar(file);
        //exportarArquivo.executar();
    }
}
