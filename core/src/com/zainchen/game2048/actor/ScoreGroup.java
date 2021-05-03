package com.zainchen.game2048.actor;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.zainchen.game2048.Game2048;
import com.zainchen.game2048.actor.base.BaseGroup;

/**
 * 分数演员组，显示分数
 *
 * @author Zain Chen
 * @date 2021/4/8 11:15
 */
public class ScoreGroup extends BaseGroup {
    /**
     * 背景
     */
    private Image bgImage;

    /**
     * 分数文本显示
     */
    private Label scoreLabel;

    /**
     * 当前显示的分数
     */
    private int score = 99999;

    public ScoreGroup(Game2048 mainGame, TextureRegion bgRegion) {
        super(mainGame);
        init(bgRegion);
    }

    private void init(TextureRegion bgRegion) {
        // 首先设置组的宽高（以背景的宽高作为组的宽高）
        setSize(bgRegion.getRegionWidth(), bgRegion.getRegionHeight());

        bgImage = new Image(bgRegion);
        addActor(bgImage);

        /*
         * 分数文本显示的标签
         */
        //创建标签样式
        Label.LabelStyle style = new Label.LabelStyle();
        style.font = getGame2048().getBitmapFont();

        //创建文本标签
        scoreLabel = new Label(String.valueOf(score), style);

        //设置字体缩放
        scoreLabel.setFontScale(0.4F);

        //设置标签的宽高（把标签的宽高设置为文本字体的宽高, 即标签包裹文本）
        scoreLabel.setSize(scoreLabel.getPrefWidth(), scoreLabel.getPrefHeight());

        //设置文本标签在组中水平居中显示
        scoreLabel.setX(getWidth() / 2 - scoreLabel.getWidth() / 2);
        scoreLabel.setY(18);

        addActor(scoreLabel);
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
        scoreLabel.setText(String.valueOf(score));

        //重新设置文本后, 文本的宽度可能被改变, 需要重新设置标签的宽度, 并重新水平居中
        scoreLabel.setWidth(scoreLabel.getPrefWidth());
        scoreLabel.setX(getWidth() / 2 - scoreLabel.getWidth() / 2);
    }

    public void addScore(int scoreStep) {
        setScore(this.score + scoreStep);
    }

}
