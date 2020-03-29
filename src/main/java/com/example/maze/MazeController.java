package com.example.maze;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.commons.io.FilenameUtils;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;
import java.util.Objects;

@RestController
@RequestMapping("mazes")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MazeController
{
    MazeService mazeService;
    
    @PostMapping
    public ResponseEntity<Solution> solve(@RequestParam("file") MultipartFile file)
    {
        if(!isValid(file))
        {
            return ResponseEntity.badRequest().build();
        }
        int[][] maze = mazeService.extractMaze(file);
        Solution solution = mazeService.solve(maze);
        return ResponseEntity.ok().body(solution);
    }
    
    @GetMapping
    public Collection<Maze> getMazes()
    {
        return mazeService.findAll();
    }
    
    @GetMapping("/pages")
    public Page<Maze> getMazes(@RequestParam("page") int page, @RequestParam("size") int size)
    {
        return mazeService.findPaginated(page, size);
    }
    
    private static boolean isValid(MultipartFile file)
    {
        return Objects.nonNull(file)
               && MediaType.TEXT_PLAIN_VALUE.equals(file.getContentType())
               && "txt".equals(FilenameUtils.getExtension(file.getOriginalFilename()));
    }
}
