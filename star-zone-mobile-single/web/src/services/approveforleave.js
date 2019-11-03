import proxyRequest from '../utils/request';
import { baseUrl } from '../utils/baseServer';

export function getListsByPage(params) {
  return proxyRequest.get(`${baseUrl}api/applyForLeave/getApplyForLeavePage`, params);
}

export function saveApply(params) {
  return proxyRequest.post(`${baseUrl}api/applyForLeave/insertApplyForLeave`, params);
}

export function queryApplyFollow(params) {
  return proxyRequest.get(`${baseUrl}api/applyForLeave/queryApplyFollow`, params);
}

export function queryApplyInfoByProcessId(params) {
  return proxyRequest.get(`${baseUrl}api/applyForLeave/queryApplyInfoByProcessId`, params);
}

export function submitApprove(params) {
  return proxyRequest.post(`${baseUrl}api/applyForLeave/submitApprove`, params);
}

export function reSubmitApply(params) {
  return proxyRequest.post(`${baseUrl}api/applyForLeave/reSubmitApply`, params);
}
