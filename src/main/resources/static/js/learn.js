//提交回复
function post(){
    var questionId = $("#question_id").val();
    var content = $("#comment_content").val();
    comment2target(questionId,1,content);
}
function comment2target(targetId,type,content) {
    if (!content){
        alert("回复内容不能为空！");
        return;
    }
    $.ajax({
        type:"POST",
        url:"/comment",
        contentType:'application/json',
        data:JSON.stringify({
            "parentId":targetId,
            "content":content,
            "type":type
        }),
        success:function (response) {
            if (response.code == 200){
                window.location.reload();
            }else {
                if (response.code == 2003){
                    var isAccepted = confirm(response.message);
                    if (isAccepted){
                        window.open("https://github.com/login/oauth/authorize?client_id=7f1dd2f20c7b0c481848&redirect_uri=http://localhost:8887/callback&scope=user&state=1");
                        window.localStorage.setItem("closable",true);
                    }
                }else {
                    alert(response.message);
                }
            }
            console.log(response)

        },
        dataType:"json"

    });
}
function comment(e) {
    var commentId = e.getAttribute("data-id");
    var content = $("#input-"+commentId).val();
    comment2target(commentId,2,content);
}

//展开二级评论
function collapseComments(e){
    var id = e.getAttribute("data-id");
    var comments = $("#comment-"+id);
    // comments.toggleClass("in");

    var collapse = e.getAttribute("data-collapse");
    if (collapse){
        comments.removeClass("in");
        e.removeAttribute("data-collapse");
        e.classList.remove("active");
    }
    else {
        comments.addClass("in");
        e.setAttribute("data-collapse","in");
        e.classList.add("active");
    }



}