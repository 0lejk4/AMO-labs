window.d3 = d3;

new Vue({el: '#header'});


new Vue({
    el: '#lab4',
    data: () => ({
        a: 0.1,
        b: 3.00001,
        func: "5*x",
        epsilon: 0.001,
        valid: false,
        result: "",
        numberRules: [
            (v) => !!v || 'Number is required',
            (v) => {
                return /^([+\-])*\d+\.\d+$/.test(v) || 'It should be number and should be double'
            }
        ],
        email: '',
        emailRules: [
            (v) => !v || /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test(v) || 'E-mail must be valid'
        ]
    }),
    methods: {
        submitSolve() {
            functionPlot({
                target: '#func',
                data: [{
                    fn: this.func.replace(/Math\./g, '')
                }]
            });
            this.func = this.func.replace(/\+/g, "%2B");
            Vue.http.post(`/laba4?a=${this.a}&b=${this.b}&func=${this.func}&epsilon=${this.epsilon}`, {}, {headers: {'X-CSRF-Token': document.getElementById("_csrf").value}}).then(response => {
                console.log(response);
                this.result = response.bodyText;
            });
        }
    }
});