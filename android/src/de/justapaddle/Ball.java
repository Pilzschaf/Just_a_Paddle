package de.justapaddle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;

class Ball {
	
	Rectangle ball;
	boolean exists = false;
	float velocityX;
	float velocityY;
	BreakBlock breakBlock;
	private int delay;
	
	void update(){
		if(delay < 1)
		{
			ball.x += velocityX;
			ball.y += velocityY;
			if(velocityY > 20.0f)
				velocityY = 20.0f;
			if(Math.abs(velocityY)< 5.0f) {
				if(velocityY < 0)
					velocityY = -5.0f;
				else
					velocityY = 5.0f;
			}
			if(velocityX > 10.0f)
				velocityX = 10.0f;
			
			if(ball.x > 480.0f - ball.width){
				ball.x = 480.0f - ball.width;
				velocityX = velocityX * -1.0f;
				breakBlock.game.wallSound.play(breakBlock.game.volume);
			}
			else if(ball.x < 0.0f){
				ball.x = 0.0f;
				velocityX = velocityX * -1.0f;
				breakBlock.game.wallSound.play(breakBlock.game.volume);
			}
			if(ball.y > 740.0f - ball.height){
				ball.y = 740.0f - ball.height;
				velocityY = velocityY * -1.0f;
				velocityX = (float)Math.random() * 14.0f - 7.0f;
				breakBlock.game.wallSound.play(breakBlock.game.volume);
			}
			else if(ball.y < breakBlock.paddle.y + breakBlock.paddle.height){
				if(ball.x <= breakBlock.paddle.x + breakBlock.paddle.width && ball.x >= breakBlock.paddle.x - ball.width && ball.y > breakBlock.paddle.y){
					ball.y = breakBlock.paddle.y + breakBlock.paddle.height;
					velocityY = velocityY * (-1.0f * (1.15f + (breakBlock.level/27)));
					if(breakBlock.paddle.x != 0.0f && breakBlock.paddle.x != 480.0f - breakBlock.paddle.width){
						velocityX = velocityX + Gdx.input.getAccelerometerX() * breakBlock.game.accacc * 0.5f;
					}
					breakBlock.game.wallSound.play(breakBlock.game.volume);
				}
				if(ball.y < 0.0f){
					exists = false;
				}
				
			}
			for(int i = 0; i < 32; i++){
				if(breakBlock.blocks[i].exists){
					breakBlock.blocks[i].collide(this);
				}
			}
		}
		else{
			delay --;
		}
	}
	void create(BreakBlock breakBlock){
		this.breakBlock = breakBlock;
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
	void create(BreakBlock breakBlock, int delay){
		this.breakBlock = breakBlock;
		exists = true;
		velocityX = (float)Math.random() * 4.0f -2.0f;
		velocityY = -5.0f;
		ball = new Rectangle();
		ball.x = 480/2;
		ball.y = 500;
		ball.width = 32;
		ball.height = 32;
		this.delay = delay;
	}
}
