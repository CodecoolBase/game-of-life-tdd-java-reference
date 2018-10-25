package com.codecool.gameoflife;

import com.codecool.gameoflife.game.BoardReader;
import com.codecool.gameoflife.game.Game;
import com.codecool.gameoflife.game.Rules;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GameoflifeApplication {

    public static void main(String[] args) {
        SpringApplication.run(GameoflifeApplication.class, args);
    }

    @Bean
    public Rules rules() {
        return new Rules();
    }


    @Bean
    public BoardReader boardReader() {
        return new BoardReader();
    }


    @Bean
    public Game game(Rules rules, BoardReader boardReader) {
        return new Game(rules, boardReader);
    }

}
