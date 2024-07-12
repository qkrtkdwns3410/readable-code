package cleancode.minesweeper.tobe;

import cleancode.minesweeper.tobe.gameLevel.Beginner;
import cleancode.minesweeper.tobe.gameLevel.GameLevel;

/**
 * packageName    : cleancode.minesweeper.tobe
 * fileName       : GameApplication
 * author         : ipeac
 * date           : 24. 7. 9.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 24. 7. 9.        ipeac       최초 생성
 */

public class GameApplication {
    public static void main(String[] args) {
        GameLevel gameLevel = new Beginner();
        
        Minesweeper minesweeper = new Minesweeper(gameLevel);
        minesweeper.run();
    }
}