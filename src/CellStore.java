import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public class CellStore {
    private Cell[] store;

    public CellStore(Cell... store) {
        this.store = store;
    }

    public CellStore() {
        this.store = new Cell[0];
    }

    public void push(Cell cell){
        Cell[] result = Arrays.copyOf(this.store,this.store.length+1);
        result[result.length-1] = cell;
        this.store = result;
    }

    public Cell pop(){
        if (isEmpty())
            throw new IllegalStateException("cant pop from empty Store");

        Cell[] newArray = Arrays.copyOf(this.store,this.store.length-1);
        Cell result = this.store[this.store.length-1];
        this.store = newArray;
        return result;
    }

    public boolean isEmpty(){
        return store.length < 1;
    }

    /**
     * gives a random entity from the store without removing it
     *
     * @return random cell from store or null if store isEmpty
     */
    public Cell getRandomCell(){
        if (isEmpty())
            return null;
        else
            return store[ThreadLocalRandom.current().nextInt(0, store.length)];
    }
}
