package de.justapaddle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;

class BreakBlock {
	
	public Game game;
	private Color color1;
	private Color color2;
	Rectangle paddle;
	private Rectangle liveSlot;
    private int lives;
	int level;
	private Ball balls[] = new Ball[32];
	Block blocks[] = new Block[32];
	
	void create(Game game){
		this.game = game;
		paddle = new Rectangle();
		paddle.x = 480 / 2;
		paddle.y = 30;
		paddle.width = 128;
		paddle.height = 32;
		liveSlot = new Rectangle();
		liveSlot.height = 40.0f;
		liveSlot.width = 68.0f;
		color1 = new Color();
		color2 = new Color();
		color1 = this.game.colors[this.game.color1id];
		color2 = this.game.colors[this.game.color2id];
		lives = 1;
		for(int i = 0; i < 32; i++){
			balls[i] = new Ball();
			blocks[i] = new Block();
		}
		balls[0].create(this);
		level = 1;
		createLevel(level);
	}
	void render(){
        handleInput();
        update();
        Gdx.gl.glClearColor(color1.r, color1.g, color1.b, color1.a);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		game.shapeRenderer.begin(ShapeType.Filled);
		game.shapeRenderer.setColor(color2);
		game.shapeRenderer.rect(paddle.x, paddle.y, paddle.width, paddle.height);
		game.shapeRenderer.rect(0.0f, 740.0f, 480.0f, 60.0f);

		int ballCounter = 0;
		int blockCounter = 0;
		for(int i = 0; i < 32; i++){
			if(blocks[i].exists){
				game.shapeRenderer.rect(blocks[i].block.x, blocks[i].block.y, blocks[i].block.width, blocks[i].block.height);
				blockCounter ++;
			}
		}
		for(int i = 0; i < 32; i++){
			if(balls[i].exists){
                if(!game.isPaused) {
                    balls[i].update();
                }
				game.shapeRenderer.rect(balls[i].ball.x, balls[i].ball.y, balls[i].ball.width, balls[i].ball.height);
				ballCounter ++;
			}
		}
		if(ballCounter < 1){
			if(lives > 0){
				balls[0].create(this);
				lives --;
			}
			else{
                submitHighscore();
				game.SetGameState(EGameState.GS_MAIN);
			}
		}
		if(blockCounter < 1){
			level ++;
            if(level > 10){
                game.playServices.unlockAchievement(R.string.achievement_play_all_unique_break_block_levels);
            }
			createLevel(level);
			for(int i = 0; i < 32; i++){
				if(balls[i].exists){
					balls[i].create(this, i * 20);
				}
			}
		}
        drawLives();
        if(game.isPaused){
            drawPauseButton();
        }
		game.shapeRenderer.end();
        game.batch.begin();
        game.Font.setColor(color1);
        if(level < 10){
            game.Font.draw(game.batch, Integer.toString(level), 220.0f, 810.0f);
        }
        else{
            game.Font.draw(game.batch, Integer.toString(level), 200.0f, 810.0f);
        }
        game.batch.end();
	}

    private void drawLives() {
        game.shapeRenderer.setColor(color1);
        for(int i = 0; i < lives; i++){
            if(i < 2) {
                game.shapeRenderer.rect(10.0f + 78.0f * i, 750.0f, liveSlot.width, liveSlot.height);
            } else {
                game.shapeRenderer.rect(10.0f + 78.0f * (i + 2), 750.0f, liveSlot.width, liveSlot.height);
            }
        }
    }

    private void submitHighscore() {
        if(level > game.highscorebb){
            game.highscorebb = level;
            game.prefs.putInteger("highscorebb", game.highscorebb);
            game.prefs.putInteger("highscoreee", game.highscoreee);
            game.prefs.putInteger("highscoreem", game.highscoreem);
            game.prefs.putInteger("highscoreen", game.highscoreen);
            game.prefs.flush();
        }
        game.playServices.submitScore(R.string.leaderboard_break_block, level);
    }

    private void drawPauseButton() {
        game.shapeRenderer.setColor(color2);
        game.shapeRenderer.rect(190.0f, 350.0f, 100.0f, 100.0f);
        game.shapeRenderer.setColor(color1);
        game.shapeRenderer.rect(205.0f, 365.0f, 25.0f, 70.0f);
        game.shapeRenderer.rect(250.0f, 365.0f, 25.0f, 70.0f);
        game.shapeRenderer.setColor(color2);
    }

    private void update() {
        if(!game.isPaused) {
            paddle.x -= Gdx.input.getAccelerometerX() * game.accacc;
            if (paddle.x > 480.0f - paddle.width)
                paddle.x = 480.0f - paddle.width;
            else if (paddle.x < 0.0f)
                paddle.x = 0.0f;
        }
    }

    private void handleInput() {
        if(Gdx.input.justTouched()){
            if(!game.isPaused){
                game.isPaused = true;
            }
            else if((float)Gdx.input.getX() / Gdx.graphics.getWidth() > 0.4f && (float)Gdx.input.getX() / Gdx.graphics.getWidth() < 0.6f && (float)Gdx.input.getY() / Gdx.graphics.getHeight() > 0.4f && (float)Gdx.input.getY() / Gdx.graphics.getHeight() < 0.6f ){
                game.isPaused = false;
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
    }

    void addNewBall(){
        int temp = 0;
        while(this.balls[temp].exists){
            temp ++;
        }
        this.balls[temp].create(this);
        if(temp >= 4)
            game.playServices.unlockAchievement(R.string.achievement_play_with_5_balls_at_the_same_time);
    }

    void addNewLife(){
        this.lives ++;
        if(this.lives > 4){
            this.lives = 4;
        }
        if(this.lives == 4){
            game.playServices.unlockAchievement(R.string.achievement_play_with_4_extra_lives);
        }
    }

    void destroy(){
		
	}
	void swapColors(){
		Color colorTemp;
		colorTemp = color2;
		color2 = color1;
		color1 = colorTemp;
	}
	private void createLevel(int Level){
		switch (Level){
		case 1:
			createRow("1111", 1);
			createRow("1  1", 2);
			createRow("1  1", 3);
			createRow(" 11 ", 4);
			break;
		case 2:
			createRow("1111", 1);
			createRow("2222", 4);
			break;
		case 3:
			createRow("1111", 1);
			createRow(" 22 ", 2);
			createRow(" 22 ", 3);
			createRow("1111", 4);
			break;
		case 4:
			createRow(" 3 3", 1);
			createRow("3 3 ", 2);
			createRow(" 3 3", 3);
			createRow("3 3 ", 4);
			break;
		case 5:
			createRow("2222", 1);
			createRow("2222", 2);
			createRow("2222", 3);
			createRow("2222", 4);
			break;
		case 6:
			createRow("4444", 1);
			createRow("3333", 2);
			createRow("2222", 3);
			createRow("1111", 4);
			break;
        case 7:
            createRow("3333", 1);
            createRow("    ", 2);
            createRow("5555", 3);
            createRow("    ", 4);
            createRow("    ", 5);
            createRow("5555", 6);
            break;
		case 8:
			createRow("3  3", 1);
			createRow(" 33 ", 2);
			createRow(" 33 ", 3);
			createRow("3  3", 4);
			break;
		case 9:
			createRow("5555", 1);
			createRow("5115", 2);
			createRow("5115", 3);
			createRow("5555", 4);
			break;
		case 10:
			createRow("5  5", 1);
			createRow("5  5", 2);
			createRow("5  5", 3);
			createRow("5  5", 4);
			break;
		default:
			createRow("55555", 1);
			createRow("55555", 2);
			createRow("55555", 3);
			createRow("55555", 4);
			break;
		}
	}
	private void createRow(String str, int numRow){
		float posX = 16.0f;
		for(int i = 0; i < 4; i++){
			if(str.charAt(i) != ' '){
				for(int j = 0; j < 32; j ++){
					if(!blocks[j].exists){
						String st = "" + str.charAt(i);
						blocks[j].create(posX, 740.0f - numRow * 50.0f, Integer.parseInt(st));
						break;
					}
				}
			}
			posX += 116.0f;
		}
	}
}
