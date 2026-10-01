/**
 * @author 杨嘉
 * @version 1.0
 * @date 2026-10-01
 * @description 自定义异常类，学生信息不存在
 */

package exception;

public class StudentNotExistException extends RuntimeException {
    public StudentNotExistException(String msg) {
        super(msg);
    }
}
