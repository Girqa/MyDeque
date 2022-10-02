package Project.Classes;

import Project.Exceptions.DequeIsEmptyException;
import org.junit.jupiter.api.Test;

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
}