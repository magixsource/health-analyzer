package gl.linpeng.health;

/**
 * Hit class
 */
public class Hit {
    /**
     * 字符内容
     */
    private String value;
    /**
     * 词性类型：TYPE_DISEASE\TYPE_FOOD...
     */
    private C.WordType wordType;
    /**
     * 字符在文本中的开始位置索引
     */
    private int begin;
    /**
     * 字符在文本中的结束位置索引
     */
    private int end;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public C.WordType getWordType() {
        return wordType;
    }

    public void setWordType(C.WordType wordType) {
        this.wordType = wordType;
    }

    public int getBegin() {
        return begin;
    }

    public void setBegin(int begin) {
        this.begin = begin;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }
}
