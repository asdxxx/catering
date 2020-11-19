(function () {
    var myChart = echarts.init(document.getElementById("main"));
    var option = {
        grid: {
            left: "10%",
            top: "30%",
            right: "0%",
            bottom: "0%",
            containLabel: true,
        },
        tooltip: {
            trigger: "axis",
            axisPointer: {
                type: "line",
            },
            backgroundColor: "none",
            formatter: function (params) {
                return `<div style="background: url(../index/images/icon.png); width:100px;height:80px;position: relative"><p style="position: absolute; left: 18%; top: 25%; color: #fff; text-align: center; font-size: 12px;" >${'收运：' + params[0].value + "次" + '</br>' +'检查：' +  params[1].value + "次"
                    }</p></div>`;
            },
            textStyle: {
                color: "#000",
                fontSize: 10,
            },
        },
        xAxis: [
            {
                type: "category",
                data: [
                    "01-01",
                    "01-02",
                    "01-03",
                    "01-04",
                    "01-05",
                    "01-06",
                    "01-07",
                ],
                axisTick: {
                    show: false,
                },
                axisLine: {
                    lineStyle: {
                        color: "#999",
                    },
                },
            },
        ],
        yAxis: [
            {
                type: "value",
                splitNumber: 5,
                splitLine: {
                    lineStyle: {
                        type: "dashed",
                        color: "#DDD",
                    },
                },
                axisTick: {
                    show: false,
                },
                axisLine: {
                    show: false,
                    lineStyle: {
                        color: "#333",
                    },
                },
                nameTextStyle: {
                    color: "#999",
                },
                splitArea: {
                    show: false,
                },
            },
        ],
        series: [
            {
                name: "次",
                type: "line",
                data: [0, 0, 0, 0, 0, 0, 0],
                lineStyle: {
                    normal: {
                        width: 5,
                        color: {
                            type: "linear",
                            colorStops: [
                                {
                                    offset: 0,
                                    color: "#4CA8FF", // 0% 处的颜色
                                },
                                {
                                    offset: 1,
                                    color: "#4EA8FF", // 100% 处的颜色
                                },
                            ],
                            globalCoord: false, // 缺省为 false
                        },
                        shadowColor: "rgba(78, 168, 255, 0.2)",
                        shadowBlur: 5,
                        shadowOffsetY: 30,
                    },
                },
                itemStyle: {
                    normal: {
                        color: "#fff",
                        borderWidth: 10,
                        shadowColor: "rgba(72,216,191, 0.3)",
                        shadowBlur: 100,
                        borderColor: "#4ea8ff",
                    },
                },
                smooth: true,
            }, {
                name: "次",
                type: "line",
                data: [0, 0, 0, 0, 0, 0, 0],
                lineStyle: {
                    normal: {
                        width: 5,
                        color: {
                            type: "linear",
                            colorStops: [
                                {
                                    offset: 0,
                                    color: "#FF4CFD", // 0% 处的颜色
                                },
                                {
                                    offset: 1,
                                    color: "#FF4CFD", // 100% 处的颜色
                                },
                            ],
                            globalCoord: false, // 缺省为 false
                        },
                        shadowColor: "rgba(78, 168, 255, 0.2)",
                        shadowBlur: 5,
                        shadowOffsetY: 30,
                    },
                },
                itemStyle: {
                    normal: {
                        color: "#fff",
                        borderWidth: 10,
                        shadowColor: "rgba(72,216,191, 0.3)",
                        shadowBlur: 100,
                        borderColor: "#FF4CFD",
                    },
                },
                smooth: true,
            },
        ],
    };
    myChart.setOption(option);

    window.addEventListener("resize", function () {
        myChart.resize();
    });
    $(".line .btn").on("click", "a", function () {
        console.log($(this).index());
        console.log(monthData[$(this).index()])
        $(this).addClass("active");
        $(this).siblings().removeClass("active");
        let obj = monthData[$(this).index()];
        option.xAxis[0].data = obj[0].data;
        option.series[0].data = obj[1].data;
        option.series[1].data = obj[2].data;
        myChart.setOption(option);
    });

})();

(function () {
    // 1. 实例化对象
    let myChart = echarts.init(document.getElementById("main1"));
    var datas = [
        {
            name: "已完成",
            value: 0,
            company: "%",
            ringColor: [
                {
                    offset: 0,
                    color: "#02d6fc", // 0% 处的颜色
                },
                {
                    offset: 1,
                    color: "#367bec", // 100% 处的颜色
                },
            ],
        },
        {
            name: "未完成",
            value: 100,
        },
    ];
    let sum = datas.reduce((a, b) => {
            return a + b.value * 1;
        },
        0
        )
    ;
    let percent = [];
    for (let i = 0; i < datas.length; i++) {
        percent.push((datas[i].value / sum).toFixed(2) * 100);
    }

    let option = {
        title: {
            text: percent[1] + datas[0].company,
            x: "center",
            y: "center",
            textStyle: {
                fontWeight: "normal",
                color: "#4CA8FF",
                fontSize: "32",
            },
        },
        tooltip: {
            trigger: "item",
            formatter: "{a} <br/>{b}: {c} ({d}%)",
        },

        color: ["#E4E4EE"],

        series: [
            {
                name: "收运情况",
                type: "pie",
                radius: ["50%", "70%"],
                avoidLabelOverlap: false,
                label: {
                    show: false,
                    position: "center",
                },
                labelLine: {
                    show: false,
                },
                right: "35%",
                data: [
                    {
                        value: datas[0].value,
                        name: "已完成普查数",
                        itemStyle: {
                            normal: {
                                color: 'blue',
                                label: {
                                    show: false,
                                },
                                labelLine: {
                                    show: false,
                                },
                            },
                        },
                    },
                    {
                        name: "未完成普查数",
                        value: datas[1].value,
                        itemStyle: {
                            normal: {
                                color: 'green',
                                label: {
                                    show: false,
                                },
                                labelLine: {
                                    show: false,
                                },
                            },
                        },
                    },
                ],
            },
        ],
    };
    myChart.setOption(option);
    window.addEventListener("resize", function () {
        myChart.resize();
    });
})();


(function () {
    var myChart = echarts.init(document.getElementById("main2"));

    let option = {
        grid: [
            {
                x: "10%",
                y: "7%",
                width: "30%",
                height: "80%",
            },
            {
                x2: "7%",
                y: "7%",
                width: "30%",
                height: "70%",
            },
        ],
        xAxis: [
            {
                gridIndex: 0,
                show: false,
            },
            {
                gridIndex: 1,
                show: false,
            },
        ],
        yAxis: [
            {
                gridIndex: 0,
                type: "category",
                axisLine: {
                    show: false,
                },
                axisTick: {
                    show: false,
                },
                axisLabel: {
                    color: "#999",
                },
                data: [
                    "茶山街道",
                    "郭溪街道",
                    "泽雅镇",
                    "新桥街道",
                    "娄桥街道",
                    "丽岳街道",
                    "梧田街道",
                ],
            },
            {
                gridIndex: 1,
                type: "category",
                axisLine: {
                    show: false,
                },
                axisTick: {
                    show: false,
                },
                axisLabel: {
                    color: "#999",
                },
                data: [
                    "仙岩街道",
                    "南白象街道",
                    "三洋街道",
                    "景山街道",
                    "覆溪街道",
                    "潘桥街道",
                ],
            },
        ],
        series: [
            {
                name: "I",
                type: "bar",
                xAxisIndex: 0,
                yAxisIndex: 0,
                barWidth: "10px",
                showBackground: true,
                backgroundStyle: {
                    color: "rgba(220, 220, 220, 0.4)",
                    barBorderRadius: [30, 30, 30, 30], //圆角大小
                },
                label: {
                    show: true,
                    position: "right",
                    formatter: ["{dialog|{c}}"].join("\n"),
                    rich: {
                        dialog: {
                            height: 16,
                            color: "#fff",
                            align: "center",
                            fontSize: 12,
                            backgroundColor: {
                                image: "../index/images/item4.png",
                            },
                        },
                    },
                },
                itemStyle: {
                    normal: {
                        color: function (params) {
                            let colorList = [
                                ["#D398FF", "#61BFFF"],
                                ["#D398FF", "#61BFFF"],
                                ["#E094F9", "#FA8C8D"],
                                ["#89DAD4", "#02CDAD"],
                            ];
                            // console.log(params.dataIndex)
                            let index = params.dataIndex;
                            if (params.dataIndex >= colorList.length) {
                                index = params.dataIndex - colorList.length;
                            }
                            return new echarts.graphic.LinearGradient(0, 0, 1, 0, [
                                {
                                    offset: 0,
                                    color: colorList[index][0],
                                },
                                {
                                    offset: 1,
                                    color: colorList[index][1],
                                },
                            ]);
                        },
                        barBorderRadius: [30, 30, 30, 30], //圆角大小
                    },
                },
                data: [
                    {
                        name: "茶山街道",

                        value: 20,
                    },
                    {
                        name: "韩溪街道",

                        value: 30,
                    },
                    {
                        name: "泽雅镇",

                        value: 200,
                    },
                    {
                        name: "新桥街道",

                        value: 247,
                    },
                    {
                        name: "娄桥街道",
                        value: 230,
                    },
                    {
                        name: "丽岳街道",
                        value: 450,
                    },
                    {
                        name: "梧田街道",
                        value: 600,
                    },
                ],

                // markLine: markLineOpt
            },
            {
                name: "II",
                type: "bar",
                xAxisIndex: 1,
                yAxisIndex: 1,
                barWidth: "10px",
                showBackground: true,
                backgroundStyle: {
                    color: "rgba(220, 220, 220, 0.4)",
                    barBorderRadius: [30, 30, 30, 30], //圆角大小
                },
                label: {
                    show: true,
                    position: "right",
                    formatter: ["{dialog|{c}}"].join("\n"),
                    rich: {
                        dialog: {
                            height: 16,
                            color: "#fff",
                            align: "center",
                            fontSize: 12,
                            backgroundColor: {
                                image: "../index/images/item4.png",
                            },
                        },
                    },
                },
                itemStyle: {
                    normal: {
                        color: function (params) {
                            let colorList = [
                                ["#D398FF", "#61BFFF"],
                                ["#D398FF", "#61BFFF"],
                                ["#E094F9", "#FA8C8D"],
                                ["#89DAD4", "#02CDAD"],
                            ];
                            // console.log(params.dataIndex)
                            let index = params.dataIndex;
                            if (params.dataIndex >= colorList.length) {
                                index = params.dataIndex - colorList.length;
                            }
                            return new echarts.graphic.LinearGradient(0, 0, 1, 0, [
                                {
                                    offset: 0,
                                    color: colorList[index][0],
                                },
                                {
                                    offset: 1,
                                    color: colorList[index][1],
                                },
                            ]);
                        },
                        barBorderRadius: [30, 30, 30, 30], //圆角大小
                    },
                },
                data: [
                    {
                        name: "仙岩街道",

                        value: 20,
                    },
                    {
                        name: "南白象街道",

                        value: 30,
                    },
                    {
                        name: "三洋街道",

                        value: 200,
                    },
                    {
                        name: "景山街道",

                        value: 247,
                    },
                    {
                        name: "覆溪街道",
                        value: 230,
                    },
                    {
                        name: "潘桥街道",
                        value: 444,
                    },
                ],

                // markLine: markLineOpt
            },
        ],
    };
    myChart.setOption(option);

    // $("#change").change(function () {
    //   let str = $(this).val();
    //   console.log(str);
    //   let xdata = []; // 获取series的data
    //   let yaxis = [];
    //   // let ydata = option.yAxis.data
    //   // let datas = option.series[0].data[0]
    //   let val = option.series[0].data;
    //   let val2 = option.series[1].data;
    //   console.log(val);
    //   console.log(val2);
    //   val.map((item) => {
    //     if (item.name === str) {
    //       return xdata.push(item.value);
    //     }
    //   });
    //   // console.log(option.series[0].data)
    //   // switch(str) {
    //   //   case '茶山街道':
    //   // }
    //   if (str !== "全部") {
    //     yaxis.push(str);
    //     console.log(yaxis)
    //     myChart.setOption({
    //       yAxis: [{
    //         data: yaxis,
    //       }],
    //       series: [
    //         {
    //           data: xdata,
    //         },
    //         {
    //           data: xdata
    //         }
    //     ],
    //     });
    //   } else {
    //     myChart.setOption(option);
    //   }
    // });
    window.addEventListener("resize", function () {
        myChart.resize();
    });
})();

