public class MoveZeros {
    public static void solution(int[] arr){ // shift by 1
        int i = 0;
        int end = arr.length;
        while(i<end){
            if(arr[i]==0){
                for(int j = i; j<end-1; j++)
                    arr[j] = arr[j+1];
                arr[--end] = 0;
            }
            else i++;
        }
        printArr(arr);
    }

    public static void solution_2(int[] arr){// 2 pointers -> push to front
        int j = 0;
        for(int i = 0; i<arr.length; i++){
            if(arr[i]!=0){
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
                j++;
            }
        }
        printArr(arr);
    }

    public static void printArr(int [] arr){
        for(int num: arr)
            System.out.printf("%d ", num);
        System.out.println("");
    }

    // -----------------------------------------------------------
    public static void main(String[] args) {
        int[] arr = {0, 1, 0, 3, 4, 5};
        solution(arr.clone());
        solution_2(arr.clone());

        arr = new int[]{0, 1, 0, 0, 4, 2, 0};
        solution(arr.clone());
        solution_2(arr.clone());
    }   
}
