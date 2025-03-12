package example.상속과구현;

class Car2 { // 자동차 클래스 선언
    Tire2 tire2; // 일반타이어 타입 멤버변수 선언
    public void roll(){ tire2.roll(); } // 일반타이어 타입으로 roll 메소드 호출
} // f end
interface Tire2 { // 일반타이어 인터페이스 선언
    void roll(); // 추상메소드 : 구현부가 없는 메소드
}
class HankookTire2 implements Tire2{ // extends 클래스명  vs implements 인터페이스명
    @Override
    public void roll() {
        System.out.println("한국 타이어가 회전합니다.");
    }
}
class KumhoTire2 implements Tire2 {
    @Override
    public void roll() {
        System.out.println("금호 타이어가 회전합니다.");
    }
}

public class 구현예제 {
    public static void main(String[] args) {
        // [1] 오류
        Car2 myCar = new Car2();
        //myCar.roll(); // tire2에 인스턴스가 존재하지 않으므로 roll 메소드 실행할 수 없다.

        // [2] 한국 타이어가 회전합니다.
        Car2 yourCar = new Car2();
        yourCar.tire2 = new HankookTire2(); // 'Tire2' 추상메소드를 'HankookTire2'클래스가 구현 했으므로 'HankookTire2' 구현(객)체
        yourCar.roll(); // tire2 에 HankookTire2 인스턴스를 대입 했으므로 roll 메소드 실행

        // [3] 한국 타이어가 회전합니다.
        myCar.tire2 = new HankookTire2();
        myCar.roll(); // tire2 에 HankookTire2 인스턴스를 대입 했으므로 roll 메소드 실행

        // [4] 금호 타이어가 회전합니다.
        myCar.tire2 = new KumhoTire2(); // 'Tire2' 추상메소드를 'KumhoTire2' 클래스가 구현 했으므로 'KumhoTire2' 구현(객)체
        myCar.roll(); // tire2 에 KumhoTire2 인스턴스를 대입 했으므로 roll 메소드 실행

        // [5] 한국 타이어가 회전합니다.
        yourCar.roll(); // yourCar는 tire2 변경을 안했으므로 roll 메소드 그대로 실행
    }
}





















