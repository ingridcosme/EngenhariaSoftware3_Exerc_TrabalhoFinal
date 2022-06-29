package persistence;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.SessionFactory;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import model.Aluno;
import model.AlunoBuilder;

public class AlunoDao implements IObjDao<Aluno> {

	private SessionFactory sf;
	
	public AlunoDao(SessionFactory sf) {
		this.sf = sf;
	}
	
	@Override
	public void insert(Aluno aluno) throws SQLException {
		EnderecoDao endDao = new EnderecoDao(sf);
		endDao.insert(aluno.getEndereco());

		EntityManager entityManager = sf.createEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		entityManager.persist(aluno);
		transaction.commit();
	}

	@Override
	public void update(Aluno aluno) throws SQLException {
		EnderecoDao endDao = new EnderecoDao(sf);
		endDao.update(aluno.getEndereco());
		
		EntityManager entityManager = sf.createEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		entityManager.merge(aluno);
		transaction.commit();
	}

	@Override
	public void delete(Aluno aluno) throws SQLException {
		EnderecoDao endDao = new EnderecoDao(sf);
		endDao.delete(aluno.getEndereco());
		
		EntityManager entityManager = sf.createEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		entityManager.remove(aluno);
		transaction.commit();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Aluno selectOne(Aluno aluno) throws SQLException {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT a.id, a.nome, a.cpf, a.nascimento, a.telefone, ");
		sql.append("e.id, e.logradouro, e.numero, e.bairro, e.cep, a.data_max_acesso ");
		sql.append("FROM aluno a, endereco e ");
		sql.append("WHERE a.id_endereco = e.id ");
		sql.append("AND a.nome LIKE '%" + aluno.getNome() + "%' ");

		EntityManager entityManager = sf.createEntityManager();
		Query query = entityManager.createNativeQuery(sql.toString());

		List<Object[]> alunosResultSet = query.getResultList();
		
		for (Object[] obj : alunosResultSet) {
			aluno = AlunoBuilder.builder()
					.addId(Integer.parseInt(obj[0].toString()))
					.addNome(obj[1].toString())
					.addCpf(obj[2].toString())
					.addNascimento(LocalDate.parse(obj[3].toString()))
					.addTelefone(obj[4].toString())
					.addEndereco(Integer.parseInt(obj[5].toString()), obj[6].toString(), Integer.parseInt(obj[7].toString()), obj[8].toString(), obj[9].toString())
					.addDataMaxAcesso(LocalDate.parse(obj[10].toString()))
					.get();
		}
		return aluno;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Aluno> selectAll() throws SQLException {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT a.id, a.nome, a.cpf, a.nascimento, a.telefone, ");
		sql.append("e.id, e.logradouro, e.numero, e.bairro, e.cep, a.data_max_acesso ");
		sql.append("FROM aluno a, endereco e ");
		sql.append("WHERE a.id_endereco = e.id ");

		EntityManager entityManager = sf.createEntityManager();
		Query query = entityManager.createNativeQuery(sql.toString());

		List<Object[]> alunosResultSet = query.getResultList();
		List<Aluno> alunos = new ArrayList<>();
		
		for (Object[] obj : alunosResultSet) {
			Aluno aluno = AlunoBuilder.builder()
					.addId(Integer.parseInt(obj[0].toString()))
					.addNome(obj[1].toString())
					.addCpf(obj[2].toString())
					.addNascimento(LocalDate.parse(obj[3].toString()))
					.addTelefone(obj[4].toString())
					.addEndereco(Integer.parseInt(obj[5].toString()), obj[6].toString(), Integer.parseInt(obj[7].toString()), obj[8].toString(), obj[9].toString())
					.addDataMaxAcesso(LocalDate.parse(obj[10].toString()))
					.get();

			alunos.add(aluno);
		}
		return alunos;
	}

}
