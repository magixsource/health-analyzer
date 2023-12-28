package gl.linpeng;

import gl.linpeng.health.C;
import gl.linpeng.health.HealthAnalyzer;
import gl.linpeng.health.Hit;

import java.util.List;
import java.util.Map;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        System.out.println("Hello World!");
        //String text = "fa糖尿病更讲究的是饮食疗法，饮食中根据病人的体重和活动量，估计所需总热量，合理安排每天的饮食。含糖量高的食品不吃，含脂肪和淀粉的食品少吃，以吃蔬菜杂粮类为主，配以一定数量的优质蛋白质的食物。1、多食含纤维素的食物2、多吃五谷杂粮3、多食含B族维生素的食物1、禁食胆固醇较高的食物2、忌食糖制甜食3、忌抽烟44、x喝酒";
        String text = "对于患有高血压和湿疹的您，建议遵循以下饮食原则：\n" +
                "1. 限制钠摄入：每日食盐摄入量不超过6克，避免食用过咸食品；\n" +
                "2. 减少饱和脂肪和反式脂肪酸的摄入，例如少吃肥肉、黄油、猪皮等；\n" +
                "3. 多吃富含钾、钙的食物，如蔬菜、水果、豆类、牛奶等；\n" +
                "4. 少吃辛辣刺激性食物，如辣椒、芥末等；\n" +
                "5. 避免饮酒和吸烟。\n" +
                "请注意，以上仅是一般性的建议，具体饮食方案还需根据您的身体状况和医生的指导进行调整。";
        String text2 = "菜单如下：\n" +
                "\n" +
                "主菜：清炒猪心\n" +
                "- 猪心富含蛋白质，有助于补充人体所需的营养物质。\n" +
                "- 清炒的方式能保留猪心的原汁原味，避免过于油腻。\n" +
                "汤品：\n" +
                "- 莲子百合瘦肉汤：润肺养颜，适合湿疹患者食用。\n" +
                "- 鸡蛋西红柿汤：富含维生素C，可帮助降低血压。\n" +
                "- 番茄豆腐汤：番茄含有丰富的抗氧化物质，有利于身体健康。\n" +
                "- 冬瓜排骨汤：冬瓜有利尿作用，适合高血压患者食用。\n" +
                "- 白菜豆腐汤：白菜含有丰富的维生素C和矿物质，有益于身体健康。\n" +
                "- 西红柿鸡蛋汤：富含蛋白质和维生素，简单易做。\n" +
                "- 黄豆海带排骨汤：黄豆富含蛋白质，海带有助于降压。\n" +
                "- 花菇鸡脚汤：花菇具有健脾开胃的作用，鸡脚含有丰富的胶原蛋白。\n" +
                "- 山药枸杞老鸭汤：山药具有补中益气的作用，枸杞滋阴补肾。";
        long begin = System.currentTimeMillis();
        HealthAnalyzer analyzer = new HealthAnalyzer();
        List<Hit> hits = analyzer.analyze(text2);
        List<Hit> foods = analyzer.analyzeFilter(hits, C.WordType.TYPE_FOOD);
        // Map<C.AdverbType,List<Hit>> map = analyzer.analyzePrinciple(hits);
        System.out.println(foods);
        for (Hit hit : foods) {
            System.out.println(hit.getValue());
        }
        System.out.println("======Cost " + (System.currentTimeMillis() - begin));

    }
}
