package cleancode.minesweeper.tobe.io;

import cleancode.minesweeper.tobe.AppException;
import cleancode.minesweeper.tobe.GameBoard;

/**
 * packageName    : cleancode.minesweeper.tobe.io
 * fileName       : OutputHandler
 * author         : ipeac
 * date           : 24. 7. 14.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 24. 7. 14.        ipeac       최초 생성
 */
public interface OutputHandler {
    void showGameStartComment();
    
    void showBoard(GameBoard board);
    
    void gameLosingComment();
    
    void gameWinningComment();
    
    void showCommentForSelectingCell();
    
    void showCommentForUserAction();
    
    void showExceptionMessage(AppException e);
    
    void showSimpleMessage();
}