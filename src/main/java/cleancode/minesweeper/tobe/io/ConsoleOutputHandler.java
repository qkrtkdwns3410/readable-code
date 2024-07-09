package cleancode.minesweeper.tobe.io;

import cleancode.minesweeper.tobe.Cell;

/**
 * packageName    : cleancode.minesweeper.tobe.io
 * fileName       : ConsoleInputHandler
 * author         : ipeac
 * date           : 24. 7. 9.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 24. 7. 9.        ipeac       최초 생성
 */
public class ConsoleOutputHandler {
    
    public void showGameStartComment() {
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        System.out.println("지뢰찾기 게임 시작!");
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
    }
    
    public void showBoard(Cell[][] board) {
        System.out.println("   a b c d e f g h i j");
        for (int i = 0; i < board.length; i++) {
            System.out.printf("%d  ", i + 1);
            
            for (int j = 0; j < board[0].length; j++) {
                System.out.print(board[i][j].getSign() + " ");
            }
            
            System.out.println();
        }
        System.out.println();
    }
    
    public void gameLosingComment() {
        System.out.println("지뢰를 밟았습니다. GAME OVER!");
    }
    
    public void gameWinningComment() {
        System.out.println("지뢰를 모두 찾았습니다. GAME CLEAR!");
    }
}