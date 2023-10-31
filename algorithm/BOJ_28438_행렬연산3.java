import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BOJ_28438_행렬연산3 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int q = Integer.parseInt(st.nextToken());

        int[][] arr = new int[n+2][m+2];
        for(int i=0; i<q; i++) {
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            int z = Integer.parseInt(st.nextToken());

            if (x==1) {
                arr[y][1] += z;
                arr[y+1][1] -= z;
            }else {
                arr[1][y] += z;
                arr[1][y+1] -= z;
            }
        }

        for(int i=1; i<=n; i++) {
            for(int j=1; j<=m; j++) {
                arr[i][j] += (arr[i-1][j] + arr[i][j-1] + arr[i-1][j-1]);
            }
        }

        for(int i=1; i<=n; i++) {
            for(int j=1; j<=m; j++) {
                System.out.print(arr[i][j]+" ");
            }
            System.out.println();
        }
    }
}
