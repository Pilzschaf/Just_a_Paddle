package de.pilzschaf.testgame.android;

/**
 * Created by pilzschaf on 11.03.17.
 */

interface PlayServices
{
    public void signIn();
    public void signOut();
    public void rateGame();
    public void unlockAchievement();
    public void submitScore(int highScore);
    public void showAchievement();
    public void showScore();
    public boolean isSignedIn();
}
