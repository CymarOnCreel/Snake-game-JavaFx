package application.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="high_scores")
public class Player {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(name="player_name")
	private String playerName;
	
	@Column(name="player_score")
	private int playerScore;

	public Player(int id, String playerName, int playerScore) {
		super();
		this.id = id;
		this.playerName = playerName;
		this.playerScore = playerScore;
	}

	public Player(String playerName, int playerScore) {
		super();
		this.playerName = playerName;
		this.playerScore = playerScore;
	}

	public int getId() {
		return id;
	}

	public String getPlayerName() {
		return playerName;
	}

	public int getPlayerScore() {
		return playerScore;
	}
	
	

	public void setPlayerScore(int playerScore) {
		this.playerScore = playerScore;
	}

	@Override
	public String toString() {
		return "Name:"+getPlayerName()+"Score"+getPlayerScore();
	}
	
	

}
