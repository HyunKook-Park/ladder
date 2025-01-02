import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // 사다리 타기 할 인원 수 입력
        System.out.println("참여 인원 수를 입력하세요. : ");
        int participantsCounts = scanner.nextInt();
        scanner.nextLine();

        // 사다리 타기 할 참가자들 이름 입력 (인원 수에 맞게)
        String[] participantsName = new String[participantsCounts];
        System.out.println("참가자 이름을 입력하세요. : ");
        for (int i = 0; i < participantsCounts; i++) {
            System.out.printf("참가자 %d 번 : ", i + 1);
            participantsName[i] = scanner.nextLine();
        }

        // 당첨 항목 입력
        String[] results = new String[participantsCounts];
        System.out.println("당첨 항목을 입력하세요. : ");
        for (int i = 0; i < participantsCounts; i++) {
            System.out.printf("결과 %d: ", i + 1);
            results[i] = scanner.nextLine();
        }

        // 사다리 높이 설정
        int ladderHeight = participantsCounts * 2;

        // 사다리 생성(greedy algorithm)
        boolean[][] ladder = generateLadderGreedy(participantsCounts, ladderHeight);

        // 사다리 출력
        printLadder(ladder, participantsName, results);

        // 결과 계산 및 출력
        Map<String, String> finalResults = calculateResultsBubbleSort(participantsName,results,ladder);
        System.out.println("[사다리 게임 결과]");
        for(String participant : participantsName) {
            System.out.println(participant + ": " + finalResults.get(participant));
        }
    }
        // 사다리 생성
        private static boolean[][] generateLadderGreedy(int width, int height) {
            Random random = new Random();
            boolean[][] ladder = new boolean[height][width - 1];
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    if(j>0 && ladder[i][j-1]) {
                        ladder[i][j] = false;
                    } else{
                        ladder[i][j] = random.nextBoolean();
                    }
                }
        }

            return ladder;
    }

        // 참가자 출력
    private static void printLadder(boolean[][] ladder, String[] participantsName, String[] results) {
        int width = participantsName.length;

        System.out.println("\n[참가자]");
        for (String participant : participantsName) {
            System.out.println(participant + "\t");
        }
        System.out.println();

        // 사다리 출력
        System.out.println("\n[사다리]");
        for (boolean[] row : ladder) {
            for (int j = 0; j < row.length; j++) {
                System.out.println("|");
                if(j<width-1){
                    System.out.println(row);
                }
            }
        }
    }

    private static Map<String, String> calculateResultsBubbleSort(String[] participantsName, String[] results, boolean[][] ladder){
        int width = participantsName.length;
        Map<String, String> finalResults = new HashMap<>();

        // 참가자 인덱스 정렬
        int[] positions = new int[width];
        for (int i = 0; i < width; i++) {
            positions[i] = i;
        }

        for (boolean[] row : ladder) {
            for (int i = 0; i < width-1; i++) {
                if (row[i]) {
                    int temp = positions[i];
                    positions[i] = positions[i+1];
                    positions[i+1] = temp;
                }
            }
        }
        for (int i = 0; i < width; i++) {
            finalResults.put(participantsName[i], results[positions[i]]);
        }
        return finalResults;
    }
}