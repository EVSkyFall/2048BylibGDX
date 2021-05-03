package com.zainchen.game2048.actor.base;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.zainchen.game2048.Game2048;

/**
 * 演员组基类
 *
 * @author Zain Chen
 * @date 2021/4/8 11:23
 */
public abstract class BaseGroup extends Group {

    private Game2048 mainGame;

    public BaseGroup(Game2048 mainGame) {
        this.mainGame = mainGame;
    }

    public Game2048 getGame2048() {
        return mainGame;
    }

    public void setGame2048(Game2048 mainGame) {
        this.mainGame = mainGame;
    }

}
