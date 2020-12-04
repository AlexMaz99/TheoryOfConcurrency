const fs = require('fs')
const async = require('async')
const walkdir = require('walkdir')
const {performance} = require('perf_hooks')

const paths = walkdir.sync('pam08');
let globalSum = 0;

function countLines(file, callback) {
    let counter = 0;
    fs.createReadStream(file).on('data', function (chunk) {
        counter += chunk.toString('utf8')
            .split(/\r\n|[\n\r\u0085\u2028\u2029]/g)
            .length - 1;
    }).on('end', function () {
        globalSum += counter;
        console.log(file, counter);
        callback()
    }).on('error', function (err) {
        console.error(err);
        callback()
    });
}

function countLinesSync() {
    const startTime = performance.now();
    async.waterfall(paths.map(path => (callback) => countLines(path, callback)), () => {
        console.log(globalSum);
        console.log("Time: ", (performance.now() - startTime));
    })
}

function countLinesAsync() {
    const startTime = performance.now();
    async.parallel(paths.map(path => (callback) => countLines(path, callback)), () => {
        console.log(globalSum);
        console.log("Time: ", (performance.now() - startTime));
    })
}

// countLinesSync()
countLinesAsync()