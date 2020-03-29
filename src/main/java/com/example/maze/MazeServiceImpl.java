package com.example.maze;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
class MazeServiceImpl implements MazeService
{
    MazeRepository mazeRepository;
    
    @Override
    public Solution solve(int[][] maze)
    {
        Solution solution = new MazeSolver(maze).solve();
        mazeRepository.save(new Maze(maze, solution));
        return solution;
    }
    
    @Override
    public Collection<Maze> findAll()
    {
        return mazeRepository.findAll();
    }
    
    @Override
    public Page<Maze> findPaginated(int page, int size)
    {
        return mazeRepository.findPaginated(page, size);
    }
    
    @Override
    @SneakyThrows
    public int[][] extractMaze(MultipartFile file)
    {
        List<String> lines = IOUtils.readLines(file.getInputStream(), StandardCharsets.UTF_8);
        if(!validateMaze(lines))
        {
            throw new IllegalStateException("Provided input is invalid.");
        }
        var maze = lines.stream()
                        .map(v -> v.chars()
                                   .map(Character::getNumericValue)
                                   .toArray())
                        .toArray(int[][]::new);
        log.info("Accepted maze: {}", Arrays.deepToString(maze));
        return maze;
    }
    
    private static boolean validateMaze(List<String> lines)
    {
        var matrixSizes = Arrays.stream(lines.remove(0).split(",")).mapToInt(Integer::valueOf).toArray();
        boolean sizeValid = Arrays.stream(matrixSizes).allMatch(MazeServiceImpl::checkSizeRange);
        boolean entranceValid = lines.get(1).startsWith("1");
        boolean exitValid = lines.get(matrixSizes[1] - 2).endsWith("1");
        boolean binaryMatrix = lines.stream()
                                    .map(v -> v.chars()
                                               .map(Character::getNumericValue)
                                               .toArray())
                                    .flatMapToInt(Arrays::stream)
                                    .allMatch(value -> value == 0 || value == 1);
        return sizeValid && entranceValid && exitValid && binaryMatrix;
    }
    
    private static boolean checkSizeRange(Integer v)
    {
        return 7 <= v && v <= 10000;
    }
}
