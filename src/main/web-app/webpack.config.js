const path = require('path');
module.exports = {
    mode: 'development',
    devtool: 'inline-source-map',
    entry: './src/index.js',
    output: {
        filename: './dist/main.js',
        path: __dirname
    }
}