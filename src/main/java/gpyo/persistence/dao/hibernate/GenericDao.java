package gpyo.persistence.dao.hibernate;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

import javax.validation.OverridesAttribute;

import org.hibernate.Criteria;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Example;
import gpyo.persistence.dao.IGenericDao;

import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Realiza las operaciones básicas del DAO.
 * 
 * @author Alberto Revilla Gil.
 *
 * @param <T> Tipo de Objeto con las que se van a realizar las operaciones del DAO. 
 * @param <ID> Identificador del objeto.
 */
public abstract class GenericDao <T, ID extends Serializable> extends HibernateTemplate implements IGenericDao<T, ID>, Serializable{

	/**
	 * Número para la de serialización de la clase para asegurar compatibilidades.
	 */
	private static final long serialVersionUID = 854138182598996563L;
	private Class<T> persistentClass;
	private static final int BATCH_SIZE = 1000;

	/**
	 * Constructor.
	 */
	@SuppressWarnings("unchecked")
	public GenericDao() {
		this.persistentClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass())
				.getActualTypeArguments()[0];
	}


	public Class<T> getPersistentClass() {
		return persistentClass;
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	@Transactional(readOnly = true)
	public T findById(ID id, boolean lock) {
		T entity;
		if (lock)
			entity = (T) getSession().load(getPersistentClass(), id, LockMode.UPGRADE);
		else
			entity = (T) getSession().load(getPersistentClass(), id);

		return entity;
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	@Transactional(readOnly = true)
	public T getById(ID id, boolean lock) {
		T entity;
		if (lock)
			entity = (T) getSession().get(getPersistentClass(), id, LockMode.UPGRADE);
		else
			entity = (T) getSession().get(getPersistentClass(), id);

		return entity;
	}

	@Transactional(readOnly = true)
	public List<T> findAll() {
		return findByCriteria();
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public List<T> findByExample(T exampleInstance, String... excludeProperty) {
		Criteria crit = getSession().createCriteria(getPersistentClass());
		Example example = Example.create(exampleInstance);
		for (String exclude : excludeProperty) {
			example.excludeProperty(exclude);
		}
		crit.add(example);
		return crit.list();
	}


	@Transactional
	public T makePersistent(T entity) {
		getSession().saveOrUpdate(entity);
		return entity;
	}

	@Transactional
	public T saveEntity(T entity) {
		getSession().save(entity);
		return entity;
	}
	
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void saveAll(List<T> data){
		Session session = null;
		try{
			session = getSession();
			for(int i=0;i<data.size();i++){
				Object object = data.get(i);
				session.save(object);
				if(i != 0 && i % BATCH_SIZE == 0)
					session.flush();
				data.set(i, null);
			}
			session.flush();
		}
		
		catch (Throwable e){
			throw new RuntimeException(e.getMessage(),e);
		} finally{
			if(session != null)
				session.clear();
		}
	}
	
	@Transactional
	public T saveInTable(T entity, String tableName) {
		getSession().save(tableName, entity);
		return entity;
	}

	@Transactional
	public void makeTransient(T entity) {
		getSession().delete(entity);
	}
	
	@SuppressWarnings("unchecked")
	protected List<T> findByCriteria(Criterion... criterion) {
		Criteria crit = getSession().createCriteria(getPersistentClass());
		for (Criterion c : criterion) {
			crit.add(c);
		}
		return crit.list();
	}
	
	@SuppressWarnings("unchecked") 
	@Transactional(readOnly = true)
	public boolean findTable(String tableName){
		List<String> tables = new ArrayList<String>();
		tables = (List<String>) getSession().createSQLQuery(
					"SELECT table_name FROM information_schema.TABLES WHERE TABLE_SCHEMA=DATABASE()").list();
		if (tables.contains(tableName))
			return true;
		return false;
	}

}
