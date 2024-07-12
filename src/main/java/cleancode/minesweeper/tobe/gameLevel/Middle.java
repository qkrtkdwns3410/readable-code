package cleancode.minesweeper.tobe.gameLevel;

/**
 * packageName    : cleancode.minesweeper.tobe.gameLevel
 * fileName       : VeryBeginner
 * author         : ipeac
 * date           : 24. 7. 12.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 24. 7. 12.        ipeac       최초 생성
 */
public class Middle implements GameLevel {
    
    @Override
    public int getRowSize() {
        return 14;
    }
    
    @Override
    public int getColSize() {
        return 18;
    }
    
    @Override
    public int getLandMineCount() {
        return 40;
    }
    
}