package com.mygdx.game.States.BattleState;

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
import com.mygdx.game.States.GameState;
import com.mygdx.game.Utils.Damage;
import com.mygdx.game.Utils.Enums.CharacterState;
import com.mygdx.game.Characters.Character;

public class BattleState implements Screen{
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
    SwitchScreen switchScreen;
    float enlapsedTime;

    Character opponent ;

    //status
    int BATTLE_RUNNING = 0;
    int BATTLE_FINISH = 1;

    int gamestatus;

    public BattleState(final RogueMonster game, final GameState oldState){
        gamestatus = 0;

        this.game = game;
        this.oldState = oldState;
        this.player = oldState.getPlayer();
        this.switchScreen = new SwitchScreen(player);
        for (Character iter : GameState.pokemon) {
            if(iter.isOpponent){
                opponent = iter;
                GameState.pokemon.remove(iter);
                break;
            }
        }

        //button setup
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

        //camera settings
        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false, 1000, 1000);
        
        //battle setting battle scene
        player.setState(CharacterState.NORTH); 
        player.setPosition(400,400);
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
        drawScene();
        drawButtons();
        renderScreen();
        drawScene();
        game.batch.end();

        standCharacter();
        //exit condition
        if(Gdx.input.justTouched() && !switchScreen.isVisible){
            isButtonTouched(Gdx.input.getX(), Gdx.input.getY());   
        }else if(Gdx.input.justTouched()){
            changePokemon(Gdx.input.getX(), Gdx.input.getY());
        } 
        if(gamestatus == BATTLE_FINISH){
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

    void isButtonTouched(float x, float y){
        y = Math.abs(y - 1000);

        if(attackButton.contains(x, y)){
            Battle();

        }else if(runButton.contains(x, y)){
            endBattle();

        }else if(bagButton.contains(x, y)){
            System.out.println(" ha usato bag");
            
            //bag function to use items

        }else if(switchButton.contains(x, y)){
            switchScreen.isVisible = true;
            //aswitch pokemon function
        }

    }

    void changePokemon(float x, float y){
        y = Math.abs(y - 1000);

        for (int i = 0; i < switchScreen.buttons.length; i++) {
            if(switchScreen.buttons[i].contains(x, y) && player.getPokemon(i) != null){
                player.swapPokemon(0, i);
                switchScreen.swapIcon(0, i);
                switchScreen.isVisible = false;
                break;
            }
        }
    }


    void Battle(){
        if(opponent.getSpeed() > player.getPokemon(0).getSpeed()){
            int dmg = damage.getDamage(opponent, player.getPokemon(0));

            //opponent damage to the pokemon on the field
            player.getPokemon(0).takeDamage(dmg);
            
            System.out.println("O attacca P hp rimanenti:" + player.getPokemon(0).getHp());
            System.out.println(dmg);
            

            //the pokemon on the field attack only if it isn't dead
            if(player.getPokemon(0).getHp() <= 0){
                //logica di morte e switch di pokemon
                System.out.println("è morto il mio");
                player.removePokemon(0); 
                switchScreen.isVisible = true;
            }else{
                opponent.takeDamage(damage.getDamage(player.getPokemon(0), opponent));
                System.out.println("P attacca O hp rimanenti:" + opponent.getHp());
                
            }
            if(opponent.getHp() <= 0){
                //logica di fine battaglia e switch al GameState
                System.out.print("è morto il suo");
                endBattle();
            }
        }else{
            opponent.takeDamage(damage.getDamage(player.getPokemon(0), opponent));
            System.out.println("P attacca O hp rimanenti:" + opponent.getHp());

            if(opponent.getHp() <= 0){
                //logica di fine battaglia e switch al GameState
                System.out.print("è morto il suo");
                endBattle();
            }else{
                player.getPokemon(0).takeDamage(damage.getDamage(opponent, player.getPokemon(0)));
                System.out.println("O attacca P hp rimanenti:" + player.getPokemon(0).getHp());
            }   
             if(player.getPokemon(0).getHp() <= 0){
                //logica di morte e switch di pokemon
                System.out.println("è morto il mio");
                player.removePokemon(0); 
                switchScreen.isVisible = true;
            }
        }
        checkLoose();
    
    }
    void teamSetPosition(){
        for (int i = 0; i < 6; i++) {
            if(player.getPokemon(i) != null){
                player.getPokemon(i).setPosition(470, 400);
                player.getPokemon(i).setState(CharacterState.NORTH);

            }    
        }
    }

    void endBattle(){
        gamestatus = BATTLE_FINISH;
    }

    void renderScreen(){
        if(switchScreen.isVisible){
            for (int i = 0; i < switchScreen.buttons.length; i++) {
                game.batch.draw(switchScreen.buttonTexture, switchScreen.buttons[i].x, switchScreen.buttons[i].y, switchScreen.buttons[i].width, switchScreen.buttons[i].height);
            }
            for (int i = 0; i < switchScreen.pokemonIcon.length; i++) {
                if(player.getPokemon(i) != null){
                    game.batch.draw(switchScreen.pokemonIcon[i], switchScreen.buttons[i].x - 70, switchScreen.buttons[i].y);
                }
            }        
        }
    }

    void drawButtons(){
        game.batch.draw(attackTexture, attackButton.x, attackButton.y, attackButton.width, attackButton.height);
        game.batch.draw(switchTexture, switchButton.x, switchButton.y, switchButton.width, switchButton.height);
        game.batch.draw(bagTexture, bagButton.x, bagButton.y, bagButton.width, bagButton.height);
        game.batch.draw(runTexture, runButton.x, runButton.y, runButton.width, runButton.height);
    }
    void standCharacter(){
        player.movement(0, 0, CharacterState.STANDING);
        if(player.getPokemon(0) != null)
            player.getPokemon(0).movement(0, 0, CharacterState.STANDING);
        opponent.movement(0, 0, CharacterState.STANDING);
    }
    void drawScene(){
        game.batch.draw(player.getAnimation().getKeyFrame(enlapsedTime, true), player.getX(), player.getY());
        game.batch.draw(opponent.getAnimation().getKeyFrame(enlapsedTime, true), opponent.getX(), opponent.getY());
        if(player.getPokemon(0) != null){
            game.batch.draw(player.getPokemon(0).getAnimation().getKeyFrame(enlapsedTime, true), player.getPokemon(0).getX(), player.getPokemon(0).getY());
        }   
    }

    void checkLoose(){
        boolean found = false;
        for (int i = 0; i < 6; i++) {
            if(player.getPokemon(i) != null){
                found = true;
            }
        }
        if(!found){
            //game end loose, for now just return game state
            endBattle();
        }
    }
}
