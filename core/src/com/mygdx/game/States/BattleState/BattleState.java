package com.mygdx.game.States.BattleState;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.RogueMonster;
import com.mygdx.game.Characters.Player;
import com.mygdx.game.Observers.BattleNotifier;
import com.mygdx.game.Observers.Observer;
import com.mygdx.game.States.GameState;
import com.mygdx.game.Utils.Damage;
import com.mygdx.game.Utils.Enums.CharacterState;
import com.mygdx.game.Characters.Character;

public class BattleState implements Screen {
    final RogueMonster game;
    final Screen oldState;
    OrthographicCamera camera;
    Viewport viewport;
    Damage damage = new Damage();
    Player player;
    Rectangle attackButton = new Rectangle();
    Rectangle runButton = new Rectangle();
    Rectangle bagButton = new Rectangle();
    Rectangle switchButton = new Rectangle();
    Texture attackTexture = new Texture("Buttons/button_attack.png");
    Texture runTexture = new Texture("Buttons/button_run.png");
    Texture switchTexture = new Texture("Buttons/button_switch.png");
    Texture bagTexture = new Texture("Buttons/button_bag.png");
    Vector3 touchPoint = new Vector3();
    TeamScreen switchScreen;
    BagScreen bagScreen;
    float enlapsedTime;

    Character opponent;
    private List<Observer> observers = new ArrayList<>();
    // status
    int BATTLE_RUNNING = 0;
    int BATTLE_FINISH = 1;

    int gamestatus;

    BattleBox firstBattleBox = new BattleBox();
    BattleBox secondBattleBox = new BattleBox();

    String battleText = new String();

    public BattleState(final RogueMonster game, final GameState oldState) {
        gamestatus = 0;

        this.game = game;
        this.oldState = oldState;
        this.player = oldState.getPlayer();
        this.switchScreen = new TeamScreen(player);
        this.bagScreen = new BagScreen(player);
        for (Character iter : GameState.pokemon) {
            if (iter.isOpponent) {
                opponent = iter;
                GameState.pokemon.remove(iter);
                break;
            }
        }
        addObserver(new BattleNotifier(firstBattleBox));
        addObserver(new BattleNotifier(secondBattleBox));
        // button setup
        attackButton.setPosition(10, 10);
        switchButton.setPosition(195, 10);
        bagButton.setPosition(380, 10);
        runButton.setPosition(565, 10);
        attackButton.setWidth(175);
        attackButton.setHeight(65);
        switchButton.setWidth(175);
        switchButton.setHeight(65);
        bagButton.setWidth(175);
        bagButton.setHeight(65);
        runButton.setWidth(175);
        runButton.setHeight(65);

        // camera settings
        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false, 1000, 1000);

        // battle setting battle scene
        player.setState(CharacterState.NORTH);
        player.setPosition(400, 400);
        teamSetPosition();
        opponent.setState(CharacterState.SOUTH);
        opponent.setPosition(470, 600);
    }

    @Override
    public void show() {
        // TODO Auto-generated method stub

    }

    @Override
    public void render(float delta) {
        // TODO Auto-generated method stub
        ScreenUtils.clear(Color.FOREST);
        enlapsedTime += delta;

        camera.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));

        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        drawText();
        drawButtons();
        renderScreen();
        drawScene();
        standCharacter();
        game.batch.end();
        // exit condition
        if (Gdx.input.justTouched() && bagScreen.isVisible) {
            chooseItem(Gdx.input.getX(), Gdx.input.getY());
        } else if (Gdx.input.justTouched() && switchScreen.isVisible) {
            changePokemon(Gdx.input.getX(), Gdx.input.getY());
        } else if (Gdx.input.justTouched()) {
            isButtonTouched(Gdx.input.getX(), Gdx.input.getY());
        }
        if (gamestatus == BATTLE_FINISH) {
            game.setScreen(oldState);
            dispose();
        }

    }

    @Override
    public void resize(int width, int height) {
        // TODO Auto-generated method stub

    }

    @Override
    public void pause() {
        // TODO Auto-generated method stub

    }

    @Override
    public void resume() {
        // TODO Auto-generated method stub

    }

    @Override
    public void hide() {
        // TODO Auto-generated method stub

    }

    @Override
    public void dispose() {
        // TODO Auto-generated method stub
        bagTexture.dispose();
        attackTexture.dispose();
        switchTexture.dispose();
        runTexture.dispose();

    }

    void isButtonTouched(float x, float y) {
        y = Math.abs(y - 1000);

        if (attackButton.contains(x, y)) {
            Battle();

        } else if (runButton.contains(x, y)) {
            endBattle();

        } else if (bagButton.contains(x, y)) {
            bagScreen.isVisible = true;

        } else if (switchButton.contains(x, y)) {
            switchScreen.isVisible = true;

        }

    }

    void chooseItem(float x, float y) {
        y = Math.abs(y - 1000);

        for (int i = 0; i < bagScreen.buttons.size(); i++) {
            if (bagScreen.buttons.get(i).contains(x, y) && !player.getBag().getBag().isEmpty()) {
                player.getBag().getBag().get(i).get(0).useItem(player.getPokemon(0));
                player.getBag().remove(player.getBag().getBag().get(i).get(0));
            }
        }
        bagScreen.isVisible = false;
    }

    void changePokemon(float x, float y) {
        y = Math.abs(y - 1000);

        for (int i = 0; i < switchScreen.buttons.length; i++) {
            if (switchScreen.buttons[i].contains(x, y) && player.getPokemon(i) != null) {
                player.swapPokemon(0, i);
                switchScreen.swapIcon(0, i);
                switchScreen.isVisible = false;
                break;
            }
        }
    }

    void Battle() {
        int dmg;
        String text = new String();
        if (opponent.getSpeed() > player.getPokemon(0).getSpeed()) {
            dmg = damage.getDamage(opponent, player.getPokemon(0));
            // opponent damage to the pokemon on the field
            player.getPokemon(0).takeDamage(dmg);
            text = opponent.getName() + " attacca " + player.getPokemon(0).getName() + " Danni : " + dmg
                    + " " + damage.getEffective( opponent, player.getPokemon(0)) + "\n";

            // the pokemon on the field attack only if it isn't dead
            if (player.getPokemon(0).getActualHp() <= 0) {
                // logica di morte e switch di pokemon
                player.removePokemon(0);
                switchScreen.isVisible = true;
            } else {
                dmg = damage.getDamage(player.getPokemon(0), opponent);
                opponent.takeDamage(dmg);
                text += player.getPokemon(0).getName() + " attacca " + opponent.getName() + " Danni : " + dmg
                        + " " + damage.getEffective(player.getPokemon(0), opponent);
                updateGameBox(text);

            }
            if (opponent.getActualHp() <= 0) {
                // logica di fine battaglia e switch al GameState
                player.getPokemon(0).levelUp();
                endBattle();
            }
        } else {
            dmg = damage.getDamage(player.getPokemon(0), opponent);
            opponent.takeDamage(dmg);
            text = player.getPokemon(0).getName() + " attacca " + opponent.getName() + " Danni : " + dmg
                    + " " + damage.getEffective(player.getPokemon(0), opponent) + "\n";
            updateGameBox(text);
            if (opponent.getActualHp() <= 0) {
                // logica di fine battaglia e switch al GameState
                player.getPokemon(0).levelUp();
                endBattle();
            } else {
                dmg = damage.getDamage(opponent, player.getPokemon(0));
                player.getPokemon(0).takeDamage(dmg);
                text += opponent.getName() + " attacca " + player.getPokemon(0).getName() + " Danni : " + dmg
                        + " " + damage.getEffective(opponent, player.getPokemon(0));
                updateGameBox(text);
            }
            if (player.getPokemon(0).getActualHp() <= 0) {
                // logica di morte e switch di pokemon
                player.removePokemon(0);
                switchScreen.isVisible = true;
            }
        }
        checkLoose();

    }

    void teamSetPosition() {
        for (int i = 0; i < 6; i++) {
            if (player.getPokemon(i) != null) {
                player.getPokemon(i).setPosition(470, 400);
                player.getPokemon(i).setState(CharacterState.NORTH);

            }
        }
    }

    void endBattle() {
        gamestatus = BATTLE_FINISH;
        this.dispose();
    }

    void renderScreen() {
        if (switchScreen.isVisible) {
            switchScreen.draw(game);
        }
        if (bagScreen.isVisible) {
            bagScreen.drawBagScreen(game);
        }
    }

    void drawButtons() {
        game.batch.draw(attackTexture, attackButton.x, attackButton.y, attackButton.width, attackButton.height);
        game.batch.draw(switchTexture, switchButton.x, switchButton.y, switchButton.width, switchButton.height);
        game.batch.draw(bagTexture, bagButton.x, bagButton.y, bagButton.width, bagButton.height);
        game.batch.draw(runTexture, runButton.x, runButton.y, runButton.width, runButton.height);
    }

    void standCharacter() {
        player.movement(0, 0, CharacterState.STANDING);
        if (player.getPokemon(0) != null)
            player.getPokemon(0).movement(0, 0, CharacterState.STANDING);
        opponent.movement(0, 0, CharacterState.STANDING);
    }

    void drawScene() {
        game.batch.draw(player.getAnimation().getKeyFrame(enlapsedTime, true), player.getX(), player.getY());
        game.batch.draw(opponent.getAnimation().getKeyFrame(enlapsedTime, true), opponent.getX(), opponent.getY());
        game.font.draw(game.batch, "Lv: " + opponent.getLevel() + " " + opponent.getActualHp() + " / " + opponent.getHp(), opponent.getX()+ 16, opponent.getY() + 80);
        if (player.getPokemon(0) != null) {
            game.batch.draw(player.getPokemon(0).getAnimation().getKeyFrame(enlapsedTime, true), player.getPokemon(0).getX(), player.getPokemon(0).getY());
            game.font.draw(game.batch,"Lv: " + player.getPokemon(0).getLevel() + " " + player.getPokemon(0).getActualHp() + " / " + player.getPokemon(0).getHp(), player.getPokemon(0).getX()+ 16, player.getPokemon(0).getY() + 80);
        }
    }

    void checkLoose() {
        boolean found = false;
        for (int i = 0; i < 6; i++) {
            if (player.getPokemon(i) != null) {
                found = true;
            }
        }
        if (!found) {
            endBattle();
            // game end loose, for now just return game state

        }
    }

    void addObserver(Observer o) {
        this.observers.add(o);
    }

    void removeObserver(Observer o) {
        this.observers.remove(o);
    }

    public void updateGameBox(String toPrint) {
        for (Observer o : observers) {
            o.update(toPrint);
        }
    }

    public void drawText() {
        if (firstBattleBox.isVisible) {
            firstBattleBox.draw(game);
        } else if (secondBattleBox.isVisible) {
            secondBattleBox.draw(game);
        }
    }

}
