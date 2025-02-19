//<Promise의 3가지 상태>
//Pending - 대기(지연)
//Fullfilled - 이행
//Rejected - 실패

//<대표적인 3개지 콜백함수>
// then() : 성공했을때
// catch() : 실패할 경우
// finally() : 성공이든실패든 마지막에 항상 실행할 콜백 함수를 등록

//*콜백함수는 인자로 등록되는 함수

// Promise생성자 함수에 콜백함수를 정의
// 콜백함수의 인자로 resolve, reject를 정의한다.
// 비동기가 정적처리 되면 resolve()
// 비동기가 실패하면 reject()

// fetch("https://jsonplaceholder.typicode.com/photos")
//   .then((response) => {return(response.json())})
//   .then((json) => console.log(json));


// fetch("https://jsonplaceholder.typicode.com/posts/1")
//   .then(function(response){return response.json()})
//   .then(function(post){console.log(post);return post.userId})
//   .then(function(userId){ return "https://jsonplaceholder.typicode.com/users/" +userId;})
//   .then(function(url){return fetch(url);})
//   .then(function(response){return response.json()})
//   .then(function(user){console.log("user:", user)})
//   .catch(function(e){console.log("e:", e)});

  async function getUser() {
    const response = await fetch("https://jsonplaceholder.typicode.com/posts/1");
    const post = await response.json();
    console.log(post.userId)
    const user = (await fetch("https://jsonplaceholder.typicode.com/users/" +post.userId)).then(resp=>resp.json());
    console.log('1번글을 작성한 사용자는 : ', user)

  }
  getUser();