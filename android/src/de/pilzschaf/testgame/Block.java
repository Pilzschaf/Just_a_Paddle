package de.pilzschaf.testgame;

import com.badlogic.gdx.math.Rectangle;

class Block {
	private int hardness;
	boolean exists = false;
	Rectangle block;
	
	void create(float posX, float posY, int hardness){
		exists = true;
		block = new Rectangle();
		block.height = 32.0f;
		block.width = 100.0f;
		block.x = posX;
		block.y = posY;
		this.hardness = hardness;
	}
	void collide(Ball ball){
		if(ball.ball.x + 32.0f > block.x && ball.ball.x < block.x + 100.0f && ball.ball.y + 32.0f > block.y && ball.ball.y < block.y + 32.0f){
			ball.breakBlock.swapColors();
			float fDistLeft = Math.abs(ball.ball.x + 32.0f - block.x);
			float fDistRight = Math.abs(ball.ball.x - block.x - 100.0f);
			float fDistTop = Math.abs(ball.ball.y + 32.0f - block.y);
			float fDistBottom = Math.abs(ball.ball.y - block.y - 32.0f);
			float fMinDist = Math.min(Math.min(fDistLeft, fDistTop), Math.min(fDistRight, fDistBottom));
			if(fMinDist == fDistLeft){
				//Left
				ball.velocityX = ball.velocityX * -0.8f;
				ball.ball.x = block.x - 33.0f;
			}
			else if(fMinDist == fDistRight){
				//Right
				ball.velocityX = ball.velocityX * -0.8f;
				ball.ball.x = block.x + 101.0f;
			}
			else if(fMinDist == fDistTop){
				//Bottom
				ball.velocityY = ball.velocityY * -0.9f;
				ball.ball.y = block.y - 33.0f;
			}
			else if(fMinDist == fDistBottom){
				//Top
				ball.velocityY = ball.velocityY * -0.9f;
				ball.ball.y = block.y + 33.0f;
			}
			hardness --;
			ball.breakBlock.game.wallSound.play(ball.breakBlock.game.volume);
			if(hardness < 1){
				exists = false;
				double random = Math.random();
				if(random < 0.11){
					ball.breakBlock.addNewBall();
				}
				else if(random < 0.15){
					ball.breakBlock.addNewLife();
				}
			}
			
		}
	}
}

