package com.zainchen.game2048.stage.base;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.zainchen.game2048.Game2048;

/**
 * 舞台基类
 *
 * @author Zain Chen
 * @date 2021/4/8 11:24
 */
public abstract class BaseStage extends Stage {

    private Game2048 mainGame;

    /**
     * 舞台是否可见（是否更新和绘制）
     */
    private boolean visible = true;

    public BaseStage(Game2048 mainGame, Viewport viewport) {
        super(viewport);
        this.mainGame = mainGame;
    }

    public Game2048 getGame2048() {
        return mainGame;
    }

    public void setGame2048(Game2048 mainGame) {
        this.mainGame = mainGame;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}
