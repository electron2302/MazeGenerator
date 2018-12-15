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

        CellStore store = new CellStore();

        Cell curentCell = getCell(0,0);
        curentCell.setVisited(true);

        while (hasUnvisitedCells()){
            Cell unvisitedNeighbour = getUnvisitedNeighbour(curentCell);
            if(unvisitedNeighbour != null){
                store.push(curentCell);
                removeWallBetweenCells(curentCell,unvisitedNeighbour);
                curentCell = unvisitedNeighbour;
                curentCell.setVisited(true);
            }
            else if(!store.isEmpty()){
                curentCell = store.pop();
            }
            else {
                throw new IllegalStateException();
            }
        }

    }

    private void removeWallBetweenCells(Cell cellOne, Cell cellTwo){
        if(cellOne.getHeight() == cellTwo.getHeight() && cellOne.getWidth() == cellTwo.getWidth()+1){
            //next to each other, cellTwo is on the left
            cellOne.setWallWest(false);
            cellTwo.setWallEast(false);
        }
        else if(cellOne.getHeight() == cellTwo.getHeight() && cellOne.getWidth() == cellTwo.getWidth()-1){
            //next to each other, cellTwo is on the right
            cellOne.setWallEast(false);
            cellTwo.setWallWest(false);
        }
        else if(cellOne.getWidth() == cellTwo.getWidth() && cellOne.getHeight() == cellTwo.getHeight()+1){
            //over each other, cellTwo is above
            cellOne.setWallNorth(false);
            cellTwo.setWallSouth(false);
        }
        else if(cellOne.getWidth() == cellTwo.getWidth() && cellOne.getHeight() == cellTwo.getHeight()-1){
            //over each other, cellTwo is below
            cellOne.setWallSouth(false);
            cellTwo.setWallNorth(false);
        }
        else{
            throw new IllegalStateException("something went wrong");
        }

    }

    private Cell getUnvisitedNeighbour(Cell cell){
        CellStore neighbours = new CellStore();

        int height = cell.getHeight();
        int width = cell.getWidth();

        if(height-1 >= 0 && !getCell(height-1,width).isVisited())
            neighbours.push(getCell(height-1,width));

        if(height+1 < getMazeHeight() && !getCell(height+1,width).isVisited())
            neighbours.push(getCell(height+1,width));

        if(width-1 >= 0 && !getCell(height,width-1).isVisited())
            neighbours.push(getCell(height,width-1));

        if(width+1 < getMazeWidth() && !getCell(height,width+1).isVisited())
            neighbours.push(getCell(height,width+1));

        return neighbours.getRandomCell();
    }

    private boolean hasUnvisitedCells(){
        for (int i = 0; i < getMazeHeight(); i++){
            for(int j = 0; j < getMazeWidth(); j++){
                if(!getCell(i,j).isVisited())
                    return true;
            }
        }
        return false;
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
