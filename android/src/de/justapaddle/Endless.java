package de.justapaddle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;

class Endless {
	
	private Color color1;
	private Color color2;
	private Rectangle paddle;
	private Rectangle ball;
	private float ballDirectionX;
	private float ballDirectionY;
	private Integer points = 0;
	private Game game;
	private float time = 0.0f;
    private float volume = 1.0f;
    private Sound wallSound;

    void create(Game game){
        this.game = game;
        points = 0;
        paddle = new Rectangle();
        paddle.x = 480 / 2;
        paddle.y = 30;
        paddle.width = 128;
        paddle.height = 32;
        ball = new Rectangle();
        ball.x = 480/2;
        ball.y = 700;
        ball.width = 32;
        ball.height = 32;
        ballDirectionX = (float)Math.random() * 4.0f * ballDirectionX;
        ballDirectionY = -6.0f;
        prepareColors();
        this.volume = this.game.volume;
        this.wallSound = game.wallSound;
    }

    private void prepareColors() {
        color1 = new Color();
        color2 = new Color();
        if(this.game.ecm == ECM.ECM_LIGHT){
            color1 = this.game.colors[this.game.color1id];
            color2 = this.game.colors[this.game.color2id];
        }
        else{
            changeColor();
        }
    }

    void render() {
        move();
        Gdx.gl.glClearColor(color1.r, color1.g, color1.b, color1.a);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		game.shapeRenderer.begin(ShapeType.Filled);
		game.shapeRenderer.setColor(color2);
		game.shapeRenderer.rect(paddle.x, paddle.y, paddle.width, paddle.height);
		game.shapeRenderer.rect(ball.x, ball.y, ball.width, ball.height);
		game.shapeRenderer.rect(0.0f, 740.0f, 480.0f, 60.0f);
        if(game.isPaused){
            game.shapeRenderer.rect(190.0f, 350.0f, 100.0f, 100.0f);
            game.shapeRenderer.setColor(color1);
            game.shapeRenderer.rect(205.0f, 365.0f, 25.0f, 70.0f);
            game.shapeRenderer.rect(250.0f, 365.0f, 25.0f, 70.0f);
        }
		game.shapeRenderer.end();
		game.batch.begin();
		game.Font.setColor(color1);
		if(points < 10){
			game.Font.draw(game.batch, points.toString(), 220.0f, 810.0f);
		}
		else{
			game.Font.draw(game.batch, points.toString(), 200.0f, 810.0f);
		}
		game.batch.end();
	}

    private void move() {
        if(Gdx.input.justTouched()){
            if(!game.isPaused){
                game.isPaused = true;
            }
            else if((float)Gdx.input.getX() / Gdx.graphics.getWidth() > 0.4f && (float)Gdx.input.getX() / Gdx.graphics.getWidth() < 0.6f && (float)Gdx.input.getY() / Gdx.graphics.getHeight() > 0.4f && (float)Gdx.input.getY() / Gdx.graphics.getHeight() < 0.6f ){
                game.isPaused = false;
            }
        }
        if(!game.isPaused) {
            if (game.ecm == ECM.ECM_HARD) {
                time += Gdx.graphics.getDeltaTime();
                if (time > 0.17f) {
                    time -= 0.17f;
                    changeColor();
                }

            }
            paddle.x -= Gdx.input.getAccelerometerX() * game.accacc;
            if (paddle.x > 480.0f - paddle.width)
                paddle.x = 480.0f - paddle.width;
            else if (paddle.x < 0.0f)
                paddle.x = 0.0f;

            if (ballDirectionY > 20.0f)
                ballDirectionY = 20.0f;
            ball.x += ballDirectionX;
            ball.y += ballDirectionY;

            if (ball.x > 480.0f - ball.width) {
                    ball.x = 480.0f - ball.width;
                ballDirectionX = ballDirectionX * -1.0f;
                changeColor();
                playWallSound();
            } else if (ball.x < 0.0f) {
                ball.x = 0.0f;
                ballDirectionX = ballDirectionX * -1.0f;
                changeColor();
                playWallSound();
            }
            if (ball.y > 740.0f - ball.height) {
                ball.y = 740.0f - ball.height;
                ballDirectionY = ballDirectionY * -1.0f;
                points++;
                ballDirectionX = (float) Math.random() * 14.0f - 7.0f;
                changeColor();
                playWallSound();
            } else if (ball.y < paddle.y + paddle.height) {
                if (ball.x <= paddle.x + paddle.width && ball.x >= paddle.x - ball.width && ball.y > paddle.y) {
                    ball.y = paddle.y + paddle.height;
                    ballDirectionY = ballDirectionY * -1.1f;
                    if (paddle.x != 0.0f && paddle.x != 480.0f - paddle.width) {
                        ballDirectionX = ballDirectionX + Gdx.input.getAccelerometerX() * game.accacc * 0.5f;
                    }
                    changeColor();
                    playWallSound();
                }
                if (ball.y < 0.0f) {
                    if (game.ecm == ECM.ECM_LIGHT) {
                        if (points > game.highscoreee) {
                            game.highscoreee = points;
                            game.prefs.putInteger("highscorebb", game.highscorebb);
                            game.prefs.putInteger("highscoreee", game.highscoreee);
                            game.prefs.putInteger("highscoreem", game.highscoreem);
                            game.prefs.putInteger("highscoreen", game.highscoreen);
                            game.prefs.flush();
                        }
                    }
                    if (game.ecm == ECM.ECM_NORMAL) {
                        if (points > game.highscoreem) {
                            game.highscoreem = points;
                            game.prefs.putInteger("highscorebb", game.highscorebb);
                            game.prefs.putInteger("highscoreee", game.highscoreee);
                            game.prefs.putInteger("highscoreem", game.highscoreem);
                            game.prefs.putInteger("highscoreen", game.highscoreen);
                            game.prefs.flush();
                        }
                    }
                    if (game.ecm == ECM.ECM_HARD) {
                        if (points > game.highscoreen) {

                            game.highscoreen = points;
                            game.prefs.putInteger("highscorebb", game.highscorebb);
                            game.prefs.putInteger("highscoreee", game.highscoreee);
                            game.prefs.putInteger("highscoreem", game.highscoreem);
                            game.prefs.putInteger("highscoreen", game.highscoreen);
                            game.prefs.flush();
                        }
                    }
                    ballDirectionY = ballDirectionY * -1.0f;
                    ballDirectionX = (float) Math.random() * 8.0f - 4.0f;
                    ballDirectionY = -4.0f;
                    ball.x = 480 / 2;
                    ball.y = 700;
                    points = 0;
                }

            }
        }
        if(Gdx.input.isKeyPressed(Keys.BACK)){
            if(game.ecm == ECM.ECM_LIGHT){
                if(points > game.highscoreee){
                    game.highscoreee = points;
                    game.prefs.putInteger("highscorebb", game.highscorebb);
                    game.prefs.putInteger("highscoreee", game.highscoreee);
                    game.prefs.putInteger("highscoreem", game.highscoreem);
                    game.prefs.putInteger("highscoreen", game.highscoreen);
                    game.prefs.flush();
                }
            }
            if(game.ecm == ECM.ECM_NORMAL){
                if(points > game.highscoreem){
                    game.highscoreem = points;
                    game.prefs.putInteger("highscorebb", game.highscorebb);
                    game.prefs.putInteger("highscoreee", game.highscoreee);
                    game.prefs.putInteger("highscoreem", game.highscoreem);
                    game.prefs.putInteger("highscoreen", game.highscoreen);
                    game.prefs.flush();
                }
            }
            if(game.ecm == ECM.ECM_HARD){
                if(points > game.highscoreen){

                    game.highscoreen = points;
                    game.prefs.putInteger("highscorebb", game.highscorebb);
                    game.prefs.putInteger("highscoreee", game.highscoreee);
                    game.prefs.putInteger("highscoreem", game.highscoreem);
                    game.prefs.putInteger("highscoreen", game.highscoreen);
                    game.prefs.flush();
                }
            }
            game.backpressed = true;
            points = 0;
            game.SetGameState(EGameState.GS_MAIN);

        }
    }

    private void playWallSound() {
        wallSound.play(this.volume);
    }

    void destroy(){
        points = 0;
        paddle = new Rectangle();
        paddle.x = 480 / 2;
        paddle.y = 30;
        paddle.width = 128;
        paddle.height = 32;
        ball = new Rectangle();
        ball.x = 480/2;
        ball.y = 700;
        ball.width = 32;
        ball.height = 32;
        ballDirectionX = (float)Math.random() * 4.0f * ballDirectionX;
        ballDirectionY = -6.0f;
        color1 = new Color();
        color2 = new Color();
        if(game.ecm == ECM.ECM_LIGHT){
            color1 = game.colors[game.color1id];
            color2 = game.colors[game.color2id];
        }
	}
	private void changeColor(){
		if(game.ecm == ECM.ECM_LIGHT){
			Color colorTemp;
			colorTemp = color2;
			color2 = color1;
			color1 = colorTemp;
		}
		else if(game.ecm == ECM.ECM_NORMAL){
			int rand = (int) (Math.random() * 10);
			color1 = game.colors[rand];
			int rand2 = (int) (Math.random() * 10);
			while (rand2 == rand){
				rand2 = (int) (Math.random() * 10);
			}
			color2 = game.colors[rand2];
		}
		else if(game.ecm == ECM.ECM_HARD){
            color1.r = (float) Math.random();
            color1.g = (float) Math.random();
            color1.b = (float) Math.random();
            color1.a = 1.0f;
            color2.r = (float) Math.random();
            color2.g = (float) Math.random();
            color2.b = (float) Math.random();
            color2.a = 1.0f;
        }
	}
}
