package gl.linpeng.health;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Health analyzer class
 */
public class HealthAnalyzer {
    Map<String, List<Hit>> hitMap = new HashMap<>();

    /**
     * Actor to deliver
     *
     * @param text
     * @return
     */
    public List<Hit> analyze(String text) {
        String content = text.toLowerCase();
        List<Hit> result = new ArrayList<>();
        Dictionary dictionary = new Dictionary();
        for (String key : dictionary.dictContext.keySet()) {
            C.WordType wordType = getWordType(key);
            List<String> dict = dictionary.dictContext.get(key);
            // mode 1 : basic
            for (String dictValue : dict) {
                if (content.contains(dictValue)) {
                    //determine create or not
                    int begin = content.indexOf(dictValue);
                    int end = begin + dictValue.length() - 1;
                    boolean isValid = validateHit(result, dictValue, begin, end);

                    if (isValid) {
                        // create a new hit
                        Hit hit = new Hit();
                        hit.setWordType(wordType);
                        hit.setValue(dictValue);
                        hit.setBegin(begin);
                        hit.setEnd(end);
                        for (int i = begin; i < end + 1; i++) {
                            String index = "index_" + i;
                            List<Hit> list = hitMap.get(index);
                            if (list == null) {
                                list = new ArrayList<>();
                            }
                            list.add(hit);
                            hitMap.put(index, list);
                        }

                        result.add(hit);
                    }
                    // rev
                    String subText = content.substring(end + 1);
                    revVisit(subText, dictValue, end, wordType, result);
                }
            }

        }
        result.sort(Comparator.comparingInt(Hit::getBegin));
        return result;
    }

    private boolean validateHit(List<Hit> result, String dictValue, int begin, int end) {
        for (int i = begin; i < end + 1; i++) {
            String index = "index_" + i;
            List<Hit> list = hitMap.get(index);
            if (list != null) {
                for (Hit hit : list) {
                    if (hit.getValue().length() > dictValue.length()) {
                        System.out.println("=========匹配度低，该Hit被忽略：" + dictValue + "位置:" + begin + "-" + end);
                        return false;
                    } else {
                        result.remove(hit);
                        //list.remove(hit);
                    }
                }
            }
        }
        return true;
    }

    private void revVisit(String text, String dictValue, int cursor, C.WordType wordType, List<Hit> result) {
        if (text.contains(dictValue)) {
            //determine create or not
            int orginBegin = text.indexOf(dictValue);
            int orginEnd = orginBegin + dictValue.length() - 1;
            int begin = orginBegin + cursor;
            int end = orginEnd + cursor;

            boolean isValid = validateHit(result, dictValue, orginBegin, orginEnd);
            if (isValid) {
                // create a new hit
                Hit hit = new Hit();
                hit.setWordType(wordType);
                hit.setValue(dictValue);

                hit.setBegin(begin);
                hit.setEnd(end);
                for (int i = begin; i < end + 1; i++) {
                    String index = "index_" + i;
                    List<Hit> list = hitMap.get(index);
                    if (list == null) {
                        list = new ArrayList<>();
                    }
                    list.add(hit);
                    hitMap.put(index, list);
                }
                result.add(hit);
            }

            // rev
            String subText = text.substring(orginEnd + 1);
            revVisit(subText, dictValue, end, wordType, result);
        }
    }

    private C.WordType getWordType(String key) {
        C.WordType wordType = null;
        if ("DISEASE".equalsIgnoreCase(key)) {
            wordType = C.WordType.TYPE_DISEASE;
        } else if ("FOOD".equalsIgnoreCase(key)) {
            wordType = C.WordType.TYPE_FOOD;
        } else if ("NUTRIENT".equalsIgnoreCase(key)) {
            wordType = C.WordType.TYPE_NUTRIENT;
        } else if ("ADVERB".equalsIgnoreCase(key)) {
            wordType = C.WordType.TYPE_ADVERB;
        }
        return wordType;
    }


    /**
     * 分析饮食原则
     *
     * @param hits 词组
     * @return 多、少、禁
     */
    public Map<C.AdverbType, List<Hit>> analyzePrinciple(List<Hit> hits) {
        Map<C.AdverbType, List<Hit>> result = new HashMap<>();
        List<Hit> adverbMoreList = new ArrayList<>();
        List<Hit> adverbLessList = new ArrayList<>();
        List<Hit> adverbForbiddenList = new ArrayList<>();
        Hit lastHit = null;
        for (Hit hit : hits) {
            if (C.WordType.TYPE_ADVERB.equals(hit.getWordType())) {
                if (lastHit != null && hit.getValue().contentEquals(lastHit.getValue())) {
                    // 同义词
                    System.out.println("同义词,执行忽略操作" + hit.getValue());
                } else {
                    // 新的风暴已经形成
                    lastHit = hit;
                }

            } else if (C.WordType.TYPE_FOOD.equals(hit.getWordType()) || C.WordType.TYPE_NUTRIENT.equals(hit.getWordType())) {
                if (lastHit != null) {
                    // 将hit.getValue作为条件用于switch判断
                    switch (lastHit.getValue()) {
                        case "多":
                        case "宜":
                            adverbMoreList.add(hit);
                            break;
                        case "少":
                        case "限制":
                        case "低":
                            adverbLessList.add(hit);
                            break;
                        case "禁":
                        case "忌":
                        case "不":
                        case "避免":
                            adverbForbiddenList.add(hit);
                            break;
                        default:
                            System.out.println("Unknown adverb type: " + lastHit.getValue());
                    }
                } else {
                    System.out.println("No adverb,Just ignore " + hit.getValue());
                }
            }

        }
        result.put(C.AdverbType.TYPE_MORE, adverbMoreList);
        result.put(C.AdverbType.TYPE_LESS, adverbLessList);
        result.put(C.AdverbType.TYPE_FORBIDDEN, adverbForbiddenList);
        return result;
    }

    public List<Hit> analyzeFilter(List<Hit> hits, C.WordType wordType) {
        return hits.stream()
                .filter(hit -> hit.getWordType().equals(wordType))
                .collect(Collectors.toList());
    }
}
