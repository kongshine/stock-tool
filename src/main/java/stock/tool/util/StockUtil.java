package stock.tool.util;

import stock.tool.model.WhiteHorseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class StockUtil {

    private static final String[] cyckeys={"汽车零部件","汽车整车","建筑装饰","建筑材料","房地产","钢铁"};
    /**
     * 判断是否是周期股
     * @param industry
     * @return
     */
    public static boolean isCyclical(String industry){
        for(String key : cyckeys){
            if(industry.contains(key)){
                return true;
            }
        }
        return false;
    }

    /**
     * 是否是白马股
     * @return
     */
    public static boolean isWhiteHorseStock(WhiteHorseEntity e){
        boolean isCycStock = StockUtil.isCyclical(e.getIndustry());
        if(isCycStock){
            System.out.println("剔除周期股："+e.getStockName());
            return false;
        }

        //过滤掉增长下滑
        List<Float> growthRate = new ArrayList<>();
        growthRate.addAll(e.getNetProfitGrowthRate());
        growthRate.addAll(e.getRevenueGrowthRate());
        for (Float gr : growthRate){
            if(gr < 0){
                System.out.println("剔除过滤掉增长下滑："+e.getStockName());
                return false;
            }
        }
        //银行股直接过
        if(e.getIndustryType().intValue() == 1){
            return true;
        }
        //判定一,二
        List<Double> or = new ArrayList<>(e.getOpRevenue());
        List<Double> ar = new ArrayList<>(e.getAcctReceivable());
        List<Double> iv = new ArrayList<>(e.getInventory());
        boolean ispass = bearPredicate(or,ar) && bearPredicate(or,iv);
        if (!ispass){
            System.out.println("判定一,二剔除："+e.getStockName());
            return false;
        }
        //判定三
        List<Float> ratios = new ArrayList<>(e.getLqRatio());
        int risk=0;
        for(Float r: ratios){
            if(r<1)
                risk++;
        }
        if(risk > 2){
            System.out.println("判定三剔除："+e.getStockName());
            return false;
        }
        return true;
    }

    //未考虑银行股
    public static List<WhiteHorseEntity> getWhStock( List<WhiteHorseEntity> wheList){
        return wheList.stream()
                .filter(e -> {//过滤周期股
                    boolean isCycStock = StockUtil.isCyclical(e.getIndustry());
                    if(isCycStock)
                        System.out.println("剔除周期股："+e.getStockName());
                    return !isCycStock;
                })
                .filter(e->{//过滤掉增长下滑
                    List<Float> growthRate = new ArrayList<>();
                    growthRate.addAll(e.getNetProfitGrowthRate());
                    growthRate.addAll(e.getRevenueGrowthRate());
                    for (Float gr : growthRate){
                        if(gr < 0){
                            System.out.println("剔除过滤掉增长下滑："+e.getStockName());
                            return false;
                        }
                    }
                    return true;
                }).filter(e->{//判定一,二
            List<Double> or = new ArrayList<>(e.getOpRevenue());
            List<Double> ar = new ArrayList<>(e.getAcctReceivable());
            List<Double> iv = new ArrayList<>(e.getInventory());
            boolean ispass = bearPredicate(or,ar) && bearPredicate(or,iv);
            if (!ispass)
                System.out.println("判定一,二剔除："+e.getStockName());
            return ispass;
        }).filter(e->{//判定三
            List<Float> ratios = new ArrayList<>(e.getLqRatio());
            int risk=0;
            for(Float r: ratios){
                if(r<1)
                    risk++;
            }
            if(risk > 2){
                System.out.println("判定三剔除："+e.getStockName());
                return false;
            }
            return true;
        }).collect(Collectors.toList());
    }


    private static boolean bearPredicate(List<Double> l1,List<Double> l2 ){
        if(l1.get(0)-l1.get(1) < l2.get(0)-l2.get(1) &&
                l1.get(1)-l1.get(2) < l2.get(1)-l2.get(2))
            return false;
        if(l1.get(1)-l1.get(2) < l2.get(1)-l2.get(2) &&
                l1.get(2)-l1.get(3) < l2.get(2)-l2.get(3))
            return false;
        return true;
    }
}
