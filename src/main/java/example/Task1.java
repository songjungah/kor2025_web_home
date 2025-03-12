package example;

import java.util.ArrayList;
import java.util.List;
/*
    자바의 상속
        - 기존 클래스(부모)의 멤버변수 와 메소드를 새로운 클래스(자식)에게 물려(연장하다)주는 기능
        - 핵심 : 1. 클래스 코드 의 재사용 , 2. 유지보수 용이 , 3. **다형성**
        - 자식클래스명 extends 부모클래스명{ }

    자바는 100% 객체지향 이면서 다형성 특징 갖는다.
        - 근거 : 자바의 최상위 클래스는 Object 클래스가 존재한다.
        즉] 모든 클래스(+라이브러리) 는 Object 로 부터 상속를 자동으로 받는다. 100% 상속

    다형성 : 다양한 형태(모양/타입) 성질
       - 기본타입 : 기본타입변환
       - 참조타입 :
                  1. 강제타입변환(캐스팅) : 부모타입이 자식타입 변환      ( *처음 객체가 생성될때의 타입이면 가능* )
                        (1) Animal --> Dog  부모 -> 자식 (불가능하다.)
                        (2) Dog -> Animal   자식 -> 부모
                            Animal -> Dog    부모 -> 자식  ( 가능하다.)
                  2. 자동타입변환 : 자식타입이 부모타입 변환             ( 자식이 부모로부터 extends 했을경우 )
                        (1) Dog -> Animal
                        (2) Animal -> Object
*/


class Animal { // 동물
}
class Dog extends Animal{ // 강아지 // 상속 받았다.
}
class Cat extends Animal { // 고양이 // 상속 받았다.
}
public class Task1 {
    public static void main(String[] args) {
        Animal animal = new Animal(); // 동물 객체
        Dog dog = new Dog(); // 강아지 객체
        Cat cat = new Cat(); // 고양이 객체
        // 고민1 : 아래에 List 객체에 3개 객체(동물,강아지,고양이)를 모두 대입할 수 있는 방법(2가지이상)
            // [1] : Object 타입 할 경우에는 Animal/Dog/Cat 타입 모두 대응할 수 있다.
            // [2] : Animal 타입 할 경우에는 Animal/Dog/Cat 타입 모두 대응할 수 있다.
            // [*] : Dog/Cat 타입 할 경우에는 상위타입 또는 형제타입 를  대응할 수 없다.
        List <Animal> list1 = new ArrayList<>();
            list1.add( animal );
            list1.add( dog );
            list1.add( cat );
        // --
        method1( animal ); // animal : 인수 ? O 매개변수? X
        method1( dog );
        method1( cat );
    }
    // 고민2 : 메소드 매개변수에 3개 객체를 모두 대입할 수 있는 방법(2가지이상)
    public static void method1( Animal param ){ // param : 인수x 매개변수o
    } // f end

    // 용어고민3?
        // 1. 인자/인수 : 함수로 들어가는 값 그자체 2.매개변수 : 함수로 들어오는 인수/값를 저장하는 메모리공간 , (중)매 : 인자값과 변수 연결
        // 3. 리터럴 : 값 그자체  , 4.변수 : 데이터를 저장할수있는 메모리공간
        // 5. 타입/자료형 : 값을 표현할 수 있는 형태/모양/형식
        // 6. 다형성 : 타입변환

} // clas end
