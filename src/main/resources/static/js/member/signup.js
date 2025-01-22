
// JS 자동완성 : 1. VSCODE  2. 플러그인 : tobnine

// [1] 회원가입 함수
const onSignUp = ()=>{
    console.log('함수 실행');


    // (1) INPUT DOM 가져온다
    let midInput = document.querySelector('.midInput');                 console.log(midInput);
    let mpwdInput = document.querySelector('.mpwdInput');               console.log(mpwdInput);
    let mpwdCheckInput = document.querySelector('.mpwdCheckInput');     console.log(mpwdCheckInput);
    let mnameInput = document.querySelector('.mnameInput');             console.log(mnameInput);
    let memailInput = document.querySelector('.memailInput');           console.log(memailInput);

    // (2) DOM의 value(입력받은 값) 반환 받는다.
    let mid = midInput.value;                   console.log(mid);
    let mpwd = mpwdInput.value;                 console.log(mpwd);
    let mpwdCheck = mpwdCheckInput.value;       console.log(mpwdCheck);
    let mname = mnameInput.value;               console.log(mname);
    let memail = memailInput.value;             console.log(memail);
    // (!!생략) 다양한 유효성검사 코드 생략

    // (3) 입력받은 값들을 객체화 한다.
    let dataObj = {mid : mid, mpwd : mpwd, mname : mname, memail : memail};
    console.log(dataObj);

    // (4) fetch 옵션
     const option = {
        method : 'POST',        // HTTP 통신 요청을 보낼 때 사용할  METHOD 선택
        headers : {'Content-Type' : 'application/json'},    // HTTP 통신 요청 보낼 때 header body(본문) 타입 설정
        body : JSON.stringify(dataObj)      // HTTP 통신 요청 보낼 때 body(본문) 자료 대입하는데
                                            // JSON.stringfy(객체) : 객체타입 --> 문자열타입 변환, HTTP는 문자열 타입만 전송이 가능
     }

    // (5) fetch 통신
    fetch('/member/signup.do', option)          // fetch('통신할 URL', 옵션)
        .then(response => response.json())      // .then() 통신 요청 보내고 응답 객체를 반환받고 .json() 를 이용한 응답객체를 json타입으로 변환  
        .then(data => {                         // .then() json 으로 변환된 자료를 실행문 처리
            // (6) fetch 응답에 따른 화면 구현
            if(data == true) {                  // 만일 응답자료가 true 이면 성공아내, 로그인페이지로 이동
                alert('가입등록 완료');     
                location.href = "/member/login";
            } else {
                alert('가입실패');              // 만일 응답자료가 false 이면 실패 안내
            }
        })
        .catch(error => {alert('가입오류 : 관리자에게 문의');}) // 통신오류가 발생하면 오류 메세지 안내 



}