package cleancode.minesweeper.tobe;

/**
 * packageName    : cleancode.minesweeper.tobe
 * fileName       : BoardIndexConverter
 * author         : ipeac
 * date           : 24. 7. 11.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 24. 7. 11.        ipeac       최초 생성
 */
public class BoardIndexConverter {
    public static final char BASE_CHAR_FOR_COL = 'a';
    
    public int getSelectedRowIndex(String cellInput, int rowMaxSize) {
        String cellInputRow = cellInput.substring(1);
        return convertRowFrom(cellInputRow, rowMaxSize);
    }
    
    public int getSelectedColIndex(String cellInput, int colMaxSize) {
        char cellInputCol = cellInput.charAt(0);
        return convertColFrom(cellInputCol, colMaxSize);
    }
    
    
    private int convertRowFrom(String cellInputRow, int rowMaxSize) {
        int rowIndex = Integer.parseInt(cellInputRow) - 1;
        if (rowIndex < 0 || rowIndex > rowMaxSize) {
            throw new AppException("잘못된 행을 선택하셨습니다.");
        }
        
        return rowIndex;
    }
    
    private int convertColFrom(char cellInputCol, int colMaxSize) {
        int colIndex = cellInputCol - BASE_CHAR_FOR_COL;
        if (colIndex < 0 || colIndex > colMaxSize) {
            throw new AppException("잘못된 열을 선택하셨습니다.");
        }
        
        return colIndex;
    }
}