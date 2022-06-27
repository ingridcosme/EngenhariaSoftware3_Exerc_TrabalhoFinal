package persistence;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.SessionFactory;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import model.Professor;
import model.ProfessorBuilder;

public class ProfessorDao implements IObjDao<Professor> {

	private SessionFactory sf;
	
	public ProfessorDao(SessionFactory sf) {
		this.sf = sf;
	}
	
	@Override
	public void insert(Professor professor) throws SQLException {
		EnderecoDao endDao = new EnderecoDao(sf);
		endDao.insert(professor.getEndereco());
		
		EntityManager entityManager = sf.createEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		entityManager.persist(professor);
		transaction.commit();
	}

	@Override
	public void update(Professor professor) throws SQLException {
		EnderecoDao endDao = new EnderecoDao(sf);
		endDao.update(professor.getEndereco());
		
		EntityManager entityManager = sf.createEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		entityManager.merge(professor);
		transaction.commit();
	}

	@Override
	public void delete(Professor professor) throws SQLException {
		EnderecoDao endDao = new EnderecoDao(sf);
		endDao.delete(professor.getEndereco());
		
		EntityManager entityManager = sf.createEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		entityManager.remove(professor);
		transaction.commit();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Professor selectOne(Professor professor) throws SQLException {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT p.id, p.nome, p.cpf, p.nascimento, p.telefone, p.salario, ");
		sql.append("e.id, e.logradouro, e.numero, e.bairro, e.cep ");
		sql.append("FROM professor p, endereco e ");
		sql.append("WHERE p.id_endereco = e.id ");
		sql.append("AND p.nome LIKE '%" + professor.getNome() + "%' ");

		EntityManager entityManager = sf.createEntityManager();
		Query query = entityManager.createNativeQuery(sql.toString());

		List<Object[]> professoresResultSet = query.getResultList();
		
		for (Object[] obj : professoresResultSet) {
			professor = ProfessorBuilder.builder()
					.addId(Integer.parseInt(obj[0].toString()))
					.addNome(obj[1].toString())
					.addCpf(obj[2].toString())
					.addNascimento(LocalDate.parse(obj[3].toString()))
					.addTelefone(obj[4].toString())
					.addSalario(Double.parseDouble(obj[5].toString()))
					.addEndereco(Integer.parseInt(obj[6].toString()), obj[7].toString(), Integer.parseInt(obj[8].toString()), obj[9].toString(), obj[10].toString())
					.get();
		}
		return professor;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Professor> selectAll() throws SQLException {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT p.id, p.nome, p.cpf, p.nascimento, p.telefone, p.salario, ");
		sql.append("e.id, e.logradouro, e.numero, e.bairro, e.cep ");
		sql.append("FROM professor p, endereco e ");
		sql.append("WHERE p.id_endereco = e.id ");

		EntityManager entityManager = sf.createEntityManager();
		Query query = entityManager.createNativeQuery(sql.toString());

		List<Object[]> professoresResultSet = query.getResultList();
		List<Professor> professores = new ArrayList<>();
		
		for (Object[] obj : professoresResultSet) {
			Professor professor = ProfessorBuilder.builder()
					.addId(Integer.parseInt(obj[0].toString()))
					.addNome(obj[1].toString())
					.addCpf(obj[2].toString())
					.addNascimento(LocalDate.parse(obj[3].toString()))
					.addTelefone(obj[4].toString())
					.addSalario(Double.parseDouble(obj[5].toString()))
					.addEndereco(Integer.parseInt(obj[6].toString()), obj[7].toString(), Integer.parseInt(obj[8].toString()), obj[9].toString(), obj[10].toString())
					.get();

			professores.add(professor);
		}
		return professores;
	}
	
}
