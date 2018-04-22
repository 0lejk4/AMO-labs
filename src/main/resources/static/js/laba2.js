new Vue({el: '#header'});

function redraw(data, id) {
    d3.select("#" + id).selectAll("g").remove();
    var width = data.length * 50,
        height = 700;


    var chart = d3.select("#" + id)
        .attr("width", width)
        .attr("height", height);
    var barWidth = width / data.length;

    var bar = chart.selectAll("g")
        .data(data)
        .enter().append("g")
        .attr("transform", function (d, i) {
            return "translate(" + i * barWidth + ",0)";
        })

    bar.append("rect")
        .attr("y", function (d) {
            return 100;
        })
        .attr("height", function (d) {
            return d * 10;
        })
        .attr("width", barWidth - 1)
        .attr("fill", "#00b174")

    bar.append("text")
        .attr("fill", "#00f")
        .style("font-size", "10px")
        .attr("y", 80)
        .attr("x", barWidth / 2)
        .attr("text-anchor", "end")
        .text(function (d) {
            return d;
        })
};


new Vue({
    el: '#lab2',
    created() {


        Vue.http.get("/laba2/table").then(response => {
            console.log(response);
            google.charts.load('current', {'packages': ['line']});
            google.charts.setOnLoadCallback(drawServerChart);

            function drawServerChart() {

                var data = new google.visualization.DataTable();
                data.addColumn('number', 'Розмір масиву');
                data.addColumn('number', 'Кількість часу');
                const array = response.body;
                data.addRows(array);

                var options = {
                    chart: {
                        title: 'Графік залежності швидкості сортування в залежності від кількості елементів масиву ',
                        subtitle: 'Пораховано в режимі реального часу'
                    },
                    width: 900,
                    height: 500,
                    colors: ['#a52714']
                };

                var chart = new google.charts.Line(document.getElementById('linechart_server'));

                chart.draw(data, google.charts.Line.convertOptions(options));
            }

        });

    },
    data: () => ({
        n: 12,
        valid: false,
        readFile: false,
        random: null,
        sorted: null,
        actions: [],
        result: "",
        time: "",
        numberRules: [
            (v) => !!v || 'Number is required',
            (v) => {
                return /^\d+$/.test(v) || 'It should be number and should be Long'
            }
        ],
        email: '',
        emailRules: [
            (v) => !v || /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test(v) || 'E-mail must be valid'
        ]
    }),
    methods: {
        submitSort() {
            if (this.n <= 500000 && (this.valid || this.readFile) ) {
                // Native form submission is not yet supported
                this.random = null;
                this.sorted = null;
                if (this.n <= 100 && !this.readFile) {
                    Redraw(this.n);
                    Bose();
                } else {
                    Vue.http.post(`/laba2?readFile=${this.readFile}&n=${this.n}&email=${this.email}`, {}, {headers: {'X-CSRF-Token': document.getElementById("_csrf").value}}).then(response => {
                        console.log(response);
                        const parts = response.bodyText.split("@");
                        this.random = parts[0];
                        this.sorted = parts[1];
                        this.time = parts[2];
                        if (this.n <= 2000) {
                            redraw(JSON.parse(this.random), "array");
                            redraw(JSON.parse(this.sorted), "sorted");
                        }

                    });
                }
            }
        }
    }
});
let count = 1 + 40,
    durationTime = 2000 / 50,
    array = d3.shuffle(d3.range(1, count)),
    unsortedArray = [...array
    ],
    sortedArray = [],
    stop = false,
    steps = 0,
    bogoShuffles = 0;

let margin = {top: 40, right: 40, bottom: 180, left: 40},
    width = 960 - margin.left - margin.right,
    height = 700 - margin.top - margin.bottom;

let barWidth = width / count;

let x;


function Redraw(number) {
    d3.select("#array").selectAll("g").remove();
    count = +number;
    durationTime = 2000 / count;
    array = d3.shuffle(d3.range(1, count));
    unsortedArray = [...array];
    sortedArray = [];
    stop = falsesteps = 0;
    bogoShuffles = 0;

    margin = {top: 40, right: 40, bottom: 180, left: 40},
        width = 2048 - margin.left - margin.right,
        height = 1280 - margin.top - margin.bottom;

    barWidth = width / count;

    x = d3.scaleLinear()
        .domain([0, count])
        .range([0, width]);

    let svg = d3.select("#array")
        .attr("width", width + margin.left + margin.right)
        .attr("height", height + margin.top + margin.bottom)
        .append("g")
        .attr("transform", "translate(" + margin.left + "," + margin.top + ")")

    let rects = svg.append("g")
        .attr("transform", "translate(" + barWidth + ",2)")
        .selectAll("rect")
        .data(unsortedArray)
        .enter().append("rect")

    let labels = svg.selectAll("text")
        .data(unsortedArray)
        .enter().append("text")

    labels.attr("id", function (d) {
        return "text" + d
    })
        .attr("transform", function (d, i) {
            return "translate(" + x(i) + ",0)"
        })
        .html(function (d) {
            return d;
        })

    rects.attr("id", function (d) {
        return "rect" + d
    })
        .attr("transform", function (d, i) {
            return "translate(" + (x(i) - barWidth) + ",0)"
        })
        .attr("width", barWidth * .9)
        .attr("height", function (d) {
            return d * barWidth / 3
        })
}

function reset() {
    unsortedArray = [...array
    ]
    ;
    sortedArray = [];
    stop = false;

    d3.select("#counter").html(steps = 0)

    labels.attr("class", "")
        .transition().duration(2000)
        .attr("transform", function (d, i) {
            return "translate(" + (x(i)) + ", 0)"
        })

    rects.attr("class", "")
        .transition().duration(2000)
        .attr("transform", function (d, i) {
            return "translate(" + (x(i - 1)) + ", 0)"
        })
}

function Bose(array = unsortedArray) {
    sortedArray = array;
    let m = 1;
    let j;
    do {
        j = 0;
        while (j + m < array.length) {
            // d3.timeout(function() {}, durationTime); \
            Sli(array, j, m, m);
            j += 2 * m;
        }
        m *= 2;
    } while (m < array.length);
    // return array;
}


function Sli(array, j, r, m) {
    if (j + r < array.length) {

        if (m == 1) {
            d3.select("#counter").html(++steps);

            if (array[j] > array[j + r]) {
                let x = array[j];
                array[j] = array[j + r];
                array[j + r] = x;
                slide(array[j], j);
                slide(array[j + r], j + r);
            }

            return;

        } else {
            m /= 2;
            d3.timeout(function () {
                Sli(array, j, r, m);
            }, durationTime * 15);

            if (j + r + m < array.length) {
                d3.timeout(function () {
                    Sli(array, j + m, r, m);
                }, durationTime * 15);
            }
            d3.timeout(function () {
                Sli(array, j + m, r - m, m);
            }, durationTime * 15);
        }

    }
}

// console.log(Bose([2,3,1,34,2,3,2]))


function slide(d, i) {
    d3.select("#text" + d)
        .transition().duration(durationTime * 15)
        .attr("transform", function (d) {
            return "translate(" + (x(i)) + ", 0)"
        })

    d3.select("#rect" + d)
        .transition().duration(durationTime * 15)
        .attr("transform", function (d) {
            return "translate(" + (x(i - 1)) + ", 0)"
        })
}

google.charts.load('current', {'packages': ['line']});
google.charts.setOnLoadCallback(drawChart);
google.charts.setOnLoadCallback(drawCalculatedChart);


function drawCalculatedChart() {

    var data = new google.visualization.DataTable();
    data.addColumn('number', 'Розмір масиву');
    data.addColumn('number', 'Складність');

    data.addRows([
        [500, 1349],
        [1000, 3000],
        [2000, 6602],
        [3000, 10431],
        [4000, 14408],
        [5000, 18494],
        [10000, 40000],
        [25000, 109948],
        [50000, 234948],
        [250000, 1349485],
        [500000, 2849485],
        [1000000, 6000000]

    ]);

    var options = {
        chart: {
            title: 'Графік теоретичної обчислювальної складності',
            subtitle: 'O(n * log(n))'
        },
        width: 900,
        height: 500,
        colors:['#fdaaff']
    };

    var chart = new google.charts.Line(document.getElementById('linechart_calculated'));

    chart.draw(data, google.charts.Line.convertOptions(options));
}

function drawChart() {

    var data = new google.visualization.DataTable();
    data.addColumn('number', 'Розмір масиву');
    data.addColumn('number', 'Кількість часу');

    data.addRows([
        [500, 0.001],
        [1000, 0.001],
        [2000, 0.002],
        [3000, 0.003],
        [4000, 0.004],
        [5000, 0.006],
        [10000, 0.019],
        [25000, 0.077],
        [50000, 0.236],
        [250000, 2.815],
        [500000, 8.447],
        [1000000, 25.192]

    ]);

    var options = {
        chart: {
            title: 'Графік залежності швидкості сортування в залежності від кількості елементів масиву',
            subtitle: 'в секундах'
        },
        width: 900,
        height: 500,
        colors:['#097138']
    };

    var chart = new google.charts.Line(document.getElementById('linechart_material'));

    chart.draw(data, google.charts.Line.convertOptions(options));
}