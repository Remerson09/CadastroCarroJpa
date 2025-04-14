package aula2603.repository;

import aula2603.model.entity.Veiculo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class VeiculoRepository {

    @PersistenceContext
    private EntityManager em;

    public List<Veiculo> listarTodos() {
        return em.createQuery("FROM Veiculo", Veiculo.class).getResultList();
    }

    public Veiculo buscarPorId(Long id) {
        return em.find(Veiculo.class, id);
    }

    @Transactional
    public void salvar(Veiculo veiculo) {
        if (veiculo.getId() == null) {
            em.persist(veiculo);
        } else {
            em.merge(veiculo);
        }
    }

    @Transactional
    public void deletar(Long id) {
        Veiculo veiculo = buscarPorId(id);
        if (veiculo != null) {
            em.remove(veiculo);
        }
    }

    public List<Veiculo> buscarPorModelo(String modelo) {
        return em.createQuery(
                        "SELECT v FROM Veiculo v WHERE v.modelo LIKE :modelo", Veiculo.class)
                .setParameter("modelo", "%" + modelo + "%")
                .getResultList();
    }
}