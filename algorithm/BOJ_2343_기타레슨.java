import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static int n;
    static int m;
    static int max = -98765;
    static int[] arr;
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        arr = new int[n];
        st = new StringTokenizer(br.readLine());
        int max = 0;
        int sum = 0;
        for (int i=0; i<n; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
            max = Math.max(max, arr[i]);
            sum += arr[i];
        }
        long left = max;
        long right = sum;
        long result = sum;
        while(left<=right) {
            long mid = (left + right)/2;
            if(check(mid)){ //m개의 블루레이로 나눌 수 있는지 확인
                result = mid;
                right = mid -1;
            }else {
                left  = mid +1;
            }
        }
        System.out.println(result);
    }
    static boolean check(long max) {
        long sum = 0;
        int count = 1;
        for(int e : arr) {
            if (sum + e > max) {
                sum = 0;
                count +=1;
            }
            sum += e;
        }
        return count <= m;
    }
}
