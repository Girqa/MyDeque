package Project.Classes;

import java.util.Collection;
import java.util.Deque;
import java.util.Iterator;

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
    private Cluster head;
    private Cluster tail;

    public MyDeq() {
        /*
         *  Инициализация двусторонней очереди с параметрами по умолчанию
         */
        this.clusterSize = ClusterSize.NORMAL;

    }

    public MyDeq(ClusterSize clusterSize) {
        /*
         * Инициализация двусторонней очереди (без инициализации значений)
         */
        this.clusterSize = clusterSize;
    }

    public MyDeq(ClusterSize clusterSize, T... startState) {
        /*
         * Инициализация двусторонней очереди с инициализацией значений
         * startState - начальное состояние (наполнение) очереди
         */
        this.clusterSize = clusterSize;
    }


    @Override
    public void addFirst(T o) {

    }

    @Override
    public void addLast(T o) {

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
        return null;
    }

    @Override
    public T removeLast() {
        return null;
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
        return null;
    }

    @Override
    public T getLast() {
        return null;
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
        return null;
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

    }

    @Override
    public T pop() {
        return null;
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
        return 0;
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
