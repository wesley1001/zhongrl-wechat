package cn.xn.freamwork.support.mybatis;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.RowBounds;

import java.sql.Connection;


@Intercepts( {@Signature( type = StatementHandler.class, method = "prepare", args = {Connection.class} )} )
public class OraclePaginationInterceptor extends AbstractPaginationInterceptor {
	@Override
	public String toPaginationSQL(String originalSql, RowBounds rowBounds) {
		if (StringUtils.isEmpty(originalSql)) {
			return originalSql;
		}
		originalSql = toLineSql(originalSql);

		originalSql = originalSql.trim();
		boolean isForUpdate = false;
		if (originalSql.toLowerCase().endsWith(" for update")) {
			originalSql = originalSql.substring(0, originalSql.length() - 11);
			isForUpdate = true;
		}

		StringBuilder pagingSelect = new StringBuilder(originalSql.length() + 100);

		pagingSelect.append("select * from ( select row_.*, rownum rownum_ from ( ");

		pagingSelect.append(originalSql);

		pagingSelect.append(" ) row_ ) where rownum_ > " + rowBounds.getOffset() + " and rownum_ <= " + (rowBounds.getOffset() + rowBounds.getLimit()));

		if (isForUpdate) {
			pagingSelect.append(" for update");
		}

		return pagingSelect.toString();
	}
}
