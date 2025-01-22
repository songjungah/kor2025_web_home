
// 로그인 함수
const onLogin = () => {
    console.log('함수 실행함');


    // (1) INPUT DOM 가져오기
    let midInput = document.querySelector('.midInput');
    let mpwdInput = document.querySelector('.mpwdInput');

    // (2) 가져온 DOM 의 value(입력값) 가져오기
    let mid = midInput.value;
    let mpwd = mpwdInput.value;

    // (!) 유효성검사 생략

    // (3) 입력받은 값들을 보낼 객체 만들기
    let dataObj = {mid : mid , mpwd : mpwd};

    // (4) fetch 함수 옵션 정의
    const option = {
        method : 'POST',
        headers : {'Content-Type' : 'application/json'},
        body : JSON.stringify(dataObj)
    }

    // (5) fetch 함수 실행
    fetch('/member/login.do', option) 
        .then(repsonse => repsonse.json())
        .then(data => {
            if(data == true) {
                alert('로그인 성공');
                location.href = "/";
            } else {
                alert('회원정보가 일치하지 않습니다.');
            }
        })
        .catch(error => {alert('시스템 오류 : 관리자에게 문의');
            console.log(error);
        })

    // (6) 결과에 따른 화면 제어

}