function post() {
    var questionId = $("#question_id").val();
    var content = $("#comment_content").val();

    if(!content){
        alert("回复不能为空")
        return;
    }

    $.ajax({
        type: "post",
        url: "/comment",
        contentType: "application/json",
        data: JSON.stringify({
            "parentId": questionId,
            "content": content,
            "type": 1
        }),
        success: function (response) {
            if (response.code == 200) {
                window.location.reload();
                // $("#comment_section").hide();
            } else {
                if (response.code == 2003) {
                    var isAccepted = confirm("response.message");
                    if(isAccepted){
                        window.open("/signIn");
                        window.localStorage.setItem("closable",true);
                    }
                } else {
                    alert(response.message);
                }
            }
            console.log(response);
        },
        dataType: "json"
    });
}