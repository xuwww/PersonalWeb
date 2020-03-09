//提交回复
function post() {
    var questionId = $("#question_id").val();
    var content = $("#comment_content").val();

    comment2Target(questionId, 1, content);
}

function comment2Target(targetId, type, content) {
    if (!content) {
        alert("回复不能为空");
        return;
    }

    $.ajax({
        type: "post",
        url: "/comment",
        contentType: "application/json",
        data: JSON.stringify({
            "parentId": targetId,
            "content": content,
            "type": type
        }),
        success: function (response) {
            if (response.code == 200) {
                window.location.reload();
                // $("#comment_section").hide();
            } else {
                if (response.code == 2003) {
                    var isAccepted = confirm("response.message");
                    if (isAccepted) {
                        window.open("/signIn");
                        window.localStorage.setItem("closable", true);
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

//二级评论
function comment(e) {
    var commentId = e.getAttribute("data-id");
    var content = $("#input-" + commentId).val();
    comment2Target(commentId, 2, content);
}

//展开二级评论
function collapseComments(e) {
    var id = e.getAttribute("data-id");
    var comments = $("#comment-" + id);

    var attribute = e.getAttribute("data-collapse");
    if (attribute) {
        //折叠
        comments.removeClass("in");
        e.removeAttribute("data-collapse");
        e.classList.remove("active");
    } else {
        /*$.getJSON("/comment/" + id, function (data) {
            var commentBody = $("comment-body-" + id);
            var items = [];

            $.each(data.data, function (comment) {
                var c = $("<div/>", {
                    "class": "col-lg-12 col-md-12 col-sm-12 col-xs-12 comments",
                    html: comment.content
                })
            });

            $("<div/>", {
                "class": "col-lg-12 col-md-12 col-sm-12 col-xs-12 collapse sub-comments",
                "id": "comment-" + id,
                html: items.join("")
            }).appendTo(commentBody);*/

        //展开二级评论
        comments.addClass("in");
        e.setAttribute("data-collapse", "in");
        e.classList.add("active");

    }
}