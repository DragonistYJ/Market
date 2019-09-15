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

let indexProduct = new Vue({
    el: '#productNumber',
    methods: {
        countAllProduct: function () {
            var settings = {
                "async": true,
                "crossDomain": true,
                "url": "index/countAllProduct",
                "method": "GET",
                "success": function (response) {
                    $('#productNumber').text(response);
                }
            };
            $.ajax(settings);
        }
    },
    mounted: function () {
        this.countAllProduct();
    }
});

let indexOrder = new Vue({
    el: '#orderNumber',
    methods: {
        countAllOrder: function () {
            var settings = {
                "async": true,
                "crossDomain": true,
                "url": "index/countTransitingOrder",
                "method": "GET",
                "success": function (response) {
                    $('#orderNumber').text(response);
                }
            };
            $.ajax(settings);
        }
    },
    mounted: function () {
        this.countAllOrder();
    }
});

// 柱状图的显示
let indexBarChart = new Vue({
    el: '#barChartBox',
    data: {
        numbers: []
    },
    methods: {
        countByCategory: function () {
            var form = new FormData();
            form.append("top", "true");
            form.append("bottom", "true");
            form.append("shoe", "true");
            form.append("acc", "true");

            var settings = {
                "async": true,
                "crossDomain": true,
                "url": "index/countByCategory",
                "method": "POST",
                "processData": false,
                "contentType": false,
                "mimeType": "multipart/form-data",
                "data": form,
                "success": function (response) {
                    var json = $.parseJSON(response);
                    indexBarChart.numbers.push(json.top);
                    indexBarChart.numbers.push(json.bottom);
                    indexBarChart.numbers.push(json.shoe);
                    indexBarChart.numbers.push(json.acc);
                    indexBarChart.showChart();
                }
            };
            $.ajax(settings);
        },
        showChart: function () {
            var barChartHome = new Chart($('#barChartHome'), {
                type: 'bar',
                options:
                    {
                        scales:
                            {
                                xAxes: [{
                                    display: true
                                }],
                                yAxes: [{
                                    display: true
                                }]
                            },
                        legend: {
                            display: false
                        }
                    },
                data: {
                    labels: ["上装", "下装", "鞋子", "饰品"],
                    datasets: [
                        {
                            backgroundColor: [
                                'rgb(121, 106, 238)',
                                'rgb(67, 205, 128)',
                                'rgb(255, 215, 0)',
                                'rgb(255, 106, 106)'
                            ],
                            data: indexBarChart.numbers
                        }
                    ]
                }
            });
        }
    },
    mounted: function () {
        this.countByCategory();
    }
});

// 主页的折线图绘制，速度有点问题
let indexLineChart = new Vue({
    el: '#lineChartBox',
    data: {
        labels: [],
        failNumber: [],
        successNumber: []
    },
    methods: {
        countOrder: function () {
            var time = (new Date()).getTime();
            var date = new Date(time);
            var year = date.getFullYear();
            var month = date.getMonth() > 8 ? (date.getMonth() + 1) : ("0" + (date.getMonth() + 1));
            var day = date.getDate() > 9 ? (date.getDate()) : ("0" + (date.getDate() + 1));
            var form = new FormData();
            form.append("toDate", year + "-" + month + "-" + day);
            form.append("length", "17");

            var settings = {
                "async": true,
                "crossDomain": true,
                "url": "index/orderNumbers",
                "method": "POST",
                "processData": false,
                "contentType": false,
                "mimeType": "multipart/form-data",
                "data": form,
                "success": function (response) {
                    var json = $.parseJSON(response);
                    indexLineChart.labels = json.dates;
                    indexLineChart.failNumber = json.fail;
                    indexLineChart.successNumber = json.success;
                    indexLineChart.showLineChart();
                }
            };
            $.ajax(settings);
        },
        showLineChart: function () {
            var legendState = true;
            if ($(window).outerWidth() < 576) {
                legendState = false;
            }

            var myLineChart = new Chart($('#lineChart'), {
                type: 'line',
                options: {
                    scales: {
                        xAxes: [{
                            display: true,
                            gridLines: {
                                display: false
                            }
                        }],
                        yAxes: [{
                            display: true,
                            gridLines: {
                                display: false
                            }
                        }]
                    },
                    legend: {
                        display: legendState
                    }
                },
                data: {
                    labels: indexLineChart.labels,
                    datasets: [
                        {
                            label: "异常订单数",
                            fill: true,
                            lineTension: 0,
                            backgroundColor: "transparent",
                            borderColor: '#f15765',
                            pointBorderColor: '#da4c59',
                            pointHoverBackgroundColor: '#da4c59',
                            borderCapStyle: 'butt',
                            borderDash: [],
                            borderDashOffset: 0.0,
                            borderJoinStyle: 'miter',
                            borderWidth: 1,
                            pointBackgroundColor: "#fff",
                            pointBorderWidth: 1,
                            pointHoverRadius: 5,
                            pointHoverBorderColor: "#fff",
                            pointHoverBorderWidth: 2,
                            pointRadius: 1,
                            pointHitRadius: 0,
                            data: indexLineChart.failNumber,
                            spanGaps: false
                        },
                        {
                            label: "正常订单数",
                            fill: true,
                            lineTension: 0,
                            backgroundColor: "transparent",
                            borderColor: "#54e69d",
                            pointHoverBackgroundColor: "#44c384",
                            borderCapStyle: 'butt',
                            borderDash: [],
                            borderDashOffset: 0.0,
                            borderJoinStyle: 'miter',
                            borderWidth: 1,
                            pointBorderColor: "#44c384",
                            pointBackgroundColor: "#fff",
                            pointBorderWidth: 1,
                            pointHoverRadius: 5,
                            pointHoverBorderColor: "#fff",
                            pointHoverBorderWidth: 2,
                            pointRadius: 1,
                            pointHitRadius: 10,
                            data: indexLineChart.successNumber,
                            spanGaps: false
                        }
                    ]
                }
            });
        }
    },
    mounted: function () {
        this.countOrder();
    }
});

// 主页第一行的四个单元格内容
//里面有注释需要去掉
let indexTitle = new Vue({
    el: '#index-title',
    data: {
        customerNumber: '',
        failOrder: '',
        successOrder: '',
        shortSupply: ''
    },
    methods: {
        countBuyer: function () {
            var settings = {
                "async": true,
                "crossDomain": true,
                "url": "index/countBuyer",
                "method": "GET",
                "success": function (response) {
                    indexTitle.customerNumber = response;
                }
            };
            $.ajax(settings);
        },
        countFailOrder: function () {
            var time = (new Date()).getTime() - 24 * 3600 * 1000 * 7;
            var date = new Date(time);
            var year = date.getFullYear();
            var month = date.getMonth() > 8 ? (date.getMonth() + 1) : ("0" + (date.getMonth() + 1));
            var day = date.getDate() > 9 ? (date.getDate()) : ("0" + (date.getDate() + 1));

            var form = new FormData();
            form.append("fromDate", year + "-" + month + "-" + day);
            var settings = {
                "async": true,
                "crossDomain": true,
                "url": "index/countFailOrder",
                "method": "POST",
                "processData": false,
                "contentType": false,
                "mimeType": "multipart/form-data",
                "data": form,
                "success": function (response) {
                    indexTitle.failOrder = response;
                }
            };
            $.ajax(settings);
        },
        countSuccessOrder: function () {
            var time = (new Date()).getTime() - 24 * 3600 * 1000 * 7;
            var date = new Date(time);
            var year = date.getFullYear();
            var month = date.getMonth() > 8 ? (date.getMonth() + 1) : ("0" + (date.getMonth() + 1));
            var day = date.getDate() > 9 ? (date.getDate()) : ("0" + (date.getDate() + 1));

            var form = new FormData();
            form.append("fromDate", year + "-" + month + "-" + day);
            var settings = {
                "async": true,
                "crossDomain": true,
                "url": "index/countSuccessOrder",
                "method": "POST",
                "processData": false,
                "contentType": false,
                "mimeType": "multipart/form-data",
                "data": form,
                "success": function (response) {
                    indexTitle.successOrder = response;
                }
            };
            $.ajax(settings);
        },
        countProductNoInventory: function () {
            var settings = {
                "async": true,
                "crossDomain": true,
                "url": "index/countProductNoInventory",
                "method": "GET",
                "success": function (response) {
                    indexTitle.shortSupply = response;
                }
            };
            $.ajax(settings);
        }
    },
    mounted: function () {
        this.countBuyer();
        this.countFailOrder();
        this.countSuccessOrder();
        this.countProductNoInventory()
        // setInterval(this.countBuyer, 5000);
        // setInterval(this.countFailOrder, 5000);
        // setInterval(this.countSuccessOrder, 5000);
        // setInterval(this.countProductNoInventory, 5000);
    }
});