package com.example.maze;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;

public interface MazeService
{
    Solution solve(int[][] maze);
    
    Collection<Maze> findAll();
    
    Page<Maze> findPaginated(int page, int size);
    
    int[][] extractMaze(MultipartFile file);
}
