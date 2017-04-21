package ohtu;

public class TennisGame {
    
    private int m_score1 = 0;
    private int m_score2 = 0;
    private String player1Name;
    private String player2Name;

    public TennisGame(String player1Name, String player2Name) {
        this.player1Name = player1Name;
        this.player2Name = player2Name;
    }

    public void wonPoint(String playerName) {
        if (playerName == "player1")
            m_score1 += 1;
        else
            m_score2 += 1;
    }

    private String[] scoreLiterals = {"Love", "Fifteen", "Thirty", "Forty", "Deuce"};

    private String getScoreLiteral(int score) {
        if (score < 4) {
            return scoreLiterals[score] + "-All";
        }
        return scoreLiterals[4];
    }

    private String getScoreFromMinusResult(int minusResult) {
        if (minusResult == 1) {
            return "Advantage player1";
        } else if (minusResult == -1) {
            return "Advantage player2";
        } else if (minusResult >= 2) {
            return  "Win for player1";
        } else {
            return "Win for player2";
        }
    }

    public String getScore() {
        if (m_score1 == m_score2) {
            return getScoreLiteral(m_score1);
        } else if (m_score1 >= 4 || m_score2 >= 4) {
            return getScoreFromMinusResult(m_score1-m_score2);
        }
        return scoreLiterals[m_score1] + "-" + scoreLiterals[m_score2];
    }
}