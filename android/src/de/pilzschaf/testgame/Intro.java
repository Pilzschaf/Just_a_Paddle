package de.pilzschaf.testgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

class Intro {
	
	private Game game;
	private Texture introImage;
	private int frame = 0 ;

	void render(){
		Gdx.gl.glClearColor(0.0392f, 0.0784f, 0.106f, 1.0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		game.batch.begin();
		game.batch.draw(introImage, -15.0f, 150.0f);
		game.batch.end();
		frame ++;
		if(frame > 50){
			game.SetGameState(EGameState.GS_MAIN);
		}
	}
	void create(Game game){
		this.game = game;
		introImage = new Texture(Gdx.files.internal("studio.jpg"));
	}
	void destroy(){
		Color color = new Color();
		color.r = 1.0f;
		color.g = 1.0f;
		color.b = 1.0f;
		color.a = 1.0f;
		FreeTypeFontParameter param = new FreeTypeFontParameter();
		param.size = 100;
		param.color = color;
		game.FontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("AHRONBD.TTF"));
		game.Font = game.FontGenerator.generateFont(param);
		param.size = 30;
		game.font2 = game.FontGenerator.generateFont(param);
		game.FontGenerator.dispose();
		game.highscorebb = game.prefs.getInteger("highscorebb", 0);
		game.highscoreee = game.prefs.getInteger("highscoreee", 0);
		game.highscoreem = game.prefs.getInteger("highscoreem", 0);
		game.highscoreen = game.prefs.getInteger("highscoreen", 0);
		game.accacc = game.prefs.getFloat("accacc", 2.7f);
		game.volume = game.prefs.getFloat("volume", 0.72f);
	}
}
