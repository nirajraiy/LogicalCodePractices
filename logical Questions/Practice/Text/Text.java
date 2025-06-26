import java.util.ArrayList;
import java.util.List;

public class Text {
    public static boolean isPrime(int num) {
        if (num < 2) {
            return false;
        }
        for (int i = 2; i <= Math.sqrt(num); i++) {
            if (num % i == 0) {
                return false;
            }
        }
        return true;
    }

    public static List<List<Integer>> generatePrimeSequence(int n) {
        List<List<Integer>> sequence = new ArrayList<>();
        int num = 2;
        for (int i = 0; i < n; i++) {
            List<Integer> row = new ArrayList<>();
            for (int j = 0; j < n-i; j++) {
                while (!isPrime(num)) {
                    num++;
                }
                row.add(num);
                num++;
            }
            sequence.add(row);
        }
        return sequence;
    }

    public static void main(String[] args) {
        int n = 5; 
        List<List<Integer>> output = generatePrimeSequence(n);
        List<List<Integer>> transposed = new ArrayList<>();
    
        int maxLen = 0;
        for (List<Integer> row : output) {
            maxLen = Math.max(maxLen, row.size());
        }
    
        for (int j = 0; j < maxLen; j++) {
            List<Integer> col = new ArrayList<>();
            for (List<Integer> row : output) {
                if (j < row.size()) {
                    col.add(row.get(j));
                } else {
                    col.add(0); 
                }
            }
            transposed.add(col);
        }
    
        for (List<Integer> row : transposed) {
            for (int num : row) {
                System.out.print((num==0)? "":num + " ");
            }
            System.out.println();
        }
    }
}


// public class Text {

//     public static void main(String[] args) {
//         int n=5;
//         int lineCheanger=0;
//         for(int i=0;i<n;i++)  {
//             if(i==lineCheanger) {
//                 lineCheanger=i+1;
//                 i=0;
//                 System.out.println();
//             } else {
//                 System.out.print("* ");
//             }
//         }
//     }
// }





// import java.util.ArrayList;
// import java.util.Collections;
// import java.util.List;

// public class Text {

//     public static void main(String[] args) {
//         // int[] arr = {21, 7, 4, 8, 5, 1, 7, -4};
//         int [] arr = {15, 4, 10,2,5,8,4};
//         List<Integer> nums = new ArrayList<>();
//         int t = arr[arr.length - 1];
//         nums.add(t);

//         for (int i = arr.length - 2; i >= 0; i--) {
//             if (t < arr[i]) {
//                 nums.add(arr[i]);
//                 t = arr[i];
//             }
//         }
//         Collections.reverse(nums);

//         for (int num : nums) {
//             System.out.print(num+ " ");
//         }
//     }
// }

