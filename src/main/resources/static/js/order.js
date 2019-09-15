let adminVue = new Vue({
    el: "#admin",
    data: {
        name: '',
        level: '',
        portrait: ''
    },
    mounted: function () {
        var settings = {
            "async": true,
            "crossDomain": true,
            "url": "adminInfo",
            "method": "POST",
            "success": function (response) {
                var json = $.parseJSON(response);
                adminVue.name = json.name;
                adminVue.level = json.level;
                adminVue.portrait = json.portrait;
            }
        };
        $.ajax(settings);
    }
});

var orderVue = new Vue({
    el: '#order',
    data: {
        choose: '部位',
        startTime: '',
        endTime: '',
        orders: [],
    },
    methods: {
        getOrdersByProduct: function() {
            var form = new FormData();
            form.append("product", $("#productInput").val());

            var settings = {
                "async": true,
                "crossDomain": true,
                "url": "order/product",
                "method": "POST",
                "processData": false,
                "contentType": false,
                "mimeType": "multipart/form-data",
                "data": form,
                "success":function (response) {
                    let json = $.parseJSON(response);
                    json.forEach(function (item) {
                        if (item.state === "fail") item.state = "交易失败";
                        else if (item.state === "success") item.state = "交易成功";
                        else item.state = "运输中";
                    });
                    orderVue.orders = json;
                }
            };

            $.ajax(settings);
        },
        getOrdersByBuyer: function () {
            var form = new FormData();
            form.append("buyer", $("#buyerInput").val());

            var settings = {
                "async": true,
                "crossDomain": true,
                "url": "order/buyer",
                "method": "POST",
                "processData": false,
                "contentType": false,
                "mimeType": "multipart/form-data",
                "data": form,
                "success": function (response) {
                    let json = $.parseJSON(response);
                    json.forEach(function (item) {
                        if (item.state === "fail") item.state = "交易失败";
                        else if (item.state === "success") item.state = "交易成功";
                        else item.state = "运输中";
                    });
                    orderVue.orders = json;
                }
            };

            $.ajax(settings);
        },
        getOrders: function () {
            var form = new FormData();
            form.append("fromDate", this.startTime);
            form.append("endTime", this.endTime);

            var settings = {
                "async": true,
                "crossDomain": true,
                "url": "order/all",
                "method": "POST",
                "processData": false,
                "contentType": false,
                "mimeType": "multipart/form-data",
                "data": form,
                "success": function (response) {
                    let json = $.parseJSON(response);
                    json.forEach(function (item) {
                        if (item.state === "fail") item.state = "交易失败";
                        else if (item.state === "success") item.state = "交易成功";
                        else item.state = "运输中";
                    });
                    orderVue.orders = json;
                }
            };

            $.ajax(settings);
        },
        chooseTime: function () {
            this.startTime = document.getElementById("startTime").value;
            this.endTime = document.getElementById("endTime").value;
            this.getOrders();
        },
        orderClick: function (id) {
            window.location.href = "orderInformation?oId=" + id;
        }
    },
    mounted: function () {
        this.getOrders();
    }
});