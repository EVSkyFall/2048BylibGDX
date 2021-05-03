package com.zainchen.game2048.actor;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.ScaleToAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Align;
import com.zainchen.game2048.Game2048;
import com.zainchen.game2048.core.CoreModel;
import com.zainchen.game2048.actor.base.BaseGroup;
import com.zainchen.game2048.constant.ConstantRes;

/**
 * 网格演员组，放置数字卡片，生成数据模型绑定到卡片上，捕捉并完成滑动事件。
 *
 * @author Zain Chen
 * @date 2021/4/6 12:43
 */
public class GridGroup extends BaseGroup {

    public static final String TAG = "CHEN Debug GridGroup";

    /**
     * 卡片的行列数
     */
    private static final int CARD_ROWS = 4;
    private static final int CARD_COLS = 4;

    /**
     * 背景图片
     */
    private Image bgImage;

    /**
     * 二维Card数组，作为背景卡片
     */
    private final CardGroup[][] bgCards = new CardGroup[CARD_ROWS][CARD_COLS];

    /**
     * 二维Card数组，作为数字卡片
     */
    private final CardGroup[][] cards = new CardGroup[CARD_ROWS][CARD_COLS];

    /**
     * 移动音效和合并音效
     */
    private Sound moveSound;
    private Sound mergeSound;

    public GridGroup(Game2048 mainGame) {
        super(mainGame);
        init();
    }

    /**
     * 核心数据模型
     */
    private CoreModel coreModel;

    private void init() {
        /*
         * 背景图片
         */
        bgImage = new Image(getGame2048().getAtlas().findRegion(com.zainchen.game2048.constant.ConstantRes.Assets.GRID_BACKGROUND));
        addActor(bgImage);

        // 设置GridGroup的宽高（以背景宽高作为组的宽高）
        setSize(bgImage.getWidth(), bgImage.getHeight());

        for (int row = 0; row < CARD_ROWS; row++) {
            for (int col = 0; col < CARD_COLS; col++) {
                bgCards[row][col] = new CardGroup(getGame2048());
                addActor(bgCards[row][col]);
                cards[row][col] = new CardGroup(getGame2048());
                cards[row][col].setOrigin(Align.center);
                addActor(cards[row][col]);
            }
        }

        //获取卡片的宽高
        float cardWidth = bgCards[0][0].getWidth();
        float cardHeight = bgCards[0][0].getHeight();

        //计算所有卡片排列好后在GridGroup中的水平和竖直间隙大小
        float horizontalInterval = (getWidth() - CARD_COLS * cardWidth) / (CARD_COLS + 1);
        float verticalInterval = (getHeight() - CARD_ROWS * cardHeight) / (CARD_ROWS + 1);

        /*
        均匀排列所有背景卡片和数字卡片
         */
        float cardY;
        for (int row = 0; row < CARD_ROWS; row++) {
            //每行的Y轴坐标
            cardY = getHeight() - (verticalInterval + cardHeight) * (row + 1);
            for (int col = 0; col < CARD_COLS; col++) {
                bgCards[row][col].setPosition(
                        horizontalInterval + (cardWidth + horizontalInterval) * col,
                        cardY
                );
                cards[row][col].setPosition(
                        horizontalInterval + (cardWidth + horizontalInterval) * col,
                        cardY
                );
            }
        }

        //添加输入监听器
        addListener(new InputListenerImpl());

        //创建数据模型
        coreModel = CoreModel.Builder.createDataModel(CARD_ROWS, CARD_COLS, new DataListenerImpl());
        coreModel.initData();
        addDataToCardGroup();

        //获取音效
        moveSound=getGame2048().getAssetManager().get(com.zainchen.game2048.constant.ConstantRes.Audio.MOVE_AUDIO);
        mergeSound=getGame2048().getAssetManager().get(ConstantRes.Audio.MERGE_AUDIO);
    }

    /**
     * 获取CoreModel生成的数据模型，将生成的数字矩阵添加到CardGroup
     */
    private void addDataToCardGroup() {
        int[][] data = coreModel.getData();
        for (int row = 0; row < CARD_ROWS; row++) {
            for (int col = 0; col < CARD_COLS; col++) {
                cards[row][col].setNum(data[row][col]);
            }
        }
    }

    public void toUp() {
        //操作数据模型中的数据
        coreModel.toUp();
        moveSound.play();
    }

    public void toDown() {
        coreModel.toDown();
        moveSound.play();
    }

    public void toLeft() {
        coreModel.toLeft();
        moveSound.play();
    }

    public void toRight() {
        coreModel.toRight();
        moveSound.play();
    }

    /**
     * 重新开始游戏
     */
    public void restartGame() {
        coreModel.initData();
        addDataToCardGroup();
    }

    /**
     * 用户输入监听器，监听滑动操作，并实现对应的移动方法
     */
    private class InputListenerImpl extends InputListener {

        private float touchDownX;
        private float touchDownY;

        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            touchDownX = x;
            touchDownY = y;
            return true;
        }

        @Override
        public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            float moveX = x - touchDownX;
            float moveY = y - touchDownY;
            //通过滑动距离判断执行上下滑动方法还是左右滑动方法
            if (Math.abs(moveX) >= 20 && Math.abs(moveX) > Math.abs(moveY)) {
                if (moveX > 0) {
                    toRight();
                } else {
                    toLeft();
                }
            } else if (Math.abs(moveY) >= 20 && Math.abs(moveY) > Math.abs(moveX)) {
                if (moveY > 0) {
                    toUp();
                } else {
                    toDown();
                }
            }
        }
    }

    /**
     * 数据监听器，数据变化时添加动画
     */
    private class DataListenerImpl implements CoreModel.DataListener {

        @Override
        public void onGenerateNumber(int row, int col, int num) {
            cards[row][col].setScale(0.2f);
            ScaleToAction scaleTo = Actions.scaleTo(1.0f, 1.0f, 0.2f);
            cards[row][col].addAction(scaleTo);
        }

        @Override
        public void onNumberMove(final int begin, final int end, final int fixed,boolean isUpOrDown) {

            //beginRow和beginCol记录开始时卡片在grid中的网格位置
            final int beginRow;
            final int beginCol;
            //分别记录移动动作开始和结束时的实际坐标
            final float beginX;
            final float beginY;
            float endX;
            float endY;

            if(isUpOrDown){
                //如果上下移动，列号不动为fixed，begin为开始的行号，end为结束的行号
                beginRow=begin;
                beginCol=fixed;
                beginX = cards[begin][fixed].getX();
                beginY = cards[begin][fixed].getY();
                endX = bgCards[end][fixed].getX();
                endY = bgCards[end][fixed].getY();
            }else{
                //如果左右移动，行号不动为fixed，begin为开始的列号，end为结束的列号
                beginRow=fixed;
                beginCol=begin;
                beginX=cards[fixed][begin].getX();
                beginY=cards[fixed][begin].getY();
                endX = bgCards[fixed][end].getX();
                endY = bgCards[fixed][end].getY();
            }

            //移动动作
            SequenceAction moveToAction = Actions.sequence(
                    Actions.moveTo(endX, endY, 0.1f),
                    Actions.run(new Runnable() {
                        @Override
                        public void run() {
                            //移动完毕后将移动的卡片重置回原来的位置
                            cards[beginRow][beginCol].setPosition(beginX, beginY);
                            //确保动作播放完成后再进行数据同步
                            addDataToCardGroup();
                        }
                    })
            );
            //将显示动作的卡片置于顶层，确保动画不会被遮盖
            cards[beginRow][beginCol].toFront();
            cards[beginRow][beginCol].addAction(moveToAction);
            //moveSound.play();
        }

        @Override
        public void onNumberMerge(int rowAfterMerge, int colAfterMerge, int numAfterMerge, int currentScoreAfterMerger) {
            cards[rowAfterMerge][colAfterMerge].setScale(0.8f);
            SequenceAction sequenceAction = Actions.sequence(
                    Actions.scaleTo(1.2f, 1.2f, 0.1f),
                    Actions.scaleTo(1.0f, 1.0f, 0.1f)
            );
            cards[rowAfterMerge][colAfterMerge].addAction(sequenceAction);
            mergeSound.play();
            //增加当前分数
            getGame2048().getGameScreen().getGameStage().addCurrentScore(numAfterMerge);
        }

        /**
         * 调用GameScreen中的showGameOverStage()方法显示游戏结束舞台，并传入数据模型中的当前分数
         *
         * @param isWin 是否胜利
         */
        @Override
        public void onGameOver(boolean isWin) {
            getGame2048().getGameScreen().showGameOverStage(isWin, coreModel.getCurrentScore());
        }
    }


}
