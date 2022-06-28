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
import model.Pagamento;

public class PagamentoDao implements IObjDao<Pagamento>, IPagamentoDao {

	private SessionFactory sf;

    public PagamentoDao(SessionFactory sf) {
		this.sf = sf;
    }
	
	@Override
	public void insert(Pagamento pag) throws SQLException {
		EntityManager entityManager = sf.createEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		entityManager.persist(pag);
		transaction.commit();
	}

	@Override
	public void update(Pagamento pag) throws SQLException {
		EntityManager entityManager = sf.createEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		entityManager.merge(pag);
		transaction.commit();
	}

	@Override
	public void delete(Pagamento pag) throws SQLException {
		EntityManager entityManager = sf.createEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		entityManager.remove(pag);
		transaction.commit();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Pagamento> selectAll() throws SQLException {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT a.id, a.nome, a.cpf, a.nascimento, a.telefone, ");
		sql.append("e.id, e.logradouro, e.numero, e.bairro, e.cep, ");
		sql.append("p.id, p.data, p.valor, p.tipo, p.info_adicional ");
		sql.append("FROM aluno a, endereco e, pagamento p ");
		sql.append("WHERE a.id = p.id_aluno AND e.id = a.id_endereco ");

		EntityManager entityManager = sf.createEntityManager();
		Query query = entityManager.createNativeQuery(sql.toString());

		List<Object[]> pagamentosResultSet = query.getResultList();
		List<Pagamento> pagamentos = new ArrayList<Pagamento>();

		for (Object[] obj : pagamentosResultSet) {
			Aluno aluno = AlunoBuilder.builder()
					.addId(Integer.parseInt(obj[0].toString()))
					.addNome(obj[1].toString())
					.addCpf(obj[2].toString())
					.addNascimento(LocalDate.parse(obj[3].toString()))
					.addTelefone(obj[4].toString())
					.addEndereco(Integer.parseInt(obj[5].toString()), obj[6].toString(), 
							Integer.parseInt(obj[7].toString()), obj[8].toString(), obj[9].toString())
					.get();
			
			Pagamento pagamento = new Pagamento();
			pagamento.setId(Integer.parseInt(obj[10].toString()));
			pagamento.setData(LocalDate.parse(obj[11].toString()));
			pagamento.setValor(Double.parseDouble(obj[12].toString()));
			pagamento.setTipo(obj[13].toString());
			pagamento.setInfoAdicional(obj[14].toString());
			pagamento.setAluno(aluno);

			pagamentos.add(pagamento);
		}
		return pagamentos;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Pagamento> selectOneAluno(Pagamento pag) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT a.id, a.nome, a.cpf, a.nascimento, a.telefone, ");
		sql.append("e.id, e.logradouro, e.numero, e.bairro, e.cep, ");
		sql.append("p.id, p.data, p.valor, p.tipo, p.info_adicional ");
		sql.append("FROM aluno a, endereco e, pagamento p ");
		sql.append("WHERE a.id = p.id_aluno AND e.id = a.id_endereco ");
		sql.append("AND a.nome LIKE '%" + pag.getAluno().getNome() + "%' ");

		EntityManager entityManager = sf.createEntityManager();
		Query query = entityManager.createNativeQuery(sql.toString());

		List<Object[]> pagamentosResultSet = query.getResultList();
		List<Pagamento> pagamentos = new ArrayList<Pagamento>();

		for (Object[] obj : pagamentosResultSet) {
			Aluno aluno = AlunoBuilder.builder()
					.addId(Integer.parseInt(obj[0].toString()))
					.addNome(obj[1].toString())
					.addCpf(obj[2].toString())
					.addNascimento(LocalDate.parse(obj[3].toString()))
					.addTelefone(obj[4].toString())
					.addEndereco(Integer.parseInt(obj[5].toString()), obj[6].toString(), 
							Integer.parseInt(obj[7].toString()), obj[8].toString(), obj[9].toString())
					.get();
			
			pag = new Pagamento();
			pag.setId(Integer.parseInt(obj[10].toString()));
			pag.setData(LocalDate.parse(obj[11].toString()));
			pag.setValor(Double.parseDouble(obj[12].toString()));
			pag.setTipo(obj[13].toString());
			pag.setInfoAdicional(obj[14].toString());
			pag.setAluno(aluno);

			pagamentos.add(pag);
		}
		return pagamentos;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Pagamento selectOne(Pagamento pag) throws SQLException {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT a.id, a.nome, a.cpf, a.nascimento, a.telefone, ");
		sql.append("e.id, e.logradouro, e.numero, e.bairro, e.cep, ");
		sql.append("p.id, p.data, p.valor, p.tipo, p.info_adicional ");
		sql.append("FROM aluno a, endereco e, pagamento p ");
		sql.append("WHERE a.id = p.id_aluno AND e.id = a.id_endereco ");
		sql.append("AND p.id = ?1 ");

		EntityManager entityManager = sf.createEntityManager();
		Query query = entityManager.createNativeQuery(sql.toString());
		query.setParameter(1, pag.getId());

		List<Object[]> pagamentosResultSet = query.getResultList();

		for (Object[] obj : pagamentosResultSet) {
			Aluno aluno = AlunoBuilder.builder()
					.addId(Integer.parseInt(obj[0].toString()))
					.addNome(obj[1].toString())
					.addCpf(obj[2].toString())
					.addNascimento(LocalDate.parse(obj[3].toString()))
					.addTelefone(obj[4].toString())
					.addEndereco(Integer.parseInt(obj[5].toString()), obj[6].toString(), 
							Integer.parseInt(obj[7].toString()), obj[8].toString(), obj[9].toString())
					.get();
			
			pag = new Pagamento();
			pag.setId(Integer.parseInt(obj[10].toString()));
			pag.setData(LocalDate.parse(obj[11].toString()));
			pag.setValor(Double.parseDouble(obj[12].toString()));
			pag.setTipo(obj[13].toString());
			pag.setInfoAdicional(obj[14].toString());
			pag.setAluno(aluno);
			
		}
		return pag;
	}
	

}
