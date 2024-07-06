package cleancode.minesweeper.tobe;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class MinesweeperGame {
    
    public static final int BOARD_ROW_SIZE = 8;
    public static final int BOARD_COL_SIZE = 10;
    public static final Scanner SCANNER = new Scanner(System.in);
    private static final String[][] BOARD = new String[BOARD_ROW_SIZE][BOARD_COL_SIZE];
    private static final Integer[][] NEARBY_LAND_MINE_COUNTS = new Integer[BOARD_ROW_SIZE][BOARD_COL_SIZE];
    private static final boolean[][] LAND_MINES = new boolean[BOARD_ROW_SIZE][BOARD_COL_SIZE];
    public static final int LAND_MINE_COUNT = 10;
    public static final String FLAG_SIGN = "⚑";
    public static final String LAND_MINE_SIGN = "☼";
    public static final String CLOSED_CELL_SIGN = "□";
    public static final String OPENED_CELL_SIGN = "■";
    
    private static int gameStatus = 0; // 0: 게임 중, 1: 승리, -1: 패배

    public static void main(String[] args) {
        // 안내 문구 메서드 분리
        showGameStartComment();
        
        Scanner scanner = new Scanner(System.in);
        
        // 게임 초기화 메서드 분리
        initializeGame();
        
        while (true) {
            showBoard();
            
            if (doesUserWinTheGame()) {
                System.out.println("지뢰를 모두 찾았습니다. GAME CLEAR!");
                break;
            }
            
            if (gameStatus == -1) {
                System.out.println("지뢰를 밟았습니다. GAME OVER!");
                break;
            }
            
            String cellInput = getCellInputFromUser();
            String userActionInput = getUserActionInputFromUser();
            
            actOnCell(cellInput, userActionInput);
        }
    }
    
    private static void actOnCell(String cellInput, String userActionInput) {
        int selectedColIndex = getSelectedColIndex(cellInput);
        int selectedRowIndex = getSelectedRowIndex(cellInput);
        
        if (doesUserChooseToPlantFlag(userActionInput)) {
            BOARD[selectedRowIndex][selectedColIndex] = FLAG_SIGN;
            checkIfGameIsOver();
            return;
        }
        
        if (doesUserChooseToOpenCell(userActionInput)) {
            if (isLandMineCell(selectedRowIndex, selectedColIndex)) {
                BOARD[selectedRowIndex][selectedColIndex] = LAND_MINE_SIGN;
                changeGameStatusToLose();
                return;
            }
            
            open(selectedRowIndex, selectedColIndex);
            checkIfGameIsOver();
            return;
        }
        
        System.out.println("잘못된 번호를 선택하셨습니다.");
    }
    
    private static void changeGameStatusToLose() {
        gameStatus = -1;
    }
    
    private static boolean isLandMineCell(int selectedRowIndex, int selectedColIndex) {
        return LAND_MINES[selectedRowIndex][selectedColIndex];
    }
    
    private static boolean doesUserChooseToOpenCell(String userActionInput) {
        return userActionInput.equals("1");
    }
    
    private static boolean doesUserChooseToPlantFlag(String userActionInput) {
        return userActionInput.equals("2");
    }
    
    private static int getSelectedRowIndex(String cellInput) {
        char cellInputRow = cellInput.charAt(1);
        return convertRowFrom(cellInputRow);
    }
    
    private static int getSelectedColIndex(String cellInput) {
        char cellInputCol = cellInput.charAt(0);
        return convertColFrom(cellInputCol);
    }
    
    private static String getUserActionInputFromUser() {
        System.out.println("선택한 셀에 대한 행위를 선택하세요. (1: 오픈, 2: 깃발 꽂기)");
        
        //input2 -> actionInput 으로 변수명 명확화
        return SCANNER.nextLine();
    }
    
    private static String getCellInputFromUser() {
        System.out.println();
        System.out.println("선택할 좌표를 입력하세요. (예: a1)");
        
        //input -> cellInput 으로 변수명 명확화
        return SCANNER.nextLine();
    }
    
    private static boolean doesUserWinTheGame() {
        return gameStatus == 1;
    }
    
    private static void checkIfGameIsOver() {
        boolean isAllOpened = isAllCellOpened();
        
        if (isAllOpened) {
            changeGameStatusToWin();
        }
    }
    
    private static void changeGameStatusToWin() {
        gameStatus = 1;
    }
    
    private static boolean isAllCellOpened() {
        return Arrays.stream(BOARD) // Stream<String[]> (2차원 배열을 Stream으로 변환)
                .flatMap(Arrays::stream) // Stream<String> (2차원 배열을 1차원 배열로 변환)
                .noneMatch(cell -> cell.equals(CLOSED_CELL_SIGN)); // 모든 셀이 CLOSED_CELL_SIGN인지 확인
    }
    
    private static int convertRowFrom(char cellInputRow) {
        return Character.getNumericValue(cellInputRow) - 1;
    }
    
    private static int convertColFrom(char cellInputCol) {
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
            default -> -1;
        };
    }
    
    private static void showBoard() {
        System.out.println("   a b c d e f g h i j");
        for (int i = 0; i < BOARD_ROW_SIZE; i++) {
            System.out.printf("%d  ", i + 1);
            
            for (int j = 0; j < BOARD_COL_SIZE; j++) {
                System.out.print(BOARD[i][j] + " ");
            }
            
            System.out.println();
        }
        System.out.println();
    }
    
    private static void initializeGame() {
        for (int row = 0; row < BOARD_ROW_SIZE; row++) {
            for (int col = 0; col < BOARD_COL_SIZE; col++) {
                BOARD[row][col] = MinesweeperGame.CLOSED_CELL_SIGN;
            }
        }
        
        for (int i = 0; i < LAND_MINE_COUNT; i++) {
            int col = new Random().nextInt(BOARD_COL_SIZE);
            int row = new Random().nextInt(BOARD_ROW_SIZE);
            LAND_MINES[row][col] = true;
        }
        
        // i , j  row col  가독성 증대
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 10; col++) {
                int count = 0;
                if (!isLandMineCell(row, col)) {
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
                    NEARBY_LAND_MINE_COUNTS[row][col] = count;
                    continue;
                }
                NEARBY_LAND_MINE_COUNTS[row][col] = 0;
            }
        }
    }
    
    private static void showGameStartComment() {
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        System.out.println("지뢰찾기 게임 시작!");
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
    }
    
    private static void open(int row, int col) {
        if (row < 0 || row >= 8 || col < 0 || col >= 10) {
            return;
        }
        
        if (!BOARD[row][col].equals(MinesweeperGame.CLOSED_CELL_SIGN)) {
            return;
        }
        
        if (isLandMineCell(row, col)) {
            return;
        }
        
        if (NEARBY_LAND_MINE_COUNTS[row][col] != 0) {
            BOARD[row][col] = String.valueOf(NEARBY_LAND_MINE_COUNTS[row][col]);
            return;
        } else {
            BOARD[row][col] = OPENED_CELL_SIGN;
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