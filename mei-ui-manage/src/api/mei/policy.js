import request from '@/utils/request'

// 查询MEI-政策公告信息列表
export function listPolicy(query) {
  return request({
    url: '/mei/policy/list',
    method: 'get',
    params: query
  })
}

// 查询MEI-政策公告信息详细
export function getPolicy(id) {
  return request({
    url: '/mei/policy/' + id,
    method: 'get'
  })
}

// 新增MEI-政策公告信息
export function addPolicy(data) {
  return request({
    url: '/mei/policy',
    method: 'post',
    data: data
  })
}

// 修改MEI-政策公告信息
export function updatePolicy(data) {
  return request({
    url: '/mei/policy',
    method: 'put',
    data: data
  })
}

// 删除MEI-政策公告信息
export function delPolicy(id) {
  return request({
    url: '/mei/policy/' + id,
    method: 'delete'
  })
}
