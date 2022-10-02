package Project;


import Project.Classes.MyDeq;

public class Main {
    public static void main(String[] args) {
        MyDeq<String> deq1 = new MyDeq<>();
        MyDeq<String> deq2 = new MyDeq<>(MyDeq.ClusterSize.BIG);
        int[] arr = new int[]{1, 2, 3, 4, 5};
        MyDeq<Integer> deq3 = new MyDeq<>(MyDeq.ClusterSize.SMALL, intArrayToIntegerArray(arr));
        deq1.addFirst("S0");
        deq1.addFirst("S1");
        deq1.addFirst("S2");
        deq1.addFirst("S3");
        deq1.addFirst("S4");
        deq1.addFirst("S5");
        System.out.println(deq1.getFirst());
        deq1.removeFirst();
        System.out.println(deq1.getFirst());
        deq1.removeFirst();
        System.out.println(deq1.getFirst());
        deq1.removeFirst();
        System.out.println(deq1.getFirst());
        deq1.removeFirst();
        System.out.println(deq1.getFirst());
        deq1.removeFirst();
        System.out.println(deq1.getFirst());
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
