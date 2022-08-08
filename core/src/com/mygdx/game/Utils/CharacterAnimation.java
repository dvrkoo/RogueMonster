package com.mygdx.game.Utils;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class CharacterAnimation {
    Texture img;
    TextureRegion[] animationFramesNorth;
    TextureRegion[] animationFramesSouth;
    TextureRegion[] animationFramesEast;
    TextureRegion[] animationFramesWest;
    Animation<TextureRegion> animationNorth;
    Animation<TextureRegion> animationEast;
    Animation<TextureRegion> animationWest;
    Animation<TextureRegion> animationSouth;

    public CharacterAnimation(Texture texture){
        img = texture;

        TextureRegion[][] tmpFrames = TextureRegion.split(img, 64, 64);
        animationFramesSouth = new TextureRegion[4];
        animationFramesWest = new TextureRegion[4];
        animationFramesEast = new TextureRegion[4];
        animationFramesNorth = new TextureRegion[4];
        
        for( int i =0; i < 4; i++){
            for(int j = 0; j < 4; j++){
                switch(i){
                    case(0):{
                        animationFramesSouth[j] = tmpFrames[i][j];  
                    }
                    case(1):{
                        animationFramesWest[j] = tmpFrames[i][j];
                    }
                    case(2):{
                        animationFramesEast[j] = tmpFrames[i][j];
                    }
                    case(3):{
                        animationFramesNorth[j] = tmpFrames[i][j];
                    }
                }
                
            }
        }

        animationEast = new Animation<TextureRegion>(1f/4f, animationFramesEast);
        animationWest = new Animation<TextureRegion>(1f/4f, animationFramesWest);
        animationNorth = new Animation<TextureRegion>(1f/4f, animationFramesNorth);
        animationSouth = new Animation<TextureRegion>(1f/4f, animationFramesSouth);
    }

    public Animation<TextureRegion> getSouthAnimation(){
        return animationSouth;
    }

    public Animation<TextureRegion> getNorthAnimation(){
        return animationNorth;
    }

    public Animation<TextureRegion> getEastAnimation(){
        return animationEast;
    }

    public Animation<TextureRegion> getWestAnimation(){
        return animationWest;
    }

    
    
}
