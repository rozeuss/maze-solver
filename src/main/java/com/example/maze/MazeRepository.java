package com.example.maze;

import org.springframework.data.domain.Page;

import java.util.Collection;

public interface MazeRepository
{
    Collection<Maze> findAll();
    
    void save(Maze maze);
    
    Page<Maze> findPaginated(int page, int size);
}
