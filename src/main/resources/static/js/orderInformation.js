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

var orderInfoVue = new Vue({
    el: "#orderInfo",
    data: {
        oId: '',
        productName: '',
        size: '',
        price: '',
        brand: '',
        origin: '',
        destination: '',
        company: '',
        expressNumber: '',
        buyerName: '',
        birthday: '',
        location: '',
        phone: '',
        gender: '',
        createTime: '',
        endTime: '',
        quantity: '',
        allPrice: '',
        state: '',
        spId: '',
        buyerImg: '',
        productImg: ''
    },
    methods: {
        updateState: function () {
            var form = new FormData();
            form.append("id", this.oId);
            form.append("state", $("#state").val());

            var settings = {
                "async": true,
                "crossDomain": true,
                "url": "orderInformation/update",
                "method": "POST",
                "processData": false,
                "contentType": false,
                "mimeType": "multipart/form-data",
                "data": form,
                "success": function (response) {
                    if (response === "fail") {
                        alert("修改失败");
                    } else {
                        alert("修改成功");
                        orderInfoVue.getInfo();
                    }
                }
            };

            $.ajax(settings);
        },
        getInfo: function () {
            var form = new FormData();
            form.append("id", this.oId);

            var settings = {
                "async": true,
                "crossDomain": true,
                "url": "orderInformation/brief",
                "method": "POST",
                "processData": false,
                "contentType": false,
                "mimeType": "multipart/form-data",
                "data": form,
                "success": function (response) {
                    let json = $.parseJSON(response);
                    orderInfoVue.productName = json.productName;
                    orderInfoVue.size = json.size;
                    orderInfoVue.price = json.price;
                    orderInfoVue.brand = json.brand;
                    orderInfoVue.origin = json.origin;
                    orderInfoVue.destination = json.destination;
                    orderInfoVue.company = json.company;
                    orderInfoVue.expressNumber = json.expressNumber;
                    orderInfoVue.buyerName = json.buyerName;
                    orderInfoVue.birthday = json.birthday;
                    orderInfoVue.location = json.location;
                    orderInfoVue.phone = json.phone;
                    orderInfoVue.gender = json.gender === 0 ? "男性" : "女性";
                    orderInfoVue.createTime = json.createTime;
                    orderInfoVue.endTime = json.endTime;
                    orderInfoVue.allPrice = json.allPrice;
                    orderInfoVue.quantity = json.quantity;
                    orderInfoVue.state = json.state;
                    $("#state").val(json.state);
                    orderInfoVue.spId = json.spId;
                    orderInfoVue.buyerImg = json.buyerImg;
                    orderInfoVue.productImg = json.productImg;
                }
            };

            $.ajax(settings);
        },
        goBack: function () {
            window.location.href = "order";
        },
        saveChangeState() {

        }
    },
    mounted: function () {
        this.oId = window.location.search.substring(1).substring(4);
        this.getInfo();
    }
});