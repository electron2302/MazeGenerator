public class RunMaze {
    public static void main(String[] args) {
        final Maze maze = new Maze(10,10);
        maze.generat();
        System.out.println(maze.toString());
    }
}
