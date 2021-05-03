package com.zainchen.game2048.stage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.zainchen.game2048.Game2048;
import com.zainchen.game2048.stage.base.BaseStage;
import com.zainchen.game2048.constant.ConstantRes;

/**
 * 游戏结束舞台，展示过关信息，展示分数标签，再来一局按钮。
 *
 * @author Zain Chen
 * @date 2021/4/9 13:09
 */
public class GameOverStage extends BaseStage {

    public static final String TAG = "CHEN Debug GameOverStage";

    /**
     * 分数面板
     */
    private Image boardImage;

    /**
     * 游戏结束（失败）信息
     */
    private Image overText;

    /**
     * 游戏胜利信息
     */
    private Image victoryText;

    /**
     * 本局分数标签
     */
    private Label scoreLabel;

    /**
     * 历史最佳分数标签
     */
    private Label bestScoreLabel;

    /**
     * 3. 游戏结束舞台
     */
    private GameOverStage gameOverStage;

    /**
     * 重来一局按钮
     */
    private Button restartButton;

    public GameOverStage(Game2048 mainGame, Viewport viewport) {
        super(mainGame, viewport);
        init();
    }

    private void init() {

        //游戏结束提示面板
        boardImage = new Image(getGame2048().getAtlas().findRegion(com.zainchen.game2048.constant.ConstantRes.Assets.SCORE_BOARD));
        //在整个世界居中
        boardImage.setPosition(
                getWidth() / 2 - boardImage.getWidth() / 2,
                getHeight() / 2 - boardImage.getHeight() / 2
        );
        addActor(boardImage);

        //“恭喜过关”和“游戏结束”图片
        victoryText = new Image(getGame2048().getAtlas().findRegion(com.zainchen.game2048.constant.ConstantRes.Assets.VICTORY_TEXT));
        overText = new Image(getGame2048().getAtlas().findRegion(com.zainchen.game2048.constant.ConstantRes.Assets.OVER_TEXT));

        //本局分数标签
        Label.LabelStyle style = new Label.LabelStyle();
        style.font = getGame2048().getBitmapFont();
        scoreLabel = new Label("", style);
        scoreLabel.setColor(Color.BLACK);
        addActor(scoreLabel);

        //最佳分数标签
        bestScoreLabel = new Label("", style);
        //设置和“历史最高”文本相同的颜色
        bestScoreLabel.setColor(new Color(0x808080FF));
        bestScoreLabel.setFontScale(0.4f);
        addActor(bestScoreLabel);

        //再来一局按钮
        Button.ButtonStyle restartStyle = new Button.ButtonStyle();
        //按钮按下前的纹理
        restartStyle.up = new TextureRegionDrawable(getGame2048().getAtlas().findRegion(com.zainchen.game2048.constant.ConstantRes.Assets.OVER_RESTART, 1));
        //按钮按下去的纹理
        restartStyle.down = new TextureRegionDrawable(getGame2048().getAtlas().findRegion(ConstantRes.Assets.OVER_RESTART, 2));
        restartButton = new Button(restartStyle);
        //水平居中，位于分数面板下面
        restartButton.setPosition(
                getWidth() / 2 - restartButton.getWidth() / 2,
                boardImage.getY() - restartButton.getHeight() - 10
        );
        restartButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //隐藏结束舞台（返回主游戏舞台）
                getGame2048().getGameScreen().restartGame();
            }
        });
        addActor(restartButton);
    }

    public void setGameOverState(boolean isWin, int score) {
        //如果游戏胜利，显示“恭喜过关”文字，否则显示“游戏结束”文字
        if (isWin) {
            //水平居中，位于分数面板上方
            victoryText.setPosition(
                    getWidth() / 2 - victoryText.getWidth() / 2,
                    boardImage.getY() + boardImage.getHeight() - 100
            );
            addActor(victoryText);
        } else {
            overText.setPosition(
                    getWidth() / 2 - overText.getWidth() / 2,
                    boardImage.getY() + boardImage.getHeight() - 100
            );
            addActor(overText);
        }

        //显示本局分数
        scoreLabel.setText(score);
        scoreLabel.setAlignment(Align.center);
        scoreLabel.setPosition(
                boardImage.getX() + boardImage.getPrefWidth() / 2,
                boardImage.getY() + boardImage.getPrefHeight() / 2 - 30
        );

        //获取并显示历史最佳分数
        Preferences prefs = Gdx.app.getPreferences("game_score_pref");
        bestScoreLabel.setText(prefs.getInteger("best_score", 0));
        bestScoreLabel.setPosition(
                boardImage.getX() + boardImage.getWidth() / 2 + 10,
                boardImage.getY() + 123
        );
    }
}
