package com.face_read_clock.tools;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public class NameFirst {

    public static String run(String chineseName) {
        StringBuilder initials = new StringBuilder();
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setCaseType(HanyuPinyinCaseType.UPPERCASE);
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        for (int i = 0; i < chineseName.length(); i++) {
            char c = chineseName.charAt(i);
            try {
                String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(c, format);
                if (pinyinArray != null && pinyinArray.length > 0) {
                    initials.append(pinyinArray[0].charAt(0));
                }
            } catch (BadHanyuPinyinOutputFormatCombination e) {
                e.printStackTrace();
            }
        }
        return initials.toString();
    }

    public static void main(String[] args) {
        String name = "张三";
        String initials = run(name);
        System.out.println(initials); // 输出: ZS
    }
}
