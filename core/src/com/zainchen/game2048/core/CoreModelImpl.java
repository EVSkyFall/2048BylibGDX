package com.zainchen.game2048.core;

import java.util.Random;

/**
 * 核心游戏逻辑
 *
 * @author Zain Chen
 * @date 2021/4/6 14:13
 */
public class CoreModelImpl implements CoreModel {

    /**
     * 数据的行数
     */
    private final int rowSum;

    /**
     * 数据的列数
     */
    private final int colSum;

    /**
     * 数据监听器
     */
    private DataListener dataListener;

    /**
     * 二维数组数据
     */
    private final int[][] data;

    /**
     * 当前得分
     */
    private int currentScore;

    /**
     * 游戏状态, 默认为游戏状态
     */
    private GameState gameState = GameState.game;

    /**
     * 随机数生成器
     */
    private final Random random;

    public CoreModelImpl(int rowSum, int colSum, DataListener dataListener) {
        this.rowSum = rowSum;
        this.colSum = colSum;
        this.dataListener = dataListener;
        data = new int[rowSum][colSum];
        random = new Random();
    }

    @Override
    public void initData() {

        //所有卡片数字初始化为0
        for (int row = 0; row < rowSum; row++) {
            for (int col = 0; col < colSum; col++) {
                data[row][col] = 0;
            }
        }

        // 重置状态
        currentScore = 0;
        gameState = GameState.game;

        // 随机生成两个数字
        randomGenerateNumber();
        randomGenerateNumber();
    }

    @Override
    public int[][] getData() {
        return data;
    }


    @Override
    public int getCurrentScore() {
        return currentScore;
    }

    @Override
    public void toUp() {
        //若非正在游戏状态时调用该方法，直接忽略
        if (gameState != GameState.game) {
            return;
        }

        //是否有卡片移动或合并的标记
        boolean hasMove = false;

        //竖直方向移动, 依次遍历每一列
        for (int col = 0; col < colSum; col++) {
            //向上移动, 从第 0 行开始依次向下遍历每一行
            for (int row = 0; row < rowSum; row++) {
                //找出当前遍历行 row 下面的首个非空卡片, 将该非空卡片移动到当前 row 行位置
                for (int tmpRow = row + 1; tmpRow < rowSum; tmpRow++) {
                    //判断该位置下面的位置，如果是0，就不需要进行移动或合并操作，继续下一个tmpRow循环，遍历下一个位置
                    if (data[tmpRow][col] == 0) {
                        continue;
                    }
                    //如果遇到下面的row行位置卡片非空，对当前位置进行判断
                    if (data[row][col] == 0) {
                        //如果当前 row 行位置是空的, 则直接移动卡片
                        data[row][col] = data[tmpRow][col];
                        if(dataListener != null){
                            dataListener.onNumberMove(tmpRow,row,col,true);
                        }
                        hasMove = true;
                        //数字移动后原位置清零
                        data[tmpRow][col] = 0;
                        //当前空位置被移入新卡片后，break进入下一次row循环，需要再次对刷新数字的该位置进行判断
                        row--;
                    } else if (data[row][col] == data[tmpRow][col]) {
                        //如果当前row行位置和找到的row下面首个非空卡片的数字相同, 则合并数字
                        data[row][col] += data[tmpRow][col];
                        hasMove = true;
                        //增加分数
                        currentScore += data[row][col];
                        //回调监听
                        if (dataListener != null) {
                            dataListener.onNumberMove(tmpRow,row,col,true);
                            dataListener.onNumberMerge(row, col, data[row][col], currentScore);
                        }
                        //合并后原位置清零
                        data[tmpRow][col] = 0;
                    }
                    //如果数字不相等，进入下一个row循环
                    break;
                }
            }
        }

        //进行滑动操作后需要检测是否游戏结束和生成新数字
        if (hasMove) {
            //校验游戏是否结束（过关或失败）
            checkGameFinish();
            //移动完一次后, 随机生成一个数字
            randomGenerateNumber();
            //防止生成数字后就是不可再移动状态，需要再次检验
            checkGameFinish();
        }
    }

    @Override
    public void toDown() {

        if (gameState != GameState.game) {
            return;
        }

        boolean hasMove = false;

        //从左到右，从下到上
        for (int col = 0; col < colSum; col++) {
            for (int row = rowSum - 1; row > 0; row--) {
                for (int tmpRow = row - 1; tmpRow >= 0; tmpRow--) {
                    if (data[tmpRow][col] == 0) {
                        continue;
                    }
                    if (data[row][col] == 0) {
                        data[row][col] = data[tmpRow][col];
                        if(dataListener != null){
                            dataListener.onNumberMove(tmpRow,row,col,true);
                        }
                        hasMove = true;
                        data[tmpRow][col] = 0;
                        row++;
                    } else if (data[tmpRow][col] == data[row][col]) {
                        data[row][col] += data[tmpRow][col];
                        hasMove = true;
                        currentScore += data[row][col];
                        if (dataListener != null) {
                            dataListener.onNumberMove(tmpRow,row,col,true);
                            dataListener.onNumberMerge(row, col, data[row][col], currentScore);
                        }
                        data[tmpRow][col] = 0;
                    }
                    break;
                }
            }
        }

        if (hasMove) {
            checkGameFinish();
            randomGenerateNumber();
            checkGameFinish();
        }
    }

    @Override
    public void toLeft() {

        if (gameState != GameState.game) {
            return;
        }

        boolean hasMove = false;

        //从上到下，从左到右
        for (int row = 0; row < rowSum; row++) {
            for (int col = 0; col < colSum; col++) {
                for (int tmpCol = col + 1; tmpCol < colSum; tmpCol++) {
                    if (data[row][tmpCol] == 0) {
                        continue;
                    }
                    if (data[row][col] == 0) {
                        data[row][col] = data[row][tmpCol];
                        if(dataListener != null){
                            dataListener.onNumberMove(tmpCol,col,row,false);
                        }
                        hasMove = true;
                        data[row][tmpCol] = 0;
                        col--;
                    } else if (data[row][col] == data[row][tmpCol]) {
                        data[row][col] += data[row][col];
                        hasMove = true;
                        currentScore += data[row][col];
                        if (dataListener != null) {
                            dataListener.onNumberMove(tmpCol,col,row,false);
                            dataListener.onNumberMerge(row, col, data[row][col], currentScore);
                        }
                        data[row][tmpCol] = 0;
                    }
                    break;
                }
            }
        }

        if (hasMove) {
            checkGameFinish();
            randomGenerateNumber();
            checkGameFinish();
        }
    }

    @Override
    public void toRight() {

        if (gameState != GameState.game) {
            return;
        }

        boolean hasMove = false;

        //从上到下，从右到左
        for (int row = 0; row < rowSum; row++) {
            for (int col = colSum - 1; col >= 0; col--) {
                for (int tmpCol = col - 1; tmpCol >= 0; tmpCol--) {
                    if (data[row][tmpCol] == 0) {
                        continue;
                    }
                    if (data[row][col] == 0) {
                        data[row][col] = data[row][tmpCol];
                        if(dataListener != null){
                            dataListener.onNumberMove(tmpCol,col,row,false);
                        }
                        hasMove = true;
                        data[row][tmpCol] = 0;
                        col++;
                    } else if (data[row][col] == data[row][tmpCol]) {
                        data[row][col] += data[row][col];
                        hasMove = true;
                        currentScore += data[row][col];
                        if (dataListener != null) {
                            dataListener.onNumberMove(tmpCol,col,row,false);
                            dataListener.onNumberMerge(row, col, data[row][col], currentScore);
                        }
                        data[row][tmpCol] = 0;
                    }
                    break;
                }
            }
        }

        if (hasMove) {
            checkGameFinish();
            randomGenerateNumber();
            checkGameFinish();
        }

    }

    /**
     * 随机生成数字2或4，20%的概率生成4,80%的概率生成2
     */
    private void randomGenerateNumber() {

        // 计算出空卡片的数量（数字为 0 的卡片）
        int emptyCardsCount = 0;
        for (int row = 0; row < rowSum; row++) {
            for (int col = 0; col < colSum; col++) {
                if (data[row][col] == 0) {
                    emptyCardsCount++;
                }
            }
        }

        //如果没有空卡片，游戏结束
        if (emptyCardsCount == 0) {
            gameState = GameState.gameOver;
            if (dataListener != null) {
                dataListener.onGameOver(false);
            }
        }

        /*
        如果有空卡片，在这些空卡片中随机挑选一个生成数字
         */
        //在所有空位置中随机挑选一个位置生成数字，位置范围0~emptyCardsCount-1
        int newNumPosition = random.nextInt(emptyCardsCount);
        //通过设置的概率生成2，否则生成4
        float newTwoProbability = 0.8f;
        int newNum = random.nextFloat() < newTwoProbability ? 2 : 4;
        //寻找指定空位置，放入生成的数字
        int emptyCardPosition = 0;
        for (int row = 0; row < rowSum; row++) {
            for (int col = 0; col < colSum; col++) {
                //忽略非空卡片
                if (data[row][col] != 0) {
                    continue;
                }
                //指定位置的空卡片, 放入数字
                if (emptyCardPosition == newNumPosition) {
                    data[row][col] = newNum;
                    // 有数字生成, 回调监听
                    if (dataListener != null) {
                        dataListener.onGenerateNumber(row, col, newNum);
                    }
                }
                // 还没有遍历到指定位置的空卡片, 继续遍历
                emptyCardPosition++;
            }
        }
    }

    /**
     * 判断游戏是否结束
     */
    private void checkGameFinish() {
        //判断是否游戏胜利（过关）
        for (int row = 0; row < rowSum; row++) {
            for (int col = 0; col < colSum; col++) {
                //如果有一个卡片拼凑出2048, 游戏即胜利
                if (data[row][col] == 2048) {
                    gameState = GameState.win;
                    if (dataListener != null) {
                        dataListener.onGameOver(true);
                    }
                }
            }
        }
        //若游戏还没有胜利, 则判断是否还可移动
        if (!isRemovable()) {
            //如果游戏没有胜利, 卡片又不可再移动, 则游戏失败
            gameState = GameState.gameOver;
            if (dataListener != null) {
                dataListener.onGameOver(false);
            }
        }
    }

    /**
     * 判断卡片是否可以再移动
     *
     * @return 是否还可以移动
     */
    private boolean isRemovable() {
        //判断水平方式是否可移动
        for (int row = 0; row < rowSum; row++) {
            for (int col = 0; col < colSum-1; col++) {
                if (data[row][col] == 0 || data[row][col + 1] == 0 || data[row][col] == data[row][col + 1]) {
                    return true;
                }
            }
        }
        //判断垂直方向是否可移动
        for (int col = 0; col < colSum; col++) {
            for (int row = 0; row < rowSum - 1; row++) {
                if (data[row][col] == 0 || data[row + 1][col] == 0 || data[row][col] == data[row + 1][col]) {
                    return true;
                }
            }
        }
        return false;
    }

}
