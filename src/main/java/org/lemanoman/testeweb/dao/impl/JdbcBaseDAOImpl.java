package org.lemanoman.testeweb.dao.impl;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.stereotype.Repository;

@Repository
public abstract class JdbcBaseDAOImpl<T> extends NamedParameterJdbcDaoSupport {

}
