package com.example.maze;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
final class MazeSolver
{
    Queue<Pair> queue;
    int[][] maze;
    int n;
    int m;
    int[][] path;
    boolean[][] visited;
    int startX;
    int startY;
    int exitX;
    int exitY;
    
    MazeSolver(int[][] maze)
    {
        queue = new LinkedList<>();
        this.maze = maze;
        n = maze.length;
        m = maze[0].length;
        path = new int[n][m];
        visited = new boolean[n][m];
        startX = 1;
        startY = 0;
        exitX = maze.length - 2;
        exitY = maze[0].length - 1;
    }
    
    Solution solve()
    {
        final int numberOfCorners = findStraightestPath();
        return new Solution(numberOfCorners);
    }
    
    private int findStraightestPath()
    {
        Pair pair = new Pair(startX, startY);
        queue.offer(pair);
        visited[startX][startY] = true;
        path[startX][startY] = -1;
        int i;
        while(!queue.isEmpty())
        {
            pair = queue.poll();
            if(pair.x == exitX && pair.y == exitY)
            {
                return path[pair.x][pair.y];
            }
            for(i = pair.y; i < m; i++)
            {
                if(isWall(pair.x, i))
                {
                    break;
                }
                if(!visited[pair.x][i])
                {
                    visit(pair, i, pair.x);
                }
            }
            for(i = pair.y; i >= 0; i--)
            {
                if(isWall(pair.x, i))
                {
                    break;
                }
                if(!visited[pair.x][i])
                {
                    visit(pair, i, pair.x);
                }
            }
            for(i = pair.x; i < n; i++)
            {
                if(isWall(i, pair.y))
                {
                    break;
                }
                if(!visited[i][pair.y])
                {
                    visit(pair, pair.y, i);
                }
            }
            for(i = pair.x; i >= 0; i--)
            {
                if(isWall(i, pair.y))
                {
                    break;
                }
                if(!visited[i][pair.y])
                {
                    visit(pair, pair.y, i);
                }
            }
        }
        return -1;
    }
    
    private void visit(Pair pair, int i, int x)
    {
        path[x][i] = path[pair.x][pair.y] + 1;
        visited[x][i] = true;
        queue.offer(new Pair(x, i));
    }
    
    private boolean isWall(int x, int y)
    {
        return maze[x][y] == 0;
    }
    
    @AllArgsConstructor
    private static class Pair implements Comparator<Pair>
    {
        final int x;
        final int y;
        
        @Override
        public int compare(Pair o1, Pair o2)
        {
            return (o1.x + o1.y - o2.x - o2.y);
        }
    }
}
