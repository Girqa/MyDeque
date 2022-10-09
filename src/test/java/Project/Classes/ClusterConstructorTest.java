package Project.Classes;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class ClusterConstructorTest {

    private static Integer[] intArrayToIntegerArray(int[] array){
        /*
        Статический метод для преобразования массива int в массив Integer (переход от примитива к ссылочному типу)
         */
        Integer[] transformedArray = new Integer[array.length];
        for (int i = 0; i < array.length; i++) {
            transformedArray[i] = array[i];  // Автоматически кастует к Integer
        }
        return transformedArray;
    }

    @Test
    void clusterConstructor() {
        int size = 4;
        // Проверка создания кластера без предзаданного начального размера
        Integer[] elements = new Integer[size];
        Cluster<Integer> cluster1 = new Cluster<>(elements);
        // Проверка корректности записи массивов (содержание одинаковое)
        assertArrayEquals(cluster1.getElements(), elements);
        // Проверка того, что элементы записались в другую область памяти (не скопировали ссылку)
        assertNotEquals(cluster1, elements);

        // Проверка создания кластера с предзаданным начальным размером
        Cluster<Integer> cluster = new Cluster<>(size, elements);
        // Проверка, что невозможно сосздать кластер, в которм указан размер не равный длине поданного массива
        assertThrows(IllegalArgumentException.class, () -> {
                    Cluster<Integer> clr = new Cluster<>(size + 1, elements);
                }
        );
        // Проверка инициализации с пустым массивом (или без параметров)
        int[] emptyArray;
        Cluster<Integer> cluster3 = new Cluster<>();
        assertEquals(cluster3.getSize(), 0);
    }

    @Test
    void getElements() {
        int[] numbers = {1, 2, 3, 4};
        Integer[] elements = intArrayToIntegerArray(numbers);
        Cluster<Integer> cluster1 = new Cluster<>(elements);
        // Проверка корректности записи массивов (содержание одинаковое)
        assertArrayEquals(cluster1.getElements(), elements);
        // Проверка невозможности доступа к элементам кластера через elements (внешний)
        assertNotEquals(cluster1.getElements(), elements); // различность области памяти хранения массивов
        elements[0] = -1;
        assertNotEquals(cluster1.getElements(), elements);
    }

    @Test
    void setElements() {
        // Проверка возможности замены элементов кластера
        int[] intArray1 = {0, 0, 0};
        Integer[] integerArray1 = intArrayToIntegerArray(intArray1);
        int[] intArray2 = {-3, -2, -1};
        Integer[] integerArray2 = intArrayToIntegerArray(intArray1);

        Cluster<Integer> claster1 = new Cluster<>(integerArray1);
        claster1.setElements(integerArray2);
        assertArrayEquals(claster1.getElements(), integerArray2);

        // Проверка невозможности подсунуть массив длины не равной size
        int[] intArray3 = {10, -9};
        Integer[] integerArray3 = intArrayToIntegerArray(intArray3);
        assertThrows(IllegalArgumentException.class, () -> {
            claster1.setElements(integerArray3);
        });
    }

    @Test
    void removeElement() {
        // Удаление одного неповторяющегося элемента
        Character[] data1 = new Character[]{'a', 'b', 'c', 'd', 'e'};
        Cluster<Character> cluster1 = new Cluster<>(data1);
        cluster1.removeElement(3);
        assertArrayEquals(new Character[]{'a', 'b', 'c', 'e', null}, cluster1.getElements());

        // Удаление одного повторяющегося элемента
        Character[] data2 = new Character[]{'a', 'b', 'd', 'd', 'e'};
        Cluster<Character> cluster2 = new Cluster<>(data2);
        cluster2.removeElement(3);
        assertArrayEquals(new Character[]{'a', 'b', 'd', 'e', null}, cluster2.getElements());

        // Удаление первого элемента (первым по курсору будет 'e')
        Character[] data3 = new Character[]{'a', 'b', 'c', 'd', 'e'};
        Cluster<Character> cluster3 = new Cluster<>(data3);
        cluster3.removeElement(4);
        assertArrayEquals(new Character[]{'a', 'b', 'c', 'd', null}, cluster3.getElements());

        // Удаление последнего элемента (последним будет 'a')
        Character[] data4 = new Character[]{'a', 'b', 'c', 'd', 'e'};
        Cluster<Character> cluster4 = new Cluster<>(data4);
        cluster4.removeElement(0);
        assertArrayEquals(new Character[]{'b', 'c', 'd', 'e', null}, cluster4.getElements());
    }

    @Test
    void removeFirst() {
        Character[] data1 = new Character[]{'a', 'b', 'c', 'd', 'e'};
        Cluster<Character> cluster = new Cluster<>(data1);
        cluster.removeLast();
        System.out.println(Arrays.toString(cluster.getElements()));
        System.out.println(cluster.getCursor());
    }
}