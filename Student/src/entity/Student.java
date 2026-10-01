/**
 * @author 杨嘉
 * @version 1.0
 * @date 2026-09-29
 * @description 学生实体类，封装学生学号、姓名、年龄、分数信息
 */

package entity;

import static util.ValidateUtil.*;

public class Student {
    /** 学生编号
     * 学院编号+入学年份+班级编号+班内编号（后续版本添加） */
    private String id;
    /** 学生姓名
     * 纯中文或纯英文，纯中文长度<=6，不允许中英混合或其他语言 */
    private String name;
    /** 学生年龄
     * 合法范围为：[18, 100] */
    private int age;
    /** 学生成绩
     * 合法范围为：[0, 100]*/
    private double score;

    /** 无参构造 */
    public Student() {
    }
    /** 全参构造 */
    public Student(String id, String name, int age, double score) {
        this.id = id;
        /* 借用set函数，避免重复检验代码 */
        setName(name);
        setAge(age);
        setScore(score);
    }

    /** getter、setter */
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        checkName(name);
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        checkAge(age);
        this.age = age;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        checkScore(score);
        this.score = score;
    }

    /** toString */
    @Override
    public String toString() {
        return id + "\t" + name + "\t" + age + "\t" + score;
    }
}
