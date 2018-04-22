new Vue({ el: '#header' });
new Vue({el: '#lab1',
    data: () => ({
    active: null,
    valid1: true,
    valid2: true,
    valid3: true,
    readFile:false,
    readDb:false,
    a: '',
    b: '',
    c: '',
    d: '',
    l: '',
    k: '',
    w: '',
    f: '',
    resultLinear:'',
    resultCycle:'',
    resultBranching:'',
    numberRules: [
        (v) => !!v || 'Number is required',
        (v) => { return /^\d+$/.test(v) || 'It should be number and should be Long'}
],
email: '',
    emailRules: [
    (v) => !v || /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test(v) || 'E-mail must be valid'
]
}),
methods: {
    submitLinear() {
        if (this.valid1 || this.readFile || this.readDb ) {
            // Native form submission is not yet supported
            Vue.http.post(`/laba1/linear?readDb=${this.readDb}&readFile=${this.readFile}&a=${this.a}&b=${this.b}&c=${this.c}&d=${this.d}&email=${this.email}`, {},{headers: {'X-CSRF-Token': document.getElementById("_csrf").value}}).then(response => {
                console.log(response);
                this.resultLinear = response.bodyText;
        });

    }
    },
    submitCycle() {
        if (this.valid2 || this.readFile || this.readDb) {
            // Native form submission is not yet supported
            Vue.http.post(`/laba1/cycle?readDb=${this.readDb}&readFile=${this.readFile}&b=${this.b}&email=${this.email}`, {},{headers: {'X-CSRF-Token': document.getElementById("_csrf").value}}).then(response => {
                console.log(response);
            this.resultCycle = response.bodyText;
        });

        }
    },
    submitBranching() {
        if (this.valid3 || this.readFile || this.readDb) {
            // Native form submission is not yet supported
            Vue.http.post(`/laba1/branching?readDb=${this.readDb}&readFile=${this.readFile}&f=${this.f}&d=${this.d}&l=${this.l}&k=${this.k}&w=${this.w}&email=${this.email}`, {},{headers: {'X-CSRF-Token': document.getElementById("_csrf").value}}).then(response => {
                console.log(response);
            this.resultBranching = response.bodyText;
        });

        }
    },
    clear () {
        this.$refs.formCycle.reset();
        this.$refs.formBranching.reset();
        this.$refs.formLinear.reset();
    }
}});