# Maze

Maze solver to find the path with least turns.

Input file consists of:
- comma separated matrix sizes (x, y) in first line 
- x binary digits (0 - wall, 1 - path) in the following y lines representing the maze
- entrance at (0, 1), exit at (x - 1, y - 2)

```
9,8
000000000
111101110
010101010
010111010
010000010
011110010
010011111
000000000
```

Output is integer, e.g. for example above `4`.

Upload a maze:
```
curl --location --request POST 'http://localhost:8080/mazes' --form 'file=@/C:/Users/X/Desktop/input.txt'
```
Return all solved mazes:
```
curl --location --request GET 'http://localhost:8080/mazes'
```
Return paginated mazes:
```
curl --location --request GET 'http://localhost:8080/mazes/pages?page=1&size=10'
```
