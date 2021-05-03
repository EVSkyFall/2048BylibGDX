package com.zainchen.game2048.actor;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.zainchen.game2048.Game2048;
import com.zainchen.game2048.actor.base.BaseGroup;
import com.zainchen.game2048.constant.ConstantRes;

/**
 * 数字卡片演员组，为卡片添加数字标签，为不同颜色的数字卡片添加不同的颜色。
 *
 * @author Zain Chen
 * @date 2021/4/8 13:13
 */
public class CardGroup extends BaseGroup {
    /**
     * 卡片背景
     */
    private Image bgImage;

    /**
     * 卡片显示的数字标签
     */
    private Label numLabel;

    /**
     * 卡片当前显示的数字
     */
    private int num;

    public CardGroup(Game2048 mainGame) {
        super(mainGame);
        init();
    }

    private void init() {
        bgImage = new Image(getGame2048().getAtlas().findRegion(com.zainchen.game2048.constant.ConstantRes.Assets.CARD_BACKGROUND));
        addActor(bgImage);

        // 设置组的宽高，每个背景图就是一个CardGroup，以卡片背景的宽高作为组的宽高
        setSize(bgImage.getWidth(), bgImage.getHeight());

        /*
        设置卡片显示数字的标签
         */
        //设置数字标签样式
        LabelStyle style = new LabelStyle();
        style.font = getGame2048().getBitmapFont();
        style.fontColor = new Color(0x5E5B51FF);

        numLabel = new Label("0", style);
        //设置字体缩放
        numLabel.setFontScale(0.6f);

        // 设置标签的宽高（把标签的宽高设置为文本字体的宽高, 即标签包裹文本）
        numLabel.setSize(numLabel.getPrefWidth(), numLabel.getPrefHeight());

        // 设置文本标签在组中居中显示
        numLabel.setX(getWidth() / 2 - numLabel.getWidth() / 2);
        numLabel.setY(getHeight() / 2 - numLabel.getHeight() / 2);

        addActor(numLabel);

        setNum(num);
    }

    /**
     * 设置标签数字
     */
    public void setNum(int num) {

        this.num = num;

        if (this.num == 0) {
            //如果是0, 则不显示文本
            numLabel.setText("");
        } else {
            numLabel.setText(String.valueOf(this.num));
        }

        //数字改变后, 文本的宽度可能被改变, 需要重新设置标签的宽度, 并重新水平居中
        numLabel.setWidth(numLabel.getPrefWidth());
        numLabel.setX(getWidth() / 2 - numLabel.getWidth() / 2);

        //根据不同的数字给卡片背景设置颜色
        switch (this.num) {
            case 2: {
                bgImage.setColor(com.zainchen.game2048.constant.ConstantRes.CardColors.RGBA_2);
                break;
            }
            case 4: {
                bgImage.setColor(com.zainchen.game2048.constant.ConstantRes.CardColors.RGBA_4);
                break;
            }
            case 8: {
                bgImage.setColor(com.zainchen.game2048.constant.ConstantRes.CardColors.RGBA_8);
                break;
            }
            case 16: {
                bgImage.setColor(com.zainchen.game2048.constant.ConstantRes.CardColors.RGBA_16);
                break;
            }
            case 32:{
                bgImage.setColor(com.zainchen.game2048.constant.ConstantRes.CardColors.RGBA_32);
                break;
            }
            case 64: {
                bgImage.setColor(com.zainchen.game2048.constant.ConstantRes.CardColors.RGBA_64);
                break;
            }
            case 128:
            case 60: {
                bgImage.setColor(com.zainchen.game2048.constant.ConstantRes.CardColors.RGBA_128);
                break;
            }
            case 256: {
                bgImage.setColor(com.zainchen.game2048.constant.ConstantRes.CardColors.RGBA_256);
                break;
            }
            case 512: {
                bgImage.setColor(com.zainchen.game2048.constant.ConstantRes.CardColors.RGBA_512);
                break;
            }
            case 1024: {
                bgImage.setColor(com.zainchen.game2048.constant.ConstantRes.CardColors.RGBA_1024);
                break;
            }
            case 2048: {
                bgImage.setColor(com.zainchen.game2048.constant.ConstantRes.CardColors.RGBA_2048);
                break;
            }
            default: {
                bgImage.setColor(ConstantRes.CardColors.RGBA_0);
                break;
            }
        }
    }
}
