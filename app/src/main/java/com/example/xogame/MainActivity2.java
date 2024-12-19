package com.example.xogame;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity2 extends AppCompatActivity {
    private TextView player1Score, player2Score, currentPlayerText;
    private int counter = 0;
    private int scorePlayer1 = 0;
    private int scorePlayer2 = 0;

    private String[] board = {
            "", "", "",
            "", "", "",
            "", "", ""
    };
    private int[] boardIDs = {
            R.id.bu0, R.id.bu1, R.id.bu2,
            R.id.bu3, R.id.bu4, R.id.bu5,
            R.id.bu6, R.id.bu7, R.id.bu8
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        // Initialize views
        player1Score = findViewById(R.id.Player1score);
        player2Score = findViewById(R.id.Player2score);
        currentPlayerText = findViewById(R.id.winner);
    }

    private boolean checkBoardStatus(int idImage) {
        int found = -1;
        for (int i = 0; i < boardIDs.length; i++) {
            if (boardIDs[i] == idImage) {
                found = i;
                break;
            }
        }
        if (found == -1 || !board[found].isEmpty()) {
            return false;
        }
        board[found] = (counter % 2 == 0) ? "X" : "O";
        return true;
    }

    public void onPlayerClick(View view) {
        if (view instanceof ImageView) {
            ImageView imageView = (ImageView) view;
            boolean isEmpty = checkBoardStatus(imageView.getId());
            if (!isEmpty) return;

            // Update image based on player turn
            imageView.setImageResource((counter % 2 == 0) ? R.drawable.img_1 : R.drawable.img_2);

            counter++;

            // Check if there's a winner
            String winner = checkWinner();
            if (winner != null) {
                Toast.makeText(this, winner + " wins!", Toast.LENGTH_SHORT).show();
                updateScore(winner);
                resetGame();
            } else if (counter == 9) {
                // If the board is full, it's a draw
                Toast.makeText(this, "It's a draw!", Toast.LENGTH_SHORT).show();
                resetGame();
            }

            // Update current player text
            currentPlayerText.setText((counter % 2 == 0) ? "Player 1's Turn" : "Player 2's Turn");
        }
    }

    private String checkWinner() {
        // Check rows
        for (int i = 0; i < 9; i += 3) {
            if (!board[i].isEmpty() && board[i].equals(board[i + 1]) && board[i].equals(board[i + 2])) {
                return board[i];
            }
        }

        // Check columns
        for (int i = 0; i < 3; i++) {
            if (!board[i].isEmpty() && board[i].equals(board[i + 3]) && board[i].equals(board[i + 6])) {
                return board[i];
            }
        }

        // Check diagonals
        if (!board[0].isEmpty() && board[0].equals(board[4]) && board[0].equals(board[8])) {
            return board[0];
        }
        if (!board[2].isEmpty() && board[2].equals(board[4]) && board[2].equals(board[6])) {
            return board[2];
        }

        return null; // No winner
    }

    private void updateScore(String winner) {
        if ("X".equals(winner)) {
            scorePlayer1 += 5;
            player1Score.setText(String.valueOf(scorePlayer1));
        } else if ("O".equals(winner)) {
            scorePlayer2 += 5;
            player2Score.setText(String.valueOf(scorePlayer2));
        }
    }

    private void resetGame() {
        counter = 0;
        for (int i = 0; i < board.length; i++) {
            board[i] = "";
            ImageView imageView = findViewById(boardIDs[i]);
            if (imageView != null) {
                imageView.setImageResource(0); // Clear images
            }
        }

        currentPlayerText.setText("Player 1's Turn");
    }
}
