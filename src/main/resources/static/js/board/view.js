console.log( 'view.js open' )

// [1] 개별 게시물 조회 요청 함수
const onFind = () =>{

    // 1. 현재 보고자하는 게시물의 번호를 찾기 / 사용자가 클릭한 게시물 번호 찾기
    // board/view?bno = 3 , 즉] url 경로상의 쿼리스트링으로 bno 존재한다.
    // console.log( new URL() ) // new URL() : url 정보를 담는 객체를 생성
    // console.log( new URL( location.href ) ) // 현재 페이지의 url 정보를 담은 객체를 생성
    // console.log( new URL( location.href ).searchParams ) // 현재 페이지의 url 정보중에 매개변수를 반환 속성

    const bno = new URL( location.href ).searchParams.get('bno')

    // 2. fetch
    fetch( `/board/find.do?bno=${ bno }` )
        .then( r => r.json() )
        .then( data => {
            console.log( data );
            document.querySelector('.midbox').innerHTML = data.mid
            document.querySelector('.bviewbox').innerHTML = data.bview
            document.querySelector('.cdatebox').innerHTML = data.cdate

            document.querySelector('.btitle').innerHTML = data.btitle
            document.querySelector('.bcontent').innerHTML = data.bcontent
        })
        .catch( e =>{ console.log(e); })

}
onFind(); // 페이지가 열릴때 개별 게시물 조회 함수 실행


// [2] 댓글 쓰기 요청 함수, 실행조건 : 댓글 게시 버튼을 클릭했을 때
const onReplyWrite= () => {
    // 1. 입력받은 값 가져오기
    const rcontentInput = document.querySelector('rcontentInput')
    const rcontent = rcontentInput.value;
    // 2. 현재 게시물 번호
    const bno = new URL(location.href).searchParams.get('bno');
    // 3. 객체화
    const obj = {rcontent : rcontent, bno : bno}
    // 4. fetch
    const option = {
        method : 'POST',
        haeders : {'Content-Type' : 'application/json'},
        body : JSON.stringify(obj)
    }
    fetch('/reply/write.do', option)
        .then(r => r.json)
        .then(data => {
            if(data == true) {
                alert('댓글등록 성공');
                // 글쓰기 성공 시 댓글 전체 조회 함수 실행, 새로고침
                onReplyFindAll();
            } else {
                alert('댓글등록 실패');
            }
        })
        .catch(e => {console.log(e);})
}

// [3] 개별 게시물의 존재하는 댓글 조회 요청 함수
const onReplyFindAll = ( ) => {
        // [준비물] bno
        const bno = new URL( location.href ).searchParams.get("bno");
        // fetch queryString
        fetch( `/reply/findall.do?bno=${bno}` )
                .then( r => r.json() )
                .then( data => {
                        console.log( data );
                        const replybox = document.querySelector('.replybox')
                        let html = ``;
                        data.forEach( reply =>{
                                html +=`<div class="card mt-3">
                                                  <div class="card-header">
                                                          <img src="/img/${ reply.mimg}" style="width:30px;" />
                                                    ${ reply.mid }
                                                  </div>
                                                  <div class="card-body">
                                                     ${ reply.rcontent }
                                                  </div>
                                                </div>`
                        }); // for end
                        replybox.innerHTML = html;
                })
                .catch( e => { console.log(e); })
} // class end
onReplyFindAll();