import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Ladder {
    private static final double LADDER_PROBABILITY = 0.5; // 가로선 생성 확률
    private final boolean[][] ladder;
    private int width;

    public Ladder(int width, int height) {
        this.ladder = generateLadderGreedy(width, height);
    }

    // 사다리 생성(greedy algorithm)
    public boolean[][] generateLadderGreedy(int width, int height) {
        Random random = new Random();

        boolean[][] ladder = new boolean[height][width - 1];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width-1; j++) {
                // 연속된 가로선 방지
                if(j>0 && ladder[i][j-1])
                    ladder[i][j] = false;
                else{
                    ladder[i][j] = random.nextDouble() < LADDER_PROBABILITY; // 최소 가로선 수 보장을 위한 확률 조정
                }
            }
        }

        return ladder;
    }

    // 참가자 출력
    public void printLadder(String[] participantsName, String[] results) {
        width = participantsName.length;

        // 참가자 출력
        System.out.println("\n[참가자]");

        for (String participant : participantsName) {
            System.out.print(participant + "\t");
        }
        System.out.println();

        // 사다리 출력
        System.out.println("\n[사다리]");

        for (boolean[] row : ladder) {
            StringBuilder rowStr = new StringBuilder("|");
            for (int j = 0; j < width-1; j++) {
                rowStr.append(row[j] ? "---|" : "   |");
            }
            System.out.println(rowStr);
        }

        // 당첨 항목 출력
        System.out.println("\n[당첨 항목]");

        for (String result : results) {
            System.out.print(result + "\t");
        }
        System.out.println();
    }

    public Map<String, String> calculateResults(String[] participantsName, String[] results){
        width = participantsName.length;
        Map<String, String> finalResults = new HashMap<>();

        // 참가자 인덱스 정렬
        int[] positions = new int[width];

        for (int i = 0; i < width; i++) {
            positions[i] = i;
        }

        // 사다리 타고 이동
        for (boolean[] row : ladder) {
            for (int i = 0; i < width-1; i++) {
                // 가로선이 있을때만 swap
                if (row[i]) {
                    int temp = positions[i];
                    positions[i] = positions[i+1];
                    positions[i+1] = temp;
                }
            }
        }

        // 최종 결과 매핑
        for (int i = 0; i < width; i++) {
            finalResults.put(participantsName[i], results[positions[i]]);
        }
        return finalResults;
    }
}
