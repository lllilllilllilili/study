import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class 서강그라운드2 {
    static int N, M, R, maxItems;
    static int[] items;
    static int[][] paths;
    static boolean[] visited;
    static int sum = 0;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken()); // 지역의 개수
        M = Integer.parseInt(st.nextToken()); // 수색 범위
        R = Integer.parseInt(st.nextToken()); // 길의 개수

        items = new int[N + 1];
        paths = new int[101][101];
        visited = new boolean[101];

        st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= N; i++) {
            items[i] = Integer.parseInt(st.nextToken());
        }

        // 길의 정보를 입력 받아서 paths 배열을 채움
        for (int i = 0; i < R; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int l = Integer.parseInt(st.nextToken());
            a = items[a];
            b = items[b];
            paths[a][b] = l;
            paths[b][a] = l;
        }

        maxItems = 0;
        for (int i = 1; i <= N; i++) {
            sum = items[i];
            dfs(items[i], M);
        }

        System.out.println(maxItems);
    }

    static void dfs(int node, int remainingDistance) {
        visited[node] = true;

        maxItems = Math.max(maxItems, sum);

        for (int next = 1; next <= N; next++) {
            int nextNode = items[next];
            if (!visited[nextNode] && paths[node][nextNode] > 0 && remainingDistance >= paths[node][nextNode]) {
                sum += nextNode;
                dfs(nextNode, remainingDistance - paths[node][nextNode]);
            }
        }

        visited[node] = false; // 백트래킹을 위해 방문 상태를 되돌림
    }
}