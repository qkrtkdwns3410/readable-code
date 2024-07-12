package cleancode.minesweeper.tobe.cell;

/**
 * packageName    : cleancode.minesweeper.tobe
 * fileName       : Cell
 * author         : ipeac
 * date           : 24. 7. 8.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 24. 7. 8.        ipeac       최초 생성
 */
public abstract class Cell2 {
    protected static final String FLAG_SIGN = "⚑";
    protected static final String UNCHECKED_SIGN = "□";
    private static final String EMPTY_SIGN = "■";
    
    private int nearbyLandMineCount;
    private boolean isLandMine;
    protected boolean isFlaged;
    protected boolean isOpened;
    
    public abstract void turnOnLandMine();
    
    public abstract void updateNearbyLandMineCount(int count);
    
    public boolean isChecked() {
        return isFlaged || isOpened;
    }
    
    public void flag() {
        this.isFlaged = true;
    }
    
    public abstract boolean isLandMine();
    
    public void open() {
        this.isOpened = true;
    }
    
    public boolean isOpened() {
        return isOpened;
    }
    
    public abstract boolean hasLandMineCount();
    
    public abstract String getSign();
}