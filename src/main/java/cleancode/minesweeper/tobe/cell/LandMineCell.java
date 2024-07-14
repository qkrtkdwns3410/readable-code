package cleancode.minesweeper.tobe.cell;

/**
 * packageName    : cleancode.minesweeper.tobe.cell
 * fileName       : LandMineCell
 * author         : ipeac
 * date           : 24. 7. 12.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 24. 7. 12.        ipeac       최초 생성
 */
public class LandMineCell extends Cell {
    
    private static final String LAND_MINE_SIGN = "☼";
    
    @Override
    public boolean isLandMine() {
        return true;
    }
    
    @Override
    public boolean hasLandMineCount() {
        return false;
    }
    
    @Override
    public String getSign() {
        if (isOpened) {
            return LAND_MINE_SIGN;
        }
        
        if (isFlaged) {
            return FLAG_SIGN;
        }
        
        return UNCHECKED_SIGN;
    }
}