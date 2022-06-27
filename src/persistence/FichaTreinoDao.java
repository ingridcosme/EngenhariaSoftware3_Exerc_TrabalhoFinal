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
import model.FichaTreino;
import model.Professor;
import model.ProfessorBuilder;

public class FichaTreinoDao implements IObjDao<FichaTreino>, IFichaTreinoDao {

	private SessionFactory sf;

    public FichaTreinoDao(SessionFactory sf) {
		this.sf = sf;
    }
	
	@Override
	public void insert(FichaTreino treino) throws SQLException {
		EntityManager entityManager = sf.createEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		entityManager.persist(treino);
		transaction.commit();
	}

	@Override
	public void update(FichaTreino treino) throws SQLException {
		EntityManager entityManager = sf.createEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		entityManager.merge(treino);
		transaction.commit();
	}

	@Override
	public void delete(FichaTreino treino) throws SQLException {
		EntityManager entityManager = sf.createEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		entityManager.remove(treino);
		transaction.commit();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<FichaTreino> selectAll() throws SQLException {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT a.id, a.nome, a.cpf, a.nascimento, a.telefone, ");
		sql.append("ea.id, ea.logradouro, ea.numero, ea.bairro, ea.cep, ");
		sql.append("p.id, p.nome, p.cpf, p.nascimento, p.telefone, p.salario, ");
		sql.append("ep.id, ep.logradouro, ep.numero, ep.bairro, ep.cep, ");
		sql.append("t.id, t.data, t.tipo, t.info_adicional ");
		sql.append("FROM aluno a, endereco ea, professor p, endereco ep, ficha_treino t ");
		sql.append("WHERE a.id = t.id_aluno AND ea.id = a.id_endereco ");
		sql.append("AND p.id = t.id_professor AND ep.id = p.id_endereco ");

		EntityManager entityManager = sf.createEntityManager();
		Query query = entityManager.createNativeQuery(sql.toString());

		List<Object[]> treinosResultSet = query.getResultList();
		List<FichaTreino> treinos = new ArrayList<FichaTreino>();

		for (Object[] obj : treinosResultSet) {
			Aluno aluno = AlunoBuilder.builder()
					.addId(Integer.parseInt(obj[0].toString()))
					.addNome(obj[1].toString())
					.addCpf(obj[2].toString())
					.addNascimento(LocalDate.parse(obj[3].toString()))
					.addTelefone(obj[4].toString())
					.addEndereco(Integer.parseInt(obj[5].toString()), obj[6].toString(), 
							Integer.parseInt(obj[7].toString()), obj[8].toString(), obj[9].toString())
					.get();
			
			Professor professor = ProfessorBuilder.builder()
					.addId(Integer.parseInt(obj[10].toString()))
					.addNome(obj[11].toString())
					.addCpf(obj[12].toString())
					.addNascimento(LocalDate.parse(obj[13].toString()))
					.addTelefone(obj[14].toString())
					.addSalario(Double.parseDouble(obj[15].toString()))
					.addEndereco(Integer.parseInt(obj[16].toString()), obj[17].toString(), 
							Integer.parseInt(obj[18].toString()), obj[19].toString(), obj[20].toString())
					.get();
			
			FichaTreino treino = new FichaTreino();
			treino.setId(Integer.parseInt(obj[21].toString()));
			treino.setDataTreino(LocalDate.parse(obj[22].toString()));
			treino.setTipoTreino(obj[23].toString());
			treino.setInfoAdicional(obj[24].toString());
			treino.setAluno(aluno);
			treino.setProfessor(professor);

			treinos.add(treino);
		}
		return treinos;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<FichaTreino> selectOneAluno(FichaTreino treino) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT a.id, a.nome, a.cpf, a.nascimento, a.telefone, ");
		sql.append("ea.id, ea.logradouro, ea.numero, ea.bairro, ea.cep, ");
		sql.append("p.id, p.nome, p.cpf, p.nascimento, p.telefone, p.salario, ");
		sql.append("ep.id, ep.logradouro, ep.numero, ep.bairro, ep.cep, ");
		sql.append("t.id, t.data, t.tipo, t.info_adicional ");
		sql.append("FROM aluno a, endereco ea, professor p, endereco ep, ficha_treino t ");
		sql.append("WHERE a.id = t.id_aluno AND ea.id = a.id_endereco ");
		sql.append("AND p.id = t.id_professor AND ep.id = p.id_endereco ");
		sql.append("AND a.id = ?1 OR a.nome LIKE '%?2%' ");

		EntityManager entityManager = sf.createEntityManager();
		Query query = entityManager.createNativeQuery(sql.toString());
		query.setParameter(1, treino.getAluno().getId());
		query.setParameter(2, treino.getAluno().getNome());

		List<Object[]> treinosResultSet = query.getResultList();
		List<FichaTreino> treinos = new ArrayList<FichaTreino>();

		for (Object[] obj : treinosResultSet) {
			Aluno aluno = AlunoBuilder.builder()
					.addId(Integer.parseInt(obj[0].toString()))
					.addNome(obj[1].toString())
					.addCpf(obj[2].toString())
					.addNascimento(LocalDate.parse(obj[3].toString()))
					.addTelefone(obj[4].toString())
					.addEndereco(Integer.parseInt(obj[5].toString()), obj[6].toString(), 
							Integer.parseInt(obj[7].toString()), obj[8].toString(), obj[9].toString())
					.get();
			
			Professor professor = ProfessorBuilder.builder()
					.addId(Integer.parseInt(obj[10].toString()))
					.addNome(obj[11].toString())
					.addCpf(obj[12].toString())
					.addNascimento(LocalDate.parse(obj[13].toString()))
					.addTelefone(obj[14].toString())
					.addSalario(Double.parseDouble(obj[15].toString()))
					.addEndereco(Integer.parseInt(obj[16].toString()), obj[17].toString(), 
							Integer.parseInt(obj[18].toString()), obj[19].toString(), obj[20].toString())
					.get();
			
			treino = new FichaTreino();
			treino.setId(Integer.parseInt(obj[21].toString()));
			treino.setDataTreino(LocalDate.parse(obj[22].toString()));
			treino.setTipoTreino(obj[23].toString());
			treino.setInfoAdicional(obj[24].toString());
			treino.setAluno(aluno);
			treino.setProfessor(professor);

			treinos.add(treino);
		}
		return treinos;
	}

	@SuppressWarnings("unchecked")
	@Override
	public FichaTreino selectOne(FichaTreino treino) throws SQLException {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT a.id, a.nome, a.cpf, a.nascimento, a.telefone, ");
		sql.append("ea.id, ea.logradouro, ea.numero, ea.bairro, ea.cep, ");
		sql.append("p.id, p.nome, p.cpf, p.nascimento, p.telefone, p.salario, ");
		sql.append("ep.id, ep.logradouro, ep.numero, ep.bairro, ep.cep, ");
		sql.append("t.id, t.data, t.tipo, t.info_adicional ");
		sql.append("FROM aluno a, endereco ea, professor p, endereco ep, ficha_treino t ");
		sql.append("WHERE a.id = t.id_aluno AND ea.id = a.id_endereco ");
		sql.append("AND p.id = t.id_professor AND ep.id = p.id_endereco ");
		sql.append("AND p.id = ?1 ");

		EntityManager entityManager = sf.createEntityManager();
		Query query = entityManager.createNativeQuery(sql.toString());
		query.setParameter(1, treino.getId());

		List<Object[]> treinosResultSet = query.getResultList();

		for (Object[] obj : treinosResultSet) {
			Aluno aluno = AlunoBuilder.builder()
					.addId(Integer.parseInt(obj[0].toString()))
					.addNome(obj[1].toString())
					.addCpf(obj[2].toString())
					.addNascimento(LocalDate.parse(obj[3].toString()))
					.addTelefone(obj[4].toString())
					.addEndereco(Integer.parseInt(obj[5].toString()), obj[6].toString(), 
							Integer.parseInt(obj[7].toString()), obj[8].toString(), obj[9].toString())
					.get();
			
			Professor professor = ProfessorBuilder.builder()
					.addId(Integer.parseInt(obj[10].toString()))
					.addNome(obj[11].toString())
					.addCpf(obj[12].toString())
					.addNascimento(LocalDate.parse(obj[13].toString()))
					.addTelefone(obj[14].toString())
					.addSalario(Double.parseDouble(obj[15].toString()))
					.addEndereco(Integer.parseInt(obj[16].toString()), obj[17].toString(), 
							Integer.parseInt(obj[18].toString()), obj[19].toString(), obj[20].toString())
					.get();
			
			treino = new FichaTreino();
			treino.setId(Integer.parseInt(obj[21].toString()));
			treino.setDataTreino(LocalDate.parse(obj[22].toString()));
			treino.setTipoTreino(obj[23].toString());
			treino.setInfoAdicional(obj[24].toString());
			treino.setAluno(aluno);
			treino.setProfessor(professor);
		}
		return treino;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<FichaTreino> selectOneProfessor(FichaTreino treino) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT a.id, a.nome, a.cpf, a.nascimento, a.telefone, ");
		sql.append("ea.id, ea.logradouro, ea.numero, ea.bairro, ea.cep, ");
		sql.append("p.id, p.nome, p.cpf, p.nascimento, p.telefone, p.salario, ");
		sql.append("ep.id, ep.logradouro, ep.numero, ep.bairro, ep.cep, ");
		sql.append("t.id, t.data, t.tipo, t.info_adicional ");
		sql.append("FROM aluno a, endereco ea, professor p, endereco ep, ficha_treino t ");
		sql.append("WHERE a.id = t.id_aluno AND ea.id = a.id_endereco ");
		sql.append("AND p.id = t.id_professor AND ep.id = p.id_endereco ");
		sql.append("AND p.id = ?1 OR p.nome LIKE '%?2%' ");

		EntityManager entityManager = sf.createEntityManager();
		Query query = entityManager.createNativeQuery(sql.toString());
		query.setParameter(1, treino.getProfessor().getId());
		query.setParameter(2, treino.getProfessor().getNome());

		List<Object[]> treinosResultSet = query.getResultList();
		List<FichaTreino> treinos = new ArrayList<FichaTreino>();

		for (Object[] obj : treinosResultSet) {
			Aluno aluno = AlunoBuilder.builder()
					.addId(Integer.parseInt(obj[0].toString()))
					.addNome(obj[1].toString())
					.addCpf(obj[2].toString())
					.addNascimento(LocalDate.parse(obj[3].toString()))
					.addTelefone(obj[4].toString())
					.addEndereco(Integer.parseInt(obj[5].toString()), obj[6].toString(), 
							Integer.parseInt(obj[7].toString()), obj[8].toString(), obj[9].toString())
					.get();
			
			Professor professor = ProfessorBuilder.builder()
					.addId(Integer.parseInt(obj[10].toString()))
					.addNome(obj[11].toString())
					.addCpf(obj[12].toString())
					.addNascimento(LocalDate.parse(obj[13].toString()))
					.addTelefone(obj[14].toString())
					.addSalario(Double.parseDouble(obj[15].toString()))
					.addEndereco(Integer.parseInt(obj[16].toString()), obj[17].toString(), 
							Integer.parseInt(obj[18].toString()), obj[19].toString(), obj[20].toString())
					.get();
			
			treino = new FichaTreino();
			treino.setId(Integer.parseInt(obj[21].toString()));
			treino.setDataTreino(LocalDate.parse(obj[22].toString()));
			treino.setTipoTreino(obj[23].toString());
			treino.setInfoAdicional(obj[24].toString());
			treino.setAluno(aluno);
			treino.setProfessor(professor);

			treinos.add(treino);
		}
		return treinos;
	}

}
