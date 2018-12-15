import java.util.ArrayList;
import java.util.Stack;
import java.util.concurrent.ThreadLocalRandom;

public class Maze {
    private final int mazeWidth;
    private final int mazeHeight;
    private Cell[][] cells;
    private boolean generated;

    public Maze(int mazeWidth, int mazeHeight) {
        if(mazeHeight < 2 || mazeWidth < 2)
            throw new IllegalArgumentException("maze needs min dimensions of 2x2");

        this.mazeWidth = mazeWidth;
        this.mazeHeight = mazeHeight;
        this.generated = false;

        this.cells = new Cell[mazeHeight][mazeWidth];

        for (int i = 0; i < getMazeHeight(); i++){
            for(int j = 0; j < getMazeWidth(); j++){
                setCell(i,j,new Cell(i,j));
            }
        }
    }

    public void generat(){
        if (generated)
            throw new IllegalStateException("already generated");
        generated = true;

        Stack<Cell> stack = new Stack<>();

        Cell curentCell = getCell(0,0);
        curentCell.setVisited(true);

        while (hasUnvisitedCells()){
            Cell unvisitedNeighbour = getUnvisitedNeighbour(curentCell);
            if(unvisitedNeighbour != null){
                stack.push(curentCell);
                removeWallBetweenCells(curentCell,unvisitedNeighbour);
                curentCell = unvisitedNeighbour;
                curentCell.setVisited(true);
            }
            else if(!stack.isEmpty()){
                curentCell = stack.pop();
            }
            else {
                throw new IllegalStateException("fuck");
            }
        }

    }

    private void removeWallBetweenCells(Cell cellOne, Cell cellTwo){
        if(cellOne.getHeight() == cellTwo.getHeight() && cellOne.getWidth() == cellTwo.getWidth()+1){
            //nebeneinander, cellTwo ist links
            cellOne.setWallWest(false);
            cellTwo.setWallEast(false);
        }
        else if(cellOne.getHeight() == cellTwo.getHeight() && cellOne.getWidth() == cellTwo.getWidth()-1){
            //nebeneinander, cellTwo ist rechts
            cellOne.setWallEast(false);
            cellTwo.setWallWest(false);
        }
        else if(cellOne.getWidth() == cellTwo.getWidth() && cellOne.getHeight() == cellTwo.getHeight()+1){
            //uebereinander, cellTwo ist drueber
            cellOne.setWallNorth(false);
            cellTwo.setWallSouth(false);
        }
        else if(cellOne.getWidth() == cellTwo.getWidth() && cellOne.getHeight() == cellTwo.getHeight()-1){
            //uebereinander, cellTwo ist drunter
            cellOne.setWallSouth(false);
            cellTwo.setWallNorth(false);
        }
        else{
            throw new IllegalStateException("fuck");
        }

    }

    private Cell getUnvisitedNeighbour(Cell cell){
        ArrayList<Cell> neighbours = new ArrayList<Cell>();

        int height = cell.getHeight();
        int width = cell.getWidth();

        if(height-1 >= 0 && !getCell(height-1,width).isVisited())
            neighbours.add(getCell(height-1,width));

        if(height+1 < getMazeHeight() && !getCell(height+1,width).isVisited())
            neighbours.add(getCell(height+1,width));

        if(width-1 >= 0 && !getCell(height,width-1).isVisited())
            neighbours.add(getCell(height,width-1));

        if(width+1 < getMazeWidth() && !getCell(height,width+1).isVisited())
            neighbours.add(getCell(height,width+1));

        if(neighbours.isEmpty())
            return null;
        else
            return neighbours.get(ThreadLocalRandom.current().nextInt(0, neighbours.size()));
    }

    private boolean hasUnvisitedCells(){
        boolean result = false;
        for (int i = 0; i < getMazeHeight(); i++){
            for(int j = 0; j < getMazeWidth(); j++){
                if(!getCell(i,j).isVisited())
                    result = true;
            }
        }
        return result;
    }

    public String toString(){
        String result = "";
        //final String cellChar = "\u2591\u2591";
        final String cellChar = "  ";
        final String wallChar = "\u2588\u2588";
        final String freeChar = "  ";

        for (int i = 0; i < getMazeHeight(); i++){

            if(i == 0){
                for(int j = 0; j < getMazeWidth(); j++){
                    if(j==0){
                        result += wallChar;
                    }
                    result += getCell(i,j).hasWallNorth()? wallChar: freeChar;
                    result += wallChar;
                }
                result += "\n";
            }


            for(int j = 0; j < getMazeWidth(); j++){
                if(j==0){
                    result += getCell(i,j).hasWallWest()? wallChar: freeChar;
                }
                result += cellChar;
                result += getCell(i,j).hasWallEast()? wallChar: freeChar;
            }
            result += "\n";

            for(int j = 0; j < getMazeWidth(); j++){
                if(j==0){
                    result += wallChar;
                }
                result += getCell(i,j).hasWallSouth()? wallChar: freeChar;
                result += wallChar;
            }
            result += "\n";
        }

        return result;
    }


    private int getMazeWidth() {
        return mazeWidth;
    }

    private int getMazeHeight() {
        return mazeHeight;
    }

    private Cell getCell(int height, int width) {
        return cells[height][width];
    }

    private void setCell(int height, int width,Cell newCell) {
        cells[height][width] = newCell;
    }
}
