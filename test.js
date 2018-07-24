class Test {
    get body() {
        console.log(`你来到值${this._body}`)
        return this._body;
    }

    set body(v) {

        console.log(`你来赋值 ${v}`)
        this._body = v;
    }

}


let t = new Test();

t.body = "richard";
console.log(t.body)



