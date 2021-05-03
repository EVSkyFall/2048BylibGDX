package com.zainchen.game2048.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.zainchen.game2048.Game2048;
import com.zainchen.game2048.stage.GameStage;
import com.zainchen.game2048.stage.GameOverStage;

/**
 * 主游戏场景，绘制各个舞台
 *
 * @author Zain Chen
 * @date 2021/4/6 11:05
 */
public class GameScreen extends ScreenAdapter {

    public static final String TAG ="CHEN Debug GameScreen";

    private Game2048 mainGame;

    /**
     * 主游戏舞台
     */
    private GameStage gameStage;

    private com.zainchen.game2048.stage.GameOverStage gameOverStage;

    /**
     * 背景颜色
     */
    Color bgColor = new Color(0xF8F8F0FF);


    public GameScreen(Game2048 mainGame) {
        this.mainGame = mainGame;
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        //创建主游戏舞台
        gameStage = new GameStage(
                mainGame,
                new StretchViewport(
                        mainGame.getWorldWidth(),
                        mainGame.getWorldHeight()
                )
        );

        //创建游戏结束舞台，默认不可见
        gameOverStage=new GameOverStage(
                mainGame,
                new StretchViewport(
                        mainGame.getWorldWidth(),
                        mainGame.getWorldHeight()
                )
        );
        gameOverStage.setVisible(false);

        //把输入处理设置到主游戏舞台
        Gdx.input.setInputProcessor(gameStage);
    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(bgColor.r, bgColor.g, bgColor.b, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //绘制舞台
        //主游戏舞台始终都需要绘制
        gameStage.act();
        gameStage.draw();

        //游戏结束舞台
        if (gameOverStage.isVisible()) {
            gameOverStage.act();
            gameOverStage.draw();
        }
    }

    @Override
    public void dispose() {
        // 场景销毁时, 同时销毁所有舞台
        gameStage.dispose();

        if (gameOverStage != null) {
            gameOverStage.dispose();
        }

    }

    public GameStage getGameStage() {
        return gameStage;
    }

    /**
     * 显示结束舞台（并设置结束舞台中的文本显示状态和分数）
     * Usage: GridGroup.onGameOver()
     */
    public void showGameOverStage(boolean isWin, int score) {
        // 设置结束舞台中的文本显示状态状态和分数
        gameOverStage.setGameOverState(isWin, score);
        gameOverStage.setVisible(true);
        Gdx.input.setInputProcessor(gameOverStage);
    }

    /**
     * 隐藏结束舞台并重新开始
     * Usage: GameOverStage.init()
     */
    public void restartGame() {
        gameOverStage.setVisible(false);
        Gdx.input.setInputProcessor(gameStage);
        gameStage.restartGame();
    }

}
