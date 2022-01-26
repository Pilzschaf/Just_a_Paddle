package de.pilzschaf.testgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;

import de.pilzschaf.testgame.android.R;

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
enum EyeCancerMode {
	ECM_LIGHT,
	ECM_NORMAL,
	ECM_HARD
}

class Menu {
	
	private Game game;
	private MenuState menuState = MenuState.MS_ROOT;
	private Rectangle[] balls = new Rectangle[5];
	private Rectangle paddle = new Rectangle();
	private float endlessX;
	private float endlessY;
	private float optionX;
	private float optionY;
	private float breakBlockX;
	private float breakBlockY;
	private float highscoreX;
	private float highscoreY;
    private float achievementsX;
    private float achievementsY;
	private long Frame;
	private int color1id = -1;
	private int color2id = -1;
	private Rectangle sensitivityRect = new Rectangle();
	private Rectangle volumeRect = new Rectangle();
	private boolean wasVolumeTouchedLastFrame = false;

    void create(Game game){
        this.game = game;
        initBalls();
        paddle.height = 32.0f;
        paddle.width = 128.0f;
        paddle.x = 200.0f;
        paddle.y = 30.0f;
        sensitivityRect.height = 32.0f;
        sensitivityRect.width = 32.0f;
        volumeRect.height= 32.0f;
        volumeRect.width = 32.0f;
    }

    private void initBalls() {
        for(int i = 0; i < balls.length; i++){
            balls[i] = new Rectangle();
        }
        balls[0].width = 32.0f;
        balls[0].height = 32.0f;
        balls[1].width = 32.0f;
        balls[1].height = 32.0f;
        balls[2].width = 32.0f;
        balls[2].height = 32.0f;
        balls[3].width = 32.0f;
        balls[3].height = 32.0f;
        balls[4].height = 32.0f;
        balls[4].width = 32.0f;
        balls[0].x = -32.0f;
        balls[0].y = breakBlockY;
        balls[1].x = -32.0f;
        balls[1].y = endlessY;
        balls[2].x = -32.0f;
        balls[2].y = optionY;
        balls[3].x = -32.0f;
        balls[3].y = highscoreY;
        balls[4].x = -32.0f;
        balls[4].y = achievementsY;
    }

    void destroy(){

    }

	void render(){
        update();
        draw();
	}

    private void update() {
        updatePaddle();
        updateSelectedMenu();
    }

    private void updatePaddle() {
        paddle.x -= Gdx.input.getAccelerometerX() * game.accacc;
        if(paddle.x > 480.0f - paddle.width)
            paddle.x = 480.0f - paddle.width;
        else if(paddle.x < 0.0f)
            paddle.x = 0.0f;
    }

    private void updateSelectedMenu() {
        if(menuState == MenuState.MS_ROOT){
            updateRoot();
        }
        else if(menuState == MenuState.MS_ROOTENDLESS){
            updateEndlessTransition();
        }
        else if(menuState == MenuState.MS_ROOTBREAKBLOCK){
            updateBreakBlockTransition();
        }
        else if(menuState == MenuState.MS_ROOTOPTIONS){
            updateOptionsTransition();
        }
        else if(menuState == MenuState.MS_ROOTHIGHSCORE){
            updateHighscoreTransition();
        }
        else if(menuState == MenuState.MS_ENDLESS){
            updateEndless();
        }
        else if(menuState == MenuState.MS_COLORPICKERENDLESS || menuState == MenuState.MS_BREAKBLOCK){
            updateColorPicker();
        }
        else if(menuState == MenuState.MS_HIGHSCORE){
            updateHighscore();
        }
        else if(menuState == MenuState.MS_OPTIONS){
            updateOptions();
        }
    }

    private void draw() {
        Gdx.gl.glClearColor(1.0f, 0.0f, 0.0f, 1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        drawSelectedMenu();
    }

    private void drawSelectedMenu() {
        if(menuState == MenuState.MS_ENDLESS){
            drawEndless();
        }
        else if(menuState == MenuState.MS_HIGHSCORE){
            drawHighscore();
        }
        else if(menuState == MenuState.MS_OPTIONS){
            drawOptions();
        }
        else if(menuState == MenuState.MS_COLORPICKERENDLESS || menuState == MenuState.MS_BREAKBLOCK){
            drawColorPicker();
        }
        else {
            drawDefaultElements();
        }
    }

    private void drawDefaultElements() {
        game.shapeRenderer.begin(ShapeType.Filled);
        game.shapeRenderer.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        game.shapeRenderer.rect(endlessX - 10.0f, endlessY - 30.0f, 256.0f, 40.0f);
        game.shapeRenderer.rect(optionX - 10.0f, optionY - 30.0f, 256.0f, 40.0f);
        game.shapeRenderer.rect(breakBlockX - 10.0f, breakBlockY - 30.0f, 256.0f, 40.0f);
        game.shapeRenderer.rect(highscoreX - 10.0f, highscoreY - 30.0f, 256.0f, 40.0f);
        game.shapeRenderer.rect(achievementsX - 10.0f, achievementsY - 30.0f, 256.0f, 40.0f);
        game.shapeRenderer.end();
        game.batch.begin();
        game.font2.setColor(1.0f, 0.0f, 0.0f, 1.0f);
        game.font2.draw(game.batch, game.myBundle.format("endless"), endlessX, endlessY);
        game.font2.draw(game.batch, game.myBundle.format("options"), optionX, optionY);
        game.font2.draw(game.batch, game.myBundle.format("breakblock"), breakBlockX, breakBlockY);
        game.font2.draw(game.batch, game.myBundle.format("highscores"), highscoreX, highscoreY);
        game.font2.draw(game.batch, game.myBundle.format("achievements"), achievementsX, achievementsY);
        game.batch.end();
        game.shapeRenderer.begin(ShapeType.Filled);
        game.shapeRenderer.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        for (Rectangle ball : balls) {
            game.shapeRenderer.rect(ball.x, ball.y, ball.width, ball.height);
        }
        game.shapeRenderer.rect(paddle.x, paddle.y, paddle.width, paddle.height);
        game.shapeRenderer.end();
    }

    private void drawHighscore() {
        game.shapeRenderer.begin(ShapeType.Filled);
        game.shapeRenderer.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        game.shapeRenderer.rect(50.0f, 600.0f - 30.0f, 380.0f, 40.0f);
        game.shapeRenderer.rect(50.0f, 500.0f - 30.0f, 380.0f, 40.0f);
        game.shapeRenderer.rect(50.0f, 400.0f - 30.0f, 380.0f, 40.0f);
        game.shapeRenderer.rect(50.0f, 300.0f - 30.0f, 380.0f, 40.0f);
        game.shapeRenderer.end();
        game.batch.begin();
        game.font2.draw(game.batch, game.myBundle.format("breakblock")+":   " + ""/*game.highscorebb.toString()*/, 60.0f, 600.0f);
        game.font2.draw(game.batch, game.myBundle.format("endless")+" " + game.myBundle.format("easy")+":   " + ""/*game.highscoreee.toString()*/, 60.0f, 500.0f);
        game.font2.draw(game.batch, game.myBundle.format("endless")+" " + game.myBundle.format("normal")+":   " + ""/*game.highscoreem.toString()*/, 60.0f, 400.0f);
        game.font2.draw(game.batch, game.myBundle.format("endless")+" " + game.myBundle.format("nightmare")+":   " + ""/*game.highscoreen.toString()*/, 60.0f, 300.0f);
        game.batch.end();
    }

    private void drawEndless() {
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
        game.font2.draw(game.batch, game.myBundle.format("easy"), 100.0f, 500.0f);
        game.font2.draw(game.batch, game.myBundle.format("normal"), 100.0f, 400.0f);
        game.font2.draw(game.batch, game.myBundle.format("nightmare"), 100.0f, 300.0f);
        game.batch.end();
    }

    private void updateRoot() {
        game.resetColors();
        breakBlockX = 100.0f;
        breakBlockY = 600.0f;
        endlessX = 100.0f;
        endlessY = 500.0f;
        optionX = 100.0f;
        optionY = 400.0f;
        highscoreX = 100.0f;
        highscoreY = 300.0f;
        achievementsX = 100.0f;
        achievementsY = 200.0f;

        if(Gdx.input.isTouched()){
            if((float)Gdx.input.getX() / Gdx.graphics.getWidth() > 0.15f && (float)Gdx.input.getX() / Gdx.graphics.getWidth() < 0.75f && (float)Gdx.input.getY() / Gdx.graphics.getHeight() > 0.22f && (float)Gdx.input.getY() / Gdx.graphics.getHeight() < 0.29f ){
                menuState = MenuState.MS_ROOTBREAKBLOCK;
                Frame = 0;
            }
            else if((float)Gdx.input.getX() / Gdx.graphics.getWidth() > 0.15f && (float)Gdx.input.getX() / Gdx.graphics.getWidth() < 0.75f && (float)Gdx.input.getY() / Gdx.graphics.getHeight() > 0.35f && (float)Gdx.input.getY() / Gdx.graphics.getHeight() < 0.42f ){
                menuState = MenuState.MS_ROOTENDLESS;
                Frame = 0;
            }
            else if((float)Gdx.input.getX() / Gdx.graphics.getWidth() > 0.15f && (float)Gdx.input.getX() / Gdx.graphics.getWidth() < 0.75f && (float)Gdx.input.getY() / Gdx.graphics.getHeight() > 0.47f && (float)Gdx.input.getY() / Gdx.graphics.getHeight() < 0.55f ){
                menuState = MenuState.MS_ROOTOPTIONS;
                Frame = 0;
            }
            else if((float)Gdx.input.getX() / Gdx.graphics.getWidth() > 0.15f && (float)Gdx.input.getX() / Gdx.graphics.getWidth() < 0.75f && (float)Gdx.input.getY() / Gdx.graphics.getHeight() > 0.6f && (float)Gdx.input.getY() / Gdx.graphics.getHeight() < 0.68f ){
                menuState = MenuState.MS_ROOTHIGHSCORE;
                Frame = 0;
            }
            else if((float)Gdx.input.getX() / Gdx.graphics.getWidth() > 0.15f && (float)Gdx.input.getX() / Gdx.graphics.getWidth() < 0.75f && (float)Gdx.input.getY() / Gdx.graphics.getHeight() > 0.73f && (float)Gdx.input.getY() / Gdx.graphics.getHeight() < 0.81f ){
                game.playServices.showAchievements();
            }
        }
    }

    private void updateEndless() {
        endlessX = 100.0f;
        endlessY = 740.0f;
        optionX = 1000.0f;
        optionY = 400.0f;
        highscoreX = 1000.0f;
        highscoreY = 300.0f;
        breakBlockX = 1000.0f;
        breakBlockY = 500.0f;
        achievementsX = 1000.0f;
        achievementsY = 200.0f;
        if(Gdx.input.justTouched()){
            if((float)Gdx.input.getX() / Gdx.graphics.getWidth() > 0.15f && (float)Gdx.input.getX() / Gdx.graphics.getWidth() < 0.75f && (float)Gdx.input.getY() / Gdx.graphics.getHeight() > 0.35f && (float)Gdx.input.getY() / Gdx.graphics.getHeight() < 0.42f ){
                menuState = MenuState.MS_COLORPICKERENDLESS;
                Frame = 0;
                game.eyeCancerMode = EyeCancerMode.ECM_LIGHT;
            }
            else if((float)Gdx.input.getX() / Gdx.graphics.getWidth() > 0.15f && (float)Gdx.input.getX() / Gdx.graphics.getWidth() < 0.75f && (float)Gdx.input.getY() / Gdx.graphics.getHeight() > 0.47f && (float)Gdx.input.getY() / Gdx.graphics.getHeight() < 0.55f ){
                Frame = 0;
                game.eyeCancerMode = EyeCancerMode.ECM_NORMAL;
                game.SetGameState(EGameState.GS_ENDLESS);
            }
            else if((float)Gdx.input.getX() / Gdx.graphics.getWidth() > 0.15f && (float)Gdx.input.getX() / Gdx.graphics.getWidth() < 0.75f && (float)Gdx.input.getY() / Gdx.graphics.getHeight() > 0.6f && (float)Gdx.input.getY() / Gdx.graphics.getHeight() < 0.68f ){
                Frame = 0;
                game.SetGameState(EGameState.GS_ENDLESS);
                game.eyeCancerMode = EyeCancerMode.ECM_HARD;
            }
        }
        if(Gdx.input.isKeyPressed(Keys.BACK)&& !game.backpressed){
            menuState = MenuState.MS_ROOT;
            game.backpressed = true;
        }
    }

    private void updateEndlessTransition() {
        endlessX = 100.0f;
        endlessY = 500.0f;
        optionX = 100.0f;
        optionY = 400.0f;
        breakBlockX = 100.0f;
        breakBlockY = 600.0f;
        highscoreX = 100.0f;
        highscoreY = 300.0f;
        achievementsX = 100.0f;
        achievementsY = 200.0f;
        Frame ++;
        balls[0].x = -32.0f + Frame * 20.0f;
        balls[0].y = breakBlockY - 30.0f;
        balls[1].x = -32.0f;
        balls[1].y = endlessY - 30.0f;
        balls[2].x = -32.0f + Frame * 20.0f;
        balls[2].y = optionY - 30.0f;
        balls[3].x = -32.0f + Frame * 20.0f;
        balls[3].y = highscoreY - 30.0f;
        balls[4].x = -32.0f + Frame * 20.0f;
        balls[4].y = achievementsY - 30.0f;
        if(Frame > 4){
            optionX = 100.0f + 20.0f * (Frame - 5.0f);
            breakBlockX = 100.0f + 20.0f * (Frame - 5.0f);
            highscoreX = 100.0f + 20.0f * (Frame - 5.0f);
            achievementsX = 100.0f + 20.0f * (Frame - 5.0f);
        }
        if(Frame > 30){
            endlessY = 500.0f + 10.0f * (Frame - 30.0f);
            if(endlessY > 740.0f)
                endlessY = 740.0f;
        }
        if(Frame > 65){
            menuState = MenuState.MS_ENDLESS;
            Frame = 0;
        }
    }

    private void updateBreakBlockTransition() {
        endlessX = 100.0f;
        endlessY = 500.0f;
        optionX = 100.0f;
        optionY = 400.0f;
        breakBlockX = 100.0f;
        breakBlockY = 600.0f;
        highscoreX = 100.0f;
        highscoreY = 300.0f;
        achievementsX = 100.0f;
        achievementsY = 200.0f;
        Frame ++;
        balls[0].x = -32.0f;
        balls[0].y = breakBlockY - 30.0f;
        balls[1].x = -32.0f + Frame * 20.0f;
        balls[1].y = endlessY - 30.0f;
        balls[2].x = -32.0f + Frame * 20.0f;
        balls[2].y = optionY - 30.0f;
        balls[3].x = -32.0f + Frame * 20.0f;
        balls[3].y = highscoreY - 30.0f;
        balls[4].x = -32.0f + Frame * 20.0f;
        balls[4].y = achievementsY - 30.0f;
        if(Frame > 4){
            optionX = 100.0f + 20.0f * (Frame - 5.0f);
            endlessX = 100.0f + 20.0f * (Frame - 5.0f);
            highscoreX = 100.0f + 20.0f * (Frame - 5.0f);
            achievementsX = 100.0f + 20.0f * (Frame -5.0f);
        }
        if(Frame > 30){
            breakBlockY = 600.0f + 10.0f * (Frame - 30.0f);
            if(breakBlockY > 740.0f)
                breakBlockY = 740.0f;
        }
        if(Frame > 65){
            menuState = MenuState.MS_BREAKBLOCK;
            Frame = 0;
        }
    }

    private void updateOptionsTransition() {
        endlessX = 100.0f;
        endlessY = 500.0f;
        optionX = 100.0f;
        optionY = 400.0f;
        breakBlockX = 100.0f;
        breakBlockY = 600.0f;
        highscoreX = 100.0f;
        highscoreY = 300.0f;
        achievementsX = 100.0f;
        achievementsY = 200.0f;
        Frame ++;
        balls[0].x = -32.0f + Frame * 20.0f;
        balls[0].y = breakBlockY - 30.0f;
        balls[1].x = -32.0f + Frame * 20.0f;
        balls[1].y = endlessY - 30.0f;
        balls[2].x = -32.0f;
        balls[2].y = optionY - 30.0f;
        balls[3].x = -32.0f + Frame * 20.0f;
        balls[3].y = highscoreY - 30.0f;
        balls[4].x = -32.0f + Frame * 20.0f;
        balls[4].y = achievementsY - 30.0f;
        if(Frame > 4){
            endlessX = 100.0f + 20.0f * (Frame - 5.0f);
            breakBlockX = 100.0f + 20.0f * (Frame - 5.0f);
            highscoreX = 100.0f + 20.0f * (Frame - 5.0f);
            achievementsX = 100.0f + 20.0f * (Frame - 5.0f);
        }
        if(Frame > 30){
            optionY = 400.0f + 10.0f * (Frame - 30.0f);
            if(optionY > 740.0f)
                optionY = 740.0f;
        }
        if(Frame > 65){
            menuState = MenuState.MS_OPTIONS;
            Frame = 0;
        }
    }

    private void updateHighscoreTransition() {
        endlessX = 100.0f;
        endlessY = 500.0f;
        optionX = 100.0f;
        optionY = 400.0f;
        breakBlockX = 100.0f;
        breakBlockY = 600.0f;
        highscoreX = 100.0f;
        highscoreY = 300.0f;
        achievementsX = 100.0f;
        achievementsY = 200.0f;
        Frame ++;
        balls[0].x = -32.0f + Frame * 20.0f;
        balls[0].y = breakBlockY - 30.0f;
        balls[1].x = -32.0f + Frame * 20.0f;
        balls[1].y = endlessY - 30.0f;
        balls[2].x = -32.0f + Frame * 20.0f;
        balls[2].y = optionY - 30.0f;
        balls[3].x = -32.0f;
        balls[3].y = highscoreY - 30.0f;
        balls[4].x = -32.0f + Frame * 20.0f;
        balls[4].y = achievementsY - 30.0f;
        if(Frame > 4){
            endlessX = 100.0f + 20.0f * (Frame - 5.0f);
            breakBlockX = 100.0f + 20.0f * (Frame - 5.0f);
            optionX = 100.0f + 20.0f * (Frame - 5.0f);
            achievementsX = 100.0f + 20.0f * (Frame - 5.0f);
        }
        if(Frame > 30){
            highscoreY = 300.0f + 12.0f * (Frame - 30.0f);
            if(highscoreY > 740.0f)
                highscoreY = 740.0f;
        }
        if(Frame > 65){
            menuState = MenuState.MS_HIGHSCORE;
            Frame = 0;
        }
    }

    private void updateColorPicker() {
        endlessX = 1000.0f;
        endlessY = 740.0f;
        optionX = 1000.0f;
        optionY = 400.0f;
        highscoreX = 1000.0f;
        highscoreY = 300.0f;
        breakBlockX = 1000.0f;
        breakBlockY = 600.0f;
        achievementsX = 1000.0f;
        achievementsY = 200.0f;
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
            else if((float)Gdx.input.getX() / Gdx.graphics.getWidth() > 0.82f && (float)Gdx.input.getX() / Gdx.graphics.getWidth() < 1.0f && (float)Gdx.input.getY() / Gdx.graphics.getHeight() > 0.4f && (float)Gdx.input.getY() / Gdx.graphics.getHeight() < 0.5f){
            if(color2id != 4)
            color1id = 4;
            }
            if((float)Gdx.input.getX() / Gdx.graphics.getWidth() > 0.1f && (float)Gdx.input.getX() / Gdx.graphics.getWidth() < 0.28f && (float)Gdx.input.getY() 	   / Gdx.graphics.getHeight() > 0.3f && (float)Gdx.input.getY() / Gdx.graphics.getHeight() < 0.4f){
                if(color2id != 5)
                    color1id = 5;
            }
            else if((float)Gdx.input.getX() / Gdx.graphics.getWidth() > 0.28f && (float)Gdx.input.getX() / Gdx.graphics.getWidth() < 0.46f && (float)Gdx.input.getY() / Gdx.graphics.getHeight() > 0.3f && (float)Gdx.input.getY() / Gdx.graphics.getHeight() < 0.4f){
                if(color2id != 6)
                    color1id = 6;
            }
            else if((float)Gdx.input.getX() / Gdx.graphics.getWidth() > 0.46f && (float)Gdx.input.getX() / Gdx.graphics.getWidth() < 0.64f && (float)Gdx.input.getY() / Gdx.graphics.getHeight() > 0.3f && (float)Gdx.input.getY() / Gdx.graphics.getHeight() < 0.4f){
                if(color2id != 7)
                    color1id = 7;
            }
            else if((float)Gdx.input.getX() / Gdx.graphics.getWidth() > 0.64f && (float)Gdx.input.getX() / Gdx.graphics.getWidth() < 0.82f && (float)Gdx.input.getY() / Gdx.graphics.getHeight() > 0.3f && (float)Gdx.input.getY() / Gdx.graphics.getHeight() < 0.4f){
                if(color2id != 8)
                    color1id = 8;
            }
            else if((float)Gdx.input.getX() / Gdx.graphics.getWidth() > 0.82f && (float)Gdx.input.getX() / Gdx.graphics.getWidth() < 1.0f && (float)Gdx.input.getY() / Gdx.graphics.getHeight() > 0.3f && (float)Gdx.input.getY() / Gdx.graphics.getHeight() < 0.4f){
            if(color2id != 9)
            color1id = 9;
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
            else if((float)Gdx.input.getX() / Gdx.graphics.getWidth() > 0.82f && (float)Gdx.input.getX() / Gdx.graphics.getWidth() < 1.0f && (float)Gdx.input.getY() / Gdx.graphics.getHeight() > 0.67f && (float)Gdx.input.getY() / Gdx.graphics.getHeight() < 0.77f){
            if(color1id != 4)
            color2id = 4;
            }
            if((float)Gdx.input.getX() / Gdx.graphics.getWidth() > 0.1f && (float)Gdx.input.getX() / Gdx.graphics.getWidth() < 0.28f && (float)Gdx.input.getY() 	   / Gdx.graphics.getHeight() > 0.57f && (float)Gdx.input.getY() / Gdx.graphics.getHeight() < 0.67f){
                if(color1id != 5)
                    color2id = 5;
            }
            else if((float)Gdx.input.getX() / Gdx.graphics.getWidth() > 0.28f && (float)Gdx.input.getX() / Gdx.graphics.getWidth() < 0.46f && (float)Gdx.input.getY() / Gdx.graphics.getHeight() > 0.57f && (float)Gdx.input.getY() / Gdx.graphics.getHeight() < 0.67f){
                if(color1id != 6)
                    color2id = 6;
            }
            else if((float)Gdx.input.getX() / Gdx.graphics.getWidth() > 0.46f && (float)Gdx.input.getX() / Gdx.graphics.getWidth() < 0.64f && (float)Gdx.input.getY() / Gdx.graphics.getHeight() > 0.57f && (float)Gdx.input.getY() / Gdx.graphics.getHeight() < 0.67f){
                if(color1id != 7)
                    color2id = 7;
            }
            else if((float)Gdx.input.getX() / Gdx.graphics.getWidth() > 0.64f && (float)Gdx.input.getX() / Gdx.graphics.getWidth() < 0.82f && (float)Gdx.input.getY() / Gdx.graphics.getHeight() > 0.57f && (float)Gdx.input.getY() / Gdx.graphics.getHeight() < 0.67f){
                if(color1id != 8)
                    color2id = 8;
            }
            else if((float)Gdx.input.getX() / Gdx.graphics.getWidth() > 0.82f && (float)Gdx.input.getX() / Gdx.graphics.getWidth() < 01.0f && (float)Gdx.input.getY() / Gdx.graphics.getHeight() > 0.57f && (float)Gdx.input.getY() / Gdx.graphics.getHeight() < 0.67f){
            if(color1id != 9)
            color2id = 9;
            }
        }
        if(color1id != -1 && color2id != -1){
            game.color1id = color1id;
            game.color2id = color2id;
            if(menuState == MenuState.MS_COLORPICKERENDLESS)
                game.SetGameState(EGameState.GS_ENDLESS);
            else
                game.SetGameState(EGameState.GS_BREAKBLOCK);
            color1id = -1;
            color2id = -1;
            Frame = 0;
        }
    }

    private void updateHighscore() {
        endlessX = 1000.0f;
        endlessY = 740.0f;
        optionX = 1000.0f;
        optionY = 400.0f;
        highscoreX = 100.0f;
        highscoreY = 740.0f;
        breakBlockX = 1000.0f;
        breakBlockY = 600.0f;
        achievementsX = 1000.0f;
        achievementsY = 200.0f;
        if(Gdx.input.isKeyPressed(Keys.BACK)&& !game.backpressed){
            menuState = MenuState.MS_ROOT;
            game.backpressed = true;
        }
        if(Gdx.input.isTouched()){
            if((float)Gdx.input.getX() / Gdx.graphics.getWidth() > 0.1f && (float)Gdx.input.getX() / Gdx.graphics.getWidth() < 0.9f && (float)Gdx.input.getY() / Gdx.graphics.getHeight() > 0.20f && (float)Gdx.input.getY() / Gdx.graphics.getHeight() < 0.30f ){
                game.playServices.showScore(R.string.leaderboard_break_block);
            }
            else if((float)Gdx.input.getX() / Gdx.graphics.getWidth() > 0.1f && (float)Gdx.input.getX() / Gdx.graphics.getWidth() < 0.9f && (float)Gdx.input.getY() / Gdx.graphics.getHeight() > 0.33f && (float)Gdx.input.getY() / Gdx.graphics.getHeight() < 0.43f ){
                game.playServices.showScore(R.string.leaderboard_endless_easy);
            }
            else if((float)Gdx.input.getX() / Gdx.graphics.getWidth() > 0.1f && (float)Gdx.input.getX() / Gdx.graphics.getWidth() < 0.9f && (float)Gdx.input.getY() / Gdx.graphics.getHeight() > 0.46f && (float)Gdx.input.getY() / Gdx.graphics.getHeight() < 0.56f ){
                game.playServices.showScore(R.string.leaderboard_endless_normal);
            }
            else if((float)Gdx.input.getX() / Gdx.graphics.getWidth() > 0.1f && (float)Gdx.input.getX() / Gdx.graphics.getWidth() < 0.9f && (float)Gdx.input.getY() / Gdx.graphics.getHeight() > 0.59f && (float)Gdx.input.getY() / Gdx.graphics.getHeight() < 0.69f ){
                game.playServices.showScore(R.string.leaderboard_endless_nightmare);
            }
        }
    }

    private void updateOptions() {
        sensitivityRect.x = 30.0f + game.accacc * 85.0f;
        sensitivityRect.y = 600.0f - 50.0f;
        volumeRect.x = 30.0f + game.volume * Gdx.graphics.getWidth() * 0.37f;
        volumeRect.y = 450.0f - 50.0f;
        sensitivityRect.height = 32.0f;
        sensitivityRect.width = 32.0f;
        volumeRect.height= 32.0f;
        volumeRect.width = 32.0f;
        endlessX = 1000.0f;
        endlessY = 740.0f;
        optionX = 100.0f;
        optionY = 740.0f;
        highscoreX = 1000.0f;
        highscoreY = 740.0f;
        breakBlockX = 1000.0f;
        breakBlockY = 500.0f;
        achievementsX = 1000.0f;
        achievementsY = 200.0f;
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

    private void drawOptions() {
        game.shapeRenderer.begin(ShapeType.Filled);
        game.shapeRenderer.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        game.shapeRenderer.rect(50.0f, 600.0f, 270.0f, 40.0f);
        game.shapeRenderer.rect(50.0f, 450.0f, 270.0f, 40.0f);
        game.shapeRenderer.rect(50.0f, 600.0f - 40.0f, 380.0f, 10.0f);
        game.shapeRenderer.rect(50.0f, 450.0f - 40.0f, 380.0f, 10.0f);
        game.shapeRenderer.rect(volumeRect.x, volumeRect.y, volumeRect.width, volumeRect.height);
        game.shapeRenderer.rect(sensitivityRect.x, sensitivityRect.y, sensitivityRect.width, sensitivityRect.height);
        game.shapeRenderer.end();
        game.batch.begin();
        game.font2.draw(game.batch, game.myBundle.format("sensitivity")+":", 60.0f, 630.0f);
        game.font2.draw(game.batch, game.myBundle.format("volume")+":", 60.0f, 480.0f);
        game.batch.end();
    }

    private void drawColorPicker() {
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
        for(int i = 0; i < 10; i++){
            game.shapeRenderer.begin(ShapeType.Filled);
            game.shapeRenderer.setColor(game.colors[i]);
            game.shapeRenderer.rect( 50.0f + 80.0f * ((i)%5), 400.0f + 80.0f * ((i)/5), 64.0f, 64.0f);
            game.shapeRenderer.end();
            //Umrandungen
            game.shapeRenderer.begin(ShapeType.Line);
            if(color1id == i){
                game.shapeRenderer.setColor(1.0f, 1.0f, 1.0f, 1.0f);
            }
            else {
                game.shapeRenderer.setColor(0.0f, 0.0f, 0.0f, 1.0f);
            }
            game.shapeRenderer.rect( 50.0f + 80.0f * ((i)%5), 400.0f + 80.0f * ((i)/5), 64.0f, 64.0f);
            game.shapeRenderer.end();
        }
        for(int i = 0; i < 10; i++){
            game.shapeRenderer.begin(ShapeType.Filled);
            game.shapeRenderer.setColor(game.colors[i]);
            game.shapeRenderer.rect( 50.0f + 80.0f * ((i)%5), 200.0f + 80.0f * ((i)/5), 64.0f, 64.0f);
            game.shapeRenderer.end();
            //Umrandungen
            game.shapeRenderer.begin(ShapeType.Line);
            if(color2id == i){
                game.shapeRenderer.setColor(1.0f, 1.0f, 1.0f, 1.0f);
            }
            else {
                game.shapeRenderer.setColor(0.0f, 0.0f, 0.0f, 1.0f);
            }
            game.shapeRenderer.rect( 50.0f + 80.0f * ((i)%5), 200.0f + 80.0f * ((i)/5), 64.0f, 64.0f);
            game.shapeRenderer.end();
        }
    }
}
