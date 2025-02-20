//* < JavaScript ES6 문법 > */

//* 01.변수선언 & 스코프
    // var는 함수 스코프
    // const, let 은 블록 스코프

//* 02.Template Literals
    // 빽틱(`)으로 문자열을 감싸고 ${변수명} 을 넣어 사용할 수 있다.
const temLit = 100;
console.log(`temLit은 ${temLit}이다`);


//* 03.this키워드
    // this는 객체를 가리키는 키워드. 즉 객체
    // this는 호출 주체다. 
    // 호출한 주체가 없는경우 기본값으로 window객체다.
    // bind() this를 지정할 수 있다. ※ 한번만 적용된다 ※
    // 애로우함수의 this는 언제나 상위 스코프의 this, 자기자신을 가리키는 this는 없다.


// console.log(this === window) //(브라우저에서) true
const obj = {
    username : 'kim',
    func : function() {
        console.log(this === obj);
        console.log(this.username === 'kim');
    }
};

obj.func();
/*
const btn = document.querySelector('button');
btn.addEventListener('click',function(){
    console.log(this);
    console.log(this === btn) //true
});
*/


//* 동기와 비동기


//* for ~ of 문



//* 함수 선언문과 표현식
// 선언문은 호이스팅이 일어난다.
// 표현식은 호이스팅이 일어나지 않는다.
//AirBnb의 JS Style가이드에서 함수선언식보다는 함수표현식을 지향


//* 애로우 함수
//const 변수명=(입력)=>출력
//단, 애로우함수가 기존의 함수선언문이나 함수표현식을 완전히 대신할 수 없다.
//인자가 하나일때는 괄호(()) 생략가능
//명령어가 하나일때는 중괄호({}) 생략가능
//this키워드 사용시 애로우를 사용하면 안된다.


//* 배열 관련 유용한 함수
//forEach
//map
//reduce


//* 클래스 정의
// 클래스 선언문은 호이스팅이 되지 않는다.
// 클래스 표현식은 잘 않쓴다.

//* 프로토타입
//자스의 모든 객체는 프로토타입객체를 가지고 있다.
//프로토타입객체,체인,상속이 있다.
//프로토타입객체 : 해당객체가 사용할 수 있는 메소드와 속성을 정의한다.
//프로토타입체인 : 객체는 체인을 따라 상속받은 메서드와 속성을 찾는다.
//프로토타입상속 : 객체는 다른 객체의 프로토타입을 상속받아 메서드와 속성을 공유할 수 있다.


//* import와 export
//페이지 방식

//Babel : ES6 -> ES5로 트랜스파일러 해주는 것것
//Webpack : 번들러라고 하는데 여러 모듈을 하나로 묶는걸 말한다.
//export는 여러번
//export default는 한번만 사용 가능하다.


//* Spread 연산자


//* Rest파라미터


//* Computed Property Name


//* 구조분해 할당

//* Shorthand Property

//* 메소드 축약 표현


//* 객체 복사


//* Promise



//* async/await