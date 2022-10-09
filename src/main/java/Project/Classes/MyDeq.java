package Project.Classes;

import Project.Exceptions.DequeIsEmptyException;

import java.util.*;

public class MyDeq<T> implements Deque<T> {
    /**
     * Реализация двунаправленной очереди от Маринова Я.А.
     *---
     * Основной элемент - Cluster, содержит clusterSize хранимых элементов.
     * Для тренировки перечислений ограничил возможные значения размера кластера с помощью
     * enum ClusterSize(SMALL(1), NORMAL(5), BIG(10)). По умолчанию NORMAL(5).
     * - Cluster head - приватное поле для хранения головы очереди
     * - Cluster tail - приватное поле для хранения хвоста очереди
     */
    public enum ClusterSize {
        /**
         * Перечисление доступных размеров кластера.
         */
        SMALL(1), NORMAL(5), BIG(10);
        final private int value;
        ClusterSize(int value) {this.value = value;}
    }

    final private ClusterSize clusterSize;
    private int size;
    private Cluster<T> head;
    private Cluster<T> tail;

    public MyDeq() {
        /*
         *  Инициализация двусторонней очереди с параметрами по умолчанию
         */
        this.clusterSize = ClusterSize.NORMAL;
        Cluster<T> initCluster = new Cluster<>(this.clusterSize.value);
        head = initCluster;
        tail = initCluster;
    }

    public MyDeq(ClusterSize clusterSize) {
        /*
         * Инициализация двусторонней очереди (без инициализации значений)
         */
        this.clusterSize = clusterSize;
        Cluster<T> initCluster = new Cluster<>(this.clusterSize.value);
        head = initCluster;
        tail = initCluster;
    }

    public MyDeq(ClusterSize clusterSize, T[] startState) {
        /*
         * Инициализация двусторонней очереди с инициализацией значений
         * startState - начальное состояние (наполнение) очереди
         */
        this.clusterSize = clusterSize;
        Cluster<T> initCluster = new Cluster<>(this.clusterSize.value);
        head = initCluster;
        tail = initCluster;
        for (T element: startState) {
            addFirst(element);
        }
    }
    public MyDeq(ClusterSize clusterSize, Collection<? extends T> c) {
        /*
         * Инициализация двусторонней очереди с инициализацией значений
         * startState - начальное состояние (наполнение) очереди
         */
        this.clusterSize = clusterSize;
        Cluster<T> initCluster = new Cluster<>(this.clusterSize.value);
        head = initCluster;
        tail = initCluster;
        addAll(c);
    }

    @Override
    public void addFirst(T o) {
        if (o == null) {
            throw new NullPointerException();
        }
        size += 1;
        head = head.addFirst(o);
    }

    @Override
    public void addLast(T o) {
        if (o == null) {
            throw new NullPointerException();
        }
        size += 1;
        tail = tail.addLast(o);
    }

    @Override
    public boolean offerFirst(T o) {
        if (o == null) {
            throw new NullPointerException();
        }
        try {
            addFirst(o);
            return true;
        } catch (Exception ex) {
            System.out.println(ex.getStackTrace());
            return false;
        }
    }

    @Override
    public boolean offerLast(T o) {
        if (o == null) {
            throw new NullPointerException();
        }
        try {
            addLast(o);
            return true;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            System.out.println(ex.getStackTrace());
            return false;
        }
    }

    @Override
    public T removeFirst() {
        T first;
        if (size > 0) {
            if (head.getCursor() > 1) {
                first = head.removeFirst();
                size -= 1;
                return first;
            } else {
                if (head.getCursor() == 1 && head.getPrev() != null) {
                    first = head.removeFirst();
                    head = head.getPrev();
                    head.setNext(null);
                    size -= 1;
                    return first;
                } else if (head.getCursor() == 1 && head.getPrev() == null) {
                    first = head.removeFirst();
                    size -= 1;
                    return first;
                }
            }
        }
        throw new DequeIsEmptyException();
    }

    @Override
    public T removeLast() {
        T last;
        if (size > 0) {
            if (tail.getCursor() > 1) {
                last = tail.removeLast();
                size -= 1;
                return last;
            } else if (tail.getCursor() == 1 && tail.getNext() != null) {
                last = tail.removeLast();
                tail = tail.getNext();
                tail.setPrev(null);
                size -= 1;
                return last;
            } else if (tail.getCursor() == 1 && tail.getNext() == null) {
                last = tail.removeLast();
                size -= 1;
                return last;
            }
        }
        throw new DequeIsEmptyException();
    }

    @Override
    public T pollFirst() {
        return removeLast();
    }

    @Override
    public T pollLast() {
        return removeLast();
    }

    @Override
    public T getFirst() {
        if (size > 0 && head.getCursor() > 0) {
            return head.getFirst();
        }
        throw new DequeIsEmptyException("Deque is empty");
    }

    @Override
    public T getLast() {
        if (size > 0) {
            return tail.getLast();
        }
        throw new DequeIsEmptyException("Deque is empty");
    }

    @Override
    public T peekFirst() {
        return getFirst();
    }

    @Override
    public T peekLast() {
        return getLast();
    }

    @Override
    public boolean removeFirstOccurrence(Object o) {
        T element = (T) o;
        boolean found = false;
        Cluster<T> curCluster = head;  // Идем с головы
        while (curCluster != null && !found) {   // Пока не дошли до хвоста
            T[] elements = curCluster.getElements();
            int i = curCluster.getCursor() - 1;  // Проходим кластера с головы
            while (!found && i >= 0) {
                if (elements[i] == element && !found) {       // Если нашли элемент -> удаляем
                    found = true;
                    curCluster.removeElement(i);
                    size -= 1;
                }
                i--;
            }
            if (!found) {
                curCluster = curCluster.getPrev();
            }
        }
        if (curCluster != null && curCluster.getCursor() < 1) { // Если опустошили кластер -> выкидываем
            if (curCluster.getNext() != null && curCluster.getPrev() != null) {// Если есть следующий и предыдущий элемент
                Cluster<T> prev = curCluster.getPrev();
                Cluster<T> next = curCluster.getNext();
                curCluster = null;
                next.setPrev(prev);
                prev.setNext(next);
            } else if (curCluster.getNext() != null && curCluster == tail) {  // Если очистили хвост
                tail = curCluster.getNext();
                curCluster = tail;
                tail.setPrev(null);
            } else if (curCluster.getPrev() != null && curCluster == head) {  // Если отчистили голову
                head = curCluster.getPrev();
                curCluster = head;
                head.setNext(null);
            }
        }
        if (curCluster != null && curCluster.getNext() != null) {  // Если удалили элемент не с головного кластера
            curCluster = curCluster.getNext();  // Перешли на кластер, который ближе к голове (будем с головы в конец отдавать)
            T flowElement;  // Память под элемент, который мы будем из кластера в кластер переводить
            // Теперь перекидываем по одному элементу к кластеру с удалением
            // (чтобы не пустовали ячейки)
            while (found && curCluster != null && curCluster.getCursor() > 0) {
                flowElement = curCluster.removeLast();
                curCluster.getPrev().addFirst(flowElement);
                curCluster = curCluster.getNext();
            }
        }
        return found ? true : false;
    }

    @Override
    public boolean removeLastOccurrence(Object o) {
        T element = (T) o;
        boolean found = false;
        Cluster<T> curCluster = tail;  // Идем с хвоста
        while (curCluster != null && !found) {   // Пока не дошли до головы
            T[] elements = curCluster.getElements();
            int i = 0;  // Проходим кластера с хвоста
            while (!found && i <= curCluster.getCursor() - 1) {
                if (elements[i] == element && !found) {       // Если нашли элемент -> удаляем
                    found = true;
                    curCluster.removeElement(i);
                    size -= 1;
                }
                i++;
            }
            if (!found) {
                curCluster = curCluster.getNext();
            }
        }
        if (curCluster != null && curCluster.getCursor() < 1) { // Если опустошили кластер -> выкидываем
            if (curCluster.getNext() != null && curCluster.getPrev() != null) {// Если есть следующий и предыдущий элемент
                Cluster<T> prev = curCluster.getPrev();
                Cluster<T> next = curCluster.getNext();
                curCluster = null;
                next.setPrev(prev);
                prev.setNext(next);
            } else if (curCluster.getNext() != null && curCluster == tail) {  // Если очистили хвост
                tail = curCluster.getNext();
                curCluster = tail;
                tail.setPrev(null);
            } else if (curCluster.getPrev() != null && curCluster == head) {  // Если отчистили голову
                head = curCluster.getPrev();
                curCluster = head;
                head.setNext(null);
            }
        }
        if (curCluster != null && curCluster.getNext() != null) {  // Если удалили элемент не с головного кластера
            curCluster = curCluster.getNext();  // Перешли на кластер, который ближе к голове (будем с головы в конец отдавать)
            T flowElement;  // Память под элемент, который мы будем из кластера в кластер переводить
            // Теперь перекидываем по одному элементу к кластеру с удалением
            // (чтобы не пустовали ячейки)
            while (found && curCluster != null && curCluster.getCursor() > 0) {
                flowElement = curCluster.removeLast();
                curCluster.getPrev().addFirst(flowElement);
                curCluster = curCluster.getNext();
            }
        }
        return found ? true : false;
    }

    @Override
    public boolean add(T o) {
        addLast(o);
        return true;
    }

    @Override
    public boolean offer(T o) {
        addLast(o);
        return true;
    }

    @Override
    public T remove() {
        return removeFirst();
    }

    @Override
    public T poll() {
        return pollFirst();
    }

    @Override
    public T element() {
        return getFirst();
    }

    @Override
    public T peek() {
        return getFirst();
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        if (c == null) {
            throw new NullPointerException("Collection is null");
        }
        try {
            for (Object element: c) {
                addLast((T) element);
            }
            return true;
        } catch (ClassCastException clsCastEx) {
            throw new ClassCastException("Collection contains illegal type of arguments for this Deque");
        } catch (Exception ex) {
            throw ex;
        }
    }

    @Override
    public void clear() {

    }

    @Override
    public boolean retainAll(Collection c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection c) {
        return false;
    }

    @Override
    public void push(T o) {
        this.addFirst(o);
    }

    @Override
    public T pop() {
        return this.removeFirst();
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public boolean contains(Object o) {
        return false;
    }

    @Override
    public boolean containsAll(Collection c) {
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public Iterator iterator() {
        return null;
    }

    /**
     * Преобразует очередь к массиву. Элементы упорядоченны в порядке от хвоста к голове.
     * @return массив из элементов очереди, где последний элемент -> головной (First), первый -> хвостовой (Last)
     */
    @Override
    public Object[] toArray() {
        Object[] array = new Object[size];
        int i = size-1;
        Cluster<T> curCluster = tail;
        while (i >= 0) {  // Пока не прошли всю очередь
            Object[] elements = curCluster.getElements();
            for (int j = 0; j < curCluster.getCursor(); j++) {  // Заполняем из кластера выходной массив
                array[i] = elements[j];
                i -= 1;
            }
            curCluster = curCluster.getNext();
        }
        return array;
    }

    @Override
    public Object[] toArray(Object[] a) {
        return new Object[0];
    }

    @Override
    public Iterator descendingIterator() {
        return null;
    }

    public Cluster<T> getHead() {
        return head;
    }
}
