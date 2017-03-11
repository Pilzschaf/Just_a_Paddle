package de.pilzschaf.testgame.android;

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
		if(ball.ball.x + 32.0f >= block.x && ball.ball.x <= block.x + 100.0f && ball.ball.y + 32.0f > block.y && ball.ball.y < block.y + 32.0f){
			ball.breakBlock.swapColors();
			float fDistLeft = Math.abs(ball.ball.x + 32.0f) - block.x;		//Stimmt
			float fDistRight = Math.abs(ball.ball.x) - block.x + 100.0f;	//Stimmt
			float fDistTop = Math.abs(ball.ball.y + 32.0f) - block.y;		//Stimmt
			float fDistBottom = Math.abs(ball.ball.y) - block.y + 32.0f;	//Stimmt
			float fMinDist = Math.min(Math.min(fDistLeft, fDistTop), Math.min(fDistRight, fDistBottom));
			if(fMinDist == fDistLeft){
				ball.velocityX = ball.velocityX * -0.8f;
				ball.ball.x = block.x - 32.0f;
				System.out.println("Left");
			}
			else if(fMinDist == fDistRight){
				ball.velocityX = ball.velocityX * -0.8f;
				ball.ball.x = block.x + 132.0f;
				System.out.println("Right");
			}
			else if(fMinDist == fDistTop){
				//Bottom
				ball.velocityY = ball.velocityY * -1.0f;		
				ball.ball.y = block.y - 32.0f;
				System.out.println("Top");
			}
			else if(fMinDist == fDistBottom){
				//Top
				ball.velocityY = ball.velocityY * -1.0f;
				ball.ball.y = block.y + 32.0f;
				System.out.println("Bottom");
			}
			hardness --;
			ball.breakBlock.game.wallSound.play(ball.breakBlock.game.volume);
			if(hardness < 1){
				exists = false;
				double random = Math.random();
				if(random < 0.1){
					int temp = 0;
					while(ball.breakBlock.balls[temp].exists){
						temp ++;
					}
					ball.breakBlock.balls[temp].create(ball.breakBlock);
				}
				else if(random < 0.14){
					ball.breakBlock.lives ++;
				}
			}
			
		}
	}
}

