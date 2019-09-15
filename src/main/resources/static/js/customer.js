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

var customerVue = new Vue({
    el: "#customer",
    data: {
        customers: [],
        male: true,
        female: true,
        isVIP: true,
        notVIP: true,
        L0: true,
        L1: true,
        L2: true,
        L3: true,
        L4: true,
        L5: true,
        fromAge: 0,
        toAge: 200
    },
    methods: {
        deleteBuyer: function (id) {
            var form = new FormData();
            form.append("id", id);

            var settings = {
                "async": true,
                "crossDomain": true,
                "url": "customer/delete",
                "method": "POST",
                "processData": false,
                "contentType": false,
                "mimeType": "multipart/form-data",
                "data": form,
                "success": function (response) {
                    if (response === "success") {
                        alert("删除用户成功");
                        customerVue.fromAge = 0;
                        customerVue.toAge = 200;
                        customerVue.getCustomers();
                    }
                }
            };
            $.ajax(settings);
        },
        getCustomersByName: function () {
            var form = new FormData();
            form.append("name", $("#nameInput").val());

            var settings = {
                "async": true,
                "crossDomain": true,
                "url": "customer/name",
                "method": "POST",
                "processData": false,
                "contentType": false,
                "mimeType": "multipart/form-data",
                "data": form,
                "success": function (response) {
                    let json = $.parseJSON(response);
                    json.forEach(function (item) {
                        if (item.gender === 0) item.gender = "男";
                        else item.gender = "女";
                        if (item.vip === 0) item.vip = "否";
                        else item.vip = "是";
                    });
                    customerVue.customers = json;
                }
            };
            $.ajax(settings);
        },
        getCustomers: function () {
            let form = new FormData();
            form.append("male", this.male);
            form.append("female", this.female);
            form.append("isVIP", this.isVIP);
            form.append("notVIP", this.notVIP);
            form.append("L0", this.L0);
            form.append("L1", this.L1);
            form.append("L2", this.L2);
            form.append("L3", this.L3);
            form.append("L4", this.L4);
            form.append("L5", this.L5);
            form.append("fromAge", this.fromAge);
            form.append("toAge", this.toAge);

            var settings = {
                "async": true,
                "crossDomain": true,
                "url": "customer/all",
                "method": "POST",
                "processData": false,
                "contentType": false,
                "mimeType": "multipart/form-data",
                "data": form,
                "success": function (response) {
                    let json = $.parseJSON(response);
                    json.forEach(function (item) {
                        if (item.gender === 0) item.gender = "男";
                        else item.gender = "女";
                        if (item.vip === 0) item.vip = "否";
                        else item.vip = "是";
                    });
                    customerVue.customers = json;
                }
            };
            $.ajax(settings);
        },
        ageClick: function () {
            this.fromAge = $('#minInput').val() === "" ? this.fromAge : $('#minInput').val();
            this.toAge = $('#maxInput').val() === "" ? this.toAge : $('#maxInput').val();
            this.getCustomers();
        }
    },
    mounted: function () {
        this.getCustomers();
        $('#gender').multiselect({
            buttonWidth: '160px',
            dropRight: true,
            nonSelectedText: '性别',
            allSelectedText: '全部性别',
            onChange: function (option, checked) {
                if ($(option).val() === "male") {
                    customerVue.male = checked;
                } else {
                    customerVue.female = checked;
                }
                customerVue.getCustomers();
            }
        });
        $('#level').multiselect({
            buttonWidth: '160px',
            dropRight: true,
            nonSelectedText: '等级',
            allSelectedText: '全部等级',
            onChange: function (option, checked) {
                if ($(option).val() === "0") {
                    customerVue.L0 = checked;
                } else if ($(option).val() === "1") {
                    customerVue.L1 = checked;
                } else if ($(option).val() === "2") {
                    customerVue.L2 = checked;
                } else if ($(option).val() === "3") {
                    customerVue.L3 = checked;
                } else if ($(option).val() === "4") {
                    customerVue.L4 = checked;
                } else if ($(option).val() === "5") {
                    customerVue.L5 = checked;
                }
                customerVue.getCustomers();
            }
        });
        $('#vip').multiselect({
            buttonWidth: '160px',
            dropRight: true,
            nonSelectedText: 'VIP',
            allSelectedText: '全部VIP',
            onChange: function (option, checked) {
                if ($(option).val() === "yes") {
                    customerVue.isVIP = checked;
                } else {
                    customerVue.notVIP = checked;
                }
                customerVue.getCustomers();
            }
        });
    }
});