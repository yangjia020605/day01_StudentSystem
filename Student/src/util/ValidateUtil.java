/**
 * @author 杨嘉
 * @version 1.0
 * @date 2026-10-01
 * @description 验证工具类，用于对Student实体的成员变量添加约束
 */

package util;

public class ValidateUtil {
    /** 学生姓名
     * 纯中文或纯英文，纯中文长度<=6，不允许中英混合或其他语言 */
    public static void checkName(String name) {
        /* 空体判断 */
        if(name == null) throw new RuntimeException("姓名不能为空");
        /* 约束 */
        // 1. 去除首尾空格，便于以下判断，实际应当允许空格
        name = name.trim();
        // 2. 获取姓名长度
        int len = name.length();
        // 3. 判断是否纯中或纯英
        boolean allChinese = true;
        boolean allEnglish = true;
        // 4. 循环判断每一个字符
        for(int i = 0; i < len; i++) {
            char c = name.charAt(i);
            // 判断是否为中文
            if(!(c >= 0x4e00 && c <= 0x9fa5)) allChinese = false;
            // 判断是否为英文
            if(!(c >= 'a' && c <= 'z') && !(c >= 'A' && c <= 'Z')) allEnglish = false;
        }
        // 5. 分情况讨论
        // 纯中文
        if(allChinese) {
            if(len < 1 || len > 6) throw new RuntimeException("中文姓名长度必须在1~6字符");
            return;
        }
        // 纯英文
        if(allEnglish) return;
        // 其他情况
        throw new RuntimeException("学生姓名必须是纯中文或纯英文，不允许中英混合或其他语言");
    }

    /** 学生年龄
     * 合法范围为：[18, 100] */
    public static void checkAge(int age) {
        if(age >= 18 && age <= 100) return;
        throw new RuntimeException("学生年龄的合法范围为：[18, 100]");
    }

    /** 学生成绩
     * 合法范围为：[0, 100]*/
    public static void checkScore(double score) {
        if(score >= 0.0 && score <= 100) return;
        throw new RuntimeException("学生成绩的合法范围为：[0, 100]");
    }
}
