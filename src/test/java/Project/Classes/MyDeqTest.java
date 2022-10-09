package Project.Classes;

import Project.Exceptions.DequeIsEmptyException;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class MyDeqTest {

    @Test
    void constructor() {

        // Проверка пустого конструктора
        MyDeq<Integer> deq = new MyDeq<>();
        assertEquals(0, deq.size());
        // Проверка конструктора предзаполненной очереди
        String[] data = new String[]{"Los", "Os", "Sos", "Pos", "Ros"};
        MyDeq<String> deq1 = new MyDeq<>(MyDeq.ClusterSize.BIG, data);
        assertEquals(data.length, deq1.size());
    }

    @Test
    void addFirst() {
        MyDeq<Integer> deq = new MyDeq<>();

        // Проверка очереди с одним элементом
        deq.addFirst(2);
        assertEquals(deq.getFirst(), 2);
        assertEquals(1, deq.size());
        // Проверка очереди с нескольикими элементами
        deq.addFirst(5);
        assertEquals(deq.getFirst(), 5);
        assertEquals(2, deq.size());
        // Проверка урезанной очереди
        deq.removeFirst();
        assertEquals(deq.getFirst(), 2);
        assertEquals(1, deq.size());
        // Проверка пустой очереди
        deq.removeFirst();
        assertThrows(DequeIsEmptyException.class, () -> {
            deq.getFirst();
        });
        assertEquals(0, deq.size());
        // Проверка пустой очереди (без предварительного заполнения)
        MyDeq<String> deq2 = new MyDeq<>(MyDeq.ClusterSize.BIG);
        assertThrows(DequeIsEmptyException.class, () -> {
            deq2.getFirst();
        });
        assertEquals(0, deq2.size());
    }

    @Test
    void getFirst() {
        MyDeq<Boolean> deq = new MyDeq<>();

        // Проверка пустой очереди
        assertThrows(DequeIsEmptyException.class, () -> {
            deq.getFirst();
        });

        // Проверка очереди с одним элементом
        deq.addFirst(true);
        assertEquals(deq.getFirst(), true);

        // Проверка очереди с несколькими кластерами
        Double[] data = new Double[]{1.0, 2.0, 3.0, 3.14};
        MyDeq<Double> deq1 = new MyDeq<>(MyDeq.ClusterSize.SMALL, data);
        assertEquals(deq1.getFirst(), 3.14);
    }

    @Test
    void removeFirst() {
        MyDeq<Character> deq = new MyDeq<>();

        // Проверка пустой очереди
        assertThrows(DequeIsEmptyException.class, () -> {
            deq.removeFirst();
        });

        // Проверка заполненной очереди
        deq.addFirst('A');
        deq.addFirst('B');
        assertEquals(2, deq.size());
        assertEquals(deq.removeFirst(), 'B');  // Возврат первого элемента
        assertEquals(1, deq.size());
        assertEquals(deq.getFirst(), 'A');     // Удаление старого первого элемента

        // Проверка очереди из белее чем одного кластера
        Long[] data = new Long[]{1L, 2L, 10000000000L, 3L};
        MyDeq<Long> deq1 = new MyDeq<>(MyDeq.ClusterSize.SMALL, data);
        assertEquals(3L, deq1.removeFirst());
        assertEquals(3, deq1.size());
        assertEquals(10000000000L, deq1.removeFirst());
        assertEquals(2, deq1.size());
    }

    @Test
    void getLast() {

        // Проверка пустой очереди
        MyDeq<Short> deq1 = new MyDeq<>();
        assertThrows(DequeIsEmptyException.class, () -> {
            deq1.getLast();
        });

        // Проверка очереди с одним кластером
        Float[] data2 = new Float[]{1f, 34242442f, -234_567_898_765_422_350_000_000_000_000_000_000_000f};
        MyDeq<Float> deq2 = new MyDeq<>(MyDeq.ClusterSize.NORMAL, data2);
        assertEquals(data2[0], deq2.getLast());

        // Проверка очереди с несколькими кластерами (не потерял хвост)
        Byte[] data3 = new Byte[]{1, -1, 127, 0, -128, 64, -16, 32};
        MyDeq<Byte> deq3 = new MyDeq<>(MyDeq.ClusterSize.NORMAL, data3);
        assertEquals(data3[0], deq3.getLast());

        // Проверка очереди с несколькими кластерами после удаления элементов головного кластера
        Character[] data4 = new Character[]{'@', '#', '%'};
        MyDeq<Character> deq4 = new MyDeq<>(MyDeq.ClusterSize.SMALL, data4);
        deq4.removeFirst();
        assertEquals('@', deq4.getLast());

        // Проверка после удаления последнего элемента
        deq4.removeLast();
        assertEquals('#', deq4.getLast());

        // Проверка очереди с несколькими кластерами после отчистки хвостового кластера
        Boolean[] data6 = new Boolean[]{true, false, true, false, true};
        MyDeq<Boolean> deq6 = new MyDeq<>(MyDeq.ClusterSize.SMALL, data6);
        deq6.removeLast();
        assertEquals(false, deq6.getLast());
    }

    @Test
    void removeLast() {
        // Проверка пустой очереди
        MyDeq<Integer> deq1 = new MyDeq<>();
        assertThrows(DequeIsEmptyException.class, () -> {
            deq1.removeLast();
        });

        // Проверка очереди с одним элементом (затем еще раз пустой очереди)
        MyDeq<Float> deq2 = new MyDeq<>(MyDeq.ClusterSize.BIG, new Float[]{3f});
        assertEquals(3f ,deq2.removeLast());
        assertEquals(0, deq2.size());
        assertThrows(DequeIsEmptyException.class, () -> {
            deq2.removeLast();
        });

        // Проверка очереди с одним кластером после удаления всех элементов с начала
        MyDeq<Integer> deq3 = new MyDeq<>(MyDeq.ClusterSize.BIG, new Integer[]{1, 2, 3, 4});
        deq3.removeLast();
        deq3.removeFirst();
        deq3.removeFirst();
        assertEquals(1, deq3.size());
        assertEquals(2, deq3.removeLast());
        assertEquals(0, deq3.size());

        // Проверка очереди с несколькими кластерами
        MyDeq<Boolean> deq4 = new MyDeq<>(MyDeq.ClusterSize.SMALL, new Boolean[]{true, false, true});
        assertEquals(true, deq4.removeLast());
        assertEquals(2, deq4.size());
        assertEquals(true, deq4.getFirst());
        assertEquals(false, deq4.getLast());

        // Проверка очереди с несколькими кластерами после отчистки последнего кластера
        MyDeq<String> deq5 = new MyDeq<>(MyDeq.ClusterSize.NORMAL, new String[]{
                "a",
                "ab",
                "aba",
                "abba",
                "abbba",
                "abbbba",
                "abbbbba",
                "abbbbbba"});
        deq5.removeLast();
        deq5.removeLast();
        deq5.removeLast();
        deq5.removeLast();
        deq5.removeLast();
        assertEquals("abbbba", deq5.getLast());
        assertEquals("abbbba", deq5.removeLast());
    }

    @Test
    void addLast() {
        // Проверка пустой очереди
        MyDeq<Integer> deq1 = new MyDeq<>();
        deq1.addLast(404);
        assertEquals(404, deq1.getLast());
        assertEquals(1, deq1.size());

        // Проверка очереди с одним неполным кластером
        MyDeq<Float> deq2 = new MyDeq<>(MyDeq.ClusterSize.NORMAL, new Float[]{0f, 33f, 4f, 5f});
        deq2.addLast(2f);
        assertEquals(2f, deq2.getLast());
        assertEquals(5f, deq2.getFirst());
        assertEquals(5, deq2.size());

        // Проверка очереди с одним полным кластером
        MyDeq<Short> deq3 = new MyDeq<>(MyDeq.ClusterSize.SMALL, new Short[]{12});
        deq3.addLast((short) 2);
        assertEquals((short) 2, deq3.getLast());
        assertEquals((short) 12, deq3.getFirst());
        assertEquals(2, deq3.size());
    }

    @Test
    void removeFirstOccurrence(){
        // Удаление единичного экземпляра в середине полного кластера
        Character[] data1 = new Character[]{'a', 'b', 'c', 'd', 'e'};
        Character[] resultData = new Character[]{'e', 'd', 'b', 'a'};
        MyDeq<Character> deq1 = new MyDeq<>(MyDeq.ClusterSize.NORMAL, data1);
        assertEquals(true, deq1.removeFirstOccurrence('c'));
        assertArrayEquals(resultData, deq1.toArray());

        // Удаление повторяющегося элемента
        Integer[] data2 = new Integer[]{3, 4, 5, 5, 2, 1};
        MyDeq<Integer> deq2 = new MyDeq<>(MyDeq.ClusterSize.NORMAL, data2);
        assertEquals(true, deq2.removeFirstOccurrence(5));

        // Попытка удалить отсутствующий элемент
        List<Integer> data3 = Arrays.asList(new Integer[]{1, 2, 3, 4, 5, 6, 7});
        MyDeq<Integer> deq3 = new MyDeq<>(MyDeq.ClusterSize.BIG, data3);
        assertEquals(false, deq3.removeFirstOccurrence(8));
        assertArrayEquals(data3.toArray(), deq3.toArray());

        // Удаление элемента из кластера, который не головной
        List<Integer> data4 = Arrays.asList(new Integer[]{1, 2, 3, 4, 5, 6, 7});
        MyDeq<Integer> deq4 = new MyDeq<>(MyDeq.ClusterSize.NORMAL, data4);
        assertEquals(true, deq4.removeFirstOccurrence(3));
        assertArrayEquals(new Integer[]{1, 2, 4, 5, 6, 7}, deq4.toArray());

        // Отчистка кластера
        Integer[] data5 = new Integer[]{1, 2, 3, 4};
        MyDeq<Integer> deq5 = new MyDeq<>(MyDeq.ClusterSize.SMALL, data5);
        assertEquals(true, deq5.removeFirstOccurrence(2));
        Cluster<Integer> head = deq5.getHead();
        assertArrayEquals(new Integer[]{4, 3, 1}, deq5.toArray());

        // Отчистка головного кластера
        Integer[] data6 = new Integer[]{1, 2, 3, 4, 5, 6};
        MyDeq<Integer> deq61 = new MyDeq<>(MyDeq.ClusterSize.NORMAL, data6);
        assertEquals(true, deq61.removeFirstOccurrence(6));
        assertArrayEquals(new Integer[] {5, 4, 3, 2, 1}, deq61.toArray());

        MyDeq<Integer> deq62 = new MyDeq<>(MyDeq.ClusterSize.SMALL, data6);
        assertEquals(true, deq62.removeFirstOccurrence(6));
        assertArrayEquals(new Integer[] {5, 4, 3, 2, 1}, deq62.toArray());

        // Отчистка хвоста (возможна только при размере кластеров в 1 элемент
        Integer[] data7 = new Integer[]{5, 2, 10, 100};
        MyDeq<Integer> deq7 = new MyDeq<>(MyDeq.ClusterSize.SMALL, data7);
        assertEquals(true, deq7.removeFirstOccurrence(5));
        assertArrayEquals(new Integer[]{100, 10, 2}, deq7.toArray());

        // Уделение единственного элемента очереди
        MyDeq<Boolean> deq8 = new MyDeq<>();
        deq8.addFirst(true);
        assertEquals(true, deq8.removeFirstOccurrence(true));
        assertArrayEquals(new Boolean[]{}, deq8.toArray());
        deq8.addFirst(true);
        deq8.addFirst(false);
        deq8.addFirst(true);
        assertArrayEquals(new Boolean[]{true, false, true}, deq8.toArray());
    }

    @Test
    void removeLastOccurence(){
        // Удаление элемента из середины кластера
        MyDeq<Integer> deq1 = new MyDeq<>();
        deq1.addAll(Arrays.asList(new Integer[]{1, 2, 3, 4, 5}));
        assertEquals(true, deq1.removeLastOccurrence(3));
        assertArrayEquals(new Integer[]{1, 2, 4, 5}, deq1.toArray());

        // Удаление последнего элемента кластера
        MyDeq<Character> deq2 = new MyDeq<>();
        deq2.addAll(Arrays.asList(new Character[]{'1', '2', 'a', '&'}));
        assertEquals(true, deq2.removeLastOccurrence('&'));
        assertArrayEquals(new Character[]{'1', '2', 'a'}, deq2.toArray());

        // Удаление первого элемента кластера
        MyDeq<Integer> deq3 = new MyDeq<>();
        deq3.addAll(Arrays.asList(new Integer[]{5, 2, 3, 4, 44, -1}));
        assertEquals(true, deq3.removeLastOccurrence(5));
        assertArrayEquals(new Integer[]{2, 3, 4, 44, -1}, deq3.toArray());

        // Удаление последнего повторения при наличаи больше одного повторения
        MyDeq<String> deq4 = new MyDeq<>();
        deq4.addAll(Arrays.asList(new String[]{"112", "32", "32", "as", "sa"}));
        assertEquals(true, deq4.removeLastOccurrence("32"));
        assertArrayEquals(new String[]{"112", "32", "as", "sa"}, deq4.toArray());

        // Попытка удаления отсутствующего элемента
        MyDeq<Integer> deq5 = new MyDeq<>(MyDeq.ClusterSize.SMALL, new Integer[]{5,2,3,1});
        assertEquals(false, deq5.removeLastOccurrence(4));
        assertArrayEquals(new Integer[]{1, 3, 2, 5}, deq5.toArray());

        // Удаление последнего элемента кластера
        assertEquals(true, deq5.removeLastOccurrence(2));
        assertArrayEquals(new Integer[]{1,3,5}, deq5.toArray());

        // Удаление единственного элемента очереди
        MyDeq<Integer> deq6 = new MyDeq<>();
        deq6.addLast(2);
        assertEquals(true, deq6.removeLastOccurrence(2));
        assertArrayEquals(new Integer[]{}, deq6.toArray());
    }

    @Test
    void addAll() {
        // Добавление в пустую очередь
        MyDeq<Float> deq1 = new MyDeq<>();
        List<Float> data1 = new LinkedList<>();
        data1.add(2f);
        data1.add(3f);
        data1.add(2.2f);
        data1.add(1.2f);
        assertEquals(true, deq1.addAll(data1));
        Deque<Float> deque = new ArrayDeque<>();
        deque.addAll(data1);
        assertArrayEquals(deque.toArray(), deq1.toArray());

        // Добавление пустой коллекции
        MyDeq<Integer> deq2 = new MyDeq<>();
        List<Integer> data2 = new ArrayList<>();
        assertEquals(true, deq2.addAll(data2));
        assertArrayEquals(data2.toArray(), deq2.toArray());

        // Добавление в уже предзаполненную очередь
        MyDeq<Byte> deq3 = new MyDeq<>(MyDeq.ClusterSize.NORMAL, new Byte[]{1, -1, 2, 5});
        List<Byte> data3 = new ArrayList<>();
        data3.add((byte) -2);
        data3.add((byte) -2);
        data3.add((byte) -3);
        assertEquals(true, deq3.addAll(data3));
        Byte[] rightData = new Byte[]{5, 2, -1, 1, -2, -2, -3};
        assertArrayEquals(rightData, deq3.toArray());

        // Добавление null вместо коллекции
        MyDeq<String> deq4 = new MyDeq<>();
        List<String> data4 = null;
        assertThrows(NullPointerException.class, () -> {
            deq4.addAll(data4);
        });
    }
}