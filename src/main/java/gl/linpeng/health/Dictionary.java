package gl.linpeng.health;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Dictionary {

    Map<String, List> dictContext = new HashMap<>();

    public Dictionary() {
        loadDict();
    }

    private void loadDict() {
        loadDict("dic/food.dic");
        loadDict("dic/disease.dic");
        loadDict("dic/adverb.dic");
        loadDict("dic/nutrient.dic");
    }

    private void loadDict(String path) {
        String dictName = path.split("\\/")[1].replaceAll(C.DICT_SUFFIX, "").toUpperCase();
        List<String> dictValues = new ArrayList<>();
        // load dict into list
        InputStream is = null;
        try {
            is = this.getClass().getClassLoader().getResourceAsStream(path);
            BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"), 128);
            String word = null;
            do {
                word = br.readLine();
                if (word != null && !"".equals(word.trim())) {
                    // add dict value to list
                    dictValues.add(word.trim().toLowerCase());
                }
            } while (word != null);
        } catch (IOException e) {
            System.err.println("Dictionary loading exception.");
            e.printStackTrace();
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // put dict list to map
        dictContext.put(dictName, dictValues);
    }


}
