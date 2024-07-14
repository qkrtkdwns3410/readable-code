package cleancode.minesweeper.tobe.cell;

/**
 * packageName    : cleancode.minesweeper.tobe.cell
 * fileName       : NumberCell
 * author         : ipeac
 * date           : 24. 7. 12.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 24. 7. 12.        ipeac       최초 생성
 */
public class EmptyCell extends Cell {
    
    private static final String EMPTY_SIGN = "■";
    
    @Override
    public boolean isLandMine() {
        return false;
    }
    
    @Override
    public boolean hasLandMineCount() {
        return true;
    }
    
    @Override
    public String getSign() {
        if (isOpened) {
            return EMPTY_SIGN;
        }
        
        if (isFlaged) {
            return FLAG_SIGN;
        }
        
        return UNCHECKED_SIGN;
    }
}