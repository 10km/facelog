package net.gdface.facelog;

/** 令牌操作 */
public enum TokenOp {
	/** 未初始化 */UNINITIALIZED,
	/** 设备注册  */REGISTER,
	/** 设备注销 */UNREGISTER,
	/** 申请令牌 */APPLY,
	/** 释放令牌 */RELEASE,
	/** 验证令牌 */VALIDATE,
	/** 验证密码 */VALIDPWD;
	/** 指定为上下文{@link TokenContext}中的令牌操作类型 */
}