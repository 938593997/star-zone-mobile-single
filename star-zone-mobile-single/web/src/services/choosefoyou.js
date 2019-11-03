import proxyRequest from '../utils/request';
import { baseUrl } from '../utils/baseServer';

export function querySzMenuList(params) {
  return proxyRequest.get(`${baseUrl}api/szMenu/querySzMenuList`, params);
}

export function getChooseLists(params) { // 普通接口
  return proxyRequest.get(`${baseUrl}/api/szChooseforyou/querySzChooseforyouList`, params);
}

// export async function getChooseLists(params) { // 用Example的接口
//   return proxyRequest.get(`${baseUrl}api/szChooseforyou/querySzChooseforyouListExample`, params);
// }

export function deleteChoose(params) {
  return proxyRequest.delete(`${baseUrl}api/szChooseforyou/deleteSzChooseforyouByPrimaryKey`, params);
}

export function addChoose(params) {
  return proxyRequest.post(`${baseUrl}api/szChooseforyou/insertSzChooseforyou`, params);
}

export function editChoose(params) {
  return proxyRequest.put(`${baseUrl}api/szChooseforyou/updateSzChooseforyouByPrimaryKey`, params);
}

export function checkHasConfig(params) {
  return proxyRequest.get(`${baseUrl}api/szChooseforyouset/querySzChooseforyousetList`, params);
}

export function addChooseItems(params) {
  return proxyRequest.post(`${baseUrl}api/szChooseforyouset/insertSzChooseforyouset`, params);
}

export function choice(params) {
  return proxyRequest.get(`${baseUrl}api/szChooseforyouset/choice`, params);
}

export function findChooseRes(params) {
  return proxyRequest.get(`${baseUrl}api/szChooseresults/querySzChooseresultsList`, params);
}
