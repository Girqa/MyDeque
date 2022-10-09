package Project.Classes;

import Project.Exceptions.ClusterIsEmptyException;

public class Cluster<T> {
    /**
     * Базисный элемент двунаправленной очереди.
     * - size - размер кластера (количество хранимых объектов)
     * - elements - массив хранимых элементов
     * - cursor - индекс текущего элемента массива
     * - next - следующий кластер
     * - prev - предыдущий кластер
     */
    final private int size;
    private int cursor = 0;
    private Object[] elements;
    private Cluster<T> next;
    private Cluster<T> prev;

    public Cluster(int size) {
        this.size = size;
        elements = new Object[this.size];
    }

    public Cluster(T... elements) {
        /*
         * Конструктор ячейки Двунаправленной очереди
         * elements - элементы кластера (по умолчанию пустой массив типов T)
         * количество элементов кластера будет определено автоматически
         */
        size = elements.length;
        cursor = size;
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
        cursor = size;
    }

    public T[] getElements() {
        /*
         * Возвращает полную копию элементов кластера
         */
        return (T[]) elements.clone();
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

    public int getCursor() {
        return cursor;
    }

    public Cluster<T> addFirst(T element) {
        // Добавление первого элемента
        if (cursor < size) {                          // Если не переполнен кластер -> кладем туда же
            elements[cursor] = element;
            cursor += 1;
            return this;
        } else if (next == null) {                    // Если переполнен и нет следующего -> создаем и кладем туда
            Cluster<T> newNext = new Cluster<>(size);
            newNext.addFirst(element);
            next = newNext;
            newNext.prev = this;
            return next;
        } else {                                      // Если есть следующий -> записываем туда как последний (заглушка)
            return next.addLast(element);
        }
    }

    public Cluster<T> addLast(T element) {
        if (cursor != size) {                        // Если кластер не полный -> втыкаем в начало
            for (int i = cursor; i > 0; i--) {
                elements[i] = elements[i-1];
            }
            cursor += 1;
            elements[0] = element;
            return this;
        } else if (prev == null) {                    // Иначе если нет предыдущего -> создаем его и в него добавляем
            Cluster<T> newPrev = new Cluster<>(size);
            newPrev.addLast(element);
            prev = newPrev;
            newPrev.setNext(this);
            return newPrev;
        } else {                                      // Заглушка (такого вызова не должно быть!)
            return prev.addLast(element);
        }
    }

    public T getFirst() {
        if (cursor > 0) {
            return (T) elements[cursor-1];
        }
        throw new ClusterIsEmptyException("Cluster have no data");
    }

    public T getLast() {
         if (cursor > 0) {
             return (T) elements[0];
         }
         throw new ClusterIsEmptyException("Cluster is empty");
    }

    public T removeFirst() {
        if (cursor > 0) {
            T first = (T) elements[cursor-1];
            elements[cursor-1] = null;
            cursor -= 1;
            return first;
        }
        else {
            throw new ClusterIsEmptyException("Cluster is empty");
        }
    }

    public T removeLast() {
        if (cursor > 1) {
            T last = (T) elements[0];
            for (int i = 0; i < cursor-1; i++) {
                elements[i] = elements[i+1];
            }
            elements[cursor-1] = null;
            cursor -= 1;
            return last;
        } else if (cursor == 1) {
            T last = (T) elements[0];
            elements[0] = null;
            cursor -= 1;
            return last;
        } else {
            throw new ClusterIsEmptyException();
        }
    }

    public void removeElement(int index) {
        if (index == cursor) {
            elements[cursor] = null;
            cursor -= 1;
        }
        for (int i = index; i < cursor-1; i++) {
            elements[i] = elements[i+1];         // сдвинули все элементы
        }
        elements[cursor-1] = null;
        cursor -= 1;
    }

    public Cluster<T> getNext() {
        return next;
    }

    public Cluster<T> getPrev() {
        return prev;
    }

    public void setNext(Cluster<T> element) {
        next = element;
    }

    public void setPrev(Cluster<T> element) {
        prev = element;
    }
}
