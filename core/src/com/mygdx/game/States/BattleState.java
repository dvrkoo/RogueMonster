package com.mygdx.game.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.RogueMonster;
import com.mygdx.game.Characters.Player;
import com.mygdx.game.Utils.Damage;

public class BattleState implements Screen{
    final RogueMonster game;
    final Screen oldState;
    OrthographicCamera camera = new OrthographicCamera();
    Damage damage;
    Player player;
    Rectangle attackButton = new Rectangle();
    Rectangle runButton = new Rectangle();
    Rectangle bagButton = new Rectangle();
    Rectangle switchButton = new Rectangle();
    Texture attackTexture = new Texture("Buttons/button_attack.png");
    Texture runTexture = new Texture("Buttons/button_run.png");
    //Texture switchTexture = new Texture("Buttons/button_switch.png");
    //Texture bagTexture = new Texture("Buttons/button_bag.png");
    Vector3 touchPoint = new Vector3();

    public BattleState(final RogueMonster game, final GameState oldState){
        this.game = game;
        this.oldState = oldState;
        this.player = oldState.getPlayer();

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


       


    }

    @Override
    public void show() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void render(float delta) {
        // TODO Auto-generated method stub
        ScreenUtils.clear(Color.FOREST);
        camera.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));

        game.batch.begin();
        game.batch.draw(attackTexture, attackButton.x, attackButton.y, attackButton.width, attackButton.height);
        //game.batch.draw(switchTexture, switchButton.x, switchButton.y, switchButton.width, switchButton.width);
        //game.batch.draw(bagTexture, bagButton.x, bagButton.y, bagButton.width, bagButton.width);
        game.batch.draw(runTexture, runButton.x, runButton.y, runButton.width, runButton.height);
        game.batch.end();





        //exit condition
        if(Gdx.input.justTouched()){
            isButtonTouched(Gdx.input.getX(), Gdx.input.getY());
            
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
        
    }

    void isButtonTouched(float x, float y){
        y = Math.abs(y - 1000);
        System.out.println(x + " " + y);

        if(attackButton.contains(x, y)){
            System.out.println(" ha attaccato");
            //attack function between pokemon party and foe
           
        }else if(runButton.contains(x, y)){
            game.setScreen(oldState);
            dispose();

        }

    }
    
}
