import java.util.Map;
import java.util.Scanner;

public class Main {
    private static final String PARTICIPANTS_COUNT = "참여 인원 수를 입력하세요. : ";
    private static final String PARTICIPANTS_PROMPT = "참가자 이름을 입력하세요. : ";
    private static final String RESULTS_PROMPT = "당첨 항목을 입력하세요. : ";
    private static final String INVALID_PARTICIPANTS_MESSAGE = "참여 인원 수는 1 이상이어야 합니다.";
    private static final int HEIGHT_MULTIPLIER = 2; // 사다리 높이

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // 사다리 타기 할 인원 수 입력
        System.out.println(PARTICIPANTS_COUNT);
        int participantsCounts = scanner.nextInt();

        if (participantsCounts < 1) {
            throw new IllegalArgumentException(INVALID_PARTICIPANTS_MESSAGE);
        }

        scanner.nextLine();

        String[] participantsName = getInput(PARTICIPANTS_PROMPT, participantsCounts, scanner);
        String[] results = getInput(RESULTS_PROMPT, participantsCounts, scanner);

        // 사다리 높이 설정
        int ladderHeight = participantsCounts * HEIGHT_MULTIPLIER;

        // 사다리 생성
        Ladder ladder = new Ladder(participantsCounts, ladderHeight);
        ladder.generateLadderGreedy();

        // 사다리 출력
        ladder.printLadder(participantsName, results);

        // 결과 계산 및 출력
        Map<String, String> finalResults = ladder.calculateResults(participantsName,results);

        System.out.println("[사다리 게임 결과]");

        for(String participant : participantsName) {
            System.out.println(participant + ": " + finalResults.get(participant));
        }
    }

    private static String[] getInput(String message, int count, Scanner scanner) {
        System.out.println(message);
        String[] inputs = new String[count];

        for (int i = 0; i < count; i++) {
            System.out.printf("입력 %d: ", i + 1);
            inputs[i] = scanner.nextLine();
        }

        return inputs;
    }
}