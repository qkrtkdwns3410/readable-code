package cleancode.minesweeper.tobe;

import cleancode.minesweeper.tobe.game.GameInitializable;
import cleancode.minesweeper.tobe.game.GameRunnable;
import cleancode.minesweeper.tobe.gameLevel.GameLevel;
import cleancode.minesweeper.tobe.io.InputHandler;
import cleancode.minesweeper.tobe.io.OutputHandler;

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
public class Minesweeper implements GameInitializable, GameRunnable {
    private final GameBoard gameBoard;
    private final BoardIndexConverter boardIndexConverter = new BoardIndexConverter();
    private final InputHandler inputHandler;
    private final OutputHandler outputHandler;
    
    private static int gameStatus = 0; // 0: 게임 중, 1: 승리, -1: 패배
    
    public Minesweeper(GameLevel gameLevel, InputHandler inputHandler, OutputHandler outputHandler) {
        gameBoard = new GameBoard(gameLevel);
        this.inputHandler = inputHandler;
        this.outputHandler = outputHandler;
    }
    
    @Override
    public void initialize() {
        gameBoard.initializeGame();
    }
    
    public void run() {
        outputHandler.showGameStartComment();
        
        while (true) {
            try {
                outputHandler.showBoard(gameBoard);
                
                if (doesUserWinTheGame()) {
                    outputHandler.gameWinningComment();
                    break;
                }
                
                if (gameStatus == -1) {
                    outputHandler.gameLosingComment();
                    break;
                }
                
                String cellInput = getCellInputFromUser();
                String userActionInput = getUserActionInputFromUser();
                
                actOnCell(cellInput, userActionInput);
            } catch (AppException e) {
                outputHandler.showExceptionMessage(e);
            } catch (Exception e) {
                outputHandler.showSimpleMessage();
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
        outputHandler.showCommentForUserAction();
        return inputHandler.getUserInput();
    }
    
    private String getCellInputFromUser() {
        outputHandler.showCommentForSelectingCell();
        return inputHandler.getUserInput();
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