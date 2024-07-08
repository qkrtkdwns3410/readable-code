package cleancode.minesweeper.tobe;

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
public class Cell {
    public static final String FLAG_SIGN = "⚑";
    public static final String LAND_MINE_SIGN = "☼";
    public static final String UNCHECKED_SIGN = "□";
    public static final String EMPTY_SIGN = "■";
    
    private int nearbyLandMineCount;
    private boolean isLandMine;
    private boolean isFlaged;
    private boolean isOpened;
    
    private Cell(int nearbyLandMineCount, boolean isLandMine, boolean isFlaged, boolean isOpened) {
        this.nearbyLandMineCount = nearbyLandMineCount;
        this.isLandMine = isLandMine;
        this.isFlaged = isFlaged;
        this.isOpened = isOpened;
    }
    
    public static Cell of(int nearbyLandMineCount, boolean isLandMine, boolean isFlaged, boolean isOpened) {
        return new Cell(nearbyLandMineCount, isLandMine, isFlaged, isOpened);
    }
    
    public static Cell create() {
        return of(0, false, false, false);
    }
    
    public boolean isChecked() {
        return isFlaged || isOpened;
    }
    
    public void turnOnLandMine() {
        this.isLandMine = true;
    }
    
    public void updateNearbyLandMineCount(int count) {
        this.nearbyLandMineCount = count;
    }
    
    public void flag() {
        this.isFlaged = true;
    }
    
    public boolean isLandMine() {
        return isLandMine;
    }
    
    public void open() {
        this.isOpened = true;
    }
    
    public boolean isOpened() {
        return isOpened;
    }
    
    public boolean hasLandMineCount() {
        return nearbyLandMineCount != 0;
    }
    
    public String getSign() {
        if (isOpened) {
            if (isLandMine) {
                return LAND_MINE_SIGN;
            }
            
            if (hasLandMineCount()) {
                return String.valueOf(nearbyLandMineCount);
            }
            
            return EMPTY_SIGN;
        }
        
        if (isFlaged) {
            return FLAG_SIGN;
        }
        
        return UNCHECKED_SIGN;
    }
}