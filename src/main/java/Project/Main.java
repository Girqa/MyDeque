package Project;


import Project.Classes.MyDeq;
import java.util.Deque;
import java.util.ArrayDeque;

public class Main {
    public static void main(String[] args) {
        Deque<Integer> deq = new ArrayDeque<>();
        deq.addFirst(5);
        deq.addFirst(5);
        deq.addFirst(5);
        deq.addFirst(5);
        deq.addFirst(5);
        System.out.println(deq.offerFirst(3));
        System.out.println(deq.removeFirst());
    }


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
}
