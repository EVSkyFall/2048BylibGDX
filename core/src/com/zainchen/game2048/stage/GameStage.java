package com.zainchen.game2048.stage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.zainchen.game2048.Game2048;
import com.zainchen.game2048.actor.BottomGroup;
import com.zainchen.game2048.actor.GridGroup;
import com.zainchen.game2048.actor.TopGroup;
import com.zainchen.game2048.stage.base.BaseStage;

/**
 * 主游戏舞台，添加顶部演员组TopGroup（显示Logo和分数）、网格演员组GridGroup（放置数字卡片）。
 * 监听分数变化，分数增加时映射到分数框。
 * 销毁时保存最佳分数。
 * 添加生成数字和合并数字时的动画。
 *
 * @author Zain Chen
 * @date 2021/4/6 11:13
 */
public class GameStage extends BaseStage {

    public static final String TAG = "CHEN Debug GameStage";
    /**
     * 顶部演员组（包含Logo和分数显示）
     */
    private TopGroup topGroup;

    /**
     * 网格演员组（包含数字卡片）
     */
    private GridGroup gridGroup;

    private BottomGroup bottomGroup;

    public GameStage(Game2048 mainGame, Viewport viewport) {
        super(mainGame, viewport);
        init();
    }

    private void init() {
        /*
         * 网格演员组，设置到舞台中央，其他演员组以该演员组为位置参考
         */
        gridGroup = new GridGroup(getGame2048());
        //把网格演员组设置到主舞台GameStage的中心
        gridGroup.setPosition(
                getWidth() / 2 - gridGroup.getWidth() / 2,
                getHeight() / 2 - gridGroup.getHeight() / 2
        );
        addActor(gridGroup);

        /*
         * 顶部演员组，设置到网格演员组上方
         */
        topGroup = new TopGroup(getGame2048());
        //获得网格演员组上方Y轴坐标
        float gridGroupTopY = gridGroup.getY() + gridGroup.getHeight();
        //水平居中，竖直方向位于网格演员组上方
        topGroup.setPosition(
                getWidth() / 2 - gridGroup.getWidth() / 2,
                gridGroupTopY + 20
        );
        addActor(topGroup);

        //初始化分数
        topGroup.getCurrentScoreGroup().setScore(0);

        //读取最佳分数
        Preferences prefs = Gdx.app.getPreferences("game_score_pref");
        int bestScore = prefs.getInteger("best_score", 0);
        // 设置最佳分数
        topGroup.getBestScoreGroup().setScore(bestScore);

        /*
        底部演员组，添加两个按钮
         */
        bottomGroup=new BottomGroup(getGame2048());
        //水平居中
        bottomGroup.setX(getWidth() / 2 - bottomGroup.getWidth() / 2);
        //底部竖直居中
        bottomGroup.setY(gridGroup.getY()-bottomGroup.getHeight()-20);
        addActor(bottomGroup);

    }

    /**
     * 增加当前分数数据
     */
    public void addCurrentScore(int scoreStep) {
        // 增加分数
        topGroup.getCurrentScoreGroup().addScore(scoreStep);
        //如果当前分数大于最佳分数, 则更新最佳分数
        int currentSore = topGroup.getCurrentScoreGroup().getScore();
        int bestSore = topGroup.getBestScoreGroup().getScore();
        if (currentSore > bestSore) {
            topGroup.getBestScoreGroup().setScore(currentSore);
        }
    }

    /**
     * 重新开始游戏
     */
    public void restartGame() {
        gridGroup.restartGame();
        //当前分数清零
        topGroup.getCurrentScoreGroup().setScore(0);
    }

    private void addAlphaMask(){

    }

    @Override
    public void dispose() {
        super.dispose();
        //舞台销毁时保存最佳分数
        Preferences prefs = Gdx.app.getPreferences("game_score_pref");
        prefs.putInteger("best_score", topGroup.getBestScoreGroup().getScore());
        prefs.flush();
    }
}
