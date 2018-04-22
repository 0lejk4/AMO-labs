new Vue({el: '#header'});

new Vue({
    el: '#lab3',
    created() {
        Vue.http.get("/laba3/getY").then(response => {
            console.log(response);
            x_array = Array.from(new Array(80), (val, index) => index * 0.1 + 1);
            y_array = response.body;
            google.charts.load('current', {'packages': ['line']});
            google.charts.setOnLoadCallback(drawRealChart);
            google.charts.setOnLoadCallback(drawLagrangeChart);
            google.charts.setOnLoadCallback(drawNewtonChart);
            google.charts.setOnLoadCallback(drawEitkenChart);
            google.charts.setOnLoadCallback(drawBlurinessChart);


            function drawBlurinessChart() {

                var data = new google.visualization.DataTable();
                data.addColumn('number', 'X');
                data.addColumn('number', 'Похибка');
                Vue.http.get("/laba3/newton").then(response => {
                    console.log(response);
                    eitken_array = response.body;

                    let array = twoMap(x_array, minusByAbs(y_array, eitken_array));
                    console.log(array);

                    data.addRows(array);

                    var options = {
                        chart: {
                            title: 'Похибка інтерполяції'
                        },
                        width: 900,
                        height: 500,
                        vAxis: {
                            format: '0.0000000000000',
                            viewWindow: {min: 0, max: 0.001}
                        },
                        hAxis: {viewWindow: {min: 1, max: 5}},
                        colors: ['#0e18a5'
                            , '#fff600']
                    };

                    var chart = new google.charts.Line(document.getElementById('linechart_bluriness'));

                    chart.draw(data, google.charts.Line.convertOptions(options));
                });

            }

            function threeMap(array1, array2, array3) {
                const points = [1, 1.4, 1.8, 2.2, 2.6, 3, 3.4, 3.8, 4.2, 4.6, 5];
                let result = [];
                for (let i = 0; i < array1.length; i++) {
                    result.push([array1[i], array2[i], array3[i], points.includes(Math.round(array1[i] * 10) / 10) ? array2[i] : null]);
                }
                return result;
            }


            function minusByAbs(array1, array2) {
                let result = [];
                for (let i = 0; i < array1.length; i++) {
                    result.push(Math.abs(array1[i] - array2[i]));
                }
                return result;
            }

            function twoMap(array1, array2) {
                let result = [];
                for (let i = 0; i < array1.length; i++) {
                    result.push([array1[i], array2[i]]);
                }
                return result;
            }


            function drawRealChart() {

                var data = new google.visualization.DataTable();
                data.addColumn('number', 'X');
                data.addColumn('number', 'Y');

                let array = twoMap(x_array, y_array);

                data.addRows(array);

                var options = {
                    chart: {
                        title: 'Початкова Функція'
                    },
                    width: 900,
                    height: 500,
                    colors: ['#0e18a5']
                };

                var chart = new google.charts.Line(document.getElementById('linechart_real'));

                chart.draw(data, google.charts.Line.convertOptions(options));

            }

            function drawEitkenChart() {

                var data = new google.visualization.DataTable();
                data.addColumn('number', 'X');
                data.addColumn('number', 'Y');
                data.addColumn('number', 'Z - Eitken');
                data.addColumn('number', 'points');

                Vue.http.get("/laba3/eitken").then(response => {
                    console.log(response);
                    eitken_array = response.body;

                    let array = threeMap(x_array, y_array, eitken_array);


                    data.addRows(array);

                    var options = {
                        chart: {
                            title: 'Інтерполяція Ейткіна'
                        },
                        width: 900,
                        height: 500,
                        vAxis: {viewWindow: {min: -10, max: 5}},
                        hAxis: {viewWindow: {min: 1, max: 8.8}},
                        colors: ['#0e18a5',
                            '#fff600',
                            '#18ff00']
                        , series: {
                            0: {
                                // set any applicable options on the first series
                            },
                            1: {
                                // set any applicable options on the first series
                            },
                            2: {
                                // set any applicable options on the first series
                            },
                            3: {
                                // set the options on the second series
                                lineWidth: 0,
                                pointSize: 10,
                                visibleInLegend: true
                            }
                        }
                    };

                    var chart = new google.charts.Line(document.getElementById('linechart_eitken'));

                    chart.draw(data, google.charts.Line.convertOptions(options));
                });

            }

            function drawNewtonChart() {

                var data = new google.visualization.DataTable();
                data.addColumn('number', 'X');
                data.addColumn('number', 'Y');
                data.addColumn('number', 'Z - Newton');
                data.addColumn('number', 'points');
                Vue.http.get("/laba3/newton").then(response => {
                    console.log(response);
                    newton_array = response.body;


                    let array = threeMap(x_array, y_array, newton_array);

                    data.addRows(array);

                    var options = {
                        chart: {
                            title: 'Інтерполяція Ньютона'
                        },
                        width: 900,
                        height: 500,
                        vAxis: {viewWindow: {min: -10, max: 5}},
                        hAxis: {viewWindow: {min: 1, max: 8.8}},
                        colors: ['#0e18a5',
                            '#fff600',
                            '#18ff00']
                        , series: {
                            0: {
                                // set any applicable options on the first series
                            },
                            1: {
                                // set any applicable options on the first series
                            },
                            2: {
                                // set any applicable options on the first series
                            },
                            3: {
                                // set the options on the second series
                                lineWidth: 0,
                                pointSize: 10,
                                visibleInLegend: true
                            }
                        }
                    };

                    var chart = new google.charts.Line(document.getElementById('linechart_newton'));

                    chart.draw(data, google.charts.Line.convertOptions(options));
                });
            }

            function drawLagrangeChart() {

                var data = new google.visualization.DataTable();
                data.addColumn('number', 'X');
                data.addColumn('number', 'Y');
                data.addColumn('number', 'Z - Lagrange');
                data.addColumn('number', 'points');
                Vue.http.get("/laba3/lagrange").then(response => {
                    console.log(response);
                    lagrange_array = response.body;

                    let array = threeMap(x_array, y_array, lagrange_array);

                    data.addRows(array);

                    var options = {
                        chart: {
                            title: 'Інтерполяція Лагранжа'
                        },
                        width: 900,
                        height: 500,
                        vAxis: {viewWindow: {min: -10, max: 5}},
                        hAxis: {viewWindow: {min: 1, max: 8.8}},
                        colors: ['#0e18a5',
                            '#fff600',
                            '#18ff00']
                        , series: {
                            0: {
                                // set any applicable options on the first series
                            },
                            1: {
                                // set any applicable options on the first series
                            },
                            2: {
                                // set any applicable options on the first series
                            },
                            3: {
                                // set the options on the second series
                                lineWidth: 0,
                                pointSize: 10,
                                visibleInLegend: true
                            }
                        }
                    };

                    var chart = new google.charts.Line(document.getElementById('linechart_lagrange'));

                    chart.draw(data, google.charts.Line.convertOptions(options));
                });
            }

        });

    },
    data: () => ({
        valid: false,
        result: "",
        x_array: [],
        y_array: [],
        newton_array: [],
        lagrange_array: [],
        eitkin_array: [],
        time: "",
    })
});
