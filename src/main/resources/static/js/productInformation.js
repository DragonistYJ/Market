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

var productInfo = new Vue({
    el: "#productInfo",
    data: {
        spId: '',
        name: '',
        inventory: '',
        price: '',
        description: '',
        size: '',
        brand: '',
        category: '',
        crowed: '',
        img1: '',
        img2: '',
        img3: '',
        img4: '',
        pictures: '',
        pic1: '',
        pic2: '',
        pic3: '',
        pic4: ''
    },
    methods: {
        deleteProduct: function () {
            let form = new FormData();
            form.append("id", this.spId);

            var settings = {
                "async": true,
                "crossDomain": true,
                "url": "product/delete",
                "method": "POST",
                "processData": false,
                "contentType": false,
                "mimeType": "multipart/form-data",
                "data": form,
                "success": function (response) {
                    if (response === "success") {
                        window.location.href = "product";
                    } else {
                        alert("删除商品失败");
                    }
                }
            };
            $.ajax(settings);
        },
        selectPicture: function () {
            $("#pictureInput").change(function () {
                if (this.files.length >= 1) {
                    let canvas1 = document.createElement("canvas");
                    let context1 = canvas1.getContext("2d");
                    canvas1.width = 200;
                    canvas1.height = 126;
                    let img1 = new Image();
                    img1.src = window.URL.createObjectURL(this.files[0]);
                    img1.onload = function () {
                        context1.drawImage(img1, 0, 0, 200, 126);
                        productInfo.pic1 = canvas1.toDataURL("image/jpeg");
                    };

                }

                if (this.files.length >= 2) {
                    let canvas2 = document.createElement("canvas");
                    let context2 = canvas2.getContext("2d");
                    canvas2.width = 200;
                    canvas2.height = 126;
                    let img2 = new Image();
                    img2.src = window.URL.createObjectURL(this.files[1]);
                    img2.onload = function () {
                        context2.drawImage(img2, 0, 0, 200, 126);
                        productInfo.pic2 = canvas2.toDataURL("image/jpeg");
                    };
                }

                if (this.files.length >= 3) {
                    let canvas3 = document.createElement("canvas");
                    let context3 = canvas3.getContext("2d");
                    canvas3.width = 200;
                    canvas3.height = 126;
                    let img3 = new Image();
                    img3.src = window.URL.createObjectURL(this.files[2]);
                    img3.onload = function () {
                        context3.drawImage(img3, 0, 0, 200, 126);
                        productInfo.pic3 = canvas3.toDataURL("image/jpeg");
                    };
                }

                if (this.files.length >= 4) {
                    let canvas4 = document.createElement("canvas");
                    let context4 = canvas4.getContext("2d");
                    canvas4.width = 200;
                    canvas4.height = 126;
                    let img4 = new Image();
                    img4.src = window.URL.createObjectURL(this.files[3]);
                    img4.onload = function () {
                        context4.drawImage(img4, 0, 0, 200, 126);
                        productInfo.pic4 = canvas4.toDataURL("image/jpeg");
                    };
                }
            })
        },
        getBriefInfo: function () {
            let form = new FormData();
            form.append("id", this.spId);

            var settings = {
                "async": true,
                "crossDomain": true,
                "url": "productInfo/getInfo",
                "method": "POST",
                "processData": false,
                "contentType": false,
                "mimeType": "multipart/form-data",
                "data": form,
                "success": function (response) {
                    var json = $.parseJSON(response);

                    $("#nameInput").val(json.name);

                    $("#inventoryInput").val(json.inventory);

                    $("#priceInput").val(json.price);

                    $("#brandInput").val(json.brand);

                    $("#categorySelect").val(json.category);
                    $("#categorySelect").change(function () {
                        productInfo.category = $("#categorySelect").val();
                    });

                    $("#crowedSelect").val(json.crowed);
                    $("#crowedSelect").change(function () {
                        productInfo.crowed = $("#crowedSelect").val();
                    });

                    $("#descriptionInput").val(json.description === undefined ? "" : json.description);

                    let split = json.size.split(",");
                    split.forEach(function (s) {
                        $("input[name=size]").each(function (key, value) {
                            if ($(value).val() === s) {
                                $(value).prop("checked", true);
                            }
                        });
                    });

                    productInfo.img1 = json.img1;
                    productInfo.img2 = json.img2;
                    productInfo.img3 = json.img3;
                    productInfo.img4 = json.img4;
                }
            };
            $.ajax(settings);
        },
        getSizes: function () {
            let size = "";
            $("input[name=size]:checked").each(function (key, value) {
                size = size + $(value).val() + ",";
            });
            productInfo.size = size.substring(0, size.length - 1);
        },
        cancel: function () {
            window.location.href = "product";
        },
        upload: function () {
            if (productInfo.spId === '') {
                this.getSizes();
                var form = new FormData();
                form.append("name", $("#nameInput").val());
                form.append("inventory", $("#inventoryInput").val());
                form.append("price", $("#priceInput").val());
                form.append("description", $("#descriptionInput").val());
                form.append("size", this.size);
                form.append("brand", $("#brandInput").val());
                form.append("category", $("#categorySelect").val());
                form.append("crowed", $("#crowedSelect").val());
                form.append("img1", this.pic1);
                form.append("img2", this.pic2);
                form.append("img3", this.pic3);
                form.append("img4", this.pic4);

                var settings = {
                    "async": true,
                    "crossDomain": true,
                    "url": "product/add",
                    "method": "POST",
                    "processData": false,
                    "contentType": false,
                    "mimeType": "multipart/form-data",
                    "data": form,
                    "success": function (response) {
                        if (response === "success") {
                            window.location.href = "product";
                        } else {
                            alert("商品添加失败");
                        }
                    }
                };
                $.ajax(settings);

            } else {
                this.getSizes();
                var form = new FormData();
                form.append("id", this.spId);
                form.append("name", $("#nameInput").val());
                form.append("inventory", $("#inventoryInput").val());
                form.append("price", $("#priceInput").val());
                form.append("description", $("#descriptionInput").val());
                form.append("size", this.size);
                form.append("brand", $("#brandInput").val());
                form.append("category", $("#categorySelect").val());
                form.append("crowed", $("#crowedSelect").val());
                form.append("img1", this.pic1);
                form.append("img2", this.pic2);
                form.append("img3", this.pic3);
                form.append("img4", this.pic4);

                var settings = {
                    "async": true,
                    "crossDomain": true,
                    "url": "product/update",
                    "method": "POST",
                    "processData": false,
                    "contentType": false,
                    "mimeType": "multipart/form-data",
                    "data": form,
                    "success": function (response) {
                        if (response === "success") {
                            window.location.href = "product";
                        } else {
                            alert("仓库更新失败");
                        }
                    }
                };
                $.ajax(settings);
            }
        }
    },
    mounted: function () {
        this.spId = window.location.search.substring(1).substring(5);
        this.getBriefInfo();
        this.selectPicture();
    }
});

$("#pictureInput").fileinput({
    language: 'zh',                                          // 多语言设置，需要引入local中相应的js，例如locales/zh.js
    uploadUrl: "",         // 上传地址
    minFileCount: 1,                                        // 最小上传数量
    maxFileCount: 4,                                        // 最大上传数量
    overwriteInitial: true,                        // 覆盖初始预览内容和标题设置
    showCancel: true,                                       // 显示取消按钮
    showZoom: false,                                         // 显示预览按钮
    showCaption: true,                                  // 显示文件文本框
    dropZoneEnabled: false,                          // 是否可拖拽
    browseLabel: '选择附件',                            // 浏览按钮内容
    showRemove: true,                                       // 显示移除按钮
    hideThumbnailContent: false,                  // 是否隐藏文件内容
    showUpload: false,
});