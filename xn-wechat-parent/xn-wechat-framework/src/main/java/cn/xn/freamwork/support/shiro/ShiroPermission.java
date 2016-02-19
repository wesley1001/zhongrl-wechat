package cn.xn.freamwork.support.shiro;

import org.apache.commons.collections.MapUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.velocity.tools.Scope;
import org.apache.velocity.tools.config.DefaultKey;
import org.apache.velocity.tools.config.ValidScope;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.Collection;
import java.util.Map;

/**
 * velocity ShiroPermission
 */
@SuppressWarnings("UnusedDeclaration")
@DefaultKey("shiro")
@ValidScope(Scope.APPLICATION)
public class ShiroPermission {

	private static final String ROLE_NAMES_DELIMITER = ",";
	private static final String PERMISSION_NAMES_DELIMITER = ",";

	private static final Logger logger = LoggerFactory.getLogger(ShiroPermission.class);


	/**
	 * 验证是否为已认证通过的用户，不包含已记住的用户，这是与 isUser 标签方法的区别所在。
	 *
	 * @return is isAuthenticated
	 */
	public static boolean isAuthenticated() {
		Subject subject = SecurityUtils.getSubject();
		return subject != null && subject.isAuthenticated();
	}

	/**
	 * 验证是否为未认证通过用户，与 isAuthenticated 标签相对应，与 isGuest 标签的区别是，该标签包含已记住用户。
	 */
	public static boolean isNotAuthenticated() {
		Subject subject = SecurityUtils.getSubject();
		return subject == null || !subject.isAuthenticated();
	}

	/**
	 * 验证当前用户是否为“访客”，即未认证（包含未记住）的用户。
	 */
	public static boolean isGuest() {
		Subject subject = SecurityUtils.getSubject();
		return subject == null || subject.getPrincipal() == null;
	}

	/**
	 * 验证当前用户是否认证通过或已记住的用户。
	 */
	public static boolean isUser() {
		try {
			Subject subject = SecurityUtils.getSubject();
			return subject != null && subject.getPrincipal() != null;
		}
		catch (Exception e) {
			logger.warn("",e);
			return false;
		}
	}

	/**
	 * 获取当前用户 Principal。
	 */
	public static Object getPrincipal() {
		Subject subject = SecurityUtils.getSubject();
		if (subject != null) {
			return subject.getPrincipal();
		}

		return null;
	}

	/**
	 * 获取当前用户属性。
	 */
	public static Object getPrincipalProperty(String property) throws Exception {
		Subject subject = SecurityUtils.getSubject();
		Object value = null;

		if (subject != null) {
			Object principal = subject.getPrincipal();

			BeanInfo bi = Introspector.getBeanInfo(principal.getClass());

			boolean foundProperty = false;
			for (PropertyDescriptor pd : bi.getPropertyDescriptors()) {
				if (pd.getName().equals(property)) {
					value = pd.getReadMethod().invoke(principal, (Object[]) null);
					foundProperty = true;
					break;
				}
			}

			if (!foundProperty) {
				if(logger.isTraceEnabled())
				logger.trace("Property [" + property + "] not found in principal of type [" + principal.getClass().getName() + "]");
			}
		}

		return value;
	}

	/**
	 * 验证当前用户是否属于该角色 。
	 */
	public static boolean hasRole(String role) {
		Subject subject = SecurityUtils.getSubject();
		return subject != null && subject.hasRole(role);
	}

	/**
	 * 验证当前用户是否不属于该角色，与 hasRole 逻辑相反。
	 */
	public static boolean lacksRole(String role) {
		return !hasRole(role);
	}

	/**
	 * 验证当前用户是否属于以下任意一个角色。
	 */
	public static boolean hasAnyRoles(String roleNames, String delimeter) {
		Subject subject = SecurityUtils.getSubject();
		if (subject != null) {
			if (delimeter == null || delimeter.length() == 0) {
				delimeter = ROLE_NAMES_DELIMITER;
			}

			for (String role : roleNames.split(delimeter)) {
				if (subject.hasRole(role.trim())) {
					return true;
				}
			}
		}

		return false;
	}

	/**
	 * 验证当前用户是否属于以下任意一个角色。
	 */
	public static boolean hasAnyRoles(String roleNames) {
		return hasAnyRoles(roleNames, ROLE_NAMES_DELIMITER);
	}

	/**
	 * 验证当前用户是否属于以下任意一个角色。
	 */
	public static boolean hasAnyRoles(Collection<String> roleNames) {
		Subject subject = SecurityUtils.getSubject();

		if (subject != null && roleNames != null) {
			for (String role : roleNames) {
				if (role != null && subject.hasRole(role.trim())) {
					return true;
				}
			}
		}

		return false;
	}

	/**
	 * 验证当前用户是否属于以下任意一个角色。
	 */
	public static boolean hasAnyRoles(String[] roleNames) {
		Subject subject = SecurityUtils.getSubject();

		if (subject != null && roleNames != null) {
			for (String role : roleNames) {
				if (role != null && subject.hasRole(role.trim())) {
					return true;
				}
			}
		}

		return false;
	}

	/**
	 * 验证当前用户是否拥有指定权限
	 */
	public static boolean hasPermission(String permission) {
		Subject subject = SecurityUtils.getSubject();
		return subject != null && subject.isPermitted(permission);
	}

	/**
	 * 验证当前用户是否不拥有指定权限，与 hasPermission 逻辑相反。
	 */
	public static boolean lacksPermission(String permission) {
		return !hasPermission(permission);
	}

	/**
	 * 验证当前用户是否拥有以下任意一个权限。
	 */
	public static boolean hasAnyPermissions(String permissions, String delimeter) {
		Subject subject = SecurityUtils.getSubject();
		if (subject != null) {
			if (delimeter == null || delimeter.length() == 0) {
				delimeter = PERMISSION_NAMES_DELIMITER;
			}

			for (String permission : permissions.split(delimeter)) {
				if (permission != null && subject.isPermitted(permission.trim())) {
					return true;
				}
			}
		}

		return false;
	}

	/**
	 * 验证当前用户是否拥有以下任意一个权限。
	 */
	public static boolean hasAnyPermissions(String permissions) {
		return hasAnyPermissions(permissions, PERMISSION_NAMES_DELIMITER);
	}

	/**
	 * 验证当前用户是否拥有以下任意一个权限。
	 */
	public static boolean hasAnyPermissions(Collection<String> permissions) {
		Subject subject = SecurityUtils.getSubject();

		if (subject != null && permissions != null) {
			for (String permission : permissions) {
				if (permission != null && subject.isPermitted(permission.trim())) {
					return true;
				}
			}
		}

		return false;
	}

	/**
	 * 验证当前用户是否拥有以下任意一个权限。
	 */
	public static boolean hasAnyPermissions(String[] permissions) {
		Subject subject = SecurityUtils.getSubject();

		if (subject != null && permissions != null) {
			for (String permission : permissions) {
				if (permission != null && subject.isPermitted(permission.trim())) {
					return true;
				}
			}
		}

		return false;
	}

	/**
	 * 获得一个属性值
	 */
	@SuppressWarnings ( "unchecked" )
	public static String getAttribute(String key) {
		Object obj=getAttributeObject(key);
		if(obj==null) return null;
		return String.valueOf(obj);
	}

	public static Object getAttributeObject(String key){
		return getAttrMap().get(key);
	}

    /**
     * 模拟参数
     * @param key
     * @param obj
     * @return
     */
    public static Object setAttributeObject(String key, Object obj){
        return getAttrMap().put(key, obj);
    }

	/**
	 * 获得当前用户所有属性
	 * @return Map
	 */
	@SuppressWarnings ( "unchecked" )
	public static Map<String, Object> getAttrMap() {
		PrincipalCollection principalCollection = SecurityUtils.getSubject()
				.getPrincipals();
		if (principalCollection != null) {

			return (Map<String, Object>) principalCollection.getPrimaryPrincipal();
		} else {
			return MapUtils.EMPTY_MAP;
		}
	}

}
