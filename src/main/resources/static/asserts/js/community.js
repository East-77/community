function postComment() {
    var questionId = $("#question_id").val();
    var content = $("#comment_content").val();
    $.ajax({
        type: "post",
        url: "/comment",
        contentType: "application/json",
        data: JSON.stringify({
            "parentId": questionId,
            "content": content,
            "type": 1
        }),
        dataType: "json",
        success: function (response) {
            if (response.code == 200) {
                $("#comment_section").hide();
            } else {
                if (response.code == 3001) {
                    var isAccepted = confirm(response.message);
                    if (isAccepted) {
                        window.open("https://github.com/login/oauth/authorize?client_id=7ffcf9222a376d295f34&redirect_uri=http://localhost:8080/callback&scope=user&state=1");
                        //localStorage,类似cookie但有其优点
                        window.localStorage.setItem("closable",true);
                    }
                } else {
                    alert(response.message);
                }
            }
        }
    });
}