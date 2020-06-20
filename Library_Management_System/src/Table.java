import java.time.LocalDateTime;
import java.util.Comparator;

/**
 * Table class which extends Inventory
 */
public class Table extends Inventory implements Comparable {

    /**
     * Holds time for a break
     */
    private int breakCounter;

    /**
     * Constructor
     */
    public Table(){
        super();
        breakCounter = 0;
    }

    /**
     * Constructor
     *
     * @param id     to be assigned
     * @param status to be assigned
     * @param time   to be assigned
     */
    protected Table(String id, String status, Integer time) {
        super(id, status, time);
        breakCounter = 0;
    }

    /**
     * Changes status of the table
     */
    @Override
    public void changeStatus(String newStatus){
        if(isAvailable())
            setStatus(newStatus);
        else
            System.out.println("Table has already occupied");
    }

    /**
     * Updates time of a the table for 60 minutes (1 hour)
     */
    @Override
    public void updateTime() {
        //setTime(getTime().plusMinutes(60));
    }


    /**
     * Setters and getters
     */
    public Integer getBreakCounter() {
        return breakCounter;
    }
    public void setBreakCounter(Integer breakCounter) {
        this.breakCounter = breakCounter;
    }


    @Override
    public int compareTo(Object o) {
        if(this.getTime() < ((Table)o).getTime())
            return -1;

        else if(this.getTime() == ((Table)o).getTime())
            return 0;

        else
            return 1;

    }
}
