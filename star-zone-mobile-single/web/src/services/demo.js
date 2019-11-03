import proxyRequest from '../utils/request';
import { baseUrl } from '../utils/baseServer';

export function querySzMenuList(params) {
  return proxyRequest.get(`${baseUrl}api/szMenu/querySzMenuList`, params);
}

export function orderCount(params) {
  return proxyRequest.get(`${baseUrl}recycleapi/order/user/orderCount`, params);
}

export function getUserInfoById(params) {
  return proxyRequest.get(`${baseUrl}apiUser/szUser/getSzUserById`, params);
}

export function savePersonalInfo(params) {
  return proxyRequest.post(`${baseUrl}apiUser/szUser/updateSzUserByPrimaryKey`, params);
}
