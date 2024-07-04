package cleancode.minesweeper.tobe;

import java.util.Random;
import java.util.Scanner;

public class MinesweeperGame {

    private static String[][] board = new String[8][10];
    private static Integer[][] landMineCounts = new Integer[8][10];
    private static boolean[][] landMines = new boolean[8][10];
    private static int gameStatus = 0; // 0: 게임 중, 1: 승리, -1: 패배

    public static void main(String[] args) {
        // 안내 문구 메서드 분리
        showGameStartComment();
        
        Scanner scanner = new Scanner(System.in);
        
        // 게임 초기화 메서드 분리
        initializeGame();
        
        while (true) {
            showBoard();
            
            if (gameStatus == 1) {
                System.out.println("지뢰를 모두 찾았습니다. GAME CLEAR!");
                break;
            }
            
            if (gameStatus == -1) {
                System.out.println("지뢰를 밟았습니다. GAME OVER!");
                break;
            }
            
            System.out.println();
            System.out.println("선택할 좌표를 입력하세요. (예: a1)");
            
            //input -> cellInput 으로 변수명 명확화
            String cellInput = scanner.nextLine();
            System.out.println("선택한 셀에 대한 행위를 선택하세요. (1: 오픈, 2: 깃발 꽂기)");
            
            //input2 -> actionInput 으로 변수명 명확화
            String actionInput = scanner.nextLine();
            
            // c -> cellInputCol, r -> cellInputRow 변수명 명확화
            char cellInputCol = cellInput.charAt(0);
            char cellInputRow = cellInput.charAt(1);
            
            int selectedColIndex = convertColFrom(cellInputCol);
            int selectedRowIndex = convertRowFrom(cellInputRow);
            
            if (actionInput.equals("2")) {
                board[selectedRowIndex][selectedColIndex] = "⚑";
                // open -> isAllOpened 변수명 명확화
                checkIfGameIsOver();
            } else if (actionInput.equals("1")) {
                if (landMines[selectedRowIndex][selectedColIndex]) {
                    board[selectedRowIndex][selectedColIndex] = "☼";
                    gameStatus = -1;
                    continue;
                } else {
                    open(selectedRowIndex, selectedColIndex);
                }
                
                // open -> isAllOpened 변수명 명확화
                checkIfGameIsOver();
            } else {
                System.out.println("잘못된 번호를 선택하셨습니다.");
            }
        }
    }
    
    private static void checkIfGameIsOver() {
        boolean isAllOpened = isAllCellOpened();
        
        if (isAllOpened) {
            gameStatus = 1;
        }
    }
    
    private static boolean isAllCellOpened() {
        boolean isAllOpened = true;
        
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 10; col++) {
                if (board[row][col].equals("□")) {
                    isAllOpened = false;
                }
            }
        }
        
        return isAllOpened;
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
        for (int i = 0; i < 8; i++) {
            System.out.printf("%d  ", i + 1);
            
            for (int j = 0; j < 10; j++) {
                System.out.print(board[i][j] + " ");
            }
            
            System.out.println();
        }
        System.out.println();
    }
    
    private static void initializeGame() {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 10; col++) {
                board[row][col] = "□";
            }
        }
        for (int i = 0; i < 10; i++) {
            int col = new Random().nextInt(10);
            int row = new Random().nextInt(8);
            landMines[row][col] = true;
        }
        
        // i , j  row col  가독성 증대
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 10; col++) {
                int count = 0;
                if (!landMines[row][col]) {
                    if (row - 1 >= 0 && col - 1 >= 0 && landMines[row - 1][col - 1]) {
                        count++;
                    }
                    if (row - 1 >= 0 && landMines[row - 1][col]) {
                        count++;
                    }
                    if (row - 1 >= 0 && col + 1 < 10 && landMines[row - 1][col + 1]) {
                        count++;
                    }
                    if (col - 1 >= 0 && landMines[row][col - 1]) {
                        count++;
                    }
                    if (col + 1 < 10 && landMines[row][col + 1]) {
                        count++;
                    }
                    if (row + 1 < 8 && col - 1 >= 0 && landMines[row + 1][col - 1]) {
                        count++;
                    }
                    if (row + 1 < 8 && landMines[row + 1][col]) {
                        count++;
                    }
                    if (row + 1 < 8 && col + 1 < 10 && landMines[row + 1][col + 1]) {
                        count++;
                    }
                    landMineCounts[row][col] = count;
                    continue;
                }
                landMineCounts[row][col] = 0;
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
        if (!board[row][col].equals("□")) {
            return;
        }
        if (landMines[row][col]) {
            return;
        }
        if (landMineCounts[row][col] != 0) {
            board[row][col] = String.valueOf(landMineCounts[row][col]);
            return;
        } else {
            board[row][col] = "■";
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