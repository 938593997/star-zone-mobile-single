import proxyRequest from '../utils/request';
import { baseUrl } from '../utils/baseServer';

export function getSpendDetailsLists(params) {
  return proxyRequest.get(`${baseUrl}api/szSpendDetails/querySzSpendDetailsList`, params);
}

export function delSpendDetails(params) {
  return proxyRequest.delete(`${baseUrl}api/szSpendDetails/deleteSzSpendDetailsByPrimaryKey`, params);
}

export function saveSpendDetails(params) {
  return proxyRequest.post(`${baseUrl}api/szSpendDetails/insertSzSpendDetails`, params);
}

export function modifySpendDetails(params) {
  return proxyRequest.put(`${baseUrl}api/szSpendDetails/updateSzSpendDetailsByPrimaryKey`, params);
}

export function getSpendTypeLists(params) {
  return proxyRequest.get(`${baseUrl}api/szSpendType/querySzSpendTypeList`, params);
}

export function findPieAndBarDatas(params) {
  return proxyRequest.get(`${baseUrl}api/szSpendDetails/findPieAndBarDatas`, params);
}
