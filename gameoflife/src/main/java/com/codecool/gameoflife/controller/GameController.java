package com.codecool.gameoflife.controller;

import com.codecool.gameoflife.game.Game;
import com.codecool.gameoflife.game.GameOfLifeException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GameController {

    @Autowired
    private Game game;

    @PostMapping("/init")
    public void init(@RequestBody DTO dto) throws GameOfLifeException {
        game.initializeBoard(dto.getData());
    }

    @GetMapping("/next")
    public DTO next() throws GameOfLifeException {
        game.updateState();
        return new DTO(game.getBoardStatAsString());
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DTO {
        private String data;
    }

}
