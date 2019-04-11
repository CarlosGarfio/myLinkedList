package linkedList;

import exception.isEmptyException;
import java.lang.reflect.Array;

public class LinkedList<T extends Comparable<T>> {

    private Node<T> head;
    private Node<T> tail;
    private int lenght;

    public LinkedList() {
        this.head = new Node<>();
        this.tail = new Node<>();
        this.lenght = 0;
    }

    public void isEmpty() throws isEmptyException {
        if (head.getNext() == null) {
            throw new isEmptyException("Empty list");
        }
    }

    // Siempre se manda a llamar con el head
    // Si la lista esta vacia, regresa el head
    private Node<T> getLastElement(Node<T> node) {
        if (node.getNext() == null) {
            return node;
        } else {
            return getLastElement(node.getNext());
        }
    }

    // Se manda a llamar con head y el valor del que buscamos el anterior
    private Node<T> getPrevElement(Node<T> node, T value) {
        if (node.getNext() == null) {
            return null;
        } else if (node.getNext().getValue().equals(value)) {
            return node;
        } else {
            return getPrevElement(node.getNext(), value);
        }
    }

    private Node<T> getElementAt(int position) {
        try {
            isEmpty();
            if (position < 0 || position >= lenght) {
                return null;
            } else {
                return getElementAt(head, -1, position);
            }
        } catch (isEmptyException e) {
            return null;
        }
    }

    private Node<T> getElementAt(Node<T> node, int i, int position) {
        if (i == position) {
            return node;
        } else {
            return getElementAt(node.getNext(), i + 1, position);
        }
    }

    public boolean isThere(Node<T> node, T value) {
        if (node.getNext() == null) {
            return false;
        } else if (node.getNext().getValue().equals(value)) {
            return true;
        } else {
            return isThere(node.getNext(), value);
        }
    }

    public boolean add(T value) {
        // La lista no acepta valores nulos
        if (value == null) {
            return false;
        } else {
            Node<T> _new = new Node<>(value);
            try {
                isEmpty();// Caso 1: No está vacía la lista
                getLastElement(head).setNext(_new);
            } catch (isEmptyException e) {//Caso 2: La lista está vacía
                head.setNext(_new);
            }
            lenght++;
            return true;
        }
    }

    public boolean add(Node<T> node) {
        return add(node.getValue());
    }

    public boolean remove(T value) {
        if (value == null) {
            return false;
        } else {
            try {
                isEmpty();
                if (isThere(head, value)) { //Caso 1: Si hay lista, y si esta
                    Node<T> ref = getPrevElement(head, value);
                    Node<T> rem = ref.getNext();
                    if (rem.getNext() == null) { // El removido es el último de la lista
                        ref.setNext(null);
                    } else { // No es el último
                        ref.setNext(rem.getNext());
                        rem.setNext(null);
                    }
                    rem = null;
                    System.gc();
                    lenght--;
                    return true;
                } else // Caso 2: La lista no esta vacia, pero no está el valor
                {
                    return false;
                }
            } catch (isEmptyException e) {
                return false; // Caso 3: La lista está vacía
            }
        }
    }

    public boolean remove(Node<T> node) {
        return remove(node.getValue());
    }

    public boolean addAt(T value, int position) {
        if (value == null) // No se aceptan valores nulos
        {
            return false;
        } else {
            try {
                isEmpty();
                if (position < 0 || position >= lenght) // Caso 1: No existe la posicion
                {
                    return false;
                } else { // Caso 2: Si existe la posición, por lo que sí se agregaría
                    Node<T> tmp; // Este va ser el nodo al que se le agregaria adelante el nuevo
                    if (position == 0) {
                        tmp = head;
                    } else {
                        tmp = getElementAt(position - 1);
                    }
                    Node<T> _new = new Node<>(value);
                    if (tmp.getNext() != null) {
                        _new.setNext(tmp.getNext());
                    }
                    tmp.setNext(_new);
                    lenght++;
                    return true;
                }
            } catch (isEmptyException e) { // Esta vacia la lista
                return false;
            }
        }
    }

    public boolean addAt(Node<T> node, int position) {
        return addAt(node.getValue(), position);
    }

    public boolean addAfter(T after, T value) {
        if (after == null || value == null) {
            return false;
        } else {
            try {
                isEmpty();
                Node<T> tmp = getNode(head, after);
                if (tmp == null) // Caso 1: No está el valor del que voy a agregar después
                {
                    return false;
                } else { // Caso 2: Sí está
                    Node<T> _new = new Node<>(value);
                    if (tmp.getNext() == null) {// Es el último de la lista
                        tmp.setNext(_new);
                    } else { // No es el último de la lista
                        _new.setNext(tmp.getNext());
                        tmp.setNext(_new);
                    }
                    lenght++;
                    return true;
                }
            } catch (isEmptyException e) { // Caso 3: Está vacía la lista
                return false;
            }
        }
    }

    public boolean addBefore(T before, T value) {
        if (before == null || value == null) {
            return false;
        } else {
            try {
                isEmpty();
                Node<T> tmp = getPrevElement(head, before);
                if (tmp == null) // Caso 1: Lista no vacía, pero no estaba el before
                {
                    return false;
                } else { // Caso 2: Sí se encontró el before
                    Node<T> _new = new Node<>(value);
                    _new.setNext(tmp.getNext());
                    tmp.setNext(_new);
                    lenght++;
                    return true;
                }
            } catch (isEmptyException e) { // Caso 3: Estaba vacía
                return false;
            }
        }
    }

    public boolean removeAfter(T after) {
        if (after == null) {
            return false;
        } else {
            try {
                isEmpty();
                Node<T> tmp = getNode(head, after);
                if (tmp == null) // Caso 1: No está el after
                {
                    return false;
                } else if (tmp.getNext() == null) // Caso 2: Sí está el after, pero es el último
                {
                    return false;
                } else { // Caso 3: Sí está el after y no es el último
                    Node<T> rem = tmp.getNext();
                    if (rem.getNext() == null) { // El removido es el último
                        tmp.setNext(null);
                    } else { // El removido no es el último
                        tmp.setNext(rem.getNext());
                        rem.setNext(null);
                    }
                    rem = null;
                    System.gc();
                    lenght--;
                    return true;
                }
            } catch (isEmptyException e) { //Caso 4: La lista está vacía
                return false;
            }
        }
    }

    public boolean removeBefore(T before) {
        try {
            isEmpty();
            if (before == null) {
                return false;
            } else if (isThere(head, before)) // Sí se encontró el before en la lista
            {
                if (head.getNext().getValue().equals(before)) // Caso 1: El before es el primero, por lo que no hay nada que remover
                {
                    return false;
                } else { // Caso 2: No es el primero
                    Node<T> tmp = getPrevPrevElement(head, before);
                    Node<T> rem = tmp.getNext();
                    tmp.setNext(rem.getNext());
                    rem.setNext(null);
                    rem = null;
                    System.gc();
                    lenght--;
                    return true;
                }
            } else {
                return false;
            }
        } catch (isEmptyException e) { // Caso 3: Está vacío
            return false;
        }
    }

    public boolean addStart(T value) {
        if (value == null) // Único caso en el que no hace nada
        {
            return false;
        } else {
            Node<T> _new = new Node<>(value);
            try {
                isEmpty(); // Caso 1: No está vacía la lista
                _new.setNext(head.getNext());
                head.setNext(_new);
            } catch (isEmptyException e) { // Caso 2: Está vacía la lista
                head.setNext(_new);
            }
            lenght++;
            return true;
        }
    }

    public boolean addStart(Node<T> node) {
        return addStart(node.getValue());
    }

    public int lenght() {
        return lenght;
    }

    public boolean removeAll(T value) {
        if (value == null) //No hay valores nulos en la lista
        {
            return false;
        } else {
            try {
                isEmpty();
                if (isThere(head, value)) { // Sí está en la lista
                    do {
                        remove(value);
                    } while (isThere(head, value));
                    return true;
                } else // Lista no vacía, pero no está
                {
                    return false;
                }
            } catch (isEmptyException e) { // La lista vacía
                return false;
            }
        }
    }

// Mis métodos de prueba
    public void printList() {
        try {
            isEmpty();
            System.out.println(listToString(head) + " lenght: " + lenght);
        } catch (isEmptyException e) {
            System.out.println(e.getMessage());
        }
    }

    private String listToString(Node<T> node) {
        if (node.getNext() == null) {
            return "";
        } else {
            return "[" + node.getNext().getValue() + "] " + listToString(node.getNext());
        }
    }

    public LinkedList<T> clone(LinkedList<T> org) {
        try {
            org.isEmpty();
            LinkedList<T> _new = new LinkedList<>();
            for (int i = 0; i < org.lenght(); i++) {
                Node<T> _tmp = org.getElementAt(i);
                _new.add(_tmp);
            }
            return _new;
        } catch (isEmptyException e) {
        }
        return null;
    }

    public void update(LinkedList<T> copia, T value, int x, String str) {
        try {
            copia.isEmpty();
            String instace = copia.checkInstance(copia);

            switch (str) {
                case "recursivo":
                    if (update(copia.head.getNext(), value, 0, x)) {
                        System.out.println("Se actualizo correctamente.");
                    } else {
                        System.out.println("No actualizo correctamente.");
                    }
                    return;
                case "iterativo":
                    boolean flag = false;
                    for (int i = 0; i < copia.lenght(); i++) {
                        Node<T> node = copia.getElementAt(i);
                        if (x == i) {
                            node.setValue(value);
                            flag = true;
                            break;
                        }
                    }
                    if (flag) {
                        System.out.println("Se actualizo correctamente.");
                    } else {
                        System.out.println("No actualizo correctamente.");
                    }
                    return;
            }
        } catch (isEmptyException e) {
        }
    }

    private boolean update(Node<T> node, T value, int i, int pos) {
        if (node == null) {
            return false;
        } else if (i == pos) {
            node.setValue(value);
            return true;
        }
        return update(node.getNext(), value, ++i, pos);
    }

    public void rango(LinkedList<T> copia, int X, int Y, String str) {
        try {
            copia.isEmpty();
            String instace = copia.checkInstance(copia);

            switch (str) {
                case "recursivo":
                    System.out.print("Entre el rango de (" + X + "," + Y + ") se encuentran en: ");
                    rango(copia.head.getNext(), X, Y);
                    System.out.println("");
                    return;
                case "iterativo":
                    System.out.print("Entre el rango de (" + X + "," + Y + ") se encuentran en: ");
                    for (int i = 0; i < copia.lenght(); i++) {
                        Node<T> node = copia.getElementAt(i);
                        if ((Integer) node.getValue() > X && (Integer) node.getValue() < Y) {
                            System.out.print(node.getValue() + ",");
                        }
                    }
                    System.out.println("");
                    return;
            }
        } catch (isEmptyException e) {
        }
    }

    private void rango(Node<T> node, int X, int Y) {
        if (node == null) {
            return;
        } else if ((Integer) node.getValue() > X && (Integer) node.getValue() < Y) {
            System.out.print(node.getValue() + ",");
        }
        rango(node.getNext(), X, Y);
    }

    public void posiciones(LinkedList<T> copia, T value, String str) {
        try {
            copia.isEmpty();
            String instace = copia.checkInstance(copia);

            switch (str) {
                case "recursivo":
                    System.out.print("El elemento '" + value + "' se encuentra en: ");
                    posiciones(copia.head.getNext(), 0, value);
                    System.out.println("");
                    return;
                case "iterativo":
                    System.out.print("El elemento '" + value + "' se encuentra en: ");
                    for (int i = 0; i < copia.lenght(); i++) {
                        Node<T> node = copia.getElementAt(i);
                        if (node.getValue().compareTo(value) == 0) {
                            System.out.print(i + ",");
                        }
                    }
                    System.out.println("");
                    return;
            }
        } catch (isEmptyException e) {
        }
    }

    private void posiciones(Node<T> node, int i, T value) {
        if (node == null) {
            return;
        } else if (node.getValue().compareTo(value) == 0) {
            System.out.print(i + ",");
        }
        posiciones(node.getNext(), ++i, value);
    }

    public void mayorMenorPromedio(LinkedList<T> copia, String str) {
        try {
            copia.isEmpty();
            LinkedList<T> _new = new LinkedList<>();
            double prom = copia.promedio(copia.head.getNext(), 0, 0, copia.checkInstance(copia));
            String instace = copia.checkInstance(copia);

            T[] _arr;

            switch (str) {
                case "recursivo":
                    if ("Integer".equals(instace)) {
                        _arr = (T[]) Array.newInstance(Integer.class, 2);
                        _arr[0] = copia.head.getNext().getValue();
                        _arr[1] = copia.head.getNext().getValue();
                        _arr = copia.mayorMenorProm(_arr, copia.head.getNext(), prom, checkInstance(copia));
                        System.out.println("El numero mayor es: " + _arr[0]);
                        System.out.println("El numero menor es: " + _arr[1]);
                        System.out.println("Su promedio es: " + ((Integer) _arr[0] + (Integer) _arr[1]) / 2);
                    } else if ("String".equals(instace)) {
                        _arr = (T[]) Array.newInstance(String.class, 2);
                        _arr[0] = copia.head.getNext().getValue();
                        _arr[1] = copia.head.getNext().getValue();
                        _arr = copia.mayorMenorProm(_arr, head.getNext(), prom, checkInstance(copia));
                        System.out.println("El numero mayor es: " + _arr[0]);
                        System.out.println("El numero menor es: " + _arr[1]);
                        String Y = (String) _arr[0];
                        String Z = (String) _arr[1];
                        System.out.println("Su promedio es: " + (Y.length() + Z.length()) / 2);
                    }
                    return;
                case "iterativo":
                    if ("Integer".equals(instace)) {
                        _arr = (T[]) Array.newInstance(Integer.class, 2);
                        _arr[0] = copia.head.getNext().getValue();
                        _arr[1] = copia.head.getNext().getValue();
                        for (int i = 0; i < copia.lenght(); i++) {
                            Node<T> node = copia.getElementAt(i);
                            if ((Integer) node.getValue() > (Integer) _arr[0]) {
                                _arr[0] = node.getValue();
                            }
                            if ((Integer) node.getValue() < (Integer) _arr[1]) {
                                _arr[1] = node.getValue();
                            }
                        }
                        System.out.println("El numero mayor es: " + _arr[0]);
                        System.out.println("El numero menor es: " + _arr[1]);
                        System.out.println("Su promedio es: " + ((Integer) _arr[0] + (Integer) _arr[1]) / 2);
                    } else if ("String".equals(instace)) {
                        _arr = (T[]) Array.newInstance(String.class, 2);
                        _arr[0] = copia.head.getNext().getValue();
                        _arr[1] = copia.head.getNext().getValue();
                        for (int i = 0; i < copia.lenght(); i++) {
                            Node<T> node = copia.getElementAt(i);
                            String X = (String) node.getValue();
                            String Y = (String) _arr[0];
                            String Z = (String) _arr[1];
                            if (X.length() > Y.length()) {
                                _arr[0] = node.getValue();
                            }
                            if (X.length() < Z.length()) {
                                _arr[1] = node.getValue();
                            }
                        }
                        System.out.println("El numero mayor es: " + _arr[0]);
                        System.out.println("El numero menor es: " + _arr[1]);
                        String Y = (String) _arr[0];
                        String Z = (String) _arr[1];
                        System.out.println("Su promedio es: " + (Y.length() + Z.length()) / 2);
                    }
                    return;

            }
        } catch (isEmptyException e) {
        }
    }

    private T[] mayorMenorProm(T[] arr, Node<T> node, double prom, String str) {
        switch (str) {
            case "Integer":
                if (node == null) {
                    return arr;
                }
                if ((Integer) node.getValue() > (Integer) arr[0]) {
                    arr[0] = node.getValue();
                }
                if ((Integer) node.getValue() < (Integer) arr[1]) {
                    arr[1] = node.getValue();
                }
                return mayorMenorProm(arr, node.getNext(), prom, str);
            case "String":
                if (node == null) {
                    return arr;
                }
                String X = (String) node.getValue();
                String Y = (String) arr[0];
                String Z = (String) arr[1];
                if (X.length() > Y.length()) {
                    arr[0] = node.getValue();
                }
                if (X.length() < Z.length()) {
                    arr[1] = node.getValue();
                }
                return mayorMenorProm(arr, node.getNext(), prom, str);
        }
        return null;
    }

    private String checkInstance(LinkedList<T> copia) {
        if (copia.head.getNext().getValue() instanceof Integer) {
            return "Integer";
        }
        if (copia.head.getNext().getValue() instanceof String) {
            return "String";
        }
        return null;
    }

    public LinkedList<T> mayoresPromedio(LinkedList<T> org, String str) {
        try {
            org.isEmpty();
            LinkedList<T> copia = org.clone(org);
            LinkedList<T> _new = new LinkedList<>();
            double prom = copia.promedio(copia.head.getNext(), 0, 0, copia.checkInstance(copia));
            System.out.println("El promedio es: " + prom);
            switch (str) {
                case "recursivo":
                    return mayoresPromedo(copia.head.getNext(), _new, prom);
                case "iterativo":
                    for (int i = 0; i < copia.lenght(); i++) {
                        Node<T> node = copia.getElementAt(i);
                        if ((Integer) node.getValue() > prom) {
                            _new.add(node);
                        }
                    }
                    return _new;
            }
        } catch (isEmptyException e) {
        }
        return null;
    }

    private LinkedList<T> mayoresPromedo(Node<T> node, LinkedList<T> _new, double prom) {
        if (node == null) {
            return _new;
        } else if ((Integer) node.getValue() > prom) {
            _new.add(node);
        }
        return mayoresPromedo(node.getNext(), _new, prom);
    }

    private double promedio(Node<T> node, int sum, int i, String str) {
        switch (str) {
            case "Integer":
                if (node == null) {
                    return sum / i;
                } else {
                    sum += (Integer) node.getValue();
                }
                return promedio(node.getNext(), sum, ++i, str);
            case "String":
                if (node == null) {
                    return sum / i;
                } else {
                    String s = (String) node.getValue();
                    sum += s.length();
                }
                return promedio(node.getNext(), sum, ++i, str);
            default:
                return -1d;
        }
    }

    public LinkedList<T> parImpar(LinkedList<T> org, String str) {
        try {
            org.isEmpty();
            LinkedList<T> copia = org.clone(org);
            LinkedList<T> _new = new LinkedList<>();
            switch (str) {
                case "recursivo":
                    return _new.par_Impar(_new, copia.head.getNext());
                case "iterativo":
                    for (int i = 0; i < copia.lenght(); i++) {
                        Node<T> node = copia.getElementAt(i);
                        if (!esPar(node.getValue())) {
                            _new.add(node);
                        }
                    }
                    return _new;
            }
        } catch (isEmptyException e) {
        }
        return null;
    }

    private LinkedList<T> par_Impar(LinkedList<T> copia, Node<T> node) {
        if (node == null) {
            return copia;
        } else if (!esPar(node.getValue())) {
            copia.add(node);
        }
        return par_Impar(copia, node.getNext());
    }

    private boolean esPar(T value) {
        return (Integer) value % 2 == 0;
    }

    // Mi método. Busca el primer nodo con el valor que busco
    // Se manda a llamar con el head
    private Node<T> getNode(Node<T> node, T value) {
        if (node.getNext() == null) {
            return null;
        } else if (node.getNext().getValue().equals(value)) {
            return node.getNext();
        } else {
            return getNode(node.getNext(), value);
        }
    }

    // Estos dos metodos son exclusivos para el removeBefore
    private Node<T> getPrevPrevElement(T value) {
        if (lenght >= 2) {
            return getPrevPrevElement(head, value);
        } else {
            return null;
        }
    }

    private Node<T> getPrevPrevElement(Node<T> node, T value) {
        if (node.getNext().getNext() == null) {
            return null;
        } else if (node.getNext().getNext().getValue().equals(value)) {
            return node;
        } else {
            return getPrevPrevElement(node.getNext(), value);
        }
    }
}
