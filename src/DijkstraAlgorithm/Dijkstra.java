package DijkstraAlgorithm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Dijkstra {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int V, E, S;
    static StringBuilder sb;
    static List<Edge>[] graph;

    public static void main(String[] args) throws IOException {
        StringTokenizer st = new StringTokenizer(br.readLine());
        V = Integer.parseInt(st.nextToken());
        E = Integer.parseInt(st.nextToken());
        S = Integer.parseInt(br.readLine());
        sb = new StringBuilder("\n");

        //Node 클래스List를 저장하는 배열 생성
        graph = new ArrayList[V];
        for (int i = 0; i < V; i++) graph[i] = new ArrayList<>();

        int u, v, w;
        for (int i = 0; i < E; i++) {
            st = new StringTokenizer(br.readLine());
            u = Integer.parseInt(st.nextToken());
            v = Integer.parseInt(st.nextToken());
            w = Integer.parseInt(st.nextToken());
            graph[u - 1].add(new Edge(v, w));
            graph[v - 1].add(new Edge(u, w));
        }

        dijkstra(S);

        System.out.print(sb);
    }

    static void dijkstra(int S) {
        //정점이 Red 지역에 포함되었는지 확인하는 배열
        boolean[] red = new boolean[V];

        //PriorityQueue에 시작 정점S 삽입
        PriorityQueue<Edge> queue = new PriorityQueue<>();
        Edge start = new Edge(S, 0);
        start.d = 0;
        queue.add(start);

        int count = 0;
        while (count < V){
            //red 지역에 속하지 않은 vertices 중 최소거리를 갖는 노드
            Edge poll = queue.poll();
            if (red[poll.v - 1]) continue;

            //e에 연결되어있는 vertices의 최소거리 d갱신
            for (Edge e : graph[poll.v - 1]) {
                e.d = Math.min(e.d, poll.d + e.w);
                queue.add(e);
            }

            sb.append(poll.v).append(" ");
            red[poll.v - 1] = true;
            count++;
        }
    }
}


class Edge implements Comparable<Edge> {
    int v;
    int w;

    //Source로부터의 최소거리
    int d;

    public Edge(int v, int w) {
        this.v = v;
        this.w = w;
        d = Integer.MAX_VALUE;
    }

    //PrimAlgorithm과 달리 Source로부터의 거리 d를 기준으로 compareTo 메소드 Override
    @Override
    public int compareTo(Edge o) {
        return d - o.d;
    }
}
/*
4 5
1
1 2 3
2 3 2
3 4 4
4 1 1
2 4 5

5 10
1
1 2 10
1 4 5
1 5 6
2 3 1
2 4 3
2 4 4
3 4 2
3 5 4
3 5 6
4 5 2
 */