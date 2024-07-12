package cleancode.minesweeper.tobe;

import cleancode.minesweeper.tobe.gameLevel.GameLevel;
import cleancode.minesweeper.tobe.io.ConsoleInputHandler;
import cleancode.minesweeper.tobe.io.ConsoleOutputHandler;

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
    private final GameBoard gameBoard;
    private final BoardIndexConverter boardIndexConverter = new BoardIndexConverter();
    private final ConsoleInputHandler consoleInputHandler = new ConsoleInputHandler();
    private final ConsoleOutputHandler consoleOutputHandler = new ConsoleOutputHandler();
    
    private static int gameStatus = 0; // 0: 게임 중, 1: 승리, -1: 패배
    
    public Minesweeper(GameLevel gameLevel) {
        gameBoard = new GameBoard(gameLevel);
    }
    
    public void run() {
        consoleOutputHandler.showGameStartComment();
        gameBoard.initializeGame();
        
        while (true) {
            try {
                consoleOutputHandler.showBoard(gameBoard);
                
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
                consoleOutputHandler.printExceptionMessage(e);
            } catch (Exception e) {
                consoleOutputHandler.printSimpleMessage();
            }
        }
        
    }
    
    private void actOnCell(String cellInput, String userActionInput) {
        int selectedColIndex = boardIndexConverter.getSelectedColIndex(cellInput,gameBoard.getColSize());
        int selectedRowIndex = boardIndexConverter.getSelectedRowIndex(cellInput, gameBoard.getRowSize());
        
        if (doesUserChooseToPlantFlag(userActionInput)) {
            gameBoard.flag(selectedRowIndex, selectedColIndex);
            checkIfGameIsOver();
            return;
        }
        
        if (doesUserChooseToOpenCell(userActionInput)) {
            if (gameBoard.isLandMineCell(selectedRowIndex, selectedColIndex)) {
                gameBoard.open(selectedRowIndex, selectedColIndex);
                changeGameStatusToLose();
                return;
            }
            
            gameBoard.openSurrounedCells(selectedRowIndex, selectedColIndex);
            checkIfGameIsOver();
            return;
        }
        
        throw new AppException("잘못된 입력입니다. 다시 입력해주세요.");
    }
    
    private void changeGameStatusToLose() {
        gameStatus = -1;
    }
    
    private boolean doesUserChooseToOpenCell(String userActionInput) {
        return userActionInput.equals("1");
    }
    
    private boolean doesUserChooseToPlantFlag(String userActionInput) {
        return userActionInput.equals("2");
    }
    
    private String getUserActionInputFromUser() {
        consoleOutputHandler.printCommentForUserAction();
        return consoleInputHandler.getUserInput();
    }
    
    private String getCellInputFromUser() {
        consoleOutputHandler.printCommentForSelectingCell();
        return consoleInputHandler.getUserInput();
    }
    
    private boolean doesUserWinTheGame() {
        return gameStatus == 1;
    }
    
    private void checkIfGameIsOver() {
        if (gameBoard.isAllCellChecked()) {
            changeGameStatusToWin();
        }
    }
    
    private void changeGameStatusToWin() {
        gameStatus = 1;
    }
}