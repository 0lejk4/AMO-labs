new Vue({el: '#header'});


new Vue({
    el: '#lab5',
    data: () => ({
        A: "1 1 1\n2 3 1\n1 -1 -1",
        B: "4 9 -2",
        X: "1 2 1",
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
        validate(A, B, X) {
            const hA = A.length;
            if (B.length !== hA || X.length !== hA) return false
            if (hA <= 0) return false;
            for (const row of A) {
                if (row.length !== hA) return false;
            }
            return true;
        },
        submitSolve() {
            let reqA = [];
            this.A.split('\n').forEach(i => reqA.push(i.split(' ').map(Number)));
            const reqB = this.B.split(' ').map(Number);
            const reqX = this.X.split(' ').map(Number);
            if (this.validate(reqA, reqB, reqX)) {
                Vue.http.post(`/laba5?B=${JSON.stringify(reqB)}&X=${JSON.stringify(reqX)}&epsilon=${this.epsilon}`, reqA, {headers: {'X-CSRF-Token': document.getElementById("_csrf").value}}).then(response => {
                    console.log(response);
                    this.result = response.bodyText;
                });
            } else {
                this.result = 'Try appropriate data';
            }
        }
    }
});