package Project.Classes;

import Project.Classes.AbstractClasses.ElementOfChain;

public class Cluster<T> extends ElementOfChain {
    final private int size; // Размер кластера
    private T[] elements;   // Массив элементов класетра

    public Cluster(T... elements) {
        /*
         * Конструктор ячейки Двунаправленной очереди
         * elements - элементы кластера (по умолчанию пустой массив типов T)
         * количество элементов кластера будет определено автоматически
         */
        this.size = elements.length;
        // Обеспечили независимость внутренних элементов
        this.elements = elements.clone();
    }

    public Cluster(int size, T... elements) {
        /*
         * Конструктор ячейки Двунаправленной очереди
         * size - иммутабельное значение длины массива
         * elements - элементы кластера (по умолчанию пустой массив типов T)
         */
        if (size != elements.length) {
            throw new IllegalArgumentException("\"size\" and \"elements\" length must match!");
        }
        this.size = size;
        this.elements = elements.clone();
    }

    public T[] getElements() {
        /*
         * Возвращает полную копию элементов кластера
         */
        return elements.clone();
    }

    public void setElements(T[] elements) {
        /*
         * Записывает копию поданных элементов массива
         */
        if (size != elements.length){
            throw new IllegalArgumentException("length of \"elements\" array must match Cluster size!");
        }
        this.elements = elements.clone();
    }

    public int getSize() {
        return size;
    }

    @Override
    public ElementOfChain getNext() {
        return null;
    }

    @Override
    public ElementOfChain getPrev() {
        return null;
    }

    @Override
    public ElementOfChain setNext() {
        return null;
    }

    @Override
    public ElementOfChain setPrev() {
        return null;
    }
}
