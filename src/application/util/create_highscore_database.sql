CREATE SCHEMA `snake_game_highscores` DEFAULT CHARACTER SET utf8 COLLATE utf8_hungarian_ci ;

CREATE TABLE `snake_game_highscores`.`high_scores` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `player_name` VARCHAR(50) NOT NULL,
  `player_score` INT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC)  )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_hungarian_ci;

