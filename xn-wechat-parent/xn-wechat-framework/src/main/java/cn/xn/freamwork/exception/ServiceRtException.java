
package cn.xn.freamwork.exception;

/**
 * 服务层全局异常类
 *
 * @author lcl 2014/08/21
 * @version 1.0.0
 */
public class ServiceRtException extends XnRunTimeException
{
    private static final long serialVersionUID = -9179643660691799635L;

    public ServiceRtException () { super(); };

    public ServiceRtException (Throwable cause) { super(cause); };

    public ServiceRtException(String msg) {
        super(XnException.CODE_DEFAULT, msg);
    }

    public ServiceRtException(String code, String msg) {
        super(code, msg);
    }

    public ServiceRtException(Throwable cause, String msg) {
        super(cause, XnException.CODE_DEFAULT, msg);
    }
}
