package data.structures.recursion;

public class Sum {

    public static int sum(int[] arr){
        return sum(arr,0);
    }

    public static int sum(int[] arr, int index){
        if(index + 1 == arr.length)
            return arr[index];
        return arr[index] + sum(arr, index + 1);
    }

    public static void main(String[] args) {
        System.out.println(sum(new int[]{1,2,3,4,5,6,7,8,9}));
    }

}
