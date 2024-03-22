package map.BattleInterface;

import InteractableEntity.Enemy;
import InteractableEntity.InteractableEntity;
import command.ErrorCommand;
import map.AMap;
import textbox.PlayerStatus;
import textbox.TextBox;
import ui.Ui;
import Math.*;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BattleInterface extends AMap {
    protected PlayerStatus currentPlayer;
    protected TextBox currentTextBox;
    protected InteractableEntity currentEntity;


    public BattleInterface(PlayerStatus player, TextBox text, InteractableEntity entity) {
        this.currentPlayer = player;
        this.currentTextBox = text;
        this.currentEntity = entity;

    }

    @Override
    public void fightLoop() {

    }

    @Override
    public void fightLoop(Scanner in) {
        MathPool mathPool = new MathPool();
        mathPool.init();
        Ui ui = new Ui();

        while (currentPlayer.getPlayerHealth() > 0 && currentEntity.getHealth() > 0) {
            int answer;
            Pattern pattern = Pattern.compile("^[--]?[0-9]+$");
            Matcher matcher;
            ui.printPlayerStatus(currentPlayer);
            ui.printMap(currentMap);
            MathQuestion mathQuestion = mathPool.getQuestionByDifficulty(0);
            ui.printQuestion(mathQuestion);
            String answerCommand = "";
            while (!pattern.matcher(answerCommand).matches()) {
            answerCommand = in.nextLine().trim();
            new ErrorCommand(new NumberFormatException("Answer must be an integer.")).execute();
            }
            answer = Integer.parseInt(answerCommand);
            if (mathQuestion.checkAns(answer)) {
                System.out.println("CORRECT!!!");

                playerHitEnemy();
            } else {
                System.out.println("WRONG ANSWER!!!");
                enemyHitPlayer();
            }
        }
    }

    public void initMap(int givenWidth, int givenHeight) {
        this.width = givenWidth;
        this.height = givenHeight;
        this.currentMap = new ArrayList<>(height);

        for (int i = 0; i < height; i += 1) {
            ArrayList<Character> row = new ArrayList<>(width);
            for (int j = 0; j < width; j += 1) {
                if (i == 0 || i == height - 1) {
                    row.add('=');
                } else if (j == 0 || j == width - 1) {
                    row.add('|');
                } else if ((i == height / 2 - 2 || i == height / 2 + 1)
                        && (j >= width / 2 - 2 && j <= width / 2 + 1)
                        || (j == width / 2 - 2 || j == width / 2 + 1)
                        && (i >= height / 2 - 2 && i <= height / 2 + 1)) {
                    row.add('@');
                } else {
                    row.add(' ');
                }
            }
            currentMap.add(row);
        }
    }


    public void playerHitEnemy() {
        if (currentEntity instanceof Enemy) {
            int dmgDone = 10;
            ((Enemy) currentEntity).harmHealth(dmgDone);
        }
    }

    public void enemyHitPlayer() {
        if (currentEntity instanceof Enemy) {
            int dmgDone = ((Enemy) currentEntity).getDamage();
            currentPlayer.harmHealth(dmgDone);
        }
    }


    public InteractableEntity getCurrentEntity() {
        return currentEntity;
    }

    @Override
    public boolean getEntityDeath() {
        return currentEntity.getHealth() <= 0;
    }

    public PlayerStatus getCurrentPlayer() {
        return currentPlayer;
    }

}
