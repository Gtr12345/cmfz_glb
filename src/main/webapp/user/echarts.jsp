<%@page pageEncoding="UTF-8" isELIgnored="false" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>ECharts</title>
</head>
<body>
<!-- 为ECharts准备一个具备大小（宽高）的Dom -->
<div id="main" style="width: 600px;height:400px;"></div>
<script type="text/javascript">
    // 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('main'));

    // 指定图表的配置项和数据
    var option = {
        title: {
            text: '上半年如坑选手'
        },
        tooltip: {},
        legend: {
            data: ['男', '女']
        },
        xAxis: {
            data: ["1月", "2月", "3月", "4月", "5月", "6月"]
        },
        yAxis: {},
        series: [{
            name: '男',
            type: 'bar',
            data: [5, 20, 66, 10, 70, 10]
        }, {
            name: '女',
            type: 'bar',
            data: [23, 23, 35, 60, 18, 20]
        }]

    };

    // 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);
    $.ajax({
        url: "${pageContext.request.contextPath}/user/echarts",
        type: "get",
        datatype: "json",
        success: function (data) {
            myChart.setOption({
                xAxis: {
                    data: data.names
                },
                series: [{
                    name: '男',
                    type: 'line',//bar:柱状图
                    data: data.count1
                }, {
                    name: '女',
                    type: 'line',//bar:柱状图
                    data: data.count
                }]
            });
        }
    })
</script>
</body>
</html>