package Project;


import Project.Classes.MyDeq;

public class Main {
    public static void main(String[] args) {
        MyDeq<String> deq1 = new MyDeq<>();
        MyDeq<String> deq2 = new MyDeq<>(MyDeq.ClusterSize.BIG);
        int[] arr = new int[]{1, 2, 3, 4, 5};
        MyDeq<Integer> deq3 = new MyDeq<>(MyDeq.ClusterSize.SMALL, intArrayToIntegerArray(arr));
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
