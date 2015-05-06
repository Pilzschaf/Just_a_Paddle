package de.pilzschaf.testgame.android;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;

public class Ball {
	
	public Rectangle ball;
	public boolean exists = false;
	public float velocityX;
	public float velocityY;
	public Breakblock breakblock;
	private int delay;
	
	public void update(){
		if(delay < 1)
		{
			ball.x += velocityX;
			ball.y += velocityY;
			if(velocityY > 20.0f)
				velocityY = 20.0f;
			if(velocityX > 10.0f)
				velocityX = 10.0f;
			
			if(ball.x > 480.0f - ball.width){
				ball.x = 480.0f - ball.width;
				velocityX = velocityX * -1.0f;
				breakblock.wallsound.play(0.7f);
			}
			else if(ball.x < 0.0f){
				ball.x = 0.0f;
				velocityX = velocityX * -1.0f;
				breakblock.wallsound.play(0.7f);
			}
			if(ball.y > 740.0f - ball.height){
				ball.y = 740.0f - ball.height;
				velocityY = velocityY * -1.0f;
				velocityX = (float)Math.random() * 14.0f - 7.0f;
				breakblock.wallsound.play(0.7f);
			}
			else if(ball.y < breakblock.paddle.y + breakblock.paddle.height){
				if(ball.x <= breakblock.paddle.x + breakblock.paddle.width && ball.x >= breakblock.paddle.x - ball.width && ball.y > breakblock.paddle.y){
					ball.y = breakblock.paddle.y + breakblock.paddle.height;
					velocityY = velocityY * -1.1f;
					if(breakblock.paddle.x != 0.0f && breakblock.paddle.x != 480.0f - breakblock.paddle.width){
						velocityX = velocityX + Gdx.input.getAccelerometerX() * breakblock.game.accacc * 0.5f;
					}
					breakblock.wallsound.play(breakblock.game.volume);
				}
				if(ball.y < 0.0f){
					exists = false;
				}
				
			}
			for(int i = 0; i < 32; i++){
				if(breakblock.blocks[i].exists){
					breakblock.blocks[i].Collide(this);
				}
			}
		}
		else{
			delay --;
		}
	}
	public void create(Breakblock pbreakblock){
		breakblock = pbreakblock;
		exists = true;
		velocityX = (float)Math.random() * 4.0f -2.0f;
		velocityY = -5.0f;
		ball = new Rectangle();
		ball.x = 480/2;
		ball.y = 500;
		ball.width = 32;
		ball.height = 32;
		delay = 0;
	}
	public void create(Breakblock pbreakblock, int pdelay){
		breakblock = pbreakblock;
		exists = true;
		velocityX = (float)Math.random() * 4.0f -2.0f;
		velocityY = -5.0f;
		ball = new Rectangle();
		ball.x = 480/2;
		ball.y = 500;
		ball.width = 32;
		ball.height = 32;
		delay = pdelay;
	}
}
