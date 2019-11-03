export const baseUrl = process.env.apiUrl
export let baseUrls = null
const ENV = process.env.UMI_ENV
if (ENV  === 'prd' || ENV  === 'test') {
  baseUrls = 'http://47.101.187.84:9005/'
} else if (ENV  === 'dev') {
  baseUrls = 'http://127.0.0.1:9005/'
}

export const setFormData = (obj) => {
    const formData = new FormData();
    if (obj && obj instanceof Object) {
        const keys = Object.keys(obj);
        if (keys && keys.length) {
            keys.forEach(key => {
                formData.append(key, obj[key]);
            });
        }
    }
    return formData;
}

export const setUrlEncoded = (obj) => {
    let urlEncoded = '';
    if(obj && obj instanceof Object) {
        const keys = Object.keys(obj);
        if(keys && keys.length) {
            keys.forEach((key, index) => {
                urlEncoded += `${key}=${obj[key]}`;
                if(index + 1 < keys.length){
                    urlEncoded += '&';
                }
            });
        }
    }
    return urlEncoded;
}
