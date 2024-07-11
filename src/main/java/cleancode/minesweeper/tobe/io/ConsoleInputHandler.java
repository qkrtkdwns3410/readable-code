package cleancode.minesweeper.tobe.io;

import java.util.Scanner;

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
public class ConsoleInputHandler {
    public static final Scanner SCANNER = new Scanner(System.in);
    
    public String getUserInput() {
        return SCANNER.nextLine();
    }

}