package com.zainchen.game2048.actor;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.zainchen.game2048.Game2048;
import com.zainchen.game2048.actor.base.BaseGroup;
import com.zainchen.game2048.constant.ConstantRes;

/**
 * 顶部演员组，显示游戏Logo和分数
 *
 * @author Zain Chen
 * @date 2021/4/6 11:14
 */
public class TopGroup extends BaseGroup {

    /**
     * 2048 LOGO
     */
    private Image logoImage;

    /**
     * 当前分数
     */
    private com.zainchen.game2048.actor.ScoreGroup currentScoreGroup;

    /**
     * 最佳分数
     */
    private com.zainchen.game2048.actor.ScoreGroup bestScoreGroup;

    public TopGroup(Game2048 mainGame) {
        super(mainGame);
        init();
    }

    private void init() {

        //首先添加当前分数框，作为位置基准
        currentScoreGroup = new com.zainchen.game2048.actor.ScoreGroup(getGame2048(), getGame2048().getAtlas().findRegion(com.zainchen.game2048.constant.ConstantRes.Assets.CURRENT_SCORE));
        //设置TopGroup的宽高（以世界的宽度, 分数框的高度 作为组的宽高）
        setSize(getGame2048().getWorldWidth(), currentScoreGroup.getHeight());

        //添加2048Logo
        logoImage = new Image(getGame2048().getAtlas().findRegion(com.zainchen.game2048.constant.ConstantRes.Assets.LOGO));
        logoImage.setX(20);
        //Logo相对于分数框垂直居中
        logoImage.setY(getY() + (getHeight() / 2 - logoImage.getHeight() / 2));
        addActor(logoImage);

        //当前分数框置于Logo的右部
        currentScoreGroup.setX(logoImage.getX() + logoImage.getWidth() + 20);
        //置于TopGroup的顶部
        currentScoreGroup.setY(getHeight() - currentScoreGroup.getHeight());
        addActor(currentScoreGroup);

        //最佳分数
        bestScoreGroup = new com.zainchen.game2048.actor.ScoreGroup(getGame2048(), getGame2048().getAtlas().findRegion(ConstantRes.Assets.BEST_SCORE));
        bestScoreGroup.setX(currentScoreGroup.getX() + currentScoreGroup.getWidth() + 10);
        bestScoreGroup.setY(getHeight() - currentScoreGroup.getHeight());
        addActor(bestScoreGroup);

    }

    public com.zainchen.game2048.actor.ScoreGroup getCurrentScoreGroup() {
        return currentScoreGroup;
    }

    public ScoreGroup getBestScoreGroup() {
        return bestScoreGroup;
    }

}
