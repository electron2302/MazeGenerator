public class Cell {
    private boolean[] sides = {true,true,true,true};
    private boolean visited = false;
    private int[] coordinates = new int[2];

    public Cell(int height, int width) {
        this.coordinates[0] = height;
        this.coordinates[1] = width;
    }

    public int getHeight(){
        return coordinates[0];
    }

    public int getWidth(){
        return coordinates[1];
    }

    public boolean hasWallNorth(){
        return sides[0];
    }

    public boolean hasWallEast(){
        return sides[1];
    }

    public boolean hasWallSouth(){
        return sides[2];
    }

    public boolean hasWallWest(){
        return sides[3];
    }

    public boolean isVisited() {
        return visited;
    }

    public void setWallNorth(boolean isWall){
        sides[0] = isWall;
    }

    public void setWallEast(boolean isWall){
        sides[1] = isWall;
    }

    public void setWallSouth(boolean isWall){
        sides[2] = isWall;
    }

    public void setWallWest(boolean isWall){
        sides[3] = isWall;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }
}
