package cleancode.minesweeper.tobe;

import cleancode.minesweeper.tobe.io.ConsoleInputHandler;
import cleancode.minesweeper.tobe.io.ConsoleOutputHandler;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

/**
 * packageName    : cleancode.minesweeper.tobe
 * fileName       : Minesweeper
 * author         : ipeac
 * date           : 24. 7. 9.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 24. 7. 9.        ipeac       최초 생성
 */
public class Minesweeper {
    public static final int BOARD_ROW_SIZE = 8;
    public static final int BOARD_COL_SIZE = 10;
    public static final Scanner SCANNER = new Scanner(System.in);
    private static final Cell[][] BOARD = new Cell[BOARD_ROW_SIZE][BOARD_COL_SIZE];
    public static final int LAND_MINE_COUNT = 10;
    
    private final ConsoleInputHandler consoleInputHandler = new ConsoleInputHandler();
    private final ConsoleOutputHandler consoleOutputHandler = new ConsoleOutputHandler();
    private static int gameStatus = 0; // 0: 게임 중, 1: 승리, -1: 패배
    
    public void run() {
        consoleOutputHandler.showGameStartComment();
        initializeGame();
        
        while (true) {
            try {
                consoleOutputHandler.showBoard(BOARD);
                
                if (doesUserWinTheGame()) {
                    consoleOutputHandler.gameWinningComment();
                    break;
                }
                
                if (gameStatus == -1) {
                    consoleOutputHandler.gameLosingComment();
                    break;
                }
                
                String cellInput = getCellInputFromUser();
                String userActionInput = getUserActionInputFromUser();
                
                actOnCell(cellInput, userActionInput);
            } catch (AppException e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                System.out.println("예상치 못한 오류가 발생했습니다.");
            }
        }
        
    }
    
    private void actOnCell(String cellInput, String userActionInput) {
        int selectedColIndex = getSelectedColIndex(cellInput);
        int selectedRowIndex = getSelectedRowIndex(cellInput);
        
        if (doesUserChooseToPlantFlag(userActionInput)) {
            BOARD[selectedRowIndex][selectedColIndex].flag();
            checkIfGameIsOver();
            return;
        }
        
        if (doesUserChooseToOpenCell(userActionInput)) {
            if (isLandMineCell(selectedRowIndex, selectedColIndex)) {
                BOARD[selectedRowIndex][selectedColIndex].open();
                changeGameStatusToLose();
                return;
            }
            
            open(selectedRowIndex, selectedColIndex);
            checkIfGameIsOver();
            return;
        }
        
        throw new AppException("잘못된 입력입니다. 다시 입력해주세요.");
    }
    
    private void changeGameStatusToLose() {
        gameStatus = -1;
    }
    
    private boolean isLandMineCell(int selectedRowIndex, int selectedColIndex) {
        return BOARD[selectedRowIndex][selectedColIndex].isLandMine();
    }
    
    private boolean doesUserChooseToOpenCell(String userActionInput) {
        return userActionInput.equals("1");
    }
    
    private boolean doesUserChooseToPlantFlag(String userActionInput) {
        return userActionInput.equals("2");
    }
    
    private int getSelectedRowIndex(String cellInput) {
        char cellInputRow = cellInput.charAt(1);
        return convertRowFrom(cellInputRow);
    }
    
    private int getSelectedColIndex(String cellInput) {
        char cellInputCol = cellInput.charAt(0);
        return convertColFrom(cellInputCol);
    }
    
    private String getUserActionInputFromUser() {
        System.out.println("선택한 셀에 대한 행위를 선택하세요. (1: 오픈, 2: 깃발 꽂기)");
        
        //input2 -> actionInput 으로 변수명 명확화
        return SCANNER.nextLine();
    }
    
    private String getCellInputFromUser() {
        System.out.println();
        System.out.println("선택할 좌표를 입력하세요. (예: a1)");
        
        //input -> cellInput 으로 변수명 명확화
        return SCANNER.nextLine();
    }
    
    private boolean doesUserWinTheGame() {
        return gameStatus == 1;
    }
    
    private void checkIfGameIsOver() {
        boolean isAllCellChecked = isAllCellChecked();
        
        if (isAllCellChecked) {
            changeGameStatusToWin();
        }
    }
    
    private void changeGameStatusToWin() {
        gameStatus = 1;
    }
    
    private boolean isAllCellChecked() {
        return Arrays.stream(BOARD) // Stream<String[]> (2차원 배열을 Stream으로 변환)
                .flatMap(Arrays::stream) // Stream<String> (2차원 배열을 1차원 배열로 변환)
                .allMatch(Cell::isChecked); // 모든 셀이 CLOSED_CELL_SIGN인지 확인
    }
    
    private int convertRowFrom(char cellInputRow) {
        int rowIndex = Character.getNumericValue(cellInputRow) - 1;
        if (rowIndex >= BOARD_ROW_SIZE) {
            throw new AppException("잘못된 행을 선택하셨습니다.");
        }
        
        return rowIndex;
    }
    
    private int convertColFrom(char cellInputCol) {
        return switch (cellInputCol) {
            case 'a' -> 0;
            case 'b' -> 1;
            case 'c' -> 2;
            case 'd' -> 3;
            case 'e' -> 4;
            case 'f' -> 5;
            case 'g' -> 6;
            case 'h' -> 7;
            case 'i' -> 8;
            case 'j' -> 9;
            default -> throw new AppException("Unexpected value: " + cellInputCol);
        };
    }
    
    private void showBoard(Cell[][] board) {
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
    
    private void initializeGame() {
        for (int row = 0; row < BOARD_ROW_SIZE; row++) {
            for (int col = 0; col < BOARD_COL_SIZE; col++) {
                BOARD[row][col] = Cell.create();
            }
        }
        
        for (int i = 0; i < LAND_MINE_COUNT; i++) {
            int col = new Random().nextInt(BOARD_COL_SIZE);
            int row = new Random().nextInt(BOARD_ROW_SIZE);
            BOARD[row][col].turnOnLandMine();
        }
        
        // i , j  row col  가독성 증대
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 10; col++) {
                if (isLandMineCell(row, col)) {
                    continue;
                }
                
                int count = countNearbyLandMines(row, col);
                BOARD[row][col].updateNearbyLandMineCount(count);
                
            }
        }
    }
    
    private int countNearbyLandMines(int row, int col) {
        int count = 0;
        
        if (row - 1 >= 0 && col - 1 >= 0 && isLandMineCell(row - 1, col - 1)) {
            count++;
        }
        if (row - 1 >= 0 && isLandMineCell(row - 1, col)) {
            count++;
        }
        if (row - 1 >= 0 && col + 1 < 10 && isLandMineCell(row - 1, col + 1)) {
            count++;
        }
        if (col - 1 >= 0 && isLandMineCell(row, col - 1)) {
            count++;
        }
        if (col + 1 < 10 && isLandMineCell(row, col + 1)) {
            count++;
        }
        if (row + 1 < 8 && col - 1 >= 0 && isLandMineCell(row + 1, col - 1)) {
            count++;
        }
        if (row + 1 < 8 && isLandMineCell(row + 1, col)) {
            count++;
        }
        if (row + 1 < 8 && col + 1 < 10 && isLandMineCell(row + 1, col + 1)) {
            count++;
        }
        return count;
    }
    
    private void showGameStartComment() {
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        System.out.println("지뢰찾기 게임 시작!");
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
    }
    
    private void open(int row, int col) {
        if (row < 0 || row >= 8 || col < 0 || col >= 10) {
            return;
        }
        
        if (BOARD[row][col].isOpened()) {
            return;
        }
        
        if (isLandMineCell(row, col)) {
            return;
        }
        
        BOARD[row][col].open();
        
        if (BOARD[row][col].hasLandMineCount()) {
            return;
        }
        
        open(row - 1, col - 1);
        open(row - 1, col);
        open(row - 1, col + 1);
        open(row, col - 1);
        open(row, col + 1);
        open(row + 1, col - 1);
        open(row + 1, col);
        open(row + 1, col + 1);
    }
    
}