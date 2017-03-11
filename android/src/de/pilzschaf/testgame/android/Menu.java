package de.pilzschaf.testgame.android;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;

enum MenuState{
	MS_ROOT,
	MS_ROOTENDLESS,
	MS_ROOTBREAKBLOCK,
	MS_ENDLESS,
	MS_BREAKBLOCK,
	MS_ROOTOPTIONS,
	MS_OPTIONS,
	MS_COLORPICKERENDLESS,
	MS_ROOTHIGHSCORE,
	MS_HIGHSCORE
}
enum ECM{
	ECM_LIGHT,
	ECM_NORMAL,
	ECM_HARD
}

class Menu {
	
	private Game game;
	private MenuState menuState;
	private Rectangle ball1;
	private Rectangle ball2;
	private Rectangle ball3;
	private Rectangle ball4;
	private Rectangle paddle;
	private float endlessX;
	private float endlessY;
	private float optionX;
	private float optionY;
	private float breakblockX;
	private float breakblockY;
	private float highscoreX;
	private float highscoreY;
	private long Frame;
	private int color1id = -1;
	private int color2id = -1;
	private Rectangle sensityrect;
	private Rectangle volumerect;
	private boolean wasVolumeTouchedLastFrame;

	void render(){
		paddle.x -= Gdx.input.getAccelerometerX() * game.accacc;
		if(paddle.x > 480.0f - paddle.width)
			paddle.x = 480.0f - paddle.width;
		else if(paddle.x < 0.0f)
			paddle.x = 0.0f;
		if(menuState == MenuState.MS_ROOT){
			endlessX = 100.0f;
			endlessY = 400.0f;
			optionX = 100.0f;
			optionY = 300.0f;
			breakblockX = 100.0f;
			breakblockY = 500.0f;
			highscoreX = 100.0f;
			highscoreY = 200.0f;
			ball1.x = -32.0f;
			ball1.y = breakblockY;
			ball2.x = -32.0f;
			ball2.y = endlessY;
			ball3.x = -32.0f;
			ball3.y = optionY;
			ball4.x = -32.0f;
			ball4.y = highscoreY;
			
			if(Gdx.input.isTouched()){
				if((float)Gdx.input.getX() / Gdx.graphics.getWidth() > 0.15f && (float)Gdx.input.getX() / Gdx.graphics.getWidth() < 0.75f && (float)Gdx.input.getY() / Gdx.graphics.getHeight() > 0.35f && (float)Gdx.input.getY() / Gdx.graphics.getHeight() < 0.42f ){
					menuState = MenuState.MS_ROOTBREAKBLOCK;
					Frame = 0;
				}
				else if((float)Gdx.input.getX() / Gdx.graphics.getWidth() > 0.15f && (float)Gdx.input.getX() / Gdx.graphics.getWidth() < 0.75f && (float)Gdx.input.getY() / Gdx.graphics.getHeight() > 0.47f && (float)Gdx.input.getY() / Gdx.graphics.getHeight() < 0.55f ){
					menuState = MenuState.MS_ROOTENDLESS;
					Frame = 0;
				}
				else if((float)Gdx.input.getX() / Gdx.graphics.getWidth() > 0.15f && (float)Gdx.input.getX() / Gdx.graphics.getWidth() < 0.75f && (float)Gdx.input.getY() / Gdx.graphics.getHeight() > 0.6f && (float)Gdx.input.getY() / Gdx.graphics.getHeight() < 0.68f ){
					menuState = MenuState.MS_ROOTOPTIONS;
					Frame = 0;
				}
				else if((float)Gdx.input.getX() / Gdx.graphics.getWidth() > 0.15f && (float)Gdx.input.getX() / Gdx.graphics.getWidth() < 0.75f && (float)Gdx.input.getY() / Gdx.graphics.getHeight() > 0.73f && (float)Gdx.input.getY() / Gdx.graphics.getHeight() < 0.81f ){
					menuState = MenuState.MS_ROOTHIGHSCORE;
					Frame = 0;
				}
			}
		}
		else if(menuState == MenuState.MS_ROOTENDLESS){
			endlessX = 100.0f;
			endlessY = 400.0f;
			optionX = 100.0f;
			optionY = 300.0f;
			breakblockX = 100.0f;
			breakblockY = 500.0f;
			highscoreX = 100.0f;
			highscoreY = 200.0f;
			Frame ++;
			ball1.x = -32.0f + Frame * 20.0f;
			ball1.y = breakblockY - 30.0f;
			ball2.x = -32.0f;
			ball2.y = endlessY - 30.0f;
			ball3.x = -32.0f + Frame * 20.0f;
			ball3.y = optionY - 30.0f;
			ball4.x = -32.0f + Frame * 20.0f;
			ball4.y = highscoreY - 30.0f;
			if(Frame > 4){
				optionX = 100.0f + 20.0f * (Frame - 5.0f);
				breakblockX = 100.0f + 20.0f * (Frame - 5.0f);
				highscoreX = 100.0f + 20.0f * (Frame - 5.0f);
			}
			if(Frame > 30){
				endlessY = 400.0f + 10.0f * (Frame - 30.0f);
				if(endlessY > 740.0f)
					endlessY = 740.0f;
			}
			if(Frame > 80.0f){
				menuState = MenuState.MS_ENDLESS;
				Frame = 0;
			}
		}
		else if(menuState == MenuState.MS_ROOTBREAKBLOCK){
			endlessX = 100.0f;
			endlessY = 400.0f;
			optionX = 100.0f;
			optionY = 300.0f;
			breakblockX = 100.0f;
			breakblockY = 500.0f;
			highscoreX = 100.0f;
			highscoreY = 200.0f;
			Frame ++;
			ball2.x = -32.0f + Frame * 20.0f;
			ball1.y = breakblockY - 30.0f;
			ball1.x = -32.0f;
			ball2.y = endlessY - 30.0f;
			ball3.x = -32.0f + Frame * 20.0f;
			ball3.y = optionY - 30.0f;
			ball4.x = -32.0f + Frame * 20.0f;
			ball4.y = highscoreY - 30.0f;
			if(Frame > 4){
				optionX = 100.0f + 20.0f * (Frame - 5.0f);
				endlessX = 100.0f + 20.0f * (Frame - 5.0f);
				highscoreX = 100.0f + 20.0f * (Frame - 5.0f);
			}
			if(Frame > 30){
				breakblockY = 500.0f + 10.0f * (Frame - 30.0f);
				if(breakblockY > 740.0f)
					breakblockY = 740.0f;
			}
			if(Frame > 80.0f){
				menuState = MenuState.MS_BREAKBLOCK;
				Frame = 0;
			}
		}
		else if(menuState == MenuState.MS_ROOTOPTIONS){
			endlessX = 100.0f;
			endlessY = 400.0f;
			optionX = 100.0f;
			optionY = 300.0f;
			breakblockX = 100.0f;
			breakblockY = 500.0f;
			highscoreX = 100.0f;
			highscoreY = 200.0f;
			Frame ++;
			ball1.x = -32.0f + Frame * 20.0f;
			ball1.y = breakblockY - 30.0f;
			ball3.x = -32.0f;
			ball2.y = endlessY - 30.0f;
			ball2.x = -32.0f + Frame * 20.0f;
			ball3.y = optionY - 30.0f;
			ball4.x = -32.0f + Frame * 20.0f;
			ball4.y = highscoreY - 30.0f;
			if(Frame > 4){
				endlessX = 100.0f + 20.0f * (Frame - 5.0f);
				breakblockX = 100.0f + 20.0f * (Frame - 5.0f);
				highscoreX = 100.0f + 20.0f * (Frame - 5.0f);
			}
			if(Frame > 30){
				optionY = 300.0f + 10.0f * (Frame - 30.0f);
				if(optionY > 740.0f)
					optionY = 740.0f;
			}
			if(Frame > 80.0f){
				menuState = MenuState.MS_OPTIONS;
				Frame = 0;
			}
		}
		else if(menuState == MenuState.MS_ROOTHIGHSCORE){
			endlessX = 100.0f;
			endlessY = 400.0f;
			optionX = 100.0f;
			optionY = 300.0f;
			breakblockX = 100.0f;
			breakblockY = 500.0f;
			highscoreX = 100.0f;
			highscoreY = 200.0f;
			Frame ++;
			ball1.x = -32.0f + Frame * 20.0f;
			ball1.y = breakblockY - 30.0f;
			ball4.x = -32.0f;
			ball2.y = endlessY - 30.0f;
			ball2.x = -32.0f + Frame * 20.0f;
			ball3.y = optionY - 30.0f;
			ball3.x = -32.0f + Frame * 20.0f;
			ball4.y = highscoreY - 30.0f;
			if(Frame > 4){
				endlessX = 100.0f + 20.0f * (Frame - 5.0f);
				breakblockX = 100.0f + 20.0f * (Frame - 5.0f);
				optionX = 100.0f + 20.0f * (Frame - 5.0f);
			}
			if(Frame > 30){
				highscoreY = 200.0f + 12.0f * (Frame - 30.0f);
				if(highscoreY > 740.0f)
					highscoreY = 740.0f;
			}
			if(Frame > 80.0f){
				menuState = MenuState.MS_HIGHSCORE;
				Frame = 0;
			}
		}
		else if(menuState == MenuState.MS_ENDLESS){
			endlessX = 100.0f;
			endlessY = 740.0f;
			optionX = 1000.0f;
			optionY = 300.0f;
			highscoreX = 1000.0f;
			highscoreY = 200.0f;
			breakblockX = 1000.0f;
			breakblockY = 500.0f;
			ball1.x = -32.0f;
			ball1.y = breakblockY;
			ball2.x = -32.0f;
			ball2.y = endlessY;
			ball3.x = -32.0f;
			ball3.y = optionY;
			if(Gdx.input.justTouched()){
				if((float)Gdx.input.getX() / Gdx.graphics.getWidth() > 0.15f && (float)Gdx.input.getX() / Gdx.graphics.getWidth() < 0.75f && (float)Gdx.input.getY() / Gdx.graphics.getHeight() > 0.35f && (float)Gdx.input.getY() / Gdx.graphics.getHeight() < 0.42f ){
					menuState = MenuState.MS_COLORPICKERENDLESS;
					Frame = 0;
					game.ecm = ECM.ECM_LIGHT;
				}
				else if((float)Gdx.input.getX() / Gdx.graphics.getWidth() > 0.15f && (float)Gdx.input.getX() / Gdx.graphics.getWidth() < 0.75f && (float)Gdx.input.getY() / Gdx.graphics.getHeight() > 0.47f && (float)Gdx.input.getY() / Gdx.graphics.getHeight() < 0.55f ){
					Frame = 0;
					game.ecm = ECM.ECM_NORMAL;
					game.SetGameState(EGameState.GS_ENDLESS);
				}
				else if((float)Gdx.input.getX() / Gdx.graphics.getWidth() > 0.15f && (float)Gdx.input.getX() / Gdx.graphics.getWidth() < 0.75f && (float)Gdx.input.getY() / Gdx.graphics.getHeight() > 0.6f && (float)Gdx.input.getY() / Gdx.graphics.getHeight() < 0.68f ){
					Frame = 0;
					game.SetGameState(EGameState.GS_ENDLESS);
					game.ecm = ECM.ECM_HARD;
				}
			}
			if(Gdx.input.isKeyPressed(Keys.BACK)&& !game.backpressed){
				menuState = MenuState.MS_ROOT;
				game.backpressed = true;
			}
		}
		else if(menuState == MenuState.MS_COLORPICKERENDLESS){
			endlessX = 1000.0f;
			endlessY = 740.0f;
			optionX = 1000.0f;
			optionY = 300.0f;
			highscoreX = 1000.0f;
			highscoreY = 200.0f;
			breakblockX = 1000.0f;
			breakblockY = 500.0f;
			ball1.x = -32.0f;
			ball1.y = breakblockY;
			ball2.x = -32.0f;
			ball2.y = endlessY;
			ball3.x = -32.0f;
			ball3.y = optionY;
			if(Gdx.input.isKeyPressed(Keys.BACK) && !game.backpressed){
				menuState = MenuState.MS_ROOT;
				game.backpressed = true;
			}
			if(Gdx.input.justTouched()){
				if((float)Gdx.input.getX() / Gdx.graphics.getWidth() > 0.1f && (float)Gdx.input.getX() / Gdx.graphics.getWidth() < 0.28f 	   && (float)Gdx.input.getY() / Gdx.graphics.getHeight() > 0.4f && (float)Gdx.input.getY() / Gdx.graphics.getHeight() < 0.5f){
					if(color2id != 0)
						color1id = 0;
				}
				else if((float)Gdx.input.getX() / Gdx.graphics.getWidth() > 0.28f && (float)Gdx.input.getX() / Gdx.graphics.getWidth() < 0.46f && (float)Gdx.input.getY() / Gdx.graphics.getHeight() > 0.4f && (float)Gdx.input.getY() / Gdx.graphics.getHeight() < 0.5f){
					if(color2id != 1)
						color1id = 1;
				}
				else if((float)Gdx.input.getX() / Gdx.graphics.getWidth() > 0.46f && (float)Gdx.input.getX() / Gdx.graphics.getWidth() < 0.64f && (float)Gdx.input.getY() / Gdx.graphics.getHeight() > 0.4f && (float)Gdx.input.getY() / Gdx.graphics.getHeight() < 0.5f){
					if(color2id != 2)
						color1id = 2;
				}
				else if((float)Gdx.input.getX() / Gdx.graphics.getWidth() > 0.64f && (float)Gdx.input.getX() / Gdx.graphics.getWidth() < 0.82f && (float)Gdx.input.getY() / Gdx.graphics.getHeight() > 0.4f && (float)Gdx.input.getY() / Gdx.graphics.getHeight() < 0.5f){
					if(color2id != 3)
						color1id = 3;
				}
				if((float)Gdx.input.getX() / Gdx.graphics.getWidth() > 0.1f && (float)Gdx.input.getX() / Gdx.graphics.getWidth() < 0.28f && (float)Gdx.input.getY() 	   / Gdx.graphics.getHeight() > 0.3f && (float)Gdx.input.getY() / Gdx.graphics.getHeight() < 0.4f){
					if(color2id != 4)
						color1id = 4;
				}
				else if((float)Gdx.input.getX() / Gdx.graphics.getWidth() > 0.28f && (float)Gdx.input.getX() / Gdx.graphics.getWidth() < 0.46f && (float)Gdx.input.getY() / Gdx.graphics.getHeight() > 0.3f && (float)Gdx.input.getY() / Gdx.graphics.getHeight() < 0.4f){
					if(color2id != 5)
						color1id = 5;
				}
				else if((float)Gdx.input.getX() / Gdx.graphics.getWidth() > 0.46f && (float)Gdx.input.getX() / Gdx.graphics.getWidth() < 0.64f && (float)Gdx.input.getY() / Gdx.graphics.getHeight() > 0.3f && (float)Gdx.input.getY() / Gdx.graphics.getHeight() < 0.4f){
					if(color2id != 6)
						color1id = 6;
				}
				else if((float)Gdx.input.getX() / Gdx.graphics.getWidth() > 0.64f && (float)Gdx.input.getX() / Gdx.graphics.getWidth() < 0.82f && (float)Gdx.input.getY() / Gdx.graphics.getHeight() > 0.3f && (float)Gdx.input.getY() / Gdx.graphics.getHeight() < 0.4f){
					if(color2id != 7)
						color1id = 7;
				}
				
				
				if((float)Gdx.input.getX() / Gdx.graphics.getWidth() > 0.1f && (float)Gdx.input.getX() / Gdx.graphics.getWidth() < 0.28f 	   && (float)Gdx.input.getY() / Gdx.graphics.getHeight() > 0.67f && (float)Gdx.input.getY() / Gdx.graphics.getHeight() < 0.77f){
					if(color1id != 0)
						color2id = 0;
				}
				else if((float)Gdx.input.getX() / Gdx.graphics.getWidth() > 0.28f && (float)Gdx.input.getX() / Gdx.graphics.getWidth() < 0.46f && (float)Gdx.input.getY() / Gdx.graphics.getHeight() > 0.67f && (float)Gdx.input.getY() / Gdx.graphics.getHeight() < 0.77f){
					if(color1id != 1)
						color2id = 1;
				}
				else if((float)Gdx.input.getX() / Gdx.graphics.getWidth() > 0.46f && (float)Gdx.input.getX() / Gdx.graphics.getWidth() < 0.64f && (float)Gdx.input.getY() / Gdx.graphics.getHeight() > 0.67f && (float)Gdx.input.getY() / Gdx.graphics.getHeight() < 0.77f){
					if(color1id != 2)
						color2id = 2;
				}
				else if((float)Gdx.input.getX() / Gdx.graphics.getWidth() > 0.64f && (float)Gdx.input.getX() / Gdx.graphics.getWidth() < 0.82f && (float)Gdx.input.getY() / Gdx.graphics.getHeight() > 0.67f && (float)Gdx.input.getY() / Gdx.graphics.getHeight() < 0.77f){
					if(color1id != 3)
						color2id = 3;
				}
				if((float)Gdx.input.getX() / Gdx.graphics.getWidth() > 0.1f && (float)Gdx.input.getX() / Gdx.graphics.getWidth() < 0.28f && (float)Gdx.input.getY() 	   / Gdx.graphics.getHeight() > 0.57f && (float)Gdx.input.getY() / Gdx.graphics.getHeight() < 0.67f){
					if(color1id != 4)
						color2id = 4;
				}
				else if((float)Gdx.input.getX() / Gdx.graphics.getWidth() > 0.28f && (float)Gdx.input.getX() / Gdx.graphics.getWidth() < 0.46f && (float)Gdx.input.getY() / Gdx.graphics.getHeight() > 0.57f && (float)Gdx.input.getY() / Gdx.graphics.getHeight() < 0.67f){
					if(color1id != 5)
						color2id = 5;
				}
				else if((float)Gdx.input.getX() / Gdx.graphics.getWidth() > 0.46f && (float)Gdx.input.getX() / Gdx.graphics.getWidth() < 0.64f && (float)Gdx.input.getY() / Gdx.graphics.getHeight() > 0.57f && (float)Gdx.input.getY() / Gdx.graphics.getHeight() < 0.67f){
					if(color1id != 6)
						color2id = 6;
				}
				else if((float)Gdx.input.getX() / Gdx.graphics.getWidth() > 0.64f && (float)Gdx.input.getX() / Gdx.graphics.getWidth() < 0.82f && (float)Gdx.input.getY() / Gdx.graphics.getHeight() > 0.57f && (float)Gdx.input.getY() / Gdx.graphics.getHeight() < 0.67f){
					if(color1id != 7)
						color2id = 7;
				}
			}
			if(color1id != -1 && color2id != -1){
				game.color1id = color1id;
				game.color2id = color2id;
				game.SetGameState(EGameState.GS_ENDLESS);
				color1id = -1;
				color2id = -1;
				Frame = 0;
			}
		}
		else if(menuState == MenuState.MS_BREAKBLOCK){
			if(Gdx.input.isKeyPressed(Keys.BACK) && !game.backpressed){
				menuState = MenuState.MS_ROOT;
				game.backpressed = true;
			}
			endlessX = 1000.0f;
			endlessY = 740.0f;
			optionX = 1000.0f;
			optionY = 300.0f;
			highscoreX = 1000.0f;
			highscoreY = 200.0f;
			breakblockX = 1000.0f;
			breakblockY = 500.0f;
			ball1.x = -32.0f;
			ball1.y = breakblockY;
			ball2.x = -32.0f;
			ball2.y = endlessY;
			ball3.x = -32.0f;
			ball3.y = optionY;
			if(Gdx.input.justTouched()){
				if((float)Gdx.input.getX() / Gdx.graphics.getWidth() > 0.1f && (float)Gdx.input.getX() / Gdx.graphics.getWidth() < 0.28f 	   && (float)Gdx.input.getY() / Gdx.graphics.getHeight() > 0.4f && (float)Gdx.input.getY() / Gdx.graphics.getHeight() < 0.5f){
					if(color2id != 0)
						color1id = 0;
				}
				else if((float)Gdx.input.getX() / Gdx.graphics.getWidth() > 0.28f && (float)Gdx.input.getX() / Gdx.graphics.getWidth() < 0.46f && (float)Gdx.input.getY() / Gdx.graphics.getHeight() > 0.4f && (float)Gdx.input.getY() / Gdx.graphics.getHeight() < 0.5f){
					if(color2id != 1)
						color1id = 1;
				}
				else if((float)Gdx.input.getX() / Gdx.graphics.getWidth() > 0.46f && (float)Gdx.input.getX() / Gdx.graphics.getWidth() < 0.64f && (float)Gdx.input.getY() / Gdx.graphics.getHeight() > 0.4f && (float)Gdx.input.getY() / Gdx.graphics.getHeight() < 0.5f){
					if(color2id != 2)
						color1id = 2;
				}
				else if((float)Gdx.input.getX() / Gdx.graphics.getWidth() > 0.64f && (float)Gdx.input.getX() / Gdx.graphics.getWidth() < 0.82f && (float)Gdx.input.getY() / Gdx.graphics.getHeight() > 0.4f && (float)Gdx.input.getY() / Gdx.graphics.getHeight() < 0.5f){
					if(color2id != 3)
						color1id = 3;
				}
				if((float)Gdx.input.getX() / Gdx.graphics.getWidth() > 0.1f && (float)Gdx.input.getX() / Gdx.graphics.getWidth() < 0.28f && (float)Gdx.input.getY() 	   / Gdx.graphics.getHeight() > 0.3f && (float)Gdx.input.getY() / Gdx.graphics.getHeight() < 0.4f){
					if(color2id != 4)
						color1id = 4;
				}
				else if((float)Gdx.input.getX() / Gdx.graphics.getWidth() > 0.28f && (float)Gdx.input.getX() / Gdx.graphics.getWidth() < 0.46f && (float)Gdx.input.getY() / Gdx.graphics.getHeight() > 0.3f && (float)Gdx.input.getY() / Gdx.graphics.getHeight() < 0.4f){
					if(color2id != 5)
						color1id = 5;
				}
				else if((float)Gdx.input.getX() / Gdx.graphics.getWidth() > 0.46f && (float)Gdx.input.getX() / Gdx.graphics.getWidth() < 0.64f && (float)Gdx.input.getY() / Gdx.graphics.getHeight() > 0.3f && (float)Gdx.input.getY() / Gdx.graphics.getHeight() < 0.4f){
					if(color2id != 6)
						color1id = 6;
				}
				else if((float)Gdx.input.getX() / Gdx.graphics.getWidth() > 0.64f && (float)Gdx.input.getX() / Gdx.graphics.getWidth() < 0.82f && (float)Gdx.input.getY() / Gdx.graphics.getHeight() > 0.3f && (float)Gdx.input.getY() / Gdx.graphics.getHeight() < 0.4f){
					if(color2id != 7)
						color1id = 7;
				}
				
				
				if((float)Gdx.input.getX() / Gdx.graphics.getWidth() > 0.1f && (float)Gdx.input.getX() / Gdx.graphics.getWidth() < 0.28f 	   && (float)Gdx.input.getY() / Gdx.graphics.getHeight() > 0.67f && (float)Gdx.input.getY() / Gdx.graphics.getHeight() < 0.77f){
					if(color1id != 0)
						color2id = 0;
				}
				else if((float)Gdx.input.getX() / Gdx.graphics.getWidth() > 0.28f && (float)Gdx.input.getX() / Gdx.graphics.getWidth() < 0.46f && (float)Gdx.input.getY() / Gdx.graphics.getHeight() > 0.67f && (float)Gdx.input.getY() / Gdx.graphics.getHeight() < 0.77f){
					if(color1id != 1)
						color2id = 1;
				}
				else if((float)Gdx.input.getX() / Gdx.graphics.getWidth() > 0.46f && (float)Gdx.input.getX() / Gdx.graphics.getWidth() < 0.64f && (float)Gdx.input.getY() / Gdx.graphics.getHeight() > 0.67f && (float)Gdx.input.getY() / Gdx.graphics.getHeight() < 0.77f){
					if(color1id != 2)
						color2id = 2;
				}
				else if((float)Gdx.input.getX() / Gdx.graphics.getWidth() > 0.64f && (float)Gdx.input.getX() / Gdx.graphics.getWidth() < 0.82f && (float)Gdx.input.getY() / Gdx.graphics.getHeight() > 0.67f && (float)Gdx.input.getY() / Gdx.graphics.getHeight() < 0.77f){
					if(color1id != 3)
						color2id = 3;
				}
				if((float)Gdx.input.getX() / Gdx.graphics.getWidth() > 0.1f && (float)Gdx.input.getX() / Gdx.graphics.getWidth() < 0.28f && (float)Gdx.input.getY() 	   / Gdx.graphics.getHeight() > 0.57f && (float)Gdx.input.getY() / Gdx.graphics.getHeight() < 0.67f){
					if(color1id != 4)
						color2id = 4;
				}
				else if((float)Gdx.input.getX() / Gdx.graphics.getWidth() > 0.28f && (float)Gdx.input.getX() / Gdx.graphics.getWidth() < 0.46f && (float)Gdx.input.getY() / Gdx.graphics.getHeight() > 0.57f && (float)Gdx.input.getY() / Gdx.graphics.getHeight() < 0.67f){
					if(color1id != 5)
						color2id = 5;
				}
				else if((float)Gdx.input.getX() / Gdx.graphics.getWidth() > 0.46f && (float)Gdx.input.getX() / Gdx.graphics.getWidth() < 0.64f && (float)Gdx.input.getY() / Gdx.graphics.getHeight() > 0.57f && (float)Gdx.input.getY() / Gdx.graphics.getHeight() < 0.67f){
					if(color1id != 6)
						color2id = 6;
				}
				else if((float)Gdx.input.getX() / Gdx.graphics.getWidth() > 0.64f && (float)Gdx.input.getX() / Gdx.graphics.getWidth() < 0.82f && (float)Gdx.input.getY() / Gdx.graphics.getHeight() > 0.57f && (float)Gdx.input.getY() / Gdx.graphics.getHeight() < 0.67f){
					if(color1id != 7)
						color2id = 7;
				}
			}
			if(color1id != -1 && color2id != -1){
				game.color1id = color1id;
				game.color2id = color2id;
				game.SetGameState(EGameState.GS_BREAKBLOCK);
				color1id = -1;
				color2id = -1;
				Frame = 0;
			}
		}
		else if(menuState == MenuState.MS_HIGHSCORE){
			endlessX = 1000.0f;
			endlessY = 740.0f;
			optionX = 1000.0f;
			optionY = 300.0f;
			highscoreX = 100.0f;
			highscoreY = 740.0f;
			breakblockX = 1000.0f;
			breakblockY = 500.0f;
			ball1.x = -32.0f;
			ball1.y = breakblockY;
			ball2.x = -32.0f;
			ball2.y = endlessY;
			ball3.x = -32.0f;
			ball3.y = optionY;
			ball4.x = -32.0f;
			ball4.y = highscoreY;
			if(Gdx.input.isKeyPressed(Keys.BACK)&& !game.backpressed){
				menuState = MenuState.MS_ROOT;
				game.backpressed = true;
			}
		}
		else if(menuState == MenuState.MS_OPTIONS){
			sensityrect.x = 30.0f + game.accacc * 85.0f;
			sensityrect.y = 600.0f - 50.0f;
			volumerect.x = 30.0f + game.volume * Gdx.graphics.getWidth() * 0.37f;
			volumerect.y = 450.0f - 50.0f;
			sensityrect.height = 32.0f;
			sensityrect.width = 32.0f;
			volumerect.height= 32.0f;
			volumerect.width = 32.0f;
			endlessX = 1000.0f;
			endlessY = 740.0f;
			optionX = 100.0f;
			optionY = 740.0f;
			highscoreX = 1000.0f;
			highscoreY = 740.0f;
			breakblockX = 1000.0f;
			breakblockY = 500.0f;
			ball1.x = -32.0f;
			ball1.y = breakblockY;
			ball2.x = -32.0f;
			ball2.y = endlessY;
			ball3.x = -32.0f;
			ball3.y = optionY;
			ball4.x = -32.0f;
			ball4.y = highscoreY;
			if(Gdx.input.isKeyPressed(Keys.BACK)&& !game.backpressed){
				menuState = MenuState.MS_ROOT;
				game.backpressed = true;
			}
			if(Gdx.input.isTouched()){
				if((float)Gdx.input.getX()/Gdx.graphics.getWidth() > 0.05f && (float)Gdx.input.getX()/Gdx.graphics.getWidth() < 0.95f && (float)Gdx.input.getY()/Gdx.graphics.getHeight() < 0.39f && (float)Gdx.input.getY()/Gdx.graphics.getHeight() > 0.23f){
					game.accacc = ((float)Gdx.input.getX()/Gdx.graphics.getWidth()-0.05f)*5.0f;
                    if(this.wasVolumeTouchedLastFrame){
                        this.wasVolumeTouchedLastFrame = false;
                        game.wallSound.play(game.volume);
                    }
				}
				else if((float)Gdx.input.getX()/Gdx.graphics.getWidth() > 0.05f && (float)Gdx.input.getX()/Gdx.graphics.getWidth() < 0.95f && (float)Gdx.input.getY()/Gdx.graphics.getHeight() < 0.57f && (float)Gdx.input.getY()/Gdx.graphics.getHeight() > 0.43f){
					game.volume = ((float)Gdx.input.getX()/Gdx.graphics.getWidth()-0.05f)*1.1f;
                    wasVolumeTouchedLastFrame = true;
				} else if(this.wasVolumeTouchedLastFrame){
                    this.wasVolumeTouchedLastFrame = false;
                    game.wallSound.play(game.volume);
                }

			} else if(this.wasVolumeTouchedLastFrame){
                this.wasVolumeTouchedLastFrame = false;
                game.wallSound.play(game.volume);
            }


            if(game.volume > 1.0f)
                game.volume = 1.0f;
		}
		Gdx.gl.glClearColor(1.0f, 0.0f, 0.0f, 1.0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		game.shapeRenderer.begin(ShapeType.Filled);
		game.shapeRenderer.setColor(1.0f, 1.0f, 1.0f, 1.0f);
		game.shapeRenderer.rect(endlessX - 10.0f, endlessY - 30.0f, 256.0f, 40.0f);
		game.shapeRenderer.rect(optionX - 10.0f, optionY - 30.0f, 256.0f, 40.0f);
		game.shapeRenderer.rect(breakblockX - 10.0f, breakblockY - 30.0f, 256.0f, 40.0f);
		game.shapeRenderer.rect(highscoreX - 10.0f, highscoreY - 30.0f, 256.0f, 40.0f);
		game.shapeRenderer.end();
		game.batch.begin();
		game.font2.setColor(1.0f, 0.0f, 0.0f, 1.0f);
		game.font2.draw(game.batch, game.myBundle.format("endless"), endlessX, endlessY);
		game.font2.draw(game.batch, game.myBundle.format("options"), optionX, optionY);
		game.font2.draw(game.batch, game.myBundle.format("breakblock"), breakblockX, breakblockY);
		game.font2.draw(game.batch, game.myBundle.format("highscores"), highscoreX, highscoreY);
		game.batch.end();
		game.shapeRenderer.begin(ShapeType.Filled);
		game.shapeRenderer.setColor(1.0f, 1.0f, 1.0f, 1.0f);
		game.shapeRenderer.rect(ball1.x, ball1.y, ball1.width, ball1.height);
		game.shapeRenderer.rect(ball2.x, ball2.y, ball2.width, ball2.height);
		game.shapeRenderer.rect(ball3.x, ball3.y, ball3.width, ball3.height);
		game.shapeRenderer.rect(ball4.x, ball4.y, ball4.width, ball4.height);
		game.shapeRenderer.rect(paddle.x, paddle.y, paddle.width, paddle.height);
		game.shapeRenderer.end();
		if(menuState == MenuState.MS_ENDLESS){
			game.shapeRenderer.begin(ShapeType.Filled);
			game.shapeRenderer.setColor(1.0f, 1.0f, 1.0f, 1.0f);
			game.shapeRenderer.rect(50.0f, 600.0f - 30.0f, 296.0f, 40.0f);
			game.shapeRenderer.rect(90.0f, 500.0f - 30.0f, 256.0f, 40.0f);
			game.shapeRenderer.rect(90.0f, 400.0f - 30.0f, 256.0f, 40.0f);
			game.shapeRenderer.rect(90.0f, 300.0f - 30.0f, 256.0f, 40.0f);
			game.shapeRenderer.end();
			game.batch.begin();
			game.font2.setColor(1.0f, 0.0f, 0.0f, 1.0f);
			game.font2.draw(game.batch, game.myBundle.format("selectEyeCancerMode"), 60.0f, 600.0f);
			game.font2.draw(game.batch, game.myBundle.format("light"), 100.0f, 500.0f);
			game.font2.draw(game.batch, game.myBundle.format("normal"), 100.0f, 400.0f);
			game.font2.draw(game.batch, game.myBundle.format("nightmare"), 100.0f, 300.0f);
			game.batch.end();
		}
		else if(menuState == MenuState.MS_HIGHSCORE){
			game.shapeRenderer.begin(ShapeType.Filled);
			game.shapeRenderer.setColor(1.0f, 1.0f, 1.0f, 1.0f);
			game.shapeRenderer.rect(50.0f, 600.0f - 30.0f, 380.0f, 40.0f);
			game.shapeRenderer.rect(50.0f, 500.0f - 30.0f, 380.0f, 40.0f);
			game.shapeRenderer.rect(50.0f, 400.0f - 30.0f, 380.0f, 40.0f);
			game.shapeRenderer.rect(50.0f, 300.0f - 30.0f, 380.0f, 40.0f);
			game.shapeRenderer.end();
			game.batch.begin();
			game.font2.draw(game.batch, game.myBundle.format("breakblock")+":   " + game.highscorebb.toString(), 60.0f, 600.0f);
			game.font2.draw(game.batch, game.myBundle.format("endless")+" " + game.myBundle.format("light")+":   " + game.highscoreee.toString(), 60.0f, 500.0f);
			game.font2.draw(game.batch, game.myBundle.format("endless")+" " + game.myBundle.format("normal")+":   " + game.highscoreem.toString(), 60.0f, 400.0f);
			game.font2.draw(game.batch, game.myBundle.format("endless")+" " + game.myBundle.format("nightmare")+":   " + game.highscoreen.toString(), 60.0f, 300.0f);
			game.batch.end();
		}
		else if(menuState == MenuState.MS_OPTIONS){
			game.shapeRenderer.begin(ShapeType.Filled);
			game.shapeRenderer.setColor(1.0f, 1.0f, 1.0f, 1.0f);
			game.shapeRenderer.rect(50.0f, 600.0f, 270.0f, 40.0f);
			game.shapeRenderer.rect(50.0f, 450.0f, 270.0f, 40.0f);
			game.shapeRenderer.rect(50.0f, 600.0f - 40.0f, 380.0f, 10.0f);
			game.shapeRenderer.rect(50.0f, 450.0f - 40.0f, 380.0f, 10.0f);
			game.shapeRenderer.rect(volumerect.x, volumerect.y, volumerect.width, volumerect.height);
			game.shapeRenderer.rect(sensityrect.x, sensityrect.y, sensityrect.width, sensityrect.height);
			game.shapeRenderer.end();
			game.batch.begin();
			game.font2.draw(game.batch, game.myBundle.format("sensity")+":", 60.0f, 630.0f);
			game.font2.draw(game.batch, game.myBundle.format("volume")+":", 60.0f, 480.0f);
			game.batch.end();
		}
		else if(menuState == MenuState.MS_COLORPICKERENDLESS || menuState == MenuState.MS_BREAKBLOCK){
			game.shapeRenderer.begin(ShapeType.Filled);
			game.shapeRenderer.setColor(1.0f, 1.0f, 1.0f, 1.0f);
			game.shapeRenderer.rect(90.0f, 700.0f - 30.0f, 256.0f, 40.0f);
			game.shapeRenderer.rect(50.0f, 580.0f - 30.0f, 256.0f, 40.0f);
			game.shapeRenderer.rect(50.0f, 380.0f - 30.0f, 256.0f, 40.0f);
			game.shapeRenderer.end();
			game.batch.begin();
			game.font2.draw(game.batch, game.myBundle.format("pickColors")+":", 100.0f, 700.0f);
			game.font2.draw(game.batch, game.myBundle.format("color1")+":", 60.0f, 580.0f);
			game.font2.draw(game.batch, game.myBundle.format("color2")+":", 60.0f, 380.0f);
			game.batch.end();
			for(int i = 0; i < 8; i++){
				game.shapeRenderer.begin(ShapeType.Filled);
				game.shapeRenderer.setColor(game.colors[i]);
				game.shapeRenderer.rect( 50.0f + 80.0f * ((i)%4), 400.0f + 80.0f * ((i)/4), 64.0f, 64.0f);
				game.shapeRenderer.end();
                //Umrandungen
				game.shapeRenderer.begin(ShapeType.Line);
				if(color1id == i){
					game.shapeRenderer.setColor(1.0f, 1.0f, 1.0f, 1.0f);
				}
				else {
					game.shapeRenderer.setColor(0.0f, 0.0f, 0.0f, 1.0f);
				}
				game.shapeRenderer.rect( 50.0f + 80.0f * ((i)%4), 400.0f + 80.0f * ((i)/4), 64.0f, 64.0f);
				game.shapeRenderer.end();
			}
			for(int i = 0; i < 8; i++){
				game.shapeRenderer.begin(ShapeType.Filled);
				game.shapeRenderer.setColor(game.colors[i]);
				game.shapeRenderer.rect( 50.0f + 80.0f * ((i)%4), 200.0f + 80.0f * ((i)/4), 64.0f, 64.0f);
				game.shapeRenderer.end();
				game.shapeRenderer.begin(ShapeType.Line);
				if(color2id == i){
					game.shapeRenderer.setColor(1.0f, 1.0f, 1.0f, 1.0f);
				}
				else {
					game.shapeRenderer.setColor(0.0f, 0.0f, 0.0f, 1.0f);
				}
				game.shapeRenderer.rect( 50.0f + 80.0f * ((i)%4), 200.0f + 80.0f * ((i)/4), 64.0f, 64.0f);
				game.shapeRenderer.end();
			}
			
		}
	}
	void create(Game pgame){
        wasVolumeTouchedLastFrame = false;
		game = pgame;
		ball1 = new Rectangle();
		ball2 = new Rectangle();
		ball3 = new Rectangle();
		ball4 = new Rectangle();
		paddle = new Rectangle();
		menuState = MenuState.MS_ROOT;
		ball1.width = 32.0f;
		ball1.height = 32.0f;
		ball2.width = 32.0f;
		ball2.height = 32.0f;
		ball3.width = 32.0f;
		ball3.height = 32.0f;
		ball4.width = 32.0f;
		ball4.height = 32.0f;
		paddle.height = 32.0f;
		paddle.width = 128.0f;
		paddle.x = 200.0f;
		paddle.y = 30.0f;
		sensityrect = new Rectangle();
		volumerect = new Rectangle();
		sensityrect.x = 30.0f + game.accacc * 100.0f;
		sensityrect.y = 600.0f - 50.0f;
		volumerect.x = 30.0f + game.volume * 340.0f;
		volumerect.y = 450.0f - 50.0f;
		sensityrect.height = 32.0f;
		sensityrect.width = 32.0f;
		volumerect.height= 32.0f;
		volumerect.width = 32.0f;
	}
	void destroy(){
		
	}
}
