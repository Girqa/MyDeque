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

    @Override
    public void addFirst(T o) {
        size += 1;
        head = head.addFirst(o);
    }

    @Override
    public void addLast(T o) {
        size += 1;
        tail = tail.addLast(o);
    }

    @Override
    public boolean offerFirst(T o) {
        return false;
    }

    @Override
    public boolean offerLast(T o) {
        return false;
    }

    @Override
    public T removeFirst() {
        T first;
        if (size > 0) {
            if (head.getCursor() > 1) {
                first = head.removeFirst();
                size -= 1;
            } else {
                if (head.getCursor() == 1 && head.getPrev() != null) {
                    first = head.removeFirst();
                    head = head.getPrev();
                    head.setNext(null);
                    size -= 1;
                } else if (head.getCursor() == 1 && head.getPrev() == null) {
                    first = head.removeFirst();
                    size -= 1;
                } else {
                    throw new DequeIsEmptyException("Deque is empty");
                }
            }
            return first;
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
            } else if (tail.getCursor() == 1 && tail.getNext() != null) {
                last = tail.removeLast();
                tail = tail.getNext();
                tail.setPrev(null);
                size -= 1;
            } else if (tail.getCursor() == 1 && tail.getNext() == null) {
                last = tail.removeLast();
                size -= 1;
            } else {
                throw new DequeIsEmptyException("Deque is empty");
            }
        } else {
            throw new DequeIsEmptyException("Deque is empty");
        }
        return last;
    }

    @Override
    public T pollFirst() {
        return null;
    }

    @Override
    public T pollLast() {
        return null;
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
        return null;
    }

    @Override
    public T peekLast() {
        return null;
    }

    @Override
    public boolean removeFirstOccurrence(Object o) {
        return false;
    }

    @Override
    public boolean removeLastOccurrence(Object o) {
        return false;
    }

    @Override
    public boolean add(T o) {
        return false;
    }

    @Override
    public boolean offer(T o) {
        return false;
    }

    @Override
    public T remove() {
        return null;
    }

    @Override
    public T poll() {
        return null;
    }

    @Override
    public T element() {
        return this.getFirst();
    }

    @Override
    public T peek() {
        return null;
    }

    @Override
    public boolean addAll(Collection c) {
        return false;
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

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public Object[] toArray(Object[] a) {
        return new Object[0];
    }

    @Override
    public Iterator descendingIterator() {
        return null;
    }
}
