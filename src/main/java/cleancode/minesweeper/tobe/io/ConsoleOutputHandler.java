package cleancode.minesweeper.tobe.io;

import cleancode.minesweeper.tobe.AppException;
import cleancode.minesweeper.tobe.GameBoard;

import java.util.List;
import java.util.stream.IntStream;

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
public class ConsoleOutputHandler implements OutputHandler {
    
    public static final char BASE_CHAR_FOR_COL = 'a';
    
    public void showGameStartComment() {
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        System.out.println("지뢰찾기 게임 시작!");
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
    }
    
    public void showBoard(GameBoard board) {
        String joiningAlphabets = generateColAlphabets(board);
        
        System.out.println("    " + joiningAlphabets);
        
        for (int row = 0; row < board.getRowSize(); row++) {
            System.out.printf("%2d  ", row + 1);
            
            for (int col = 0; col < board.getColSize(); col++) {
                System.out.print(board.getSign(row, col) + " ");
            }
            
            System.out.println();
        }
        System.out.println();
    }
    
    private static String generateColAlphabets(GameBoard board) {
        List<String> alphabets = IntStream.range(0, board.getColSize())
                .mapToObj(index -> (char) (index + BASE_CHAR_FOR_COL))
                .map(String::valueOf)
                .toList();
        
        return String.join(" ", alphabets);
    }
    
    public void gameLosingComment() {
        System.out.println("지뢰를 밟았습니다. GAME OVER!");
    }
    
    public void gameWinningComment() {
        System.out.println("지뢰를 모두 찾았습니다. GAME CLEAR!");
    }
    
    public void showCommentForSelectingCell() {
        System.out.println();
        System.out.println("선택할 좌표를 입력하세요. (예: a1)");
    }
    
    public void showCommentForUserAction() {
        System.out.println();
        System.out.println("1. 열기 2. 깃발 꽂기/뺴기");
        System.out.println("원하는 동작을 선택하세요. (예: 1)");
    }
    
    public void showExceptionMessage(AppException e) {
        System.out.println(e.getMessage());
    }
    
    public void showSimpleMessage() {
        System.out.println("예상치 못한 오류가 발생했습니다.");
    }
}