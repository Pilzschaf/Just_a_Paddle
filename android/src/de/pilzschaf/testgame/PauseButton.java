package de.pilzschaf.testgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

class PauseButton {

    boolean isPaused = false;

    void update(){
        if(Gdx.input.justTouched()){
            if(!isPaused){
                isPaused = true;
            }
            else if((float)Gdx.input.getX() / Gdx.graphics.getWidth() > 0.4f && (float)Gdx.input.getX() / Gdx.graphics.getWidth() < 0.6f && (float)Gdx.input.getY() / Gdx.graphics.getHeight() > 0.4f && (float)Gdx.input.getY() / Gdx.graphics.getHeight() < 0.6f ){
                isPaused = false;
            }
        }
    }

    void draw(ShapeRenderer shapeRenderer, Color color1, Color color2){
        shapeRenderer.setColor(color2);
        shapeRenderer.rect(190.0f, 350.0f, 100.0f, 100.0f);
        shapeRenderer.setColor(color1);
        shapeRenderer.rect(205.0f, 365.0f, 25.0f, 70.0f);
        shapeRenderer.rect(250.0f, 365.0f, 25.0f, 70.0f);
    }
}
