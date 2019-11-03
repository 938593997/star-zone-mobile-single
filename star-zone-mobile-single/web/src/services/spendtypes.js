import proxyRequest from '../utils/request';
import { baseUrl } from '../utils/baseServer';

export function getSpendTypeLists(params) {
  return proxyRequest.get(`${baseUrl}api/szSpendType/querySzSpendTypeList`, params);
}

export function delSpendType(params) {
  return proxyRequest.delete(`${baseUrl}api/szSpendType/deleteSzSpendTypeByPrimaryKey`, params);
}

export function saveSpendType(params) {
  return proxyRequest.post(`${baseUrl}api/szSpendType/insertSzSpendType`, params);
}

export function modifySpendType(params) {
  return proxyRequest.put(`${baseUrl}api/szSpendType/updateSzSpendTypeByPrimaryKey`, params);
}

export function getSpendTypeListsByPage(params) {
  return proxyRequest.get(`${baseUrl}api/szSpendType/getSzSpendTypePage`, params);
}

export function findPieAndBarDatas(params) {
  return proxyRequest.get(`${baseUrl}api/szSpendType/findPieAndBarDatas`, params);
}
