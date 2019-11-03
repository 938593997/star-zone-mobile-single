import proxyRequest from '../utils/request';
import { baseUrl } from '../utils/baseServer';

export function CheckName(params) {
  return proxyRequest.get(`${baseUrl}apiUser/szUser/CheckName`, params);
}

export function login(params) {
  // return proxyRequest.post(`${baseUrl}apiUser/szUser/login`, params);
  return proxyRequest.get(`${baseUrl}apiUser/szUser/login`, params);
}

export function register(params) {
  return proxyRequest.post(`${baseUrl}apiUser/szUser/insertSzUser`, params);
}

export function quit(params) {
  return proxyRequest.get(`${baseUrl}apiUser/szUser/quit`, params);
}
