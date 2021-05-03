package com.zainchen.game2048.core;

/**
 * 核心数据模型接口
 *
 * @author Zain Chen
 * @date 2021/4/6 14:11
 */
public interface CoreModel {
    /**
     * 游戏状态枚举
     */
    enum GameState {
        //正在游戏状态
        game,
        // 游戏胜利（过关）状态
        win,
        //游戏失败状态
        gameOver;
    }


    /**
     * 数据初始化创建一个数据模型实例，开始游戏时调用该方法
     */
    void initData();

    /**
     * 获取4*4的二维数组
     *
     * @return 生成的二维数组
     */
    int[][] getData();

    /**
     * 获取当前分数
     *
     * @return 当前分数
     */
    int getCurrentScore();

    /**
     * 向上滑动
     */
    void toUp();

    /**
     * 向下滑动
     */
    void toDown();

    /**
     * 向左滑动
     */
    void toLeft();

    /**
     * 向右滑动
     */
    void toRight();

    /**
     * 数据监听器，监听到数据变化后播放相应的动画
     */
    interface DataListener {
        /**
         * GridGroup随机生成数字时调用
         *
         * @param row 生成的数字所在行
         * @param col 生成的数字所在列
         * @param num 生成的数字
         */
        void onGenerateNumber(int row, int col, int num);

        /**
         * 数字移动时调用
         *
         * @param begin 移动开始的行/列
         * @param end   移动结束的行/列
         * @param col   列
         */
        void onNumberMove(int begin, int end,int col,boolean isUpOrDown);

        /**
         * 两个数字合并时调用
         *
         * @param rowAfterMerge           合并后数字所在行
         * @param colAfterMerge           合并后数字所在列
         * @param numAfterMerge           合并后的数字
         * @param currentScoreAfterMerger 合并后的当前分数
         */
        void onNumberMerge(int rowAfterMerge, int colAfterMerge, int numAfterMerge, int currentScoreAfterMerger);

        /**
         * 游戏结束时调用
         *
         * @param isWin 是否胜利（过关）
         */
        void onGameOver(boolean isWin);
    }

    /**
     * 创建指定行列数的数据模型
     */
    class Builder {
        public static CoreModel createDataModel(int rowSum, int colSum, DataListener dataListener) {
            return new CoreModelImpl(rowSum, colSum, dataListener);
        }
    }
}
