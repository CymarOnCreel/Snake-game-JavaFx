package application.dao;

import java.util.List;

import application.dto.Player;



public interface ICrud {
		
		public void save(Player obj);
		public void updateById(Long id);
		public void deleteById(Long id);
		public Player findById(Long id);
		public List<Player> getAll(); 

}
