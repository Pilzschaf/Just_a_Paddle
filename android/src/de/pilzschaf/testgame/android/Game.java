package de.pilzschaf.testgame.android;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.I18NBundle;

enum EGameState{
	GS_INTRO,
	GS_MAIN,
	GS_ENDLESS,
	GS_BREAKBLOCK,
	GS_NONE
}

class Game implements ApplicationListener{
	
	private OrthographicCamera camera;
	ShapeRenderer shapeRenderer;
	float accacc = 2.7f;
	BitmapFont Font;
	BitmapFont font2;
	SpriteBatch batch;
	private EGameState state;
	private Intro intro;
	private Endless endless;
	private Menu main;
	private BreakBlock breakBlock;
	FreeTypeFontGenerator FontGenerator;
	ECM ecm;
	Color colors[] = new Color[8];
	int color1id;
	int color2id;
	boolean backpressed = false;
	Integer highscorebb = 0;
	Integer highscoreee = 0;
	Integer highscoreem = 0;
	Integer highscoreen = 0;
	float volume = 0.72f;
	Preferences prefs;
	I18NBundle myBundle;
    boolean isPaused = false;
	
	
	//Method called once when the application is created
	@Override
	public void create(){
		Gdx.input.setCatchBackKey(true);
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 480, 800);
		shapeRenderer = new ShapeRenderer();
		batch = new SpriteBatch();
		
		for(int i = 0; i < 8; i++){
			colors[i] = new Color();
		}
		colors[0].r = 0.0f;
		colors[0].g = 0.0f;
		colors[0].b = 0.0f;
		colors[0].a = 1.0f;
		colors[1].r = 1.0f;
		colors[1].g = 0.0f;
		colors[1].b = 0.0f;
		colors[1].a = 1.0f;
		colors[2].r = 0.0f;
		colors[2].g = 1.0f;
		colors[2].b = 0.0f;
		colors[2].a = 1.0f;
		colors[3].r = 0.0f;
		colors[3].g = 0.0f;
		colors[3].b = 1.0f;
		colors[3].a = 1.0f;
		colors[4].r = 1.0f;
		colors[4].g = 1.0f;
		colors[4].b = 0.0f;
		colors[4].a = 1.0f;
		colors[5].r = 1.0f;
		colors[5].g = 0.0f;
		colors[5].b = 1.0f;
		colors[5].a = 1.0f;
		colors[6].r = 0.0f;
		colors[6].g = 1.0f;
		colors[6].b = 1.0f;
		colors[6].a = 1.0f;
		colors[7].r = 1.0f;
		colors[7].g = 1.0f;
		colors[7].b = 1.0f;
		colors[7].a = 1.0f;
		FileHandle baseFileHandle = Gdx.files.internal("i18n/MyBundle/MyBundle");
		myBundle = I18NBundle.createBundle(baseFileHandle);
		prefs = Gdx.app.getPreferences("highscore.prefs");
		intro = new Intro();
		endless = new Endless();
		breakBlock = new BreakBlock();
		main = new Menu();
		SetGameState(EGameState.GS_INTRO);
		
	}
	//Called after create and on resize
	@Override
	public void render(){
		///////
		//Render
		///////
		Gdx.gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		shapeRenderer.setProjectionMatrix(camera.combined);
		if(state == EGameState.GS_INTRO){
			intro.render();
		}
		else if(state == EGameState.GS_ENDLESS){
			endless.render();
		}
		else if(state == EGameState.GS_MAIN){
			main.render();
		}
		else if(state == EGameState.GS_BREAKBLOCK){
			breakBlock.render();
		}
		if(!Gdx.input.isButtonPressed(Keys.BACK))
			backpressed = false;
		
	}
	//Updates and Renders in this method
	@Override
	public void resize(int width, int height){
		
	}
	//place to save gamestate
	@Override
	public void pause(){
		prefs.putInteger("highscorebb", highscorebb);
		prefs.putInteger("highscoreee", highscoreee);
		prefs.putInteger("highscoreem", highscoreem);
		prefs.putInteger("highscoreen", highscoreen);
		prefs.putFloat("accacc", accacc);
		prefs.putFloat("volume", volume);
		prefs.flush();
        if(state == EGameState.GS_BREAKBLOCK || state == EGameState.GS_ENDLESS)
            isPaused = true;
	}
	//resumes from pause
	@Override
	public void resume(){
		
	}
	//when app gets destroyed
	@Override
	public void dispose(){

    }
	public void SetGameState(EGameState NewState){
		
		if(state == EGameState.GS_INTRO){
            intro.destroy();
        }
        else if(state == EGameState.GS_ENDLESS){
            endless.destroy();
        }
        else if(state == EGameState.GS_MAIN){
            main.destroy();
        }
        else if(state == EGameState.GS_BREAKBLOCK){
            breakBlock.destroy();
        }
		state = NewState;
		if(state == EGameState.GS_INTRO){
			intro.create(this);
		}
		else if(state == EGameState.GS_ENDLESS){
			endless.create(this);
		}
		else if(state == EGameState.GS_MAIN){
			main.create(this);
		}
		else if(state == EGameState.GS_BREAKBLOCK){
			breakBlock.create(this);
		}
	}

}
