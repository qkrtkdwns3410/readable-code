package cleancode.minesweeper.tobe.gameLevel;

/**
 * packageName    : cleancode.minesweeper.tobe.gameLevel
 * fileName       : GameLevel
 * author         : ipeac
 * date           : 24. 7. 12.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 24. 7. 12.        ipeac       최초 생성
 */
public interface GameLevel {
    int getRowSize();
    
    int getColSize();
    
    int getLandMineCount();
}