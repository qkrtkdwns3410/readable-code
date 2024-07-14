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
public abstract class Cell {
    protected static final String FLAG_SIGN = "⚑";
    protected static final String UNCHECKED_SIGN = "□";
    
    protected boolean isFlaged;
    protected boolean isOpened;
    
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