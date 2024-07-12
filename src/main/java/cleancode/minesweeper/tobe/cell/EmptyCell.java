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
public class EmptyCell extends Cell2 {
    
    @Override
    public void turnOnLandMine() {
        throw new UnsupportedOperationException("지원하지 않는 기능입니다.");
    }
    
    @Override
    public void updateNearbyLandMineCount(int count) {
        throw new UnsupportedOperationException("지원하지 않는 기능입니다.");
    }
    
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
            return LAND_MINE_SIGN;
        }
        
        if (isFlaged) {
            return FLAG_SIGN;
        }
        
        return UNCHECKED_SIGN;
    }
}