package CutVertex;
import java.util.*;
import java.io.*;

class CutVertex {
    static int[][][] Edge;
    static int[] Mark, Pre, L;
    static int P, n, m;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        StringBuilder sb = new StringBuilder();
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        Edge = new int[n + 1][n + 1][2];
        Mark = new int[n + 1];
        Pre = new int[n + 1];
        L = new int[n + 1];

        int x, y;
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            x = Integer.parseInt(st.nextToken());
            y = Integer.parseInt(st.nextToken());
            Edge[x][y][0] = 1;
            Edge[y][x][0] = 1;
        }
        P = 1;
        dfs(1, 0);
        dfs2(1);
        if (isRootCut()) sb.append(1 + " ");
        for (int i = 2; i <= n; i++) {
            if (isCut(i)) sb.append(i + " ");
        }
        System.out.println(sb);
    }

    private static void dfs(int x, int p) {
        Mark[x] = 1;
        Pre[x] = P++;
        for (int i = 1; i <= n; i++) {
            if (i == p) {
                // Parent
                Edge[x][i][1] = 1;
            } else if (Edge[x][i][0] == 1) {
                if (Mark[i] == 0) {
                    // Tree
                    Edge[x][i][1] = 2;
                    dfs(i, x);
                }
                // Back
                else Edge[x][i][1] = 3;
            }
        }
    }

    private static void dfs2(int x) {
        int myL = Pre[x];
        for (int i = 1; i <= n; i++) {
            if (Edge[x][i][1] == 2) {
                dfs2(i);
                myL = Math.min(myL, L[i]);
            }
            else if (Edge[x][i][1] == 3) {
                myL = Math.min(myL, Pre[i]);
            }
        }
        L[x] = myL;
    }

    private static boolean isCut(int x) {
        for (int i = 1; i <= n; i++) {
            if (Edge[x][i][1] == 2) {
                if (L[i] >= Pre[x])
                    return true;
            }
        }
        return false;
    }


    private static boolean isRootCut() {
        int count = 0;
        for (int i = 1; i <= n; i++) {
            if (Edge[1][i][1] == 2) {
                count++;
            }
        }
        return count > 1 ? true : false;
    }
}
/*
5 4
3 2
2 1
1 4
4 5

5 5
3 2
2 1
1 4
4 5
1 3

16 20
1 2
1 3
2 4
3 4
4 5
5 6
4 6
5 7
7 8
4 12
12 13
4 11
11 13
11 10
10 9
9 11
13 14
14 15
15 16
14 16
 */