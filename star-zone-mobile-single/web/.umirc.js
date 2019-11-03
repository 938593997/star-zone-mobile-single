const path = require('path');

const server = 'http://127.0.0.1:8080';

// ref: https://umijs.org/config/
export default {
  plugins: [
    // ref: https://umijs.org/plugin/umi-plugin-react.html
    ['umi-plugin-react', {
      antd: true,
      dva: true,
      dynamicImport: false,
      dll: true,
      fastClick: true, // 优化react 项目在移动端的体验，可以 引入 fastclick ，解决点击延迟的类似问题
      routes: {
        exclude: [
          /model\.(j|t)sx?$/,
          /service\.(j|t)sx?$/,
          /models\//,
          /components\//,
          /services\//,
        ]
      },
      hardSource: false,
    }],
  ],
  alias:{
    components:path.resolve(__dirname,'src/components'),
        utils:path.resolve(__dirname,'src/utils'),
        services:path.resolve(__dirname,'src/services'),
        models:path.resolve(__dirname,'src/models'),
        // themes:path.resolve(__dirname,'src/themes'),
        images:path.resolve(__dirname,'src/assets'),
  },
  proxy: {
    "/szUser": {
      "target": 'http://127.0.0.1:8080',
      "changeOrigin": true,
      "pathRewrite": { "" : "" }// "pathRewrite": { "^/api" : "" }  { "^/api" : "/api" }
    }
  },
  define: {
    "process.env.apiUrl": 'http://127.0.0.1:9000/',
    "process.env.UMI_ENV": 'dev'
  },
}
