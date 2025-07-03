package stock.tool;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import stock.tool.model.WhiteHorseEntity;

public class WhiteHorseStockTest {

    @Test
    public void testGetHorseType_困境白马() {
        // 创建测试数据：过去3年平均值的一半大于最近2季度利润增速
        WhiteHorseEntity entity = new WhiteHorseEntity();
        
        // 设置扣除非经常性损益后的净利润同比增长率
        // 最近2季度: 10.0%
        entity.setKfNetProfitGrowthRate(10.0f);
        entity.setKfNetProfitGrowthRate(0.0f); // 不使用，因为有第一个值
        // 过去3年: 30%, 40%, 50% (平均值 = 40%)
        entity.setKfNetProfitGrowthRate(30.0f);
        entity.setKfNetProfitGrowthRate(40.0f);
        entity.setKfNetProfitGrowthRate(50.0f);
        
        // avgProfit = 40, avgProfit/2 = 20, latest = 10
        // 20 > 10，所以应该返回0（困境白马）
        int result = WhiteHorseStock.getHorseType(entity);
        assertEquals(0, result, "过去3年平均值的一半(20)大于最近2季度利润增速(10)，应该是困境白马");
    }

    @Test
    public void testGetHorseType_景气白马() {
        // 创建测试数据：过去3年平均值的一半小于等于最近2季度利润增速
        WhiteHorseEntity entity = new WhiteHorseEntity();
        
        // 设置扣除非经常性损益后的净利润同比增长率
        // 最近2季度: 25.0%
        entity.setKfNetProfitGrowthRate(25.0f);
        entity.setKfNetProfitGrowthRate(0.0f); // 不使用
        // 过去3年: 20%, 30%, 40% (平均值 = 30%)
        entity.setKfNetProfitGrowthRate(20.0f);
        entity.setKfNetProfitGrowthRate(30.0f);
        entity.setKfNetProfitGrowthRate(40.0f);
        
        // avgProfit = 30, avgProfit/2 = 15, latest = 25
        // 15 <= 25，所以应该返回1（景气白马）
        int result = WhiteHorseStock.getHorseType(entity);
        assertEquals(1, result, "过去3年平均值的一半(15)小于等于最近2季度利润增速(25)，应该是景气白马");
    }

    @Test
    public void testGetHorseType_边界情况_第一个值为零() {
        // 测试第一个值为0的情况，应该使用第二个值
        WhiteHorseEntity entity = new WhiteHorseEntity();
        
        // 设置扣除非经常性损益后的净利润同比增长率
        entity.setKfNetProfitGrowthRate(0.0f);  // 第一个值为0
        entity.setKfNetProfitGrowthRate(15.0f); // 第二个值，应该被使用
        // 过去3年: 40%, 50%, 60% (平均值 = 50%)
        entity.setKfNetProfitGrowthRate(40.0f);
        entity.setKfNetProfitGrowthRate(50.0f);
        entity.setKfNetProfitGrowthRate(60.0f);
        
        // avgProfit = 50, avgProfit/2 = 25, latest = 15 (使用第二个值)
        // 25 > 15，所以应该返回0（困境白马）
        int result = WhiteHorseStock.getHorseType(entity);
        assertEquals(0, result, "第一个值为0时应该使用第二个值(15)，与过去3年平均值的一半(25)比较，应该是困境白马");
    }

    @Test
    public void testGetHorseType_边界情况_相等() {
        // 测试avgProfit/2正好等于latest的情况
        WhiteHorseEntity entity = new WhiteHorseEntity();
        
        // 设置扣除非经常性损益后的净利润同比增长率
        // 最近2季度: 20.0%
        entity.setKfNetProfitGrowthRate(20.0f);
        entity.setKfNetProfitGrowthRate(0.0f);
        // 过去3年: 30%, 40%, 50% (平均值 = 40%)
        entity.setKfNetProfitGrowthRate(30.0f);
        entity.setKfNetProfitGrowthRate(40.0f);
        entity.setKfNetProfitGrowthRate(50.0f);
        
        // avgProfit = 40, avgProfit/2 = 20, latest = 20
        // 20 == 20，不满足 > 条件，所以应该返回1（景气白马）
        int result = WhiteHorseStock.getHorseType(entity);
        assertEquals(1, result, "过去3年平均值的一半(20)等于最近2季度利润增速(20)，应该是景气白马");
    }

    @Test
    public void testGetHorseType_负增长率() {
        // 测试负增长率的情况
        WhiteHorseEntity entity = new WhiteHorseEntity();
        
        // 设置扣除非经常性损益后的净利润同比增长率
        // 最近2季度: -5.0%
        entity.setKfNetProfitGrowthRate(-5.0f);
        entity.setKfNetProfitGrowthRate(0.0f);
        // 过去3年: 10%, 15%, 20% (平均值 = 15%)
        entity.setKfNetProfitGrowthRate(10.0f);
        entity.setKfNetProfitGrowthRate(15.0f);
        entity.setKfNetProfitGrowthRate(20.0f);
        
        // avgProfit = 15, avgProfit/2 = 7.5, latest = -5
        // 7.5 > -5，所以应该返回0（困境白马）
        int result = WhiteHorseStock.getHorseType(entity);
        assertEquals(0, result, "最近2季度利润增速为负(-5)，过去3年平均值的一半(7.5)大于最近增速，应该是困境白马");
    }
}