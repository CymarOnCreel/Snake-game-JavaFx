package application.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import application.dto.Player;

public class PlayerDao implements ICrud {
	EntityManagerFactory factory = Persistence.createEntityManagerFactory("highScores");
	EntityManager entityManager = factory.createEntityManager();

	@Override
	public void save(Player obj) {
		entityManager.getTransaction().begin();
		entityManager.persist(obj);
		entityManager.getTransaction().commit();
		entityManager.close();
		factory.close();

	}

	@Override
	public void updateById(Long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteById(Long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public Player findById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Player> getAll() {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Player> cq=cb.createQuery(Player.class);
		Root<Player> rootEntry= cq.from(Player.class);
		cq.select(rootEntry);
		List<Player> players=entityManager.createQuery(cq).getResultList();
		return players;
	}

}
