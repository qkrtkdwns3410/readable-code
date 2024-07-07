package cleancode.minesweeper.tobe;

/**
 * packageName    : cleancode.minesweeper.tobe
 * fileName       : AppException
 * author         : ipeac
 * date           : 24. 7. 7.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 24. 7. 7.        ipeac       최초 생성
 */
public class AppException extends RuntimeException {
    public AppException(String message) {
        super(message);
    }
}