import request from '@/utils/request'

// 查询MEI-在线咨询服务列表
export function listConsult(query) {
  return request({
    url: '/mei/consult/list',
    method: 'get',
    params: query
  })
}

// 查询MEI-在线咨询服务详细
export function getConsult(id) {
  return request({
    url: '/mei/consult/' + id,
    method: 'get'
  })
}

// 新增MEI-在线咨询服务
export function addConsult(data) {
  return request({
    url: '/mei/consult',
    method: 'post',
    data: data
  })
}

// 修改MEI-在线咨询服务
export function updateConsult(data) {
  return request({
    url: '/mei/consult',
    method: 'put',
    data: data
  })
}

// 删除MEI-在线咨询服务
export function delConsult(id) {
  return request({
    url: '/mei/consult/' + id,
    method: 'delete'
  })
}
