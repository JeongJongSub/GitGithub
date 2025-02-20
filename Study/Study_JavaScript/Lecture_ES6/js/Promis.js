//Promise
//자바스크립트는 싱글쓰레드이고 비동기이다
//비동기를 동기로 바꿔야 하는 일들이 많이 있다.
//이전에는 콜백함수를 많이 썼다. --에너지파 쏘는 이미지...CallBack Hell
//콜백지옥을 해결하기 위해서 Promise가 표준 내장 객체로 추가 되었다.
//Promise는 비동기 -> 동기로 처리하는 객체다.

//<Promise의 3가지 상태>
//Pending - 대기(지연)
//Fullfilled - 이행
//Rejected - 실패

//Promise의 3가지 메소드
//**콜백함수 : 어떤 함수에 인자로 전달된 함수
// 부르고 Call : 호출이 끝나면
// 다시 호출된다 Back
// ==> 그래서 콜백함수*/
/** 그런데 onclick = function()
 * 여기에서도 function()도 콜백 함수야 : onclick이 일어나고 불러지는 거니까
 *
 */


// then : 성공했을때
// catch
// finally


/////////////////////////////////////////////////실습
function a(x, y, callback) {
  const result = x + y;
  console.log("1");
  const double = callback(result);
  console.log("콜백에서 두배한 값:" + double);
}

function print(param) {
  console.log("난 콜백함수");
  console.log("더하기 결과: " + param);
  return param * 2;
}

a(1, 2, print);

////////////////////////////////////////

console.log('------------------------------')
const promise = new Promise(function(resolve){
  // resolve('성공');
  reject('실패 했어요');
});
console.log(promise)
promise.then(data=>{console.log(data); return data+" 했습니다"})
.then(function(data){console.log(data)})
.catch(error=>console.log(error))

//then이나 catch는 promise를 반환한다.


//////////////////////////////////////////////////////////////////
console.log('------------------------------')

function add1(param) {
  return param;
}

function add2(param) {
  return param + 2;
}

function add3(param) {
  return param + 3;
}

function callbackASync(callback) {
  const result1 = add1(1);
  console.log('첫번째 프로세스:', result1);

  const promise = new Promise(resolve=>{
    setTimeout(function() { 
      const result2 = callback(result1);
      resolve(result2);
    }, 2000);
  });

  promise.then(data=>{
    const result3 = add3(result2);
    console.log('세번째 프로세스:', result3);
  })
  
  
}




callbackASync(function(param) {
  const result2 = add2(param);
  console.log('두번째 프로세스:', result2);
  return result2;
});


///////////////////////////////////////////////////////////////
console.log('------------------------------')
/*
function add1(param) {
  return param;
}

function add2(param) {
  return param + 2;
}

function add3(param) {
  return param + 3;
}

function callbackASync(callback) {
  const result1 = add1(1);
  console.log('첫번째 프로세스:', result1);
  let result2;
  setTimeout(function() {
      result2 = callback(result1);
      const result3 = add3(result2);
      console.log('세번째 프로세스:', result3);
  }, 2000);
}

callbackASync(function(param) {
  const result2 = add2(param);
  console.log('두번째 프로세스:', result2);
  return result2;
});


*/














