import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Triqui {
    int boardWidth = 600;
    int boardHeight = 700; //50px for the text panel + 50px for the reset button

    JFrame frame = new JFrame("Triqui");
    JLabel textLabel = new JLabel();
    JPanel textPanel = new JPanel();
    JPanel boardPanel = new JPanel();
    JPanel controlPanel = new JPanel(); // Panel para el botón de reinicio

    JButton[][] board = new JButton[3][3];
    JButton resetButton = new JButton("Reiniciar"); // Botón de reinicio

    String playerX = "X";
    String playerO = "O";
    String currentPlayer = playerX;

    boolean gameOver = false;
    int turns = 0;

    Triqui() {
        frame.setVisible(true);
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        textLabel.setBackground(Color.darkGray);
        textLabel.setForeground(Color.white);
        textLabel.setFont(new Font("Arial", Font.BOLD, 50));
        textLabel.setHorizontalAlignment(JLabel.CENTER);
        textLabel.setText("Triqui");
        textLabel.setOpaque(true);

        textPanel.setLayout(new BorderLayout());
        textPanel.add(textLabel);
        frame.add(textPanel, BorderLayout.NORTH);

        boardPanel.setLayout(new GridLayout(3, 3));
        boardPanel.setBackground(Color.darkGray);
        frame.add(boardPanel);

        // Crear los botones del tablero
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                JButton tile = new JButton();
                board[r][c] = tile;
                boardPanel.add(tile);

                tile.setBackground(Color.darkGray);
                tile.setForeground(Color.white);
                tile.setFont(new Font("Arial", Font.BOLD, 120));
                tile.setFocusable(false);

                tile.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        if (gameOver) return; // No permitir jugadas si el juego terminó
                        JButton tile = (JButton) e.getSource();
                        if (tile.getText().equals("")) {
                            tile.setText(currentPlayer);
                            turns++;
                            checkWinner();
                            if (!gameOver) {
                                currentPlayer = currentPlayer.equals(playerX) ? playerO : playerX;
                                textLabel.setText(currentPlayer + "'s turno.");
                            }
                        }
                    }
                });
            }
        }

        // Configurar el panel de control (botón de reinicio)
        controlPanel.setLayout(new BorderLayout());
        resetButton.setFont(new Font("Arial", Font.PLAIN, 20));
        resetButton.setEnabled(false); // Desactivado hasta que termine el juego
        resetButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                resetGame(); // Reiniciar el juego al hacer clic
            }
        });
        controlPanel.add(resetButton, BorderLayout.SOUTH);
        frame.add(controlPanel, BorderLayout.SOUTH);
    }

    void checkWinner() {
        // Comprobaciones para filas, columnas y diagonales como antes
        // Horizontal
        for (int r = 0; r < 3; r++) {
            if (board[r][0].getText().equals("")) continue;

            if (board[r][0].getText().equals(board[r][1].getText()) &&
                board[r][1].getText().equals(board[r][2].getText())) {
                for (int i = 0; i < 3; i++) {
                    setWinner(board[r][i]);
                }
                gameOver = true;
                resetButton.setEnabled(true); // Activar botón de reinicio
                return;
            }
        }

        // Vertical
        for (int c = 0; c < 3; c++) {
            if (board[0][c].getText().equals("")) continue;

            if (board[0][c].getText().equals(board[1][c].getText()) &&
                board[1][c].getText().equals(board[2][c].getText())) {
                for (int i = 0; i < 3; i++) {
                    setWinner(board[i][c]);
                }
                gameOver = true;
                resetButton.setEnabled(true); // Activar botón de reinicio
                return;
            }
        }

        // Diagonales
        if (board[0][0].getText().equals(board[1][1].getText()) &&
            board[1][1].getText().equals(board[2][2].getText()) &&
            !board[0][0].getText().equals("")) {
            for (int i = 0; i < 3; i++) {
                setWinner(board[i][i]);
            }
            gameOver = true;
            resetButton.setEnabled(true); // Activar botón de reinicio
            return;
        }

        if (board[0][2].getText().equals(board[1][1].getText()) &&
            board[1][1].getText().equals(board[2][0].getText()) &&
            !board[0][2].getText().equals("")) {
            setWinner(board[0][2]);
            setWinner(board[1][1]);
            setWinner(board[2][0]);
            gameOver = true;
            resetButton.setEnabled(true); // Activar botón de reinicio
            return;
        }

        // Comprobar empate
        if (turns == 9) {
            for (int r = 0; r < 3; r++) {
                for (int c = 0; c < 3; c++) {
                    setTie(board[r][c]);
                }
            }
            gameOver = true;
            resetButton.setEnabled(true); // Activar botón de reinicio
        }
    }

    void setWinner(JButton tile) {
        tile.setForeground(Color.green);
        tile.setBackground(Color.gray);
        textLabel.setText(currentPlayer + " es el ganador!");
    }

    void setTie(JButton tile) {
        tile.setForeground(Color.orange);
        tile.setBackground(Color.gray);
        textLabel.setText("Empate");
    }

    // Método para reiniciar el juego
    void resetGame() {
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                board[r][c].setText(""); // Limpiar botones
                board[r][c].setForeground(Color.white); // Restablecer colores
                board[r][c].setBackground(Color.darkGray);
            }
        }
        turns = 0;
        currentPlayer = playerX; // Reiniciar jugador
        gameOver = false;
        textLabel.setText("Triqui");
        resetButton.setEnabled(false); // Desactivar botón de reinicio
    }
}
