package de.pilzschaf.testgame;
/**
 * Created by pilzschaf on 12.03.17.
 */

public interface PlayServices
{
    public void signIn();
    public void signOut();
    public void rateGame();
    public void unlockAchievement(int resID);
    public void submitScore(int resID, int highScore);
    public void showAchievements();
    public void showScore(int resID);
    public boolean isSignedIn();
}