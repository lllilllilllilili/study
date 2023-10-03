public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int xA = sc.nextInt();
        int yA = sc.nextInt();
        int xB = sc.nextInt();
        int yB = sc.nextInt();
        int xC = sc.nextInt();
        int yC = sc.nextInt();

        // 세 점이 한 직선 위에 있는지 확인
        if ((xB - xA) * (yC - yA) == (yB - yA) * (xC - xA)) {
        System.out.println(-1.0);
        return;
        }

        // 가능한 모든 D 점 찾기
        int[] D1 = {xA + xB - xC, yA + yB - yC};
        int[] D2 = {xB + xC - xA, yB + yC - yA};
        int[] D3 = {xC + xB - xA, yC + yB - yA};

        // 각 D 점에 대해 둘레 계산
        double p1 = distance(xA, yA, xB, yB) + distance(xA, yA, D1[0], D1[1]);
        double p2 = distance(xB, yB, xC, yC) + distance(xB, yB, D2[0], D2[1]);
        double p3 = distance(xC, yC, xA, yA) + distance(xC, yC, D3[0], D3[1]);

        // 둘레는 2배하여 계산
        p1 *= 2;
        p2 *= 2;
        p3 *= 2;

        // 가장 큰 둘레와 가장 작은 둘레의 차이 출력
        System.out.println(Math.abs(Math.max(Math.max(p1, p2), p3) - Math.min(Math.min(p1, p2), p3)));
        }

// 거리 계산 메소드
private static double distance(int x1, int y1, int x2, int y2) {
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
        }