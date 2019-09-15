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

var productList = new Vue({
    el: "#productList",
    data: {
        maxPrice: 10000000,
        minPrice: 0,
        male: true,
        female: true,
        teenager: true,
        child: true,
        top: true,
        bottom: true,
        shoe: true,
        acc: true,
        have: true,
        no: true,
        products: []
    },
    methods: {
        itemCLick: function (id) {
            window.location.href = "productInformation?spId=" + id;
        },
        getProductByName: function () {
            var form = new FormData();
            form.append("name", $("#nameInput").val());

            var settings = {
                "async": true,
                "crossDomain": true,
                "url": "product/name",
                "method": "POST",
                "processData": false,
                "contentType": false,
                "mimeType": "multipart/form-data",
                "data": form,
                "success": function (response) {
                    let json = $.parseJSON(response);
                    json.forEach(function (item) {
                        if (item.category === "top") item.category = "上装";
                        else if (item.category === "bottom") item.category = "下装";
                        else if (item.category === "shoe") item.category = "鞋子";
                        else item.category = "饰品";

                        if (item.crowed === "male") item.crowed = "男装";
                        else if (item.crowed === "female") item.crowed = "女装";
                        else if (item.crowed === "teenager") item.crowed = "青少年";
                        else item.crowed = "儿童";
                    });
                    productList.products = json;
                }
            };
            $.ajax(settings);
        },
        getProduct: function () {
            var form = new FormData();
            form.append("maxPrice", this.maxPrice);
            form.append("minPrice", this.minPrice);
            form.append("male", this.male);
            form.append("female", this.female);
            form.append("teenager", this.teenager);
            form.append("child", this.child);
            form.append("top", this.top);
            form.append("bottom", this.bottom);
            form.append("shoe", this.shoe);
            form.append("acc", this.acc);
            form.append("have", this.have);
            form.append("no", this.no);

            var settings = {
                "async": true,
                "crossDomain": true,
                "url": "product/productList",
                "method": "POST",
                "processData": false,
                "contentType": false,
                "mimeType": "multipart/form-data",
                "data": form,
                "success": function (response) {
                    let json = $.parseJSON(response);
                    json.forEach(function (item) {
                        if (item.category === "top") item.category = "上装";
                        else if (item.category === "bottom") item.category = "下装";
                        else if (item.category === "shoe") item.category = "鞋子";
                        else item.category = "饰品";

                        if (item.crowed === "male") item.crowed = "男装";
                        else if (item.crowed === "female") item.crowed = "女装";
                        else if (item.crowed === "teenager") item.crowed = "青少年";
                        else item.crowed = "儿童";
                    });
                    productList.products = json;
                }
            };
            $.ajax(settings);
        },
        priceClick: function () {
            this.minPrice = $('#minInput').val() === "" ? this.minPrice : $('#minInput').val();
            this.maxPrice = $('#maxInput').val() === "" ? this.minPrice : $('#maxInput').val();
            this.getProduct();
        },
        addProduct: function () {
            window.location.href = "productInformation";
        }
    },
    mounted: function () {
        $('#category').multiselect({
            buttonWidth: '160px',
            selectAllText: '全选',
            dropRight: true,
            nonSelectedText: '部位分类',
            allSelectedText: '全部部位',
            onChange: function (option, checked) {
                if ($(option).val() === "top") {
                    productList.top = checked;
                } else if ($(option).val() === "bottom") {
                    productList.bottom = checked;
                } else if ($(option).val() === "shoe") {
                    productList.shoe = checked;
                } else {
                    productList.acc = checked;
                }
                productList.getProduct();
            }
        });
        $('#crowed').multiselect({
            buttonWidth: '160px',
            selectAllText: '全选',
            dropRight: true,
            nonSelectedText: '人群分类',
            allSelectedText: '全部人群',
            onChange: function (option, checked) {
                if ($(option).val() === "male") {
                    productList.male = checked;
                } else if ($(option).val() === "female") {
                    productList.female = checked;
                } else if ($(option).val() === "teenager") {
                    productList.teenager = checked;
                } else {
                    productList.child = checked;
                }
                productList.getProduct();
            }
        });
        $('#quantity').multiselect({
            buttonWidth: '160px',
            selectAllText: '全选',
            dropRight: true,
            nonSelectedText: '库存量',
            allSelectedText: '全部库存',
            onChange: function (option, checked) {
                if ($(option).val() === "have") {
                    productList.have = checked;
                } else {
                    productList.no = checked;
                }
                productList.getProduct();
            }
        });
        this.getProduct();
    }
});

// 类别分类饼状图
var productPieCategory = new Vue({
    el: "#pieChartCategory",
    data: {
        number: []
    },
    methods: {
        getData: function () {
            var settings = {
                "async": true,
                "crossDomain": true,
                "url": "product/pieCategory",
                "method": "POST",
                "success": function (response) {
                    var json = $.parseJSON(response);
                    productPieCategory.number = json.category;
                    productPieCategory.showChart();
                }
            };

            $.ajax(settings);
        },
        showChart: function () {
            var pieChartCategory = new Chart($('#pieChartCategory'), {
                type: 'pie',
                data: {
                    labels: [
                        "上装",
                        "下装",
                        "鞋子",
                        "饰品"
                    ],
                    datasets: [
                        {
                            data: productPieCategory.number,
                            borderWidth: 0,
                            backgroundColor: [
                                '#FFC125',
                                "#EEB422",
                                "#CD9B1D",
                                "#8B6914"
                            ],
                            hoverBackgroundColor: [
                                '#FFC125',
                                "#EEB422",
                                "#CD9B1D",
                                "#8B6914"
                            ]
                        }]
                }
            });
        }
    },
    mounted: function () {
        this.getData();
    }
});

// 人群分类饼状图
var productPieCrowed = new Vue({
    el: "#pieChartCrowd",
    data: {
        number: []
    },
    methods: {
        getData: function () {
            var settings = {
                "async": true,
                "crossDomain": true,
                "url": "product/pieCrowed",
                "method": "POST",
                "success": function (response) {
                    var json = $.parseJSON(response);
                    productPieCrowed.number = json.crowed;
                    productPieCrowed.showChart();
                }
            };

            $.ajax(settings);
        },
        showChart: function () {
            var pieChartCrowd = new Chart($('#pieChartCrowd'), {
                type: 'pie',
                data: {
                    labels: [
                        "男装",
                        "女装",
                        "青少年",
                        "儿童"
                    ],
                    datasets: [
                        {
                            data: productPieCrowed.number,
                            borderWidth: 0,
                            backgroundColor: [
                                '#44b2d7',
                                "#59c2e6",
                                "#71d1f2",
                                "#00FFFF"
                            ],
                            hoverBackgroundColor: [
                                '#44b2d7',
                                "#59c2e6",
                                "#71d1f2",
                                "#00FFFF"
                            ]
                        }]
                }
            });
        }
    },
    mounted: function () {
        this.getData();
    }
});

// 柱状图的绘制
var productBarChart = new Vue({
    el: '#barChart',
    data: {
        maleNumber: [],
        femaleNumber: [],
        teenagerNumber: [],
        childNumber: []
    },
    methods: {
        getData: function () {
            var settings = {
                "async": true,
                "crossDomain": true,
                "url": "product/barChart",
                "method": "POST",
                "success": function (response) {
                    var json = $.parseJSON(response);
                    productBarChart.maleNumber = json.male;
                    productBarChart.femaleNumber = json.female;
                    productBarChart.teenagerNumber = json.teenager;
                    productBarChart.childNumber = json.child;
                    productBarChart.showChart();
                }
            };
            $.ajax(settings);
        },
        showChart: function () {
            var barChartCrowd = new Chart($('#barChartExample'), {
                type: 'bar',
                options: {
                    scales: {
                        xAxes: [{
                            display: true,
                            gridLines: {
                                color: '#eee'
                            }
                        }],
                        yAxes: [{
                            display: true,
                            gridLines: {
                                color: '#eee'
                            }
                        }]
                    }
                },
                data: {
                    labels: ["上装", "下装", "鞋子", "饰品"],
                    datasets: [
                        {
                            label: "男装",
                            backgroundColor: [
                                'rgb(255, 106, 106)',
                                'rgb(255, 106, 106)',
                                'rgb(255, 106, 106)',
                                'rgb(255, 106, 106)'
                            ],
                            data: productBarChart.maleNumber
                        },
                        {
                            label: "女装",
                            backgroundColor: [
                                'rgb(67, 205, 128)',
                                'rgb(67, 205, 128)',
                                'rgb(67, 205, 128)',
                                'rgb(67, 205, 128)'
                            ],
                            data: productBarChart.femaleNumber
                        },
                        {
                            label: "青少年",
                            backgroundColor: [
                                'rgb(121, 106, 238)',
                                'rgb(121, 106, 238)',
                                'rgb(121, 106, 238)',
                                'rgb(121, 106, 238)'
                            ],
                            data: productBarChart.teenagerNumber
                        },
                        {
                            label: "儿童",
                            backgroundColor: [
                                'rgb(238 180 34)',
                                'rgb(238 180 34)',
                                'rgb(238 180 34)',
                                'rgb(238 180 34)'
                            ],
                            data: productBarChart.childNumber
                        }
                    ]
                }
            });
        }
    },
    mounted: function () {
        this.getData();
    }
});