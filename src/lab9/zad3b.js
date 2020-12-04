const async = require('async');

function printAsync(s, cb) {
    var delay = Math.floor((Math.random() * 1000) + 500);
    setTimeout(function () {
        console.log(s);
        if (cb) cb();
    }, delay);
}

function task(n) {
    return new Promise((resolve, reject) => {
        printAsync(n, function () {
            resolve(n);
        });
    });
}


// 'then' returns a new Promise, therefore we can chain another 'then'.
// In this case 'task(x)' directly returns a Promise object, however
// 'then' could also return a value in which case it would be wrapped
// in a Promise that would be automatically resolved with that value.

function tasks(callback) {
    task(1)
        .then((n) => {
            console.log('task', n, 'done');
            return task(2);
        })
        .then((n) => {
            console.log('task', n, 'done');
            return task(3);
        })
        .then((n) => {
            console.log('task', n, 'done');
            callback();
        });
}

async.waterfall([tasks, tasks, tasks, tasks], () => console.log("done"))