package org.lemanoman.testeweb.dao;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.stereotype.Repository;

@Repository
public abstract class JdbcBaseDAO<T> extends NamedParameterJdbcDaoSupport {

}
