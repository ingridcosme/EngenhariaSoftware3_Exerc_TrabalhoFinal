package persistence;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.SessionFactory;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import model.Endereco;

public class EnderecoDao implements IObjDao<Endereco> {

	private SessionFactory sf;
	
	public EnderecoDao(SessionFactory sf) {
		this.sf = sf;
	}

	@Override
	public void insert(Endereco end) throws SQLException {
		EntityManager entityManager = sf.createEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		entityManager.persist(end);
		transaction.commit();
	}

	@Override
	public void update(Endereco end) throws SQLException {
		EntityManager entityManager = sf.createEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		entityManager.merge(end);
		transaction.commit();
	}

	@Override
	public void delete(Endereco end) throws SQLException {
		EntityManager entityManager = sf.createEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		entityManager.remove(end);
		transaction.commit();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Endereco selectOne(Endereco end) throws SQLException {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT id, logradouro, numero, bairro, cep ");
		sql.append("FROM endereco WHERE cep = ?1 AND numero = ?2 ");

		EntityManager entityManager = sf.createEntityManager();
		Query query = entityManager.createNativeQuery(sql.toString());
		query.setParameter(1, end.getCep());
		query.setParameter(2, end.getNumero());

		List<Object[]> enderecosResultSet = query.getResultList();
		
		if(enderecosResultSet != null) {
			for (Object[] obj : enderecosResultSet) {
				end = new Endereco();
				end.setId(Integer.parseInt(obj[0].toString()));
				end.setLogradouro(obj[1].toString());
				end.setNumero(Integer.parseInt(obj[2].toString()));
				end.setBairro(obj[3].toString());
				end.setCep(obj[4].toString());
			}
			return end;
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Endereco> selectAll() throws SQLException {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT id, logradouro, numero, bairro, cep FROM endereco ");

		EntityManager entityManager = sf.createEntityManager();
		Query query = entityManager.createNativeQuery(sql.toString());

		List<Object[]> enderecosResultSet = query.getResultList();
		List<Endereco> enderecos = new ArrayList<Endereco>();

		for (Object[] obj : enderecosResultSet) {
			Endereco end = new Endereco();
			end.setId(Integer.parseInt(obj[0].toString()));
			end.setLogradouro(obj[1].toString());
			end.setNumero(Integer.parseInt(obj[2].toString()));
			end.setBairro(obj[3].toString());
			end.setCep(obj[4].toString());

			enderecos.add(end);
		}
		return enderecos;
	}
	
}
