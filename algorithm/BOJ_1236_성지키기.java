import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // ���� ũ�� �Է� �ޱ�
        int N = scanner.nextInt();
        int M = scanner.nextInt();
        scanner.nextLine(); // �� �ٲ� ó��

        // ���� ���¸� ������ �迭
        char[][] castle = new char[N][M];

        // ���� ���� �Է� �ޱ�
        for (int i = 0; i < N; i++) {
            String line = scanner.nextLine();
            for (int j = 0; j < M; j++) {
                castle[i][j] = line.charAt(j);
            }
        }

        // �� ��� ���� ������ �ִ��� üũ
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

        // ������ ���� ��� ���� �� ����
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

        // �ʿ��� �ּ����� ���� ���� ������ ���� ��� �� �� �� ���� ��
        int guardsNeeded = Math.max(rowCount, colCount);
        System.out.println(guardsNeeded);
    }
}