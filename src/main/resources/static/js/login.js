let loginVue = new Vue({
    el: "#login",
    methods: {
        login: function () {
            var form = new FormData();
            form.append("account", $("#login-username").val());
            form.append("password", $("#login-password").val());

            var settings = {
                "async": true,
                "crossDomain": true,
                "url": "login/login",
                "method": "POST",
                "processData": false,
                "contentType": false,
                "mimeType": "multipart/form-data",
                "data": form,
                "success": function (response) {
                    if (response === "fail") {
                        alert("用户名或密码错误");
                    } else {
                        window.location.href = "index";
                    }
                }
            };
            $.ajax(settings);
        }
    },
    mounted: function () {

    }
});