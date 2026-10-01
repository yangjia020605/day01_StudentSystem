/**
 * @author 杨嘉
 * @version 1.0
 * @date 2026-09-29
 * @description 自定义异常类，学生信息已存在
 */

package exception;

public class StudentExistException extends RuntimeException {
    public StudentExistException(String msg) {
        super(msg);
    }
}
