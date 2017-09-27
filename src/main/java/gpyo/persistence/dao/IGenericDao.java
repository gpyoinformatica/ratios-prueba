package gpyo.persistence.dao;

import java.io.Serializable;
import java.util.List;

import org.springframework.dao.DataAccessException;

/**
 * Realiza las operaciones básicas del DAO.
 * 
 * @author Alberto Revilla Gil
 *
 * @param <T> Tipo de Objeto con las que se van a realizar las operaciones del DAO. 
 * @param <ID> Identificador del objeto.
 */
public interface IGenericDao<T, ID extends Serializable>  {

	T findById(ID id, boolean lock);

	T getById(ID id, boolean lock);

	List<T> findAll();

	List<T> findByExample(T exampleInstance, String... excludeProperty);

	T makePersistent(T entity);

	void makeTransient(T entity);

	void update(Object o) throws DataAccessException;

	public T saveEntity(T entity);
	
	public boolean findTable(String tableName);
	
	public void saveAll(List<T> data);

}
