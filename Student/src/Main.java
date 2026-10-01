/**
 * @author 杨嘉
 * @version 1.0
 * @date 2026-10-01
 * @description 学生信息管理系统主入口，控制台实现增删改查，文件持久化
 * 备注（我不知道备注的标签）：为了区分辅助函数，我使用了下划线开头以命名
 */

import entity.Student;
import exception.StudentExistException;
import exception.StudentNotExistException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    /** 创建集合，用于读取学生信息 */
    static List<Student> StudentList = new ArrayList<>();
    /** 直接创建全局静态输入流对象，避免多次创建输入流对象 */
    static Scanner sc = new Scanner(System.in);
    /** 写入写出文件名 */
    static final String FILE_NAME = "student.txt";
    /** 判断是否保存 */
    static boolean isSave = true;
    /** 主函数 */
    public static void main(String[] args) {
        // 程序启动，自动加载文件
        try {
            loadFromFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // 选项面版及其实现
        while(true) {
            // 选项面版
            System.out.println("===== 学生信息管理系统 =====");
            System.out.println("1. 添加学生");
            System.out.println("2. 删除学生（按学号）");
            System.out.println("3. 修改学生信息（按学号）");
            System.out.println("4. 查询全部学生");
            System.out.println("5. 查询学生（按学号）");
            System.out.println("6. 查询学生（按姓名）");
            System.out.println("7. 保存学生信息到文件");
            System.out.println("8. 从文件加载学生数据");
            System.out.println("9. 清空数据");
            System.out.println("0. 退出系统");
            System.out.println("============================");
            System.out.print("请输入要使用的功能:");
            int op = sc.nextInt();
            /* 吸收换行符及多余字符 */
            sc.nextLine();
            try {
                switch(op) {
                    case 1:
                        /* 添加学生 */
                        addStudent();
                        break;
                    case 2:
                        /* 删除学生（按学号） */
                        deleteStudent();
                        break;
                    case 3:
                        /* 修改学生信息（按学号） */
                        updateStudent();
                        break;
                    case 4:
                        /* 查询全部学生 */
                        showAll();
                        break;
                    case 5:
                        /* 查询学生（按学号） */
                        queryById();
                        break;
                    case 6:
                        /* 查询学生（按姓名） */
                        queryByName();
                        break;
                    case 7:
                        /* 保存学生信息到文件 */
                        saveToFile();
                        break;
                    case 8:
                        /* 从文件加载学生数据 */
                        loadFromFile();
                        break;
                    case 9:
                        /* 清空数据 */
                        clear();
                        break;
                    case 0:
                        /* 退出系统 */
                        /* 确认退出 */
                        if(!isSave) {
                            System.out.println("您还有修改暂未保存，您确认要继续退出吗");
                        }
                        System.out.println("如确认退出请输入'y'或'Y'，不退出则输入此外的任意键返回菜单栏");
                        String confirm = sc.nextLine();
                        if(!confirm.isEmpty() && (confirm.charAt(0) == 'y' || confirm.charAt(0) == 'Y')) {
                            System.exit(0);
                        }
                        break;
                    default:
                        /* 输入错误 */
                        throw new RuntimeException("非法输入/错误输入");
                }
            } catch (Exception e) {
                System.out.println("操作失败: " + e.getMessage());
            }
        }
    }

    /** 辅助方法：判断学号是否存在 */
    private static boolean _isIdExist(String id) {
        /*
        for(Student s : StudentList) {
            if(s.getId().equals(id)) return true;
        }
        */

        // 复用queryById
        return _queryById(id) != null;
    }
    /* 辅助方法：按照学号查询学生 */
    private static Student _queryById(String id) {
        for(Student s : StudentList) {
            if(s.getId().equals(id)) return s;
        }
        return null;
    }

    /** 1. 添加学生 */
    static void addStudent() {
        System.out.print("请输入学生ID:");
        String id = String.valueOf(sc.nextInt());
        sc.nextLine();
        /* 判断该id是否已存在 */
        if(_isIdExist(id)) {
            throw new StudentExistException("要添加的学生学号已存在");
        }
        System.out.print("请输入学生姓名:");
        String name = sc.nextLine();
        /* 姓名合法性检验 */
        // 在实体类里统一检验
        System.out.print("请输入学生年龄:");
        int age = sc.nextInt();
        sc.nextLine();
        /* 年龄合法性检验 */
        // 在实体类里统一检验
        System.out.print("请输入学生分数:");
        double score = sc.nextDouble();
        sc.nextLine();
        /* 分数合法性检验 */
        // 在实体类里统一检验
        Student stu = new Student(id, name, age, score);
        /* 确认添加 */
        System.out.println(stu.toString());
        System.out.println("请确认要添加的学生信息，如确认添加请输入'y'或'Y'，不添加则输入此外的任意键返回菜单栏");
        String confirm = sc.nextLine();
        if(!confirm.isEmpty() && (confirm.charAt(0) == 'y' || confirm.charAt(0) == 'Y')) {
            StudentList.add(stu);
            isSave = false;
            System.out.println("添加成功");
        }
    }

    /** 2. 删除学生（按学号） */
    static void deleteStudent() {
        System.out.print("请输入要删除的学生ID:");
        String id = String.valueOf(sc.nextInt());
        sc.nextLine();
        /* 根据ID查询学生 */
        Student stu = _queryById(id);
        /* 判断该id是否不存在 */
        if(stu == null) {
            throw new StudentNotExistException("要删除的学生学号不存在");
        }
        /* 确认删除 */
        System.out.println(stu.toString());
        System.out.println("请确认要删除的学生信息，如确认删除请输入'y'或'Y'，不删除则输入此外的任意键返回菜单栏");
        String confirm = sc.nextLine();
        if(!confirm.isEmpty() && (confirm.charAt(0) == 'y' || confirm.charAt(0) == 'Y')) {
            StudentList.remove(stu);
            isSave = false;
            System.out.println("删除成功");
        }
    }

    /** 3. 修改学生信息（按学号） */
    static void updateStudent() {
        System.out.print("请输入要修改的学生ID:");
        String id = String.valueOf(sc.nextInt());
        sc.nextLine();
        /* 根据ID查询学生 */
        Student stu = _queryById(id);
        /* 判断该id是否不存在 */
        if(stu == null) {
            throw new StudentNotExistException("要修改的学生学号不存在");
        }
        /* 确认修改 */
        System.out.println(stu.toString());
        System.out.println("请确认要修改的学生信息，如确认修改请输入'y'或'Y'，不修改则输入此外的任意键返回菜单栏");
        String confirm = sc.nextLine();
        if(!confirm.isEmpty() && (confirm.charAt(0) == 'y' || confirm.charAt(0) == 'Y')) {
            /* 录入修改后信息 */
            System.out.print("请输入学生新ID:");
            String newId = String.valueOf(sc.nextInt());
            sc.nextLine();
            /* 判断该id是否已存在(先判断id是否有修改，若无修改则无需再判断是否存在) */
            if(!newId.equals(id) && _isIdExist(newId)) {
                throw new StudentExistException("要添加的学生学号已存在");
            }
            System.out.print("请输入学生新姓名:");
            String newName = sc.nextLine();
            /* 姓名合法性检验 */
            // 。。。
            System.out.print("请输入学生新年龄:");
            int newAge = sc.nextInt();
            sc.nextLine();
            /* 年龄合法性检验 */
            // 。。。
            System.out.print("请输入学生新分数:");
            double newScore = sc.nextDouble();
            sc.nextLine();
            /* 分数合法性检验 */
            // 。。。

            /* 统一修改，避免录入过程中意外中断 */
            stu.setId(newId);
            stu.setName(newName);
            stu.setAge(newAge);
            stu.setScore(newScore);
            isSave = false;
            System.out.println("修改成功");
        }
    }

    /** 4. 查询全部学生 */
    static void showAll() {
        /* 处理空表情况 */
        if(StudentList.isEmpty()) {
            System.out.println("当前表内无学生");
        }
        /* 直接遍历输出 */
        for(Student s : StudentList) {
            System.out.println(s.toString());
        }
        /* 设置停止语句，避免控制台输出过快，使菜单覆盖查询内容 */
        System.out.print("输入任意字符结束查询:");
        sc.nextLine();
        System.out.println("查询完成");
    }

    /** 5. 查询学生（按学号） */
    static void queryById() {
        System.out.print("请输入要查询的学生ID:");
        String id = String.valueOf(sc.nextInt());
        sc.nextLine();
        /* 根据ID查询学生 */
        Student stu = _queryById(id);
        /* 判断该id是否不存在 */
        if(stu == null) {
            throw new StudentNotExistException("要查询的学生学号不存在");
        }
        /* 输出查询内容 */
        System.out.println(stu.toString());
        /* 设置停止语句，避免控制台输出过快，使菜单覆盖查询内容 */
        System.out.print("输入任意字符结束查询:");
        sc.nextLine();
        System.out.println("查询完成");
    }

    /** 6. 查询学生（按姓名） */
    static void queryByName() {
        System.out.print("请输入要查询的学生姓名:");
        String name = sc.nextLine();
        /* 判断是否存在 */
        boolean isExist = false;
        /* 直接遍历并输出 */
        for(Student s : StudentList) {
            if(s.getName().equals(name)) {
                System.out.println(s);
                isExist = true;
            }
        }
        /* 处理不存在的情况 */
        if(!isExist) {
            throw new StudentNotExistException("要查询的学生姓名不存在");
        }
        /* 设置停止语句，避免控制台输出过快，使菜单覆盖查询内容 */
        System.out.print("输入任意字符结束查询:");
        sc.nextLine();
        System.out.println("查询完成");
    }

    /** 7. 保存学生信息到文件 */
    static void saveToFile() throws IOException {
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME))) {
            /* 保存 */
            for(Student s : StudentList) {
                bw.write(s.toString());
                /* 换行 */
                bw.newLine();
            }
            /* 关闭缓冲流 */
            bw.close();
            isSave = true;
            System.out.println("保存完成");
        }
    }

    /** 8. 从文件加载学生数据 */
    static void loadFromFile() throws IOException {
        /* 确认加载 */
        if(!isSave) {
            System.out.println("您还有修改暂未保存，加载将会清空修改，您确认要继续加载吗");
            System.out.println("如确认加载请输入'y'或'Y'，不加载则输入此外的任意键返回菜单栏");
            String confirm = sc.nextLine();
            if(!confirm.isEmpty() && (confirm.charAt(0) == 'y' || confirm.charAt(0) == 'Y')) {}
            else return;
        }
        StudentList.clear();
        File file = new File(FILE_NAME);
        /* 处理文件不存在的情况，当文件不存在时，直接调用保存函数创建文件，并直接返回 */
        if(!file.exists()) {
            try {
                saveToFile();
            } catch (IOException e) {
                System.out.println("新建文件失败："+e.getMessage());
            }
            return;
        }
        /* 加载 */
        try(BufferedReader br = new BufferedReader(new FileReader(file))) {
            /* 加载 */
            String stu;
            while(((stu = br.readLine()) != null)) {
                String[] student = stu.split("\t");
                Student s = new Student(student[0], student[1], Integer.parseInt(student[2]), Double.parseDouble(student[3]));
                StudentList.add(s);
            }
            /* 关闭缓冲流 */
            br.close();
            System.out.println("加载完成");
        }
    }

    /* 9. 清空数据 */
    static void clear() {
        System.out.println("请确认要清空数据，如确认清空请输入'y'或'Y'，不清空则输入此外的任意键返回菜单栏");
        System.out.println("清空后如不保存，文件中信息将任然保留");
        String confirm = sc.nextLine();
        if(!confirm.isEmpty() && (confirm.charAt(0) == 'y' || confirm.charAt(0) == 'Y')) {
            StudentList.clear();
            System.out.println("清空完成");
        }
    }
}