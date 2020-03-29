package com.example.maze;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Repository
class MazeRepositoryImpl implements MazeRepository
{
    Collection<Maze> mazes = new ArrayList<>();
    
    @Override
    public Collection<Maze> findAll()
    {
        return mazes;
    }
    
    @Override
    public void save(Maze maze)
    {
        mazes.add(maze);
    }
    
    @Override
    public Page<Maze> findPaginated(int page, int size)
    {
        return new PageImpl<>(getPage(new ArrayList<>(this.mazes), page, size));
    }
    
    private static <T> List<T> getPage(List<T> sourceList, int page, int size)
    {
        if(size <= 0 || page <= 0)
        {
            throw new IllegalArgumentException("invalid page size: " + size);
        }
        
        int fromIndex = (page - 1) * size;
        if(sourceList == null || sourceList.size() < fromIndex)
        {
            return Collections.emptyList();
        }
        return sourceList.subList(fromIndex, Math.min(fromIndex + size, sourceList.size()));
    }
}
