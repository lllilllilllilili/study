import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // 성의 크기 입력 받기
        int N = scanner.nextInt();
        int M = scanner.nextInt();
        scanner.nextLine(); // 줄 바꿈 처리

        // 성의 상태를 저장할 배열
        char[][] castle = new char[N][M];

        // 성의 상태 입력 받기
        for (int i = 0; i < N; i++) {
            String line = scanner.nextLine();
            for (int j = 0; j < M; j++) {
                castle[i][j] = line.charAt(j);
            }
        }

        // 각 행과 열에 경비원이 있는지 체크
        boolean[] rowHasGuard = new boolean[N];
        boolean[] colHasGuard = new boolean[M];

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (castle[i][j] == 'X') {
                    rowHasGuard[i] = true;
                    colHasGuard[j] = true;
                }
            }
        }

        // 경비원이 없는 행과 열의 수 세기
        int rowCount = 0;
        int colCount = 0;
        for (int i = 0; i < N; i++) {
            if (!rowHasGuard[i]) {
                rowCount++;
            }
        }
        for (int i = 0; i < M; i++) {
            if (!colHasGuard[i]) {
                colCount++;
            }
        }

        // 필요한 최소한의 경비원 수는 경비원이 없는 행과 열 중 더 많은 것
        int guardsNeeded = Math.max(rowCount, colCount);
        System.out.println(guardsNeeded);
    }
}