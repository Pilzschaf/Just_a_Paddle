package de.pilzschaf.testgame.android;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;

public class Breakblock {
	
	public Game game;
	public Color color1;
	public Color color2;
	public Rectangle paddle;
	private Rectangle liveslot;
	public Sound wallsound;
	public int lives;
	public int level;
	public Ball balls[] = new Ball[32];
	public Block blocks[] = new Block[32];
	
	public void create(Game pgame){
		game = pgame;
		paddle = new Rectangle();
		paddle.x = 480 / 2;
		paddle.y = 30;
		paddle.width = 128;
		paddle.height = 32;
		liveslot = new Rectangle();
		liveslot.height = 40.0f;
		liveslot.width = 68.0f;
		color1 = new Color();
		color2 = new Color();
		color1 = game.colors[game.color1id];
		color2 = game.colors[game.color2id];
		lives = 3;
		for(int i = 0; i < 32; i++){
			balls[i] = new Ball();
			blocks[i] = new Block();
		}
		balls[0].create(this);
		level = 1;
		CreateLevel(level);
		wallsound = Gdx.audio.newSound(Gdx.files.internal("wallsound.wav"));
	}
	public void render(){
        if(Gdx.input.justTouched()){
            if(!game.bPaused){
                game.bPaused = true;
            }
            else if((float)Gdx.input.getX() / Gdx.graphics.getWidth() > 0.4f && (float)Gdx.input.getX() / Gdx.graphics.getWidth() < 0.6f && (float)Gdx.input.getY() / Gdx.graphics.getHeight() > 0.4f && (float)Gdx.input.getY() / Gdx.graphics.getHeight() < 0.6f ){
                game.bPaused = false;
            }
        }
        if (Gdx.input.isKeyPressed(Keys.BACK)) {
            if (level > game.highscorebb) {
                game.highscorebb = level;
                game.prefs.putInteger("highscorebb", game.highscorebb);
                game.prefs.flush();
            }
            game.backpressed = true;
            game.SetGameState(EGameState.GS_MAIN);
        }
        if(!game.bPaused) {
            if (lives > 6)
                lives = 6;
            paddle.x -= Gdx.input.getAccelerometerX() * game.accacc;
            if (paddle.x > 480.0f - paddle.width)
                paddle.x = 480.0f - paddle.width;
            else if (paddle.x < 0.0f)
                paddle.x = 0.0f;
        }
		Gdx.gl.glClearColor(color1.r, color1.g, color1.b, color1.a);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		game.shapeRenderer.begin(ShapeType.Filled);
		game.shapeRenderer.setColor(color2);
		game.shapeRenderer.rect(paddle.x, paddle.y, paddle.width, paddle.height);
		game.shapeRenderer.rect(0.0f, 740.0f, 480.0f, 60.0f);
        if(game.bPaused){
            game.shapeRenderer.rect(190.0f, 350.0f, 100.0f, 100.0f);
            game.shapeRenderer.setColor(color1);
            game.shapeRenderer.rect(205.0f, 365.0f, 25.0f, 70.0f);
            game.shapeRenderer.rect(250.0f, 365.0f, 25.0f, 70.0f);
            game.shapeRenderer.setColor(color2);
        }
		int ballcounter = 0;
		int blockcounter = 0;
		for(int i = 0; i < 32; i++){
			if(blocks[i].exists){
				game.shapeRenderer.rect(blocks[i].block.x, blocks[i].block.y, blocks[i].block.width, blocks[i].block.height);
				blockcounter ++;
			}
		}
		for(int i = 0; i < 32; i++){
			if(balls[i].exists){
                if(!game.bPaused) {
                    balls[i].update();
                }
				game.shapeRenderer.rect(balls[i].ball.x, balls[i].ball.y, balls[i].ball.width, balls[i].ball.height);
				ballcounter ++;
			}
		}
		if(ballcounter < 1){
			if(lives > 0){
				balls[0].create(this);
				lives --;
			}
			else{
				if(level > game.highscorebb){
					game.highscorebb = level;
					game.prefs.putInteger("highscorebb", game.highscorebb);
					game.prefs.putInteger("highscoreee", game.highscoreee);
					game.prefs.putInteger("highscoreem", game.highscoreem);
					game.prefs.putInteger("highscoreen", game.highscoreen);
					game.prefs.flush();
				}
				game.SetGameState(EGameState.GS_MAIN);
			}
		}
		if(blockcounter < 1){
			level ++;
			CreateLevel(level);
			for(int i = 0; i < 32; i++){
				if(balls[i].exists){
					balls[i].create(this, i * 20);
				}
			}
		}
		game.shapeRenderer.setColor(color1);
		for(int i = 0; i < lives; i++){
			game.shapeRenderer.rect(10.0f + 78.0f * i, 750.0f, liveslot.width, liveslot.height);
		}
		game.shapeRenderer.end();
	}
	public void destroy(){
		
	}
	public void ChangeColor(){
		Color colortemp = new Color();
		colortemp = color2;
		color2 = color1;
		color1 = colortemp;
	}
	public void CreateLevel(int Level){
		switch (Level){
		case 1:
			CreateRow("1111", 1);
			CreateRow("1  1", 2);
			CreateRow("1  1", 3);
			CreateRow(" 11 ", 4);
			break;
		case 2:
			CreateRow("1111", 1);
			CreateRow("2222", 4);
			break;
		case 3:
			CreateRow("1111", 1);
			CreateRow(" 22 ", 2);
			CreateRow(" 22 ", 3);
			CreateRow("1111", 4);
			break;
		case 4:
			CreateRow(" 3 3", 1);
			CreateRow("3 3 ", 2);
			CreateRow(" 3 3", 3);
			CreateRow("3 3 ", 4);
			break;
		case 5:
			CreateRow("2222", 1);
			CreateRow("2222", 2);
			CreateRow("2222", 3);
			CreateRow("2222", 4);
			break;
		case 6:
			CreateRow("4444", 1);
			CreateRow("3333", 2);
			CreateRow("2222", 3);
			CreateRow("1111", 4);
			break;
		case 7:
			CreateRow("3  3", 1);
			CreateRow(" 33 ", 2);
			CreateRow(" 33 ", 3);
			CreateRow("3  3", 4);
			break;
		case 8:
			CreateRow("5555", 1);
			CreateRow("5115", 2);
			CreateRow("5115", 3);
			CreateRow("5555", 4);
			break;
		case 9:
			CreateRow("5555", 1);
			CreateRow("5555", 4);
			break;
		case 10:
			CreateRow("5  5", 1);
			CreateRow("5  5", 2);
			CreateRow("5  5", 3);
			CreateRow("5  5", 4);
			break;
		default:
			CreateRow("55555", 1);
			CreateRow("55555", 2);
			CreateRow("55555", 3);
			CreateRow("55555", 4);
			break;
		}
	}
	public void CreateRow(String str, int numrow){
		float posx = 16.0f;
		for(int i = 0; i < 4; i++){
			if(str.charAt(i) != ' '){
				for(int j = 0; j < 32; j ++){
					if(!blocks[j].exists){
						String st = "" + str.charAt(i);
						blocks[j].create(posx, 740.0f - numrow * 50.0f, Integer.parseInt(st));
						break;
					}
				}
			}
			posx += 116.0f;
		}
	}
}
