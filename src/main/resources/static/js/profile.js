$(document).ready(function () {
    $("#category-input").click(function () {
        let categoryName = $("#category-name").val();
        if(categoryName === "" || categoryName == null){
            return;
        }
        $.ajax({
            type: "post",
            url: "/profile/category",
            contentType: "application/json",
            data: JSON.stringify({
                "category": categoryName
            }),
            success: function (response) {
                if (response.code === 200) {
                    window.location.reload();
                } else {
                    if (response.code === 2003) {
                        const isAccepted = confirm("response.message");
                        if (isAccepted) {
                            window.open("/signIn");
                            window.localStorage.setItem("closable", true);
                        }
                    } else {
                        alert(response.message());
                    }
                }
                console.log(response);
            },
            dataType: "json"
        })
    });
});
