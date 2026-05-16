import request from '@/utils/request'

// 查询MEI-双创专家信息列表
export function listExpert(query) {
  return request({
    url: '/mei/expert/list',
    method: 'get',
    params: query
  })
}

// 查询MEI-双创专家信息详细
export function getExpert(id) {
  return request({
    url: '/mei/expert/' + id,
    method: 'get'
  })
}

// 新增MEI-双创专家信息
export function addExpert(data) {
  return request({
    url: '/mei/expert',
    method: 'post',
    data: data
  })
}

// 修改MEI-双创专家信息
export function updateExpert(data) {
  return request({
    url: '/mei/expert',
    method: 'put',
    data: data
  })
}

// 删除MEI-双创专家信息
export function delExpert(id) {
  return request({
    url: '/mei/expert/' + id,
    method: 'delete'
  })
}
