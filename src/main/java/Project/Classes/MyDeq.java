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
     * - int size - число элементов очереди
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
    /**
     *  Инициализация двусторонней очереди с параметрами по умолчанию
     */
    public MyDeq() {
        this.clusterSize = ClusterSize.NORMAL;
        Cluster<T> initCluster = new Cluster<>(this.clusterSize.value);
        head = initCluster;
        tail = initCluster;
    }
    /**
     * Инициализация двусторонней очереди (без инициализации значений)
     */
    public MyDeq(ClusterSize clusterSize) {
        this.clusterSize = clusterSize;
        Cluster<T> initCluster = new Cluster<>(this.clusterSize.value);
        head = initCluster;
        tail = initCluster;
    }
    /**
     * Инициализация двусторонней очереди с инициализацией значений
     * startState - начальное состояние (наполнение) очереди
     */
    public MyDeq(ClusterSize clusterSize, T[] startState) {
        this.clusterSize = clusterSize;
        Cluster<T> initCluster = new Cluster<>(this.clusterSize.value);
        head = initCluster;
        tail = initCluster;
        for (T element: startState) {
            addFirst(element);
        }
    }
    /**
     * Инициализация двусторонней очереди с инициализацией значений
     * startState - начальное состояние (наполнение) очереди
     */
    public MyDeq(ClusterSize clusterSize, Collection<? extends T> c) {
        this.clusterSize = clusterSize;
        Cluster<T> initCluster = new Cluster<>(this.clusterSize.value);
        head = initCluster;
        tail = initCluster;
        addAll(c);
    }

    /**
     * Добавление элемента в голову
     * Бросает NullPointerException если произошла попытка добавления null лемента
     * @param o the element to add
     */
    @Override
    public void addFirst(T o) {
        if (o == null) {
            throw new NullPointerException();
        }
        size += 1;
        head = head.addFirst(o);
    }

    /**
     * Добавление в хвост
     * Бросает NullPointerException если произошла попытка добавления null лемента
     * @param o the element to add
     */
    @Override
    public void addLast(T o) {
        if (o == null) {
            throw new NullPointerException();
        }
        size += 1;
        tail = tail.addLast(o);
    }

    /**
     * Попытка добавления в начало очереди
     * @param o the element to add
     * @return добавил в голову или нет (true/false)
     */
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

    /**
     * Попытка добавления в конец очереди
     * @param o the element to add
     * @return добавил или нет true/false
     */
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

    /**
     * Попытка достать элемент из начала (вернуть значение и удалить из очереди).
     * Выбрасывает DequeIsEmptyException если очередь пуста
     * @return first element
     */
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

    /**
     * Попытка достать последний элемент (вернуть значение и удалить из очереди).
     * Если очередь пуста -> DequeIsEmptyException.
     * @return last element
     */
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

    /**
     * То же что и removeFirst, но вернет null, если очередь пуста
     */
    @Override
    public T pollFirst() {
        if (size == 0) {
            return null;
        }
        return removeFirst();
    }

    /**
     * То же что и removeLast, но вернет null, если очередь пуста
     */
    @Override
    public T pollLast() {
        if (size == 0) {
            return null;
        }
        return removeLast();
    }

    /**
     * Возвращает значение первого элемента.
     * Если очередь пуста -> DequeIsEmptyException
     * @return first element
     */
    @Override
    public T getFirst() {
        if (size > 0 && head.getCursor() > 0) {
            return head.getFirst();
        }
        throw new DequeIsEmptyException("Deque is empty");
    }
    /**
     * Возвращает значение последнего элемента.
     * Если очередь пуста -> DequeIsEmptyException
     * @return last element
     */
    @Override
    public T getLast() {
        if (size > 0) {
            return tail.getLast();
        }
        throw new DequeIsEmptyException("Deque is empty");
    }

    /**
     * То же, что и getFirst, но возвращает null, если очередь пуста
     * @return first element
     */
    @Override
    public T peekFirst() {
        if (size == 0) {
            return null;
        }
        return getFirst();
    }
    /**
     * То же, что и getLast, но возвращает null, если очередь пуста
     * @return last element
     */
    @Override
    public T peekLast() {
        if (size == 0) {
            return null;
        }
        return getLast();
    }

    /**
     * Удаляет элемент o если он присутствует. Ищет элемент от начала.
     * @param o element to be removed from this deque, if present
     * @return если произошло удаление -> true иначе -> false
     */
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
    /**
     * Удаляет элемент o если он присутствует. Ищет элемент с конца.
     * @param o element to be removed from this deque, if present
     * @return если произошло удаление -> true иначе -> false
     */
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

    /**
     * То же, что и addLast
     * @param o element whose presence in this collection is to be ensured
     * @return true если очередь изменилась
     */
    @Override
    public boolean add(T o) {
        addLast(o);
        return true;
    }
    /**
     * То же, что и addLast
     * @param o element whose presence in this collection is to be ensured
     * @return true если очередь изменилась
     */
    @Override
    public boolean offer(T o) {
        addLast(o);
        return true;
    }

    /**
     * То же, что и removeFirst
     * @return firstElement
     */
    @Override
    public T remove() {
        return removeFirst();
    }
    /**
     * То же, что и pollFirst
     * @return firstElement
     */
    @Override
    public T poll() {
        return pollFirst();
    }

    /**
     * То же, что и getFirst
     * @return first element
     */
    @Override
    public T element() {
        return getFirst();
    }
    /**
     * То же, что и getFirst
     * @return first element
     */
    @Override
    public T peek() {
        return getFirst();
    }

    /**
     * Добавляет все элементы из представленной коллекции в очередь.
     * Бросает:
     * - ClassCastException если коллекция содержит элементы другого типа.
     * - NullPointerException если коллекция == null (или содержит null)
     * @param c collection containing elements to be added to this collection
     * @return true если произошло успешное добавление
     */
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

    /**
     * Очищает DEQ. Отчистка осуществляется за счет сборщика мусора. Переопределяю ссылки на головной и
     * хвостовой элемент, чем говрю сборщику мусора, что можно удалить прошлый набор кластеров.
     */
    @Override
    public void clear() {
        size = 0;
        Cluster<T> initCluster = new Cluster<>(clusterSize.value);
        head = initCluster;
        tail = initCluster;
    }

    /**
     * Удаляет все элементы, которые не включенны в Collection c.
     * @param c collection containing elements to be retained in this collection
     * @return true если выполнили удаления, иначе false
     */
    @Override
    public boolean retainAll(Collection c) {
        boolean flg = false;
        if (c == null) {
            throw new NullPointerException();
        }
        for (T el: this) {
            if (! c.contains(el)) {
                flg = true;
                removeLastOccurrence(el);
            }
        }
        return flg;
    }

    /**
     * Удаляет все элементы, которые включенны в Collection c.
     * @param c collection containing elements to be removed from this collection
     * @return true если произошло удаление
     */
    @Override
    public boolean removeAll(Collection c) {
        boolean flg = false;
        if (c == null) {
            throw new NullPointerException();
        }
        for (T el: this) {
            if (c.contains(el)) {
                flg = true;
                removeLastOccurrence(el);
            }
        }
        return flg;
    }

    /**
     * То же, что и addFirst
     * @param o the element to push
     */
    @Override
    public void push(T o) {
        this.addFirst(o);
    }
    /**
     * То же, что и removeFirst
     */
    @Override
    public T pop() {
        return this.removeFirst();
    }

    /**
     * То же, что и removeFirstOccurrence
     * @param o element to be removed from this deque, if present
     */
    @Override
    public boolean remove(Object o) {
        return removeFirstOccurrence(o);
    }

    /**
     * Проверка вхождения переданного элемента в очередь.
     * @param o element whose presence in this deque is to be tested
     * @return если элемент в очереди -> true, иначе -> false
     */
    @Override
    public boolean contains(Object o) {
        if (o == null) {
            throw new NullPointerException();
        }
        Iterator<T> it = iterator();
        boolean found = false;
        while (it.hasNext() && !found) {
            T element = it.next();
            if (element == o) {
                found = true;
            }
        }
        return found;
    }

    /**
     * Проверяет, входят ли ВСЕ элементы коллекции в очередь.
     * @param c collection to be checked for containment in this collection
     * @return если все элементы входят -> true, иначе -> false
     */
    @Override
    public boolean containsAll(Collection c) {
        if (c == null) {
            throw new NullPointerException();
        }
        for (Object element: c) {
            if (!contains(element)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Возвращает размер очереди
     * @return количество элементов в очереди
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Проверка наполненности очереди
     * @return если очередь пуста -> true, иначе -> false
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Возвращает итератор по очереди
     * @return Iterator
     */
    @Override
    public Iterator iterator() {
        MyDeq<T> deq = this;
        Iterator<T> it = new Iterator<T>() {
            private int curIndex = 0;
            private T[] array = (T[]) deq.toArray();

            @Override
            public boolean hasNext() {
                return curIndex < array.length && array[curIndex] != null;
            }

            @Override
            public T next() {
                return array[curIndex++];
            }
        };
        return it;
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

    /**
     * @param a the array into which the elements of this collection are to be
     *        stored, if it is big enough; otherwise, a new array of the same
     *        runtime type is allocated for this purpose.
     * @return исходный заполненный массив или новый массив с элементами очереди, если объем исходного недостаточен
     */
    @Override
    public Object[] toArray(Object[] a) {
        if (a.length >= size) {
            int i = 0;
            for (T el : this) {
                a[i++] = el;
            }
            return a;
        }else {
            return toArray();
        }
    }

    /**
     * -------
     * @return
     */
    @Override
    public Iterator descendingIterator() {
        return null;
    }

    /**
     * Вспомогательный метод для проведения тестов (стоит удалить, но пока оставим)
     * @return
     */
    public Cluster<T> getHead() {
        return head;
    }
}
