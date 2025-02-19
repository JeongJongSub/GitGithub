//Rest파라미터 ...
//함수 인자에 ...
function add(arg1, arg2, ...args) {
  console.log(arg1, arg2);
  console.log(args);
  return args.map((x) => {x*2});
}
// console.log(add(...[1, 2, 3, 4, 5]));

// console.log( add(1,2,3,4,5));
console.log( add(...[1,2,3,4,5])); //이건 스프래드 연산자

console.log('\n------------------------------------------------------\n')
/*
var i = 0;
var a = {};
a["foo" + ++i] = i;
a["foo" + ++i] = i;
a["foo" + ++i] = i;
console.log(a.foo1); // 1
console.log(a.foo2); // 2
console.log(a.foo3); // 3
console.log(a);
*/

console.log('\n------------------------------------------------------\n')

let i = 0;
const a = {
    ["foo" + ++i]: i,
    ["foo" + ++i]: i,
    ["foo" + ++i]: i
};
console.log(a.foo1); // 1
console.log(a.foo2); // 2
console.log(a.foo3); // 3
console.log(a);


const param = 'size';
const config = {
    [param]: 12,
    ["mobile" + param.charAt(0).toUpperCase() + param.slice(1)]: 4
};
console.log(config); // { size: 12, mobileSize: 4 }

console.log('\n------------------------------------------------------\n')
//구조분해 할당
const arr = [1, 2, 3];
const [x, y, z] = arr;
console.log(arr);

/*
// 혹은
let x, y, z;
[x, y, z] = arr;
console.log(arr);
*/

console.log('\n------------------------------------------------------\n')

const foo = ['one', 'two', 'three'];
const [q, w, e] = foo;
console.log(q); // "one"
console.log(w); // "two"
console.log(e); // "three"

// 대괄호 생략시 (ES5 문법)
const fooES5 = ['one', 'two', 'three'];
let oneES5, twoES5, threeES5;
oneES5 = fooES5[0];
twoES5 = fooES5[1];
threeES5 = fooES5[2];
console.log(oneES5); // "one"
console.log(twoES5); // "two"
console.log(threeES5); // "three"

console.log('\n------------------------------------------------------\n')
let ax,bx,cx;
[ax,bx] = [1,2];
console.log(ax, bx);

console.log('\n------------------------------------------------------\n')

let ay = 1;
let by = 2;
[ay,by] = [by,ay]
console.log(ay,by);

console.log('\n------------------------------------------------------\n')
function xx(){
  az=[1,2,3]
  return az
}
let qq,ww,ee;
[qq,ww,ee] = xx()
console.log(qq,ww,ee);