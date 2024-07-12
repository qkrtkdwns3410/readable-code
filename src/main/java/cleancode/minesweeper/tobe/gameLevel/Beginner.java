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
public class Beginner implements GameLevel {
    
    @Override
    public int getRowSize() {
        return 8;
    }
    
    @Override
    public int getColSize() {
        return 10;
    }
    
    @Override
    public int getLandMineCount() {
        return 10;
    }
}