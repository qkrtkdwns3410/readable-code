package cleancode.minesweeper.tobe;

import cleancode.minesweeper.tobe.cell.Cell2;
import cleancode.minesweeper.tobe.cell.EmptyCell;
import cleancode.minesweeper.tobe.cell.LandMineCell;
import cleancode.minesweeper.tobe.gameLevel.GameLevel;

import java.util.Arrays;
import java.util.Random;

/**
 * packageName    : cleancode.minesweeper.tobe
 * fileName       : GameBoard
 * author         : ipeac
 * date           : 24. 7. 11.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 24. 7. 11.        ipeac       최초 생성
 */
public class GameBoard {
    public static final int LAND_MINE_COUNT = 10;
    
    private final Cell2[][ ] board;
    private final int landMineCount;
    
    public GameBoard(GameLevel gameLevel) {
        int rowSize = gameLevel.getRowSize();
        int colSize = gameLevel.getColSize();
        
        board = new Cell2[rowSize][colSize];
        
        landMineCount = gameLevel.getLandMineCount();
    }
    
    public void initializeGame() {
        int rowSize = getRowSize();
        int colSize = getColSize();
        
        for (int row = 0; row < rowSize; row++) {
            for (int col = 0; col < colSize; col++) {
                board[row][col] = new EmptyCell();
            }
        }
        
        for (int i = 0; i < LAND_MINE_COUNT; i++) {
            int landMineCol = new Random().nextInt(colSize);
            int rowMineCol = new Random().nextInt(rowSize);
            
            LandMineCell landMineCell = new LandMineCell();
            landMineCell.turnOnLandMine();
            
            board[rowMineCol][landMineCol] = landMineCell;
        }
        
        for (int row = 0; row < rowSize; row++) {
            for (int col = 0; col < colSize; col++) {
                if (isLandMineCell(row, col)) {
                    continue;
                }
                
                int count = countNearbyLandMines(row, col);
                findCell(row, col).updateNearbyLandMineCount(count);
            }
        }
    }
    
    public boolean isLandMineCell(int selectedRowIndex, int selectedColIndex) {
        Cell2 foundCell = findCell(selectedRowIndex, selectedColIndex);
        return foundCell.isLandMine();
    }
    
    public int getRowSize() {
        return board.length;
    }
    
    public int getColSize() {
        return board[0].length;
    }
    
    public String getSign(int rowIndex, int colIndex) {
        Cell2 cell = findCell(rowIndex, colIndex);
        return cell.getSign();
    }
    
    public void flag(int rowIndex, int colIndex) {
        Cell2 foundCell = findCell(rowIndex, colIndex);
        foundCell.flag();
    }
    
    public void open(int rowIndex, int colIndex) {
        Cell2 foundCell = findCell(rowIndex, colIndex);
        foundCell.open();
    }
    
    private Cell2 findCell(int row, int col) {
        return board[row][col];
    }
    
    private int countNearbyLandMines(int row, int col) {
        int count = 0;
        int rowSize = getRowSize();
        int colSize = getColSize();
        
        if (row - 1 >= 0 && col - 1 >= 0 && isLandMineCell(row - 1, col - 1)) {
            count++;
        }
        
        if (row - 1 >= 0 && isLandMineCell(row - 1, col)) {
            count++;
        }
        
        if (row - 1 >= 0 && col + 1 < colSize && isLandMineCell(row - 1, col + 1)) {
            count++;
        }
        
        if (col - 1 >= 0 && isLandMineCell(row, col - 1)) {
            count++;
        }
        
        if (col + 1 < colSize && isLandMineCell(row, col + 1)) {
            count++;
        }
        
        if (row + 1 < rowSize && col - 1 >= 0 && isLandMineCell(row + 1, col - 1)) {
            count++;
        }
        
        if (row + 1 < rowSize && isLandMineCell(row + 1, col)) {
            count++;
        }
        
        if (row + 1 < rowSize && col + 1 < colSize && isLandMineCell(row + 1, col + 1)) {
            count++;
        }
        
        return count;
    }
    
    public boolean isAllCellChecked() {
        return Arrays.stream(board) // Stream<String[]> (2차원 배열을 Stream으로 변환)
                .flatMap(Arrays::stream) // Stream<String> (2차원 배열을 1차원 배열로 변환)
                .allMatch(Cell2::isChecked); // 모든 셀이 CLOSED_CELL_SIGN인지 확인
    }
    
    public void openSurrounedCells(int rowIndex, int colIndex) {
        if (rowIndex < 0 || rowIndex >= 8 || colIndex < 0 || colIndex >= 10) {
            return;
        }
        
        if (findCell(rowIndex,colIndex).isOpened()) {
            return;
        }
        
        if (isLandMineCell(rowIndex, colIndex)) {
            return;
        }
        
        open(rowIndex, colIndex);
        
        if (doesCellHaveLandMineCount(rowIndex, colIndex)) {
            return;
        }
        
        openSurrounedCells(rowIndex - 1, colIndex - 1);
        openSurrounedCells(rowIndex - 1, colIndex);
        openSurrounedCells(rowIndex - 1, colIndex + 1);
        openSurrounedCells(rowIndex, colIndex - 1);
        openSurrounedCells(rowIndex, colIndex + 1);
        openSurrounedCells(rowIndex + 1, colIndex - 1);
        openSurrounedCells(rowIndex + 1, colIndex);
        openSurrounedCells(rowIndex + 1, colIndex + 1);
    }
    
    private boolean doesCellHaveLandMineCount(int rowIndex, int colIndex) {
        return findCell(rowIndex, colIndex).hasLandMineCount();
    }
}