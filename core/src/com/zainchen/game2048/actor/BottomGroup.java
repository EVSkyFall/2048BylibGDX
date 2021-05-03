package com.zainchen.game2048.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.zainchen.game2048.Game2048;
import com.zainchen.game2048.actor.base.BaseGroup;
import com.zainchen.game2048.constant.ConstantRes;

/**
 * 底部演员组，添加“重新开始”和“退出游戏”按钮
 *
 * @author Zain Chen
 * @date 2021/4/13 11:30
 */
public class BottomGroup extends BaseGroup {

    private Button restartButton;

    private Button exitButton;

    public BottomGroup(Game2048 mainGame) {
        super(mainGame);
        init();
    }

    private void init(){
        /*
        重新开始按钮
         */
        Button.ButtonStyle restartStyle=new Button.ButtonStyle();
        restartStyle.up=new TextureRegionDrawable(
                getGame2048().getAtlas().findRegion(com.zainchen.game2048.constant.ConstantRes.Assets.RESTART_BUTTON,1)
        );
        restartStyle.down=new TextureRegionDrawable(
                getGame2048().getAtlas().findRegion(com.zainchen.game2048.constant.ConstantRes.Assets.RESTART_BUTTON,2)
        );
        restartButton=new Button(restartStyle);
        //按钮在屏幕左半部分居中
        restartButton.setX(getGame2048().getWorldWidth()/4-restartButton.getPrefWidth()/2);
        //设置按钮点击监听
        restartButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //重新开始
                getGame2048().getGameScreen().restartGame();
            }
        });
        addActor(restartButton);

        setSize(getGame2048().getWorldWidth(), restartButton.getHeight());

        /*
        退出游戏按钮
         */
        Button.ButtonStyle exitStyle=new Button.ButtonStyle();
        exitStyle.up=new TextureRegionDrawable(
                getGame2048().getAtlas().findRegion(com.zainchen.game2048.constant.ConstantRes.Assets.EXIT_BUTTON,1)
        );
        exitStyle.down=new TextureRegionDrawable(
                getGame2048().getAtlas().findRegion(ConstantRes.Assets.EXIT_BUTTON,2)
        );
        exitButton=new Button(exitStyle);
        exitButton.setX(restartButton.getX()+restartButton.getPrefWidth()+50);
        //设置按钮点击监听
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //退出游戏
                Gdx.app.exit();
            }
        });
        addActor(exitButton);
    }

    @Override
    public Game2048 getGame2048() {
        return super.getGame2048();
    }

    @Override
    public void setGame2048(Game2048 mainGame) {
        super.setGame2048(mainGame);
    }
}
