
import java.util.*;
import java.lang.*;
import java.io.*;

//이 문제는 재귀로 문제를 접근할 수 있따.
class Main
{
    static int n;
    static int blue;
    static int white;
    static int[][] arr;
    public static void main (String[] args) throws java.lang.Exception
    {
        // your code goes here
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        n = Integer.parseInt(br.readLine());
        arr = new int[n][n];

        for(int i=0; i<n; i++){
            String[] arr2 = br.readLine().split(" ");
            for(int j=0; j<n; j++){
                arr[i][j] = Integer.parseInt(arr2[j]);
            }
        }//end

        getPartition(0, 0, n);
        System.out.println(white);
        System.out.println(blue);


    }

    static void getPartition(int row, int col, int size) {
        int color = arr[row][col];
        if(isSameColor(row, col, size)) {
            if (color == 1) {
                blue += 1;
            }else {
                white += 1;
            }
            return;
        }
        size /= 2;
        getPartition(row, col, size);
        getPartition(row+size, col+size, size);
        getPartition(row+size, col, size);
        getPartition(row, col+size, size);
    }

    static boolean isSameColor(int row, int col, int size) {
        int color = arr[row][col];
        for(int i=row; i<row+size; i++) {
            for(int j=col; j<col+size; j++) {
                if(color != arr[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }
}