package baseball;
import camp.nextstep.edu.missionutils.Randoms;
import camp.nextstep.edu.missionutils.Console;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Application {
    public static void main(String[] args) {
        // TODO: 프로그램 구현
        // 다시 게임을 할 지 체크
        boolean playAgain = true;

        // 게임 루프
        while (playAgain) {
            // 무작위 3개의 숫자를 computer가 가지고 있습니다.
            List<Integer> computer = new ArrayList<>();

            while (computer.size() < 3) {
                int randomNumber = Randoms.pickNumberInRange(1, 9);
                if (!computer.contains(randomNumber)) {
                    computer.add(randomNumber);
                }
            }

            // 스트라이크를 세기 위한 변수
            int strikeCount = 0;

            while(strikeCount != 3){
                strikeCount = baseBallGame(computer);
            }
            System.out.println(strikeCount+"개의 숫자를 모두 맞히셨습니다! 게임 종료");
            System.out.print("게임을 새로 시작하려면 1, 종료하려면 2를 입력하세요.");
            String reStart = Console.readLine();

            if (reStart.equals("1")) {
                // 게임을 다시 시작
                playAgain = true;
            } else if (reStart.equals("2")) {
                // 게임을 종료
                playAgain = false;
                System.out.println("게임을 종료합니다.");
            } else {
                // 잘못된 입력 처리
                System.out.println("잘못된 입력입니다. 종료합니다.");
                playAgain = false;
            }
        }
    }

    public static int baseBallGame(List<Integer> computer) {
        // 스트라이크를 세기 위한 변수
        int strikeCount = 0;
        // 볼을 세기 위한 변수
        int ballCount = 0;

        // 사용자에게 3개의 숫자를 입력 받습니다.
        System.out.print("숫자를 입력해주세요: ");
        String userNumber = Console.readLine();

        // 입력 값 검증
        if (userNumber.length() != 3 || !userNumber.matches("\\d{3}")) {
            throw new IllegalArgumentException("잘못된 입력: 반드시 3자리 숫자를 입력해야 합니다.");
        }

        // 스트라이크를 체크하고 이미 맞춘 자리(숫자)를 제외하기 위한 리스트
        List<Integer> unmatchedComputer = new ArrayList<>();
        List<Integer> unmatchedUser = new ArrayList<>();

        // 1. 스트라이크 체크
        for (int i = 0; i < computer.size(); i++) {
            int computerNum = computer.get(i);
            int userNum = Character.getNumericValue(userNumber.charAt(i));

            if (computerNum == userNum) {
                strikeCount++;  // 스트라이크일 경우 증가
            } else {
                // 스트라이크가 아니면 나중에 볼 체크를 위해 리스트에 저장
                unmatchedComputer.add(computerNum);
                unmatchedUser.add(userNum);
            }
        }

        // 2. 볼 체크 (스트라이크가 아닌 남은 숫자들 비교)
        for (int userNum : unmatchedUser) {
            if (unmatchedComputer.contains(userNum)) {
                ballCount++;
                unmatchedComputer.remove(Integer.valueOf(userNum));  // 중복 체크 방지
            }
        }

        // 결과 출력
        if (strikeCount > 0 && ballCount > 0) {
            System.out.println(ballCount + "볼 " + strikeCount + "스트라이크");
        } else if (strikeCount > 0) {
            System.out.println(strikeCount + "스트라이크");
        } else if (ballCount > 0) {
            System.out.println(ballCount + "볼");
        } else {
            System.out.println("낫싱");
        }

        // strikeCount 반환
        return strikeCount;
    }
}
