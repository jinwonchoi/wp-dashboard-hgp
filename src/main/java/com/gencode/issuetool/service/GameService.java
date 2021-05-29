package com.gencode.issuetool.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.gencode.issuetool.obj.GameState;
import com.gencode.issuetool.obj.Move;

@Service
public class GameService {

    public GameState createGame(UUID uuid) {
        return new GameState(uuid, new int[][] {{1, 1}, {1, 1}}, 0, false);
    }

    public GameState move(UUID uuid, Move move) {
        return new GameState(uuid, new int[][] {{2, 2}, {2, 2}}, 1, false);
    }}
