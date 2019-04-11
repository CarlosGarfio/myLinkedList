
import linkedList.LinkedList;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        LinkedList<Integer> lista = new LinkedList<>();
        String recursivo = "recursivo", iterativo = "iterativo";
        Scanner s = new Scanner(System.in);
        int valor, despues, antes;

        for (int i = 0; i < 8; i++) {
            lista.add((int) (Math.random() * 91) + 10);
        }
//        lista.add("uno");
//        lista.add("dos");
//        lista.add("tres");
//        lista.add("cuatro");
//        lista.add("dias");
//        lista.add("noches");
//        lista.add("tristes");       
//        lista.add("uno");
//
//        lista.add("onomatopeya");
//        lista.add("netzahualcoyotl");
        LinkedList<Integer> copia = lista.clone(lista);
        copia.printList();

        copia.mayorMenorPromedio(copia, iterativo);
        copia.mayorMenorPromedio(copia, recursivo);
//        
//        copia.posiciones(copia, "uno", recursivo);
        copia.rango(copia, 10, 80, iterativo);        
        copia.rango(copia, 10, 80, recursivo);
        copia.update(copia, 1, 5, iterativo);
        copia.printList();
        copia.update(copia, 2, 5, recursivo);
        copia.printList();

//        LinkedList<Integer> _new = copia.mayoresPromedio(copia, recursivo);
//        _new.printList();
//        System.out.print("¿Antes de cual valor vas a agregar? ");
//        antes = s.nextInt();
//        System.out.print("¿Valor a agregar? ");
//        valor = s.nextInt();
//        lista.addBefore(antes, valor);
//        lista.printList();
//
//        System.out.print("¿Valor del que va remover después? ");
//        despues = s.nextInt();
//        lista.removeAfter(despues);
//        lista.printList();
//
//        System.out.print("¿Antes de cuál valor a borrar? ");
//        antes = s.nextInt();
//        lista.removeBefore(antes);
//        lista.printList();
//
//        System.out.print("¿Valor a meter al inicio? ");
//        valor = s.nextInt();
//        lista.addStart(valor);
//        lista.printList();
    }
}
